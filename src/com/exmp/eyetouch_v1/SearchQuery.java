package com.exmp.eyetouch_v1;

import android.app.Application;

public class SearchQuery extends Application {

    private String mSearchText;

    public String getSearchText() {
        return mSearchText;
    }

    public void setSearchText(String searchText) {
        this.mSearchText = searchText;
    }

}

