package com.WattanArt.ui.ShippingForMobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.WattanArt.ui.Shipping.ShippingMvpView;
import com.WattanArt.ui.base.BaseActivity;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ShippingMobileActivity extends BaseActivity implements ShippingMobileMvpView {
    ImageView mToolbarBackImageView;
    CustomeTextView submit;
    CustomeTextViewBold mToolbarTitleTextView;
   ImageView bitmap_image_view;
    Bitmap bmp;
    String mobileName,coverName;
    Spinner country_spinner,city_spinner;
    CustomeTextView cityy_text;
    private List<String> countrylist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();
    int cityId, countryId;

    @Inject
    ShippingMobileMvpPresenter<ShippingMobileMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_mobile);
        ButterKnife.bind(this);

       initViews();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ViewHelper.hideKeyboard(this);

        initToolbar();
        getImage();
        getData();

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
        bitmap_image_view=findViewById(R.id.bitmap_image_view);
        country_spinner=findViewById(R.id.country_spinner);
        city_spinner=findViewById(R.id.city_spinner);
        cityy_text=findViewById(R.id.defualt_cityy_text);

        if (isNetworkConnected()) {
            mPresenter.getShippingPrices();
        } else {
            Toast.makeText(ShippingMobileActivity.this, getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
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

    public void getImage(){
        bmp = null;
        String filename = getIntent().getStringExtra("image");
        Log.e("filename",filename+"");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);

            Log.e("bmoImage",bmp+"");
            is.close();
            bitmap_image_view.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        Intent intent=getIntent();
        if (intent.hasExtra("mobileName")){
            mobileName=intent.getStringExtra("mobileName");
            Log.e("mobileName",mobileName);
        }
        if (intent.hasExtra("coverName")){
            coverName=intent.getStringExtra("coverName");
            Log.e("coverName",coverName);
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
    public void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList) {
        getCountryList(patternTypeEntityList);
    }


}
