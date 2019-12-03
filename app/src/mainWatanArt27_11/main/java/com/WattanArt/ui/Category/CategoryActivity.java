package com.WattanArt.ui.Category;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends BaseActivity implements CategoryMvpView,CategoryMobileTypesAdapter.ItemListenerOfItems
,CategoryT_ShirtTypesAdapter.ItemListenerOfItems{

    @BindView(R.id.recycler_mobile_types)
    RecyclerView recycler_mobile_types;

    @BindView(R.id.relative_iphone)
    RelativeLayout relative_iphone;

    @BindView(R.id.relative_android)
    RelativeLayout relative_android;

    @BindView(R.id.iphone_txt)
    CustomeTextView iphone_txt;

    @BindView(R.id.view_iphone)
    View view_iphone;

    @BindView(R.id.android_txt)
    CustomeTextView android_txt;

    @BindView(R.id.view_android)
    View view_android;

    @BindView(R.id.nodata_txt)
    CustomeTextViewBold nodata_txt;

    @BindView(R.id.toolbar_tv_title)
    public CustomeTextViewBold mToolbarTitleTextView;

//    ArrayList<CategoryItemModel> categoryItemModels;
    List<CategoryMobileRsponseModel.ResultBean.ItemsBean> categoryItemModels;
    CategoryMobileTypesAdapter categoryTypesAdapter;

    CategoryT_ShirtTypesAdapter categoryT_shirtTypesAdapter;
    private int[] imgs;
    UserData userData;
    int cateId=0;
    int mobileType=0;//Android
//    1 Iphone

    @Inject
    CategoryMvpPresenter<CategoryMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(getString(R.string.category));

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        Intent intent=getIntent();
        if (intent.hasExtra("catId")){
            cateId=intent.getIntExtra("catId",0);
            Log.e("cateId",cateId+"");
        }
        userData = new UserData();
        if (isNetworkConnected()) {
            mPresenter.getSubCategory(userData.getLocalization(this),cateId);
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }

        initialData();

        clickMobileType();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    private void initItemsRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(CategoryActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recycler_mobile_types.setLayoutManager(manager);
        recycler_mobile_types.setItemAnimator(new DefaultItemAnimator());
        recycler_mobile_types.setNestedScrollingEnabled(false);
        recycler_mobile_types.setHasFixedSize(true);
        recycler_mobile_types.scrollToPosition(0);
        categoryTypesAdapter = new CategoryMobileTypesAdapter(mobileType,CategoryActivity.this, categoryItemModels ,this);
        recycler_mobile_types.setAdapter(categoryTypesAdapter);
    }

    @Override
    public void onItemsClickFromAdapter(int position) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }

    public void clickMobileType(){
        relative_iphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryTypesAdapter!=null)
                categoryTypesAdapter.notifyDataSetChanged();
                mobileType=1;
                iphone_txt.setTextColor(Color.parseColor("#f5a43b"));
                view_iphone.setBackgroundColor(Color.parseColor("#f5a43b"));

                android_txt.setTextColor(Color.parseColor("#000000"));
                view_android.setBackgroundColor(Color.parseColor("#FFBDBDBD"));

                view_android.getLayoutParams().height = 1;
                view_iphone.getLayoutParams().height = 4;

                if (isNetworkConnected()) {
                    mPresenter.getSubCategory(userData.getLocalization(CategoryActivity.this),cateId);
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));
                }

            }
        });


        relative_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryTypesAdapter!=null)
                categoryTypesAdapter.notifyDataSetChanged();
                mobileType=0;
                android_txt.setTextColor(Color.parseColor("#f5a43b"));
                view_android.setBackgroundColor(Color.parseColor("#f5a43b"));

                iphone_txt.setTextColor(Color.parseColor("#000000"));
                view_iphone.setBackgroundColor(Color.parseColor("#FFBDBDBD"));

                view_android.getLayoutParams().height = 4;
                view_iphone.getLayoutParams().height = 1;

                if (isNetworkConnected()) {
                    mPresenter.getSubCategory(userData.getLocalization(CategoryActivity.this),cateId);
                } else {
                    showMessage(getString(R.string.error_no_internet_connection));
                }
            }
        });
    }

    public void initialData(){
        android_txt.setTextColor(Color.parseColor("#f5a43b"));
        view_android.setBackgroundColor(Color.parseColor("#f5a43b"));
        view_android.getLayoutParams().height = 4;
    }

    @Override
    public void onT_ShirtItemsClickFromAdapter(int position) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnSubCategory(CategoryMobileRsponseModel responseModel) {

        if (responseModel.getResult()!=null) {
            if (responseModel.getResult().isEmpty()){
                recycler_mobile_types.setVisibility(View.GONE);
                nodata_txt.setText("no data");
                nodata_txt.setVisibility(View.VISIBLE);
            }else {
                recycler_mobile_types.setVisibility(View.VISIBLE);
                nodata_txt.setVisibility(View.GONE);
                categoryItemModels = responseModel.getResult().get(mobileType).getItems();
                if (categoryItemModels.isEmpty()){
                    recycler_mobile_types.setVisibility(View.GONE);
                    nodata_txt.setVisibility(View.VISIBLE);
                    nodata_txt.setText("no data");
                }
                Log.e("categmoddd", categoryItemModels + "\n" + categoryItemModels.size());
                initItemsRecyclerView();
            }
        }else {
            recycler_mobile_types.setVisibility(View.GONE);
            nodata_txt.setText("no data");
            nodata_txt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (categoryItemModels!=null) {
            if (!categoryItemModels.isEmpty())
                categoryItemModels.clear();
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
