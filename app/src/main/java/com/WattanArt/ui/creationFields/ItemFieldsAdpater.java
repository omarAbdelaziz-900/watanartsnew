package com.WattanArt.ui.creationFields;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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
import com.WattanArt.ui.Coaster.CoasterActivity;
import com.WattanArt.ui.EditDesign.EditDesignActivity;
import com.WattanArt.ui.FlashMemory.FlashMemoryActivity;
import com.WattanArt.ui.FlashMemory.FlashMemorySizeModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFieldsAdpater extends RecyclerView.Adapter<ItemFieldsAdpater.MyViewHolder> {

    //    ArrayList<DataModel> mValues ;
    List<HomeIntroResponseModel.ResultBean.CategoryBean> mValues ;
    protected ItemListener mListener;
    Context mContext;
    List< HomeIntroResponseModel.ResultBean.CategoryBean.ItemsBean> itemsArrayList;
//    HomeIntroResponseModel.Result.CategoryBean item;
    List<FlashMemorySizeModel>flashMemorySizeModelList=new ArrayList<>();

    int itemPosition;
    int prod_Id;
    String priceIn ,priceOut,prod_Name;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_field_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }




    public ItemFieldsAdpater(Context context, List< HomeIntroResponseModel.ResultBean.CategoryBean.ItemsBean> itemsArrayList,List<HomeIntroResponseModel.ResultBean.CategoryBean> mValues, ItemListener itemListener) {

        this.mValues = mValues;
        this.mContext = context;
        this.mListener=itemListener;
        this.itemsArrayList=itemsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final HomeIntroResponseModel.ResultBean.CategoryBean item = mValues.get(position);




        holder.textView.setText(item.getName());
        loadImage(item.getImage(),holder.imageView);


        for (int i=0 ; i<itemsArrayList.size();i++){
            Log.e("itemsArrayList",itemsArrayList.size()+"");
            prod_Id =itemsArrayList.get(i).getProd_ID();
            priceIn =itemsArrayList.get(i).getPrice()+"";
            priceOut =itemsArrayList.get(i).getOutPrice()+"";
            prod_Name =itemsArrayList.get(i).getProd_Name()+"";
        }






        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0) {
                    Intent intent = new Intent(mContext, FlashMemoryActivity.class);
                    intent.putExtra("prod_Id", prod_Id+"");
                    Log.e("prod_Id",prod_Id+"");
                    intent.putExtra("priceIn", priceIn+"");
                    Log.e("priceIn",priceIn+"");
                    intent.putExtra("priceOut", priceOut+"");
                    Log.e("priceOut",priceOut+"");
                    if (flashMemorySizeModelList!=null){
                        flashMemorySizeModelList.clear();
                    }
                    for (int i=0 ; i<mValues.get(0).getItems().size();i++){
                        Log.e("itemsArrayListddd",item.getItems().size()+"");
                        prod_Id =mValues.get(0).getItems().get(i).getProd_ID();
                        priceIn =mValues.get(0).getItems().get(i).getPrice()+"";
                        priceOut =mValues.get(0).getItems().get(i).getOutPrice()+"";
                        prod_Name =mValues.get(0).getItems().get(i).getProd_Name()+"";
                        String prod_Namesss =mValues.get(0).getItems().get(i).getProd_Name()+"";
                        Log.e("prod_Namsse",prod_Namesss);
                        flashMemorySizeModelList.add(new FlashMemorySizeModel(
                                mValues.get(0).getItems().get(i).getProd_ID(),
                                String.valueOf(mValues.get(0).getItems().get(i).getPrice()),
                                String.valueOf(mValues.get(0).getItems().get(i).getOutPrice()),
                                mValues.get(0).getItems().get(i).getProd_Name()));
                    }
                    Bundle args = new Bundle();
                    args.putSerializable("FlashMemoryArrayList",(Serializable)flashMemorySizeModelList);
                    intent.putExtra("BUNDLE",args);
                    mContext.startActivity(intent);
                } else {
                    if (position == 1 ) {
                        Toast.makeText(mContext, "Comming Soon", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(mContext, EditDesignActivity.class);
//                        mContext.startActivity(intent);
                    } else if (position==2){
                        Toast.makeText(mContext, "Comming Soon", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(mContext, CoasterActivity.class);
//                        intent.putExtra("catId", item.getCat_ID());
//                        intent.putExtra("prod_id_circle", item.getItems().get(0).getProd_ID());
//                        intent.putExtra("Coaster_circle", item.getItems().get(0).getProd_image());
//                        intent.putExtra("price_in_circle", item.getItems().get(0).getPrice()+"");
//                        intent.putExtra("price_out_circle", item.getItems().get(0).getOutPrice()+"");
//
//                        Log.e("price_in_circle",item.getItems().get(0).getPrice()+"");
//                        Log.e("price_out_circle",item.getItems().get(0).getOutPrice()+"");
//                        Log.e("price_in_circle1",item.getItems().get(1).getPrice()+"");
//                        Log.e("price_out_circle1",item.getItems().get(1).getOutPrice()+"");
//
//                        intent.putExtra("prod_id_square", item.getItems().get(1).getProd_ID());
//                        intent.putExtra("Coaster_square", item.getItems().get(1).getProd_image());
//                        intent.putExtra("price_in_square", item.getItems().get(1).getPrice()+"");
//                        intent.putExtra("price_out_square", item.getItems().get(1).getOutPrice()+"");
////
//                        mContext.startActivity(intent);
//                        if (mListener != null) {
//                            mListener.onItemClick(item, position);
//                        }
                    }else {
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
        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;



        public MyViewHolder(View v) {
            super(v);

//            v.setOnClickListener(this);
            textView =  v.findViewById(R.id.textView);
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

