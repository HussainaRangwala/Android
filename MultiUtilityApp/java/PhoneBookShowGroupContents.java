package collection.anew.multiutilapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class PhoneBookShowGroupContents extends AppCompatActivity {

    Button btn;
    static  int flag_checked = 0;
    CheckBox checkBox;
    public static int prgmimage = R.drawable.ic_person_black_24dp;
    SQLiteDatabase sqLiteDatabase;
    PhonebookGroupDatabase ph_gd;
    ListView listView;
    Cursor cursor;
    String strArray[] = {"Make a Call", "Message"};
    PhoneBookShowGroupContentsAdapter listDataAdapter;
    static ArrayList<String> groupContentsContactNames;
    ArrayList<String> groupContentsContactNumbers;
    static String group_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book_show_group_contents);

        if(flag_checked == 1)
        {
            Intent i = getIntent();
            group_name = i.getStringExtra("gn");
        }
        else {
            Intent i = getIntent();
            group_name = i.getStringExtra("group_name");
        }

        this.getSupportActionBar().setTitle(group_name);  //try to set dynamic title of groupname

        //Toast.makeText(getBaseContext(), group_name, Toast.LENGTH_SHORT).show();
        final int count;

        ph_gd = new PhonebookGroupDatabase(getApplicationContext());
        sqLiteDatabase = ph_gd.getReadableDatabase();

        cursor = ph_gd.getGroupContents(sqLiteDatabase, group_name);
        groupContentsContactNames = new ArrayList<String>();
        groupContentsContactNumbers = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String contact_name, contact_number;
                contact_name = cursor.getString(1);
                contact_number = cursor.getString(2);
                //Log.i("GN:", contact_name);
                groupContentsContactNames.add(contact_name);
                groupContentsContactNumbers.add(contact_number);
            } while (cursor.moveToNext());
        }

        count = groupContentsContactNames.size();       //count for no of checkboxes to be added


        listView = (ListView) findViewById(R.id.list_view_contacts_group_contents);
        listDataAdapter = new PhoneBookShowGroupContentsAdapter(getApplicationContext(), R.layout.display_group_contents_row, prgmimage, groupContentsContactNames, groupContentsContactNumbers);
        listView.setAdapter(listDataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhoneBookShowGroupContents.this);
                builder.setTitle("Select Your Choice")


                        .setItems(strArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (strArray[which].equals("Make a Call")) {
                                    String name = groupContentsContactNames.get(position);
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + groupContentsContactNumbers.get(position)));
                                    if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(callIntent);

                                } else if (strArray[which].equals("Message")) {
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("smsto:"));
                                    sendIntent.setType("vnd.android-dir/mms-sms");
                                    sendIntent.putExtra("address", new String(groupContentsContactNumbers.get(position)));
                                    sendIntent.putExtra("sms_body", "");
                                    startActivity(sendIntent);
                                }

                            }
                        }).show();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int i;
                for (i = 0; i < count; i++) {
                    checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.checkbox_group);
                    checkBox.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        flag_checked = 0;
        btn = (Button)findViewById(R.id.btn_add_member);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_checked = 1;
                Intent  i = new Intent(getApplicationContext(),SelectGroupPhonebook.class);
                startActivity(i);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.showgroupcontents, menu);
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

            case R.id.action_back:
                Intent i = new Intent(PhoneBookShowGroupContents.this, PhoneBookShowGroupNames.class);
                this.startActivity(i);
                return true;

            case R.id.action_delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);  //use this only over here
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ph_gd = new PhonebookGroupDatabase(getApplicationContext());
                                sqLiteDatabase = ph_gd.getWritableDatabase();
                                for(int j=0;j<PhoneBookShowGroupContentsAdapter.selectedNames.size();j++) {
                                    ph_gd.deleteGroupMembers(sqLiteDatabase,group_name,PhoneBookShowGroupContentsAdapter.selectedNames.get(j),PhoneBookShowGroupContentsAdapter.selectedNos.get(j));
                                }
                                Intent intent = getIntent();  //refreshing the current activity
                                finish();
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Member Deleted Successfully",Toast.LENGTH_SHORT).show();
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