
package com.WattanArt.Dagger.component;

import android.app.Application;
import android.content.Context;

import com.WattanArt.MyApplication;
import com.WattanArt.Dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = ApplicationModule.class )
public interface ApplicationComponent {

    void inject(MyApplication app);

    Context context();

    Application application();
}