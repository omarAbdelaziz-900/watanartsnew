package com.WattanArt.Utils.image;

public class ImageHelper {

    private ImageHelper(){}

//
//    /**
//     *  Load the image by the resource ID.
//     *
//     * @param resID the id of the image whic you need to load
//     * @param placeHolderID  the id of the image which you need to load as placeholder
//     * @param imageView the image which u need to set the loaded image into it
//     */
//    public static void loadImageByResourceId(@DrawableRes int resID, @DrawableRes int placeHolderID, @NonNull ImageView imageView){
//
//        DisplayMetrics metrics  = imageView.getResources().getDisplayMetrics();
//        final int widthMetrics  = metrics.widthPixels;
//        final int heightMetrics =  metrics.heightPixels;
//
//        RequestOptions options = new RequestOptions();
//        options.centerCrop();
//        options.dontAnimate();
//        options.diskCacheStrategy(DiskCacheStrategy.NONE);
//        options.placeholder(placeHolderID);
//
//
//        Glide.with(imageView.getContext())
//                .applyDefaultRequestOptions(options)
//                .load(resID)
//                .into(imageView);
//
////        new BitmapImageViewTarget(imageView){
////
////            /* We don't want to load very big images on devices with small screens.
////             *  This will help Glide correctly choose images scale when reading them.
////             */
////            @Override
////            public void getSize(final SizeReadyCallback cb) {
////                super.getSize(new SizeReadyCallback() {
////                    @Override
////                    public void onSizeReady(int width, int height) {
////                        cb.onSizeReady(widthMetrics / 2, heightMetrics / 2);
////                    }
////                });
////
////            }}
//
//    }
//
//
//    /**
//     *  Load the Thubm of the image Till it load the Full Image.
//     *
//     * @param photoUrl the url of the image full
//     * @param thumbUrl the url of the image thumb
//     * @param placeHolderID the id of the placeholder
//     * @param imageView where u need to set the thumb
//     */
//    public static void loadImageAsThumb(String photoUrl,String thumbUrl,
//                                        @DrawableRes int placeHolderID, @NonNull ImageView imageView){
//
//
//        RequestOptions options = new RequestOptions();
//        options.centerCrop();
//        options.dontAnimate();
//        options.diskCacheStrategy(DiskCacheStrategy.ALL);
//        options.placeholder(placeHolderID);
//
//        Glide.with(imageView.getContext())
//                .load(photoUrl)
//                .thumbnail(Glide.with(imageView.getContext())
//                        .load(thumbUrl)
//                .into(new GlideImageTarget(imageView));
//    }
//
//
//    /**
//     * Load the Full image with compelete Size .
//     *
//     * @param photoUrl
//     * @param thumbUrl
//     * @param placeHolderID
//     * @param imageView
//     * @param listener the listener which listen to downlaod the image.
//     */
//    public static void loadFullImage(String photoUrl, String thumbUrl, @DrawableRes int placeHolderID,
//                              @NonNull ImageView imageView, @Nullable final ImageLoadingListener listener){
//
//        Glide.with(imageView.getContext())
//                .load(photoUrl)
//                .asBitmap()
//                .dontAnimate()
//                .placeholder(placeHolderID)
//                .thumbnail(Glide.with(imageView.getContext())
//                        .load(thumbUrl)
//                        .asBitmap()
//                        .animate(ANIMATOR)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL))
//                .listener(new GlideDrawableListener() {
//                    @Override
//                    public boolean onException(Exception ex, String url, Target<Bitmap> target, boolean isFirstResource) {
//                        listener.onLoaded();
//                        return super.onException(ex, url, target, isFirstResource);
//                    }
//
//                    @Override
//                    public void onFail(String url) {
//                        super.onFail(url);
//                        listener.onFailed();
//                    }
//
//                    @Override
//                    public void onSuccess(String url) {
//                        super.onSuccess(url);
//                        listener.onLoaded();
//                    }
//                });
//    }
//
//    public interface ImageLoadingListener {
//        void onLoaded();
//
//        void onFailed();
//    }
//
//    private static final ViewPropertyAnimation.Animator ANIMATOR =
//            new ViewPropertyAnimation.Animator() {
//                @Override
//                public void animate(View view) {
//                    view.setAlpha(0f);
//                    view.animate().alpha(1f);
//                }
//            };

}
