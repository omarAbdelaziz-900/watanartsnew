package com.WattanArt.ui.CanvasPrint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.Adapters.CanvasPrintAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.WattanArt.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanvasPrintActivity extends BaseActivity implements CanvasPrintMvpView {
    @BindView(R.id.introList_recycle)
    RecyclerView mIntroRecycle;

    //    @BindView(R.id.order_list_error_message)
//    CustomeTextViewBold mErrorMessage;

    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;

    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;
    @Inject
    CanvasPrintPresenterImp<CanvasPrintMvpView> mPresenter;

    private RecyclerView.Adapter adapter;
    LinearLayoutManager linearLayoutManager;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_print);
        ButterKnife.bind(this);
        initRecyclerView();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        userData = new UserData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.title_canvas_print));
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

        if (isNetworkConnected()) {
            mPresenter.getData(userData.getLocalization(this));

        } else {
            showMessage(getString(R.string.error_no_internet_connection));

        }
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mIntroRecycle.setLayoutManager(linearLayoutManager);
        mIntroRecycle.setItemAnimator(new DefaultItemAnimator());
        mIntroRecycle.setNestedScrollingEnabled(false);
        mIntroRecycle.setHasFixedSize(true);
        mIntroRecycle.scrollToPosition(0);
    }

    private void setData(CanvasPrintResponseModel responseModel) {
        adapter = new CanvasPrintAdapter(this, responseModel);
        mIntroRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    @Override
    public void returnData(CanvasPrintResponseModel responseModel) {
        initRecyclerView();
        setData(responseModel);


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
