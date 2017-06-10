package com.zombo.jd.musicorg.model;

/**
 * Created by jd on 12/20/15.
 */
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zombo.jd.musicorg.model.dataObjects.Label;
import com.zombo.jd.musicorg.util.GlobalConstants;

public class TestAdapter
{
    protected static final String TAG = "MusicDatabaseAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getLabelData()
    {
        try
        {
            String sql ="SELECT * FROM " + GlobalConstants.LABEL_TABLE;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getLabelData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Label getLabelById(long id){
        String where = GlobalConstants.LABEL_ID + "=?";
        String[] whereArgs = {Long.toString(id)};
        Cursor cursor = mDb.query(GlobalConstants.LABEL_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        return getLabelFromCursor(cursor);
    }

    private Label getLabelFromCursor(Cursor cursor) {
        if(cursor == null || cursor.getCount() == 0){
            return null;
        } else {
            try{
                Label label = new Label(
                        cursor.getInt(cursor.getColumnIndex(GlobalConstants.LABEL_ID)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_NAME)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_CITY)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_CONTACT_MADE)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_MUSIC_RELEASE)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_PRIMARY_CONTACT)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(GlobalConstants.LABEL_PHONE))
                );
                return label;
            }catch (Exception e){
                Log.d(TAG, "Exception! " + e.getLocalizedMessage());
                return null;
            }
        }
    }

    public long insertLabel(Label label){

        ContentValues cv = new ContentValues();
        cv.put(GlobalConstants.LABEL_NAME, label.getName());
        cv.put(GlobalConstants.LABEL_CITY, label.getCity());
        cv.put(GlobalConstants.LABEL_COUNTRY, label.getCountry());
        cv.put(GlobalConstants.LABEL_CONTACT_MADE, label.getContactMade());
        cv.put(GlobalConstants.LABEL_MUSIC_RELEASE, label.getMusicReleased());
        cv.put(GlobalConstants.LABEL_PRIMARY_CONTACT, label.getPrimaryContact());
        cv.put(GlobalConstants.LABEL_EMAIL, label.getEmail());
        cv.put(GlobalConstants.LABEL_PHONE, label.getPhone());

        long rowID = mDb.insert(GlobalConstants.LABEL_TABLE, null, cv);

        Log.d(TAG, "NEW LABEL CREATED");

        return rowID;
    }


    public Cursor getReleaseData()
    {
        try
        {
            String sql ="SELECT * FROM " + GlobalConstants.RELEASE_TABLE;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getReleaseData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void deleteLabelById(long id) {

        mDb.delete(GlobalConstants.LABEL_TABLE, GlobalConstants.LABEL_ID+"=?", new String[]{Long.toString(id)});
    }

    public void updateLabel(Label label) {
        ContentValues cv = new ContentValues();
        cv.put(GlobalConstants.LABEL_ID, label.getId());
        cv.put(GlobalConstants.LABEL_NAME,label.getName());
        cv.put(GlobalConstants.LABEL_CITY, label.getCity());
        cv.put(GlobalConstants.LABEL_CONTACT_MADE, label.getContactMade());
        cv.put(GlobalConstants.LABEL_MUSIC_RELEASE, label.getMusicReleased());
        cv.put(GlobalConstants.LABEL_COUNTRY, label.getCountry());
        cv.put(GlobalConstants.LABEL_EMAIL, label.getEmail());
        cv.put(GlobalConstants.LABEL_PHONE, label.getPhone());
        cv.put(GlobalConstants.LABEL_PRIMARY_CONTACT, label.getPrimaryContact());

        mDb.update(GlobalConstants.LABEL_TABLE, cv, GlobalConstants.LABEL_ID + "=?", new String[]{Long.toString(label.getId())});
    }
}
