package com.worked.db_recycler_view.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.worked.db_recycler_view.R;
import com.worked.db_recycler_view.utils.ParseUtils;
import com.worked.db_recycler_view.utils.shared.Constants;

public class Intro_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(R.string.recycler_view);

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
