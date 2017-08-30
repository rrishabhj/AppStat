package com.example.android.appusagestatistics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {

    private PatternLockView mPatternLockView;
    String password = "03678";
    TextView profileName;
    private boolean isChange=false;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String userPattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
            Log.d(getClass().getName(), "Pattern complete: " +password);

            if (!PrefUtil.getLogin(PatternLockActivity.this)|| isChange){
                PrefUtil.setPassword(PatternLockActivity.this,userPattern);
                PrefUtil.setLogin(PatternLockActivity.this,true);
                Toast.makeText(PatternLockActivity.this,"Logging in!",Toast.LENGTH_SHORT).show();
            }

            if (userPattern.equalsIgnoreCase(PrefUtil.getPassword(PatternLockActivity.this))){
                mPatternLockView.setVisibility(View.GONE);

                startActivity(new Intent(PatternLockActivity.this, AppUsageStatisticsActivity.class));
                finish();
            }else {
                Toast.makeText(PatternLockActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                mPatternLockView.clearPattern();
            }
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        profileName = (TextView) findViewById(R.id.profile_name);
        isChange = getIntent().getBooleanExtra("CHANGE",false);

        if (!PrefUtil.getLogin(PatternLockActivity.this)|| isChange){

            profileName.setText("Enter New Password");


        }else {
            profileName.setText("Enter Password");

        }
        mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

    }
}
