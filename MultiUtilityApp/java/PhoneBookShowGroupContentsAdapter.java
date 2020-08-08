package collection.anew.multiutilapp;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 14-03-2017.
 */

public class PhoneBookShowGroupContentsAdapter extends ArrayAdapter {

    ArrayList<String> groupContactNames = new ArrayList<String>();
    ArrayList<String> groupContactNumbers = new ArrayList<String>();

    static ArrayList<String> selectedNames =  new ArrayList<>(); //to store the names and nos selected by user while creation of group
    static ArrayList<String> selectedNos = new ArrayList<>();
    int imageId;
    SparseBooleanArray sba1 = new SparseBooleanArray();


    public PhoneBookShowGroupContentsAdapter(Context context, int resource, int prgmImage, ArrayList<String> ContactNames, ArrayList<String> ContactNumbers) {
        super(context, resource);
        imageId = prgmImage;
        groupContactNames = ContactNames;
        groupContactNumbers = ContactNumbers;
        selectedNames = new ArrayList<String>();
        selectedNos = new ArrayList<String>();
    }


    static class LayoutHandler {        //holder class
        TextView CONTACT_NAME;
        TextView CONTACT_NUMBER;
        ImageView CONTACT_IMAGE;
        CheckBox checkBox;
    }

    public void add(Object object) {
        super.add(object);
        groupContactNames.add((String) object);
        groupContactNumbers.add((String) object);
    }


    public int getCount() {
        return groupContactNames.size();
    }

    public Object getItem(int position) {
        return groupContactNames.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler = null;
        if (row == null) //row does not exist
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_group_contents_row, parent, false); //reused display_group_name_row for imageview and textview
            layoutHandler = new LayoutHandler();
            layoutHandler.CONTACT_NAME = (TextView) row.findViewById(R.id.tv_disp_group_contact_name);
            layoutHandler.CONTACT_NUMBER = (TextView) row.findViewById(R.id.tv_disp_group_contact_number);
            layoutHandler.CONTACT_IMAGE = (ImageView) row.findViewById(R.id.img_group_contents);

            layoutHandler.checkBox = (CheckBox)row.findViewById(R.id.checkbox_group);
            layoutHandler.checkBox.setVisibility(View.GONE);

            row.setTag(layoutHandler);

        } else {
            layoutHandler = (LayoutHandler) row.getTag();
            layoutHandler.checkBox.setOnCheckedChangeListener(null);
        }

        layoutHandler.CONTACT_NAME.setText(groupContactNames.get(position));
        layoutHandler.CONTACT_NUMBER.setText(groupContactNumbers.get(position));
        layoutHandler.CONTACT_IMAGE.setImageResource(imageId);

        layoutHandler.checkBox.setChecked(sba1.get(position));
        final LayoutHandler finalLayoutHandler = layoutHandler;   // for checkbox any variable inside inner class must be final .. so we declare this var to use in onclick of checkbo
        // x

        layoutHandler.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (finalLayoutHandler.checkBox.isChecked()) {
                    sba1.put(position, true);
                    CheckBox cb = (CheckBox) v;
                    selectedNames.add(groupContactNames.get(position));
                    selectedNos.add(groupContactNumbers.get(position));
                    //Toast.makeText(getContext(), "Clicked on Checkbox: " + groupContactNames.get(position) + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                } else {
                    selectedNames.remove(groupContactNames.get(position));
                    selectedNos.remove(groupContactNumbers.get(position));
                    sba1.put(position, false);
                }
            }
        });


        TextView tv = (TextView)row.findViewById(R.id.tv_disp_group_contact_name);
        tv.setTextColor(Color.BLACK);

        TextView tv1 = (TextView)row.findViewById(R.id.tv_disp_group_contact_number);
        tv1.setTextColor(Color.BLACK);

        return row;
    }

}