package collection.anew.multiutilapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 12-02-2017.
 */

public class Tab1ShowContacts extends Fragment {

    View rootView;
    ListView lv;
    TextView textView;
    Map<String, Integer> mapIndex;
    static ArrayList<String> alContactNames;
    static ArrayList<String> alContactNos;
    String strArray[]={"Make a Call","Message"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.tab1showcontacts, container, false);

        Log.i("Inside List view","Root view tab");

        /*final ArrayList<String> alContactNames = new ArrayList<String>();
        final ArrayList<String> alContactNos = new ArrayList<String>();*/

        alContactNames = new ArrayList<>();
        alContactNos = new ArrayList<>();

        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.i(name, phoneNo);
                        alContactNames.add(name);
                        alContactNos.add(phoneNo);
                    }
                    pCur.close();
                }
            }
        }

        lv = (ListView) rootView.findViewById(R.id.list_view_contacts);
        PhoneBookCustomAdapter listDataAdapter = new PhoneBookCustomAdapter(getContext(), R.layout.contactlist_android_items, alContactNames, alContactNos);
        lv.setAdapter(listDataAdapter);

        getIndexList(alContactNames);    //function call to getIndexList


        final Toast toast = new Toast(getContext());

        LayoutInflater inflater1;
        final View layout = inflater.inflate(R.layout.alphabetic_toast,container,false);

        LinearLayout indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());

        for (final String index : indexList) {
            textView = (TextView) inflater.inflate(R.layout.side_index_item, null);
            textView.setText(index.toString());
            indexLayout.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView)v;
                    String temp = selectedIndex.getText().toString();
                    TextView text = (TextView) layout.findViewById(R.id.alphabet_text);
                    text.setText(temp);
                    toast.setGravity(Gravity.TOP | Gravity.RIGHT, 60 , 220);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    lv.setSelection(mapIndex.get(selectedIndex.getText()));
                        //selectedIndex.setTextColor(Color.BLUE);
                }
            });
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Your Choice")
                        .setItems(strArray, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(strArray[which].equals("Make a Call"))
                                        {
                                            String name = alContactNames.get(position);
                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel:" + alContactNos.get(position)));
                                            startActivity(callIntent);
                                        }

                                        else if(strArray[which].equals("Message"))
                                        {
                                               String name = alContactNames.get(position);
                                               Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                               sendIntent.setData(Uri.parse("smsto:"));
                                               sendIntent.setType("vnd.android-dir/mms-sms");
                                               sendIntent.putExtra("address"  , new String(alContactNos.get(position)));
                                               sendIntent.putExtra("sms_body"  , "");
                                               startActivity(sendIntent);
                                        }
                                    }
                                }).show();
            }
        });
        return rootView;
    }


    private void getIndexList(ArrayList<String> alContactNames) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < alContactNames.size(); i++) {
            String name = alContactNames.get(i);
            String index = name.substring(0, 1);
            if (mapIndex.get(index) == null)
                    mapIndex.put(index, i);
        }
    }

}


