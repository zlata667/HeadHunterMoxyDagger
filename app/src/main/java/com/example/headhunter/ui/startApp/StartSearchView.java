package com.example.headhunter.ui.startApp;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.headhunter.common.BaseView;
import com.example.headhunter.data.model.Country;

import java.util.List;

public interface StartSearchView extends BaseView{

    void loadRegions(List<Country> countries);

    @StateStrategyType(SkipStrategy.class)
    void openVacanciesFragment();
}
