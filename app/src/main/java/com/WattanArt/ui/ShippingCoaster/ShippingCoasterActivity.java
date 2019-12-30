package com.WattanArt.ui.ShippingCoaster;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.WattanArt.Utils.widgets.CircleImageView;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.Coaster.BitmapCoasterHelper;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Payment.WebviewActivity;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.ShippingForMobile.MobileOrderResponse;
import com.WattanArt.ui.ShippingT_Shirt.ShippingT_ShirtActivity;
import com.WattanArt.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.WattanArt.ui.ShippingForMobile.ShippingMobileActivity.REQUEST_STORAGE_WRITE_PERMISSION;

public class ShippingCoasterActivity extends BaseActivity implements ShippingCoasterMvpView {


    public static final int OPEN_REGISTERATION_CODE_FROM_SHOPPING_FLASH= 103;
    ImageView mToolbarBackImageView;
    CustomeTextView submit;
    CustomeTextViewBold mToolbarTitleTextView;
    CircleImageView bitmap_image_view_front,bitmap_image_view_back;
    Bitmap bmpFront, bmpCoverFront ,bmpBack, bmpCoverBack;
    String imageFrontName, imageCoverFrontName ,imageBackName , imageCoverBackName;
    Spinner country_spinner,city_spinner;
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
    int validCode=0;
    int type = 1;
    boolean isInEgypt = false;
    String currency;
    MobileOrderRequest mobileOrderRequest;
    MobileOrderRequest.OrderBean orderBean;
    MobileOrderRequest.OrderDetilsBean orderDetilsBean;
    List<MobileOrderRequest.OrderDetilsBean> orderDetilsBeanList;
    List<MobileOrderRequest.OrderDetilsBean.ImagesBean> images;
    MobileOrderRequest.OrderDetilsBean.ImagesBean imagesBean,imagesBean2;
    String filename, filename2,filename3,filename4;
    File fileForFront, fileForCoverFront ,fileForBack ,fileForCoverBack;
    ArrayList<String> photosFront,photosBack;
    String photoFront,photoBack;
    @Inject
    ShippingCoasterMvpPresenter<ShippingCoasterMvpView> mPresenter;

    ValidationTool validationTool;
    UserData userData;

    boolean isCouponCodeApplied = false;
    boolean hasCouponChecked=false;
    String color,style;
    String prod_Id;

    Intent intentforData ;
    String styleNameFront,styleNameBack,colorNameFront,colorNameback;
    TextView shippingPrice, deliveryTime;

    ImageView minusIV, plusIV;
    CustomeTextView quantityTv, pieces_number, priceTextView;
    int quantity_Tv = 1;
    String priceIn, priceOut;

