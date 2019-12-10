package com.WattanArt.ui.ShippingForMobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Payment.WebviewActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.WattanArt.ui.mobileCase.BitmapMobileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ShippingMobileActivity extends BaseActivity implements ShippingMobileMvpView {
    ImageView mToolbarBackImageView;
    CustomeTextView submit;
    CustomeTextViewBold mToolbarTitleTextView;
    ImageView bitmap_image_view;
    Bitmap bmpMobile, bmpCover;
    String mobileName, coverName;
    Spinner country_spinner, city_spinner;
    CustomeTextView cityy_text;
    private List<String> countrylist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();
    int cityId, countryId;
    RadioButton onlineRadioButton;
    RadioButton cashRadioButton;
    CustomEditText addressEditText;
    CustomEditText couponEditText;
    CustomEditText phoneEditText;
    CustomeTextView checkCode;
    RadioGroup radioGroupPayment;
    int validCode = 0;
    int type = 1;
    boolean isInEgypt = false;
    String currency;
    MobileOrderRequest mobileOrderRequest;
    MobileOrderRequest.OrderBean orderBean;
    MobileOrderRequest.OrderDetilsBean orderDetilsBean;
    List<MobileOrderRequest.OrderDetilsBean> orderDetilsBeanList;
    List<MobileOrderRequest.OrderDetilsBean.ImagesBean> images;
    MobileOrderRequest.OrderDetilsBean.ImagesBean imagesBean;
    @Inject
    ShippingMobileMvpPresenter<ShippingMobileMvpView> mPresenter;

    public static final int REQUEST_STORAGE_WRITE_PERMISSION = 11;

    ValidationTool validationTool;
    UserData userData;

    boolean isCouponCodeApplied = false;
    boolean hasCouponChecked = false;
    String color, style;

    Intent intentforData;
    public static final int OPEN_REGISTERATION_CODE_FROM_SHOPPING_MOBILE = 102;

    String filename, filename2;
    File fileForMobile, fileForCover;

    ArrayList<String> photos;
    String photo;
    String mobileId;
    TextView shippingPrice, deliveryTime;
    ImageView minusIV, plusIV;
    CustomeTextView quantityTv, pieces_number, priceTextView;
    int quantity_Tv = 1;
    String priceIn, priceOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_mobile);
        ButterKnife.bind(this);

        photos = new ArrayList<>();
        userData = new UserData();
        mobileOrderRequest = new MobileOrderRequest();
        orderBean = new MobileOrderRequest.OrderBean();
        orderDetilsBean = new MobileOrderRequest.OrderDetilsBean();
        images = new ArrayList<>();
        orderDetilsBeanList = new ArrayList<>();
        onlineRadioButton = findViewById(R.id.onlineRadioButton);
        cashRadioButton = findViewById(R.id.cashRadioButton);
        addressEditText = findViewById(R.id.addressEditText);
        couponEditText = findViewById(R.id.couponEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        checkCode = findViewById(R.id.checkCode);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);

        deliveryTime = findViewById(R.id.deliveryTime);
        shippingPrice = findViewById(R.id.shippingPrice);

        minusIV = findViewById(R.id.minusIV);
        plusIV = findViewById(R.id.plusIV);
        quantityTv = findViewById(R.id.quantityTv);
        pieces_number = findViewById(R.id.pieces_number);
        priceTextView = findViewById(R.id.priceTextView);


        imagesBean = new MobileOrderRequest.OrderDetilsBean.ImagesBean();
        validationTool = new ValidationTool(this);
        initViews();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        try {
            isInEgypt = UtilitiesManager.getUserCountry(this).toLowerCase().equals("eg");
//            Toast.makeText(this,isInEgypt+"",Toast.LENGTH_LONG).show();
            Log.d("isInEgypt", isInEgypt + UtilitiesManager.getUserCountry(this));
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

        ViewHelper.hideKeyboard(this);

        checkPaymentMethod();
        initToolbar();
        getData();
        returnBitmapFromHelper();

        orderDetilsBean.setQuantiy(1);
        pieces_number.setText(getString(R.string.no_item_1) + " " + orderDetilsBean.getQuantiy() + " " + getString(R.string.no_item_2));
        actionPlus();
        actionMinus();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    void initViews() {
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }

        mToolbarBackImageView = findViewById(R.id.toolbar_image_view);
        mToolbarTitleTextView = findViewById(R.id.toolbar_tv_title);
        submit = findViewById(R.id.submit);
        bitmap_image_view = findViewById(R.id.bitmap_image_view);
        country_spinner = findViewById(R.id.country_spinner);
        city_spinner = findViewById(R.id.city_spinner);
        cityy_text = findViewById(R.id.defualt_cityy_text);

        if (isNetworkConnected()) {
            mPresenter.getShippingPrices();
        } else {
            Toast.makeText(ShippingMobileActivity.this, getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void initToolbar() {
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.title_shipping_order);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void returnBitmapFromHelper() {
        if (BitmapMobileHelper.getInstance().getBitmapMobile() != null) {
            saveTempBitmapForMobile(BitmapMobileHelper.getInstance().getBitmapMobile());
            bitmap_image_view.setImageBitmap(BitmapMobileHelper.getInstance().getBitmapMobile());
        }
        if (BitmapMobileHelper.getInstance().getBitmapCover() != null) {
//            bitmap_image_view.setImageBitmap(BitmapMobileHelper.getInstance().getBitmapCover());
            saveTempBitmapForCover(BitmapMobileHelper.getInstance().getBitmapCover());
        }
    }


    public void getData() {
        photo = getIntent().getStringExtra("photos");
        photos.add(photo);
        Log.e("photosggg", photos + "");
        if (getIntent().hasExtra("color")) {
            color = getIntent().getStringExtra("color");
            Log.e("colorrrr", color);
        }
        if (getIntent().hasExtra("style")) {
            style = getIntent().getStringExtra("style");
            Log.e("styleeee", style);
        }
        if (getIntent().hasExtra("mobileId")) {
            mobileId = getIntent().getStringExtra("mobileId");
            Log.e("mobileId", mobileId);
        }
        if (getIntent().hasExtra("priceIn")) {
            priceIn = getIntent().getStringExtra("priceIn");
            Log.e("priceIn", priceIn);
        }
        if (getIntent().hasExtra("priceOut")) {
            priceOut = getIntent().getStringExtra("priceOut");
            Log.e("priceOut", priceOut);
        }
        if (isInEgypt){
            priceTextView.setText(priceIn);
        }else {
            priceTextView.setText(priceOut);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
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

                    deliveryTime.setVisibility(View.VISIBLE);
                    shippingPrice.setVisibility(View.VISIBLE);
                    deliveryTime.setText(getString(R.string.delivery_time) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePeriod());
                    shippingPrice.setText(getString(R.string.delivery_price) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePrice()+" "+currency);

                    if (country_spinner.getSelectedItemPosition()==1){
                        isInEgypt=true;
                        if (isInEgypt)
                            currency = getString(R.string.le);
                        else
                            currency = getString(R.string.dolar);
                            cashRadioButton.setEnabled(true);
                            cashRadioButton.setChecked(true);
                            onlineRadioButton.setChecked(false);
                            type = 1;
                        CalculatePrice();
                        shippingPrice.setText(getString(R.string.delivery_price) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePrice()+" "+currency);

                    }else {
                        isInEgypt=false;
                        if (isInEgypt)
                            currency = getString(R.string.le);
                        else
                            currency = getString(R.string.dolar);
                        cashRadioButton.setEnabled(false);
                        onlineRadioButton.setChecked(true);
                        cashRadioButton.setChecked(false);
                        type = 2;
                        CalculatePrice();
                        shippingPrice.setText(getString(R.string.delivery_price) + " " + responseList.getCountryCitiesLst().get(i - 1).getCountryObj().getChargePrice()+" "+currency);
                    }

                } else {
                    if (country_spinner.getSelectedItemPosition() == 0) {
                        deliveryTime.setVisibility(View.GONE);
                        shippingPrice.setVisibility(View.GONE);
                        CalculatePrice();
                    }
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
    public void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList) {
        getCountryList(patternTypeEntityList);
    }

    @Override
    public void returnFromMakeOrder(MobileOrderResponse mobileOrderResponse) {
        Toast.makeText(this, getString(R.string.done_succsess) + "", Toast.LENGTH_SHORT).show();
        if (BitmapMobileHelper.getInstance() != null) {
            BitmapMobileHelper.getInstance().clearInstance();
            Log.e("clearrr", "clearrr");
        }
        navigateFromOrderId(mobileOrderResponse.getResult());
    }

    @Override
    public void applyCouponCode(boolean isValid) {
        Log.e("isValidsss", isValid + "");
        isValid = true;
        hasCouponChecked = isValid;
        if (!couponEditText.getText().toString().isEmpty()) {
            if (isValid) {
                orderBean.setCouponCode(couponEditText.getText().toString());
                orderBean.setValidCouponCode(true);
                Toast.makeText(this, getString(R.string.code_verification_ok) + "", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.code_verification_no) + "", Toast.LENGTH_SHORT).show();
                orderBean.setCouponCode("");
                orderBean.setValidCouponCode(false);
            }

        } else {
            orderBean.setCouponCode("");
            orderBean.setValidCouponCode(false);
        }
    }

    @Override
    public void applyAfterLogin() {
        completeOrder();
    }

    @Override
    public void returnUploadedImageForMobile(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromMobile", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            mobileName = imageUploadResponseModel.getFileName();
            orderDetilsBean.setMainImage(mobileName);
            if (mobileName != null) {
                imagesBean.setPrintscreenImg(mobileName);
            }
            imagesBean.setStyle(style);
            imagesBean.setColor(color);
//            images.add(imagesBean);
//            mobileOrderRequest.setOrderDetils(orderDetilsBeanList);
            mPresenter.returnUploadedImageForCover(photos, fileForCover);
        }
    }

    @Override
    public void returnUploadedImageForCover(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            coverName = imageUploadResponseModel.getFileName();
            if (coverName != null) {
                imagesBean.setImage(coverName);
            }
//            images.add(imagesBean);
            mPresenter.getOrderSubmit(mobileOrderRequest);
        }
    }

    @Override
    public void showLoadingInner() {
        showLoaderDialog();
    }

    @Override
    public void hideLoadingInner() {
        hideLoaderDialog();
    }


    private void completeOrder() {
        if (checkWriteStoragePermission())
            if (spinnerValid(country_spinner, countryId) && spinnerValid(city_spinner, cityId)
                    && validationTool.validateField(addressEditText, getString(R.string.verify_address))
                    && validationTool.validatePhone(this , phoneEditText)) {


                if (userData.getUserID(this) == null || userData.getUserID(this).isEmpty()) {
                    Toast.makeText(this, getString(R.string.login_first), Toast.LENGTH_LONG).show();

                    mPresenter.showLoginPopup(this, hasCouponChecked, couponEditText);

                } else {

                    fillOrderRequest();

                    if (couponEditText.getText().toString().isEmpty()) {
                        // no copoun code used
                        if (mobileOrderRequest != null) {
                            mPresenter.returnUploadedImageForMobile(photos, fileForMobile);
                        }
                    } else if (!hasCouponChecked) {
                        Toast.makeText(this, getString(R.string.check_code), Toast.LENGTH_LONG).show();
                    } else {
                        mPresenter.returnUploadedImageForMobile(photos, fileForMobile);
                    }
                }
            }
    }

    public boolean checkWriteStoragePermission() {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(ShippingMobileActivity.this, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Log.e("asdsad", "adasdsad");
                ActivityCompat.requestPermissions(ShippingMobileActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_WRITE_PERMISSION);

            }
        }
        return writeStoragePermissionGranted;
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

    public void completeOrder(View view) {
        completeOrder();
    }

    public void checkCodeAction() {
        if (couponEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.text_code_empty), Toast.LENGTH_SHORT).show();
        } else if (userData.getUserID(ShippingMobileActivity.this).isEmpty()) {
            Toast.makeText(this, getString(R.string.login_before), Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkConnected()) {
                CouponCodeModel couponCodeModel = new CouponCodeModel();
                couponCodeModel.setCouponCode(couponEditText.getText().toString().trim());
                couponCodeModel.setUserID(userData.getUserID(ShippingMobileActivity.this));
                mPresenter.checkCouponCode(couponCodeModel);
            } else {
                Toast.makeText(this, getString(R.string.error_no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkCode(View view) {
        checkCodeAction();
    }

    public void checkPaymentMethod() {

        radioGroupPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cashRadioButton) {
                type = 1;
            } else {
                type = 2;
            }
        });

        if (userData.getPhone(this).isEmpty()) {
            phoneEditText.setText("");
            phoneEditText.setClickable(true);
        } else {
            phoneEditText.setText(userData.getPhone(this));
            phoneEditText.setClickable(false);
            phoneEditText.setFocusable(false);
        }
    }

    public void navigateFromOrderId(int orderId) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_REGISTERATION_CODE_FROM_SHOPPING_MOBILE && resultCode == RESULT_OK) {

            if (couponEditText.getText().toString().isEmpty()) {
                hasCouponChecked = true;
                Log.e("fromActivityResulyt", "fromActivityResulyt");
            }
            if (hasCouponChecked) {
                completeOrder();
                Log.e("fromActivityResulyt", "fromActivityResulyt");
            } else {
                Toast.makeText(this, getString(R.string.check_code), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void fillOrderRequest() {


        orderBean.setAddress1(addressEditText.getText().toString());
        orderBean.setPhoneNumber(phoneEditText.getText().toString());
        orderBean.setBuytype(type);
        orderBean.setInEgypt(isInEgypt);
        orderBean.setZipcode("tfyrtyr");
        orderBean.setUserID(userData.getUserID(this));
        orderBean.setCouponCode("");
        orderBean.setOrderType(1);
        orderBean.setCityID(cityId);
        orderBean.setCountryID(countryId);
        mobileOrderRequest.setOrder(orderBean);

        if (mobileId != null)
            orderDetilsBean.setProductID(Integer.parseInt(mobileId));


        if (color != null) {
            imagesBean.setColor(color);
        }
        if (style != null) {
            imagesBean.setStyle(style);
        }
        imagesBean.setDesignerFlag(0);
        imagesBean.setDesignerID("0");
        images.add(imagesBean);
        orderDetilsBean.setImages(images);


        orderDetilsBeanList.add(orderDetilsBean);
        mobileOrderRequest.setOrderDetils(orderDetilsBeanList);

    }


    public void saveTempBitmapForMobile(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForMobile(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForCover(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCover(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public File saveImageForMobile(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename = timeStamp + ".jpg";

        fileForMobile = new File(myDir, filename);

        if (fileForMobile.exists()) fileForMobile.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForMobile);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForMobile + "");
//            saveImageForMobileForSending(finalBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForMobile;
    }

    public File saveImageForCover(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images2");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename2 = timeStamp + ".jpg";

        fileForCover = new File(myDir, filename2);


        if (fileForCover.exists()) fileForCover.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCover);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCover + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCover;
    }


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public void actionPlus(){
        plusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusImg();
            }
        });
    }
    public void actionMinus(){
        minusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusImg();
            }
        });
    }

    public void plusImg() {
        if (orderDetilsBean.getQuantiy() >= 1) {
            quantityTv.setText(orderDetilsBean.getQuantiy() + 1 + "");
            orderDetilsBean.setQuantiy(orderDetilsBean.getQuantiy() + 1);
            pieces_number.setText(getString(R.string.no_item_1) + " " + orderDetilsBean.getQuantiy() + " " + getString(R.string.no_item_2));
            CalculatePrice();
        } else {
        }
    }

    public void minusImg() {
        if (orderDetilsBean.getQuantiy() > 1) {
            if (orderDetilsBean.getQuantiy() != 1) {
                orderDetilsBean.setQuantiy(orderDetilsBean.getQuantiy() - 1);
                quantityTv.setText(orderDetilsBean.getQuantiy() + "");
                pieces_number.setText(getString(R.string.no_item_1) + " " + orderDetilsBean.getQuantiy() + " " + getString(R.string.no_item_2));
                CalculatePrice();
            }
        }
    }

    public void CalculatePrice(){
        if (isInEgypt) {
            priceTextView.setText(Double.parseDouble(priceIn)*orderDetilsBean.getQuantiy()+" "+currency);
        }else {
            priceTextView.setText(Double.parseDouble(priceOut)*orderDetilsBean.getQuantiy()+" "+currency);
        }
    }
}
