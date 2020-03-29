package com.dragster.android.information.system.my.android.models;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    public void setIndex(String index) {
        mTitle.setValue(index);
    }


}
