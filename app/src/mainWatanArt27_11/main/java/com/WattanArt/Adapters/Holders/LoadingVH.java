package com.WattanArt.Adapters.Holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.ui.base.BaseViewHolder;

/**
 * Created by Android Team on 1/30/2018.
 */

public class LoadingVH extends BaseViewHolder {
    private ProgressBar mProgressBar;
    private ImageButton mRetryBtn;
    private TextView mErrorTxt;
    private LinearLayout mErrorLayout;


    public LoadingVH(View itemView) {
        super(itemView);

        mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
        mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
        mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

//        mRetryBtn.setOnClickListener(this);
//        mErrorLayout.setOnClickListener(this);
    }

    @Override
    protected void clear() {

    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.loadmore_retry:
//            case R.id.loadmore_errorlayout:
//
//                showRetry(false, null);
//                mCallback.retryPageLoad();
//
//                break;
//        }
//    }
}
