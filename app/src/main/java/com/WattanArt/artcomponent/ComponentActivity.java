package com.WattanArt.artcomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import com.WattanArt.R;


public class ComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);
        ImageCaseComponent component = findViewById(R.id.componentView);
        DimensionData dimensionData;
        dimensionData = new DimensionData(700, 1024, 295,
                624, 0, 0, 80, 40,
                40, 0, 0);


        component.initData(dimensionData, new Pair<>(R.drawable.tshirt_test,R.drawable.test));
        CoverView cover = (CoverView) component.getComponentView(R.id.cover);
        cover.setData(R.drawable.test);
        cover.setZoom(3.5f, 0.65f, 0.098f);

    }


}
