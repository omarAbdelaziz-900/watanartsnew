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
import android.widget.LinearLayout;

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

    private List<OrderDetailsResponseModel.ResultBean> responseList;

    public OrderDetailsAdapter(Context context, List<OrderDetailsResponseModel.ResultBean> responseList) {
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

        if (responseList.get(0).getOrderType()==0){
            holder.kind_txt.setText(context.getString(R.string.ratio));
        }else {
            holder.kind_txt.setText(context.getString(R.string.kind));
        }
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
        Log.e("LowResoltionImage", "->" + Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getLowResultionImage());
        Glide.with(context)
                .load(Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getLowResultionImage())
                .apply(options).into(holder.mdetailsImageView);// {

        if (responseList.get(position).getCat_ID()==9){

            holder.detailsImage_iv2.setVisibility(View.VISIBLE);
            Log.e("LowResoltionImage", "->" + Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getLowResultionImage());
            Glide.with(context)
                    .load(Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getImages().get(0).getLowPrintscreenImg())
                    .apply(options).into(holder.mdetailsImageView);// {

            Glide.with(context)
                    .load(Constants.BASE_URL + Constants.UPLOAD + responseList.get(position).getImages().get(1).getLowPrintscreenImg())
                    .apply(options).into(holder.detailsImage_iv2);// {
        }else {
            holder.detailsImage_iv2.setVisibility(View.GONE);
        }
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


        ImageView mdetailsImageView,line_gray,detailsImage_iv2;
        CustomeTextView mRatioTextView  , mquantityTextView , mpriceTextView ,kind_txt;

        public MyViewHolder(View view) {
            super(view);

            mdetailsImageView = view.findViewById(R.id.detailsImage_iv);
            detailsImage_iv2 = view.findViewById(R.id.detailsImage_iv2);
            line_gray = view.findViewById(R.id.line_gray);
            mRatioTextView = view.findViewById(R.id.ratio_tv);
            mquantityTextView = view.findViewById(R.id.quantity_tv);
            mpriceTextView = view.findViewById(R.id.price_tv);
            kind_txt = view.findViewById(R.id.kind_txt);


        }
    }
}

