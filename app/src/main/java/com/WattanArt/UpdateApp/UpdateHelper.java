package com.WattanArt.UpdateApp;

import android.content.Context;

public class UpdateHelper {
    public static String KEY_UPDATE_ENABLE="is_update";
    public static String KEY_UPDATE_VERSION="version";
    public static String KEY_UPDATE_URL="update_url";

    public interface onUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }

    public static Builder with(Context context){
        return new Builder(context);
    }

    private Context context;
    private onUpdateCheckListener onUpdateCheckListener;

    public UpdateHelper(Context context, UpdateHelper.onUpdateCheckListener onUpdateCheckListener) {
        this.context = context;
        this.onUpdateCheckListener = onUpdateCheckListener;
    }

    public void check(){
        
    }
    public static class Builder{
        private Context context;
        private onUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateCheck(UpdateHelper.onUpdateCheckListener onUpdateCheckListener) {
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }
    }
}
