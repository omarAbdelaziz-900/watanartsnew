package com.WattanArt.ui.Shipping;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Adapters.ShippingAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.WrapContentLinearLayoutManager;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.EditImage.EditImageActivity;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Payment.WebviewActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yalantis.ucrop.Constants;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ShippingActivity extends BaseActivity implements ShippingMvpView,
        View.OnClickListener,
        ShippingAdapter.getNewPrice,
        ShippingAdapter.getNewpieces,
        ShippingAdapter.onDeleteOnImage,
        ShippingAdapter.onReplaceOnImage,
        ShippingAdapter.clickListner {

    RecyclerView recyclerView;
    public static List<ImageModel> imageModelList = new ArrayList<>();
    ArrayList<String> photosList;
    int listSize = 0;
    int secondaryListSize = 0;

    ShippingAdapter adapter;
    int index = 0;
    ValidationTool validationTool;

    LinearLayout add_holder, delete_all_holder;

    public static final int OPEN_REGISTERATION_CODE = 101;

    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 10;
    public static final int REQUEST_STORAGE_WRITE_PERMISSION = 11;

    int type = 1;

    UserData userData = new UserData();
    TextView shippingPrice, deliveryTime;

    boolean replaceImage = false;
    int replaceImageIndex = 0;

    CustomeButtonBold completeOrder;

    boolean isInEgypt = false;

    double mainPriceHolder = 0;
    boolean isCouponCodeApplied = false;
    boolean hasCouponChecked=false;

//    @BindView(R.id.toolbar_image_view)
//    public ImageView mToolbarBackImageView;

    @BindView(R.id.toolbar_tv_title)
    public CustomeTextViewBold mToolbarTitleTextView;
    @BindView(R.id.pieces_number)
    CustomeTextView pieces_number;

    @BindView(R.id.priceTextView)
    CustomeTextView priceTextView;


    @BindView(R.id.country_spinner)
    Spinner country_spinner;

    @BindView(R.id.onlineRadioButton)
    RadioButton onlineRadioButton;
    @BindView(R.id.cashRadioButton)
    RadioButton cashRadioButton;

    @BindView(R.id.city_spinner)
    Spinner city_spinner;


    @BindView(R.id.addressEditText)
    CustomEditText addressEditText;


    @BindView(R.id.couponEditText)
    CustomEditText couponEditText;
    @BindView(R.id.phoneEditText)
    CustomEditText phoneEditText;


    @BindView(R.id.checkCode)
    CustomeTextView checkCode;

    @BindView(R.id.defualt_cityy_text)
    CustomeTextView cityy_text;

    @BindView(R.id.radioGroupPayment)
    RadioGroup radioGroupPayment;
    int validCode=0;

    @Inject
    ShippingPresenterMvp<ShippingMvpView> mPresenter;

    SelectCountryCitiyListsResponseModel.Result pattenList;
    private List<String> countrylist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();

    int cityId, countryId;
    public static final int REQUEST_FOR_ACTIVITY_CODE = 105;
    ProgressDialog progressDialog;
//    AppEventsLogger logger;

    String currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.title_shipping_order));
//        mToolbarBackImageView.setOnClickListener(v -> onBackPressed());

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

