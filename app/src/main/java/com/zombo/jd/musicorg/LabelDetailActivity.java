package com.zombo.jd.musicorg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zombo.jd.musicorg.model.TestAdapter;
import com.zombo.jd.musicorg.model.dataObjects.Label;
import com.zombo.jd.musicorg.util.GlobalConstants;

public class LabelDetailActivity extends AppCompatActivity {
    TextView labelNameView;
    TextView locationView;
    TextView contactView;
    Button editButton;
    Button deleteButton;
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
        if(labelId != -1){
            displayLabelInfo(labelId);
            initializeButtons();
        } else {
            labelNameView.setText("error");
        }
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
        //TODO: launch intent to modify existing label
    }

    private void deleteLabel(){
        //TODO: Display Delete confirmation dialog, if OK, delete label.
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

        Label label = dbHelper.getLabelById(id);
        labelNameView.setText(label.getName());
        locationView.setText(label.getCity() + ", " + label.getCountry());
        contactView.setText(label.getPrimaryContact());
        dbHelper.close();
    }
}
