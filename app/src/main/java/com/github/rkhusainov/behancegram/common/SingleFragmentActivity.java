package com.github.rkhusainov.behancegram.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.github.rkhusainov.behancegram.R;

public abstract class SingleFragmentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshOwner {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_swipe_container);
        mSwipeRefreshLayout = findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState == null) {
            changeFragment(getFragment());
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onRefresh() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof Refreshable) {
            ((Refreshable) fragment).onRefreshData();
        }
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }
}
