package com.geomslayer.tinkoffnews.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.geomslayer.tinkoffnews.R;
import com.geomslayer.tinkoffnews.models.Title;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Title> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        dataset = generateDataset();
        final NewsAdapter adapter = new NewsAdapter(dataset);
        adapter.setOnclickListener(new NewsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                String title = dataset.get(position).getText();
                Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    ArrayList<Title> generateDataset() {
        ArrayList<Title> res = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            res.add(new Title(i, "Title " + i));
        }
        return res;
    }

}
