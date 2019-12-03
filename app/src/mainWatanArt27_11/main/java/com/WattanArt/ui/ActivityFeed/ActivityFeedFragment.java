package com.WattanArt.ui.ActivityFeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.ui.base.BaseFragment;
import com.WattanArt.ui.creationFields.ItemFieldsAdpater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityFeedFragment extends BaseFragment implements FeedTypeAdapter.ItemListener
,FeedItemsOfTypesAdapter.ItemListenerOfItems{


    @BindView(R.id.recycler_all_types)
    RecyclerView recycler_all_types;

    @BindView(R.id.recycler_all_items_of_types)
    RecyclerView recycler_all_items_of_types;

    FeedTypeAdapter feedTypeAdapter;
    FeedItemsOfTypesAdapter feedItemsOfTypesAdapter;

     ArrayList<ActivityFeedTypesModel> activityFeedTypesModels;
     ArrayList<ItemsOfTypesModel> itemsOfTypesModels;
    private int[] imgs;
    View view;

    public ActivityFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

                view = inflater.inflate(R.layout.fragment_activity_feed, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        initItemsRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        activityFeedTypesModels =new ArrayList<>();
        activityFeedTypesModels.add(new ActivityFeedTypesModel("All"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Tablou"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Mobile Covers"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("T_Shirt"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Flash Memory"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Mogs"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Keyboards"));
        activityFeedTypesModels.add(new ActivityFeedTypesModel("Pc Computers"));
//        recycler_all_types.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_all_types.setLayoutManager(manager);
        recycler_all_types.setItemAnimator(new DefaultItemAnimator());
        recycler_all_types.setNestedScrollingEnabled(false);
        recycler_all_types.setHasFixedSize(true);
        recycler_all_types.scrollToPosition(0);
        feedTypeAdapter = new FeedTypeAdapter(getActivity(), activityFeedTypesModels,this);
        recycler_all_types.setAdapter(feedTypeAdapter);
    }

    private void initItemsRecyclerView() {
        itemsOfTypesModels=new ArrayList<>();
        imgs = new int[]{R.drawable.phonecovertest,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test,
                R.drawable.test};


        itemsOfTypesModels.add(new ItemsOfTypesModel("image0",imgs[0]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image1k",imgs[1]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image2image1",imgs[2]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image3",imgs[3]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image4",imgs[4]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image5nnnn",imgs[5]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image6",imgs[6]));
        itemsOfTypesModels.add(new ItemsOfTypesModel("image7cc",imgs[7]));
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recycler_all_items_of_types.setLayoutManager(manager);
        recycler_all_items_of_types.setItemAnimator(new DefaultItemAnimator());
        recycler_all_items_of_types.setNestedScrollingEnabled(false);
        recycler_all_items_of_types.setHasFixedSize(true);
        recycler_all_items_of_types.scrollToPosition(0);
        feedItemsOfTypesAdapter = new FeedItemsOfTypesAdapter(getActivity(), itemsOfTypesModels ,this);
        recycler_all_items_of_types.setAdapter(feedItemsOfTypesAdapter);
    }

    @Override
    public void onTypeClickFromAdapter(int position) {
        Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemsClickFromAdapter(int position) {
        Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
    }
}
