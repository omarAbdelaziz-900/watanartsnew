package com.WattanArt.ui.Setting;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.Localization;
import com.WattanArt.Utils.SharedPrefTool.PreferenceHelper;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.ui.About.AboutActivity;
import com.WattanArt.ui.ContactUs.ContactUsActivity;
import com.WattanArt.ui.EditProfile.EditProfileActivity;
import com.WattanArt.ui.FAQ.FaqActivity;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Login.LoginActivity;
import com.WattanArt.ui.MyOrdersActivity;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryFragment;
import com.WattanArt.ui.base.BaseFragment;
import com.WattanArt.ui.getFreeCredit.GetFreeCreditActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.gson.JsonObject;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements SettingMvpView {

    View view;
    UserData userData;
    String userId;
    Dialog dialog;

    @Inject
    SettingMvpPresenter<SettingMvpView> mPresenter;

    public SettingFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.log_out_tv)
    CustomeTextViewBold logOutTextView;

//    @BindView(R.id.log_out_icon)
//    ImageView logOutImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        ButterKnife.bind(this, view);
        userData = new UserData();
        userId = userData.getUserID(getActivity());
        ((HomeActivity) Objects.requireNonNull(getActivity())).setToolbarTitle(getString(R.string.title_setting));
        ((HomeActivity) Objects.requireNonNull(getActivity())).visibileToolbarIconBack();
//        ((HomeActivity) Objects.requireNonNull(getActivity())).mToolbarBackImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ((HomeActivity) Objects.requireNonNull(getActivity())). openFragment(HomeFragment.class, null);
//                startActivity(new Intent(getActivity(), HomeActivity.class));
//                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                getActivity().finish();
//
//            }
//        });
        if (!userId.isEmpty()) {
            logOutTextView.setText(getResources().getString(R.string.log_out));
//            logOutImageView.setImageResource(R.drawable.ic_logout);
        } else {
            view.findViewById(R.id.editProfileLinear).setVisibility(View.GONE);
            view.findViewById(R.id.gray_line_profile).setVisibility(View.GONE);
        }

        if (view.findViewById(R.id.editProfileLinear) != null)
            view.findViewById(R.id.editProfileLinear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkConnected()) {

                        if (!userId.isEmpty()) {
                            startActivity(new Intent(getActivity(), EditProfileActivity.class));
                        } else {
                            showMessage(getString(R.string.login_before));
                        }

                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }

                }
            });

        if (view.findViewById(R.id.promoCodeLinear) != null)
            view.findViewById(R.id.promoCodeLinear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkConnected()) {

                        if (!userId.isEmpty()) {
                            startActivity(new Intent(getActivity(), GetFreeCreditActivity.class));
                        } else {
                            showMessage(getString(R.string.login_before));
                        }

                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }

                }
            });


        view.findViewById(R.id.languageLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openLanguageDialog(getActivity());
            }
        });

        view.findViewById(R.id.aboutusLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    startActivity(new Intent(getActivity(), AboutActivity.class));

                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }

            }
        });

        view.findViewById(R.id.contactusLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    startActivity(new Intent(getActivity(), ContactUsActivity.class));

                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }

            }
        });


        view.findViewById(R.id.my_orders_linear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getActivity()!=null)
                        userId = userData.getUserID(getActivity());
                        if (!userId.isEmpty()) {
                        if (isNetworkConnected()) {
                            Intent intent=new Intent(getActivity(), MyOrdersActivity.class);
                            startActivity(intent);
                        } else {
                            showMessage(R.string.error_no_internet_connection);
                        }
                    } else {
                        showMessage(R.string.login_before);
                    }

                    }
                });



        view.findViewById(R.id.faqLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    startActivity(new Intent(getActivity(), FaqActivity.class));

                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }

            }
        });

        view.findViewById(R.id.shareappLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    Intent facebookIntent = new Intent(android.content.Intent.ACTION_SEND);
                    facebookIntent.setType("text/plain");
//                    facebookIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.subject));
                    facebookIntent.putExtra(Intent.EXTRA_TEXT, Constants.LinkTOShare);
//                    facebookIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("hhhhh"));
                    Intent chooser = Intent.createChooser(facebookIntent, getString(R.string.share));
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chooser);
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));
                }
            }
        });


