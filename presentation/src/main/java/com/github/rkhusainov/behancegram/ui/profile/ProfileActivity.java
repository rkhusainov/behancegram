package com.github.rkhusainov.behancegram.ui.profile;

import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.common.SingleFragmentActivity;

public class ProfileActivity extends SingleFragmentActivity {

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return ProfileFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }
}
