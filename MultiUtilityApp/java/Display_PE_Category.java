package collection.anew.multiutilapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class Display_PE_Category extends Activity {
    SQLiteDatabase sqLiteDatabase;
    EditText et;
    public static String[] categoriesList = {"Clothes","Eating Out","Entertainment","Fuel","General","Gifts","Holidays","Kids","Shopping","Travel","Sports"};
    public static int[] prgmImages={R.drawable.clothes,R.drawable.food,R.drawable.entertainment,R.drawable.fuel,R.drawable.general,R.drawable.gift,R.drawable.shopping,R.drawable.kids,R.drawable.shoppingcart,R.drawable.travel,R.drawable.sports};
    CategoryDatabase cd;
    ListView listView;
    Cursor cursor;
    String dbValues[];
    PECategoryListDataAdapter listDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__pe__category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.list_view_category);

        listDataAdapter = new PECategoryListDataAdapter(getApplicationContext(),R.layout.display_category_row,prgmImages);


        listView.setAdapter(listDataAdapter);

        cd = new CategoryDatabase(getApplicationContext());
        sqLiteDatabase = cd.getReadableDatabase();
        cursor = cd.getCategories(sqLiteDatabase);

        if(cursor.moveToFirst()) { // moveToFirst will return true if there is some data in the registration database
            do {
                String category_name;
                category_name = cursor.getString(1);   // (1) gives second row and cursor.getString(0) gives first row
                CategoryDataProvider dataProvider = new CategoryDataProvider(category_name);
                listDataAdapter.add(dataProvider);
            }while(cursor.moveToNext()); //moveToNext will return true if another row is available
            //listDataAdapter.add(prgmImages); ... ERROR
        }

        //@Override

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String cat_name = categoriesList[position];
                Intent i = new Intent(Display_PE_Category.this,PE_Expense_Button.class);
                i.putExtra("cat_name",cat_name);
                setResult(PE_Expense_Button.CAPTURE_REQ_CODE,i);
                finish();
            }
        });
    }

}
