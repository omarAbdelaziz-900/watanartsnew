package com.WattanArt.ui.EditDesign;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;

import java.util.ArrayList;

public class T_ShirtAdapter extends RecyclerView.Adapter<T_ShirtAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<T_ShirtModel> t_shirtModelArrayList;
    int row_index=0;
    protected ItemListener mListener;

    public T_ShirtAdapter(Context context, ArrayList<T_ShirtModel> t_shirtModelArrayList,ItemListener mListener){

        mContext=context;
        this.t_shirtModelArrayList = t_shirtModelArrayList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.t_shirt_items_rows, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {




        holder.t_shirt_txt.setText(t_shirtModelArrayList.get(position).getName());
        Log.e("ssss",t_shirtModelArrayList.get(position).getName());

        holder.parent_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onSizeTypeClickFromAdapter(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return t_shirtModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CustomeTextViewBold t_shirt_txt;
        RelativeLayout parent_relative;

        public MyViewHolder(View itemView) {
            super(itemView);

            t_shirt_txt = (CustomeTextViewBold) itemView.findViewById(R.id.t_shirt_txt);
            parent_relative =  itemView.findViewById(R.id.parent_relative);
        }
    }


    public interface ItemListener {
        void onSizeTypeClickFromAdapter(int position);
    }
}

