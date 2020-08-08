

package collection.anew.multiutilapp;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class About extends Activity
{
  ImageView imgHome;
  @Override
  protected void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    setContentView(R.layout.activity_about);
    imgHome=(ImageView)findViewById(R.id.img_home);
    imgHome.setImageResource(R.drawable.home2);
  }
}

