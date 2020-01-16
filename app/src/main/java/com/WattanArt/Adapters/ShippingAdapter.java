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
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.Shipping.InEgyptHelper;
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

    public static int patternId=0;

    List<Integer> patternIds=new ArrayList<>();

    float[] ratios = new float[]{
            20f / 20f,
            20f / 30f,
            30f / 20f,
            30f / 30f,
            30f / 40f,
            30f / 50f,
            60f / 20f,
            60f / 30f,
            90f / 20f,
            40f / 40f,
            60f / 60f,
            80f / 80f};



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
            new Pair(0, 0),//20*20
            new Pair(0, 0),//30*20
            new Pair(0, 0),//20*30
            new Pair(0, 0),//30*30
            new Pair(0, 0),//40*30
            new Pair(0, 0),//50*30
            new Pair(2, 0),//20*60
            new Pair(2, 0),//30*60
            new Pair(2, 0),//20*90
            new Pair(1, 1),//40*40
            new Pair(2, 2),//60*60
            new Pair(3, 3)//80*80
    };

    List<RatioDimensions> ratioDimensions = new ArrayList<>();

    getNewPrice getNewPrice;
    getNewpieces getNewpieces;
    onDeleteOnImage onDeleteOnImage;
    onReplaceOnImage onReplaceOnImage;
    clickListner clickListner;
    List<SelectCountryCitiyListsResponseModel.Result.PatternTypeBean> pattenList;
    boolean isInEgypt = false;

    public ShippingAdapter(boolean isInEgypt ,List<SelectCountryCitiyListsResponseModel.Result.PatternTypeBean> pattenList){
        this.isInEgypt=isInEgypt;
        this.pattenList = pattenList;
    }
    public ShippingAdapter(boolean isInEgypt , List<SelectCountryCitiyListsResponseModel.Result.PatternTypeBean> pattenList,int patternId ,Context context, List<ImageModel> ImageModelList
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
        this.patternId = patternId;
        this.pattenList = pattenList;
        this.isInEgypt = isInEgypt;
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        ratioDimensions.add(new RatioDimensions(ratios[0], 400, 400));//20*20
//        ratioDimensions.add(new RatioDimensions(ratios[1], 400, 266));//30*20
//        ratioDimensions.add(new RatioDimensions(ratios[2], 266, 400));//20*30
//        ratioDimensions.add(new RatioDimensions(ratios[3], 200, 600));//20*60
//        ratioDimensions.add(new RatioDimensions(ratios[4], 280, 560));//30*60
//        ratioDimensions.add(new RatioDimensions(ratios[5], 133, 600));//20*90
//
//        ratioDimensions.add(new RatioDimensions(ratios[6], 400, 400));//30*30
//
//        ratioDimensions.add(new RatioDimensions(ratios[7], 300, 400));//30*40
//        ratioDimensions.add(new RatioDimensions(ratios[8], 300, 450));//30*50
//
//        ratioDimensions.add(new RatioDimensions(ratios[9], 400, 400));//40*40
//        ratioDimensions.add(new RatioDimensions(ratios[10], 400, 400));//60*60
//        ratioDimensions.add(new RatioDimensions(ratios[11], 400, 400));//80*80


        ratioDimensions.add(new RatioDimensions(ratios[0], 400, 400));//20*20
        ratioDimensions.add(new RatioDimensions(ratios[1], 400, 266));//30*20
        ratioDimensions.add(new RatioDimensions(ratios[2], 266, 400));//20*30
        ratioDimensions.add(new RatioDimensions(ratios[3], 400, 400));//30*30

        ratioDimensions.add(new RatioDimensions(ratios[4], 400, 300));//40*30

        ratioDimensions.add(new RatioDimensions(ratios[5], 450, 300));//50*30
        ratioDimensions.add(new RatioDimensions(ratios[6], 200, 600));//20*60
        ratioDimensions.add(new RatioDimensions(ratios[7], 280, 560));//30*60
        ratioDimensions.add(new RatioDimensions(ratios[8], 133, 600));//20*90
        ratioDimensions.add(new RatioDimensions(ratios[9], 400, 400));//40*40
        ratioDimensions.add(new RatioDimensions(ratios[10], 400, 400));//60*60
        ratioDimensions.add(new RatioDimensions(ratios[11], 400, 400));//80*80

        // ratioDimensions.add(new RatioDimensions(ratios[0], 400, 400));//20*20
//        ratioDimensions.add(new RatioDimensions(ratios[1], 400, 266));//30*20
//        ratioDimensions.add(new RatioDimensions(ratios[2], 266, 400));//20*30
//        ratioDimensions.add(new RatioDimensions(ratios[6], 400, 400));//30*30
//
//        ratioDimensions.add(new RatioDimensions(ratios[7], 400, 300));//40*30
//
//        ratioDimensions.add(new RatioDimensions(ratios[8], 450, 300));//50*30
//        ratioDimensions.add(new RatioDimensions(ratios[3], 200, 600));//20*60
//        ratioDimensions.add(new RatioDimensions(ratios[4], 280, 560));//30*60
//        ratioDimensions.add(new RatioDimensions(ratios[5], 133, 600));//20*90
//        ratioDimensions.add(new RatioDimensions(ratios[9], 400, 400));//40*40
//        ratioDimensions.add(new RatioDimensions(ratios[10], 400, 400));//60*60
//        ratioDimensions.add(new RatioDimensions(ratios[11], 400, 400));//80*80
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

        Log.e("hhhhh",pattenList.size()+"");
        for (int i=0;i<ShippingActivity.imageModelList.size();i++){
            Log.e("patternIdFromAdpterss",ShippingActivity.imageModelList.get(i).getTypePatternId()+"");
            if (pattenList.size()!=0) {
                getprice(i, ShippingActivity.imageModelList.get(i).getTypePatternId());
                Log.e("getPattrenInPrice", ShippingActivity.imageModelList.get(i).getPattrenInPrice() + "");
                Log.e("getPattrenOutPrice", ShippingActivity.imageModelList.get(i).getPattrenOutPrice() + "");
            }
        }
       Log.e("imageModelListimage",ShippingActivity.imageModelList.size()+"");


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

                                if (ShippingActivity.imageModelList.get(position).getMainImageHeight() >= 600 &&
                                        ShippingActivity.imageModelList.get(position).getMainImageWidth() >= 600 &&
                                        (((float) ShippingActivity.imageModelList.get(position).getMainImageHeight()) / 1.5f) >= 600) {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 8f / 12f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(8f / 12);

                                } else {
                                    ShippingAdapter.this.setImageDimensions(holder.gestureCropImageView, 1f);
                                    ShippingActivity.imageModelList.get(position).setCurrentRatio(1f);
                                }

                            } else if (ShippingActivity.imageModelList.get(position).getMainImageHeight()
                                    < ShippingActivity.imageModelList.get(position).getMainImageWidth()) {
                                if (ShippingActivity.imageModelList.get(position).getMainImageHeight() >= 600 &&
                                        ShippingActivity.imageModelList.get(position).getMainImageWidth() >= 600 &&
                                        (((float) ShippingActivity.imageModelList.get(position).getMainImageWidth()) / 1.5f) >= 600
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

//                            sfesfs
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

        holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getPatternAmount() + "");

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

            omar();
            if (InEgyptHelper.getInstance().isInEgypt()) {
                isInEgypt = true;
            }else {
                isInEgypt = false;
            }

            if (ShippingActivity.imageModelList.get(position).getPatternAmount()>=1){
                holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getPatternAmount()+1 + "");
                ShippingActivity.imageModelList.get(position).setPatternAmount(ShippingActivity.imageModelList.get(position).getPatternAmount()+1);
                CalculatePrice();
            }else {
//                holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getPatternAmount() + "");
//                ShippingActivity.imageModelList.get(position).setPatternAmount(1);
            }


