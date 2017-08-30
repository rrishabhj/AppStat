/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.appusagestatistics;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Fragment that demonstrates how to use App Usage Statistics API.
 */
public class AppUsageStatisticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = AppUsageStatisticsFragment.class.getSimpleName();

    //VisibleForTesting for variables below
    UsageStatsManager mUsageStatsManager;
    UsageListAdapter mUsageListAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Button mOpenUsageSettingButton;
    Spinner mSpinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TimerTask timer;

    private Map<String, Boolean> whitelistMap = new HashMap<>();


    //not match with
    void initWhiteList(){
        whitelistMap.put("Settings",true);
        whitelistMap.put("AndroidSystem",true);
        whitelistMap.put("PackageInstaller",true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment {@link AppUsageStatisticsFragment}.
     */
    public static AppUsageStatisticsFragment newInstance() {
        AppUsageStatisticsFragment fragment = new AppUsageStatisticsFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public AppUsageStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsageStatsManager = (UsageStatsManager) getActivity()
                .getSystemService("usagestats"); //Context.USAGE_STATS_SERVICE
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_usage_statistics, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        initWhiteList();

        mUsageListAdapter = new UsageListAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_app_usage);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mLayoutManager = mRecyclerView.getLayoutManager();
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setAdapter(mUsageListAdapter);
        mOpenUsageSettingButton = (Button) rootView.findViewById(R.id.button_open_usage_setting);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinner_time_span);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.action_list, android.R.layout.simple_spinner_dropdown_item);

        swipeRefreshLayout.setOnRefreshListener(this);

        mSpinner.setAdapter(spinnerAdapter);
        final Timer timertask = new Timer();
        timer = new TimerTask() {
            @Override
            public void run() {
                loadData();
            }
        };

        timertask.schedule(timer , 300000);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            String[] strings = getResources().getStringArray(R.array.action_list);
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                StatsUsageInterval statsUsageInterval = StatsUsageInterval
//                        .getValue(strings[position]);
//                if (statsUsageInterval != null) {
//                    List<UsageStats> usageStatsList =
//                            getUsageStatistics(statsUsageInterval.mInterval);
//                    Collections.sort(usageStatsList, new LastTimeLaunchedComparatorDesc());
//                    updateAppsList(usageStatsList);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, -1 );


