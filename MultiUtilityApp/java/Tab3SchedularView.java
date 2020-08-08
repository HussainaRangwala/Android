package collection.anew.multiutilapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 16-03-2017.
 */

public class Tab3SchedularView extends Fragment {

    private final String TAG = "AlarmMe";

    private ListView mAlarmList;
    //public static AlarmListAdapter mAlarmListAdapter;
    //private Alarm mCurrentAlarm;

    private final int NEW_ALARM_ACTIVITY = 0;
    private final int EDIT_ALARM_ACTIVITY = 1;
    private final int PREFERENCES_ACTIVITY = 2;
    private final int ABOUT_ACTIVITY = 3;

    private final int CONTEXT_MENU_EDIT = 0;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_DUPLICATE = 2;
    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.tab3schedularview,container, false);


        Log.i(TAG, "AlarmMe.onCreate()");


        mAlarmList = (ListView)rootView.findViewById(R.id.alarm_list);
        Tab1SchedularCreate.mAlarmListAdapter = new AlarmListAdapter(getContext());
        mAlarmList.setAdapter(Tab1SchedularCreate.mAlarmListAdapter);
        mAlarmList.setOnItemClickListener(mListOnItemClickListener);
        registerForContextMenu(mAlarmList);


        return  rootView;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "AlarmMe.onDestroy()");
          Tab1SchedularCreate.mAlarmListAdapter.save();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "AlarmMe.onResume()");
        Tab1SchedularCreate.mAlarmListAdapter.updateAlarms();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                Tab1SchedularCreate.mCurrentAlarm.fromIntent(data);
                Tab1SchedularCreate.mAlarmListAdapter.add(Tab1SchedularCreate.mCurrentAlarm);
            }
            Tab1SchedularCreate.mCurrentAlarm = null;
        }
        else if (requestCode == EDIT_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                Tab1SchedularCreate.mCurrentAlarm.fromIntent(data);
                Tab1SchedularCreate.mAlarmListAdapter.update(Tab1SchedularCreate.mCurrentAlarm);
            }
            Tab1SchedularCreate.mCurrentAlarm = null;
        }
        else if (requestCode == PREFERENCES_ACTIVITY)
        {
            Tab1SchedularCreate.mAlarmListAdapter.onSettingsUpdated();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_start_schedular, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (R.id.menu_settings == item.getItemId())
        {
            Intent intent = new Intent(getContext(), Preferences.class);
            startActivityForResult(intent, PREFERENCES_ACTIVITY);
            return true;
        }
        else if (R.id.menu_about == item.getItemId())
        {
            Intent intent = new Intent(getContext(), About.class);
            startActivity(intent);
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.alarm_list)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(Tab1SchedularCreate.mAlarmListAdapter.getItem(info.position).getTitle());
            menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Edit");
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
            //menu.add(Menu.NONE, CONTEXT_MENU_DUPLICATE, Menu.NONE, "Duplicate");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = item.getItemId();

        if (index == CONTEXT_MENU_EDIT)
        {
            Intent intent = new Intent(getContext(), EditAlarm.class);

            Tab1SchedularCreate.mCurrentAlarm = Tab1SchedularCreate.mAlarmListAdapter.getItem(info.position);
            Tab1SchedularCreate.mCurrentAlarm.toIntent(intent);
            startActivityForResult(intent, EDIT_ALARM_ACTIVITY);

        }
        else if (index == CONTEXT_MENU_DELETE)
        {
            Tab1SchedularCreate.mAlarmListAdapter.delete(info.position);
        }
        else if (index == CONTEXT_MENU_DUPLICATE)
        {
            Alarm alarm = Tab1SchedularCreate.mAlarmListAdapter.getItem(info.position);
            Alarm newAlarm = new Alarm(getContext());
            Intent intent = new Intent();

            alarm.toIntent(intent);
            newAlarm.fromIntent(intent);
            newAlarm.setTitle(alarm.getTitle() + " (copy)");
            Tab1SchedularCreate.mAlarmListAdapter.add(newAlarm);
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getContext(), EditAlarm.class);

            Tab1SchedularCreate.mCurrentAlarm = Tab1SchedularCreate.mAlarmListAdapter.getItem(position);
            Tab1SchedularCreate.mCurrentAlarm.toIntent(intent);
            Tab3SchedularView.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
    };
}
