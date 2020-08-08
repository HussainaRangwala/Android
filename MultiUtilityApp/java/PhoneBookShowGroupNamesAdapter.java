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
 * Created by Admin on 12-03-2017.
 */

public class PhoneBookShowGroupNamesAdapter extends ArrayAdapter {

    ArrayList<String> groupNames = new ArrayList<String>();
    static ArrayList<String> selectedGroupNames = new ArrayList<String>();
    int  imageId;
    SparseBooleanArray sba2 = new SparseBooleanArray();


public PhoneBookShowGroupNamesAdapter(Context context, int resource,int prgmImage, ArrayList<String> Names) {
        super(context, resource);
        imageId = prgmImage;
        groupNames = Names;
        selectedGroupNames  = new ArrayList<String>();
    }

    static class LayoutHandler {        //holder class
        TextView GROUP_NAME;
        ImageView GROUP_IMAGE;
        CheckBox checkBox1;
    }

    public void add(Object object) {
        super.add(object);
        groupNames.add((String) object);
    }

    public int getCount() {
        return groupNames.size();
    }

    public Object getItem(int position) {
        return groupNames.get(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler = null;
        if (row == null) //row does not exist
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_group_name_row, parent, false); //reused display_group_name_row for imageview and textview
            layoutHandler = new LayoutHandler();
            layoutHandler.GROUP_NAME = (TextView) row.findViewById(R.id.tv_disp_group_name);
            layoutHandler.GROUP_IMAGE=(ImageView)row.findViewById(R.id.img_group_name);

            layoutHandler.checkBox1 = (CheckBox)row.findViewById(R.id.checkbox_group_name);
            layoutHandler.checkBox1.setVisibility(View.GONE);

            row.setTag(layoutHandler);
        }
        else
        {
            layoutHandler = (LayoutHandler) row.getTag();
            layoutHandler.checkBox1.setOnCheckedChangeListener(null);
        }

        layoutHandler.GROUP_NAME.setText(groupNames.get(position));
        layoutHandler.GROUP_IMAGE.setImageResource(imageId);

        layoutHandler.checkBox1.setChecked(sba2.get(position));
        final LayoutHandler finalLayoutHandler = layoutHandler;


        layoutHandler.checkBox1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (finalLayoutHandler.checkBox1.isChecked()) {
                    sba2.put(position, true);
                    CheckBox cb = (CheckBox) v;
                    selectedGroupNames.add(groupNames.get(position));
                    //Toast.makeText(getContext(), "Clicked on Checkbox: " + groupNames.get(position) + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                } else {
                    selectedGroupNames.remove(groupNames.get(position));
                    sba2.put(position, false);
                }
            }
        });


        TextView tv = (TextView)row.findViewById(R.id.tv_disp_group_name);
        tv.setTextColor(Color.BLACK);
        return row;
    }

}
