package collection.anew.multiutilapp;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hussaina on 15-02-2017.
 */

public class PEExpenseListDataAdapter extends ArrayAdapter {
    static List list = new ArrayList();
    Map<Integer,String> mFragmentTags;
    public static PEExpenseListDataAdapter.LayoutHandler1 layoutHandler;
    public static View row;
    static ArrayList<String> selectedExpenseNames =  new ArrayList<>(); //to store the names and nos selected by user while creation of group
    static ArrayList<String> selectedExpenseAmt = new ArrayList<>();
    static ArrayList<String> selectedExpenseDate=new ArrayList<>();
    static int p;
    SparseBooleanArray sba1 = new SparseBooleanArray();

    public PEExpenseListDataAdapter(Context context, int resource) {
        super(context,resource);
        mFragmentTags=new HashMap<Integer, String>();
    }


    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    static class LayoutHandler1 {
        //holder class
        CheckBox CHECKBOX;
        TextView EXPENSE_NAME;
        TextView EXPENSE_AMT;

    }




    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        /*row = convertView;
        layoutHandler = null;
        if (row == null) //row does not exist
        {
            //Toast.makeText(getContext(),"Inside if of adapter",Toast.LENGTH_LONG).show();
            final LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_expense_row, parent, false); //this gives full row of data
            layoutHandler = new PEExpenseListDataAdapter.LayoutHandler1();
            layoutHandler.EXPENSE_NAME = (TextView) row.findViewById(R.id.tv_disp_exp_name); //we get individual components of row
            layoutHandler.EXPENSE_AMT=(TextView)row.findViewById(R.id.tv_exp_amt);
            layoutHandler.CHECKBOX=(CheckBox)row.findViewById(R.id.checkBox2);


        }
        else // if row is already existing
        {
            layoutHandler = (PEExpenseListDataAdapter.LayoutHandler1) row.getTag();
            layoutHandler.CHECKBOX.setOnCheckedChangeListener(null);
        }

        ExpenseClass EC=(ExpenseClass) getItem(position);
        layoutHandler.EXPENSE_NAME.setText(EC.getExpense_name());
        layoutHandler.EXPENSE_AMT.setText(EC.getExp_amt());
       // layoutHandler.CHECKBOX.setChecked(sba1.get(position));
        final PEExpenseListDataAdapter.LayoutHandler1 finalLayoutHandler = layoutHandler;

        row.setTag(layoutHandler);
        layoutHandler.CHECKBOX.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                //if (finalLayoutHandler.CHECKBOX.isChecked()) {
                  //  sba1.put(position, true);
                    CheckBox cb = (CheckBox) v;

                    ExpenseClass ob1 = (ExpenseClass) list.get(position);
                    selectedExpenseNames.add(ob1.getExpense_name());
                    selectedExpenseAmt.add(ob1.getExp_amt());
                    selectedExpenseDate.add(ob1.getExp_date());
                    p = position;
                }
                else {
                    selectedExpenseAmt.clear();
                    selectedExpenseDate.clear();
                    selectedExpenseNames.clear();
                    sba1.put(position, false);
                }


            }
        });

        return row;*/
        row = convertView;
        layoutHandler = null;
        if (row == null) //row does not exist
        {
            //Toast.makeText(getContext(),"Inside if of adapter",Toast.LENGTH_LONG).show();
            final LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_expense_row, parent, false); //this gives full row of data
            layoutHandler = new PEExpenseListDataAdapter.LayoutHandler1();
            layoutHandler.EXPENSE_NAME = (TextView) row.findViewById(R.id.tv_disp_exp_name); //we get individual components of row
            layoutHandler.EXPENSE_AMT=(TextView)row.findViewById(R.id.tv_exp_amt);
            layoutHandler.CHECKBOX=(CheckBox)row.findViewById(R.id.checkBox2);


        }
        else // if row is already existing
        {
            layoutHandler = (PEExpenseListDataAdapter.LayoutHandler1) row.getTag();

        }

        ExpenseClass EC=(ExpenseClass) getItem(position);
        layoutHandler.EXPENSE_NAME.setText(EC.getExpense_name());
        layoutHandler.EXPENSE_AMT.setText(EC.getExp_amt());
        row.setTag(layoutHandler);
        layoutHandler.CHECKBOX.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                if(cb.isChecked()) {
                    ExpenseClass ob1 = (ExpenseClass) list.get(position);
                    selectedExpenseNames.add(ob1.getExpense_name());
                    selectedExpenseAmt.add(ob1.getExp_amt());
                    selectedExpenseDate.add(ob1.getExp_date());
                    p = position;
                }
                else
                {
                    ExpenseClass ob1 = (ExpenseClass) list.get(position);
                    selectedExpenseNames.remove(ob1.getExpense_name());
                    selectedExpenseDate.remove(ob1.getExp_date());
                    selectedExpenseAmt.remove(ob1.getExp_amt());

                }
            }
        });

        return row;
    }
}
