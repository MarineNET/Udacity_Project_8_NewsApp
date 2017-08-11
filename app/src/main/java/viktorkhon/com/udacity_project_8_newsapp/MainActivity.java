package viktorkhon.com.udacity_project_8_newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String JSON_REQUEST =
    "http://content.guardianapis.com/us/technology?order-by=newest&api-key=test&q=technology&from-date=2017-01-01&page=1";

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        TextView emptyView = (TextView) findViewById(R.id.emptyText);

        ListView newsView = (ListView) findViewById(R.id.newsListView);
        newsView.setAdapter(newsAdapter);
        if (isConnected()) {
            getLoaderManager().initLoader(0, null, MainActivity.this);
            emptyView.setText("");
        } else {
            emptyView.setText(R.string.no_internet);
        }

        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsAdapter.getItem(position);

                Uri uri = Uri.parse(news.getWebUrl());
                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                // Send the intent to launch a new activity
                if (openBrowser.resolveActivity(getPackageManager()) != null) {
                    startActivity(openBrowser);
                }
            }
        });
    }

    final boolean isConnected() {
        // Class that answers queries about the state of network connectivity.
        // It also notifies applications when network connectivity changes.
        ConnectivityManager cm = (ConnectivityManager)
                // Context.CONNECTIVITY_SERVICE:
                // Use with getSystemService(Class) to retrieve a ConnectivityManager for handling
                // management of network connections.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Describes the status of a network interface.
        // Use getActiveNetworkInfo() to get an instance that represents the current network connection.
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // isConnectedOrConnecting - Indicates whether network connectivity exists or is
        // in the process of being established
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, JSON_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        newsAdapter.clear();
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
