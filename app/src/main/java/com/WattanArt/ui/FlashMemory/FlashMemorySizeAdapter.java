package com.WattanArt.ui.FlashMemory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.Utils.config.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class FlashMemorySizeAdapter extends RecyclerView.Adapter<FlashMemorySizeAdapter.MyViewHolder> {

    protected  ItemListenerOfFlashMemorSize itemListener;
    Context mContext;
    int colorOrStyleType;
    int row_index=-1;

    int pos;
    List<FlashMemorySizeModel>flashMemorySizeModelList;

    public FlashMemorySizeAdapter(Context context, List<FlashMemorySizeModel>flashMemorySizeModelList, ItemListenerOfFlashMemorSize itemListener){
        mContext=context;
        this.flashMemorySizeModelList = flashMemorySizeModelList;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.flash_memory_size_items, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position == 0) {
            holder.SizeFlashBottomHolder.setBackgroundResource(R.drawable.background_unselected);
        }
        FlashMemorySizeModel flashMemorySizeModel=flashMemorySizeModelList.get(position);

        holder.txt_size.setText(flashMemorySizeModel.getProd_Name());

            holder.SizeFlashBottomHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemListener!=null){
                        itemListener.onSizeItemsClickFromAdapter(position,flashMemorySizeModel.getPriceIn(),
                                flashMemorySizeModel.getPriceOut(),flashMemorySizeModelList);
                    }
                    row_index=position;
                    notifyDataSetChanged();
                }
            });


        holder.selectItems(position);
    }

    @Override
    public int getItemCount() {
        return flashMemorySizeModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView SizeFlashimagecolor;
        TextView txt_size;
        RelativeLayout SizeFlashBottomHolder;

        public MyViewHolder(View itemView) {
            super(itemView);

            SizeFlashimagecolor =  itemView.findViewById(R.id.SizeFlashimagecolor);
            txt_size =  itemView.findViewById(R.id.txt_size);
            SizeFlashBottomHolder =  itemView.findViewById(R.id.SizeFlashBottomHolder);
        }

        public void selectItems(int position){
            if(row_index!=position){
                SizeFlashBottomHolder.setBackgroundResource(R.drawable.background_unselected);//is not selected
            }
            else
            {
                SizeFlashBottomHolder.setBackgroundResource(R.drawable.background_selected);  // is selected
            }
        }
    }


    public void loadImage(String image, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_not_available);
        options.error(R.drawable.img_not_available);

        String path = Constants.BASE_URL + Constants.UPLOAD +image;
        Log.e("path", path);

        Glide.with(mContext)
                .asBitmap()
                .load(path.trim())
                .apply(options).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setImageBitmap(resource);
                return true;
            }
        }).into(imageView);
    }

    public interface ItemListenerOfFlashMemorSize {
        void onSizeItemsClickFromAdapter(int position ,String priceIn,String priceOut,List<FlashMemorySizeModel> flashMemorySizeModelList);
    }


}
