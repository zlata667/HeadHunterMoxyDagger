package com.example.headhunter.ui.vacancies;

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
public class VacanciesPresenter extends BasePresenter<VacanciesView>{

    @Inject
    Context mContext;
    @Inject
    HeadHunterApi mApi;

    @Inject
    VacanciesPresenter(){
    }

    public void getVacancies(String searchText, String searchRegion){
        compositeDisposable.add(mApi.getVacancies(searchText, searchRegion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        vacancies -> getViewState().showVacancies(vacancies.getItems()),
                        throwable -> getViewState().showError()
                ));
    }

    public void openVacancyFragment(String vacancyId){
        getViewState().openVacancyFragment(vacancyId);
    }
}
