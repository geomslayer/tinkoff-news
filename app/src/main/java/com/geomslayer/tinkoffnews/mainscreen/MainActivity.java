package com.geomslayer.tinkoffnews.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geomslayer.tinkoffnews.R;
import com.geomslayer.tinkoffnews.details.DetailsActivity;
import com.geomslayer.tinkoffnews.models.Title;
import com.geomslayer.tinkoffnews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Title>> {

    private static int NEWS_LOADER_ID = 0;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout srl;
    private ArrayList<Title> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRefreshLayout();
        initRecyclerView();
    }

    void initRefreshLayout() {
        srl = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restartLoading();
            }
        });
    }

    void initRecyclerView() {
        dataset = new ArrayList<>();
        startLoading();
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        final NewsAdapter adapter = new NewsAdapter(dataset);
        adapter.setOnclickListener(new NewsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Title title = dataset.get(position);
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(Utils.TITLE_EXTRA, title);
                startActivity(detailsIntent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    void startLoading() {
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    void restartLoading() {
        getSupportLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Title>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Title>> loader, List<Title> data) {
        dataset.clear();
        if (data == null) {
            // notify user
        } else {
            dataset.addAll(data);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        srl.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Title>> loader) {

    }
}
