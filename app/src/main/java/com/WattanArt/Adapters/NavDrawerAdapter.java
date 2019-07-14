package com.WattanArt.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.R;
import com.WattanArt.Utils.NavDrawerItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class NavDrawerAdapter extends BaseAdapter {
    Activity activity;
    private static LayoutInflater inflater = null;
    List<NavDrawerItem> navDrawerItems;

    public NavDrawerAdapter(Activity activity, List<NavDrawerItem> navDrawerItems) {
// TODO Auto-generated constructor stub
        this.activity = activity;
        this.navDrawerItems = navDrawerItems;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return navDrawerItems == null ? 0 : navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv_title;
        ImageView im_icon;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder = new Holder();
        View view;
        view = inflater.inflate(R.layout.nav_drawer_main_item, null);

        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.im_icon = (ImageView) view.findViewById(R.id.im_icon);

        holder.tv_title.setText(navDrawerItems.get(position).title);

        Glide.with(activity.getApplicationContext()).load(navDrawerItems.get(position).resourceId).into(holder.im_icon);

        return view;
    }

}