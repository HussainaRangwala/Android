package collection.anew.multiutilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hussaina on 07-02-2017.
 */

public class CategoryDatabase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "App_Database.db";
    private static final String LOGCAT = null;

    public CategoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=getWritableDatabase();
        Log.d("Database operations","Database created");
        onCreate(db);
    }

    //@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS PE_Category(cid int,cat_name varchar);");
        Log.d("Database operations","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String query;
        query = "DROP TABLE IF EXISTS PE_Category";
        db.execSQL(query);
        onCreate(db);*/
    }


    public void addCategories(CategoryDatabase db , int cid , String cat_name ) {   //insertion of images is done directly
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", cid);
        contentValues.put("cat_name", cat_name);
        long k = db1.insert("PE_Category", null, contentValues);
        Log.d("Database operations","One row inserted");
    }


    public void deleteCategories(CategoryDatabase db) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.execSQL("delete from PE_Category"); // Delete all records from category database
    }

    public Cursor getCategories(SQLiteDatabase db) {
        Cursor cursor;
        String projections[] = {"cid","cat_name"}; //projection is a string array that contains the names of columns
        cursor = db.query("PE_Category",projections,null,null,null,null,null);//query method 1st arg is tablename, 2nd colum names,next are all conditions like where clause and all .. in our case we want to retrieve all contents so all are null
        return  cursor;
    }


}
