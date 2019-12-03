package com.WattanArt.ui.Category;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextView;

import java.util.ArrayList;
import java.util.List;


public class CategoryMobileTypesAdapter extends RecyclerView.Adapter<CategoryMobileTypesAdapter.MyViewHolder> {

    protected  ItemListenerOfItems itemListener;
    Context mContext;
    private List<CategoryMobileRsponseModel.ResultBean.ItemsBean> categoryItemModels;
    int mobileType;

    public CategoryMobileTypesAdapter(int mobileType ,Context context, List<CategoryMobileRsponseModel.ResultBean.ItemsBean> categoryItemModels, ItemListenerOfItems itemListener){

        mContext=context;
        this.categoryItemModels = categoryItemModels;
        this.mobileType = mobileType;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.category_items_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        if (mobileType==1){
            final  CategoryMobileRsponseModel.ResultBean.ItemsBean result=categoryItemModels.get(position);


            holder.item_txt.setText(result.getProd_Name());

            Log.e("categoryItemModelsSize",position+"");
            Log.e("categoryItemModelsList",result.getProd_Name()+"");
            holder.card_view_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemListener!=null){
                        itemListener.onItemsClickFromAdapter(position);
                    }
                }
            });
//        }



    }

    @Override
    public int getItemCount() {
        return categoryItemModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CustomeTextView item_txt;
        CardView card_view_parent;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_txt =  itemView.findViewById(R.id.item_txt);
            card_view_parent =  itemView.findViewById(R.id.card_view_parent);
        }

    }

    public interface ItemListenerOfItems {
        void onItemsClickFromAdapter(int position);
    }
}
