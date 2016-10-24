package com.zk.my.demo01.adapter;

import android.net.Uri;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zk.my.demo01.R;
import com.zk.my.demo01.info.DataBean;

import java.util.List;


/**
 * Created by My on 2016/10/20.
 */
public class MyQuickAdapter extends BaseQuickAdapter<DataBean> {
    public MyQuickAdapter(int layoutResId, List<DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {

        SimpleDraweeView simpleDraweeView = helper.getView(R.id.simpleview);
        //加载图片
        simpleDraweeView.setImageURI(Uri.parse(item.getVertical_src()));
    }
}
