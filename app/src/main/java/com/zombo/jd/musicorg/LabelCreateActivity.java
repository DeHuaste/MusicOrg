package com.zombo.jd.musicorg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zombo.jd.musicorg.model.TestAdapter;
import com.zombo.jd.musicorg.model.dataObjects.Label;
import com.zombo.jd.musicorg.util.GlobalConstants;

public class LabelCreateActivity extends AppCompatActivity implements TextWatcher {

    Button saveButton;
    Button cancelButton;

    EditText labelName;
    EditText labelCity;
    EditText labelCountry;
    EditText labelContact;
    EditText labelEmail;
    EditText labelPhone;
    TextView labelIdView;

    CheckBox contactMade;
    CheckBox demoSubmitted;

    private boolean editSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_create);


        labelIdView = (TextView)findViewById(R.id.labelId);
        labelName = (EditText)findViewById(R.id.labelNameInput);
        labelCity = (EditText)findViewById(R.id.cityInput);
        labelCountry = (EditText)findViewById(R.id.countryInput);
        labelContact = (EditText)findViewById(R.id.contactNameInput);
        labelEmail = (EditText)findViewById(R.id.emailInput);
        labelPhone = (EditText)findViewById(R.id.phoneInput);

        contactMade = (CheckBox)findViewById(R.id.contactMadeCheckbox);
        demoSubmitted = (CheckBox)findViewById(R.id.musicSubmittedCheckbox);

        initializeForm();
    }

    public void initializeForm(){

        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLabel();
            }
        });
        saveButton.setEnabled(false);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLabelList();
            }
        });

        if(getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            editSelected = true;

            labelIdView.setText(String.valueOf(intent.getLongExtra(GlobalConstants.LABEL_ID, -1)));
            labelName.setText(intent.getStringExtra(GlobalConstants.LABEL_NAME));
            labelCity.setText(intent.getStringExtra(GlobalConstants.LABEL_CITY));
            labelCountry.setText(intent.getStringExtra(GlobalConstants.LABEL_COUNTRY));
            labelContact.setText(intent.getStringExtra(GlobalConstants.LABEL_PRIMARY_CONTACT));
            labelEmail.setText(intent.getStringExtra(GlobalConstants.LABEL_EMAIL));
            labelPhone.setText(intent.getStringExtra(GlobalConstants.LABEL_PHONE));

            contactMade.setText(intent.getStringExtra(GlobalConstants.LABEL_CONTACT_MADE));
            demoSubmitted.setText(intent.getStringExtra(GlobalConstants.LABEL_MUSIC_RELEASE));
            saveButton.setEnabled(true);

        } else {
            labelName.addTextChangedListener(this);
        }

    }


    private void saveLabel(){
        Label label = createLabel();
        TestAdapter dbHelper = new TestAdapter(getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();
        if(editSelected) {
            dbHelper.updateLabel(label);

        } else {
            dbHelper.insertLabel(label);

        }

        dbHelper.close();

        Toast.makeText(this, "SAVED!", Toast.LENGTH_LONG).show();

        returnToLabelList();
    }

    /**
     *  get values from input fields.  Create and return label object
     * @return
     */
    private Label createLabel(){

        Label label = new Label();

        label.setName(labelName.getText().toString());
        label.setCity(labelCity.getText().toString());
        label.setCountry(labelCountry.getText().toString());
        label.setPrimaryContact(labelContact.getText().toString());
        label.setEmail(labelEmail.getText().toString());
        label.setPhone(labelPhone.getText().toString());
        label.setContactMade(contactMade.isChecked() ? GlobalConstants.TRUE : GlobalConstants.FALSE);
        label.setMusicReleased(demoSubmitted.isChecked() ? GlobalConstants.TRUE : GlobalConstants.FALSE);
        label.setId(Integer.parseInt(labelIdView.getText().toString()));
        return label;
    }
    private void returnToLabelList(){
        Intent intent = new Intent(this, LabelListActivity.class);
        startActivity(intent);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!labelName.getText().toString().isEmpty()){
            saveButton.setEnabled(true);
        }else {
            saveButton.setEnabled(false);
        }
    }

    //Don't care
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Don't care
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

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
}
