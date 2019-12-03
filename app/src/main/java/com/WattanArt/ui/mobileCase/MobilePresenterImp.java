package com.WattanArt.ui.mobileCase;

import android.util.Log;
import android.widget.Toast;
import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.model.Response.ImageUploadResponseModel;;
import com.WattanArt.model.ThrowableModel;
import com.WattanArt.network.ApiService;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;
import com.google.gson.Gson;
import java.io.File;
import java.util.List;

import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Android Team on 1/18/2018.
 */

public class MobilePresenterImp<V extends MobileMvpView> extends BasePresenter<V> implements MobileMvpPresenter<V> {
    int serverUploadIndex = 0;
    File filetoUploadForMobile = null;
    File filetoUploadForCover = null;

    @Inject
    public MobilePresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void returnUploadedImageForMobile(List<String> listPaths,File filePath) {

//        getMvpView().showLoading();

        getMvpView().showLoading();
        int index = serverUploadIndex + 1;

        filetoUploadForMobile = new File(listPaths.get(0));
        String thisFileName;

        if (getExtension(listPaths.get(0)).toLowerCase().contains("png")) {
            thisFileName = getNameWithOutExtension(filetoUploadForMobile.getName()) + ".png.part_" + index + "." +1;
        } else {
            thisFileName = getNameWithOutExtension(filetoUploadForMobile.getName()) + ".jpg.part_" + index + "." + 1;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);

//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", filePath.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filePath.getName());



        ApiService getResponse = AppDataManager.getApi();
        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);


        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {

//                Log.e("Stateee",response.body().getState()+"");
//
                getMvpView().returnUploadedImageForMobile(response.body());

                getMvpView().hideLoading();

                if (response.code() == 200) {
                    if (response.body().getState() == 1) {
                        //could not find file
                        Toast.makeText(MyApplication.getAppContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 2) {
                        //part file catch from service
                        Toast.makeText(MyApplication.getAppContext(), "Error 2", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 3) {
                        //success in part and complete upload other parts , file name is uploaded parts concatenated with ,
//                        (listPaths);
                    } else if (response.body().getState() == 4) {
                        //all parts uploaded but error to join them by service
                        Toast.makeText(MyApplication.getAppContext(), "Error 4", Toast.LENGTH_SHORT).show();


                    } else if (response.body().getState() == 5) {
                        //image completed successfully
                        try {
//                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
//                            orderDetailsItems.setMainImage(response.body().getFileName());
//                            orderDetailsItems.setPatternID(10);
//                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
//                            orderDetailsItemsList.add(orderDetailsItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    Log.e("TEST on success", "-->" + new Gson().toJson(call.request()));

                } else {
                    onFailure(call, new Throwable());
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
                //TODO  : image upload failed , add to faillistNew list to upload later
                Log.e("TEST", "-->" + new Gson().toJson(call.request().body()));
                Log.e("TEST", "-->" + new Gson().toJson(call.request()));

                ThrowableModel model = new Gson().fromJson(new Gson().toJson(call.request().body()), ThrowableModel.class);
                t.printStackTrace();
                Toast.makeText(MyApplication.getAppContext(),
                        MyApplication.getAppContext().getString(R.string.slow_connection)
                        , Toast.LENGTH_SHORT).show();

                    getMvpView().hideLoading();

            }
        });

    }

    @Override
    public void returnUploadedImageForCover(List<String> list, File filePath) {


        getMvpView().showLoading();

        getMvpView().showLoading();
        int index = serverUploadIndex + 1;

        filetoUploadForCover = new File(list.get(0));
        String thisFileName;

        if (getExtension(list.get(0)).toLowerCase().contains("png")) {
            thisFileName = getNameWithOutExtension(filetoUploadForCover.getName()) + ".png.part_" + index + "." +1;
        } else {
            thisFileName = getNameWithOutExtension(filetoUploadForCover.getName()) + ".jpg.part_" + index + "." + 1;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);

//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", filePath.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filePath.getName());



        ApiService getResponse = AppDataManager.getApi();
        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);


        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {

//                Log.e("Stateee",response.body().getState()+"");
//
                getMvpView().returnUploadedImageForCover(response.body());

                getMvpView().hideLoading();

//                ViewHelper.hideProgressDialog();
                if (response.code() == 200) {
                    if (response.body().getState() == 1) {
                        //could not find file
                        Toast.makeText(MyApplication.getAppContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 2) {
                        //part file catch from service
                        Toast.makeText(MyApplication.getAppContext(), "Error 2", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 3) {
                        //success in part and complete upload other parts , file name is uploaded parts concatenated with ,
//                        (listPaths);
                    } else if (response.body().getState() == 4) {
                        //all parts uploaded but error to join them by service
                        Toast.makeText(MyApplication.getAppContext(), "Error 4", Toast.LENGTH_SHORT).show();


                    } else if (response.body().getState() == 5) {
                        //image completed successfully
                        try {
//                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
//                            orderDetailsItems.setMainImage(response.body().getFileName());
//                            orderDetailsItems.setPatternID(10);
//                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
//                            orderDetailsItemsList.add(orderDetailsItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    Log.e("TEST on success", "-->" + new Gson().toJson(call.request()));

                } else {
                    onFailure(call, new Throwable());
                }

            }

            @Override
            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
                //TODO  : image upload failed , add to faillistNew list to upload later
                Log.e("TEST", "-->" + new Gson().toJson(call.request().body()));
                Log.e("TEST", "-->" + new Gson().toJson(call.request()));

                ThrowableModel model = new Gson().fromJson(new Gson().toJson(call.request().body()), ThrowableModel.class);
                t.printStackTrace();
                Toast.makeText(MyApplication.getAppContext(),
                        MyApplication.getAppContext().getString(R.string.slow_connection)
                        , Toast.LENGTH_SHORT).show();

                getMvpView().hideLoading();

            }
        });

    }

    private String getExtension(String someFilepath) {
        String ext = someFilepath.substring(someFilepath.lastIndexOf("."));
        Log.e("EXT", "" + ext);
        return ext;
    }

    private String getNameWithOutExtension(String s) {
        return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
    }
}
