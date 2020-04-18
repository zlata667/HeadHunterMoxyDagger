package com.example.headhunter.ui.startApp;

import androidx.fragment.app.Fragment;

import com.example.headhunter.common.SingleFragmentActivity;

public class StartSearchActivity extends SingleFragmentActivity{

    @Override
    protected Fragment getFragment(){
        return StartSearchFragment.newInstance();
    }
}
