package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;


public class ATCEditText extends EditText {
    private final String TAG = getClass().getSimpleName();

    public ATCEditText(Context context) {
        super(context);
        init(context);

    }

    public ATCEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ATCEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
