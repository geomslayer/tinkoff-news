package com.geomslayer.tinkoffnews.details;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.geomslayer.tinkoffnews.R;
import com.geomslayer.tinkoffnews.models.Title;
import com.geomslayer.tinkoffnews.utils.Utils;


public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "DetailsActivity";

    private static final String NEWS_ID = "news_id";
    private static final int DETAILS_LOADER_ID = 1;

    private Title title;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = (Title) getIntent().getSerializableExtra(Utils.TITLE_EXTRA);
        ((TextView) findViewById(R.id.title)).setText(Utils.toSpanned(title.getText()));

        initContent();
    }

    private void initContent() {
        content = (TextView) findViewById(R.id.content);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        Bundle args = new Bundle();
        args.putLong(NEWS_ID, title.getId());
        getSupportLoaderManager().initLoader(DETAILS_LOADER_ID, args, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new DetailsLoader(this, args.getLong(NEWS_ID));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(TAG, "onLoadFinished: " + data);
        if (data != null) {
            content.setText(Utils.toSpanned(data));
        } else {
            // notify
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
