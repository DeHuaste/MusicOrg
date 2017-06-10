package com.zombo.jd.musicorg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zombo.jd.musicorg.model.TestAdapter;
import com.zombo.jd.musicorg.model.dataObjects.Label;
import com.zombo.jd.musicorg.util.GlobalConstants;

public class LabelDetailActivity extends AppCompatActivity {

    private String TAG = " LABEL DETAIL ACTIVITY";
    private int REQUEST_CODE = 808;

    TextView labelNameView;
    TextView locationView;
    TextView contactView;
    TextView idView;

    Button editButton;
    Button deleteButton;

    Label label;

    long labelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_detail);
        Intent intent = getIntent();

        labelId = intent.getLongExtra(GlobalConstants.INTENT_LABEL_ID, -1);

        labelNameView = (TextView)findViewById(R.id.labelText);
        locationView = (TextView)findViewById(R.id.locationView);
        contactView = (TextView)findViewById(R.id.contactView);
        idView = (TextView)findViewById(R.id.labelId);
        if(labelId != -1){
            displayLabelInfo(labelId);
            initializeButtons();
        } else {
            labelNameView.setText("error");
        }
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

    private void initializeButtons(){
        editButton = (Button)findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabel();
            }
        });
        deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLabel();
            }
        });
    }

    private void editLabel(){
        Intent intent = new Intent(this, LabelCreateActivity.class);
        intent.putExtra(GlobalConstants.LABEL_NAME , label.getName());
        intent.putExtra(GlobalConstants.LABEL_CITY, label.getCity());
        intent.putExtra(GlobalConstants.LABEL_COUNTRY, label.getCountry());
        intent.putExtra(GlobalConstants.LABEL_PRIMARY_CONTACT, label.getPrimaryContact());
        intent.putExtra(GlobalConstants.LABEL_EMAIL, label.getEmail());
        intent.putExtra(GlobalConstants.LABEL_PHONE, label.getPhone());
        intent.putExtra(GlobalConstants.LABEL_CONTACT_MADE, label.getContactMade());
        intent.putExtra(GlobalConstants.LABEL_MUSIC_RELEASE, label.getMusicReleased());
        intent.putExtra(GlobalConstants.LABEL_ID, labelId);

        Log.d(TAG, "launching edit activity");

        startActivityForResult(intent, REQUEST_CODE);
    }

    private void deleteLabel(){

        //Display Delete confirmation dialog, if OK is selected, doDelete() is called
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getResources().getString(R.string.delete_label_message));
        builder1.setCancelable(true);

        //OK confirms the delete
        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doDelete();
                        dialog.cancel();
                    }
                });

        //Cancels the delete
        builder1.setNegativeButton(
                getResources().getString(R.string.cancel_label),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.create().show();

    }

    private void doDelete(){

        TestAdapter dbHelper = new TestAdapter(getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();
        dbHelper.deleteLabelById(label.getId());
        dbHelper.close();

        Intent intent = new Intent(this, LabelListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    /**
     * Accepts label ID and queries DB for result
     *
     * @param id - the id of the label to display
     */
    private void displayLabelInfo(long id) {
        TestAdapter dbHelper = new TestAdapter(getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();

        label = dbHelper.getLabelById(id);

        labelNameView.setText(label.getName());
        locationView.setText(label.getCity() + ", " + label.getCountry());
        contactView.setText(label.getPrimaryContact());

        dbHelper.close();
    }
}
