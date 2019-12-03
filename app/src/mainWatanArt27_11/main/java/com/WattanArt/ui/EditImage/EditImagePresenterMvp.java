package com.WattanArt.ui.EditImage;

import android.content.Context;

import com.WattanArt.ui.base.MvpPresenter;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface EditImagePresenterMvp<V extends EditImageMvpView> extends MvpPresenter<V> {
    void showReplaceDialog(Context context);
    void showDeleteDialog(Context context);
}
