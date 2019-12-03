package com.WattanArt.ui.Order.OrderDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.Adapters.OrderDetailsAdapter;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsMvpView {

    @BindView(R.id.items_recycle)
    RecyclerView mOrderRecycle;

    @BindView(R.id.no_order_details_tv)
    CustomeTextView mNoOfOrderTextView;
    @BindView(R.id.date_details_tv)
    CustomeTextView mDateOfOrderTextView;
    @BindView(R.id.status_details_tv)
    CustomeTextViewBold mStatusOfOrderTextView;
    @BindView(R.id.total_details_tv)
    CustomeTextViewBold mTotalPricefOrderTextView;
    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;

    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;
    @Inject
    OrderDetailsPresenterImp<OrderDetailsMvpView> mPresenter;

    LinearLayoutManager linearLayoutManager;
    double totalPrice;
    int orderId;
    String stateValue;
    double itemCost;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.title_activity_order_details));
//        mToolbarBackImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//
//            }
//        });
        userData = new UserData();
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        final Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getIntExtra(Constants.ORDERID, 0);
            stateValue = intent.getStringExtra(Constants.STATEVALUE);
            itemCost = intent.getDoubleExtra(Constants.ORDERITEMPRICE,0
            );
        }


        mPresenter.getResponse(userData.getLocalization(this), String.valueOf(orderId));


    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mOrderRecycle.setLayoutManager(linearLayoutManager);
        mOrderRecycle.setItemAnimator(new DefaultItemAnimator());
        mOrderRecycle.setNestedScrollingEnabled(false);
        mOrderRecycle.setHasFixedSize(true);
        mOrderRecycle.scrollToPosition(0);

    }

    private void setData(List<OrderDetailsResponseModel.ResultEntity> responseList) {
        RecyclerView.Adapter adapter = new OrderDetailsAdapter(this, responseList);
        mOrderRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void returnResponseToView(OrderDetailsResponseModel responseModel) {
        initRecyclerView();
        if (responseModel.getResult().size() != 0) {
            mNoOfOrderTextView.setText(" "+String.valueOf(responseModel.getResult().get(0).getOrderID()));
            String[] dayMonthYear = responseModel.getResult().get(0).getCreatedDate().split("T");
            String part1 = dayMonthYear[0].replace("-", "/");
            mDateOfOrderTextView.setText(" "+part1);
//            mDateOfOrderTextView.setText(dayMonthYear[0]);
            mStatusOfOrderTextView.setText(" "+stateValue);
//            for (int i = 0; i < responseModel.getResult().size(); i++) {
//                totalPrice = totalPrice + responseModel.getResult().get(i).getPiecePrice();
//            }
                            totalPrice = totalPrice + itemCost;

            String currency;
            boolean isInEgypt = UtilitiesManager.getUserCountry(this).toLowerCase().equals("eg");
            if (isInEgypt)
                currency = getString(R.string.le);
            else
                currency = getString(R.string.dolar);
            mTotalPricefOrderTextView.setText(" "+String.valueOf(totalPrice) +" "+ currency +" ");


            setData(responseModel.getResult());
        } else {
            showMessage(getString(R.string.no_order));
        }
    }

/*

    private int getPiecesNumber(OrderDetailsResponseModel resultEntity){
        int totalNumberOfPices = 0;
        for (int i=0;i<resultEntity.getResult().size();i++){
            totalNumberOfPices+=resultEntity.getResult().get(i).getQuantiy();
        }

        return totalNumberOfPices;
    }
    private void checkShippingPrice(OrderDetailsResponseModel resultEntity) {
        int piecesCount = getPiecesNumber(resultEntity);
        if (piecesCount == 3) {
            double mainPrice;
            if (isInEgypt) {
                mainPrice = pattenList.getPrices().getLocal3PiecePrice();
            } else {
                mainPrice = pattenList.getPrices().getWorld3PiecePrice();
            }
            mainPriceHolder = mainPrice;
            priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice + " " + getString(R.string.price_item_2));
        } else if (piecesCount > 3) {
            double mainPrice;
            if (isInEgypt) {
                mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getLocalPiecePrice() + pattenList.getPrices().getLocal3PiecePrice();
            } else {
                mainPrice = (adapter.getPiecesNumber() - 3) * pattenList.getPrices().getWorldPiecePrice() + pattenList.getPrices().getWorld3PiecePrice();
            }
            mainPriceHolder = mainPrice;
            priceTextView.setText(getString(R.string.price_item_1) + " " + mainPrice + " " + getString(R.string.price_item_2));
        } else {
            if (isInEgypt) {
                mainPriceHolder = adapter.getPiecesNumber() * pattenList.getPrices().getLocalPiecePrice();
                priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() * pattenList.getPrices().getLocalPiecePrice() + " " + getString(R.string.price_item_2));
            } else {
                mainPriceHolder = adapter.getPiecesNumber() * pattenList.getPrices().getWorldPiecePrice();
                priceTextView.setText(getString(R.string.price_item_1) + " " + adapter.getPiecesNumber() * pattenList.getPrices().getWorldPiecePrice() + " " + getString(R.string.price_item_2));
            }
        }
        if (isCouponCodeApplied) {
            applyCouponCode(true);
        }
    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
