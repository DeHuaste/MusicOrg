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

public class LabelListActivity extends AppCompatActivity {

    private final String TAG = "LABEL LIST ACTIVITY";
    ListView labelList;
    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_list);
        //TODO: implement toolbar - refer to activity_quiz_dbmain
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initializeList();
    }

    private void initializeList(){
        labelList = (ListView)findViewById(R.id.labelList);
        labelList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //TODO: query DB for label, create new label object, send to detail activity
                        launchLabelDetailActivity(id);
                    }
                }
        );

        TestAdapter dbHelper = new TestAdapter(getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();

        Cursor cursor = dbHelper.getLabelData();
        dbHelper.close();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor,
                new String[] {GlobalConstants.LABEL_NAME, GlobalConstants.LABEL_CITY},
                //TODO change ID name from status_entry to something more meaningful
                new int[] {R.id.label_entry, R.id.status_entry});

        labelList.setAdapter(adapter);
        registerForContextMenu(labelList);
        adapter.setViewBinder(
                new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        return false;
                    }
                }
        );
    }
    private void launchLabelDetailActivity(long id) {
        Intent intent = new Intent(this, LabelDetailActivity.class);
        intent.putExtra(GlobalConstants.INTENT_LABEL_ID, id);

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
