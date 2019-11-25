package com.WattanArt.ui.ActivityFeed;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;

import java.util.ArrayList;

public class FeedTypeAdapter extends RecyclerView.Adapter<FeedTypeAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<ActivityFeedTypesModel> activityFeedTypesModels;
    int row_index=0;
    protected ItemListener mListener;

    public FeedTypeAdapter(Context context, ArrayList<ActivityFeedTypesModel> activityFeedTypesModels,ItemListener mListener){

        mContext=context;
        this.activityFeedTypesModels = activityFeedTypesModels;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.feeds_all_types, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position==0){
            holder.parent_relative.setBackgroundResource(R.drawable.types_is_selected);
            holder.feed_type_txt.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.parent_relative.setBackgroundResource(R.drawable.type_not_selected);
            holder.feed_type_txt.setTextColor(Color.parseColor("#f5a43b"));
        }


        holder.feed_type_txt.setText(activityFeedTypesModels.get(position).getName());
        Log.e("ssss",activityFeedTypesModels.get(position).getName());

        holder.parent_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onTypeClickFromAdapter(position);
                }

                row_index=position;
                notifyDataSetChanged();
            }
        });

        holder.selectItems(position);
    }

    @Override
    public int getItemCount() {
        return activityFeedTypesModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CustomeTextViewBold feed_type_txt;
        RelativeLayout parent_relative;

        public MyViewHolder(View itemView) {
            super(itemView);

            feed_type_txt = (CustomeTextViewBold) itemView.findViewById(R.id.feed_type_txt);
            parent_relative =  itemView.findViewById(R.id.parent_relative);
        }

        public void selectItems(int position){
            if(row_index!=position){
                parent_relative.setBackgroundResource(R.drawable.type_not_selected);
                feed_type_txt.setTextColor(Color.parseColor("#f5a43b"));
            }
            else
            {
                parent_relative.setBackgroundResource(R.drawable.types_is_selected);
                feed_type_txt.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
    }


    public interface ItemListener {
        void onTypeClickFromAdapter(int position);
    }
}



