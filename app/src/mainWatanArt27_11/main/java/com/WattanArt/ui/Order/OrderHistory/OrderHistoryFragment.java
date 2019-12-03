package com.WattanArt.ui.Order.OrderHistory;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.WattanArt.Adapters.OrderHistoryListAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.CheckUtils;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Request.AllOrdersHistoryRequestModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.HomeFragment.HomeFragment;
import com.WattanArt.ui.base.BaseFragment;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends BaseFragment implements OrderHistoryMvpView {

    View view;
    @Inject
    OrderHistoryPresenterImp<OrderHistoryMvpView> mPresenter;

    @BindView(R.id.order_recycler)
    RecyclerView mOrderRecycle;

    @BindView(R.id.order_list_error_message)
    CustomeTextViewBold mErrorMessage;


    LinearLayoutManager linearLayoutManager;
    UserData userData;
    String userId;
    int userType;


    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, view);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        ((HomeActivity) Objects.requireNonNull(getActivity())).setToolbarTitle(getString(R.string.title_history));

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
        userData = new UserData();
        userId = userData.getUserID(getActivity());

        if (isNetworkConnected()) {
            mPresenter.getLListOfOrders(new AllOrdersHistoryRequestModel(userData.getLocalization(getActivity()), userId));
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mOrderRecycle.setLayoutManager(linearLayoutManager);
        mOrderRecycle.setItemAnimator(new DefaultItemAnimator());
        mOrderRecycle.setNestedScrollingEnabled(false);
        mOrderRecycle.setHasFixedSize(true);
        mOrderRecycle.scrollToPosition(0);
    }

    private void setData(List<AllOrdersHistoryResponseModel.ResultEntity> resultEntityList) {
        RecyclerView.Adapter adapter = new OrderHistoryListAdapter(getActivity(), resultEntityList);
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
}
