
package com.theavalanche.eskimo.info.localAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.theavalanche.eskimo.info.localModel.LocationInfo;

public class DBConn extends SQLiteOpenHelper {

public static final String DATABASE_NAME = "MyDBName.db";
public static final String TABLE_NAME = "Location";
public static final String COLUMN_ID = "ID";
        public static final String COLUMN_EVENTID = "EventId";
public static final String COLUMN_LAT = "Lat";
        public static final String COLUMN_LONG = "Long";
    private HashMap hp;

    public DBConn(Context context) {
        super(context, DATABASE_NAME , null, 1);
        }

@Override
public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table location " +
                        "(ID integer primary key, EventId integer,Lat integer, Long integer)"
        );
}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // TODO Auto-generated method stub
                db.execSQL("DROP TABLE IF EXISTS contacts");
                onCreate(db);
        }



        public boolean insertLocation  (LocationInfo l)
        {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", l.getLocationID());
        contentValues.put("EventId", l.getEventID());
        contentValues.put("Lat", l.getLat());
        contentValues.put("Long", l.getLon());
        db.insert("location", null, contentValues);
        return true;
        }

public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from location where id="+id+"", null );
        return res;
        }

public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
        }

public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
        {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
        }

public Integer deleteContact (Integer id)
        {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
        "id = ? ",
        new String[] { Integer.toString(id) });
        }

public ArrayList<String> getAllLocations()
        {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
        array_list.add(res.getString(res.getColumnIndex(COLUMN_EVENTID)));
        res.moveToNext();
        }
        return array_list;
        }
        }