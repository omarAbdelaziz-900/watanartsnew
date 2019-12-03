package com.WattanArt.ui.base;


import android.support.v7.widget.RecyclerView;

import java.util.List;



/**
 * Created by Android Team on 1/30/2018.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public abstract void add(Object r);

    public abstract void addAll(List<Object> moveResults);

    public abstract void remove(Object result);

    public abstract void clear();

    public abstract boolean isEmpty();

    public abstract void addLoadingFooter() ;

    public abstract void removeLoadingFooter();

    public abstract Object getItem(int position);

    public abstract void showRetry(boolean show, String errorMsg) ;


}
