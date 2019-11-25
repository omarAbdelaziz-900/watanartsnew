package com.WattanArt.ui.Category;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends BaseActivity implements CategoryMobileTypesAdapter.ItemListenerOfItems
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

    ArrayList<CategoryItemModel> categoryItemModels;
    CategoryMobileTypesAdapter categoryTypesAdapter;

    CategoryT_ShirtTypesAdapter categoryT_shirtTypesAdapter;
    private int[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        initialData();
//        initItemsRecyclerView();
        initT_ShirtItemsRecyclerView();
        clickMobileType();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    private void initItemsRecyclerView() {
        categoryItemModels=new ArrayList<>();



        categoryItemModels.add(new CategoryItemModel("image0"));
        categoryItemModels.add(new CategoryItemModel("image1k"));
        categoryItemModels.add(new CategoryItemModel("image2image1"));
        categoryItemModels.add(new CategoryItemModel("image3"));
        categoryItemModels.add(new CategoryItemModel("image4"));
        categoryItemModels.add(new CategoryItemModel("image5nnnn"));
        categoryItemModels.add(new CategoryItemModel("image6"));
        categoryItemModels.add(new CategoryItemModel("image7cc"));
        GridLayoutManager manager = new GridLayoutManager(CategoryActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recycler_mobile_types.setLayoutManager(manager);
        recycler_mobile_types.setItemAnimator(new DefaultItemAnimator());
        recycler_mobile_types.setNestedScrollingEnabled(false);
        recycler_mobile_types.setHasFixedSize(true);
        recycler_mobile_types.scrollToPosition(0);
        categoryTypesAdapter = new CategoryMobileTypesAdapter(CategoryActivity.this, categoryItemModels ,this);
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
                iphone_txt.setTextColor(Color.parseColor("#f5a43b"));
                view_iphone.setBackgroundColor(Color.parseColor("#f5a43b"));

                android_txt.setTextColor(Color.parseColor("#000000"));
                view_android.setBackgroundColor(Color.parseColor("#FFBDBDBD"));

                view_android.getLayoutParams().height = 1;
                view_iphone.getLayoutParams().height = 4;

            }
        });


        relative_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android_txt.setTextColor(Color.parseColor("#f5a43b"));
                view_android.setBackgroundColor(Color.parseColor("#f5a43b"));

                iphone_txt.setTextColor(Color.parseColor("#000000"));
                view_iphone.setBackgroundColor(Color.parseColor("#FFBDBDBD"));

                view_android.getLayoutParams().height = 4;
                view_iphone.getLayoutParams().height = 1;
            }
        });
    }

    public void initialData(){
        android_txt.setTextColor(Color.parseColor("#f5a43b"));
        view_android.setBackgroundColor(Color.parseColor("#f5a43b"));
        view_android.getLayoutParams().height = 4;
    }


    private void initT_ShirtItemsRecyclerView() {
        categoryItemModels=new ArrayList<>();
        imgs = new int[]{R.drawable.phonecovertest,
                R.drawable.tshirt_test,
                R.drawable.test,
                R.drawable.tshirt_test,
                R.drawable.tshirt_test,
                R.drawable.tshirt_test,
                R.drawable.tshirt_test,
                R.drawable.tshirt_test};


        categoryItemModels.add(new CategoryItemModel("image0",imgs[0]));
        categoryItemModels.add(new CategoryItemModel("image1k",imgs[1]));
        categoryItemModels.add(new CategoryItemModel("image2image1",imgs[2]));
        categoryItemModels.add(new CategoryItemModel("image3",imgs[3]));
        categoryItemModels.add(new CategoryItemModel("image4",imgs[4]));
        categoryItemModels.add(new CategoryItemModel("image5nnnn",imgs[5]));
        categoryItemModels.add(new CategoryItemModel("image6",imgs[6]));
        categoryItemModels.add(new CategoryItemModel("image7cc",imgs[7]));
        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recycler_mobile_types.setLayoutManager(manager);
        recycler_mobile_types.setItemAnimator(new DefaultItemAnimator());
        recycler_mobile_types.setNestedScrollingEnabled(false);
        recycler_mobile_types.setHasFixedSize(true);
        recycler_mobile_types.scrollToPosition(0);
        categoryT_shirtTypesAdapter = new CategoryT_ShirtTypesAdapter(this, categoryItemModels ,this);
        recycler_mobile_types.setAdapter(categoryT_shirtTypesAdapter);
    }

    @Override
    public void onT_ShirtItemsClickFromAdapter(int position) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }
}
