package com.WattanArt.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.ButterKnife;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class CanvasPrintAdapter extends RecyclerView.Adapter<CanvasPrintAdapter.MyViewHolder> {
    private Context context;
    private CanvasPrintResponseModel responseModel;


    public CanvasPrintAdapter(Context context, CanvasPrintResponseModel responseModel) {
        this.context = context;
//        this.data = data;
        this.responseModel = responseModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.canvas_print_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mCanvasTitle.setText(responseModel.getResult().get(position).getIntroTitle());
        holder.mCanvasText.setText(responseModel.getResult().get(position).getIntroText());
//        getHtmlText(responseModel.getResult().get(position).getIntroText(),holder.mCanvasText);
//        holder.mCanvasText.setText(Html.fromHtml(responseModel.getResult().get(position).getIntroText()));
        if (responseModel.getResult().get(position).getImageList() != null &&
                responseModel.getResult().get(position).getImageList().size() != 0) {

            if (responseModel.getResult().get(position).getImageList().size() == 1) {
                loadImage(position,0,holder.mOneTablueImageView);


            } else if (responseModel.getResult().get(position).getImageList().size() == 2) {
                holder.mOneTablueImageView.setVisibility(View.GONE);
                holder.mTwoTablue_linear.setVisibility(View.VISIBLE);
                loadImage(position,0,holder.mFirstTablueInTwoTablueLinear);
                loadImage(position,1,holder.mSecondTablueInTwoTablueLinear);


            } else if (responseModel.getResult().get(position).getImageList().size() == 3) {
                holder.mOneTablueImageView.setVisibility(View.GONE);
                holder.mThreeTablueLinear.setVisibility(View.VISIBLE);
                loadImage(position,0,holder.mFirstTablueIvInThirdTablueLinear);
                loadImage(position,1,holder.mSecondTablueIvInThirdTablueLinear);
                loadImage(position,2,holder.mThirdTablueIvInThirdTablueLinear);

            }


        }

    }

    private void loadImage(int position, int indexOfList, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_not_available);
        Glide.with(context)
                .asBitmap()
                .load(Constants.BASE_URL + Constants.UPLOAD + responseModel.getResult().get(position).getImageList().get(indexOfList))
                .apply(options).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                imageView.setImageDrawable(circularBitmapDrawable);
            }});
    }

    @Override
    public int getItemCount() {
        return responseModel.getResult().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mTwoTablue_linear, mThreeTablueLinear;
        ImageView mOneTablueImageView, mFirstTablueInTwoTablueLinear, mSecondTablueInTwoTablueLinear,
                mFirstTablueIvInThirdTablueLinear, mSecondTablueIvInThirdTablueLinear, mThirdTablueIvInThirdTablueLinear;
        CustomeTextViewBold mCanvasTitle;
        CustomeTextView mCanvasText;

        public MyViewHolder(View view) {
            super(view);
            mOneTablueImageView = view.findViewById(R.id.oneTablue_iv);

            mFirstTablueInTwoTablueLinear = view.findViewById(R.id.firstTablue_iv_in_twoTablue_linear);
            mSecondTablueInTwoTablueLinear = view.findViewById(R.id.secondTablue_iv_in_twoTablue_linear);

            mFirstTablueIvInThirdTablueLinear = view.findViewById(R.id.firstTablue_iv_in_thirdTablue_linear);
            mSecondTablueIvInThirdTablueLinear = view.findViewById(R.id.secondTablue_iv_in_thirdTablue_linear);
            mThirdTablueIvInThirdTablueLinear = view.findViewById(R.id.thirdTablue_iv_in_thirdTablue_linear);

            mTwoTablue_linear = view.findViewById(R.id.twoTablue_linear);
            mThreeTablueLinear = view.findViewById(R.id.threeTablue_linear);

            mCanvasText = view.findViewById(R.id.canvasText);
            mCanvasTitle = view.findViewById(R.id.canvasTitle);


        }
    }

    public static void getHtmlText(String text, TextView textView) {
        String textStr = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, FROM_HTML_MODE_LEGACY));
//            textView.setText(Html.fromHtml(text));
            Log.e("firstCondition","firstCondition");
//            textStr = String.valueOf(sp);
        } else {
//            Spanned sp = Html.fromHtml(text);
            Log.e("secondCondition","secondCondition");
            textView.setText(Html.fromHtml(text));
//            textStr = String.valueOf(sp);

        }
//        textView.setText(textStr);
    }

}
