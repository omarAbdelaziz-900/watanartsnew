package com.WattanArt.ui.Category;

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
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;

import java.util.ArrayList;


public class CategoryT_ShirtTypesAdapter extends RecyclerView.Adapter<CategoryT_ShirtTypesAdapter.MyViewHolder> {

    protected  ItemListenerOfItems itemListener;
    Context mContext;
    private ArrayList<CategoryItemModel> categoryItemModels;


    public CategoryT_ShirtTypesAdapter(Context context, ArrayList<CategoryItemModel> categoryItemModels, ItemListenerOfItems itemListener){

        mContext=context;
        this.categoryItemModels = categoryItemModels;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.t_shirt_types_items_rows, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.t_shirt_txt.setText(categoryItemModels.get(position).getName());
        holder.t_shirt_img.setImageResource(categoryItemModels.get(position).getImg());

        Log.e("ssss",categoryItemModels.get(position).getName());
        holder.card_view_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemListener!=null){
                    itemListener.onT_ShirtItemsClickFromAdapter(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView t_shirt_img;
        CustomeTextViewBold t_shirt_txt;
        RelativeLayout card_view_parent;

        public MyViewHolder(View itemView) {
            super(itemView);

            t_shirt_img =  itemView.findViewById(R.id.t_shirt_img);
            t_shirt_txt =  itemView.findViewById(R.id.t_shirt_txt);
            card_view_parent =  itemView.findViewById(R.id.card_view_parent);
        }

    }

    public interface ItemListenerOfItems {
        void onT_ShirtItemsClickFromAdapter(int position);
    }
}
