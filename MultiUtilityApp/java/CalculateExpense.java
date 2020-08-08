package collection.anew.multiutilapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CalculateExpense extends AppCompatActivity {

    String []date_split;
    int total=0;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_expense);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseDatabase ed=new ExpenseDatabase(getBaseContext());
                Cursor res=ed.getAllData();

                while(res.moveToNext())
                {
                    date_split=res.getString(1).split("/");
                    if(date_split[1].equals("3"))
                    {
                        String n1=res.getString(3).toString();
                        Toast.makeText(getBaseContext(),"Hie in res loop "+date_split[1],Toast.LENGTH_SHORT).show();
                        total+=Integer.parseInt(n1);

                    }
                }
                Toast.makeText(getBaseContext(),total+" ",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