//        view.findViewById(R.id.logoutLinear).setOnClickListener(v -> {
//            if (new UserData().getRemmemberMe(getActivity())) {
//                if (isNetworkConnected()) {
//                    JsonObject obj = new JsonObject();
//                    obj.addProperty("UserID", userId);
//                    mPresenter.onLogout(obj);
//                } else {
//                    showMessage(getString(R.string.error_no_internet_connection));
//                }
//            } else {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//            }
////            openConfirmationDialog();
//
//        });

        view.findViewById(R.id.logoutLinear).setOnClickListener(v -> {

            if (new UserData().getRemmemberMe(getActivity())) {
                openConfirmationDialog();
            }else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

//            if (new UserData().getRemmemberMe(getActivity())) {
//                if (isNetworkConnected()) {
//                    JsonObject obj = new JsonObject();
//                    obj.addProperty("UserID", userId);
//                    mPresenter.onLogout(obj);
//                } else {
//                    showMessage(getString(R.string.error_no_internet_connection));
//                }
//            } else {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//            }
//            openConfirmationDialog();
        });

        return view;
    }

    public void openLanguageDialog(final Activity context) {

        final Dialog mDialog = new Dialog(context, R.style.CustomDialogTheme);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.language_dialog);

        TextView arabicLanguage =  mDialog.findViewById(R.id.arabic);
        TextView englishLanguage =  mDialog.findViewById(R.id.english);

        if (userData.getLocalization(context) == Localization.ARABIC_VALUE) {
            arabicLanguage.setBackgroundColor(getResources().getColor(R.color.yellow));
            arabicLanguage.setTextColor(getResources().getColor(R.color.white));
        } else {
            englishLanguage.setBackgroundColor(getResources().getColor(R.color.yellow));
            englishLanguage.setTextColor(getResources().getColor(R.color.white));
        }

        arabicLanguage.setOnClickListener(v -> {
            arabicLanguage.setBackgroundColor(getResources().getColor(R.color.yellow));
            arabicLanguage.setTextColor(getResources().getColor(R.color.white));

            englishLanguage.setBackgroundColor(getResources().getColor(R.color.white));
            englishLanguage.setTextColor(getResources().getColor(R.color.yellow));

            new Localization().changeLanguage(Constants.TAG_ARABIC_String, context);
            userData.saveLocalization(getActivity(), Localization.ARABIC_VALUE);
//                new Localization().setLanguage(getActivity(), userData.getLocalization(getActivity()));
            mDialog.dismiss();
            context.finish();


        });

        englishLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                englishLanguage.setBackgroundColor(getResources().getColor(R.color.yellow));
                englishLanguage.setTextColor(getResources().getColor(R.color.white));

                arabicLanguage.setBackgroundColor(getResources().getColor(R.color.white));
                arabicLanguage.setTextColor(getResources().getColor(R.color.yellow));

                new Localization().changeLanguage(Constants.TAG_ENGLISH_String, context);
                userData.saveLocalization(getActivity(), Localization.ENGLISH_VALUE);
//                new Localization().setLanguage(getActivity(), userData.getLocalization(getActivity()));
                mDialog.dismiss();
                context.finish();

            }
        });

        mDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openHomeActivity() {
        if (FacebookSdk.isInitialized()) {
//                    FacebookSdk.sdkInitialize(MyApplication.getAppContext());
            LoginManager.getInstance().logOut();
        }
        int lang = userData.getLocalization(getActivity());
        PreferenceHelper.clearShared(getActivity());
        userData.saveLocalization(getActivity(), lang);
        startActivity(new Intent(getActivity(), LoginActivity.class).setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
    }


    private void openConfirmationDialog() {
        dialog = new Dialog(getContext(), R.style.ThemeDialogCustom);
        dialog.setContentView(R.layout.pop_up_for_logout);
        final CustomeButtonBold txt_yes = dialog.findViewById(R.id.txt_yes);
        final CustomeButtonBold txt_no = dialog.findViewById(R.id.txt_no);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 40);
        dialog.getWindow().setBackgroundDrawable(inset);


        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new UserData().getRemmemberMe(getActivity())) {
                    if (isNetworkConnected()) {
                        JsonObject obj = new JsonObject();
                        obj.addProperty("UserID", userId);
                        mPresenter.onLogout(obj);
                        dialog.dismiss();
                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }
                } else {
                    dialog.dismiss();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        txt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