    String prod_id_circle,prod_id_square;
    String  price_in_circle ,price_out_circle;
    String   price_in_square ,price_out_square;
    String Coaster_circle,Coaster_square;
    String cateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_coaster);

        ButterKnife.bind(this);


        photosFront=new ArrayList<>();
        photosBack=new ArrayList<>();
        userData = new UserData();
        mobileOrderRequest=new MobileOrderRequest();
        orderBean=new MobileOrderRequest.OrderBean();
        orderDetilsBean=new MobileOrderRequest.OrderDetilsBean();
        images=new ArrayList<>();
        orderDetilsBeanList=new ArrayList<>();
        onlineRadioButton=findViewById(R.id.onlineRadioButton);
        cashRadioButton=findViewById(R.id.cashRadioButton);
        addressEditText=findViewById(R.id.addressEditText);
        couponEditText=findViewById(R.id.couponEditText);
        phoneEditText=findViewById(R.id.phoneEditText);
        checkCode=findViewById(R.id.checkCode);
        radioGroupPayment=findViewById(R.id.radioGroupPayment);
        deliveryTime = findViewById(R.id.deliveryTime);
        shippingPrice = findViewById(R.id.shippingPrice);

        minusIV = findViewById(R.id.minusIV);
        plusIV = findViewById(R.id.plusIV);
        quantityTv = findViewById(R.id.quantityTv);
        pieces_number = findViewById(R.id.pieces_number);
        priceTextView = findViewById(R.id.priceTextView);

        imagesBean=new MobileOrderRequest.OrderDetilsBean.ImagesBean();
        validationTool=new ValidationTool(this);
        initViews();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        try {
            isInEgypt = UtilitiesManager.getUserCountry(this).toLowerCase().equals("eg");
            Log.d("isInEgypt",isInEgypt+UtilitiesManager.getUserCountry(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isInEgypt)
            currency = getString(R.string.le);
        else
            currency = getString(R.string.dolar);



        if (!isInEgypt) {
            cashRadioButton.setEnabled(false);
            onlineRadioButton.setChecked(true);
            type = 2;
        }

        ViewHelper.hideKeyboard(this);

        checkPaymentMethod();
        initToolbar();
        getData();
        getPhotosFrontBack();
//        getData();
        returnBitmapFromHelper();

        orderDetilsBean.setQuantiy(1);
        pieces_number.setText(getString(R.string.no_item_1) + " " + orderDetilsBean.getQuantiy() + " " + getString(R.string.no_item_2));
        actionPlus();
        actionMinus();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    void initViews(){
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }

        mToolbarBackImageView=findViewById(R.id.toolbar_image_view);
        mToolbarTitleTextView=findViewById(R.id.toolbar_tv_title);
        submit=findViewById(R.id.submit);
        bitmap_image_view_front=findViewById(R.id.bitmap_image_view_front);
        country_spinner=findViewById(R.id.country_spinner);
        city_spinner=findViewById(R.id.city_spinner);
        cityy_text=findViewById(R.id.defualt_cityy_text);

        if (isNetworkConnected()) {
            mPresenter.getShippingPrices();
        } else {
            Toast.makeText(ShippingCoasterActivity.this, getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void initToolbar(){
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.title_shipping_order);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    public void returnBitmapFromHelper(){
        if (BitmapCoasterHelper.getInstance().getBitmapCoasterCircle()!=null){
            saveTempBitmapForFront(BitmapCoasterHelper.getInstance().getBitmapCoasterCircle());
            Log.e("BitmapCoasterHelper",BitmapCoasterHelper.getInstance().getBitmapCoasterCircle()+"");
            bitmap_image_view_front.setImageBitmap(BitmapCoasterHelper.getInstance().getBitmapCoasterCircle());
        }
        if (BitmapCoasterHelper.getInstance().getBitmapCoasterCircleCover()!=null){
            saveTempBitmapForCoverFront(BitmapCoasterHelper.getInstance().getBitmapCoasterCircleCover());
        }
        if (BitmapCoasterHelper.getInstance().getBitmapCoasterSquare()!=null){
            saveTempBitmapForBack(BitmapCoasterHelper.getInstance().getBitmapCoasterSquare());
        }
        if (BitmapCoasterHelper.getInstance().getBitmapCoasterSquareCover()!=null){
            saveTempBitmapForBackCover(BitmapCoasterHelper.getInstance().getBitmapCoasterSquareCover());
        }
    }

    public void getPhotosFrontBack(){
        photoFront = getIntent().getStringExtra("photosFront");
        photosFront.add(photoFront);
        Log.e("photosggg",photosFront+"");
        photoBack = getIntent().getStringExtra("photosBack");
        photosBack.add(photoBack);
        Log.e("photosBack",photosBack+"");
    }

    public void getData(){

        if (getIntent().hasExtra("styleNameFront")){
            styleNameFront=getIntent().getStringExtra("styleNameFront");
            Log.e("styleNameFront",styleNameFront);
        }
        if (getIntent().hasExtra("styleNameBack")){
            styleNameBack=getIntent().getStringExtra("styleNameBack");
            Log.e("styleNameBack",styleNameBack);
        }
        if (getIntent().hasExtra("colorNameFront")){
            colorNameFront=getIntent().getStringExtra("colorNameFront");
            Log.e("colorNameFront",colorNameFront);
        }
        if (getIntent().hasExtra("colorNameback")){
            colorNameback=getIntent().getStringExtra("colorNameback");
            Log.e("colorNameback",colorNameback);
        }
        if (getIntent().hasExtra("catId")){
            cateId=getIntent().getStringExtra("catId");
            Log.e("cateId",cateId+"");
        }
        if (getIntent().hasExtra("prod_id_circle")){
            prod_id_circle=getIntent().getStringExtra ("prod_id_circle");
            Log.e("prod_id_circle",prod_id_circle+"");
        }
        if (getIntent().hasExtra("Coaster_circle")){
            Coaster_circle=getIntent().getStringExtra("Coaster_circle");
            Log.e("Coaster_circle",Coaster_circle+"");
        }
        if (getIntent().hasExtra("price_in_circle")){
            price_in_circle=getIntent().getStringExtra("price_in_circle");
            Log.e("price_in_circle",price_in_circle+"");
        }
        if (getIntent().hasExtra("price_out_circle")){
            price_out_circle=getIntent().getStringExtra("price_out_circle");
            Log.e("price_out_circle",price_out_circle+"");
        }

        if (getIntent().hasExtra("prod_id_square")){
            prod_id_square=getIntent().getStringExtra("prod_id_square");
            Log.e("prod_id_square",prod_id_square+"");
        }
        if (getIntent().hasExtra("Coaster_square")){
            Coaster_square=getIntent().getStringExtra("Coaster_square");
            Log.e("Coaster_square",Coaster_square+"");
        }
        if (getIntent().hasExtra("price_in_square")){
            price_in_square=getIntent().getStringExtra("price_in_square");
            Log.e("price_in_square",price_in_square+"");
        }
        if (getIntent().hasExtra("price_out_square")){
            price_out_square=getIntent().getStringExtra("price_out_square");
            Log.e("price_out_square",price_out_square+"");
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
                    if (country_spinner.getSelectedItemPosition()==0){
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
        Toast.makeText(this, getString(R.string.done_succsess)+"", Toast.LENGTH_SHORT).show();
        if (BitmapCoasterHelper.getInstance()!=null){
            BitmapCoasterHelper.getInstance().clearInstance();
            Log.e("clearrr","clearrr");
        }
        navigateFromOrderId(mobileOrderResponse.getResult());
    }

    @Override
    public void applyCouponCode(boolean isValid) {
        Log.e("isValidsss",isValid+"");
        isValid=true;
        hasCouponChecked=isValid;
        if (!couponEditText.getText().toString().isEmpty()) {
            if (isValid) {
                orderBean.setCouponCode(couponEditText.getText().toString());
                orderBean.setValidCouponCode(true);
                Toast.makeText(this, getString(R.string.code_verification_ok)+"", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.code_verification_no)+"", Toast.LENGTH_SHORT).show();
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
    public void returnUploadedImageForFront(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFont", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            imageFrontName = imageUploadResponseModel.getFileName();
//            orderDetilsBean.setMainImage(imageFrontName);
            imagesBean.setDesignerFlag(0);
            imagesBean.setDesignerID("0");
            imagesBean.setStyle(styleNameFront);
            imagesBean.setColor(colorNameFront);
            imagesBean.setPrintscreenImg(imageFrontName);
            orderDetilsBean.setMainImage(imageFrontName);
            if (imageFrontName !=null){
                imagesBean.setPrintscreenImg(imageFrontName);
            }
            orderDetilsBean.setImages(images);
            mPresenter.returnUploadedImageForCoverFront(photosFront, fileForCoverFront);
        }
    }

    @Override
    public void returnUploadedImageForCoverFront(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            imageCoverFrontName = imageUploadResponseModel.getFileName();
            if (imageCoverFrontName !=null){
                imagesBean.setImage(imageCoverFrontName);
            }
            images.add(imagesBean);
            mPresenter.returnUploadedImageForBack(photosFront, fileForBack);
        }
    }

    @Override
    public void returnUploadedImageForBack(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromBack", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            imageBackName = imageUploadResponseModel.getFileName();
            imagesBean2=new MobileOrderRequest.OrderDetilsBean.ImagesBean();
            imagesBean2.setDesignerFlag(1);
            imagesBean2.setDesignerID("1");
            imagesBean2.setStyle(styleNameBack);
            imagesBean2.setColor(colorNameback);
            if (imageBackName !=null){
                imagesBean2.setPrintscreenImg(imageBackName);
            }
            orderDetilsBean.setImages(images);
            mPresenter.returnUploadedImageForCoverBack(photosFront, fileForCoverBack);
        }
    }

    @Override
    public void returnUploadedImageForCoverBack(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            imageCoverBackName = imageUploadResponseModel.getFileName();
            if (imageCoverBackName !=null){
                imagesBean2.setImage(imageCoverBackName);
            }
            images.add(imagesBean2);
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
//                    && validationTool.validatePhone(phoneEditText, getString(R.string.invalid_phone))) {
                    && validationTool.validatePhone(this ,phoneEditText)) {


                if (userData.getUserID(this) == null || userData.getUserID(this).isEmpty()) {
                    Toast.makeText(this, getString(R.string.login_first), Toast.LENGTH_LONG).show();

                    mPresenter.showLoginPopup(this, hasCouponChecked,couponEditText);

                } else {

                    fillOrderRequest();

                    if (couponEditText.getText().toString().isEmpty()) {
                        // no copoun code used
                        if (mobileOrderRequest != null) {
                            Log.e("fileForFront",fileForFront+"");
                            mPresenter.returnUploadedImageForFront(photosFront, fileForFront);
                        }
                    } else if (!hasCouponChecked) {
                        Toast.makeText(this, getString(R.string.check_code), Toast.LENGTH_LONG).show();
                    } else {
                        mPresenter.returnUploadedImageForFront(photosFront, fileForFront);
                    }
                }
            }
    }

    public boolean checkWriteStoragePermission() {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(ShippingCoasterActivity.this, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Log.e("asdsad", "adasdsad");
                ActivityCompat.requestPermissions(ShippingCoasterActivity.this,
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

    public void checkCodeAction(){
        if (couponEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.text_code_empty), Toast.LENGTH_SHORT).show();
        } else if (userData.getUserID(ShippingCoasterActivity.this).isEmpty()) {
            Toast.makeText(this, getString(R.string.login_before), Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkConnected()) {
                CouponCodeModel couponCodeModel = new CouponCodeModel();
                couponCodeModel.setCouponCode(couponEditText.getText().toString().trim());
                couponCodeModel.setUserID(userData.getUserID(ShippingCoasterActivity.this));
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

        if (userData.getPhone(this).isEmpty()){
            phoneEditText.setText("");
//            phoneEditText.setClickable(true);
        }else {
            phoneEditText.setText(userData.getPhone(this));
//            phoneEditText.setClickable(false);
//            phoneEditText.setFocusable(false);
        }
    }

    public void navigateFromOrderId(int orderId){
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
        if (requestCode == OPEN_REGISTERATION_CODE_FROM_SHOPPING_FLASH && resultCode == RESULT_OK) {

            if (couponEditText.getText().toString().isEmpty()){
                hasCouponChecked=true;
                Log.e("fromActivityResulyt","fromActivityResulyt");
            }
            if (hasCouponChecked){
                completeOrder();
                Log.e("fromActivityResulyt","fromActivityResulyt");
            }else {
                Toast.makeText(this,getString(R.string.check_code),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void fillOrderRequest(){
        orderBean.setCountryID(countryId);
        orderBean.setCityID(cityId);
        orderBean.setAddress1(addressEditText.getText().toString());
        orderBean.setPhoneNumber(phoneEditText.getText().toString());
        orderBean.setBuytype(type);
        orderBean.setInEgypt(isInEgypt);
        orderBean.setZipcode("tfyrtyr");
        orderBean.setUserID(userData.getUserID(this));
        orderBean.setCouponCode("");
        orderBean.setOrderType(1);
        mobileOrderRequest.setOrder(orderBean);

        Log.e("prod_id_circlebb",prod_id_circle+"");
        if (prod_id_circle!=null)
            orderDetilsBean.setProductID(Integer.parseInt(prod_id_circle));
//        orderDetilsBean.setQuantiy(1);
        orderDetilsBeanList.add(orderDetilsBean);
        mobileOrderRequest.setOrderDetils(orderDetilsBeanList);
    }




    public void saveTempBitmapForFront(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForFront(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForCoverFront(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCoverFront(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForBack(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForBack(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForBackCover(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCoverBack(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public File saveImageForFront(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images1");
        myDir.mkdirs();

        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename = timeStamp + ".jpg";

        fileForFront = new File(myDir, filename);

        if (fileForFront.exists()) fileForFront.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForFront);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForFront + "");
//            saveImageForMobileForSending(finalBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForFront;
    }

    public File saveImageForCoverFront(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images2");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename2 = timeStamp + ".jpg";

        fileForCoverFront = new File(myDir, filename2);

        if (fileForCoverFront.exists()) fileForCoverFront.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCoverFront);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCoverFront + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCoverFront;
    }


    public File saveImageForBack(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images3");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename3 = timeStamp + ".jpg";

        fileForBack = new File(myDir, filename3);

        if (fileForBack.exists()) fileForBack.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForBack);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForBack + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForBack;
    }


    public File saveImageForCoverBack(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images4");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename4 = timeStamp + ".jpg";

        fileForCoverBack = new File(myDir, filename4);

        if (fileForCoverBack.exists()) fileForCoverBack.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCoverBack);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCoverBack + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCoverBack;
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
            priceTextView.setText(Double.parseDouble(price_in_circle)*orderDetilsBean.getQuantiy()+" "+currency);
        }else {
            priceTextView.setText(Double.parseDouble(price_out_circle)*orderDetilsBean.getQuantiy()+" "+currency);
        }
    }
}
