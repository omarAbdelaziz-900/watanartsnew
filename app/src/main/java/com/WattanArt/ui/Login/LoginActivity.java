package com.WattanArt.ui.Login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.SharedPreferencesTool;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomEditTextBold;
import com.WattanArt.Utils.widgets.CustomeButton;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Register.RegisterActivity;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_email)
    CustomEditTextBold mEmailEditText;
    @BindView(R.id.login_password)
    CustomEditTextBold mPasswordEditText;
    @BindView(R.id.login_btn)
    CustomeButtonBold mLoginButton;
    @BindView(R.id.fb_btn)
    CustomeButtonBold mFacebookLoginButton;
    @BindView(R.id.register_btn)
    CustomeButtonBold mRegisterButton;
    @BindView(R.id.skip)
    CustomeTextViewBold mSkipTextView;
//    @BindView(R.id.remeberme_checkbox)
//    CheckBox mremeberme_checkbox;

    @BindView(R.id.forgetPasswordtv)
    CustomeTextView mforgetPasswordTextView;

    @BindView(R.id.myScrollView)
    ScrollView myScrollView;

//    @BindView(R.id.mainLayout)
//    RelativeLayout bg_main;

    private ValidationTool validationTool;
    boolean isRemember = true;
    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        bg_main.requestFocus();
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        validationTool = new ValidationTool(getMyContext());

        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
        mSkipTextView.setOnClickListener(this);
        mforgetPasswordTextView.setOnClickListener(this);

        Log.d("language Login", userData.getLocalization(this) + "");
        handleScrollWhenKeyboardVisible();
    }

    void handleScrollWhenKeyboardVisible() {
        // when click edit TEXT
        onClickOnEditTexts();
        // when Focus edit TEXT
        onFocusOnEditTexts();
    }

    void onFocusOnEditTexts() {
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, 250));
                }
            }
        });
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myScrollView.post(() -> myScrollView.scrollTo(0, 250));
                }
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

    void onClickOnEditTexts() {
        mEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, 250));

            }
        });
        mPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, 250));

            }
        });

    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {
    }

    @Override
    public void openMainActivity(String userId) {
//        if (!TextUtils.isEmpty(userId)) {
//            logUserLoginEvent(this, userId);
//        }
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finishAffinity();
    }

    private void logUserLoginEvent(Context context, String userID) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString("UserID", userID);
//        logger.logEvent("User Login", params);
    }

    @Override
    public Boolean checkIfValid() {
//        boolean validPassword = validationTool.validatePassword(mPasswordEditText, this.getString(R.string.enter_password));
        boolean validPassword = mPasswordEditText.getText().toString().isEmpty() || mPasswordEditText.getText().toString().trim().isEmpty() ? false : true;
        boolean validMail = validationTool.validateEmail(mEmailEditText, this.getString(R.string.invalid_email));

        if (!validPassword) {
            mPasswordEditText.setError(getString(R.string.enter_password));
        }

        if (validMail && validPassword) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (checkIfValid()) {
                    if (isNetworkConnected()) {
//                        if (mremeberme_checkbox.isChecked()) {
                            isRemember = true;
//                        }

                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                String newToken = instanceIdResult.getToken();
                                Log.e("newToken", newToken);
                                String deviceID = UtilitiesManager.getDeviceID(LoginActivity.this);
                                mPresenter.getLoginData
                                        (getMyContext(), "", mEmailEditText.getText().toString(), newToken,
                                                mPasswordEditText.getText().toString(),deviceID , "1" , isRemember);

                            }
                        });
                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }
                }

                break;
            case R.id.fb_btn:
                if (isNetworkConnected()) {
                    mPresenter.getLoginDataFromSocial(getMyContext());
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));
                }
                break;
            case R.id.register_btn:
                if (isNetworkConnected()) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }

                break;
            case R.id.skip:
                int lang = userData.getLocalization(LoginActivity.this);
                SharedPreferencesTool.clearObject(this);
                userData.saveLocalization(LoginActivity.this, lang);
                openMainActivity(null);
                break;

            case R.id.forgetPasswordtv:
                showLoginPopup();
                break;

        }
    }

    public void showLoginPopup() {

        Dialog mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.forget_password_pop);


        EditText mEmailEditText = mDialog.findViewById(R.id.email_et);


        mDialog.findViewById(R.id.close_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validMail = validationTool.validateEmailForgetPass(mEmailEditText, MyApplication.getAppContext().getString(R.string.invalid_email));
                if (validMail) {
                    mDialog.dismiss();
                    if (isNetworkConnected()) {
                        mPresenter.sendforgetPasswordToEmail(mEmailEditText.getText().toString());

                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }

                }
            }
        });


        mDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);

    }
}
