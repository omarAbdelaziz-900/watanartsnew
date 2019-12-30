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

import com.WattanArt.R;
import com.WattanArt.Utils.config.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ColorStyleFlashAdapter extends RecyclerView.Adapter<ColorStyleFlashAdapter.MyViewHolder> {

    protected  ItemListenerOfItems itemListener;
    Context mContext;
    private List<String> colorItemModels;
    int colorOrStyleType;
    int row_index=-1;

    int pos;
    public ColorStyleFlashAdapter(int colorOrStyleType,Context context, List<String> colorItemModels, ItemListenerOfItems itemListener){

        mContext=context;
        this.colorItemModels = colorItemModels;
        this.colorOrStyleType = colorOrStyleType;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.color_bg_items_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position == 0) {
            holder.parent_relative.setBackgroundResource(R.drawable.transparent_background);
        }

        if (colorOrStyleType==1){
            holder.color_image.setBackgroundColor(Color.parseColor("#"+colorItemModels.get(position)));

            holder.color_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemListener!=null){
                        itemListener.onColorItemsClickFromAdapter(position);
                    }
                    row_index=position;
                    notifyDataSetChanged();
                }
            });
        }else {

            loadImage(colorItemModels.get(position)+"",holder.color_image);
            holder.color_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemListener!=null){
                        itemListener.onStyleItemsClickFromAdapter(position);
                    }
                    row_index=position;
                    notifyDataSetChanged();
                }
            });
        }

        holder.selectItems(position);
    }

    @Override
    public int getItemCount() {
        return colorItemModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView color_image;
        CardView parent_card;
        RelativeLayout parent_relative;

        public MyViewHolder(View itemView) {
            super(itemView);

            color_image =  itemView.findViewById(R.id.color_image);
            parent_card =  itemView.findViewById(R.id.parent_card);
            parent_relative =  itemView.findViewById(R.id.parent_relative);
        }

        public void selectItems(int position){
            if(row_index!=position){
                parent_relative.setBackgroundResource(R.drawable.transparent_background);//is not selected
            }
            else
            {
                parent_relative.setBackgroundResource(R.drawable.type_not_selected);  // is selected
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

    public interface ItemListenerOfItems {
        void onColorItemsClickFromAdapter(int position);
        void onStyleItemsClickFromAdapter(int position);
    }


}
