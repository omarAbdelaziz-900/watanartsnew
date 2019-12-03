package com.WattanArt.ui.creationFields;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.PreferenceHelper;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.ui.Category.CategoryActivity;
import com.WattanArt.ui.FlashMemory.FlashMemoryActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFieldsAdpater extends RecyclerView.Adapter<ItemFieldsAdpater.MyViewHolder> {

    //    ArrayList<DataModel> mValues ;
    List<HomeIntroResponseModel.ResultBean.CategoryBean> mValues ;
    protected ItemListener mListener;
    Context mContext;

//    HomeIntroResponseModel.Result.CategoryBean item;

    int itemPosition;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_field_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }




    public ItemFieldsAdpater(Context context, List<HomeIntroResponseModel.ResultBean.CategoryBean> mValues, ItemListener itemListener) {

        this.mValues = mValues;
        this.mContext = context;
        this.mListener=itemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final HomeIntroResponseModel.ResultBean.CategoryBean item = mValues.get(position);


        holder.textView.setText(item.getName());
        loadImage(item.getImage(),holder.imageView);

//        holder.imageView.setImageResource(item.drawable);
//        holder.relativeLayout.setBackgroundColor(Color.parseColor(item.color));


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0) {
                    Intent intent = new Intent(mContext, FlashMemoryActivity.class);
//                    intent.putExtra("catId", item.getCat_ID());
                    mContext.startActivity(intent);
                } else {
                    if (position == 1 || position == 2) {
                        Toast.makeText(mContext, "Comming Soon", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, CategoryActivity.class);
                        intent.putExtra("catId", item.getCat_ID());
                        mContext.startActivity(intent);
                        if (mListener != null) {
                            mListener.onItemClick(item, position);
                        }
                    }
                }
            }
        });



    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomeTextViewBold textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;



        public MyViewHolder(View v) {
            super(v);

//            v.setOnClickListener(this);
            textView = (CustomeTextViewBold) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }


    }

    @Override
    public int getItemCount() {
//        return 20;
        return  mValues ==null ? 0 : mValues.size();
//        return  1;
    }




    public interface ItemListener {
        void onItemClick(HomeIntroResponseModel.ResultBean.CategoryBean item,int position);
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
}

