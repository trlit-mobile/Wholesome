package com.trl.wholesome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityCalculators extends Activity implements View.OnClickListener {

    private ImageView imgBloodAlcohol;
    private ImageView imgBodyFatUsNavy;
    private ImageView imgBodyFatYMCA;
    private ImageView imgBodyMassIndex;
    private ImageView imgCaloriesBurned;
    private ImageView imgLeanedBodyMass;
    private ImageView imgPragnencyDueDate;
    private ImageView imgOvulationEstimator;
    private ImageView imgWaistToHipRatio;
    private ImageView imgSmokingCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        imgBloodAlcohol = (ImageView) findViewById(R.id.imgBloodAlcohol);
        imgBodyFatUsNavy = (ImageView) findViewById(R.id.imgBodyFatUsNavy);
        imgBodyFatYMCA = (ImageView) findViewById(R.id.imgBodyFatYMCA);
        imgBodyMassIndex = (ImageView) findViewById(R.id.imgBodyMassIndex);
        imgCaloriesBurned = (ImageView) findViewById(R.id.imgCaloriesBurned);
        imgLeanedBodyMass = (ImageView) findViewById(R.id.imgLeanedBodyMass);
        imgPragnencyDueDate = (ImageView) findViewById(R.id.imgPragnencyDueDate);
        imgOvulationEstimator = (ImageView) findViewById(R.id.imgOvulationEstimator);
        imgWaistToHipRatio = (ImageView) findViewById(R.id.imgWaistToHipRatio);
        imgSmokingCost = (ImageView) findViewById(R.id.imgSmokingCost);

        imgBloodAlcohol.setOnClickListener(this);
        imgBodyFatUsNavy.setOnClickListener(this);
        imgBodyFatYMCA.setOnClickListener(this);
        imgBodyMassIndex.setOnClickListener(this);
        imgCaloriesBurned.setOnClickListener(this);
        imgLeanedBodyMass.setOnClickListener(this);
        imgPragnencyDueDate.setOnClickListener(this);
        imgOvulationEstimator.setOnClickListener(this);
        imgWaistToHipRatio.setOnClickListener(this);
        imgSmokingCost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBloodAlcohol:
                Intent intentBloodAlcohol = new Intent(ActivityCalculators.this, ActivityBloodAlcohol.class);
                startActivity(intentBloodAlcohol);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgOvulationEstimator:
                Intent intentOvulationEstimator = new Intent(ActivityCalculators.this, ActivityOvulationEstimator.class);
                startActivity(intentOvulationEstimator);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgBodyFatUsNavy:
                Intent intentFatUsNavy = new Intent(ActivityCalculators.this, ActivityBFUSNavy.class);
                startActivity(intentFatUsNavy);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgBodyFatYMCA:
                Intent intentFatYMCA = new Intent(ActivityCalculators.this, ActivityBFYMCA.class);
                startActivity(intentFatYMCA);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgBodyMassIndex:
                Intent intentBMI = new Intent(ActivityCalculators.this, ActivityBMI.class);
                startActivity(intentBMI);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgLeanedBodyMass:
                Intent intentLeanBodyMass = new Intent(ActivityCalculators.this, ActivityLeanBodyMass.class);
                startActivity(intentLeanBodyMass);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgWaistToHipRatio:
                Intent intentWaistToHip = new Intent(ActivityCalculators.this, ActivityWaistToHip.class);
                startActivity(intentWaistToHip);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgSmokingCost:
                Intent intentSmokingCost = new Intent(ActivityCalculators.this, ActivitySmokingCost.class);
                startActivity(intentSmokingCost);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgPragnencyDueDate:
                Intent intentPragnencyDueDate = new Intent(ActivityCalculators.this, ActivityPregnancyDueDate.class);
                startActivity(intentPragnencyDueDate);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.imgCaloriesBurned:
                Intent intentCaloriesBurned = new Intent(ActivityCalculators.this, ActivityCaloriesBurned.class);
                startActivity(intentCaloriesBurned);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }
}