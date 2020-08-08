package collection.anew.multiutilapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Admin on 16-02-2017.
 */

public class PhoneBookCustomAdapter extends ArrayAdapter {

    ArrayList<String> resultNames = new ArrayList();
    ArrayList<String> resultNos =  new ArrayList();
    Context context;
    private static LayoutInflater inflater = null;


    public PhoneBookCustomAdapter(Context context, int resource, ArrayList<String> alContactNames, ArrayList<String> alContactNos)
    {
        super(context,resource);
        resultNames =  alContactNames;
        resultNos = alContactNos;
    }

    static class LayoutHandler      //Holder class
    {
        TextView CONTACT_NAME,CONTACT_NUMBER;
    }

    public void add(Object object) {
        super.add(object);
        resultNames.add((String) object);
        resultNos.add((String)object);
    }

    public int getCount() {
        return resultNames.size();
    }

    public Object getItem(int position) {
        return resultNames.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler = null;

        if (row == null) //row does not exist
        {
            //Toast.makeText(getContext(),"Inside if of adapter",Toast.LENGTH_LONG).show();
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.contactlist_android_items, parent, false); //this gives full row of data
            layoutHandler = new LayoutHandler();
            layoutHandler.CONTACT_NAME = (TextView) row.findViewById(R.id.tv_android_contact_name); //we get individual components of row
            layoutHandler.CONTACT_NUMBER=(TextView) row.findViewById(R.id.tv_android_contact_phone);
            row.setTag(layoutHandler);
        }
        else // if row is already existing
        {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        layoutHandler.CONTACT_NAME.setText(resultNames.get(position));
        layoutHandler.CONTACT_NUMBER.setText(resultNos.get(position));


        TextView t1 = (TextView)row.findViewById(R.id.tv_android_contact_name);
        t1.setTextColor(Color.BLACK);

        TextView t2 = (TextView)row.findViewById(R.id.tv_android_contact_phone);
        t2.setTextColor(Color.BLACK);

        return  row;

        //return null;
    }



}
