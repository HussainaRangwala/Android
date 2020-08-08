package collection.anew.multiutilapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ExpenseViewDateFragment extends Fragment {

     static Button exp_view_btnFwd,exp_view_btnBwd,btn;
    static String mon_name1;
    static int year1,month1;
    View rootView;
    static TextView tvexpviewMonth;

    public static String theMonth1(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static int monthNumber1(String month_name)
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
//    Pocket_Expenser_Tab3View pe_tab3view;

    String date_split[];
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_expense_view_date_fragment, container, false);
        final ExpenseDatabase ed=new ExpenseDatabase(getContext());
          mon_name1=(String)android.text.format.DateFormat.format("MMMM", new Date());
        year1 = Calendar.getInstance().get(Calendar.YEAR);
        final  Pocket_Expenser_Tab3View pe_tab3view=new Pocket_Expenser_Tab3View();
        tvexpviewMonth=(TextView)rootView.findViewById(R.id.tv_exp_view_month);
        tvexpviewMonth.setText(mon_name1+"-"+year1);
        exp_view_btnBwd=(Button)rootView.findViewById(R.id.btn_exp_view_bwd1);
        exp_view_btnFwd=(Button)rootView.findViewById(R.id.btn_exp_view_fwd1);
        return  rootView;
    }
}
