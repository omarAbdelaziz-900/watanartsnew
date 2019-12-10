package com.WattanArt.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.WattanArt.R;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.ui.Order.OrderDetails.OrderDetailsActivity;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Esraa on 10/5/2017.
 */

public class OrderHistoryListAdapter extends RecyclerView.Adapter<OrderHistoryListAdapter.MyViewHolder> {
    private Context context;
boolean isInEgypt;
    private List<AllOrdersHistoryResponseModel.ResultEntity> resultEntityList;

    public OrderHistoryListAdapter(Context context, List<AllOrdersHistoryResponseModel.ResultEntity> resultEntityList) {
        this.context = context;
        this.resultEntityList = resultEntityList;
        isInEgypt = UtilitiesManager.getUserCountry(context).toLowerCase().equals("eg") ? true : false;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderhistory_list_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (position==resultEntityList.size()-1){
            Log.e("position", position +",,,"+resultEntityList.size() +"");
            holder.line_gray.setVisibility(View.GONE);

        }
        holder.NumOrderTextView.setText(" "+String.valueOf(resultEntityList.get(position).getID()));
        holder.NumOfCanvasTextView.setText(" "+String.valueOf(resultEntityList.get(position).getItemCount()));
        Log.d("orderstate",resultEntityList.get(position).getState()+"");
        holder.statusTextView.setText(" "+resultEntityList.get(position).getStateValue());


        String[] dayMonthYear = resultEntityList.get(position).getCreatedDate().split("T");
        String part1 = dayMonthYear[0].replace("-", "/");
        holder.dateTextView.setText(" "+part1);
        String currency;
        if (isInEgypt)
            currency = context.getString(R.string.le);
        else
            currency = context.getString(R.string.dolar);

        holder.priceTextView.setText(" "+String.valueOf(resultEntityList.get(position).getItemsCost()+
                resultEntityList.get(position).getChargePrice())
                +" "+currency+" ");

        holder.mdetailsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrderDetailsActivity.class);
                i.putExtra(Constants.ORDERID,resultEntityList.get(position).getID());
                i.putExtra(Constants.STATEVALUE,resultEntityList.get(position).getStateValue());
                i.putExtra(Constants.ORDERITEMPRICE,resultEntityList.get(position).getItemsCost());
                i.putExtra(Constants.ORDERChARGE,resultEntityList.get(position).getChargePrice());
                Log.e("khkhkhkhk",resultEntityList.get(position).getChargePrice()+"");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultEntityList.size();
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
        private CustomeTextView NumOrderTextView,NumOfCanvasTextView,statusTextView,dateTextView,priceTextView;
        ImageView mdetailsImageView,line_gray;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
            mdetailsImageView = view.findViewById(R.id.details_imv);
            line_gray = view.findViewById(R.id.line_gray);
            NumOrderTextView = view.findViewById(R.id.no_order_tv);
            NumOfCanvasTextView = view.findViewById(R.id.no_of_canvas_tv);

            statusTextView = view.findViewById(R.id.status_tv);
            dateTextView = view.findViewById(R.id.date_tv);
            priceTextView = view.findViewById(R.id.price_tv);


        }
    }
}
