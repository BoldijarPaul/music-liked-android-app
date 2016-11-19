package com.bolnizar.datafun.facebook;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bolnizar.datafun.MainApp;
import com.bolnizar.datafun.Presenter;
import com.bolnizar.datafun.Utils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONException;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class FacebookPresenter extends Presenter<FacebookView> {

    public FacebookPresenter(FacebookView facebookView) {
        super(facebookView);
    }

    public void loadLikes() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + Profile.getCurrentProfile().getId() + "/music?limit=1000",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Page[] pages = Utils.getGson().fromJson(response.getJSONObject().getJSONArray("data").toString(), Page[].class);
                            gotPages(pages);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    private void gotPages(Page[] pages) {
        SQLiteDatabase database = MainApp.getSqlHelper().getWritableDatabase();
        database.execSQL("delete from page");
        for (Page page : pages) {
            String sql = String.format("insert into page values('%s','%s','%s')", page.id, page.name, page.date.getTime());
            Log.d("sql", sql);
            database.execSQL(sql);
        }
        database.close();
    }

}
