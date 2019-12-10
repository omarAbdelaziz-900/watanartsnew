package com.WattanArt.ui.EditProfile;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomeButton;
import com.WattanArt.Utils.widgets.CustomEditText;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity implements EditProfileMvpView, View.OnClickListener {

    @Inject
    EditProfilePresenterImp<EditProfileMvpView> mPresenter;

    @BindView(R.id.editprofile_full_name)
    CustomEditText mEditProfileFullName;

    @BindView(R.id.editprofile_mail)
    CustomEditText mEditProfileMail;


    @BindView(R.id.editprofile_phone)
    CustomEditText mEditProfilePhone;

    @BindView(R.id.editprofile_country_spinner)
    Spinner mCountrySpinner;

    @BindView(R.id.editprofile_city_spinner)
    Spinner mCitySpinner;

    @BindView(R.id.editprofile_save)
    CustomeButtonBold mSaveButton;
    @BindView(R.id.icon_back_iv)
    ImageView mIconBackImageView;

    @BindView(R.id.change_tv)
    TextView changeTextView;

    @BindView(R.id.myScrollView)
    ScrollView myScrollView;

    @BindView(R.id.linear_pass)
    LinearLayout linear_pass;
    @BindView(R.id.bg_linear)
    LinearLayout bg_linear;


    private ValidationTool validationTool;
    private List<String> countrylist;
    private List<Integer> countryIdList;
    private List<Integer> cityIdList;
    private List<String> citylist;

    int cityId, countryId, registerType;
    UserData userData;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        countrylist = new ArrayList<>();
        citylist = new ArrayList<>();
        countryIdList = new ArrayList<>();
        cityIdList = new ArrayList<>();
        validationTool = new ValidationTool(getMyContext());

        userData = new UserData();
        userId = userData.getUserID(this);
        Log.d("userIdkkkkkkkkkk", userId + "");

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }


        mPresenter.getProfileData(getMyContext());
