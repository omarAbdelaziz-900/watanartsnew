package com.WattanArt.ui.FAQ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.WattanArt.Adapters.FAQCustomAdapter;
import com.WattanArt.Adapters.OrderHistoryListAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.FAQresponseModel;
import com.WattanArt.ui.About.AboutMvpView;
import com.WattanArt.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqActivity extends BaseActivity implements FAQMvpView {

    @Inject
    FAQMvpViewPresenter<FAQMvpView> mPresenter;

    @BindView(R.id.faq_recycler_view)
    RecyclerView mfaqRecyclerView;
    @BindView(R.id.error_message)
    CustomeTextViewBold mErrorMessage;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;
    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ButterKnife.bind(this);
        userData = new UserData();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        mToolbarBackImageView.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitleTextView.setText(getString(R.string.faq));
//        mToolbarBackImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        mPresenter.getFAQ(userData.getLocalization(this));
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }


    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mfaqRecyclerView.setLayoutManager(linearLayoutManager);
        mfaqRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mfaqRecyclerView.setNestedScrollingEnabled(false);
        mfaqRecyclerView.setHasFixedSize(true);
        mfaqRecyclerView.scrollToPosition(0);
    }

    private void setData(List<FAQresponseModel.ResultEntity> resultEntityList) {
        RecyclerView.Adapter adapter = new FAQCustomAdapter(mfaqRecyclerView, resultEntityList);
        mfaqRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


    @Override
    public void returnResponseData(FAQresponseModel responseModel) {
        initRecyclerView();
        if (responseModel.getResult().size() != 0) {
            setData(responseModel.getResult());
        } else {
            mErrorMessage.setVisibility(View.VISIBLE);
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
