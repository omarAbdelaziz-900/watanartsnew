package com.WattanArt.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.yalantis.ucrop.view.UCropView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled.badawy on 4/15/2018.
 */

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.MyViewHolder> {

    //    private List<ImageModel> ShippingActivity.imageModelList;
    private Context mContext;
    DisplayMetrics displayMetrics;
//    float[] ratios = new float[]{1f, 8f / 12f, 12f / 8f, 24f / 8f, 24f / 12f, 36f / 8f};

    float[] ratios = new float[]{
            20f / 20f,
            20f / 30f,
            30f / 20f,
            60f / 20f,
            60f / 30f,
            90f / 20f,

            30f / 30f,
            30f / 40f,
            30f / 50f,

            20f / 20f,
            20f / 20f,
            20f / 20f};

    Pair<Integer, Integer>[] pairDimens = new Pair[]{
            new Pair(600, 600),
            new Pair(600, 800),
            new Pair(800, 600),
            new Pair(1200, 600),
            new Pair(1200, 800),
            new Pair(1400, 600),
            new Pair(800, 800),
            new Pair(800, 1000),
            new Pair(800, 1200),
            new Pair(1000, 1000),
            new Pair(1200, 1200),
            new Pair(2000, 2000)};

    Pair<Integer, Integer>[] pairPieces = new Pair[]{
            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(2, 0),
            new Pair(2, 0),
            new Pair(2, 0),

            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(0, 0),

            new Pair(3, 0),
            new Pair(2, 2),
            new Pair(3, 3)};

    List<RatioDimensions> ratioDimensions = new ArrayList<>();

    getNewPrice getNewPrice;
    getNewpieces getNewpieces;
    onDeleteOnImage onDeleteOnImage;
    onReplaceOnImage onReplaceOnImage;
    clickListner clickListner;

    public ShippingAdapter(Context context, List<ImageModel> ImageModelList
            , getNewPrice getNewPrice, getNewpieces getNewpieces,
                           onDeleteOnImage onDeleteOnImage,
                           onReplaceOnImage onReplaceOnImage,
                           clickListner clickListner) {

        mContext = context;
        this.getNewPrice = getNewPrice;
        this.getNewpieces = getNewpieces;
        this.onDeleteOnImage = onDeleteOnImage;
        this.onReplaceOnImage = onReplaceOnImage;
        this.clickListner = clickListner;
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ratioDimensions.add(new RatioDimensions(ratios[0], 400, 400));
        ratioDimensions.add(new RatioDimensions(ratios[1], 400, 266));
        ratioDimensions.add(new RatioDimensions(ratios[2], 266, 400));
        ratioDimensions.add(new RatioDimensions(ratios[3], 200, 600));
        ratioDimensions.add(new RatioDimensions(ratios[4], 280, 560));
        ratioDimensions.add(new RatioDimensions(ratios[5], 133, 600));
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_item, parent, false);

        return new MyViewHolder(itemView);
    }


    public void setList(List<ImageModel> ImageModelList) {
        ShippingActivity.imageModelList.clear();
        ShippingActivity.imageModelList.addAll(ImageModelList);
//        notifyDataSetChanged();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //work around
//        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP){
        //No implement here
//        }else {
        holder.gestureCropImageView.forceResetCropImageView();
//        }

        holder.gestureCropImageView.getCropImageView().setImageBitmap(null);


        holder.gestureCropImageView.getCropImageView().setScaleEnabled(false);
        holder.gestureCropImageView.getCropImageView().setRotateEnabled(false);
        holder.gestureCropImageView.getCropImageView().setGestureEnabled(false);

        try {
            holder.gestureCropImageView.getCropImageView().setImageUriSecond(
                    ShippingActivity.imageModelList.get(position).getUri(),
                    ShippingActivity.imageModelList.get(position).getUri(),
                    ShippingActivity.imageModelList.get(position).getMainImageWidth(),
                    ShippingActivity.imageModelList.get(position).getMainImageHeight(),
                    (bitmap, factorW, factorH) -> {

                        ShippingActivity.imageModelList.get(position).setFactorWidth(factorW);
                        ShippingActivity.imageModelList.get(position).setFactorHeight(factorH);

                        if (ShippingActivity.imageModelList.get(position).getScreenShotImage() == null) {
                            ShippingActivity.imageModelList.get(position).setBitmap(bitmap);
                            ShippingActivity.imageModelList.get(position).setFilteredBitmap(bitmap);
                            ShippingActivity.imageModelList.get(position).setScreenShotImage(bitmap);
                            holder.gestureCropImageView.getCropImageView().setImageBitmap(ShippingActivity.imageModelList.get(position).getScreenShotImage());
//                            Log.e("Bitmap", "W=  " + bitmap.getWidth() + "   H= " + bitmap.getHeight());
                        } else {
                            holder.gestureCropImageView.getCropImageView().setImageBitmap(ShippingActivity.imageModelList.get(position).getScreenShotImage());
//                            Log.e("Bitmap", "W=  " + ShippingActivity.imageModelList.get(position).getFilteredBitmap().getWidth()
//                                    + "   H= " + ShippingActivity.imageModelList.get(position).getFilteredBitmap().getHeight());
                        }


                        ShippingActivity.imageModelList.get(position).setmExifInfo(holder.gestureCropImageView.getCropImageView().getExifInfo());

                        if (ShippingActivity.imageModelList.get(position).getmCurrentImageCorners() == null)
                            ShippingActivity.imageModelList.get(position).setmCurrentImageCorners
                                    (holder.gestureCropImageView.getCropImageView().getMCurrentImageCorners());

                        ShippingActivity.imageModelList.get(position).setmImageInputPath(holder.gestureCropImageView.getCropImageView().getImageInputPath());
                        ShippingActivity.imageModelList.get(position).setmImageOutputPath(holder.gestureCropImageView.getCropImageView().getImageOutputPath());

                        if (ShippingActivity.imageModelList.get(position).getCurrentRatio() == 0f) {
                            if (ShippingActivity.imageModelList.get(position).getMainImageHeight()
                                    == ShippingActivity.imageModelList.get(position).getMainImageWidth()) {

                                ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 1f);
                                ShippingActivity.imageModelList.get(position).setCurrentRatio(1f);

                            } else if (ShippingActivity.imageModelList.get(position).getMainImageHeight() >
                                    ShippingActivity.imageModelList.get(position).getMainImageWidth()) {

                                if (ShippingActivity.imageModelList.get(position).getMainImageHeight() >= 1000 &&
                                        ShippingActivity.imageModelList.get(position).getMainImageWidth() >= 1000 &&
                                        (((float) ShippingActivity.imageModelList.get(position).getMainImageHeight()) / 1.5f) >= 1000) {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 8f / 12f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(8f / 12);
                                } else {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 1f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(1f);
                                }
                            } else if (ShippingActivity.imageModelList.get(position).getMainImageHeight()
                                    < ShippingActivity.imageModelList.get(position).getMainImageWidth()) {
                                if (ShippingActivity.imageModelList.get(position).getMainImageHeight() >= 1000 &&
                                        ShippingActivity.imageModelList.get(position).getMainImageWidth() >= 1000 &&
                                        (((float) ShippingActivity.imageModelList.get(position).getMainImageWidth()) / 1.5f) >= 1000
                                ) {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 12f / 8f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(12f / 8f);
                                } else {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 1f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(1f);
                                }
                            }
                            holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(0);
                            ShippingActivity.imageModelList.get(position).setSegmented(false);
                            holder.gestureCropImageView.getOverlayView().drawCropGrid(new Canvas());
                        } else {
                            holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(pairPieces[(int) ShippingActivity.imageModelList.get(position).getCurrentRatioIndex()].first);
                            holder.gestureCropImageView.getOverlayView().setCropGridRowCount(pairPieces[(int) ShippingActivity.imageModelList.get(position).getCurrentRatioIndex()].second);
                            ShippingActivity.imageModelList.get(position).setSegmented(ShippingActivity.imageModelList.get(position).isSegmented());

//                            if (ShippingActivity.imageModelList.get(position).getCurrentRatio() == ratios[0]
//                                    || ShippingActivity.imageModelList.get(position).getCurrentRatio() == ratios[1] ||
//                                    ShippingActivity.imageModelList.get(position).getCurrentRatio() == ratios[2]) {
//                                holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(0);
//                                ShippingActivity.imageModelList.get(position).setSegmented(false);
//                            } else {
//                                holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(2);
//                                ShippingActivity.imageModelList.get(position).setSegmented(true);
//                            }
                            holder.gestureCropImageView.getOverlayView().drawCropGrid(new Canvas());
                            Log.e("dad", "" + ShippingActivity.imageModelList.get(position).getCurrentRatio());
                            ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView,
                                    ShippingActivity.imageModelList.get(position).getCurrentRatio());
                        }

                        if (ShippingActivity.imageModelList.get(position).getmCropRect() == null) {
                            ShippingActivity.imageModelList.get(position).setmCropRect
                                    (holder.gestureCropImageView.getCropImageView().getMCropRect());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getQuantity() + "");

//        if (ShippingActivity.imageModelList.get(position).isSegmented()) {
//            holder.gestureCropImageView.getOverlayView().setShowCropGrid(true);
//            holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(2);
//        } else {
//            holder.gestureCropImageView.getOverlayView().setShowCropGrid(false);
//            holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(0);
//        }
//        holder.gestureCropImageView.getOverlayView().drawCropGrid(new Canvas());

        getPiecesNumber();
        CalculatePrice();

        holder.gestureCropImageView.getOverlayView().setOnClickListener(v -> {
            clickListner.itemClicked(position);
        });

        holder.deleteOneImage.setOnClickListener(v -> {
            onDeleteOnImage.onOneImageDelted(position);
        });

        holder.resolution.setOnClickListener(v -> {
            Toast.makeText(mContext, mContext.getString(R.string.img_resoluyion), Toast.LENGTH_SHORT).show();
        });

        holder.plusIV.setOnClickListener(view -> {

            int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
            holder.quantityTv.setText(++quantity + "");
            ShippingActivity.imageModelList.get(position).setQuantity(quantity);

            getPiecesNumber();
            CalculatePrice();
        });

        holder.minusIV.setOnClickListener(view -> {

            int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
            if (quantity > 1) {
                holder.quantityTv.setText(--quantity + "");
                ShippingActivity.imageModelList.get(position).setQuantity(quantity);
                getPiecesNumber();
                CalculatePrice();
            }
        });

        holder.replace.setOnClickListener(view -> {
            onReplaceOnImage.onOneImageReplaced(position);
        });
    }


    public interface clickListner {
        void itemClicked(int position);
    }

    public int getPiecesNumber() {
        int pieces = 0;


        for (int i = 0; i < ShippingActivity.imageModelList.size(); i++) {
            Pair<Integer, Integer> piece = pairPieces[(int) ShippingActivity.imageModelList.get(i).getCurrentRatioIndex()];
            pieces += (piece.first+1) * (piece.second+1) *  ShippingActivity.imageModelList.get(i).getQuantity();
        }
//        for (ImageModel imageModel : ShippingActivity.imageModelList) {

//            if (imageModel.getCurrentRatio() == 0) {

//                pieces += 1 * imageModel.getQuantity();
//
//            } else {
//
//                if (imageModel.getCurrentRatio() == ratios[0] || imageModel.getCurrentRatio() == ratios[1] || imageModel.getCurrentRatio() == ratios[2]) {
//                    pieces += 1 * imageModel.getQuantity();
//                } else {
//                    pieces += 3 * imageModel.getQuantity();
//                }
//
//            }
//        }

        Log.e("Pieces", "" + pieces);
        getNewpieces.returnPrice(pieces);
        return pieces;
    }

//    private double getPatternPrice(float ratio, List<SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity> pattenList, int quantity) {
//
//        double price = 0d;
//        this.pattenList = pattenList;
//        if (ratio == ratios[0]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x8")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        } else if (ratio == ratios[1]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x12")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        } else if (ratio == ratios[2]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x8")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        } else if (ratio == ratios[3]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x24")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        } else if (ratio == ratios[4]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x24")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        } else if (ratio == ratios[5]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
//                    price = patternTypeEntity.getPrice() * quantity;
//                    break;
//                }
//            }
//        }
//
//        return price;
//    }

    public double CalculatePrice() {
        double price = 0d;
//        if (pattenList != null) {
//            for (ImageModel imageModel : ShippingActivity.imageModelList) {
//                double retur = getPatternPrice(imageModel.getCurrentRatio(), pattenList, imageModel.getQuantity());
//                price += retur;
//            }
//        }

        if (getNewPrice != null) {
            getNewPrice.returnPrice(price);
        }
        return price;
    }

    public interface getNewPrice {
        void returnPrice(double price);
    }

    public interface getNewpieces {
        void returnPrice(int piece);
    }

    public interface onDeleteOnImage {
        void onOneImageDelted(int index);
    }

    public interface onReplaceOnImage {
        void onOneImageReplaced(int index);
    }

    private void setImageDimensions(UCropView imageViewUpdated, float mTargetAspectRatio) {
        Log.e("AspectRatio", "->" + mTargetAspectRatio);
        int desiredHeight = 350;


        int width = 0;
        int height = 0;

        for (RatioDimensions ratioDimensions : ratioDimensions) {
            if (ratioDimensions.ratioValue == mTargetAspectRatio) {
                height = ratioDimensions.getHeight();
                width = ratioDimensions.getWidth();
            }
        }
/*
        int widthMode = View.MeasureSpec.getMode(imageViewUpdated.getWidth());
        int widthSize = View.MeasureSpec.getSize(imageViewUpdated.getWidth());
        int heightMode = View.MeasureSpec.getMode(imageViewUpdated.getHeight());
        int heightSize = View.MeasureSpec.getSize(imageViewUpdated.getHeight());
        int heightWindow = displayMetrics.heightPixels;
        int widthWindow = displayMetrics.widthPixels;

        Log.e(ShippingAdapter.class.getSimpleName(), "MeasureSpec:: widthWindow:" + String.valueOf(widthWindow) + ", heightWindow:" + String.valueOf(heightWindow));

        if (heightSize > (heightWindow / 6f))
            heightSize = (int) (heightWindow / 5.5f);

        //Measure Height
        if (heightMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            width = Math.min((int) (heightSize * mTargetAspectRatio), widthSize);
            height = Math.min((int) (width / mTargetAspectRatio), heightSize);
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min((int) (heightSize * mTargetAspectRatio), widthSize);
            height = Math.min((int) (width / mTargetAspectRatio), heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //Measure Width
        if (widthMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min((int) (height * mTargetAspectRatio), widthSize);
            if (width == widthSize) {
                height = Math.min((int) (width / mTargetAspectRatio), heightSize);
            } else {
                height = heightSize;
            }
        } else {
            //Be whatever you want
            width = (int) (height * mTargetAspectRatio);
        }
//
*/
        Log.e(ShippingAdapter.class.getSimpleName(), "MeasureSpec:: width:" + String.valueOf(width) + ", height:" + String.valueOf(height));
        //MUST CALL THIS
        imageViewUpdated.getLayoutParams().height = height;
        imageViewUpdated.getLayoutParams().width = width;
        imageViewUpdated.requestLayout();

        imageViewUpdated.getCropImageView().setTargetAspectRatio(mTargetAspectRatio);


    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return ShippingActivity.imageModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        UCropView gestureCropImageView;
        ImageView resolution, minusIV, plusIV, deleteOneImage, replace;
        TextView quantityTv;


        public MyViewHolder(View view) {
            super(view);
            gestureCropImageView = view.findViewById(R.id.shippingImageItem);
            resolution = view.findViewById(R.id.resolution);
            minusIV = view.findViewById(R.id.minusIV);
            plusIV = view.findViewById(R.id.plusIV);
            quantityTv = view.findViewById(R.id.quantityTv);
            deleteOneImage = view.findViewById(R.id.deleteOneImage);
            replace = view.findViewById(R.id.replace);
        }
    }

    private class RatioDimensions {
        private int height;
        private int width;
        private float ratioValue;

        public RatioDimensions(float value, int height, int width) {
            this.height = height;
            this.width = width;
            this.ratioValue = value;
        }

        public int getHeight() {
            return height;
        }

        public float getRatioValue() {
            return ratioValue;
        }

        public int getWidth() {
            return width;
        }
    }

}