//        logger = AppEventsLogger.newLogger(this);
        validationTool = new ValidationTool(this);

        try {
            isInEgypt = UtilitiesManager.getUserCountry(this).toLowerCase().equals("eg");
//            Toast.makeText(this,isInEgypt+"",Toast.LENGTH_LONG).show();
            Log.d("isInEgypt",isInEgypt+UtilitiesManager.getUserCountry(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isInEgypt)
            currency = getString(R.string.le);
        else
            currency = getString(R.string.dolar);


        if (!isInEgypt) {
//            Toast.makeText(this,isInEgypt+"ttt",Toast.LENGTH_LONG).show();
            cashRadioButton.setEnabled(false);
            onlineRadioButton.setChecked(true);
            type = 2;
        }


        completeOrder = findViewById(R.id.complete_order);
        deliveryTime = findViewById(R.id.deliveryTime);
        shippingPrice = findViewById(R.id.shippingPrice);


        delete_all_holder = findViewById(R.id.delete_all_holder);
        add_holder = findViewById(R.id.add_holder);

        completeOrder.setOnClickListener(this);
        delete_all_holder.setOnClickListener(this);
        add_holder.setOnClickListener(this);
        checkCode.setOnClickListener(this);

        photosList = getIntent().getStringArrayListExtra("photos_list");


        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//
        recyclerView.setLayoutManager(new LinearLayoutManager(this, WrapContentLinearLayoutManager.HORIZONTAL, false));//
//        adapter = new ShippingAdapter(ShippingActivity.this, imageModelList, ShippingActivity.this,
//                ShippingActivity.this, ShippingActivity.this,
//                ShippingActivity.this, ShippingActivity.this);


        File file;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;

        showLoadingcustom(ShippingActivity.this);

        for (String path : photosList) {
            file = new File(path);
            ImageModel imageModel = new ImageModel(Uri.fromFile(file), 0f);
//            BitmapFactory.decodeFile(path, options);

            Glide.with(ShippingActivity.this)
                    .asBitmap()
                    .load(path)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    Transition<? super Bitmap> transition) {
                            int imageWidth = bitmap.getWidth();
                            int imageHeight = bitmap.getHeight();

                            Log.e("ImageAttrShipping", "-->" + path + "       width = " + imageWidth + "       height = " + imageHeight);

                            imageModel.setCurrentScale(1f);
                            imageModel.setCurrentAngle(0f);
                            imageModel.setMainImageHeight(imageHeight);
                            imageModel.setMainImageWidth(imageWidth);
                            imageModel.setImageHeight(imageHeight);
                            imageModel.setImageWidth(imageWidth);
                            imageModel.setQuantity(1);
                            imageModel.setSegmented(false);
                            imageModel.setPath(path);
                            imageModelList.add(imageModel);
                            listSize++;

                            if (listSize == photosList.size()) {
                                listSize = 0;

                                for (int position = 0; position < photosList.size(); position++) {
                                    try {
                                        int finalPosition = position;

                                        BitmapLoadUtils.decodeBitmapInBackground(ShippingActivity.this,
                                                imageModelList.get(finalPosition).getUri(),
                                                imageModelList.get(finalPosition).getUri(),
                                                BitmapLoadUtils.calculateMaxBitmapSize(ShippingActivity.this)
                                                , BitmapLoadUtils.calculateMaxBitmapSize(ShippingActivity.this),
                                                new BitmapLoadCallback() {

                                                    @Override
                                                    public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
                                                                               @NonNull String imageInputPath,
                                                                               @Nullable String imageOutputPath) {

                                                        final double factorHeight = ((double) imageModelList.get(finalPosition).getMainImageHeight()) / ((double) bitmap.getHeight());
                                                        final double factorWidth = ((double) imageModelList.get(finalPosition).getMainImageWidth()) / ((double) bitmap.getWidth());

                                                        ShippingActivity.imageModelList.get(finalPosition).setFactorWidth(factorWidth);
                                                        ShippingActivity.imageModelList.get(finalPosition).setFactorHeight(factorHeight);

                                                        ShippingActivity.imageModelList.get(finalPosition).setBitmap(bitmap);
                                                        ShippingActivity.imageModelList.get(finalPosition).setFilteredBitmap(bitmap);
                                                        ShippingActivity.imageModelList.get(finalPosition).setScreenShotImage(bitmap);
                                                        ShippingActivity.imageModelList.get(finalPosition).setmExifInfo(exifInfo);
                                                        ShippingActivity.imageModelList.get(finalPosition).
                                                                setmCropRect(new RectF(0, 0, imageModelList.get(finalPosition).getMainImageWidth(),
                                                                        imageModelList.get(finalPosition).getMainImageHeight()));
                                                        if (ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight()
                                                                == ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth()) {

                                                            ShippingActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);

                                                        } else if (ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight() >
                                                                ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth()) {

                                                            if (ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
                                                                    ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
                                                                    (((float) ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight()) / 1.5f) >= 1000) {
                                                                ShippingActivity.imageModelList.get(finalPosition).setCurrentRatio(8f / 12);
                                                            } else {
                                                                ShippingActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
                                                            }
                                                        } else if (ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight()
                                                                < ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth()) {
                                                            if (ShippingActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
                                                                    ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
                                                                    (((float) ShippingActivity.imageModelList.get(finalPosition).getMainImageWidth()) / 1.5f) >= 1000
                                                            ) {
                                                                ShippingActivity.imageModelList.get(finalPosition).setCurrentRatio(12f / 8f);
                                                            } else {
                                                                ShippingActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
                                                            }
                                                        }


                                                        listSize++;

                                                        if (listSize == photosList.size()) {
                                                            adapter = new ShippingAdapter(ShippingActivity.this, imageModelList, ShippingActivity.this,
                                                                    ShippingActivity.this, ShippingActivity.this,
                                                                    ShippingActivity.this, ShippingActivity.this);
                                                            recyclerView.setAdapter(adapter);
                                                            recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);


                                                            progressDialog.dismiss();

                                                            if (pattenList == null)
                                                                if (isNetworkConnected()) {
                                                                    mPresenter.getShippingPrices();
                                                                } else {
                                                                    Toast.makeText(ShippingActivity.this, getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
                                                                }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(@NonNull Exception bitmapWorkerException) {
                                                        Log.e("setImageUriSecond", "onFailure: setImageUri", bitmapWorkerException);
                                                    }

                                                    @Override
                                                    public void afterLoadComplete() {

                                                    }
                                                });

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }


//                                returnPatternList(pattenList);
                                //returned view type in the adapter so
                                // recycler don't recycle some items and
                                //don't redraw it again as it lose the image some times
                                //        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);
                            }
                        }
                    });


        }


        radioGroupPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cashRadioButton) {
                type = 1;
            } else {
                type = 2;
            }
        });

        phoneEditText.setText(userData.getPhone(this).isEmpty() ? "" : userData.getPhone(this));

