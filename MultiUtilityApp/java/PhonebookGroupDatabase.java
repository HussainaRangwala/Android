package collection.anew.multiutilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 20-02-2017.
 */

public class PhonebookGroupDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PhonebookGroupDatabaseNew.db";

    private static final String LOGCAT = null;
    Context ctx;

    public PhonebookGroupDatabase(Context context) {
        super(context, DATABASE_NAME, null,1);
        //Log.d("Database operations","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS PhonebookGroupNames(groupName varchar);");
        db.execSQL("CREATE TABLE IF NOT EXISTS PhonebookGroupContents(groupName varchar,contactName varchar,contactNumber varchar);");
        Log.d("Database operations","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

   /* public void myCreate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ tablename + "(contactName varchar,contactNumber varchar);");

          db = this.getWritableDatabase();
        db.delete("oo",null,null);
    }*/

    public void deleteGroupMembers(SQLiteDatabase db,String grpname,String contactname,String contactnumber)
    {
        db = this.getWritableDatabase();
        db.execSQL("delete from PhonebookGroupContents where groupName = '" + grpname + "' and contactName = '"+contactname+"' and contactNumber = '"+contactnumber+"' ");
        Log.i("Deleted Successfully","");
    }


    public void addGroup(PhonebookGroupDatabase db , String groupName)
    {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("groupName",groupName);
        long k = db1.insert("PhonebookGroupNames", null, contentValues);
        Log.d("Database operations","One row inserted in PhoneBook Group names");
    }

    public void addGroupContents( PhonebookGroupDatabase db ,String groupName, String contactName , String contactNumber )
    {
        Log.i("GN",groupName);
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("groupName",groupName);
        contentValues.put("contactName", contactName);
        contentValues.put("contactNumber", contactNumber);
        long k = db1.insert("PhonebookGroupContents", null, contentValues);
        Log.d("Database operations","One row inserted in Actual Group");
    }



    public Cursor getGroupNames(SQLiteDatabase db) {
        Cursor cursor;
        /*String projections[] = {"groupName"};
        cursor = db.query("PhonebookGroupNames",projections,null,null,null,null,null);*/
        cursor = db.rawQuery("select * from PhonebookGroupNames where groupName not null ",null);
        return  cursor;
    }

    public  Cursor getGroupContents(SQLiteDatabase db,String groupName){
        Cursor cursor;
        Cursor res = db.rawQuery("select * from PhonebookGroupContents where groupName = '" + groupName + "' ",null);
        return res;
    }

}
