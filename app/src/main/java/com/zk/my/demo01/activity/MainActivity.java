package com.zk.my.demo01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zk.my.demo01.R;
import com.zk.my.demo01.adapter.MyViewAdapter;
import com.zk.my.demo01.adapter.TeaAdapter;
import com.zk.my.demo01.base.BaseActivity;
import com.zk.my.demo01.constant.MyConstants;
import com.zk.my.demo01.constant.URLConstants;
import com.zk.my.demo01.fragment.TeaListFragment;
import com.zk.my.demo01.info.ADInfo;
import com.zk.my.demo01.util.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //标题常量
    public static final String[] TITLES = {"头条", "百科", "咨询", "经营"};
    public static final int[] TYPES = {0, 52, 16, 54, 53}; //数据类型，用于网络请求


    @BindView(R.id.ad_tv)
    TextView adTv;
    @BindView(R.id.ad_dot_layout)
    LinearLayout adDotLayout;
    @BindView(R.id.main_tab)
    TabLayout mainTab;
    @BindView(R.id.tea_vp)
    ViewPager teaVp;
    @BindView(R.id.navigationview)
    NavigationView navigationview;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.ad_vp)
    ViewPager adVp;

    //广告View的集合
    private List<View> adViews;
    private MyViewAdapter adAdapter;
    private Handler adHandler = new Handler();//----------一只看不见的手
    private ADRunnable adRunnable;

    //广告的数据----------------->javabean对象----->解析的数据最终结果是对象啊
    private ADInfo adData;

    //主画面
    private TeaAdapter teaAdapter;
    private List<Fragment> teaFragments;

    //返回键点击时间
    private long backStartTime, backEndTime;

    //TODO --------------------------侧滑页面相关------------------------------
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化AD控件
        initAD();
        //初始化AD数据
        initADData();

        //初始化main控件以及main数据
        initMain();

        //初始化侧滑栏
        initNavigation();

    }

    //TODO 初始化侧滑栏
    private void initNavigation() {
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main:
                        Log.e(TAG, "点击了---------------------->" + item);
                        break;
                    case R.id.menu_setting:
                        Log.e(TAG, "点击了---------------------->" + item);
                        break;
                    case R.id.menu_my:
                        Log.e(TAG, "点击了---------------------->" + item);
                        //分享
//                        new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.QQ)
//                                .withText("hello")
//                                .setCallback(umShareListener)
//                                .share();
                        new ShareAction(MainActivity.this).withText("hello")
                                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                                .setCallback(umShareListener).open();
                        break;
                    case R.id.menu_count:
                        Log.e(TAG, "点击了---------------------->" + item);
                        //登录
                        UMShareAPI mShareAPI = UMShareAPI.get( MainActivity.this );
                        mShareAPI.doOauthVerify(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                        //mShareAPI.getPlatformInfo(UserinfoActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                        break;
                    case R.id.menu_info:
                        Log.e(TAG, "点击了---------------------->" + item);
                        break;
                    case R.id.menu_code:
                        Log.e(TAG, "点击了---------------------->" + item);
                        //扫描二维码
                        Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
                        startActivity(intent);
                        break;
                }
                drawerlayout.closeDrawers();
                return true;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //TODO onKeyDown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backEndTime = System.currentTimeMillis();
            if (backEndTime - backStartTime > 2000) {
                Toast.makeText(this, "连续按两次退出程序", Toast.LENGTH_SHORT).show();
                backStartTime = backEndTime;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //TODO initAD();
    private void initMain() {
        initMainData();
        teaAdapter = new TeaAdapter(getSupportFragmentManager(), teaFragments, TITLES);
        teaVp.setAdapter(teaAdapter);

        //关联TabLayout
        mainTab.setupWithViewPager(teaVp);
    }

    private void initMainData() {
        teaFragments = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            TeaListFragment teaListFragment = new TeaListFragment();
            Bundle bundle = new Bundle();
            if (i == 0) {
                bundle.putString(MyConstants.KEY_FRAGMENT_URL_TEY, URLConstants.TT_URL);
            } else if(i<4){
                bundle.putString(MyConstants.KEY_FRAGMENT_URL_TEY, URLConstants.OTHRER_URL);
            }else{
                //TODO 斗鱼tablayout
            }
            bundle.putInt(MyConstants.KEY_FRAGMENT_TEY, TYPES[i]);
            teaListFragment.setArguments(bundle);
            teaFragments.add(teaListFragment);
        }
    }

    //TODO initADData();
    private void initADData() {
        OkHttpUtils.doGET(URLConstants.AD_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String adText = body.string();//----------->得到结果
                        Gson gson = new Gson();
                        adData = gson.fromJson(adText, ADInfo.class);//------>解析成javabean对象
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initADText(0);//----->该方法在主线程中调用,所以该方法就属于主线程
                                initADImage();//
                            }
                        });
                    }
                }
            }
        });
    }

    private void initADImage() {
        for (int i = 0; i < adViews.size(); i++) {
            //获得广告的ImageView
            ImageView adIv = (ImageView) adViews.get(i);
            //TODO 使用Picasso加载资
            Picasso.with(this)
                    .load(adData.getData().get(i).getImage())
                    .fit()
                    .into(adIv);
        }
    }

    //TODO initADText();
    private void initADText(int current) {
        if (adData != null) {
            //获得当前ViewPager位置
            adTv.setText(adData.getData().get(current).getName());
        }
    }

    //TODO initAD();
    private void initAD() {
        //初始化AD的子View
        initADItemView();
        //初始化广告圆点
        initDot();
        //初始化AD的ViewPager
        initADViewPager();
        //轮播
        adRunnable = new ADRunnable();
    }

    private void initADViewPager() {
        adAdapter = new MyViewAdapter(adViews);
        adVp.setAdapter(adAdapter);
        //ViewPager滑动监听
        adVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                //处理圆点
                int itemCount = adDotLayout.getChildCount(); //获得Layout中子View的数量
                for (int i = 0; i < itemCount; i++) {
                    View view = adDotLayout.getChildAt(i);//提取子View
                    if (i == position) {
                        view.setSelected(true);
                    } else {
                        view.setSelected(false);
                    }
                }
                //处理文字
                initADText(position);
            }
        });
        //触摸监听，按下的时候取消handler回调，松手的时候，重新开启
        adVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction(); //获得动作
                switch (action) {
                    case MotionEvent.ACTION_DOWN: //按下
                        adHandler.removeCallbacks(adRunnable);
                        break;
                    case MotionEvent.ACTION_UP: //提前
                        adHandler.postDelayed(adRunnable, 2000);
                        break;
                }
                return false;
            }
        });
    }

    private void initDot() {
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i < 3 - 1) {
                params.rightMargin = 30;
            }
            if (i == 0) {
                iv.setSelected(true);
            }
            iv.setLayoutParams(params); //设置布局
            iv.setImageResource(R.drawable.dot); //设置图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY); //设置填充属性
            adDotLayout.addView(iv);
        }
    }

    private void initADItemView() {
        adViews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            )); //设置布局
            iv.setImageResource(R.mipmap.ic_logo); //设置默认图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER); //设置填充属性
            adViews.add(iv);
        }
    }

    //TODO ADRunnable();
    //广告轮播的任务
    class ADRunnable implements Runnable {
        @Override
        public void run() {
            int currentPosition = adVp.getCurrentItem(); //获得当前的位置
            currentPosition++;
            if (currentPosition > 2) {
                currentPosition = 0;
            }
            adVp.setCurrentItem(currentPosition);//重新设置位置
            //            adRunnable = new ADRunnable();
            adHandler.postDelayed(adRunnable, 2000);
        }
    }
}
