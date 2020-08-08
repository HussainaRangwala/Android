package collection.anew.multiutilapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class PhoneBookShowGroupNames extends AppCompatActivity {

    CheckBox checkBox;
    SQLiteDatabase sqLiteDatabase;
    public static int prgmimage = R.drawable.ic_group_black_24dp;
    PhonebookGroupDatabase ph_gd;
    ListView listView1;
    Cursor cursor;
    PhoneBookShowGroupNamesAdapter listDataAdapter;
    ArrayList<String> groupContents;
    ArrayList<String> groupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book_show_group_names);
        ph_gd = new PhonebookGroupDatabase(getApplicationContext());
        sqLiteDatabase = ph_gd.getReadableDatabase();
        cursor = ph_gd.getGroupNames(sqLiteDatabase);
        groupNames = new ArrayList<String>();

        Log.i("Cursor Count", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                String group_name11;
                group_name11 = cursor.getString(0);
                //Log.i("GN:",group_name);
                groupNames.add(group_name11);
            } while (cursor.moveToNext());
        }

        final int  groupcount = groupNames.size();


        listView1 = (ListView) findViewById(R.id.list_view_contacts_group_names);
        listDataAdapter = new PhoneBookShowGroupNamesAdapter(getApplicationContext(), R.layout.display_group_name_row, prgmimage, groupNames);
        listView1.setAdapter(listDataAdapter);



        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int i;
                for (i = 0; i < groupcount; i++) {
                    checkBox = (CheckBox) listView1.getChildAt(i).findViewById(R.id.checkbox_group_name);
                    checkBox.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String group_name = listView1.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),group_name, Toast.LENGTH_LONG).show();
                Intent i = new Intent(PhoneBookShowGroupNames.this, PhoneBookShowGroupContents.class);    //Group name is going to be the table name in database
                i.putExtra("group_name", group_name);
                startActivity(i);
            }
        });





        //Check what will this page display if no items in listview of groupnames

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.showgroupnames, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);   //To set the icon solor to white

        yourdrawable = menu.getItem(1).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_grp_name_back:
                Intent i = new Intent(PhoneBookShowGroupNames.this,StartCollegePhonebook.class);
                startActivity(i);
                return true;



            case R.id.action_grp_name_delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);  //use this only over here
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ph_gd = new PhonebookGroupDatabase(getApplicationContext());
                                sqLiteDatabase = ph_gd.getWritableDatabase();
                                int j;
                                for(j=0; j<PhoneBookShowGroupNamesAdapter.selectedGroupNames.size();j++)
                                {
                                    String GroupName = PhoneBookShowGroupNamesAdapter.selectedGroupNames.get(j);
                                    sqLiteDatabase.execSQL("delete from  PhonebookGroupContents where groupName = '" + GroupName + "' ");
                                    sqLiteDatabase.execSQL("delete from PhonebookGroupNames where groupName = '"+GroupName+"' ");
                                }
                                Intent intent = getIntent();  //refreshing the current activity
                                finish();
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(),"Group Deleted Successfully",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("no",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}