//            int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
//            holder.quantityTv.setText(++quantity + "");
//            ShippingActivity.imageModelList.get(position).setQuantity(quantity);
////            ShippingActivity.imageModelList.get(position).setTypePatternId();
//            getPiecesNumber();
//            CalculatePrice();

        });

        holder.minusIV.setOnClickListener(view -> {
            omar();
            if (InEgyptHelper.getInstance().isInEgypt()) {
                isInEgypt = true;
            }else {
                isInEgypt = false;
            }
            int quantity;
            if (ShippingActivity.imageModelList.get(position).getPatternAmount()>1) {
                if (ShippingActivity.imageModelList.get(position).getPatternAmount() != 1){
                ShippingActivity.imageModelList.get(position).setPatternAmount(ShippingActivity.imageModelList.get(position).getPatternAmount() - 1);
                holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getPatternAmount() + "");
                CalculatePrice();
            }else {

                }
            }else {
//                holder.quantityTv.setText(ShippingActivity.imageModelList.get(position).getPatternAmount() + "");
//                ShippingActivity.imageModelList.get(position).setPatternAmount(1);
            }


//            int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
//            if (quantity > 1) {
//                holder.quantityTv.setText(--quantity + "");
//                ShippingActivity.imageModelList.get(position).setQuantity(quantity);
//                getPiecesNumber();
//                CalculatePrice();
//            }
        });

        holder.replace.setOnClickListener(view -> {
            onReplaceOnImage.onOneImageReplaced(position);
        });

    }


    public interface clickListner {
        void itemClicked(int position);
    }

