package com.WattanArt.Adapters.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.ui.base.BaseViewHolder;
import com.WattanArt.Utils.Callbacks.EmptyCallback;

/**
 * Created by Android Team on 1/31/2018.
 */

public class EmptyViewHolder extends BaseViewHolder {

    private TextView noDataTextView;
    private ImageView loadAgainImageView;

    EmptyCallback mEmptyCallback;


    public EmptyViewHolder(View itemView, final EmptyCallback emptyCallback) {
        super(itemView);

        noDataTextView = (TextView) itemView.findViewById(R.id.no_data_textview);
        loadAgainImageView = (ImageView) itemView.findViewById(R.id.load_again_imageview);

        this.mEmptyCallback = emptyCallback;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emptyCallback != null)
                    emptyCallback.onalbumEmptyViewRetryClick();
            }
        });

    }


    @Override
    protected void clear() {

    }
}
