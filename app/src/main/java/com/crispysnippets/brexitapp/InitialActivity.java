package com.crispysnippets.brexitapp;

/*
 * Created by Christian Pruvost on 14/04/2017.
 */

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/*
aHR0cHM6Ly9lZGNpbmZvcm1hdGlvbi53b3JkcHJlc3MuY29tL2NhdGVnb3J5L2JyZXhpdC9mZWVkLw0KaHR0cDovL3d3dy5lY29ub21pc3QuY29tL3NlY3Rpb25zL2V1cm9wZS9yc3MueG1sDQpodHRwOi8vd3d3LmVjb25vbWlzdC5jb20vc2VjdGlvbnMvYnJpdGFpbi9yc3MueG1sDQpodHRwOi8vb3BlbmV1cm9wZS5vcmcudWsvZmVlZC8/cG9zdF90eXBlPXRvZGF5JnRhZ3M9YnJleGl0DQpodHRwczovL3d3dy50aGVndWFyZGlhbi5jb20vdWstbmV3cy9icmV4aXQtYnJpdGFpbi9yc3MNCmh0dHBzOi8vd3d3LnRoZWd1YXJkaWFuLmNvbS9idXNpbmVzcy9zZXJpZXMvYnJleGl0LWRhdGEtc25hcHNob3QvcnNzDQpodHRwczovL3d3dy50aGVndWFyZGlhbi5jb20vcG9saXRpY3Mvc2VyaWVzL2V1LXJlZmVyZW5kdW0tbW9ybmluZy1icmllZmluZy9yc3M=
 */

public class InitialActivity extends AppCompatActivity {

    private static final String TAG = AppCompatActivity.class.getName();

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mFetchFeedButton;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView mFeedTitleTextView;
    private TextView mFeedLinkTextView;
    private TextView mFeedDescriptionTextView;

    private List<RssFeedModel> mFeedModelList;
    private String mFeedTitle;
    private String mFeedLink;
    private String mFeedDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mEditText = (EditText) findViewById(R.id.rssFeedEditText);
        mFetchFeedButton = (Button) findViewById(R.id.fetchFeedButton);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mFeedTitleTextView = (TextView) findViewById(R.id.feedTitle);
        mFeedDescriptionTextView = (TextView) findViewById(R.id.feedDescription);
        mFeedLinkTextView = (TextView) findViewById(R.id.feedLink);
        //mFeedPubDateTextView = (TextView) findViewById(R.id.feedPubDate);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFetchFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchFeedTask().execute((Void) null);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });
    }

    /**
     * Class to asynchronously fetch the data online, while the UI views are getting displayed
     */
    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            mFeedTitle = null;
            mFeedLink = null;
            mFeedDescription = null;
            mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
            mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
            mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
            urlLink = mEditText.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink)) {
                //return false;
                urlLink = "https://www.theguardian.com/uk-news/brexit-britain/rss";
            }

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://")) {
                    urlLink = "http://" + urlLink;
                }

                Log.d(TAG,"Debug : urlLink is  " + urlLink);
                URL url = new URL(urlLink);

                // Unable to open with a port number...
                InputStream inputStream = url.openConnection().getInputStream();

                // Parse the RSS Feed
                mFeedModelList = RSSFeedParser.parseFeed(inputStream,false);

                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error Parsing the data", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
                mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
                mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
                // Fill RecyclerView
                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
            } else {
                Toast.makeText(InitialActivity.this,
                        "I cannot connect to this feed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
