package com.zombo.jd.musicorg;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.zombo.jd.musicorg.model.TestAdapter;
import com.zombo.jd.musicorg.util.GlobalConstants;

public class ReleaseListActivity extends AppCompatActivity {

    private final String TAG = "RELEASE LIST ACTIVITY";
    ListView releaseList;
    private final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_list);
        initializeList();
    }

    private void initializeList() {
        releaseList = (ListView)findViewById(R.id.releaseList);
        releaseList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        launchReleaseDetailActivity(id);
                    }
                }
        );
        TestAdapter dbHelper = new TestAdapter(getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();

        Cursor cursor = dbHelper.getReleaseData();
        dbHelper.close();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor,
                new String[] {GlobalConstants.RELEASE_TITLE, GlobalConstants.RELEASE_ARTIST},
                new int[] {R.id.label_entry, R.id.status_entry});
    }

    private void launchReleaseDetailActivity(long id){
        Intent intent = new Intent(this, ReleaseDetailActivity.class);
        intent.putExtra(GlobalConstants.RELEASE_ID, id);

        Log.d(TAG, "launching detail activity");

        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.label_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_create_label){
            Intent intent = new Intent(this, LabelCreateActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.action_create_release) {
            Intent intent = new Intent(this, ReleaseCreateActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onResume(){
        super.onResume();
        initializeList();
    }

}
