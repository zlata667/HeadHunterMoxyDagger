package com.example.headhunter.ui.startApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.headhunter.AppDelegate;
import com.example.headhunter.R;
import com.example.headhunter.common.PresenterFragment;
import com.example.headhunter.common.RefreshOwner;
import com.example.headhunter.common.Refreshable;
import com.example.headhunter.data.model.Country;
import com.example.headhunter.di.AppComponent;
import com.example.headhunter.ui.vacancies.VacanciesActivity;
import com.example.headhunter.ui.vacancies.VacanciesFragment;
import com.example.headhunter.utils.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StartSearchFragment extends PresenterFragment
        implements StartSearchView, Refreshable{

    private RefreshOwner refreshOwner;
    private Map<String, String> regionMap = new HashMap<>();
    private View searchView;
    private AutoCompleteTextView autoCompleteTextView;
    private EditText editTextSearch;

    @Inject
    @InjectPresenter
    StartSearchPresenter presenter;

    @ProvidePresenter
    StartSearchPresenter providePresenter(){
        return presenter;
    }

    @Override
    protected StartSearchPresenter getPresenter(){
        return presenter;
    }

    static Fragment newInstance(){
        return new StartSearchFragment();
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
        return inflater.inflate(R.layout.fr_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        searchView = view.findViewById(R.id.view_search);
        editTextSearch = view.findViewById(R.id.et_search);
        view.findViewById(R.id.btn_search).setOnClickListener(v -> {
            onButtonClick();
        });
        autoCompleteTextView = view.findViewById(R.id.et_city);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        searchView.setVisibility(View.VISIBLE);
        onRefreshData();
    }

    @Override
    public void onDetach(){
        refreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData(){
        presenter.loadRegions();
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
        if (autoCompleteTextView.getText() == null){
            Toast.makeText(getActivity(), "Не удалось загрузить список регионов", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadRegions(List<Country> countries){
        searchView.setVisibility(View.VISIBLE);
        bind(countries);
    }

    @Override
    public void openVacanciesFragment(){
        Intent intent = new Intent(getActivity(), VacanciesActivity.class);
        Bundle args = new Bundle();

        String region = autoCompleteTextView.getText().toString();
        if (regionMap.containsKey(region)){
            args.putString(VacanciesFragment.SEARCH_REGION, regionMap.get(region));
        }

        args.putString(VacanciesFragment.SEARCH_TEXT, editTextSearch.getText().toString());
        intent.putExtra(VacanciesActivity.SEARCH_KEY, args);
        startActivity(intent);
    }

    private void onButtonClick(){
        presenter.openVacanciesFragment();
    }

    private void bind(List<Country> countries){
        for (Country country : countries){
            for (Country.Region region : country.getAreas()){
                regionMap.put(region.getName(), region.getId());
            }
        }

        List<String> regionNamesList = new ArrayList<>(regionMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, regionNamesList);
        autoCompleteTextView.setAdapter(adapter);
    }
}
