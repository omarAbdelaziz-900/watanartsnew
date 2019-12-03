package com.WattanArt.ui.HomeFragment;

import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface HomeMvpView extends MvpView{

    void returnIntroData(HomeIntroResponseModel responseModel);

}
