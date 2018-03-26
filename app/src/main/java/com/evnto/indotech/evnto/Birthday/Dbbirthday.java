package com.evnto.indotech.evnto.Birthday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by indotech on 8/26/2017.
 */


public class Dbbirthday extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_IMAGE = "image";

    public Dbbirthday(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

// Creating Tables

    @Override
    public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_PH_NO + " TEXT," + KEY_IMAGE + "KEY_IMAGE" + ")";
    db.execSQL(CREATE_CONTACTS_TABLE);
}

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(BirthdayList birthdayList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, birthdayList.getName()); // Contact Name
        values.put(KEY_PH_NO, birthdayList.getPrice());
        values.put(KEY_PH_NO, birthdayList.getDescription());
        values.put(KEY_PH_NO, birthdayList.getMinOrder());// Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public ArrayList<BirthdayList> getAllContacts() {
        ArrayList<BirthdayList> contactListt = new ArrayList<BirthdayList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BirthdayList contact = new BirthdayList();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setImageUrl(cursor.getString(2));
                // Adding contact to list
                contactListt.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactListt;
    }
}


