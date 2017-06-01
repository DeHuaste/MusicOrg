package com.zombo.jd.musicorg.model;

import android.content.Context;

/**
 * Created by jd on 12/20/15.
 */
public class ShareData {
    private static ShareData sData;  // NOTE: static reference sData

    private String value;
    // declare other variables here, or change value to a reference to a Model object
    private MusicDB db;
    private Context context;

    private ShareData(Context context) {
        this.context = context;
        value = "default";
        db = new MusicDB(context);
    }

    public static ShareData get(Context context) {
        if (sData == null) {
            sData = new ShareData(context.getApplicationContext());
        }
        return sData;
    }

    public void setValue(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }

    public MusicDB getQuestionDB() {
        return db;
    }

    // Put additional set and get methods here
}