//                                        refreshList(cal.getTimeInMillis(), System.currentTimeMillis());
                                        loadData();
                                    }
                                }
        );

    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);

        StatsUsageInterval statsUsageInterval = StatsUsageInterval
                .getValue("Daily");
        if (statsUsageInterval != null) {

            List<UsageStats> usageStatsList =
                    getUsageStatistics(statsUsageInterval.mInterval);
            Collections.sort(usageStatsList, new LastTimeLaunchedComparatorDesc());
            updateAppsList(showAppsOPenedToday(usageStatsList));
        }
        swipeRefreshLayout.setRefreshing(false);
    }



    private List<UsageStats> showAppsOPenedToday(List<UsageStats> usageStatsList) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);

        Date beforedate = cal.getTime();


        List<UsageStats> usageStatses = new ArrayList<>();

        for (UsageStats usageStats: usageStatsList){

            Date afterDate = new Date(usageStats.getLastTimeUsed());

            if (afterDate.getYear()>=beforedate.getYear()){
                usageStatses.add(usageStats);
            }

        }

        return usageStatses;
    }

    /**
     * Returns the {@link #mRecyclerView} including the time span specified by the
     * intervalType argument.
     *
     * @param intervalType The time interval by which the stats are aggregated.
     *                     Corresponding to the value of {@link UsageStatsManager}.
     *                     E.g. {@link UsageStatsManager#INTERVAL_DAILY}, {@link
     *                     UsageStatsManager#INTERVAL_WEEKLY},
     *
     * @return A list of {@link android.app.usage.UsageStats}.
     */
    public List<UsageStats> getUsageStatistics(int intervalType) {
        // Get the app statistics since one year ago from the current time.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());

        if (queryUsageStats.size() == 0) {
            Log.i(TAG, "The user may not allow the access to apps usage. ");
            Toast.makeText(getActivity(),
                    getString(R.string.explanation_access_to_appusage_is_not_enabled),
                    Toast.LENGTH_LONG).show();
            mOpenUsageSettingButton.setVisibility(View.VISIBLE);
            mOpenUsageSettingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }
            });
        }
        return queryUsageStats;
    }

    /**
     * Updates the {@link #mRecyclerView} with the list of {@link UsageStats} passed as an argument.
     *
     * @param usageStatsList A list of {@link UsageStats} from which update the
     *                       {@link #mRecyclerView}.
     */
    //VisibleForTesting
    void updateAppsList(List<UsageStats> usageStatsList) {
        mUsageListAdapter.clearData();
        List<CustomUsageStats> customUsageStatsList = new ArrayList<>();
        for (int i = 0; i < usageStatsList.size(); i++) {
            CustomUsageStats customUsageStats = new CustomUsageStats();
            customUsageStats.usageStats = usageStatsList.get(i);
            try {
                Drawable appIcon = getActivity().getPackageManager()
                        .getApplicationIcon(customUsageStats.usageStats.getPackageName());

                ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo(customUsageStats.usageStats.getPackageName(),PackageManager.GET_META_DATA);
                customUsageStats.appName = (String) applicationInfo.loadLabel(getActivity().getPackageManager());

                customUsageStats.appIcon = appIcon;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w(TAG, String.format("App Icon is not found for %s",
                        customUsageStats.usageStats.getPackageName()));
                customUsageStats.appIcon = getActivity()
                        .getDrawable(R.drawable.ic_default_app_launcher);
            }
            customUsageStatsList.add(customUsageStats);
        }
        mUsageListAdapter.clearData();
        mUsageListAdapter.setCustomUsageStatsList(filteredList(customUsageStatsList));
        mUsageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    private List<CustomUsageStats> filteredList(List<CustomUsageStats> customUsageStatses2){

        List<CustomUsageStats> customUsageStatses = customUsageStatses2;

            for (int i=0;i<customUsageStatses.size();i++) {

                CustomUsageStats customUsageStats = customUsageStatses.get(i);

                String getFilteredKey = removeWhiteSpace(customUsageStats.appName);
                boolean removeItem = whitelistMap.containsKey(getFilteredKey);

                if (removeItem) {
                    customUsageStatses.remove(i);
                }
            }

        return customUsageStatses;
    }

    String removeWhiteSpace(String str){
        if (str!=null) {
            return str.replaceAll("\\s+", "");
        }
        return null;
    }



    @Override
    public void onRefresh() {
        loadData();
    }

    /**
     * The {@link Comparator} to sort a collection of {@link UsageStats} sorted by the timestamp
     * last time the app was used in the descendant order.
     */
    private static class LastTimeLaunchedComparatorDesc implements Comparator<UsageStats> {

        @Override
        public int compare(UsageStats left, UsageStats right) {
            return Long.compare(right.getLastTimeUsed(), left.getLastTimeUsed());
        }
    }

    /**
     * Enum represents the intervals for {@link android.app.usage.UsageStatsManager} so that
     * values for intervals can be found by a String representation.
     *
     */
    //VisibleForTesting
    static enum StatsUsageInterval {
        DAILY("Daily", UsageStatsManager.INTERVAL_DAILY),
        WEEKLY("Weekly", UsageStatsManager.INTERVAL_WEEKLY),
        MONTHLY("Monthly", UsageStatsManager.INTERVAL_MONTHLY),
        YEARLY("Yearly", UsageStatsManager.INTERVAL_YEARLY);

        private int mInterval;
        private String mStringRepresentation;

        StatsUsageInterval(String stringRepresentation, int interval) {
            mStringRepresentation = stringRepresentation;
            mInterval = interval;
        }

        static StatsUsageInterval getValue(String stringRepresentation) {
            for (StatsUsageInterval statsUsageInterval : values()) {
                if (statsUsageInterval.mStringRepresentation.equals(stringRepresentation)) {
                    return statsUsageInterval;
                }
            }
            return null;
        }
    }
}
