package com.WattanArt.ui.Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeButton;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.Response.TermsResposeModel;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Login.LoginActivity;
import com.WattanArt.ui.Terms.TermsActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity implements
        RegisterMvpView, View.OnClickListener {

    @Inject
    RegisterMvpPresenter<RegisterMvpView> mPresenter;

    @BindView(R.id.register_full_name)
    CustomEditText mFullNameEditText;

    @BindView(R.id.register_mail)
    CustomEditText mEmailEditText;

    @BindView(R.id.register_password)
    CustomEditText mPasswordEditText;

    @BindView(R.id.register_confirm_password)
    CustomEditText mConfirmPasswordEditText;

    @BindView(R.id.register_phone)
    CustomEditText mPhoneEditText;

    @BindView(R.id.register_country_spinner)
    Spinner mCountrySpinner;

    @BindView(R.id.register_city_spinner)
    Spinner mCitySpinner;
    @BindView(R.id.register_sign_up)
    CustomeButtonBold mSignUpButton;

    @BindView(R.id.terms)
    CustomeTextViewBold terms;

    @BindView(R.id.fb_btn)
    CustomeButtonBold mFacebookButton;

    @BindView(R.id.defualt_city_text)
    CustomeTextView city_text;

    @BindView(R.id.defult_country)
    CustomeTextView country_text;

    @BindView(R.id.icon_back_iv)
    ImageView mIconBackImageView;

    @BindView(R.id.myScrollView)
    ScrollView myScrollView;


    @BindView(R.id.bg_linear)
    LinearLayout bg_linear;

//    @BindView(R.id.mainLayout)
//    RelativeLayout bg_main;

    private List<String> countrylist;
    private List<String> citylist;
    int cityId, countryId;
    int registraionType = Constants.NORMAL_REGISTER;
    String facebookID;
    UserData userData;
    private ValidationTool validationTool;

    boolean cameFromShippingActivity = false;
    boolean cameFromShippingMobileActivity = false;
    boolean cameFromShippingFlashActivity = false;
    boolean cameFromPublicShipping = false;

    public static final String ShippingRegister = "came_from_shipping";
    public static final String ShippingMobileRegister = "came_from_shipping_mobile";
    public static final String ShippingFlashRegister = "came_from_shipping_flash";
    public static final String publicShipping = "came_from_public_shipping";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        countrylist = new ArrayList<>();
        citylist = new ArrayList<>();
        userData = new UserData();


        //indicate whether i came from login screen or from dialog in shipping presenter
        if (getIntent() != null && getIntent().hasExtra(ShippingRegister)) {
            Log.e("ShippingRegister",ShippingRegister);
            cameFromShippingActivity = true;
        }

        if (getIntent() != null && getIntent().hasExtra(publicShipping)) {
            Log.e("publicShipping",publicShipping);
            cameFromPublicShipping = true;
        }

        if (getIntent() != null && getIntent().hasExtra(ShippingMobileRegister)) {
            Log.e("ShippingMobileRegister",ShippingMobileRegister);
            cameFromShippingMobileActivity = true;
        }

        if (getIntent() != null && getIntent().hasExtra(ShippingFlashRegister)) {
            Log.e("ShippingFlashRegister",ShippingFlashRegister);
            cameFromShippingFlashActivity = true;
        }

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        ButterKnife.bind(this);
        mSignUpButton.setOnClickListener(this);
        terms.setOnClickListener(this);
        mFacebookButton.setOnClickListener(this);
        validationTool = new ValidationTool(getMyContext());
        if (isNetworkConnected()) {

            mPresenter.getResponsOfCountryCityList(userData.getLocalization(RegisterActivity.this));
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }

        mIconBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        terms.setPaintFlags(terms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, TermsActivity.class));
            }
        });
        if (getIntent() != null && getIntent().hasExtra(Constants.FACEBOOK_FIRST_NAME)) {
            mFullNameEditText.setText(getIntent().getStringExtra(Constants.FACEBOOK_FIRST_NAME));
            registraionType = getIntent().getIntExtra(Constants.FACEBOOK_LOGIN, 0);
            facebookID = getIntent().getStringExtra(Constants.FACEBOOK_ID);
        }

        handleScrollWhenKeyboardVisible();

    }

    void getCountryList(SelectCountryCitiyListsResponseModel.Result responseList) {
        countrylist.add(getString(R.string.select_country));
        for (int i = 0; i < responseList.getCountryCitiesLst().size(); i++) {
            countrylist.add(String.valueOf(responseList.getCountryCitiesLst().get(i).getCountryObj().getTitle()));
        }
        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(getMyContext(),
                R.layout.row_spinner_colored, countrylist);

        categoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);
        mCountrySpinner.setAdapter(categoryDataAdapter);
        mCountrySpinner.setSelection(0);
        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
