package com.zk.my.demo01.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.facebook.drawee.view.SimpleDraweeView;
import com.zk.my.demo01.R;
import com.zk.my.demo01.info.DataBean;

import java.util.List;


/**
 * Created by My on 2016/10/19.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<DataBean> datas;

    public MyAdapter(Context context, List<DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_face, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataBean data = datas.get(position);
        holder.simpleDraweeView.setImageURI(Uri.parse(data.getVertical_src()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
       SimpleDraweeView simpleDraweeView;
        public MyViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView=(SimpleDraweeView)itemView.findViewById(R.id.simpleview);
        }
    }
}
