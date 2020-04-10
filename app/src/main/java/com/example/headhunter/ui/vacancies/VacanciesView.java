package com.example.headhunter.ui.vacancies;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.headhunter.common.BaseView;
import com.example.headhunter.data.model.Vacancies;

import java.util.List;

public interface VacanciesView extends BaseView{

    void showVacancies(@NonNull List<Vacancies.ItemsBean> vacancies);

    @StateStrategyType(SkipStrategy.class)
    void openVacancyFragment(@NonNull String vacancyId);
}