//                country_text.setVisibility(View.GONE);
                if (mCountrySpinner.getSelectedItemPosition() != 0) {
                    countryId = responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCountryObj().getID();
                    citylist.clear();
                    getCityList(responseList);
                } else {
                    countryId = 0;
                    citylist.clear();
                    getCityList(null);
//                    citylist.add(getString(R.string.select_city));
//                    mCitySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    void getCityList(SelectCountryCitiyListsResponseModel.Result responseList) {
        citylist.add(getString(R.string.select_city));

        if (responseList != null) {
            for (int x = 0; x < responseList.getCountryCitiesLst().
                    get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().size(); x++) {
                if (countryId == responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getCountryID()) {
                    citylist.add(responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getTitle());
                }
            }
        }
        if (citylist.size() > 0) {
            ArrayAdapter<String> subCategoryDataAdapter = new ArrayAdapter<String>(getMyContext(),
                    R.layout.row_spinner_colored, citylist);
            subCategoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);
            mCitySpinner.setAdapter(subCategoryDataAdapter);
            if (citylist.size() == 1) {
                mCitySpinner.setSelection(0);
            }

            mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // On selecting a spinner item
//                        city_text.setVisibility(View.GONE);
                    if (mCitySpinner.getSelectedItemPosition() != 0) {
                        if (responseList != null) {
                            if (countryId == responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(mCitySpinner.getSelectedItemPosition() - 1).getCountryID()) {
                                cityId = responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1)
                                        .getCitiesLst().get(mCitySpinner.getSelectedItemPosition() - 1).getID();
                            }
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

    @Override
    public void openHomeActivity(String userId) {

        if (!cameFromShippingActivity && !cameFromShippingMobileActivity && !cameFromShippingFlashActivity && !cameFromPublicShipping){
            startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            Log.e("hahahaha","hahahahah");
        }
//        else if (!cameFromShippingActivity && !cameFromShippingMobileActivity && !cameFromShippingFlashActivity){
//            startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//            finish();
//            Log.e("hahahaha","hahahahah");
//        }else if (!cameFromShippingActivity && !cameFromShippingMobileActivity && !cameFromShippingFlashActivity){
//            startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//            finish();
//            Log.e("hahahaha","hahahahah");
//        }
        else {
            Log.e("hahahaha2","hahahahah2");
            //back to shippingActivity
            setResult(RESULT_OK);
            finish();
        }

//        if (!cameFromShippingActivity) {
//            startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//            finish();
//        } else {
//            //back to shippingActivity
//            Log.e("hahahaha","hahahahah");
//            setResult(RESULT_OK);
//            finish();
//        }
    }

    private void logUserRegisterEvent(Context context, String userID) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString("UserID", userID);
//        logger.logEvent("User Register", params);
//        logger.logEvent("User Login", params);
    }

    @Override
    public void returnList(SelectCountryCitiyListsResponseModel.Result responseList) {
        if (isNetworkConnected()) {
            getCountryList(responseList);
        } else {
            showMessage(getString(R.string.error_no_internet_connection));

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void returnFacebookRegistrationData(String Name, String LastName, String faceId) {
        mFullNameEditText.setText(Name + "" + LastName);
        facebookID = faceId;
        registraionType = Constants.FACEBOOK_REGISTER_WithPassword;

    }

    void handleScrollWhenKeyboardVisible() {
        // when click edit TEXT
        onClickOnEditTexts();
        // when Focus edit TEXT
        onFocusOnEditTexts();
    }

    void onClickOnEditTexts() {
        mFullNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, 150));
            }
        });
        mEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, mFullNameEditText.getBottom() + 150));
            }
        });

        mPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, mEmailEditText.getBottom()));
            }
        });

        mConfirmPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, mPasswordEditText.getBottom()));
            }
        });

        mPhoneEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, mConfirmPasswordEditText.getBottom()));
            }
        });
    }

    void onFocusOnEditTexts() {
        mFullNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, 150));
                }
            }
        });

        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, mFullNameEditText.getBottom() + 150));
                }
            }
        });

        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, mEmailEditText.getBottom()));
                }
            }
        });
        mConfirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, mPasswordEditText.getBottom()));
                }
            }
        });

        mPhoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, mConfirmPasswordEditText.getBottom()));
                }
            }
        });
    }

    @Override
    public Boolean checkIfValid() {
        boolean validName = validationTool.validateRequiredField(mFullNameEditText, this.getString(R.string.enter_full_name));
//        boolean validPhone = validationTool.validatePhone(mPhoneEditText, this.getString(R.string.invalid_phone));
        boolean validPhone = validationTool.validatePhone(this ,mPhoneEditText);

        boolean validPassword = validationTool.validatePassword(mPasswordEditText, this.getString(R.string.enter_password));
        boolean validMail = validationTool.validateEmail(mEmailEditText, this.getString(R.string.invalid_email));
        boolean validConfirmFieldPassword = validationTool.isPasswordMatch(mPasswordEditText.getText().toString(), mConfirmPasswordEditText, this.getString(R.string.invalid_confirm_password));
        boolean vaildCountrySpinner = spinnerValid(mCountrySpinner, countryId);
        boolean vaildCitySpinner = spinnerValid(mCitySpinner, cityId);
//        TextUtils.isEmpty(mEmailEditText.getText().toString());

        if (validName && validConfirmFieldPassword && validMail && validPassword && validPhone && vaildCitySpinner && vaildCountrySpinner) {
            return true;
        } else {

            return false;
        }
    }

    private boolean spinnerValid(Spinner spinnerName, int ID) {
        if (ID == 0) {
            if (spinnerName.getSelectedView() != null)
                ((TextView) spinnerName.getSelectedView()).setError("anything here, just to add the icon");
            return false;
        } else {

            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (!cameFromShippingActivity && cameFromShippingMobileActivity && cameFromShippingFlashActivity && cameFromPublicShipping) {
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }else if (cameFromShippingActivity && !cameFromShippingMobileActivity && cameFromShippingFlashActivity && cameFromPublicShipping) {
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }else if (cameFromShippingActivity && cameFromShippingMobileActivity && !cameFromShippingFlashActivity && cameFromPublicShipping) {
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        } else if (cameFromShippingActivity && cameFromShippingMobileActivity && cameFromShippingFlashActivity && !cameFromPublicShipping) {
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        } else {
            //back to shippingActivity
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_sign_up:
                if (checkIfValid()) {
                    if (isNetworkConnected()) {
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RegisterActivity.this,
                                new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                        String newToken = instanceIdResult.getToken();
//                                        Log.e("newToken", newToken);

                                        String deviceID = UtilitiesManager.getDeviceID(RegisterActivity.this);
                                        mPresenter.UserRegister(getMyContext(), mFullNameEditText.getText().toString(),
                                                mEmailEditText.getText().toString(),
                                                mPasswordEditText.getText().toString(), mPhoneEditText.getText().toString(),
                                                registraionType, cityId, countryId, facebookID, newToken,
                                                deviceID , "1",
                                                userData.getLocalization(RegisterActivity.this));
                                    }
                                });
                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));

                    }
                }
                break;

            case R.id.fb_btn:
                if (isNetworkConnected()) {
                    mPresenter.getFacebookAccount(getMyContext());
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }
                break;

        }
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

}
