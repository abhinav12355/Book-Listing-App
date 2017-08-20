package com.example.android.booklisting;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;


public class BooksLoader extends AsyncTaskLoader<List<BookItemsClass>> {

    private String mUrl;

    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookItemsClass> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<BookItemsClass> booklist = QueryUtils.fetchBooksData(mUrl);
        return booklist;
    }

}
