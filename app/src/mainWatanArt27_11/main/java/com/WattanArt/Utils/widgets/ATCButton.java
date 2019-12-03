package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Android Team on 1/31/2018.
 */

public class ATCButton extends Button {
    private final String TAG = getClass().getSimpleName();

    public ATCButton(Context context) {
        super(context);
        init(context);

    }

    public ATCButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ATCButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