//        mPresenter.getResponsOfCountryCityList("1");
        mSaveButton.setOnClickListener(this);
        changeTextView.setOnClickListener(this);
        mIconBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEditProfileFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, 350));

            }
        });

        mEditProfilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myScrollView.post(() -> myScrollView.scrollTo(0, linear_pass.getBottom()+250));

            }
        });

    }


    @Override
    public void returnProfileData(RegisterResponseModel responseModel) {
        mEditProfileFullName.setText(responseModel.getResult().getObj().getFullName());
        mEditProfileMail.setText(responseModel.getResult().getObj().getEmail());
        mEditProfilePhone.setText(responseModel.getResult().getObj().getPhone());
        cityId = responseModel.getResult().getObj().getCityID();
        countryId = responseModel.getResult().getObj().getCountryID();

        registerType = responseModel.getResult().getObj().getRegisterType();


    }

    @Override
    public void returnCountryCityList(SelectCountryCitiyListsResponseModel.Result responseList) {
        getCountryList(responseList);


    }

    @Override
    public Boolean checkIfValid() {
        boolean validName = validationTool.validateRequiredField(mEditProfileFullName, this.getString(R.string.enter_full_name));
//        boolean validPhone = validationTool.validatePhone(mEditProfilePhone, this.getString(R.string.invalid_phone));
        boolean validPhone = validationTool.validatePhone(this ,mEditProfilePhone);
        boolean validMail = validationTool.validateEmail(mEditProfileMail, this.getString(R.string.invalid_email));
        boolean vaildCountrySpinner = spinnerValid(mCountrySpinner, countryId);
        boolean vaildCitySpinner = spinnerValid(mCitySpinner, cityId);
        //        boolean validPassword = validationTool.validatePassword(mEditProfilePassword, this.getString(R.string.enter_password));
//        boolean validConfirmFieldPassword = validationTool.isPasswordMatch(mEditProfilePassword.getText().toString(),
//                mEditProfileConfirmPassword, this.getString(R.string.invalid_confirm_password));
        if (validName && validMail && validPhone && vaildCitySpinner && vaildCountrySpinner) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception e){
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean spinnerValid(Spinner spinnerName, int ID) {
        if (ID == 0) {

            Toast.makeText(this,getString(R.string.country_city_validation),Toast.LENGTH_LONG).show();
//            if (spinnerName != null)
//                ((TextView) spinnerName.getSelectedView()).setError("anything here, just to add the icon");
            return false;
        } else {

            return true;
        }

    }

    @Override
    public void openHomeActivity() {
//        startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public int indexOfID(int searchID, List<Integer> domain) {
        for (int i = 0; i < domain.size(); i++)
            if (searchID == (domain.get(i)))
                return i + 1;

        return -1;
    }

    void getCountryList(SelectCountryCitiyListsResponseModel.Result responseList) {
        countrylist.add(getString(R.string.select_country));
        for (int i = 0; i < responseList.getCountryCitiesLst().size(); i++) {
            countrylist.add(String.valueOf(responseList.getCountryCitiesLst().get(i).getCountryObj().getTitle()));
            countryIdList.add(responseList.getCountryCitiesLst().get(i).getCountryObj().getID());
        }
        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<String>(getMyContext(),
                R.layout.row_spinner_colored, countrylist);
//        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);

        mCountrySpinner.setAdapter(categoryDataAdapter);

        if (countrylist != null) {
            int i = indexOfID(countryId, countryIdList);
            if (i >= 0) {
                mCountrySpinner.setSelection(i);
                Log.d("countryIddd", countryId + "iiii" + i + "");
            }
        }
        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item

                if (mCountrySpinner.getSelectedItemPosition() != 0) {
                    countryId = responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCountryObj().getID();
                    citylist.clear();
                    cityIdList.clear();
                    getCityList(responseList);
                } else {
                    countryId = 0;
                    citylist.clear();
                    citylist.add(getString(R.string.select_city));
                    mCitySpinner.setSelection(0);
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
                    get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().size(); x++) {

                if (countryId == responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getCountryID()) {

                    citylist.add(responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getTitle());
                    cityIdList.add(responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1).getCitiesLst().get(x).getID());
                }
            }


            ArrayAdapter<String> subCategoryDataAdapter = new ArrayAdapter<String>(getMyContext(),
                    R.layout.row_spinner_colored, citylist);
            subCategoryDataAdapter.setDropDownViewResource(R.layout.row_spinner_colored);
//            subCategoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mCitySpinner.setAdapter(subCategoryDataAdapter);
            if (citylist.size() != 1 && countryId != 0) {
                if (citylist != null) {

                    int i = indexOfID(cityId, cityIdList);
                    if (i >= 0) {
                        mCitySpinner.setSelection(i);
                        Log.d("cityIddd", cityId + "iiii" + i + "");
                    }
                }

                mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // On selecting a spinner item

                        if (mCitySpinner.getSelectedItemPosition() != 0 && countryId != 0) {
                            if (countryId == responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1)
                                    .getCitiesLst().get(mCitySpinner.getSelectedItemPosition() - 1).getCountryID()) {

                                cityId = responseList.getCountryCitiesLst().get(mCountrySpinner.getSelectedItemPosition() - 1)
                                        .getCitiesLst().get(mCitySpinner.getSelectedItemPosition() - 1).getID();

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_tv:
                showChangePassPopup();
                break;
            case R.id.editprofile_save:
                if (isNetworkConnected()) {
                    if (checkIfValid()) {
                        mPresenter.sendUpdatedProfileData(mEditProfileFullName.getText().toString(), mEditProfileMail.getText().toString()
                                , mEditProfilePhone.getText().toString(),
                                userId, cityId, countryId, 1);
                    }
                }
                break;
        }


    }

    public void showChangePassPopup() {

        Dialog mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(mDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.change_password_popup);


        EditText mOldPass = mDialog.findViewById(R.id.old_pass_et);
        EditText mNewPass = mDialog.findViewById(R.id.new_pass_et);
        EditText mConfirmPass = mDialog.findViewById(R.id.confirm_pass_et);

        if (registerType == Constants.FACEBOOK_REGISTER){
            mOldPass.setText(Constants.DEFAULT_PASSWORD);
            mOldPass.setEnabled(false);
        }
            mDialog.findViewById(R.id.close_popup).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

        mDialog.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Utils.hideKeypad((Activity) context);
                boolean validOldPass = new ValidationTool(EditProfileActivity.this).validatePassword(mOldPass, getString(R.string.invalid_old_password));
                boolean validNewPass = new ValidationTool(EditProfileActivity.this).validatePassword(mNewPass, getString(R.string.invalid_new_password));
                boolean validRetypePass = new ValidationTool(EditProfileActivity.this).validatePassword(mConfirmPass, getString(R.string.invalid_confirm_pass_hint));

                boolean isPasswordMatch = new ValidationTool(EditProfileActivity.this).isPasswordMatch(mNewPass.getText().toString(), mConfirmPass, getString(R.string.invalid_confirm_password));
                if (validNewPass && validRetypePass && validOldPass && isPasswordMatch) {
                    if (registerType == Constants.FACEBOOK_REGISTER){
                        //when faceUser has'nt have old pass send it >> null
                        mPresenter.changePassword("_", mNewPass.getText().toString(), userData.getUserID(EditProfileActivity.this));

                    }else {
                        // for normal user or face user with  old pass
                        mPresenter.changePassword(mOldPass.getText().toString(), mNewPass.getText().toString(), userData.getUserID(EditProfileActivity.this));

                    }
                    mDialog.dismiss();

                }
            }
        });


        mDialog.show();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

}
