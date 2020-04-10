package com.example.headhunter.ui.vacancy;

import android.content.Context;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.example.headhunter.AppDelegate;
import com.example.headhunter.common.BasePresenter;
import com.example.headhunter.data.api.HeadHunterApi;
import com.example.headhunter.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class VacancyPresenter extends BasePresenter<VacancyView>{

    @Inject
    Context mContext;
    @Inject
    HeadHunterApi mApi;

    @Inject
    VacancyPresenter(){
        //AppDelegate.getAppComponent().inject(this);
    }

    public void getVacancy(String vacancyId){
        compositeDisposable.add(mApi.getVacancy(vacancyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        vacancy -> getViewState().showVacancy(vacancy),
                        throwable -> getViewState().showError())
        );
    }
}
