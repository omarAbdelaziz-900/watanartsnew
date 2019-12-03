package com.WattanArt.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.WattanArt.R;
import com.WattanArt.model.AppModels.ImageModel;
import com.yalantis.ucrop.view.UCropView;

import java.util.List;

/**
 * Created by khaled.badawy on 4/3/2018.
 */

public class ImagesModelAdapter extends RecyclerView.Adapter<ImagesModelAdapter.MyViewHolder> {

    private List<ImageModel> ImageModelList;
    private Context mContext;
    private int selectedIndex = 0;
    OnItemClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        UCropView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail_image);
        }
    }


    public ImagesModelAdapter(Context context, List<ImageModel> ImageModelList, OnItemClickListener onItemClickListener) {
        mContext = context;
        this.ImageModelList = ImageModelList;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thumbnail_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void addAll(List<ImageModel> ImageModelList) {
        this.ImageModelList.addAll(ImageModelList);
        notifyDataSetChanged();
    }

    public void setNewBitmap(Bitmap bitmap, int bitmapPosition) {

        if (bitmapPosition<0){
            return;
        }
        ImageModelList.get(bitmapPosition).setBitmap(bitmap);
//        notifyDataSetChanged();
    }

    public void setList(List<ImageModel> ImageModelList) {
        this.ImageModelList.clear();
        this.ImageModelList.addAll(ImageModelList);
//        notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        RequestOptions options = new RequestOptions();
//        options.centerCrop();

        holder.thumbnail.getCropImageView().setRotateEnabled(false);
        holder.thumbnail.getCropImageView().setScaleEnabled(false);

            holder.thumbnail.getCropImageView().setImageBitmap(ThumbnailUtils.extractThumbnail(ImageModelList.get(position).getBitmap()
                    , 100 , 100 ));
        holder.thumbnail.getCropImageView().setTargetAspectRatio(ImageModelList.get(position).getCurrentRatio());




        holder.thumbnail.getCropImageView().setOnClickListener(view -> {
            selectedIndex = position;
            mOnItemClickListener.onItemClick(position);
        });

        holder.thumbnail.getOverlayView().setOnClickListener(view -> {
            selectedIndex = position;
            mOnItemClickListener.onItemClick(position);
        });

//        if (selectedIndex == position) {
//            holder.thumbnail.setBackground(mContext.getDrawable(R.drawable.background_selected_bordered));
//        } else {
//            holder.thumbnail.setBackground(mContext.getDrawable(R.drawable.background_unselected));
//        }
    }

    @Override
    public int getItemCount() {
        return ImageModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


//    public interface ThumbnailAdapterListener {
//        void onFilterSelected(Filter filter);
//    }
}
