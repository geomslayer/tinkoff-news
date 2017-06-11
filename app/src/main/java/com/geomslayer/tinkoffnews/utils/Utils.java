package com.geomslayer.tinkoffnews.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    public static final String TITLE_EXTRA = "title_extra";

    public static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("HH:mm, dd MMMM yyyy", new Locale("ru", "RU"));

    public static Spanned toSpanned(String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(str);
    }

}