//    public int getPiecesNumber() {
//        int pieces = 0;
//
//
//        for (int i = 0; i < ShippingActivity.imageModelList.size(); i++) {
//            Pair<Integer, Integer> piece = pairPieces[(int) ShippingActivity.imageModelList.get(i).getCurrentRatioIndex()];
////            pieces += (piece.first+1) * (piece.second+1) *  ShippingActivity.imageModelList.get(i).getQuantity();
//            pieces += ShippingActivity.imageModelList.get(i).getQuantity();
//        }
//
//        Log.e("PiecesFromAdapter", "" + pieces);
//        getNewpieces.returnPrice(pieces);
//        return pieces;
//    }

    public int getPiecesNumber() {
        int pieces = 0;
        for (int i = 0; i < ShippingActivity.imageModelList.size(); i++) {
//            Pair<Integer, Integer> piece = pairPieces[(int) ShippingActivity.imageModelList.get(i).getCurrentRatioIndex()];
//            pieces += (piece.first+1) * (piece.second+1) *  ShippingActivity.imageModelList.get(i).getQuantity();
            pieces += ShippingActivity.imageModelList.get(i).getPatternAmount();
        }

        Log.e("PiecesFromAdapter", "" + pieces);
        getNewpieces.returnPrice(pieces);
        return pieces;
    }

    public int getPatternId() {
        int id = patternId;
        return id;
    }



    public void CalculatePrice() {
//        double price = 0d;

        if (getNewPrice != null) {
            if (isInEgypt) {
                getNewPrice.returnPrice(calculateInPrice(),getPiecesNumber());
            }else {
                getNewPrice.returnPrice(calculateOutPrice(),getPiecesNumber());
            }
        }
//        return price;
    }

    public interface getNewPrice {
        void returnPrice(double price,int piece);
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


    public double getprice(int i , int patternId ){
        double price = 0;
        if (patternId==1){//10 >>20*20
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(0).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(0).getOutPrice());
                Log.e("kjjjjj",pattenList.get(0).getOutPrice()+"");
//            }
        }else if (patternId==5){//50 >>30*20
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(4).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(4).getOutPrice());
//            }
        }else if (patternId==2){//20 >>20*30
            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(1).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(1).getOutPrice());
            }


        }else if (patternId==3){//30 >>20*60
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(2).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(2).getOutPrice());
//            }
        }else if (patternId==6){//60 >>30*60
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(5).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(5).getOutPrice());
//            }
        }else if (patternId==4){//40 >>20*90
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(3).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(3).getOutPrice());
//            }
        }else if (patternId==7){//70 >>30*30
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(6).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(6).getOutPrice());
//            }
        }else if (patternId==8){//80 >>40*30
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(7).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(7).getOutPrice());
//            }
        }else if (patternId==9){//9 >>50*30
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(8).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(8).getOutPrice());
//            }
        }else if (patternId==10){//100 >>40*40
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(9).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(9).getOutPrice());
//            }
        }else if (patternId==11){//110 >>60*60
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(10).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(10).getOutPrice());
//            }
        }else if (patternId==12){//120 >>80*80
//            if (isInEgypt) {
                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(11).getPrice());
                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(11).getOutPrice());
