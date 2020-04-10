package com.example.headhunter.ui.vacancy;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.headhunter.AppDelegate;
import com.example.headhunter.R;
import com.example.headhunter.common.PresenterFragment;
import com.example.headhunter.common.RefreshOwner;
import com.example.headhunter.common.Refreshable;
import com.example.headhunter.data.model.Vacancy;
import com.example.headhunter.ui.startApp.StartSearchPresenter;
import com.example.headhunter.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VacancyFragment extends PresenterFragment
        implements VacancyView, Refreshable{

    public static final String VACANCY_ID = "VACANCY_ID";

    private RefreshOwner refreshOwner;
    private NestedScrollView vacancyView;
    private View errorView;
    private TextView vacancyName;
    private TextView employerName;
    private TextView salary;
    private TextView vacancyDescription;
    private String vacancyId;

    @Inject
    @InjectPresenter
    VacancyPresenter presenter;

    @ProvidePresenter
    VacancyPresenter providePresenter(){
        return presenter;
    }

    @Override
    protected VacancyPresenter getPresenter(){
        return presenter;
    }

    static Fragment newInstance(Bundle args){
        VacancyFragment fragment = new VacancyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        AppDelegate.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            refreshOwner = ((RefreshOwner) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fr_vacancy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        vacancyView = view.findViewById(R.id.view_vacancy);
        errorView = view.findViewById(R.id.errorView);

        vacancyName = view.findViewById(R.id.vacancy_name);
        employerName = view.findViewById(R.id.employer_name);
        salary = view.findViewById(R.id.salary);
        vacancyDescription = view.findViewById(R.id.vacancy_description);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            vacancyId = getArguments().getString(VACANCY_ID);
        }

        if (getActivity() != null){
            getActivity().setTitle("Vacancy");
        }
        onRefreshData();
    }

    @Override
    public void onRefreshData(){
        presenter.getVacancy(vacancyId);
    }

    private void bind(Vacancy vacancy){
        vacancyName.setText(vacancy.getName());
        employerName.setText(vacancy.getEmployer().getName());
        if (vacancy.getSalary() != null){
            salary.setText(String.valueOf(vacancy.getSalary().getFrom()).concat(" ").concat(vacancy.getSalary().getCurrency()));
        }
        vacancyDescription.setText(Html.fromHtml(vacancy.getDescription()));
    }

    @Override
    public void onDetach(){
        refreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showRefresh(){
        refreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh(){
        refreshOwner.setRefreshState(false);
    }

    @Override
    public void showError(){
        if (vacancyView.getVisibility() != View.VISIBLE){
            errorView.setVisibility(View.VISIBLE);
            vacancyView.setVisibility(View.GONE);
        }

    }

    @Override
    public void showVacancy(@NonNull Vacancy vacancy){
        errorView.setVisibility(View.GONE);
        vacancyView.setVisibility(View.VISIBLE);
        bind(vacancy);
    }
}
