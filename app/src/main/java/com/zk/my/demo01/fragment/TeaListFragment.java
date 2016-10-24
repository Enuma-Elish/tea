package com.zk.my.demo01.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zk.my.demo01.R;
import com.zk.my.demo01.activity.DetailActivity;
import com.zk.my.demo01.adapter.MyAdapter;
import com.zk.my.demo01.adapter.MyQuickAdapter;
import com.zk.my.demo01.adapter.NewsAdapter;
import com.zk.my.demo01.constant.MyConstants;

import com.zk.my.demo01.info.DataBean;
import com.zk.my.demo01.info.NewsInfo;
import com.zk.my.demo01.util.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class TeaListFragment extends Fragment implements NewsAdapter.IOnItemClickListener {
    private static final String TAG = "TeaListFragment_Tag";
    @BindView(R.id.tea_rv)
    RecyclerView teaRv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    //数据的url
    private String url;

    //页面的类型
    private int type;

    //新闻的数据源
    private List<NewsInfo.DataBean> newsDatas;
    //适配器
    private NewsAdapter newsAdapter;

    //DOUYU 数据源 适配器
    private List<DataBean> mList;
    private MyQuickAdapter myQuickAdapter;
    private MyAdapter myAdapter;

    public static final int STATE_UPDETE = 100;
    public static final int STATE_REFRESH_OVER = 101;
    //刷新画面的Hanndler
    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == STATE_UPDETE) {
                newsAdapter.notifyDataSetChanged();//刷新适配器
            }
            if (msg.what == STATE_REFRESH_OVER) {
                refreshLayout.setRefreshing(false);//停止进度条
            }

        }
    };

    //当前页数
    private int currentPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_list, container, false);
        ButterKnife.bind(this, view);

        //取类型
        Bundle bundle = getArguments();
        type = bundle.getInt(MyConstants.KEY_FRAGMENT_TEY);
        url = bundle.getString(MyConstants.KEY_FRAGMENT_URL_TEY);

        //从网络取数据
        getData(currentPage);
        //加载视图
        initView();

        //下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //从网络取数据
                currentPage++;
                getData(currentPage);
            }
        });
        return view;
    }
    //TODO initView()
    private void initView() {
        //TODO 茶百科
        newsDatas = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), newsDatas,this);
        teaRv.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false));
        teaRv.setAdapter(newsAdapter);
        //TODO DOUYU
        mList=new ArrayList<>();
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        teaRv.setLayoutManager(layoutManager);
        //第三方适配器
        myQuickAdapter=new MyQuickAdapter(R.layout.item_face,mList);
        teaRv.setAdapter(myQuickAdapter);
        //自定义recyclerView适配器
        //myAdapter=new MyAdapter(this,mList);
    }
    //TODO getData(int currentPage)
    private void getData(int page) {

        String newUrl = null;
        if (type == 0) { //头条
            newUrl = String.format(url, page);
        } else {
            newUrl = String.format(url, page, type);
        }

        OkHttpUtils.doGET(newUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = updateHandler.obtainMessage();
                msg.what = STATE_REFRESH_OVER;
                updateHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = updateHandler.obtainMessage();
                msg.what = STATE_REFRESH_OVER;
                updateHandler.sendMessage(msg);//------->成功也先要发送停止停止刷新

                if (response != null && response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String result = responseBody.string();
                        //json解析
                        Gson gson = new Gson();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            String dataArrayTxt = dataArray.toString();

                            TypeToken<List<NewsInfo.DataBean>> typeToken = new
                                    TypeToken<List<NewsInfo.DataBean>>() {
                                    };

                            List<NewsInfo.DataBean> datas = gson.fromJson(dataArrayTxt, typeToken
                                    .getType());
                            newsDatas.addAll(0, datas);
                            //这里的是 通知适配器更新
                            Message msg2 = updateHandler.obtainMessage();
                            msg2.what = STATE_UPDETE; //
                            updateHandler.sendMessage(msg2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }
    //TODO 接口回调方法
    @Override
    public void onclick(int position) {
        //得到id和title
        String id = newsDatas.get(position).getId();
        String title = newsDatas.get(position).getTitle();

        //跳转
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(MyConstants.KEY_DETAIL_ID, id);
        intent.putExtra(MyConstants.KEY_DETAIL_TITLE, title);
        startActivity(intent);


    }
}
