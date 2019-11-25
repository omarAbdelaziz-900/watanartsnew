package com.WattanArt.ui.ActivityFeed;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;

import java.util.ArrayList;
import android.content.Context;
        import android.graphics.Color;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.WattanArt.R;
        import com.WattanArt.Utils.widgets.CustomeTextViewBold;

        import java.util.ArrayList;

public class FeedItemsOfTypesAdapter extends RecyclerView.Adapter<FeedItemsOfTypesAdapter.MyViewHolder> {

    protected  ItemListenerOfItems itemListener;
    Context mContext;
    private ArrayList<ItemsOfTypesModel> activityFeedTypesModels;


    public FeedItemsOfTypesAdapter(Context context, ArrayList<ItemsOfTypesModel> activityFeedTypesModels,ItemListenerOfItems itemListener){

        mContext=context;
        this.activityFeedTypesModels = activityFeedTypesModels;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_feeds_item_rows, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.item_tv.setText(activityFeedTypesModels.get(position).getName());
        holder.items_img.setImageResource(activityFeedTypesModels.get(position).getImage_drawable());

        Log.e("ssss",activityFeedTypesModels.get(position).getName());
        holder.card_view_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemListener!=null){
                    itemListener.onItemsClickFromAdapter(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityFeedTypesModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CustomeTextViewBold item_tv;
        ImageView items_img;
        CardView card_view_parent;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_tv =  itemView.findViewById(R.id.item_tv);
            items_img =  itemView.findViewById(R.id.items_img);
            card_view_parent =  itemView.findViewById(R.id.card_view_parent);
        }

    }

    public interface ItemListenerOfItems {
        void onItemsClickFromAdapter(int position);
    }
}



