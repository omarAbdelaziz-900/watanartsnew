package com.WattanArt.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.WattanArt.Adapters.OrderHistoryListAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Request.AllOrdersHistoryRequestModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryMvpView;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryPresenterImp;
import com.WattanArt.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersActivity extends BaseActivity implements OrderHistoryMvpView{

//    View view;
    @Inject
    OrderHistoryPresenterImp<OrderHistoryMvpView> mPresenter;

    @BindView(R.id.order_recycler)
    RecyclerView mOrderRecycle;

    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold toolbar_tv_title;

    @BindView(R.id.toolbar_image_view)
    ImageView toolbar_image_view;

    @BindView(R.id.order_list_error_message)
    CustomeTextViewBold mErrorMessage;


    LinearLayoutManager linearLayoutManager;
    UserData userData;
    String userId;
    int userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);

//        mOrderRecycle=findViewById(R.id.order_recycler);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        toolbar_tv_title.setText(getString(R.string.my_order));

        toolbar_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userData = new UserData();
        userId = userData.getUserID(this);

        if (isNetworkConnected()) {
            mPresenter.getLListOfOrders(new AllOrdersHistoryRequestModel(userData.getLocalization(this), userId));
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mOrderRecycle.setLayoutManager(linearLayoutManager);
        mOrderRecycle.setItemAnimator(new DefaultItemAnimator());
        mOrderRecycle.setNestedScrollingEnabled(false);
        mOrderRecycle.setHasFixedSize(true);
        mOrderRecycle.scrollToPosition(0);
    }

    private void setData(List<AllOrdersHistoryResponseModel.ResultEntity> resultEntityList) {
        RecyclerView.Adapter adapter = new OrderHistoryListAdapter(this, resultEntityList);
        mOrderRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void returnResponseData(AllOrdersHistoryResponseModel responseModel) {
        initRecyclerView();
        if (responseModel.getResult().size() != 0) {
            setData(responseModel.getResult());
        } else {
            mErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }
}
