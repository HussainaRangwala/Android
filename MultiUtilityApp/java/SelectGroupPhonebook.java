package collection.anew.multiutilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SelectGroupPhonebook extends AppCompatActivity {
    PhoneBookGroupAdapter listDataAdapter = null;
    SQLiteDatabase sqLiteDatabase;
    ListView lv;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Nos = new ArrayList<>();
    PhonebookGroupDatabase ph_gd , ph_gd2,ph_gd3;
    SQLiteDatabase db;
    Context ctx = this;
    Map<String, Integer> mapIndex;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group_phonebook);

        Names = Tab1ShowContacts.alContactNames;
        Nos = Tab1ShowContacts.alContactNos;

        lv = (ListView)findViewById(R.id.list_view_contacts_group);
        listDataAdapter = new PhoneBookGroupAdapter(getApplicationContext(), R.layout.select_group_phonebook_checkbox, Names, Nos);
        lv.setAdapter(listDataAdapter);


        getNewIndexList(Names);
        final Toast toast = new Toast(getApplicationContext());
        final View layout = getLayoutInflater().inflate(R.layout.alphabetic_toast,null);
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index1);
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());

        for (final String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index.toString());
            indexLayout.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView)v;
                    String temp = selectedIndex.getText().toString();
                    TextView text = (TextView) layout.findViewById(R.id.alphabet_text);
                    text.setText(temp);
                    toast.setGravity(Gravity.TOP | Gravity.RIGHT, 60 , 110);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    lv.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
        }


    }

    private void getNewIndexList(ArrayList<String> Names) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < Names.size(); i++) {
            String name = Names.get(i);
            String index = name.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selectgroupphonebook,menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);   //To set the icon solor to white
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_back:

                if(PhoneBookShowGroupContents.flag_checked == 1) // handling 2 types of back
                {
                    Intent intent = new Intent(this, PhoneBookShowGroupContents.class);
                    intent.putExtra("gn",PhoneBookShowGroupContents.group_name);
                    startActivity(intent);
                }
                else {
                    Intent i = new Intent(this, CreateGroupPhonebook.class);
                    this.startActivity(i);
                }
                return true;


            case R.id.action_save:   // take to page that is showing the list of groups
                int j;
                for(j=0;j<PhoneBookGroupAdapter.selectedContactNames.size();j++) {
                    Log.i("Names:",PhoneBookGroupAdapter.selectedContactNames.get(j));
                }

                for(j=0;j<PhoneBookGroupAdapter.selectedContactNos.size();j++) {
                    Log.i("Nos:",PhoneBookGroupAdapter.selectedContactNos.get(j));
                }

                if(PhoneBookGroupAdapter.selectedContactNames.size() == 0 && PhoneBookGroupAdapter.selectedContactNos.size() == 0)
                {
                    Toast.makeText(getBaseContext(),"Select Records to be added in the Group",Toast.LENGTH_LONG).show();
                }
                else {

                    ph_gd2 = new PhonebookGroupDatabase(ctx);
                    Cursor res;
                    int ff = 0;
                    if(PhoneBookShowGroupContents.flag_checked == 1) //to add more records in the same group
                    {

                        String addGroupName = PhoneBookShowGroupContents.group_name;


                        ph_gd3 = new PhonebookGroupDatabase(ctx);
                        for (j = 0; j < PhoneBookGroupAdapter.selectedContactNames.size(); j++) {
                            ff = 0;
                            String contact_name = PhoneBookGroupAdapter.selectedContactNames.get(j);
                            String contact_no = PhoneBookGroupAdapter.selectedContactNos.get(j);

                            sqLiteDatabase = ph_gd3.getReadableDatabase();
                            res = sqLiteDatabase.rawQuery("select * from PhonebookGroupContents where groupName = '"+ addGroupName +"' ",null);
                            while(res.moveToNext())
                            {
                                if(contact_name.equals(res.getString(1).toString())  && contact_no.equals(res.getString(2).toString()))
                                {
                                    Toast.makeText(getBaseContext(),contact_name+" is already present in the Group",Toast.LENGTH_LONG).show();
                                    ff = 1;
                                    break;
                                }
                            }

                            if(ff == 0)
                                ph_gd2.addGroupContents(ph_gd2, PhoneBookShowGroupContents.group_name, contact_name, contact_no);
                        }

                        Intent intent = new Intent(this, PhoneBookShowGroupContents.class);
                        intent.putExtra("gn",PhoneBookShowGroupContents.group_name);
                        startActivity(intent);

                    }

                    else  //Adding records in newly created group
                    {

                        ph_gd = new PhonebookGroupDatabase(ctx);
                        ph_gd.addGroup(ph_gd, CreateGroupPhonebook.group_name);

                        for (j = 0; j < PhoneBookGroupAdapter.selectedContactNames.size(); j++) {
                            String contact_name = PhoneBookGroupAdapter.selectedContactNames.get(j);
                            String contact_no = PhoneBookGroupAdapter.selectedContactNos.get(j);
                            ph_gd2.addGroupContents(ph_gd2, CreateGroupPhonebook.group_name, contact_name, contact_no);
                        }

                        Intent intent = new Intent(this, PhoneBookShowGroupNames.class);
                        startActivity(intent);
                    }

                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


