package com.example.android.booklisting;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<BookItemsClass>> {
    private TextView mEmptyStateTextView;
    private String TAG = "MainActivity.java";
    private final static String USGS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int Books_loader = 1;
    private Uri newUri;
    private String url;
    private ProgressBar bar;
    private EditText bookName;
    private String bookname;
    private ConnectivityManager connMgr;
    private final static String maxRes = "20";
    public static BookAdapter mAdapter;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.loading_spinner);
        bar.setVisibility(View.INVISIBLE);
        final ListView Bookslist = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.search);
        mAdapter = new BookAdapter(MainActivity.this, new ArrayList<BookItemsClass>());
        Bookslist.setAdapter(mAdapter);
        Bookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookItemsClass CurrentBook = mAdapter.getItem(position);

                Uri earthquakeUri = Uri.parse(CurrentBook.getlink());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });
        Button button = (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyStateTextView.setText("");
                Bookslist.setEmptyView(mEmptyStateTextView);
                bookName = (EditText) findViewById(R.id.edittext);
                bookname = bookName.getText().toString();
                connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    bar.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(Books_loader, null, MainActivity.this);
                } else {
                    mEmptyStateTextView.setText(R.string.no_internet);
                }
            }
        });
    }


    @Override
    public Loader<List<BookItemsClass>> onCreateLoader(int i, Bundle bundle) {
        url = USGS_REQUEST_URL + bookname + "&maxResults=20";
        Log.d("MainActivity", url);
        return new BooksLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<BookItemsClass>> loader, List<BookItemsClass> Books) {
        mAdapter.clear();

        if (Books != null && !Books.isEmpty()) {
            ProgressBar bar = (ProgressBar) findViewById(R.id.loading_spinner);
            bar.setVisibility(View.INVISIBLE);
            mAdapter.addAll(Books);
        } else {
            mEmptyStateTextView.setText(R.string.noBooks);
            bar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookItemsClass>> loader) {
        mAdapter.clear();
    }

}