//            }
        }
        Log.e("ppppp",patternId+"");
        return price;
    }


//    public double getprice(int i , int patternId ){
//        double price = 0;
//        if (patternId==1){//10 >>20*20
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(0).getPrice());
//                Log.e("ksssss",pattenList.get(0).getPrice()+"");
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(0).getOutPrice());
//                Log.e("kjjjjj",pattenList.get(0).getOutPrice()+"");
//            }
//        }else if (patternId==5){//50 >>30*20
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(4).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(4).getOutPrice());
//            }
//        }else if (patternId==2){//20 >>20*30
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(1).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(1).getOutPrice());
//            }
//
//
//        }else if (patternId==3){//30 >>20*60
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(2).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(2).getOutPrice());
//            }
//        }else if (patternId==6){//60 >>30*60
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(5).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(5).getOutPrice());
//            }
//        }else if (patternId==4){//40 >>20*90
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(3).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(3).getOutPrice());
//            }
//        }else if (patternId==7){//70 >>30*30
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(6).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(6).getOutPrice());
//            }
//        }else if (patternId==8){//80 >>40*30
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(7).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(7).getOutPrice());
//            }
//        }else if (patternId==9){//9 >>50*30
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(8).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(8).getOutPrice());
//            }
//        }else if (patternId==10){//100 >>40*40
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(9).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(9).getOutPrice());
//            }
//        }else if (patternId==11){//110 >>60*60
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(10).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(10).getOutPrice());
//            }
//        }else if (patternId==12){//120 >>80*80
//            if (isInEgypt) {
//                ShippingActivity.imageModelList.get(i).setPattrenInPrice(pattenList.get(11).getPrice());
//            }else {
//                ShippingActivity.imageModelList.get(i).setPattrenOutPrice(pattenList.get(11).getOutPrice());
//            }
//        }
//        Log.e("ppppp",patternId+"");
//        return price;
//    }




    public double calculateInPrice(){
        double price=0;
        for (int i=0;i<ShippingActivity.imageModelList.size();i++){
            price=price+(ShippingActivity.imageModelList.get(i).getPatternAmount() * ShippingActivity.imageModelList.get(i).getPattrenInPrice());
        }
        return price;
    }

    public double calculateOutPrice(){
        double price=0;
        for (int i=0;i<ShippingActivity.imageModelList.size();i++){
            price=price+(ShippingActivity.imageModelList.get(i).getPatternAmount() * ShippingActivity.imageModelList.get(i).getPattrenOutPrice());
            Log.e("OutttPrice",ShippingActivity.imageModelList.get(i).getPattrenOutPrice()+"");
        }
        return price;
    }

    void omar(){
//        for (int i=0;i<ShippingActivity.imageModelList.size();i++){
//            Log.e("patternIdFromAdpterss",ShippingActivity.imageModelList.get(i).getTypePatternId()+"");
//            if (pattenList.size()!=0) {
//                getprice(i, ShippingActivity.imageModelList.get(i).getTypePatternId());
//                Log.e("getPattrenInPrice", ShippingActivity.imageModelList.get(i).getPattrenInPrice() + "");
//                Log.e("getPattrenOutPrice", ShippingActivity.imageModelList.get(i).getPattrenOutPrice() + "");
//            }
//        }
    }
}