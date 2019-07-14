package com.WattanArt.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.WattanArt.R;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.ui.ContactUs.InfoFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    private Context context;

    private List<OrderDetailsResponseModel.ResultEntity> responseList;

    public OrderDetailsAdapter(Context context, List<OrderDetailsResponseModel.ResultEntity> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_details_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        double totalPrice = 0;
        if (position==responseList.size()-1){
            Log.e("position", position +",,,"+responseList.size() +"");
            holder.line_gray.setVisibility(View.GONE);

        }
        holder.mRatioTextView.setText(" "+String.valueOf(responseList.get(position).getPattern())+" ");
        holder.mquantityTextView.setText(" "+String.valueOf(responseList.get(position).getQuantiy())+" ");
        if (responseList.get(position).getQuantiy() < 3) {
            totalPrice = totalPrice + responseList.get(position).getPiecePrice() * responseList.get(position).getQuantiy();
        } else {
            totalPrice = totalPrice + responseList.get(position).getPiecePrice() * (responseList.get(position).getQuantiy() - 1)
                    + responseList.get(position).getPiecePrice() * 0.5;
        }
        holder.mpriceTextView.setText(String.valueOf(totalPrice) + context.getString(R.string.dolar));

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_not_available);
        options.centerInside();
        options.timeout(60000); //1M
//
//        Glide.with(context)
//                .load(Constants.BASE_URL + Constants.UPLOAD+ responseList.get(position).getMainImage())
//                .apply(options)
//                .into(holder.mdetailsImageView);

        Log.e("LowResoltionImage", "->" + Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getLowResultionImage());
        Glide.with(context)
//                .asBitmap()
                .load(Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getLowResultionImage())
                .apply(options).into(holder.mdetailsImageView);// {
//                .apply(options).into(new BitmapImageViewTarget(holder.mdetailsImageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//               holder.mdetailsImageView.setImageDrawable(circularBitmapDrawable);
//            }});
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView mdetailsImageView,line_gray;
        CustomeTextView mRatioTextView, mquantityTextView, mpriceTextView;

        public MyViewHolder(View view) {
            super(view);

            mdetailsImageView = view.findViewById(R.id.detailsImage_iv);
            line_gray = view.findViewById(R.id.line_gray);
            mRatioTextView = view.findViewById(R.id.ratio_tv);
            mquantityTextView = view.findViewById(R.id.quantity_tv);
            mpriceTextView = view.findViewById(R.id.price_tv);


        }
    }
}

