package com.geomslayer.tinkoffnews.mainscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.geomslayer.tinkoffnews.models.Title;
import com.geomslayer.tinkoffnews.utils.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<Title>> {

    private static final String TAG = "NewsLoader";

    private static final String URL = "https://api.tinkoff.ru/v1/news";

    private static final String PAYLOAD = "payload";
    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final String PUB_DATE = "publicationDate";
    private static final String MILLISECONDS = "milliseconds";

    private static final String PREF_NAME = "cache";
    private static final String JSON = "json";

    private boolean loadFromCache;

    NewsLoader(Context context, boolean loadFromCache) {
        super(context);
        this.loadFromCache = loadFromCache;
    }

    @Override
    public List<Title> loadInBackground() {
        String json = null;
        if (loadFromCache) {
            Log.d(TAG, "loadInBackground: load from cache");
            json = restoreFromCache();
        }
        if (json == null) {
            json = fetchNews();
        }
        if (json == null) {
            return null;
        }
        loadFromCache = true;
        return parseTitles(json);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onReset() {
        Log.d(TAG, "onReset: ");
        loadFromCache = false;
//        onStartLoading();
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
        Downloader downloader = new Downloader();
        String json = downloader.download(URL);
        if (json != null) {
            saveInCache(json);
        }
        return json;
    }

    private void saveInCache(String json) {
        SharedPreferences preferences = getContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(JSON, json)
                .apply();
    }

    private String restoreFromCache() {
        SharedPreferences preferences = getContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(JSON, null);
    }

}
