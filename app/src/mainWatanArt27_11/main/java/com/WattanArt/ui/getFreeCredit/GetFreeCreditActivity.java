package com.WattanArt.ui.getFreeCredit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetFreeCreditActivity extends BaseActivity implements IGetFreeCredit {

    @Inject
    GetFreeCreditPresenter<IGetFreeCredit> mPresenter;
    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;
    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;

    @BindView(R.id.theCode)
    CustomeTextViewBold theCode;

    @BindView(R.id.num_of_tries)
    CustomeTextViewBold num_of_tries;

    @BindView(R.id.get_free)
    CustomeTextViewBold get_free;

    @BindView(R.id.share_code_txt)
    CustomeTextView share_code_txt;


    private UserData userData;


    @OnClick(R.id.copy)
    public void copy(View view) {
        setClipboard(this, theCode.getText().toString());
    }

    private void setClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, getString(R.string.copied), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_free_credit);

        ButterKnife.bind(this);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.promoCode));
        mToolbarBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        userData = new UserData();
        if (isNetworkConnected()) {
            mPresenter.getProfileData(this);
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    @Override
    public void returnProfileData(RegisterResponseModel responseModel) {
        if (responseModel.getResult().getObj().getPromocode()!=null)
        theCode.setText(responseModel.getResult().getObj().getPromocode());


        share_code_txt.setText(getString(R.string.shareyourcode1)+" "+
                responseModel.getResult().getObj().getDiscountRate()+"% "
        +getString(R.string.shareyourcode2));

        get_free.setText(getString(R.string.get_10_egp_free_Credit1)+" "+
                responseModel.getResult().getObj().getDiscountRate()+"% "
        +getString(R.string.get_10_egp_free_Credit2));


        if (responseModel.getResult().getObj().getAvailableTime()!=0) {
            num_of_tries.setText(getString(R.string.have) + " " + responseModel.getResult().getObj().getDiscountRate() + "% "
                    + getString(R.string.discount) + " " + responseModel.getResult().getObj().getAvailableTime() +
                    " " + getString(R.string.order));
        }else {
            num_of_tries.setText(getString(R.string.not_have_count));
        }
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
