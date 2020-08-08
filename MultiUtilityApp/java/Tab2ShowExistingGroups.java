package collection.anew.multiutilapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Admin on 12-02-2017.
 */

public class Tab2ShowExistingGroups extends Fragment {



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2existing_groups, container, false);


        TextView add_new_group = (TextView)rootView.findViewById(R.id.tv_add_new_group);
        add_new_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateGroupPhonebook.class);
                startActivity(i);
            }
        });


        TextView show_existing_groups = (TextView)rootView.findViewById(R.id.tv_show_existing_groups);
        show_existing_groups.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PhoneBookShowGroupNames.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}