//        LatLngBounds NewDelhi = new LatLngBounds(your special area SE LatLng , NE LatLng );
//        if(NewDelhi.contains(your location LatLng))
//        Do something

    }

    ProgressDialog showLoadingcustom(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.layout_dialog_loading);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imageModelList.clear();
    }


    @Override
    protected void setUpActivityOrFragmentRequirment() {
    }

    private void prepareImage() {

        adapter.notifyItemChanged(index);

        Log.e("ThisRatio", "" + imageModelList.get(index).getCurrentRatio());
        Log.e("brightness", "" + imageModelList.get(index).getCurrentBrightness());

        if (pattenList != null) {
            pieces_number.setText(getString(R.string.no_item_1) + " " + adapter.getPiecesNumber() + " " + getString(R.string.no_item_2));

//            priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.CalculatePrice(pattenList.getPatternType()) + " " + getString(R.string.price_item_2));
            checkShippingPrice();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOR_ACTIVITY_CODE && resultCode == RESULT_OK) {

            try {
                index = data.getIntExtra(Constants.EXTRA_INDEX, 0);
                if (imageModelList.get(index) == null) {
                    //this image is deleted from the EditImageActivity
                    imageModelList.remove(index);
                    photosList.remove(index);
                    adapter.notifyDataSetChanged();
                    if (imageModelList.size() == 0) {
                        replaceImage = false;
                        pickFromGallery(ShippingActivity.this);
                    }

                } else {
                    prepareImage();
                }

            } catch (Exception e) {
                Log.e("onActivityResultError", "setImageUri", e);
            }
        } else if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (replaceImage) {
                    replaceImage = false;

                    photosList.set(replaceImageIndex, photos.get(0));

                    Glide.with(ShippingActivity.this)
                            .asBitmap()
                            .load(photos.get(0))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap bitmap,
                                                            Transition<? super Bitmap> transition) {
                                    int imageWidth = bitmap.getWidth();
                                    int imageHeight = bitmap.getHeight();

                                    Log.e("ImageAttrShipping", "-->" + photos.get(0) + "       width = " + imageWidth + "       height = " + imageHeight);

                                    File file = new File(photos.get(0));
                                    Uri uri = Uri.fromFile(file);
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;

                                    ImageModel mainImageModel = new ImageModel(uri, 0f);
                                    mainImageModel.setCurrentScale(1f);
                                    mainImageModel.setCurrentAngle(0f);
//                    BitmapFactory.decodeFile(photos.get(0), options);
//                    int imageHeight = options.outHeight;
//                    int imageWidth = options.outWidth;
//                    mainImageModel.setmCropRect(new RectF(0, 0, imageWidth, imageHeight));
                                    mainImageModel.setMainImageHeight(imageHeight);
                                    mainImageModel.setMainImageWidth(imageWidth);

                                    mainImageModel.setImageHeight(imageHeight);
                                    mainImageModel.setImageWidth(imageWidth);

                                    mainImageModel.setQuantity(1);
                                    mainImageModel.setPath(photos.get(0));
                                    imageModelList.set(replaceImageIndex, mainImageModel);
                                    adapter.notifyItemChanged(replaceImageIndex);
                                }
                            });


                } else {
                    replaceImage = false;

                    showLoadingcustom(ShippingActivity.this);

                    // add more images
                    secondaryListSize = 0;
                    List<ImageModel> imageModels = new ArrayList<>();
                    for (String path : photos) {
                        if (photosList.contains(path)) {
//                            Log.e("dasdsad", "" + photosList.indexOf(path));

                            for (int i = 0; i < imageModelList.size(); i++) {
                                if (imageModelList.get(i).getPath().equals(path)) {
                                    imageModels.add(imageModelList.get(i));
                                    break;
                                }
                            }

                            secondaryListSize++;
                            if (secondaryListSize == photos.size()) {
                                imageModelList.clear();
                                imageModelList.addAll(imageModels);

                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                        } else {
                            Glide.with(ShippingActivity.this)
                                    .asBitmap()
                                    .load(path)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap,
                                                                    Transition<? super Bitmap> transition) {
                                            int imageWidth = bitmap.getWidth();
                                            int imageHeight = bitmap.getHeight();

                                            ImageModel imageModel = new ImageModel(Uri.fromFile(new File(path)), 0f);
                                            imageModel.setCurrentScale(1f);
                                            imageModel.setCurrentAngle(0f);
                                            imageModel.setPath(path);
                                            imageModel.setCurrentRatio(0f);

                                            imageModel.setMainImageHeight(imageHeight);
                                            imageModel.setMainImageWidth(imageWidth);
                                            imageModel.setImageHeight(imageHeight);
                                            imageModel.setImageWidth(imageWidth);
                                            imageModel.setQuantity(1);
                                            imageModels.add(imageModel);
                                            secondaryListSize++;

                                            if (secondaryListSize == photos.size()) {
                                                imageModelList.clear();
                                                imageModelList.addAll(imageModels);
                                                photosList = photos;
                                                adapter.notifyDataSetChanged();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
                        }

                    }


                }
            } else {
                if (imageModelList.isEmpty()) {
                    ShippingActivity.this.finish();
                }
            }
        } else if (requestCode == OPEN_REGISTERATION_CODE && resultCode == RESULT_OK) {
            if (couponEditText.getText().toString().isEmpty()){
                // no copoun code used
                hasCouponChecked=true;
            }
            if (hasCouponChecked){
                completeOrder();
            }else {
                Toast.makeText(this,getString(R.string.check_code),Toast.LENGTH_LONG).show();
            }
//            completeOrder();
        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (imageModelList.isEmpty()) {
//            pickFromGallery(this);
//        }
//    }

    @Override
    public void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList) {
        if (adapter == null || patternTypeEntityList == null)
            return;
        pattenList = patternTypeEntityList;
        pieces_number.setText(getString(R.string.no_item_1) + " " + adapter.getPiecesNumber() + " " + getString(R.string.no_item_2));
        checkShippingPrice();

        //        Log.d("pricee bhereee",adapter.CalculatePrice(pattenList.getPatternType())+ "  patern list size "+pattenList.getPatternType().size());

        getCountryList(patternTypeEntityList);
    }

    @Override
    public void afterLogin() {
        if (couponEditText.getText().toString().isEmpty()){
            // no copoun code used
            hasCouponChecked=true;
        }
        if (hasCouponChecked){
            completeOrder();
        }else {
            Toast.makeText(this,getString(R.string.check_code),Toast.LENGTH_LONG).show();
        }
//        completeOrder();
//        if (spinnerValid(country_spinner, countryId) && spinnerValid(city_spinner, cityId)
//                && validationTool.validateField(addressEditText, getString(R.string.verify_address))) {
//            boolean hasMoreThan3Items = mPresenter.checkOrderQuantity(imageModelList);
//            if (hasMoreThan3Items) {
//                mPresenter.checkImageHasLowResolution(this, imageModelList, addressEditText, cityId, countryId, couponEditText, type);
//            } else {
//                Toast.makeText(this, getString(R.string.item_more_3), Toast.LENGTH_LONG).show();
//            }
//        }
    }

    @Override
    public void baackPress(int orderId) {
//        mToolbarBackImageView.performClick();
//        this.onBackPressed();
//        finish();

        imageModelList.clear();
        if (radioGroupPayment.getCheckedRadioButtonId() == R.id.cashRadioButton) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("From", " shipping order");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("id", orderId);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteAllImages() {
        imageModelList.clear();
        photosList.clear();


        adapter.notifyDataSetChanged();
        pickFromGallery(ShippingActivity.this);
    }

    @Override
    public void onAddNewImages() {

    }

    @Override
    public void applyCouponCode(boolean isValid) {

        hasCouponChecked=true;
        if (adapter == null || pattenList == null) {
            return;
        }
        if (isValid) {
            if (validCode==0) {
                Toast.makeText(this, getString(R.string.code_is_valid), Toast.LENGTH_LONG).show();
                validCode=1;
            }
            isCouponCodeApplied = isValid;

            if (pattenList != null) {
                double afterDiscountRate =  (Double.valueOf(100) - Double.valueOf(pattenList.getPrices().
                        getDiscountRate())) / Double.valueOf(100);


                if (adapter.getPiecesNumber() == 3) {
                    double mainPrice;
                    if (isInEgypt) {
                        mainPrice = pattenList.getPrices().getLocal3PiecePrice();
                    } else {
                        mainPrice = pattenList.getPrices().getWorld3PiecePrice();
                    }
                    priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice * afterDiscountRate + " " + currency);
                } else if (adapter.getPiecesNumber() > 3) {
                    double mainPrice;
                    if (isInEgypt) {
                        mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getLocalPiecePrice() + pattenList.getPrices().getLocal3PiecePrice();
                    } else {
                        mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getWorldPiecePrice() + pattenList.getPrices().getWorld3PiecePrice();
                    }
                    priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice * afterDiscountRate + " " + currency);
                } else {
                    if (isInEgypt) {
                        priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() *
                                pattenList.getPrices().getLocalPiecePrice() * afterDiscountRate + " " + currency);
                    } else {
                        priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() *
                                pattenList.getPrices().getWorldPiecePrice() * afterDiscountRate + " " + currency);
                    }
                }
            }
        } else {
            couponEditText.setText("");
            Toast.makeText(this, getString(R.string.code_not_valid), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void logOrderToAnalytics(ShippingRequest shippingRequest) {
//        Bundle params = new Bundle();
//        String userID = new UserData().getUserID(this);
//        int quantiy = 0;
//        for (int i = 0; i < imageModelList.size(); i++) {
//            quantiy = quantiy + imageModelList.get(i).getQuantity();
//        }
//        params.putString("UserID", userID);
//        params.putInt("Quantiy", quantiy);
//        params.putString("Address", addressEditText.getText().toString());
//        if (!TextUtils.isEmpty(couponEditText.getText().toString().trim())) {
//            params.putString("CouponCode", couponEditText.getText().toString());
//        }
//        if (type == 0) {
//            params.putString("BuyType", "Cash");
//        } else {
//            params.putString("BuyType", "Online");
//        }
//        logger.logEvent("Make Order", params);
//        Log.i("order_data", params.toString());
    }

    private boolean spinnerValid(Spinner spinnerName, int ID) {
        if (ID == 0) {
            if ((TextView) spinnerName.getSelectedView() != null)
                ((TextView) spinnerName.getSelectedView()).setError("anything here, just to add the icon");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.complete_order) {
            if (couponEditText.getText().toString().isEmpty()){
                // no copoun code used
                hasCouponChecked=true;
            }
            if (hasCouponChecked){
                completeOrder();
            }else {
                Toast.makeText(this,getString(R.string.check_code),Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.add_holder) {
            photosList.clear();
            for (ImageModel imageModel : imageModelList) {
                if (imageModel != null)
                    photosList.add(imageModel.getPath());
            }
            mPresenter.addNewImagesToOrder(this, photosList);
        } else if (v.getId() == R.id.delete_all_holder) {
            mPresenter.showDeleteAllDialog(this);
        } else if (v.getId() == R.id.checkCode) {
            if (couponEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.text_code_empty), Toast.LENGTH_SHORT).show();
            } else if (userData.getUserID(ShippingActivity.this).isEmpty()) {
                Toast.makeText(this, getString(R.string.login_before), Toast.LENGTH_SHORT).show();
            } else {
                if (isNetworkConnected()) {
                    CouponCodeModel couponCodeModel = new CouponCodeModel();
                    couponCodeModel.setCouponCode(couponEditText.getText().toString().trim());
                    couponCodeModel.setUserID(userData.getUserID(ShippingActivity.this));
                    mPresenter.checkCouponCode(couponCodeModel);
                } else {
                    Toast.makeText(this, getString(R.string.error_no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean checkWriteStoragePermission() {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(ShippingActivity.this, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Log.e("asdsad", "adasdsad");
                ActivityCompat.requestPermissions(ShippingActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_WRITE_PERMISSION);

            }
        }
        return writeStoragePermissionGranted;
    }

    private void completeOrder() {
        if (checkWriteStoragePermission())
            if (spinnerValid(country_spinner, countryId) && spinnerValid(city_spinner, cityId)
                    && validationTool.validateField(addressEditText, getString(R.string.verify_address))
                    && validationTool.validatePhone(phoneEditText, getString(R.string.invalid_phone))) {
                userData = new UserData();

                if (userData.getUserID(this) == null || userData.getUserID(this).isEmpty()) {
                    Toast.makeText(this, getString(R.string.login_first), Toast.LENGTH_LONG).show();
                    mPresenter.showLoginPopup(this, imageModelList, addressEditText, phoneEditText,
                            cityId, countryId, couponEditText, type);
                } else {
                    boolean hasMoreThan3Items = mPresenter.checkOrderQuantity(imageModelList);
                    if (hasMoreThan3Items) {


//                    int index =0;
//                    for (ImageModel imageModel : imageModelList) {
//                        if (imageModel.getCurrentRotate()!=0){
//                            Matrix cMatrix = imageModelList.get(index).getMatrix();
////                            cMatrix.postRotate(imageModel.getCurrentRotate());
//                            int temp = imageModelList.get(index).getPositionX();
//                            imageModelList.get(index).setPositionX(imageModelList.get(index).getPositionY());
//                            imageModelList.get(index).setPositionY(temp);
//
//                            int tempW = imageModelList.get(index).getMainImageWidth();
//                            imageModelList.get(index).setMainImageHeight(imageModelList.get(index).getMainImageWidth());
//                            imageModelList.get(index).setMainImageHeight(tempW);
//
//
//                            int tempW2 = imageModelList.get(index).getImageWidth();
//                            imageModelList.get(index).setImageHeight(imageModelList.get(index).getImageWidth());
//                            imageModelList.get(index).setImageHeight(tempW2);
//
//
//                            imageModelList.get(index).setFilteredBitmap(Bitmap.createBitmap
//                                    (imageModel.getFilteredBitmap(), 0, 0,
//                                    imageModel.getFilteredBitmap().getWidth(),
//                                    imageModel.getFilteredBitmap().getHeight(), cMatrix, true));
//
//                        }
//                        index++;
//                    }

                        mPresenter.checkImageHasLowResolution(this, imageModelList,
                                addressEditText, phoneEditText, cityId, countryId, couponEditText, type);
                    } else {
                        Toast.makeText(this, getString(R.string.item_more_3), Toast.LENGTH_LONG).show();
                    }
                }
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.addNewImagesToOrder(this, photosList);
                }
                break;
            case REQUEST_STORAGE_WRITE_PERMISSION:
                if (couponEditText.getText().toString().isEmpty()){
                    // no copoun code used
                    hasCouponChecked=true;
                }
                if (hasCouponChecked){
                    completeOrder();
                }else {
                    Toast.makeText(this,getString(R.string.check_code),Toast.LENGTH_LONG).show();
                }
//                completeOrder();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    void getCountryList(SelectCountryCitiyListsResponseModel.Result responseList) {
        //when deelte image don't fill again
        if (!countrylist.isEmpty())
            return;

        countrylist.add(getString(R.string.select_country));
        for (int i = 0; i < responseList.getCountryCitiesLst().size(); i++) {
            countrylist.add(String.valueOf(responseList.getCountryCitiesLst().get(i).getCountryObj().getTitle()));
        }
        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<String>(getMyContext(),
                R.layout.row_spinner_colored, countrylist);

//        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);
        country_spinner.setAdapter(categoryDataAdapter);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                if (country_spinner.getSelectedItemPosition() != 0) {
                    countryId = responseList.getCountryCitiesLst().get(country_spinner.getSelectedItemPosition() - 1).getCountryObj().getID();
                    citylist.clear();
                    getCityList(responseList);

//                    deliveryTime.setText(getString(R.string.delivery_time) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePeriod());
//                    shippingPrice.setText(getString(R.string.delivery_price) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePrice());

                } else {
                    countryId = 0;
                    citylist.clear();
                    citylist.add(getString(R.string.select_city));
                    city_spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void getCityList(SelectCountryCitiyListsResponseModel.Result responseList) {

        if (responseList != null) {
            citylist.add(getString(R.string.select_city));
            for (int x = 0; x < responseList.getCountryCitiesLst().
                    get(country_spinner.getSelectedItemPosition() - 1).getCitiesLst().size(); x++) {

                if (countryId == responseList.getCountryCitiesLst().get(country_spinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getCountryID()) {

                    citylist.add(responseList.getCountryCitiesLst().get(country_spinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getTitle());
                }
            }

            if (citylist.size() != 1) {
                ArrayAdapter<String> subCategoryDataAdapter = new ArrayAdapter<String>(getMyContext(),
                        R.layout.row_spinner_colored, citylist);

//                subCategoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);
                city_spinner.setAdapter(subCategoryDataAdapter);


                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // On selecting a spinner item
                        cityy_text.setVisibility(View.GONE);
                        if (city_spinner.getSelectedItemPosition() != 0) {
                            if (countryId == responseList.getCountryCitiesLst().get(country_spinner.getSelectedItemPosition() - 1).getCitiesLst().get(city_spinner.getSelectedItemPosition() - 1).getCountryID()) {

                                cityId = responseList.getCountryCitiesLst().get(country_spinner.getSelectedItemPosition() - 1)
                                        .getCitiesLst().get(city_spinner.getSelectedItemPosition() - 1).getID();

                            }

                        } else {
                            cityId = 0;
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
    }


    @Override
    public void returnPrice(double price) {
        if (pattenList == null)
            return;

        checkShippingPrice();
    }

    private void checkShippingPrice() {
        if (adapter == null || pattenList == null)
            return;

        if (adapter.getPiecesNumber() == 3) {
            double mainPrice;
            if (isInEgypt) {
                mainPrice = pattenList.getPrices().getLocal3PiecePrice();
            } else {
                mainPrice = pattenList.getPrices().getWorld3PiecePrice();
            }
            mainPriceHolder = mainPrice;
            priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice + " " + currency);
        } else if (adapter.getPiecesNumber() > 3) {
            double mainPrice;
            if (isInEgypt) {
                mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getLocalPiecePrice() + pattenList.getPrices().getLocal3PiecePrice();
            } else {
                mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getWorldPiecePrice() + pattenList.getPrices().getWorld3PiecePrice();
            }
            mainPriceHolder = mainPrice;
            priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice + " " + currency);
        } else {
            if (isInEgypt) {
//                mainPriceHolder = adapter.getPiecesNumber() * pattenList.getPrices().getLocalPiecePrice();
//                priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() * pattenList.getPrices().getLocalPiecePrice() + " " + getString(R.string.price_item_2));
                mainPriceHolder = pattenList.getPrices().getLocal3PiecePrice();
                priceTextView.setText(getString(R.string.price_item_1) + " " + mainPriceHolder + " " + currency);
            } else {
//                mainPriceHolder = adapter.getPiecesNumber() * pattenList.getPrices().getWorldPiecePrice();
//                priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() * pattenList.getPrices().getWorldPiecePrice() + " " + getString(R.string.price_item_2));
                mainPriceHolder = pattenList.getPrices().getWorld3PiecePrice();
                priceTextView.setText(getString(R.string.price_item_1) + " " + mainPriceHolder + " " + currency);
            }
        }
        if (isCouponCodeApplied) {
            applyCouponCode(true);
        }
    }

    @Override
    public void returnPrice(int piece) {
        pieces_number.setText(getString(R.string.no_item_1) + " " + piece + " " + getString(R.string.no_item_2));
    }

    private void showDeleteDialog(Context context, int index) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.delete_popup);
        mDialog.setCancelable(false);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            try {
                imageModelList.remove(index);
                photosList.remove(index);


                if (imageModelList.size() == 0) {
                    pickFromGallery(ShippingActivity.this);
                } else {
//                    adapter = new ShippingAdapter(this, imageModelList, this,
//                            this, this, this, this);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.removeViewAt(index);
//                    recyclerView.getAdapter().notifyItemRemoved(index);
//                    recyclerView.getAdapter().notifyItemRangeChanged(index, imageModelList.size());

//                    adapter.setList(imageModelList);
                    adapter.notifyDataSetChanged();

                    pieces_number.setText(getString(R.string.no_item_1) + " " + adapter.getPiecesNumber() + " " + getString(R.string.no_item_2));
                    returnPatternList(pattenList);

                    checkShippingPrice();
                }


            } catch (Exception ex) {
                //Exception may raise outOfBoundsException  when click on delete Twice
                ex.printStackTrace();
            }
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.close_popup).setOnClickListener(view -> {
            mDialog.dismiss();
        });
        mDialog.show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery(Activity context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false)
                    .setShowCamera(false)
                    .start(context);
        }
    }

    @Override
    public void onOneImageDelted(int index) {
        showDeleteDialog(ShippingActivity.this, index);
    }

    @Override
    public void onOneImageReplaced(int index) {

        Dialog mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.delete_all_popup);

        TextView customeTextViewBold2 = mDialog.findViewById(R.id.customeTextViewBold2);
        customeTextViewBold2.setText(getString(R.string.replace));

        TextView customeTextViewBold = mDialog.findViewById(R.id.customeTextViewBold);
        customeTextViewBold.setText(getString(R.string.confirm_replace));

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            mDialog.dismiss();
            replaceImage = true;
            replaceImageIndex = index;

            photosList.clear();
            for (ImageModel imageModel : imageModelList) {
                if (imageModel != null)
                    photosList.add(imageModel.getPath());
            }

            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setShowCamera(false)
                    .setPhotoCount(1)
                    .setRemoval(photosList)
                    .setPreviewEnabled(false)
                    .start(this);
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.show();
    }

    @Override
    public void itemClicked(int position) {

        try {
            Intent intent = new Intent(this, EditImageActivity.class);

            intent.putExtra("uri", imageModelList.get(position).getUri());
            intent.putExtra("index", position);

            if (imageModelList.get(position).getCurrentRatio() != 0) {
                intent.putExtra(Constants.EXTRA_ASPECT_RATIO_OPTIONS, imageModelList.get(position).getCurrentRatio());
            }

            startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageModelList.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
