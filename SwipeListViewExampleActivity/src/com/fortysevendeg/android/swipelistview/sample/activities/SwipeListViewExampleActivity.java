/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.fortysevendeg.android.swipelistview.sample.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fortysevendeg.android.swipelistview.R;
import com.fortysevendeg.android.swipelistview.sample.adapters.PackageItem;
import com.fortysevendeg.android.swipelistview.sample.dialogs.AboutDialog;
import com.fortysevendeg.android.swipelistview.sample.utils.PreferencesManager;
import com.fortysevendeg.android.swipelistview.sample.utils.SettingsManager;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.android.swipelistview.sample.adapters.PackageAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwipeListViewExampleActivity extends FragmentActivity {

    private static final int REQUEST_CODE_SETTINGS = 0;
    private PackageAdapter adapter;
    private List<PackageItem> data;

    private SwipeListView swipeListView;

    private ProgressDialog progressDialog;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.swipe_list_view_activity);

        data = new ArrayList<PackageItem>();

        adapter = new PackageAdapter(this, data);

        swipeListView = (SwipeListView) findViewById(R.id.example_lv_list);

        if (Build.VERSION.SDK_INT >= 11) {
            swipeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            swipeListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + swipeListView.getCountSelected() + ")");
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_delete) {
                        swipeListView.dismissSelected();
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_choice_items, menu);
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    swipeListView.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    data.remove(position);
                }
                adapter.notifyDataSetChanged();
            }

        });

        swipeListView.setAdapter(adapter);

        reload();

        new ListAppTask().execute();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    private void reload() {
        SettingsManager settings = SettingsManager.getInstance();
        swipeListView.setSwipeMode(settings.getSwipeMode());
        swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
        swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        swipeListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
        swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        swipeListView.setAnimationTime(settings.getSwipeAnimationTime());
        swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
    }

    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTINGS:
                reload();
        }
    }

    public class ListAppTask extends AsyncTask<Void, Void, List<PackageItem>> {

        protected List<PackageItem> doInBackground(Void... args) {
            PackageManager appInfo = getPackageManager();
            List<ApplicationInfo> listInfo = appInfo.getInstalledApplications(0);
            Collections.sort(listInfo, new ApplicationInfo.DisplayNameComparator(appInfo));

            List<PackageItem> data = new ArrayList<PackageItem>();

            for (int index = 0; index < listInfo.size(); index++) {
                try {
                    ApplicationInfo content = listInfo.get(index);
                    if ((content.flags != ApplicationInfo.FLAG_SYSTEM) && content.enabled) {
                        if (content.icon != 0) {
                            PackageItem item = new PackageItem();
                            item.setName(getPackageManager().getApplicationLabel(content).toString());
                            item.setPackageName(content.packageName);
                            item.setIcon(getPackageManager().getDrawable(content.packageName, content.icon, content));
                            data.add(item);
                        }
                    }
                } catch (Exception e) {

                }
            }

            return data;
        }

        protected void onPostExecute(List<PackageItem> result) {
            data.clear();
            data.addAll(result);
            adapter.notifyDataSetChanged();
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            if (PreferencesManager.getInstance(SwipeListViewExampleActivity.this).getShowAbout()) {
                AboutDialog logOutDialog = new AboutDialog();
                logOutDialog.show(getSupportFragmentManager(), "dialog");
            }
        }
    }

}
