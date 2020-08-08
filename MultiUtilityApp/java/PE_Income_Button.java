package collection.anew.multiutilapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
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

public class PE_Income_Button extends AppCompatActivity {
    EditText etDate, etCategory, etAmount;
    Button btnSave;
    String date,category, amt;
    int year_x,month_x,day_x,income;
    ExpenseDatabase ed;
    static final int DILOG_ID=0;
    String n2;
    String date_split[];
    IncomeDatabase id;
    int total_amt=0;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pe__income__button);
        btnSave = (Button) findViewById(R.id.btn_save);
        etCategory = (EditText) findViewById(R.id.et_category);
        etAmount = (EditText) findViewById(R.id.et_amount);
        etDate = (EditText) findViewById(R.id.et_date);
        etDate.requestFocus();
        id=new IncomeDatabase(ctx);
        ed=new ExpenseDatabase(ctx);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        etDate.setText(dateFormat.format(new Date()));

        etCategory.setText("Salary");
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

                    id.putInformation1(id, date.toString(), category.toString(), amt.toString());

                    Toast.makeText(getBaseContext(), "Income Record Added successfully", Toast.LENGTH_LONG).show();

                    Cursor res1 = ed.getAllData();
                    Cursor income_res = id.getAllData();

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
                    while (income_res.moveToNext()) {
                        date_split = income_res.getString(1).split("/");

                        if (Integer.parseInt(date_split[1]) == (Pocket_Expenser_Tab1Create.monthNumber(Pocket_Expenser_Tab1Create.monthname) + 1) && Integer.parseInt(date_split[2]) == Pocket_Expenser_Tab1Create.year) {

                            n2 = income_res.getString(3);
                            income = Integer.parseInt(n2);
                        }
                    }
                    String n1 = income + " ";
                    Pocket_Expenser_Tab1Create.tv_inc_total1.setText(n1);
                    n1 = (income - total_amt) + " ";
                    if (income > total_amt) {
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setText(n1);
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setTextColor(Color.GREEN);
                    } else {
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setText(n1);
                        Pocket_Expenser_Tab1Create.tv_total_amt1.setTextColor(Color.RED);
                    }

                    finish();
                }
            }
        });


        showDialogOnButtonClick();

    }
    public void showDialogOnButtonClick()
    {
        etDate=(EditText) findViewById(R.id.et_date);
        etDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                OnCreateDialog(DILOG_ID).show();
            }
        });

    }


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

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x=year;
            month_x=month;
            day_x=dayOfMonth;
            etDate.setText(day_x+"/"+(month_x+1)+"/"+year_x);
        }
    };
}
