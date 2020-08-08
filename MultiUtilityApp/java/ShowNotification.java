package collection.anew.multiutilapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ShowNotification extends AppCompatActivity {
    public static TextView tv_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        tv_display=(TextView)findViewById(R.id.tv_notification_message);
        tv_display.setText(SplashScreen.show_message);
    }

    public void dismiss_function(View v)
    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
