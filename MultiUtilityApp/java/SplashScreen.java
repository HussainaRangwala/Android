package collection.anew.multiutilapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class SplashScreen extends Activity {


    ImageView imgHome;
    EditText username, password;
    Button bt;
    public static String show_message=" ";
    TextView newuser;
    Context ctx=this;
    ExpenseDatabase ed;
    IncomeDatabase id;
    String date_split[];

    int income=0,total_amt=0;
    String mon_name;
    int year,mon_no;
    public void checkValues()
    {
        String n2=" ";
        Cursor res1=ed.getAllData();
        Cursor income_res=id.getAllData();

        total_amt=0;
        income=0;


        while(res1.moveToNext())
        {

            // Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_LONG);
            date_split=res1.getString(1).split("/");

            if(Integer.parseInt(date_split[1])==(mon_no+1)&&Integer.parseInt(date_split[2])==year) {

                n2= res1.getString(3);
                total_amt += Integer.parseInt(n2);
            }


        }


        while(income_res.moveToNext())
        {
            date_split=income_res.getString(1).split("/");

            if(Integer.parseInt(date_split[1])==(mon_no+1)&&Integer.parseInt(date_split[2])==year) {

                n2= income_res.getString(3);
                income= Integer.parseInt(n2);
            }
        }
        if((income-total_amt)<0)
        {
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

            builder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
            builder.setContentTitle("Notification");
            builder.setContentText("Expense for the month "+Pocket_Expenser_Tab1Create.theMonth(mon_no)+" "+year+" Exceeds the Income");
            //View v1=getLayoutInflater().inflate(R.layout.activity_show_notification,null);
            //ShowNotification.tv_display=(TextView)v1.findViewById(R.id.tv_notification_message);
            //ShowNotification.tv_display.setText("Expense for the month "+Pocket_Expenser_Tab1Create.theMonth(mon_no)+" "+year+" Exceeds the Income");
            show_message="Expense for the month "+Pocket_Expenser_Tab1Create.theMonth(mon_no)+" "+year+" Exceeds the Income";
            Intent intent=new Intent(this,ShowNotification.class);
            TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ShowNotification.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification note = builder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;
            note.defaults |= Notification.DEFAULT_SOUND;

            NotificationManager NM= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NM.notify(0,note);
        }

    }

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ed=new ExpenseDatabase(ctx);
        id=new IncomeDatabase(ctx);
        Calendar cal=Calendar.getInstance();
        mon_no=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);

        checkValues();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                checkValues();
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
