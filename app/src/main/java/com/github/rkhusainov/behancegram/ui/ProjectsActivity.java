package com.github.rkhusainov.behancegram.ui;

import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.common.SingleFragmentActivity;

public class ProjectsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }
}
