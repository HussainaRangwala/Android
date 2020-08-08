package collection.anew.multiutilapp;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 20-02-2017.
 */

public class PhoneBookGroupAdapter extends ArrayAdapter {

    ArrayList<String> rNames = new ArrayList();
    ArrayList<String> rNos = new ArrayList();
    static ArrayList<String> selectedContactNames =  new ArrayList<>(); //to store the names and nos selected by user while creation of group
    static ArrayList<String> selectedContactNos = new ArrayList<>();
    Context context;
    SparseBooleanArray sba = new SparseBooleanArray();


    public PhoneBookGroupAdapter(Context context, int resource, ArrayList<String> Names, ArrayList<String> Nos) {
        super(context, resource);
        rNames = Names;
        rNos = Nos;
        selectedContactNames = new ArrayList<String>();
        selectedContactNos = new ArrayList<String>();
    }


    static class Holder      //Holder class
    {
        public CheckBox checkBox;
        TextView CONTACT_NAME, CONTACT_NUMBER;
    }

    public void add(Object object) {
        super.add(object);
        rNames.add((String) object);
        rNos.add((String) object);
    }

    public int getCount() {
        return rNames.size();
    }

    public Object getItem(int position) {
        return rNames.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Holder layoutHandler = null;

        if (row == null) //row does not exist
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.select_group_phonebook_checkbox, parent, false); //this gives full row of data
            layoutHandler = new Holder();
            layoutHandler.CONTACT_NAME = (TextView) row.findViewById(R.id.tv_android_contact_name_checkbox); //we get individual components of row
            layoutHandler.CONTACT_NUMBER = (TextView) row.findViewById(R.id.tv_android_contact_phone_checkbox);
            layoutHandler.checkBox  = (CheckBox) row.findViewById(R.id.checkbox_phonebook);
            row.setTag(layoutHandler);

        } else {
            layoutHandler = (Holder) row.getTag();
            layoutHandler.checkBox.setOnCheckedChangeListener(null);   //..added now for cbox
        }

        layoutHandler.CONTACT_NAME.setText(rNames.get(position));
        layoutHandler.CONTACT_NUMBER.setText(rNos.get(position));
        layoutHandler.checkBox.setChecked(sba.get(position));


        final Holder finalLayoutHandler = layoutHandler;   // for checkbox any variable inside inner class must be final .. so we declare this var to use in onclick of checkbo
        // x

        layoutHandler.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (finalLayoutHandler.checkBox.isChecked()) {
                    sba.put(position, true);
                    CheckBox cb = (CheckBox) v;
                    selectedContactNames.add(rNames.get(position));
                    selectedContactNos.add(rNos.get(position));
                    //Toast.makeText(getContext(), "Clicked on Checkbox: " + rNames.get(position) + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                } else {
                    selectedContactNames.remove(rNames.get(position));
                    selectedContactNos.remove(rNos.get(position));
                    sba.put(position, false);
                }
            }
        });




        TextView t1 = (TextView) row.findViewById(R.id.tv_android_contact_name_checkbox);
        t1.setTextColor(Color.BLACK);

        TextView t2 = (TextView) row.findViewById(R.id.tv_android_contact_phone_checkbox);
        t2.setTextColor(Color.BLACK);


        CheckBox c1 = (CheckBox)row.findViewById(R.id.checkbox_phonebook);
        c1.setTextColor(Color.BLACK);

        return row;

    }

}

