package com.jiakaiyang.androidcommon.androidcommon.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kaiyangjia on 2016/3/3.
 *
 */
public abstract class CommonRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private Context context;
    private List<Object> data;

    public CommonRecyclerViewAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(getItemLayout(viewType), parent, false);
        CommonViewHolder holder = new CommonViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        onFillData(holder, getData().get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Object> getData() {
        return data;
    }

    abstract public int getItemLayout(int viewType);

    abstract public void onFillData(CommonViewHolder holder, Object data);
}
