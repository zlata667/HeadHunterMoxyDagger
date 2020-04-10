package com.example.headhunter;

import android.app.Application;

import com.example.headhunter.di.AppComponent;
import com.example.headhunter.di.ContextModule;
import com.example.headhunter.di.DaggerAppComponent;
import com.example.headhunter.di.NetworkModule;

public class AppDelegate extends Application{

    private static AppComponent sAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        sAppComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent(){
        return sAppComponent;
    }
}
