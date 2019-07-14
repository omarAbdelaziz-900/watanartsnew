package com.yalantis.ucrop.callback;

import android.view.View;

/**
 * Created by Burhanuddin Rashid on 18/01/2017.
 */

public interface OnPhotoEditorListener {

    void onEditTextChangeListener(View rootView, String text, int colorCode);

    void onAddViewListener();

    void onRemoveViewListener(int numberOfAddedViews);

    void onStartViewChangeListener();

    void onStopViewChangeListener();
}
