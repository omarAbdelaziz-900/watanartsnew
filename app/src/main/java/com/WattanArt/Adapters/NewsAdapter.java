package com.WattanArt.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.Utils.Callbacks.EmptyCallback;
import com.WattanArt.Adapters.Holders.EmptyViewHolder;
import com.WattanArt.Adapters.Holders.LoadingVH;
import com.WattanArt.R;
import com.WattanArt.model.Response.NewsModel;
import com.WattanArt.model.Response.NewsModel.Result;
import com.WattanArt.ui.base.BaseAdapter;
import com.WattanArt.ui.base.BaseViewHolder;
import com.WattanArt.Utils.Callbacks.RetryPageLoadHelper;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends BaseAdapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_LOADING = 2;

    private List<NewsModel.Result> mItemsList;


    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private RetryPageLoadHelper mRetryPageLoadHelper;
    EmptyCallback mEmptyCallback;

    public NewsAdapter(List<NewsModel.Result> mNewsList, RetryPageLoadHelper retryPageLoadHelper, EmptyCallback emptyCallback) {
        mRetryPageLoadHelper = retryPageLoadHelper;
        mEmptyCallback = emptyCallback;
        mItemsList = mNewsList;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));

            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_item, parent, false), mEmptyCallback);

            case VIEW_TYPE_LOADING:
                return new LoadingVH(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_item, parent, false), mEmptyCallback);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == VIEW_TYPE_NORMAL)
            holder.onBind(position);

    }

    @Override
    public int getItemViewType(int position) {
        if (mItemsList != null && mItemsList.size() > 0) {
//            return (position == mItemsList.size() - 1 && isLoadingAdded) ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
            if (mItemsList.get(position) == null)
                return VIEW_TYPE_LOADING;

            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addDataForList(List<NewsModel.Result> newDataResultList) {
        if (mItemsList == null) {
            mItemsList = new ArrayList<>();
        }
        mItemsList.addAll(newDataResultList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItemsList != null && mItemsList.size() > 0) {
            return mItemsList.size();
        } else {
            return 0;
        }
    }


    @Override
    public void add(Object r) {
        if (mItemsList == null)
            return;

        mItemsList.add((NewsModel.Result) r);
        notifyItemInserted(mItemsList.size() - 1);
    }

    @Override
    public void addAll(List<Object> moveResults) {
        if (mItemsList == null)
            return;
        for (Object object : moveResults)
            add(object);
    }

    @Override
    public void remove(Object result) {
        if (mItemsList == null)
            return;
        int position = mItemsList.indexOf(result);
        if (position > -1) {
            mItemsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        if (mItemsList == null)
            return;
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    @Override
    public boolean isEmpty() {
        if (mItemsList == null)
            return true;
        return getItemCount() == 0;
    }

    @Override
    public void addLoadingFooter() {
        if (mItemsList == null)
            return;
        isLoadingAdded = true;
        add(null);
        Log.e("Loader" , "loader footer added");
    }

    @Override
    public void removeLoadingFooter() {
        if (mItemsList == null)
            return;

        isLoadingAdded = false;

        int position = mItemsList.size() - 1;
        Result result = (Result) getItem(position);

        if (result == null) {
            mItemsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }


    @Override
    public void showRetry(boolean show,  String errorMsg) {
        mRetryPageLoadHelper.retryPageLoad();
    }

    public void addItems(List<NewsModel.Result> albumList) {
        if (mItemsList != null) {
            mItemsList.addAll(albumList);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView coverImageView;
        TextView titleTextView;
        TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.iv_avatar_img);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            contentTextView = itemView.findViewById(R.id.textViewDescription);

        }

        protected void clear() {
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            final NewsModel.Result album = mItemsList.get(position);
            if (album == null) return;

//            ImageHelper.loadImageAsThumb(album.getNewsImage(), album.getNewsImage(), R.drawable.img_not_available, coverImageView);
            titleTextView.setText(album.getName());
            contentTextView.setText(album.getDiscription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Position", "pos->" + position + "   size->" + mItemsList.size());
                }
            });
        }
    }


}
