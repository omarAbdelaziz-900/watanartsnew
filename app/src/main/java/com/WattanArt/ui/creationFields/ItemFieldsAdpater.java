package com.WattanArt.ui.creationFields;


import android.content.Context;
        import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.PreferenceHelper;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFieldsAdpater extends RecyclerView.Adapter<ItemFieldsAdpater.MyViewHolder> {

    ArrayList<DataModel> mValues ;
    protected ItemListener mListener;
    Context mContext;

    DataModel item;

    int itemPosition;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_field_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }




    public ItemFieldsAdpater(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        item = mValues.get(position);

        holder.textView.setText(item.text);
        holder.imageView.setImageResource(item.drawable);
        holder.relativeLayout.setBackgroundColor(Color.parseColor(item.color));


        itemPosition=position;


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(item,itemPosition);
                }
            }
        });



    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomeTextViewBold textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;



        public MyViewHolder(View v) {
            super(v);

//            v.setOnClickListener(this);
            textView = (CustomeTextViewBold) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }


    }

    @Override
    public int getItemCount() {
//        return 20;
        return  mValues ==null ? 0 : mValues.size();
    }




    public interface ItemListener {
        void onItemClick(DataModel item,int position);
    }
}

