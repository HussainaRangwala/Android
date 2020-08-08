package collection.anew.multiutilapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroupPhonebook extends AppCompatActivity {
    EditText et_group_name;
    public static String group_name;
    Cursor c;
    PhonebookGroupDatabase pgd;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_phonebook);

    }

    @Override  //This method to make the menu visible on screen
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creategroupphonebook,menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);   //To set the icon solor to white
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                Intent i = new Intent(CreateGroupPhonebook.this,StartCollegePhonebook.class);
                this.startActivity(i);
                return true;

            case R.id.action_next:
                et_group_name = (EditText)findViewById(R.id.et_group_name);
                group_name = et_group_name.getText().toString();

                pgd = new PhonebookGroupDatabase(getApplicationContext());
                sqLiteDatabase = pgd.getReadableDatabase();
                c = pgd.getGroupNames(sqLiteDatabase);
                int flag1 = 0,flag2 =0;
                if(c.moveToNext()) {
                    do
                    {
                        if(group_name.equals(c.getString(0).toString())) {
                            Toast.makeText(getBaseContext(), "Groupname already exists.Please Enter a another group name.", Toast.LENGTH_LONG).show();
                            flag1 = 1;
                            break;
                        }

                    }while(c.moveToNext());
                }


                if(group_name.matches("")) {
                    Toast.makeText(getBaseContext(), "Enter The Group Name To Proceed", Toast.LENGTH_LONG).show();
                    flag2 =1;
                }

                if(flag1 == 0 && flag2 ==0)
                {
                    Intent intent = new Intent(this, SelectGroupPhonebook.class);
                    startActivity(intent);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
