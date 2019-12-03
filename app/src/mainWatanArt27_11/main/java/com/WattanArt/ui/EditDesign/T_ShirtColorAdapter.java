package com.WattanArt.ui.EditDesign;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import java.util.ArrayList;

public class T_ShirtColorAdapter extends RecyclerView.Adapter<T_ShirtColorAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<T_ShirtModel> t_shirtModelArrayList;
    int row_index=0;
    protected ItemListener mListener;

    public T_ShirtColorAdapter(Context context, ArrayList<T_ShirtModel> t_shirtModelArrayList,ItemListener mListener){

        mContext=context;
        this.t_shirtModelArrayList = t_shirtModelArrayList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.t_shirt_color_items_rows, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.t_shirt_color.setBackgroundColor(t_shirtModelArrayList.get(position).getImg());

        holder.parent_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onColorTypeClickFromAdapter(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return t_shirtModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView t_shirt_color;
        CardView parent_card_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            t_shirt_color =  itemView.findViewById(R.id.t_shirt_color);
            parent_card_view =  itemView.findViewById(R.id.parent_card_view);
        }
    }


    public interface ItemListener {
        void onColorTypeClickFromAdapter(int position);
    }
}

