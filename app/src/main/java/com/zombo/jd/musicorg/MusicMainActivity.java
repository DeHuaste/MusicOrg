package com.zombo.jd.musicorg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MusicMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);
    }

    public void goToLabels(View view){
        Intent intent = new Intent(this, LabelListActivity.class);
        startActivity(intent);

    }

    public void goToReleases(View view){
        Intent intent = new Intent(this, ReleaseListActivity.class);
        startActivity(intent);
    }
}
