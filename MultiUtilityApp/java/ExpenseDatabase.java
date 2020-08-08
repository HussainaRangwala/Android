package collection.anew.multiutilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hussaina on 31-01-2017.
 */

public class ExpenseDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "App_Database.db";
    private static final String LOGCAT = null;

    public ExpenseDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
        //db.execSQL("drop table expense");
        Log.d("Database operations","Database created");
        onCreate(db);
    }


    //@Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Expense(Exp_id int,Exp_Date VARCHAR,Category VARCHAR,Amount VARCHAR);");
        //Log.d(LOGCAT, "Students Created");
        Log.d("Database operations","Table created");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
        Log.d("Insert on create","Update Success");
    }
    public void putInformation1(ExpenseDatabase db, String exp_date, String category, String amount) {

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
        contentValues.put("Exp_id",id++);
        contentValues.put("Exp_Date", exp_date);
        contentValues.put("Category", category);
        contentValues.put("Amount", amount);



        long k = db1.insert("Expense", null, contentValues);
        Log.d("Database operations","One row inserted");
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor res=db1.rawQuery("select * from Expense",null);
        return res;
    }
    public void deleteRow(String exp_date,String exp_name,String exp_amt)
    {
        SQLiteDatabase db1=this.getWritableDatabase();

        db1.execSQL("Delete from Expense where Exp_Date=\'"+exp_date+"\' and Category=\'"+exp_name+"\' and Amount=\'"+exp_amt+"\'");

    }

    public  void updateRecord(String exp_date,String exp_name,String exp_amt,String prev_expdate,String prev_expname,String prev_expamt)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        db1.execSQL("Update Expense set Exp_Date=\'"+exp_date+"\',Category=\'"+exp_name+"\',Amount=\'"+exp_amt+"\' where Exp_id in(select Exp_id from Expense where Exp_Date=\'"+prev_expdate+"\' and Category=\'"+prev_expname+"\' and Amount=\'"+prev_expamt+"\');");
        //db1.execSQL("Update Expense set Exp_Date=\'"+exp_date+"\',Category=\'"+exp_name+"\',Amount=\'"+exp_amt+"' where Exp_id=0;");
    }

    public void deleteAll()
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        db1.execSQL("Delete from Expense");
    }
}
