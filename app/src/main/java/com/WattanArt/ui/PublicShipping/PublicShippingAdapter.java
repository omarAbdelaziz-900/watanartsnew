package com.WattanArt.ui.PublicShipping;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.R;

import java.util.List;


public class PublicShippingAdapter extends RecyclerView.Adapter<PublicShippingAdapter.MyViewHolder> {

    List<PublicBitmapsModel> mValues ;
    protected plusItemListener plusItemListener;
    protected minusItemListener minusItemListener;
    protected calculatePriceListener calculatePriceListener;
    Context mContext;

    int itemPosition;
    int prod_Id;
    String priceIn ,priceOut;
    boolean isInEgypt=false;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.shipping_item2, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }




    public PublicShippingAdapter(Context context, List<PublicBitmapsModel> mValues,calculatePriceListener calculatePriceListener,plusItemListener plusItemListener,minusItemListener minusItemListener) {

        this.mValues = mValues;
        this.mContext = context;
        this.calculatePriceListener = calculatePriceListener;
        this.plusItemListener = plusItemListener;
        this.minusItemListener = minusItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final PublicBitmapsModel item = mValues.get(position);



        holder.shippingImageItem.setImageBitmap(item.getBitmapFront());

        holder.plusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusImg(position,holder.quantityTv);
                        if (plusItemListener != null) {
                            plusItemListener.onAddingItemClick(item, position);
                        }
            }
        });

        holder.minusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusImg(position,holder.quantityTv);
                if (minusItemListener != null) {
                    minusItemListener.onMinusItemClick(item, position);
                }
            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView shippingImageItem,resolution, minusIV, plusIV, deleteOneImage, replace;
        TextView quantityTv;


        public MyViewHolder(View view) {
            super(view);
            shippingImageItem = view.findViewById(R.id.shippingImageItem);
            resolution = view.findViewById(R.id.resolution);
            minusIV = view.findViewById(R.id.minusIV);
            plusIV = view.findViewById(R.id.plusIV);
            quantityTv = view.findViewById(R.id.quantityTv);
            deleteOneImage = view.findViewById(R.id.deleteOneImage);
            replace = view.findViewById(R.id.replace);
        }


    }

    @Override
    public int getItemCount() {
//        return 20;
        return  mValues ==null ? 0 : mValues.size();
//        return  1;
    }




    public interface plusItemListener {
        void onAddingItemClick(PublicBitmapsModel item,int position);
    }

    public interface minusItemListener {
        void onMinusItemClick(PublicBitmapsModel item,int position);
    }
    public interface calculatePriceListener {
        void onCalculatePriceClick(List<PublicBitmapsModel> mValues,int position);
    }

    public void plusImg(int itemPosition,TextView quantityTv) {
        if (mValues.get(itemPosition).getQuantitty() >= 1) {
            quantityTv.setText(mValues.get(itemPosition).getQuantitty() + 1 + "");
            mValues.get(itemPosition).setQuantitty(mValues.get(itemPosition).getQuantitty() + 1);
            CalculatePrice(mValues,itemPosition);
        } else {
        }
    }
//
    public void minusImg(int itemPosition,TextView quantityTv) {
        if (mValues.get(itemPosition).getQuantitty() > 1) {
            if (mValues.get(itemPosition).getQuantitty() != 1) {
                mValues.get(itemPosition).setQuantitty(mValues.get(itemPosition).getQuantitty() - 1);
                quantityTv.setText(mValues.get(itemPosition).getQuantitty() + "");
                CalculatePrice(mValues,itemPosition);
            }
        }
    }
//
    public void CalculatePrice(List<PublicBitmapsModel> mValues,int position){
        if (calculatePriceListener != null) {
            calculatePriceListener.onCalculatePriceClick(mValues,position);
        }
    }


}

