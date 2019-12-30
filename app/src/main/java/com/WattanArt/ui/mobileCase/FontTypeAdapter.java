package com.WattanArt.ui.mobileCase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.WattanArt.R;
import java.util.List;

public class FontTypeAdapter extends RecyclerView.Adapter<FontTypeAdapter.MyViewHolder> {

    List<FontTypeModel> mValues ;
    protected ItemListener mListener;
    Context mContext;
    List< FontTypeModel> itemsArrayList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.font_type_rows, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    public FontTypeAdapter(Context context, List< FontTypeModel> itemsArrayList, ItemListener itemListener) {
        this.mContext = context;
        this.mListener=itemListener;
        this.itemsArrayList=itemsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final FontTypeModel item = itemsArrayList.get(position);

        holder.font_txt.setText(item.getFontName());
        holder.image_font_txt.setImageResource(item.getImage());

        holder.card_font_ype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onFontTypeItemClick(item, position);
                }
            }
        });



    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView font_txt;
        public ImageView image_font_txt;
        public RelativeLayout card_font_ype;

        public MyViewHolder(View v) {
            super(v);
            image_font_txt =  v.findViewById(R.id.image_font_txt);
            font_txt =  v.findViewById(R.id.font_txt);
            card_font_ype =  v.findViewById(R.id.card_font_ype);
        }
    }

    @Override
    public int getItemCount() {
//        return 20;
        return  itemsArrayList ==null ? 0 : itemsArrayList.size();
//        return  1;
    }




    public interface ItemListener {
        void onFontTypeItemClick(FontTypeModel item,int position);
    }
}


