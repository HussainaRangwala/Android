package collection.anew.multiutilapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hussaina on 27-01-2017.
 */

public class Pocket_Expenser_Tab1Create extends Fragment {
    Button btnexp,btninc;
    int total_amt=0;
    String date_split[];
    public static int year;
    ExpenseDatabase ed;
    IncomeDatabase id;
    Context ctx;
    Button btnFwd,btnBwd;
    static TextView tvMonth;
    int income;
    String n2="";
    public static String monthname;
    int month;
    public static TextView tv_exp_total1,tv_inc_total1,tv_total_amt1;

    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static int monthNumber(String month_name)
    {
        if(month_name.toLowerCase().equals("january"))
            return 0;
        else if(month_name.toLowerCase().equals("february"))
            return 1;
        else if(month_name.toLowerCase().equals("march"))
            return 2;
        else if(month_name.toLowerCase().equals("april"))
            return 3;
        else if(month_name.toLowerCase().equals("may"))
            return 4;
        else if(month_name.toLowerCase().equals("june"))
            return 5;
        else if(month_name.toLowerCase().equals("july"))
            return 6;
        else if(month_name.toLowerCase().equals("august"))
            return 7;
        else if(month_name.toLowerCase().equals("september"))
            return 8;
        else if(month_name.toLowerCase().equals("october"))
            return 9;
        else if(month_name.toLowerCase().equals("november"))
            return 10;
        else
            return 11;

    }

    public void updateFieldValues()
    {
        Cursor res1=ed.getAllData();
        Cursor income_res=id.getAllData();

        total_amt=0;
        income=0;
        while(res1.moveToNext())
        {

            //Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_LONG);
            date_split=res1.getString(1).split("/");

            if(Integer.parseInt(date_split[1])==(Pocket_Expenser_Tab1Create.monthNumber(Pocket_Expenser_Tab1Create.monthname)+1)&&Integer.parseInt(date_split[2])==Pocket_Expenser_Tab1Create.year) {

                n2= res1.getString(3);
                total_amt += Integer.parseInt(n2);
            }


        }


        String n1=total_amt+" ";
        Pocket_Expenser_Tab1Create.tv_exp_total1.setText(n1);

        while(income_res.moveToNext())
        {
            date_split=income_res.getString(1).split("/");

            if(Integer.parseInt(date_split[1])==(Pocket_Expenser_Tab1Create.monthNumber(Pocket_Expenser_Tab1Create.monthname)+1)&&Integer.parseInt(date_split[2])==Pocket_Expenser_Tab1Create.year) {

                n2= income_res.getString(3);
                income= Integer.parseInt(n2);
            }
        }
        n1=income+" ";
        tv_inc_total1.setText(n1);
        n1=(income-total_amt)+" ";
        if(income>total_amt) {
            tv_total_amt1.setText(n1);
            tv_total_amt1.setTextColor(Color.GREEN);
        }
        else
        {
            tv_total_amt1.setText(n1);
            tv_total_amt1.setTextColor(Color.RED);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pocket_expenser_tab1create, container, false);
        // Reload current fragment
        ViewPager vp=new ViewPager(getContext());
        vp.setOffscreenPageLimit(0);

        btnexp=(Button) rootView.findViewById(R.id.btn_exp);
        btninc=(Button)rootView.findViewById(R.id.btn_inc);
        tv_exp_total1=(TextView)rootView.findViewById(R.id.tv_exp_total);
        tv_inc_total1=(TextView)rootView.findViewById(R.id.tv_inc_total);
        tv_total_amt1=(TextView)rootView.findViewById(R.id.tv_bal);
        ed=new ExpenseDatabase(getContext());
        id=new IncomeDatabase(getContext());
        Cursor res=ed.getAllData();
        Cursor income_res=id.getAllData();
        btnBwd=(Button)rootView.findViewById(R.id.btn_bwd);
        btnFwd=(Button)rootView.findViewById(R.id.btn_fwd);
        monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        year = Calendar.getInstance().get(Calendar.YEAR);
        tvMonth=(TextView)rootView.findViewById(R.id.tv_month);
        tvMonth.setText(monthname+"-"+year);
        total_amt=income=0;
        updateFieldValues();
        total_amt=0;

        btnFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                month=monthNumber(monthname);
                if((month+1)==12) {
                    month = -1;
                    year=year+1;
                }
                tvMonth.setText(theMonth(month+1)+"-"+year);
                monthname=theMonth(month+1);
                //When the month changes the expense related to the month is updated
                updateFieldValues();
            }
        });

        btnBwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                month=monthNumber(monthname);
                if((month-1)==-1) {
                    month = 12;
                    year=year-1;
                }
                tvMonth.setText(theMonth(month-1)+"-"+year);
                monthname=theMonth(month-1);
                updateFieldValues();
            }
        });

        btnexp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PE_Expense_Button.class);
                startActivity(intent);
                updateFieldValues();

            }


        });

        btninc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PE_Income_Button.class);
                startActivity(intent);
                updateFieldValues();
            }
        });


        return rootView;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed())
        {
            onResume();
            updateFieldValues();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        //INSERT CUSTOM CODE HERE
    }
}
