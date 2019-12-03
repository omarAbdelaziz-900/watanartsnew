package com.WattanArt.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.Adapters.Holders.EmptyViewHolder;
import com.WattanArt.R;
import com.WattanArt.model.Response.ContactUsServicesModel;
import com.WattanArt.ui.base.BaseViewHolder;

import java.util.List;


public class ContactUsServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<ContactUsServicesModel.Result> malbumResponseList;

    public ContactUsServicesAdapter(List<ContactUsServicesModel.Result> albumResponseList) {
        malbumResponseList = albumResponseList;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false) , null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (malbumResponseList != null && malbumResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (malbumResponseList != null && malbumResponseList.size() > 0) {
            return malbumResponseList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<ContactUsServicesModel.Result> albumList) {
        if (malbumResponseList != null){
            malbumResponseList.addAll(albumList);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView coverImageView;

        TextView titleTextView;
        
        TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
            contentTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final ContactUsServicesModel.Result album = malbumResponseList.get(position);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }
}
