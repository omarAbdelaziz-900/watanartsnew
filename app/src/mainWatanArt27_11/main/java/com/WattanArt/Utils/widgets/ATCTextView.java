package com.WattanArt.Utils.widgets;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;


public class ATCTextView extends TextView {
    private final String TAG = getClass().getSimpleName();

    public ATCTextView(Context context) {
        super(context);
        init(context);

    }

    public ATCTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ATCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /**
     * Set the text from html source
     * opens any url included in the html source
     *
     * @param textFromHtml html source text
     */
    public void setTextFromHtml(String textFromHtml) {
        //setText(Html.fromHtml(textFromHtml));
        setText(Html.fromHtml(replaceListTag(textFromHtml)));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setText(String text) {
        if (text == null || TextUtils.isEmpty(text))
            text = " ";
        super.setText(text);

    }

    /**
     * Replaces <li> tag with <br> &#8226 And Replaces </li> tag with </br>
     * Refer to this thread for more info http://stackoverflow.com/questions/3429546/how-do-i-add-a-bullet-symbol-in-textview
     *
     * @param htmlText
     * @return
     */
    private String replaceListTag(String htmlText) {
        String textToBeShown = htmlText.contains("<li>")
                ? htmlText.replace("<li>", "<br> &#8226") : htmlText;
        textToBeShown =
                textToBeShown.contains("</li>") ? textToBeShown.replace("</li>", "</br>") : textToBeShown;
        return textToBeShown;
    }
}
