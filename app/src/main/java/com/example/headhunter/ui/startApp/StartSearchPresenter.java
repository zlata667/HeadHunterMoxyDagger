package com.example.headhunter.ui.startApp;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.example.headhunter.AppDelegate;
import com.example.headhunter.common.BasePresenter;
import com.example.headhunter.data.api.HeadHunterApi;
import com.example.headhunter.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class StartSearchPresenter extends BasePresenter<StartSearchView>{

    @Inject
    Context mContext;
    @Inject
    HeadHunterApi mApi;

    @Inject
    StartSearchPresenter(){
    }


    public void loadRegions(){
        compositeDisposable.add(mApi.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    getViewState().showRefresh();
                })
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        countries -> getViewState().loadRegions(countries),
                        throwable -> getViewState().showError()
                ));
    }


    public void openVacanciesFragment(){
        getViewState().openVacanciesFragment();
    }
}
