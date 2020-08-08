package collection.anew.multiutilapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hussaina on 07-02-2017.
 */

public class PECategoryListDataAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int [] imageId;



    public PECategoryListDataAdapter(Context context, int resource, int[] prgmImages) {
        super(context, resource);
        imageId = prgmImages;
    }




    static class LayoutHandler {        //holder class
        TextView CATEGORY_NAME;
        ImageView CATEGORY_IMAGE;
    }


    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PECategoryListDataAdapter.LayoutHandler layoutHandler = null;
        if (row == null) //row does not exist
        {
            //Toast.makeText(getContext(),"Inside if of adapter",Toast.LENGTH_LONG).show();
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_category_row, parent, false); //this gives full row of data
            layoutHandler = new PECategoryListDataAdapter.LayoutHandler();
            layoutHandler.CATEGORY_NAME = (TextView) row.findViewById(R.id.tv_disp_cat_name); //we get individual components of row
            layoutHandler.CATEGORY_IMAGE=(ImageView)row.findViewById(R.id.img_category);
            row.setTag(layoutHandler);
        }
        else // if row is already existing
        {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        CategoryDataProvider dataProvider = (CategoryDataProvider) this.getItem(position); // this will return each obj from the list
        layoutHandler.CATEGORY_NAME.setText(dataProvider.getCategory_name());

        TextView tv = (TextView)row.findViewById(R.id.tv_disp_cat_name);
        tv.setTextColor(Color.BLACK);


        layoutHandler.CATEGORY_IMAGE.setImageResource(imageId[position]);
        return row;
    }
}
