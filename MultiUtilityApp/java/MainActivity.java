package collection.anew.multiutilapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgPe,imgPb,imgSch,imgAbt;
    CategoryDatabase cd;
    SQLiteDatabase db;
    Context ctx = this;
    int i;
    public static int[] categoryIdList={1,2,3,4,5,6,7,8,9,10,11};
    public static String[] categoriesList = {"Clothes","Eating Out","Entertainment","Fuel","General","Gifts","Holidays","Kids","Shopping","Travel","Sports"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {//Current state is stored in Bundle
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgPe=(ImageView)findViewById(R.id.img_pe);
        imgPe.setImageResource(R.drawable.pe);//To make an ImageView visible on the screen
        imgPe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new CategoryDatabase(ctx); //as soon as pe icon is clicked we will fill the database contents
                cd.deleteCategories(cd);        //we delete everytime else on multiple clicks values get added multiple times
                for(i=0; i < 11 ;i++) {
                    cd.addCategories(cd, categoryIdList[i], categoriesList[i]);
                }
                //finish();
                Intent intent=new Intent(getApplicationContext(),PocketExpenser.class);
                startActivity(intent);
            }
        });

        imgPb=(ImageView)findViewById(R.id.img_pb);
        imgPb.setImageResource(R.drawable.pb);
        imgPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StartCollegePhonebook.class);
                startActivity(intent);
            }
        });

        imgSch=(ImageView)findViewById(R.id.img_sch);
        imgSch.setImageResource(R.drawable.sch2);
        imgSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StartSchedular.class);
                startActivity(intent);

            }


        });

        imgAbt=(ImageView)findViewById(R.id.img_abt);
        imgAbt.setImageResource(R.drawable.about_us);
        imgAbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),About.class);
                startActivity(intent);


            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);   //To set the icon solor to white
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_back:
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

