package com.example.headhunter.di;

import android.content.Context;
import android.view.View;

import com.example.headhunter.data.api.HeadHunterApi;
import com.example.headhunter.ui.startApp.StartSearchFragment;
import com.example.headhunter.ui.startApp.StartSearchPresenter;
import com.example.headhunter.ui.vacancies.VacanciesFragment;
import com.example.headhunter.ui.vacancies.VacanciesPresenter;
import com.example.headhunter.ui.vacancy.VacancyFragment;
import com.example.headhunter.ui.vacancy.VacancyPresenter;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, NetworkModule.class})
public interface AppComponent{

    Context getContext();

    void inject(StartSearchFragment injector);
    void inject(VacanciesFragment injector);
    void inject(VacancyFragment injector);

    void inject(StartSearchPresenter injector);
    void inject(VacanciesPresenter injector);
    void inject(VacancyPresenter injector);

}
