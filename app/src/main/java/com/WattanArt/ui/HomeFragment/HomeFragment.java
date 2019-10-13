package com.WattanArt.ui.HomeFragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.ui.CanvasPrint.CanvasPrintActivity;
import com.WattanArt.ui.EditImage.EditImageActivity;
import com.WattanArt.ui.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import me.iwf.photopicker.PhotoPicker;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeMvpView {
//public class HomeFragment extends BaseFragment implements HomeMvpView {

    private final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 103;
    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayerFragment youTubePlayerFragment;

    View view;
    @Inject
    HomeMvpPresenter<HomeMvpView> mPresenter;


    @BindView(R.id.titleTV)
    CustomeTextViewBold mTitleTextView;

    @BindView(R.id.descTv)
    CustomeTextView mDiscreptionTextView;

    @BindView(R.id.imagepreview1)
    ImageView mFirstPreviewImageView;

    @BindView(R.id.imagepreview2)
    ImageView mSecondPreviewImageView;

    @BindView(R.id.imagepreview3)
    ImageView mThirdPreviewImageView;
    @BindView(R.id.aboutCanvas_imv)
    ImageView mAboutCanvasImageView;

    private String youtubeID = "";
    String link;
    UserData userData;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);


        userData = new UserData();
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }

        if (isNetworkConnected()) {
            mPresenter.getIntroData(userData.getLocalization(getActivity()));
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }

        if (youTubePlayerFragment == null) {
            youTubePlayerFragment = (YouTubePlayerFragment) Objects.requireNonNull(getActivity()).getFragmentManager().findFragmentById(R.id.youtube_view);
        }
        mAboutCanvasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    startActivity(new Intent(getActivity(), CanvasPrintActivity.class));


                } else {
                    showMessage(getString(R.string.error_no_internet_connection));

                }
            }
        });

        view.findViewById(R.id.create_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Intent intent = new Intent(getActivity(), EditImageActivity.class);
                    intent.putStringArrayListExtra("photos_list", photos);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void returnIntroData(HomeIntroResponseModel responseModel) {
        link = responseModel.getResult().getIntroVideo();
//        link = "https://www.youtube.com/watch?v=hmFBwTl6M-k";
//        link = "https://www.youtube.com/watch?v=7mOfNQTGCi0";
//        link = "https://www.youtube.com/watch?v=djh0mkjoaUY";

        youtubeID = link.substring(32, link.length());
        mTitleTextView.setText(responseModel.getResult().getIntroTitle());
        mDiscreptionTextView.setText(responseModel.getResult().getIntroText());

        if (responseModel.getResult().getImageList() != null && !responseModel.getResult().getImageList().isEmpty()) {
            loadImage(responseModel, 0, mFirstPreviewImageView);
            loadImage(responseModel, 1, mSecondPreviewImageView);
            loadImage(responseModel, 2, mThirdPreviewImageView);
        }

        initYouTube();
    }

    private void loadImage(HomeIntroResponseModel responseModel, int indexOfList, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_not_available);
        options.error(R.drawable.img_not_available);

//        Glide.with(getActivity())
//                .load(Constants.BASE_URL + Constants.UPLOAD +
//                        responseModel.getResult().getImageList().get(indexOfList))
//                .apply(options).into(imageView);

        String path = Constants.BASE_URL + Constants.UPLOAD +
                responseModel.getResult().getImageList().get(indexOfList);
        Log.e("path", path);
//        Picasso.get().load(path.trim())
//                .placeholder(R.drawable.img_not_available)
//                .error(R.drawable.img_not_available)
//                .into(imageView, new com.squareup.picasso.Callback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.e("fdfsfdsf", "fsfsdfsfsf");
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        e.printStackTrace();
//                    }
//                });

        Glide.with(getActivity())
                .asBitmap()
                .load(path.trim())
                .apply(options).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setImageBitmap(resource);
                return true;
            }
        }).into(imageView);

//        Log.e("getImageList", Constants.BASE_URL + Constants.UPLOAD + responseModel.getResult().getImageList().get(0));
//        Log.e("getImageList2", Constants.BASE_URL + Constants.UPLOAD + responseModel.getResult().getImageList().get(1));
//        Log.e("getImageList3", Constants.BASE_URL + Constants.UPLOAD + responseModel.getResult().getImageList().get(2));
//        Log.e("getIntroTitle", responseModel.getResult().getIntroTitle());
//        Log.e("getIntroText", responseModel.getResult().getIntroText());
//        Log.e("link", link);
    }

    private void initYouTube() {
        if (youTubePlayerFragment != null) {
//            youTubePlayerFragment = (YouTubePlayerFragment) Objects.requireNonNull(getActivity()).getFragmentManager().findFragmentById(R.id.youtube_view);


            youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    //Tell the player you want to control the fullscreen change
                    // to make full screen open as orientaion of layout <landscape or portrait

                    youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

                    // to stop full screen
                    if (!b) {
                        youTubePlayer.cueVideo(youtubeID);
                        youTubePlayer.setShowFullscreenButton(false);
                        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

                    }
//                    youTubePlayer.cueVideo(youtubeID);
                    Log.e("youtubeID", youtubeID);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    if (youTubeInitializationResult.isUserRecoverableError()) {
                        youTubeInitializationResult.getErrorDialog(Objects.requireNonNull(getActivity()), RECOVERY_REQUEST).show();
                    } else {
                    }
                }
            });
        }
    }


}
