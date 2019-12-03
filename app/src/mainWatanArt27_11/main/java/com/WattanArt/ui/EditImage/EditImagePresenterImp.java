package com.WattanArt.ui.EditImage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.WattanArt.R;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Team on 1/18/2018.
 */

public class EditImagePresenterImp<V extends EditImageMvpView> extends BasePresenter<V>
        implements EditImagePresenterMvp<V> {

    @Inject
    public EditImagePresenterImp(AppDataManager dataManager , CompositeDisposable compositeDisposable) {
        super(dataManager , compositeDisposable );
    }


    @Override
    public void showReplaceDialog(Context context) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.replace_layout);
        mDialog.setCancelable(false);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            getMvpView().onReplaceImage();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });
        mDialog.show();
    }

    @Override
    public void showDeleteDialog(Context context) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.delete_popup);
        mDialog.setCancelable(false);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            getMvpView().onDeleteImage();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });
        mDialog.show();
    }
}
