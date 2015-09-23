package com.worked.swipeable_card_list.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.worked.swipeable_card_list.R;
import com.worked.swipeable_card_list.utils.ParseUtils;
import com.worked.swipeable_card_list.utils.shared.Constants;

public class Intro_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(R.string.app_name);

        setContentView(R.layout.activity_layout);

        // set up once
        if (savedInstanceState == null) {
            setupData();

            setUpFragment();
        }
    }

    /**
     * Set Up Hub Data
     */
    private void setupData() {
        ParseUtils.parseJsonData(this.getBaseContext(), Constants.MODEL_JSON);
    }

    /**
     * Set Up Fragment
     */
    private void setUpFragment() {
        Intro_Fragment hub_fragment = Intro_Fragment.newInstance();

        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag(Intro_Fragment.TAG) == null) {
            ft.add(R.id.container, hub_fragment, Intro_Fragment.TAG);

            ft.commit();
        }
    }
}
