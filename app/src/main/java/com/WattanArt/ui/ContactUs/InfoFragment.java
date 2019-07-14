package com.WattanArt.ui.ContactUs;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.CheckUtils;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.ui.base.BaseFragment;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends BaseFragment implements View.OnClickListener, ContactUsMvpView {

    @BindView(R.id.call_txt)
    TextView callTextView;

    @BindView(R.id.Address_txt)
    TextView addressTextView;

    @BindView(R.id.website_txt)
    TextView websiteTextView;

    @BindView(R.id.fbImg)
    ImageView fbImageView;

    @BindView(R.id.twitterImg)
    ImageView twitterImageView;

    @BindView(R.id.instaImg)
    ImageView instaImageView;

    @BindView(R.id.call_img)
    ImageView callImageView;

    @BindView(R.id.website_img)
    ImageView websiteImageView;
    @BindView(R.id.Address_img)
    ImageView addressImageView;
UserData userData;
    @Inject
    ContactUsPresenterImp<ContactUsMvpView> mPresenter;

    public InfoFragment() {
        // Required empty public constructor
    }


    String longtude, latitude,facebookUrl, twitterUrl, instagramUrl,websiteUrl, tetephoneNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        userData=new UserData();
        ButterKnife.bind(this, view);

        if (isNetworkConnected()) {
            mPresenter.getContactUsData(userData.getLocalization(getActivity()));

        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }
        return view;
    }


    private void assignControls() {

        fbImageView.setOnClickListener(this);
        twitterImageView.setOnClickListener(this);
        instaImageView.setOnClickListener(this);
        websiteImageView.setOnClickListener(this);
        callImageView.setOnClickListener(this);
        addressImageView.setOnClickListener(this);

        callTextView.setOnClickListener(this);
        websiteTextView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_img:
            case R.id.call_txt:

                if (tetephoneNum != null) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                       requestPermissions( new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    else
                    {
                        String uri = "tel:" + tetephoneNum.trim();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);                    }

            }
                break;
            case R.id.website_img:
            case R.id.website_txt:

                if (CheckUtils.validString(websiteUrl)) {
                    CheckUtils.openWebPage(getActivity(), websiteUrl);
                }
                break;

            case R.id.Address_img:
//                Intent intent1 = new Intent(getActivity(), ViewLocationMapActivity.class);
//                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, latitude);
//                intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, longtude);
//                startActivity(intent1);
                break;

            case R.id.fbImg:
                if (CheckUtils.validString(facebookUrl)) {
                    CheckUtils.openWebPage(getActivity(), facebookUrl);
                }
                break;

            case R.id.instaImg:
                if (CheckUtils.validString(instagramUrl)) {
                    CheckUtils.openWebPage(getActivity(), instagramUrl);
                }
                break;

            case R.id.twitterImg:
                if (CheckUtils.validString(twitterUrl)) {
                    CheckUtils.openWebPage(getActivity(), twitterUrl);
                }
                break;


        }
    }


    @Override
    public void UpdateInfoUi(ContactUsModel contactUsResult) {
        callTextView.setText(contactUsResult.getResult().getPhoneNumber());
        addressTextView.setText(contactUsResult.getResult().getAddress());
        websiteTextView.setText(contactUsResult.getResult().getWebsiteLink());
        tetephoneNum=contactUsResult.getResult().getPhoneNumber();
//        tetephoneNum="1234556";
        facebookUrl = contactUsResult.getResult().getFacebookUrl();
        twitterUrl = contactUsResult.getResult().getTwitterUrl();
        instagramUrl = contactUsResult.getResult().getInstagramUrl();
        latitude = contactUsResult.getResult().getLatitude();
        longtude = contactUsResult.getResult().getLongtiude();
        websiteUrl = contactUsResult.getResult().getWebsiteLink();

        assignControls();

    }

    @Override
    public void flagSuccessfullySend() {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String uri = "tel:" + tetephoneNum.trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            }
        }

    }
}
