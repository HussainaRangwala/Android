package collection.anew.multiutilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hussaina on 19-02-2017.
 */

public class IncomeDatabase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "App_Database.db";
    private static final String LOGCAT = null;

    public IncomeDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
        //db.execSQL("drop table expense");
        Log.d("Database operations","Database created");
        onCreate(db);
    }


    //@Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Income(Inc_id int,Inc_Date VARCHAR,Category VARCHAR,Amount VARCHAR);");
        //Log.d(LOGCAT, "Students Created");
        Log.d("Database operations","Table created");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
        Log.d("Insert on create","Update Success");
    }
    public void putInformation1(IncomeDatabase db, String inc_date, String category, String amount) {

        int id=0;
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res=db.getAllData();
        if(res.getCount()==0)
            id=0;
        else {
            while (res.moveToNext()) {
                id++;
            }
        }
        contentValues.put("Inc_id",id++);
        contentValues.put("Inc_Date", inc_date);
        contentValues.put("Category", category);
        contentValues.put("Amount", amount);



        long k = db1.insert("Income", null, contentValues);
        Log.d("Database operations","One row inserted");
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor res=db1.rawQuery("select * from Income",null);
        return res;
    }

    public void deleteAll()
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        db1.execSQL("Delete from Income");
    }
}
