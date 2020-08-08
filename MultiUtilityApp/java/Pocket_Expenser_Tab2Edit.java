package collection.anew.multiutilapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hussaina on 27-01-2017.
 */

public class Pocket_Expenser_Tab2Edit extends Fragment {

    ExpenseDatabase ed;
    ListView listView;
    String exp_array[];
    int exp_amt[],month;
    int i=0;
    CheckBox ch1;
    Button btnFwd,btnBwd,btn;
    TextView tvMonth;
    String exp_date[];
    View rootView;
    String mon_name;
    int year,total_amt=0,flag=0;
    public static int CHECK_FLAG=0,ACTION_EDIT_FLAG=0;


    public static PEExpenseListDataAdapter adapter;
    public void updateList()
    {
        String n1;
        Cursor res1=ed.getAllData();
        i=0;
        flag=0;
        while(res1.moveToNext())
        {
            date_split=res1.getString(1).split("/");

            if(Integer.parseInt(date_split[1])==(Pocket_Expenser_Tab1Create.monthNumber(mon_name)+1)&&Integer.parseInt(date_split[2])==year) {

                n1=res1.getString(2);
                exp_amt[i]=Integer.parseInt(res1.getString(3));
                exp_array[i]=n1;
                exp_date[i]=date_split[0]+"/"+date_split[1]+"/"+date_split[2];
                i++;
                flag++;
            }


        }

        if(flag==0)
        {
            listView.setAdapter(null);

        }
        else {

            adapter.clear();
            PEExpenseListDataAdapter.list=new ArrayList();
            adapter=new PEExpenseListDataAdapter(getContext(),R.layout.display_expense_row);
            for (int k=0;k<(i);k++) {

                ExpenseClass obj = new ExpenseClass(exp_array[k], exp_amt[k],exp_date[k]);
                adapter.add(obj);

            }
            listView.setAdapter(adapter);
        }

    }

    String date_split[],n2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView = inflater.inflate(R.layout.pocket_expenser_tab2edit, container, false);
        ViewPager vp=new ViewPager(getContext());
        vp.setOffscreenPageLimit(0);
        listView=(ListView) rootView.findViewById(R.id.list_view_expense);
        adapter=new PEExpenseListDataAdapter(getContext(),R.layout.display_expense_row);


        ed=new ExpenseDatabase(getContext());
        Cursor res=ed.getAllData();
        exp_array=new String[100];
        exp_amt=new int[100];
        exp_date=new String[100];
        String n1;

        mon_name=(String)android.text.format.DateFormat.format("MMMM", new Date());
        year = Calendar.getInstance().get(Calendar.YEAR);
        tvMonth=(TextView)rootView.findViewById(R.id.tv_mon);
        tvMonth.setText(mon_name+"-"+year);
        btnBwd=(Button)rootView.findViewById(R.id.btn_bwd1);
        btnFwd=(Button)rootView.findViewById(R.id.btn_fwd1);
        //listView.setAdapter(adapter);
        updateList();


        btnFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1;
                month=Pocket_Expenser_Tab1Create.monthNumber(mon_name);
                if((month+1)==12) {
                    month = -1;
                    year=year+1;
                }
                tvMonth.setText(Pocket_Expenser_Tab1Create.theMonth(month+1)+"-"+year);
                mon_name=Pocket_Expenser_Tab1Create.theMonth(month+1);

                updateList();
            }
        });

        btnBwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1;
                month=Pocket_Expenser_Tab1Create.monthNumber(mon_name);
                if((month-1)==-1) {
                    month = 12;
                    year=year-1;
                }
                tvMonth.setText(Pocket_Expenser_Tab1Create.theMonth(month-1)+"-"+year);

                mon_name=Pocket_Expenser_Tab1Create.theMonth(month-1);
                Cursor res=ed.getAllData();
                updateList();
            }
        });

        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed())
        {
            updateList();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            updateList();
            return;
        }

        //INSERT CUSTOM CODE HERE
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pocketexpensertab2edit, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon();// change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        Drawable yourdrawable1 = menu.getItem(1).getIcon();// change 0 with 1,2 ...
        yourdrawable1.mutate();
        yourdrawable1.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        Drawable yourdrawable2 = menu.getItem(2).getIcon();// change 0 with 1,2 ...
        yourdrawable2.mutate();
        yourdrawable2.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if(PEExpenseListDataAdapter.selectedExpenseDate.size()==0)
                    Toast.makeText(getContext(),"No records selected for deletion",Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to delete?")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                    for (i = 0; i < PEExpenseListDataAdapter.selectedExpenseNames.size(); i++) {
                                        ed.deleteRow(PEExpenseListDataAdapter.selectedExpenseDate.get(i), PEExpenseListDataAdapter.selectedExpenseNames.get(i), PEExpenseListDataAdapter.selectedExpenseAmt.get(i));

                                    }
                                    PEExpenseListDataAdapter.selectedExpenseAmt.clear();
                                    PEExpenseListDataAdapter.selectedExpenseNames.clear();
                                    PEExpenseListDataAdapter.selectedExpenseDate.clear();
                                    updateList();

                                    Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();

                                    dialog.cancel();
                                }
                            }).show();
                }
                return true;
            case R.id.action_edit:
                Intent intent=new Intent(getContext(),PE_Expense_Button.class);
                intent.putExtra("edit: ","edittext");
                startActivity(intent);
                updateList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
