package com.zombo.jd.musicorg.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zombo.jd.musicorg.model.dataObjects.Label;

import java.util.ArrayList;

/**
 * Created by jd on 12/20/15.
 */
public class MusicDB {
    private static final String TAG = ": MUSIC DATABASE :";


    public static final String DB_NAME = "music.db";
    public static final int    DB_VERSION = 1;

    public static final String LABEL_TABLE = "label";

    public static final String LABEL_ID = "_id";
    public static final int LABEL_ID_COL = 0;

    public static final String LABEL_TEXT = "label";
    public static final int LABEL_COL = 1;

    public static final String STATUS = "status";
    public static final int STATUS_COL = 2;

    public static final String ANSWER = "answer";
    public static final int ANSWER_COL = 3;

    public static final String CREATE_LABEL_TABLE =
            "CREATE TABLE " + LABEL_TABLE + " (" +
                    LABEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LABEL_TEXT + " TEXT NOT NULL, " +
                    STATUS + " INTEGER, " +
                    ANSWER + " INTEGER);";

    public static final String DROP_LABEL_TABLE =
            "DROP TABLE IF EXISTS " + LABEL_TABLE;

    private static Context ctx;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //TODO: Check if DB already has data?
            db.execSQL(CREATE_LABEL_TABLE);
            Log.d(TAG, "Inserting into DB...");
            Log.d(TAG, "DB populated...");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d(TAG, "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(MusicDB.DROP_LABEL_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public MusicDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        ctx = context;
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }


    public long insertLabel(Label label) {
        ContentValues cv = new ContentValues();
//        cv.put(QUESTION_ID, label.getId());

        this.openWriteableDB();
        long rowID = db.insert(LABEL_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    /*
     * The id field in the Question object is ignored -- the system will assign the next
     * valid value.
     */

    public long insertLabelAutoId(Label label) {
        ContentValues cv = new ContentValues();

//        cv.put(QUESTION_TEXT, label.getText());


        this.openWriteableDB();
        long rowID = db.insert(LABEL_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateLabel(Label label) {
        ContentValues cv = new ContentValues();
//        cv.put(QUESTION_ID, label.getId());
//        cv.put(QUESTION_TEXT, question.getText());
//        cv.put(STATUS, question.getStatus());
//        cv.put(ANSWER, question.getAnswer());

        String where = LABEL_ID + "=?";
        String[] whereArgs = { String.valueOf(label.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(LABEL_TABLE, cv, where, whereArgs);
        db.update(LABEL_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;

    }

    public int deleteLabel(long id) {
        String where = LABEL_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(LABEL_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public void dropAndRecreateDB(){
        db.execSQL(DROP_LABEL_TABLE);
        db.execSQL(CREATE_LABEL_TABLE);
        Log.d(TAG, "Inserting into DB...");
//        for(int i = 0; i < questions.length; i++) {
//            int answer = true == key[i] ? QuestionExtraStrings.TRUE : QuestionExtraStrings.FALSE;
//            db.execSQL("INSERT INTO " + LABEL_TABLE + " VALUES (" + i + ",'" + questions[i] + "'," + Question.UNANSWERED + "," + answer + ")");
//        }
        Log.d(TAG, "DB populated...");
    }

    /*
     * Retrieves all of the questions in the database and returns as an ArrayList of Question objects.
     */

    public ArrayList<Label> getLabels() {
        this.openReadableDB();
        Cursor cursor = db.query(LABEL_TABLE, null,
                null, null,  // using null for where and whereArgs to get all of the movies
                null, null, null);
        ArrayList<Label> labels = new ArrayList<Label>();
        while (cursor.moveToNext()) {
            labels.add(getLabelFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return labels;
    }

    public Cursor getLabelsAsCursor() {
        this.openReadableDB();
        TestAdapter dbHelper = new TestAdapter(ctx);
        dbHelper.createDatabase();
        dbHelper.open();

        Cursor cursor = dbHelper.getLabelData();
        dbHelper.close();
                //this is for the old approach.
//                db.query(LABEL_TABLE, null,
//                null, null,  // using null for where and whereArgs to get all of the questions
//                null, null, null);

        return cursor;
    }

    public Label getLabel(long id) {
        String where = LABEL_ID + "=?";
        String[] whereArgs = { Long.toString(id) };

        this.openReadableDB();
        Cursor cursor = db.query(LABEL_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Label label = getLabelFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return label;
    }

    public static Label getLabelFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Label label = new Label(
//                        cursor.getInt(QUESTION_ID_COL),
//                        cursor.getString(QUESTION_COL),
//                        cursor.getInt(STATUS_COL),
//                        cursor.getInt(ANSWER_COL)
                );

                return label;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

}
