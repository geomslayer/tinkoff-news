package com.geomslayer.tinkoffnews.mainscreen;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.geomslayer.tinkoffnews.models.Title;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<Title>> {

    private static final String URL = "https://api.tinkoff.ru/v1/news";

    private static final String PAYLOAD = "payload";
    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final String PUB_DATE = "publicationDate";
    private static final String MILLISECONDS = "milliseconds";

    NewsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Title> loadInBackground() {
        String json = fetchNews();
        if (json == null) {
            return null;
        }
        return parseTitles(json);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    private ArrayList<Title> parseTitles(String json) {
        ArrayList<Title> result = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray payload = object.getJSONArray(PAYLOAD);
            for (int i = 0; i < payload.length(); ++i) {
                JSONObject news = payload.getJSONObject(i);
                long id = news.getLong(ID);
                String text = news.getString(TEXT);
                JSONObject pubDateObject = news.getJSONObject(PUB_DATE);
                Date pubDate = new Date(pubDateObject.getLong(MILLISECONDS));
                result.add(new Title(id, text, pubDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String fetchNews() {
        URL url;
        BufferedReader reader = null;
        try {
            url = new URL(URL);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
