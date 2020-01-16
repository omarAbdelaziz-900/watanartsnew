package com.WattanArt.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.WattanArt.R;
import com.WattanArt.model.SampleMokupItems;

import java.util.List;

public class SampleMokupItemsAdapter extends RecyclerView.Adapter<SampleMokupItemsAdapter.ViewHolder> {

    private final List<SampleMokupItems> mValues;
   private Context context;

    public SampleMokupItemsAdapter(Context mContext , List<SampleMokupItems> items) {
        context = mContext;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_for_mokup_items_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SampleMokupItems sampleMokupItems = mValues.get(position);
        holder.mokup_img.setImageResource(sampleMokupItems.getMokupImage());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView mokup_img;
        public ViewHolder(View view) {
            super(view);
            mokup_img = view.findViewById(R.id.mokup_img);
        }
    }
}