package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Android Team on 1/31/2018.
 */

public class ATCImageView extends ImageView {
    public ATCImageView(Context context) {
        super(context);
        init(context);
    }

    public ATCImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ATCImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * Changes The Font To Custom Font if required
     *
     * @param context
     */
    private void init(Context context) {
    }
}
