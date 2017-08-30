/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.appusagestatistics;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provide views to RecyclerView with the directory entries.
 */
public class UsageListAdapter extends RecyclerView.Adapter<UsageListAdapter.ViewHolder> {

    private final long currentMinutes;
    private final int currentHours;
    private Date currentTime;
    private List<CustomUsageStats> mCustomUsageStatsList = new ArrayList<>();
    private DateFormat mDateFormat = new SimpleDateFormat();
    private long storeLastItemUsed;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPackageName;
        private final TextView mLastTimeUsed;
        private final TextView mAppName;
        private final ImageView mAppIcon;
        private final LinearLayout llTime;
        private final TextView tvTime;


        public ViewHolder(View v) {
            super(v);
            mPackageName = (TextView) v.findViewById(R.id.textview_package_name);
            mLastTimeUsed = (TextView) v.findViewById(R.id.textview_last_time_used);
            mAppName = (TextView) v.findViewById(R.id.textview_name);
            mAppIcon = (ImageView) v.findViewById(R.id.app_icon);
            llTime = (LinearLayout) v.findViewById(R.id.ll_time);
            tvTime = (TextView) v.findViewById(R.id.tv_time);
        }

        public TextView getLastTimeUsed() {
            return mLastTimeUsed;
        }

        public TextView getPackageName() {
            return mPackageName;
        }


        public TextView getAppName() {
            return mAppName;
        }

        public ImageView getAppIcon() {
            return mAppIcon;
        }

        public TextView getTvTime() {return tvTime;}

        public LinearLayout getLlTime() {return llTime;}
    }

    public UsageListAdapter() {


        currentMinutes = new Date(System.currentTimeMillis()).getMinutes();
        currentHours = new Date(System.currentTimeMillis()).getHours();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.usage_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getPackageName().setText(mCustomUsageStatsList.get(position).usageStats.getPackageName());
        long lastTimeUsed = mCustomUsageStatsList.get(position).usageStats.getLastTimeUsed();
        long forgroundTimeUsed = mCustomUsageStatsList.get(position).usageStats.getTotalTimeInForeground();
//        long firstTimeUsed = mCustomUsageStatsList.get(position).usageStats.getFirstTimeStamp();  "Start:" + mDateFormat.format(new Date(firstTimeUsed))+

        String myDate = "\nLast:" + mDateFormat.format(new Date(lastTimeUsed)) + " \nUsage Time:" + forgroundTimeUsed/(1000) +" sec";

//        currentTime = new Date(System.currentTimeMillis());
//        Date date = new Date(lastTimeUsed);
//        int minutes = new Date(lastTimeUsed).getMinutes();
//        int hour = new Date(lastTimeUsed).getHours();
//
//
//        if ( DateUtils.minutesDiff(date, currentTime) <= 1){
//            viewHolder.getTvTime().setText("1 Minutes");
//        }else if (DateUtils.minutesDiff(currentTime, date) <=5){
//            viewHolder.getTvTime().setText("5 Minutes");
//        }else if (DateUtils.minutesDiff(currentTime, date) <=10){
//            viewHolder.getTvTime().setText("10 Minutes");
//        }else {
//            viewHolder.getTvTime().setText("20 Minutes");
//        }


//        if (new Date(currentTime).

        viewHolder.getLastTimeUsed().setText(myDate);
        viewHolder.getAppIcon().setImageDrawable(mCustomUsageStatsList.get(position).appIcon);
        viewHolder.getAppName().setText(mCustomUsageStatsList.get(position).appName);

        storeLastItemUsed = lastTimeUsed;
    }


    @Override
    public int getItemCount() {
        return mCustomUsageStatsList.size();
    }

    public void setCustomUsageStatsList(List<CustomUsageStats> customUsageStats) {
        mCustomUsageStatsList = customUsageStats;
    }

    public void clearData(){
        mCustomUsageStatsList.clear();
    }
}