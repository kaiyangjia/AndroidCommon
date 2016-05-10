package com.jiakaiyang.androidcommon.androidcommon.widget.list;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by kaiyangjia on 2016/3/3.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> itemViews;
    private View convertView;

    public CommonViewHolder(View convertView) {
        super(convertView);
        itemViews = new SparseArray<>();
        this.convertView = convertView;
        convertView.setTag(this);
    }


    public <T extends View>T getView(int viewId){
        View view = itemViews.get(viewId);
        if(view == null){
            view = convertView.findViewById(viewId);
            itemViews.put(viewId, view);
        }

        return (T)view;
    }
}
