package collection.anew.multiutilapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PE_Expense_Button extends AppCompatActivity {
    public static EditText etDate, etCategory, etAmount;
    Button btnSave;
    int flag;
    String date,category, amt;
    String expname,expdate,expamt;
    int year_x,month_x,day_x,exp_id;
    static final int DILOG_ID=0;
    String n2;
    String date_split[];
    public static final int CAPTURE_REQ_CODE = 100;
    ExpenseDatabase ed;
    IncomeDatabase id;
    int total_amt=0,income=0;
    Context ctx=this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe__expense__button);

        final Calendar myCalendar;
        btnSave = (Button) findViewById(R.id.btn_save);
        etCategory = (EditText) findViewById(R.id.et_category);
        etAmount = (EditText) findViewById(R.id.et_amount);
        etDate = (EditText) findViewById(R.id.et_date);
        etDate.requestFocus();
        ed=new ExpenseDatabase(ctx);
        id=new IncomeDatabase(ctx);
        //tv_exp_total1=(TextView)findViewById(R.id.tv_exp_total);
        ed=new ExpenseDatabase(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        etDate.setText(dateFormat.format(new Date()));
        final Bundle extras = getIntent().getExtras();
        flag=0;
        if(extras!=null)
        {
            //Toast.makeText(getBaseContext(),"New:  "+PEExpenseListDataAdapter.selectedExpenseDate.get(0)+" "+PEExpenseListDataAdapter.selectedExpenseNames.get(0)+" "+PEExpenseListDataAdapter.selectedExpenseAmt.get(0),Toast.LENGTH_SHORT).show();
            if(PEExpenseListDataAdapter.selectedExpenseDate.size()!=0) {
                PE_Expense_Button.etDate.setText(PEExpenseListDataAdapter.selectedExpenseDate.get(0));
                PE_Expense_Button.etCategory.setText(PEExpenseListDataAdapter.selectedExpenseNames.get(0));
                PE_Expense_Button.etAmount.setText(PEExpenseListDataAdapter.selectedExpenseAmt.get(0));


                expdate = PEExpenseListDataAdapter.selectedExpenseDate.get(0);
                expname = PEExpenseListDataAdapter.selectedExpenseNames.get(0);
                expamt = PEExpenseListDataAdapter.selectedExpenseAmt.get(0);

                PEExpenseListDataAdapter.selectedExpenseAmt.clear();
                PEExpenseListDataAdapter.selectedExpenseNames.clear();
                PEExpenseListDataAdapter.selectedExpenseDate.clear();
            }
            else
            {
                expdate=expname=expamt=" ";
                flag=1;
            }
        }
        etCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Display_PE_Category.class);
                startActivityForResult(intent,CAPTURE_REQ_CODE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(etAmount.getText().toString().equals("")||etCategory.getText().toString().equals(""))
                {
                    Toast.makeText(getBaseContext(),"Please enter all the details",Toast.LENGTH_LONG).show();
                }
                else {
                    date = etDate.getText().toString();
                    category = etCategory.getText().toString();

                    amt = etAmount.getText().toString();

                    if (extras != null&&flag==0) {

                        ed.updateRecord(date,category,amt,expdate, expname, expamt);
                        Cursor c=ed.getAllData();

                    }
                    else
                    {
                        flag=0;
                        ed.putInformation1(ed, date.toString(), category.toString(), amt.toString());
                    }

                    Toast.makeText(getBaseContext(), "Expense Record Added successfully", Toast.LENGTH_LONG).show();

                    Cursor res1 = ed.getAllData();
                    Cursor income_res = id.getAllData();
                    //Pocket_Expenser_Tab1Create.monthname

                    total_amt = 0;
                    income = 0;
                    while (res1.moveToNext()) {

                        // Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_LONG);
                        date_split = res1.getString(1).split("/");

                        if (Integer.parseInt(date_split[1]) == (Pocket_Expenser_Tab1Create.monthNumber(Pocket_Expenser_Tab1Create.monthname) + 1) && Integer.parseInt(date_split[2]) == Pocket_Expenser_Tab1Create.year) {

                            n2 = res1.getString(3);
                            total_amt += Integer.parseInt(n2);
                        }


                    }
                    String n1 = total_amt + " ";
                    Pocket_Expenser_Tab1Create.tv_exp_total1.setText(n1);
                    //Pocket_Expenser_Tab2Edit pe_edit=new Pocket_Expenser_Tab2Edit();
                    //pe_edit.updateList();
                    while (income_res.moveToNext()) {
                        date_split = income_res.getString(1).split("/");

                        if (Integer.parseInt(date_split[1]) == (Pocket_Expenser_Tab1Create.monthNumber(Pocket_Expenser_Tab1Create.monthname) + 1) && Integer.parseInt(date_split[2]) == Pocket_Expenser_Tab1Create.year) {

                            n2 = income_res.getString(3);
                            income = Integer.parseInt(n2);
                        }
                    }
                    n1 = income + " ";
                    Pocket_Expenser_Tab1Create.tv_inc_total1.setText(n1);
                    n1 = (income - total_amt) + " ";
                    if (income > total_amt) {
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setText(n1);
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setTextColor(Color.GREEN);
                    } else {
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setText(n1);
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setTextColor(Color.RED);
                    }

                    Intent intent=new Intent(getBaseContext(),PocketExpenser.class);
                    startActivity(intent);

                    //finish();
                }
            }
        });
        showDialogOnButtonClick();

    }

    public void showDialogOnButtonClick()
    {
        etDate=(EditText) findViewById(R.id.et_date);
        etDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                OnCreateDialog(DILOG_ID).show();
            }
        });

    }
    @RequiresApi(api = VERSION_CODES.N)
    protected Dialog OnCreateDialog(int id)
    {


        if(id==DILOG_ID) {
            Calendar c= Calendar.getInstance();

            return new DatePickerDialog(this, dpickerListener, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener()
    {

        @RequiresApi(api = VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x=year;
            month_x=month;
            day_x=dayOfMonth;
            etDate.setText(day_x+"/"+(month_x+1)+"/"+year_x);
        }
    };

    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode, resultcode, data);
        if(data==null)
        {
            return;
        }
        else if(requestcode == CAPTURE_REQ_CODE)
        {
            String cat_name = data.getStringExtra("cat_name");
            etCategory.setText(cat_name);

        }
    }

}
