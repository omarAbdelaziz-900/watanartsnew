package com.WattanArt.ui.About;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.Localization;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class AboutActivity extends BaseActivity implements AboutMvpView {

    @Inject
    AboutPresenterImp<AboutMvpView> mPresenter;
    @BindView(R.id.aboutusDesc_tv)
    CustomeTextView mAboutusDescTextView;
    @BindView(R.id.aboutUsTitle_tv)
    CustomeTextViewBold mAboutUsTitleTextView;
//
    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;
//
    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.title_about));
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
            mPresenter.getAboutUsData(userData.getLocalization(this));

        } else {
            showMessage(getString(R.string.error_no_internet_connection));

        }
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    @Override
    public void UpdateUi(Object aboutUsData) {
        AboutUsResponseModel aboutUsDatamodel=(AboutUsResponseModel) aboutUsData;
        if (userData.getLocalization(this) == Localization.ARABIC_VALUE) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getHtmlText(aboutUsDatamodel.getResult().getDescriptionAR());
//            }

        } else {
            getHtmlText(aboutUsDatamodel.getResult().getDescriptionEN());


        }
    }

    void getHtmlText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (text != null) {
                Spanned sp = Html.fromHtml(text, FROM_HTML_MODE_LEGACY);
                mAboutusDescTextView.setText(sp);
            }
        } else {
            if (text != null) {

                Spanned sp = Html.fromHtml(text);
                mAboutusDescTextView.setText(sp);
            } else {
                showMessage(getString(R.string.api_return_null));
            }
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
