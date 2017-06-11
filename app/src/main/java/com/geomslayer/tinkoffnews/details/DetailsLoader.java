package com.geomslayer.tinkoffnews.details;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.geomslayer.tinkoffnews.utils.Downloader;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsLoader extends AsyncTaskLoader<String> {

    private static final String TAG = "DetailsLoader";

    private static final String URL = "https://api.tinkoff.ru/v1/news_content?id=";
    private static final String KEY = "news-";
    private static final String PREF_NAME = "details";

    private static final String PAYLOAD = "payload";
    private static final String CONTENT = "content";

    private long newsId;

    public DetailsLoader(Context context, long id) {
        super(context);
        this.newsId = id;
    }

    @Override
    public String loadInBackground() {
        Log.d(TAG, "loadInBackground: ");
        String json = loadFromCache();
        if (json == null) {
            json = loadFromNetwork();
        }
        if (json == null) {
            return null;
        }
        return parseContent(json);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    private String parseContent(String json) {
        try {
            JSONObject object = new JSONObject(json);
            JSONObject payload = object.getJSONObject(PAYLOAD);
            return payload.getString(CONTENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadFromNetwork() {
        String json = new Downloader().download(URL + newsId);
        if (json != null) {
            saveInCache(json);
        }
        return json;
    }

    private void saveInCache(String json) {
        SharedPreferences preferences = getContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(formKey(), json)
                .apply();
    }

    private String loadFromCache() {
        SharedPreferences preferences = getContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(formKey(), null);
    }

    private String formKey() {
        return KEY + newsId;
    }

}
