package com.bolnizar.datafun;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class Utils {
    private static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        }
        return sGson;
    }

    public static String imageUrl(String id) {
        return String.format("http://graph.facebook.com/%s/picture?type=large", id);
    }
}
