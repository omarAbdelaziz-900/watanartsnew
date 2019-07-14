//package com.WattanArt.Adapters;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.os.Build;
//import android.provider.MediaStore;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.WattanArt.R;
//import com.WattanArt.model.AppModels.ImageModel;
//import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
//import com.WattanArt.ui.EditImage.EditImageActivity;
//import com.yalantis.ucrop.Constants;
//import com.yalantis.ucrop.view.UpdatedViews.GestureCropImageViewUpdated;
//import com.yalantis.ucrop.view.UpdatedViews.OverlayViewUpdated;
//
//import java.io.IOException;
//import java.util.List;
//
//import static com.WattanArt.ui.Shipping.ShippingActivity.REQUEST_FOR_ACTIVITY_CODE;
//
///**
// * Created by khaled.badawy on 4/15/2018.
// */
//
//public class ShippingAdapterCopy extends RecyclerView.Adapter<ShippingAdapterCopy.MyViewHolder> implements GestureCropImageViewUpdated.ImageClickListner {
//
//    private List<ImageModel> imagemodellist;
//    private Context mContext;
//    DisplayMetrics displayMetrics;
//    float[] ratios = new float[]{1f, 8f / 12f, 12f / 8f, 24f / 8f, 24f / 12f, 36f / 8f};
//    List<SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity> pattenList;
//
//    getNewPrice getNewPrice;
//    getNewpieces getNewpieces;
//    onDeleteOnImage onDeleteOnImage;
//    onReplaceOnImage onReplaceOnImage;
//
//    public ShippingAdapterCopy(Context context, List<ImageModel> ImageModelList, getNewPrice getNewPrice, getNewpieces getNewpieces,
//                               onDeleteOnImage onDeleteOnImage, onReplaceOnImage onReplaceOnImage) {
//
//        mContext = context;
//        this.imagemodellist = ImageModelList;
//        this.getNewPrice = getNewPrice;
//        this.getNewpieces = getNewpieces;
//        this.onDeleteOnImage = onDeleteOnImage;
//        this.onReplaceOnImage = onReplaceOnImage;
//
//        displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//    }
//
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.shipping_item, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//
//    public void setList(List<ImageModel> ImageModelList) {
//        this.imagemodellist.clear();
//        this.imagemodellist.addAll(ImageModelList);
////        notifyDataSetChanged();
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        return 1;
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//
////        holder.gestureCropImageView.setScaleEnabled(false);
////        holder.gestureCropImageView.setRotateEnabled(false);
////        holder.gestureCropImageView.setGestureEnabled(false);
//
//        Bitmap bitmap = null;
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imagemodellist.get(position).getUri());
//
//            try {
//
//                if (imagemodellist.get(position).getBitmap() == null) {
//                    holder.gestureCropImageView.setImageBitmap(bitmap);
//                    imagemodellist.get(position).setBitmap(bitmap);
//                } else {
//                    holder.gestureCropImageView.setImageBitmap(imagemodellist.get(position).getBitmap());
//                }
//
////            imagemodellist.get(position).setmExifInfo(holder.gestureCropImageView.getExifInfo());
//
////                            if (imagemodellist.get(position).getImageHeight() == 0 || imagemodellist.get(position).getImageWidth() == 0) {
////                                imagemodellist.get(position).setmMaxResultImageSizeX(bitmap.getWidth());
////                                imagemodellist.get(position).setmMaxResultImageSizeY(bitmap.getHeight());
////                            } else {
////                                imagemodellist.get(position).setmMaxResultImageSizeX(imagemodellist.get(position).getImageWidth());
////                                imagemodellist.get(position).setmMaxResultImageSizeY(imagemodellist.get(position).getImageHeight());
////                            }
//
//
//                if (imagemodellist.get(position).getmCropRect() == null) {
//                    imagemodellist.get(position).setmCropRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()));
//                }
////                              else {
////                                imagemodellist.get(position).setmCropRect(imagemodellist.get(position).getmCropRect());
////                            }
//
////            imagemodellist.get(position).setmCurrentImageCorners(holder.gestureCropImageView.getMCurrentImageCorners());
////            imagemodellist.get(position).setmImageInputPath(holder.gestureCropImageView.getImageInputPath());
////            imagemodellist.get(position).setmImageOutputPath(holder.gestureCropImageView.getImageOutputPath());
//
//                if (imagemodellist.get(position).getCurrentRatio() == 0f) {
//                    if (imagemodellist.get(position).getBitmap().getHeight() == imagemodellist.get(position).getBitmap().getWidth()) {
//                        ShippingAdapterCopy.this.setImageDimensions(holder.gestureCropImageView, 1f);
//                        imagemodellist.get(position).setCurrentRatio(1f);
//                    } else if (imagemodellist.get(position).getBitmap().getHeight() > imagemodellist.get(position).getBitmap().getWidth()) {
//                        ShippingAdapterCopy.this.setImageDimensions(holder.gestureCropImageView, 8f / 12f);
//                        imagemodellist.get(position).setCurrentRatio(8f / 12);
//                    } else if (imagemodellist.get(position).getBitmap().getHeight() < imagemodellist.get(position).getBitmap().getWidth()) {
//                        ShippingAdapterCopy.this.setImageDimensions(holder.gestureCropImageView, 12f / 8f);
//                        imagemodellist.get(position).setCurrentRatio(12f / 8f);
//                    }
////                                holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(0);
////                                holder.gestureCropImageView.getOverlayView().drawCropGrid(new Canvas());
//                } else {
//                    ShippingAdapterCopy.this.setImageDimensions(holder.gestureCropImageView,
//                            imagemodellist.get(position).getCurrentRatio());
////                holder.gestureCropImageView.getOverlayView().setCropGridColumnCount(2);
////                holder.gestureCropImageView.getOverlayView().drawCropGrid(new Canvas());
//                }
//
//
//                if (bitmap.getHeight() < 600 && bitmap.getWidth() < 600) {
//                    imagemodellist.get(position).setLowResolution(true);
//                    holder.resolution.setVisibility(View.VISIBLE);
//                } else {
//                    imagemodellist.get(position).setLowResolution(false);
//                    holder.resolution.setVisibility(View.INVISIBLE);
//                }
//
//
////                            holder.gestureCropImageView.zoomInImage(imagemodellist.get(position).getCurrentScale());
//
//                Log.e("XANDY", "    " + imagemodellist.get(position).getCurrentScale() + "     " +
//                        imagemodellist.get(position).getPositionX() + "     " + imagemodellist.get(position).getPositionY());
//
//
////                            holder.gestureCropImageView.postScale(imagemodellist.get(position).getCurrentScale() ,
////                                    imagemodellist.get(position).getPositionX() ,  imagemodellist.get(position).getPositionY());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            holder.quantityTv.setText(imagemodellist.get(position).getQuantity() + "");
////
//            if (imagemodellist.get(position).isSegmented()) {
//                holder.gestureCropImageView.setShowCropGrid(true);
//                holder.gestureCropImageView.setCropGridColumnCount(2);
//            } else {
//                holder.gestureCropImageView.setShowCropGrid(false);
//                holder.gestureCropImageView.setCropGridColumnCount(0);
//            }
//            holder.gestureCropImageView.drawCropGrid(new Canvas());
//
//            getPiecesNumber();
//            CalculatePrice(pattenList);
//
//            holder.gestureCropImageView.setOnClickListener(v -> {
//                try {
//                    Intent intent = new Intent(mContext, EditImageActivity.class);
//
//                    intent.putExtra("uri", imagemodellist.get(position).getUri());
//                    intent.putExtra("index", position);
//                    if (imagemodellist.get(position).isSegmented())
//                        intent.putExtra(Constants.IS_SEGMENTED, imagemodellist.get(position).isSegmented());
//
//                    if (imagemodellist.get(position).getCurrentRatio() != 0) {
//                        intent.putExtra(Constants.EXTRA_ASPECT_RATIO_OPTIONS, imagemodellist.get(position).getCurrentRatio());
//                    }
//                    ((Activity) mContext).startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            });
//
//            holder.deleteOneImage.setOnClickListener(v -> {
//                onDeleteOnImage.onOneImageDelted(position);
//            });
//
//            holder.resolution.setOnClickListener(v -> {
//                Toast.makeText(mContext, mContext.getString(R.string.img_resoluyion), Toast.LENGTH_SHORT).show();
//            });
//
//            holder.plusIV.setOnClickListener(view -> {
//
//                int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
//                holder.quantityTv.setText(++quantity + "");
//                imagemodellist.get(position).setQuantity(quantity);
//
//                getPiecesNumber();
//                CalculatePrice(pattenList);
//            });
//
//            holder.minusIV.setOnClickListener(view -> {
//
//                int quantity = Integer.valueOf(holder.quantityTv.getText().toString());
//                if (quantity > 1) {
//                    holder.quantityTv.setText(--quantity + "");
//                    imagemodellist.get(position).setQuantity(quantity);
//                    getPiecesNumber();
//                    CalculatePrice(pattenList);
//                }
//
//            });
//
//            holder.replace.setOnClickListener(view -> {
//                onReplaceOnImage.onOneImageReplaced(position);
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    public int getPiecesNumber() {
//        int pieces = 0;
//
//
//        for (ImageModel imageModel : imagemodellist) {
//
//            if (imageModel.getCurrentRatio() == 0) {
//
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
//
//        Log.e("Pieces", "" + pieces);
//        getNewpieces.returnPrice(pieces);
//        return pieces;
//    }
//
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
//
//    public double CalculatePrice(List<SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity> pattenList) {
//        double price = 0d;
//        if (pattenList != null) {
//            for (ImageModel imageModel : imagemodellist) {
//                double retur = getPatternPrice(imageModel.getCurrentRatio(), pattenList, imageModel.getQuantity());
//                price += retur;
//            }
//        }
//
//        if (getNewPrice != null) {
//            getNewPrice.returnPrice(price);
//        }
//        return price;
//    }
//
//    public interface getNewPrice {
//        void returnPrice(double price);
//    }
//
//    public interface getNewpieces {
//        void returnPrice(int piece);
//    }
//
//    public interface onDeleteOnImage {
//        void onOneImageDelted(int index);
//    }
//
//    public interface onReplaceOnImage {
//        void onOneImageReplaced(int index);
//    }
//
//    private void setImageDimensions(OverlayViewUpdated imageViewUpdated, float mTargetAspectRatio) {
//        Log.e("AspectRatio", "->" + mTargetAspectRatio);
//        int desiredHeight = 350;
//
//        int widthMode = View.MeasureSpec.getMode(imageViewUpdated.getWidth());
//        int widthSize = View.MeasureSpec.getSize(imageViewUpdated.getWidth());
//        int heightMode = View.MeasureSpec.getMode(imageViewUpdated.getHeight());
//        int heightSize = View.MeasureSpec.getSize(imageViewUpdated.getHeight());
//
//        int width;
//        int height;
//
//        int heightWindow = displayMetrics.heightPixels;
//        int widthWindow = displayMetrics.widthPixels;
//
//        Log.e(ShippingAdapterCopy.class.getSimpleName(), "MeasureSpec:: widthWindow:" + String.valueOf(widthWindow) + ", heightWindow:" + String.valueOf(heightWindow));
//
//        if (heightSize > (heightWindow / 6f))
//            heightSize = (int) (heightWindow / 5.5f);
//
//        //Measure Height
//        if (heightMode == View.MeasureSpec.EXACTLY) {
//            //Must be this size
//            width = Math.min((int) (heightSize * mTargetAspectRatio), widthSize);
//            height = Math.min((int) (width / mTargetAspectRatio), heightSize);
//        } else if (heightMode == View.MeasureSpec.AT_MOST) {
//            //Can't be bigger than...
//            width = Math.min((int) (heightSize * mTargetAspectRatio), widthSize);
//            height = Math.min((int) (width / mTargetAspectRatio), heightSize);
//        } else {
//            //Be whatever you want
//            height = desiredHeight;
//        }
//
//        //Measure Width
//        if (widthMode == View.MeasureSpec.EXACTLY) {
//            //Must be this size
//            width = widthSize;
//        } else if (widthMode == View.MeasureSpec.AT_MOST) {
//            //Can't be bigger than...
//            width = Math.min((int) (height * mTargetAspectRatio), widthSize);
//            if (width == widthSize) {
//                height = Math.min((int) (width / mTargetAspectRatio), heightSize);
//            } else {
//                height = heightSize;
//            }
//        } else {
//            //Be whatever you want
//            width = (int) (height * mTargetAspectRatio);
//        }
////
//        Log.e(ShippingAdapterCopy.class.getSimpleName(), "MeasureSpec:: width:" + String.valueOf(width) + ", height:" + String.valueOf(height));
//        //MUST CALL THIS
//        imageViewUpdated.getLayoutParams().height = height;
//        imageViewUpdated.getLayoutParams().width = width;
//        imageViewUpdated.requestLayout();
//
////        imageViewUpdated.setTargetAspectRatio(mTargetAspectRatio);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return imagemodellist.size();
//    }
//
//    @Override
//    public void onImageClick() {
//
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        OverlayViewUpdated gestureCropImageView;
//        ImageView resolution, minusIV, plusIV, deleteOneImage, replace;
//        TextView quantityTv;
//
//
//        public MyViewHolder(View view) {
//            super(view);
//            gestureCropImageView = view.findViewById(R.id.shippingImageItem);
//            resolution = view.findViewById(R.id.resolution);
//            minusIV = view.findViewById(R.id.minusIV);
//            plusIV = view.findViewById(R.id.plusIV);
//            quantityTv = view.findViewById(R.id.quantityTv);
//            deleteOneImage = view.findViewById(R.id.deleteOneImage);
//            replace = view.findViewById(R.id.replace);
//        }
//
//    }
//
//
//}
