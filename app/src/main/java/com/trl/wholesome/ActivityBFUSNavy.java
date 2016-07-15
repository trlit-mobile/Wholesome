package com.trl.wholesome;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.views.SweetAlertDialog;

public class ActivityBFUSNavy extends AppCompatActivity {

    private boolean metric_weight = false;
    private boolean metric_height = false;
    private boolean metric_waist = false;
    private boolean metric_neck = false;
    private boolean metric_hip = false;
    //float WeightTotal=0;
    private float HeightTotal = 0;
    private float WaistTotal = 0;
    private float NeckTotal = 0;
    private float HipTotal = 0;
    private float bmcUS = 0;

    private ImageView imgViewBack;

    private OnClickListener mClickListener = new OnClickListener() {
        public void onClick(View v) {

            TextView Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
            Results_clear.setText("");
            TextView catagory_clear = (TextView) findViewById(R.id.txtCategotyResult);
            catagory_clear.setText("");


            //Height Calculation
            EditText HeightString = (EditText) findViewById(R.id.edtHeight);

            if (HeightString.getText().length() == 0) {
                Toast.makeText(ActivityBFUSNavy.this, "Enter your height measurement", Toast.LENGTH_SHORT).show();
                return;
            } else {
                HeightTotal = Float.parseFloat(HeightString.getText().toString());
            }
            if (metric_height) {
                HeightTotal = (float) (HeightTotal * 2.54);
            }

            //Waist Calculation
            EditText WaistString = (EditText) findViewById(R.id.edtWaist);

            if (WaistString.getText().length() == 0) {
                Toast.makeText(ActivityBFUSNavy.this, "Enter your waist measurement", Toast.LENGTH_SHORT).show();
                return;
            } else {
                WaistTotal = Float.parseFloat(WaistString.getText().toString());
            }
            if (metric_waist) {
                WaistTotal = (float) (WaistTotal * 2.54);
            }

            //Neck Calculation
            EditText NeckString = (EditText) findViewById(R.id.edtNeck);

            if (NeckString.getText().length() == 0) {
                Toast.makeText(ActivityBFUSNavy.this, "Enter your neck measurement", Toast.LENGTH_SHORT).show();
                return;
            } else {
                NeckTotal = Float.parseFloat(NeckString.getText().toString());
            }
            if (metric_neck) {
                NeckTotal = (float) (NeckTotal * 2.54);
            }

            //Hip Calculation
            EditText HipString = (EditText) findViewById(R.id.edtHip);
            RadioButton FemaleButton = (RadioButton) findViewById(R.id.radioFemale);
            if (FemaleButton.isChecked()) {
                if (HipString.getText().length() == 0) {
                    Toast.makeText(ActivityBFUSNavy.this, "Enter your hip measurement", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    HipTotal = Float.parseFloat(HipString.getText().toString());
                }
                if (metric_hip) {
                    HipTotal = (float) (HipTotal * 2.54);
                }
                //HipTotal = Math.round(HipTotal);
            }


            RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
            String bfCatagory = null;
            String extraText  = "";
            String catColor = "";
            if (MaleButton.isChecked()) {
                if ((HeightTotal <= 0) || (NeckTotal <= 0) || (WaistTotal <= 0)) {
                    Toast.makeText(ActivityBFUSNavy.this, "Enter non zero values for all inputs", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    extraText = "<p><small>For Male :<br/>" +
                            "If Body Fat less than 2 - Dangerous<br/>" +
                            "If Body Fat range 2 to 5.99 - Essential Fat<br/>" +
                            "If Body Fat range 6 to 13.99 - Athletes<br/>" +
                            "If Body Fat range 14 to 17.99 - Fitness<br/>" +
                            "If Body Fat range 18 to 25 - Acceptable<br/>" +
                            "If Body Fat greater than 25   - Obese</small></p>";
                    bmcUS = (float) (495 / ((1.0324 - (0.19077 * (Math.log10(WaistTotal - NeckTotal)))) + (0.15456 * (Math.log10(HeightTotal)))) - 450);
                    if (bmcUS < 2) {
                        bfCatagory = "Dangerous";
                    }
                    else if ((bmcUS >= 2) && (bmcUS < 6)) {
                        bfCatagory = "Essential Fat";
                    }
                    else if ((bmcUS >= 6) && (bmcUS < 14)) {
                        bfCatagory = "Athletes";
                    }
                    else if ((bmcUS >= 14) && (bmcUS < 18)) {
                        bfCatagory = "Fitness";
                    }
                    else if ((bmcUS >= 18) && (bmcUS <= 25)) {
                        bfCatagory = "Acceptable";
                    }
                    else if (bmcUS > 25) {
                        bfCatagory = "Obese";
                    }
                }

            } else {
                if ((HeightTotal <= 0) || (NeckTotal <= 0) || (WaistTotal <= 0) || (HipTotal <= 0)) {
                    Toast.makeText(ActivityBFUSNavy.this, "Enter non zero values for all inputs", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    extraText = "<p><small>For Female :<br/>" +
                            "If Body Fat less than 10      - Dangerous<br/>" +
                            "If Body Fat range 10 to 13.99 - Essential Fat<br/>" +
                            "If Body Fat range 14 to 20.99 - Athletes<br/>" +
                            "If Body Fat range 21 to 24.99 - Fitness<br/>" +
                            "If Body Fat range 25 to 32 - Acceptable<br/>" +
                            "If Body Fat greater than 32   - Obese</small></p>";
                    bmcUS = (float) (495 / ((1.29579 - (0.35004 * (Math.log10(WaistTotal + HipTotal - NeckTotal)))) + (0.22100 * (Math.log10(HeightTotal)))) - 450);
                    if (bmcUS < 10) {
                        bfCatagory = "Dangerous";
                    }
                    else if ((bmcUS >= 10) && (bmcUS < 14)) {
                        bfCatagory = "Essential Fat";
                    }
                    else if ((bmcUS >= 14) && (bmcUS < 21)) {
                        bfCatagory = "Athletes";
                    }
                    else if ((bmcUS >= 21) && (bmcUS < 25)) {
                        bfCatagory = "Fitness";
                    }
                    else if ((bmcUS >= 25) && (bmcUS <= 32)) {
                        bfCatagory = "Acceptable";
                    }
                    else if (bmcUS > 32) {
                        bfCatagory = "Obese";
                    }
                }
            }


            String BMCValue = Float.toString(bmcUS);
            TextView Results = (TextView) findViewById(R.id.txtTotalBodyFat);
            TextView catagory = (TextView) findViewById(R.id.txtCategoty);
            String BMCFinal = "0.0";
            if (BMCValue.length() >= 5) {
                BMCFinal = BMCValue.substring(0, 5);
            } else {
                BMCFinal = BMCValue;
            }

            //Results.setTextColor(Color.CYAN);
            Results.setText("Your total Body Fat is " + BMCFinal + "%");

            if (FemaleButton.isChecked()) {
                if (bmcUS < 10) {
                    catColor  = "#ff0000";
                    catagory.setTextColor(Color.RED);
                }
                else if ((bmcUS >= 10) && (bmcUS < 14)) {
                    catColor  = "#ffae19";
                    catagory.setTextColor(Color.YELLOW);
                }
                else if ((bmcUS >= 14) && (bmcUS < 21)) {
                    catColor  = "#00ff00";
                    catagory.setTextColor(Color.GREEN);
                }
                else if ((bmcUS >= 21) && (bmcUS < 25)) {
                    catColor  = "#00ff00";
                    catagory.setTextColor(Color.GREEN);
                }
                else if ((bmcUS >= 25) && (bmcUS < 32)) {
                    catColor  = "#ffae19";
                    catagory.setTextColor(Color.YELLOW);
                }
                else if (bmcUS >= 32) {
                    catColor  = "#ff0000";
                    catagory.setTextColor(Color.RED);
                }
            } else {
                if (bmcUS < 2) {
                    catColor  = "#ff0000";
                    catagory.setTextColor(Color.RED);
                }
                else if ((bmcUS >= 2) && (bmcUS < 6)) {
                    catColor  = "#ffae19";
                    catagory.setTextColor(Color.YELLOW);
                }
                else if ((bmcUS >= 6) && (bmcUS < 14)) {
                    catColor  = "#00ff00";
                    catagory.setTextColor(Color.GREEN);
                }
                else if ((bmcUS >= 14) && (bmcUS < 18)) {
                    catColor  = "#00ff00";
                    catagory.setTextColor(Color.GREEN);
                }
                else if ((bmcUS >= 18) && (bmcUS < 25)) {
                    catColor  = "#ffae19";
                    catagory.setTextColor(Color.YELLOW);
                }
                else if (bmcUS >= 25) {
                    catColor  = "#ff0000";
                    catagory.setTextColor(Color.RED);
                }
            }

            catagory.setText("You are cosidered in category " + bfCatagory);

            Spanned text  = Html.fromHtml("<p>Your total body fat is <b><h3>"+BMCFinal+"%<h3></b></p>" +
                    "<p>You are considered in category <b><h1><font color=\""+catColor+"\">"+bfCatagory+"</font></h1></b></p>" +
                    "" + extraText);

            new SweetAlertDialog(ActivityBFUSNavy.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG)
                    .setTitleText("Wholesome!")
                    .setContentText(text)
                    .setCustomImage(R.drawable.imgpsh_fullsize)
                    .show();

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_usnavy);

        //Radio Button Functionalities
        RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
        RadioButton FemaleButton = (RadioButton) findViewById(R.id.radioFemale);
        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        imgViewBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (MaleButton.isChecked()) {
            TextView textHip = (TextView) findViewById(R.id.txtHip);
            EditText editHip = (EditText) findViewById(R.id.edtHip);
            Spinner spinnerHip = (Spinner) findViewById(R.id.spinnerHip);
            ImageView imgDropDown = (ImageView) findViewById(R.id.imgDropdown);
            imgDropDown.setVisibility(View.GONE);
            textHip.setVisibility(View.GONE);
            editHip.setVisibility(View.GONE);
            spinnerHip.setVisibility(View.GONE);


        } else {
            TextView textHip = (TextView) findViewById(R.id.txtHip);
            EditText editHip = (EditText) findViewById(R.id.edtHip);
            Spinner spinnerHip = (Spinner) findViewById(R.id.spinnerHip);
            ImageView imgDropDown = (ImageView) findViewById(R.id.imgDropdown);
            imgDropDown.setVisibility(View.GONE);
            textHip.setVisibility(View.VISIBLE);
            editHip.setVisibility(View.VISIBLE);
            spinnerHip.setVisibility(View.VISIBLE);
        }

        //Height Spinner & Array in Inch and cm
        Spinner spinnerHeight = (Spinner) findViewById(R.id.spinnerHeight);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(adapter2);

        spinnerHeight.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner2_insider = (Spinner) findViewById(R.id.spinnerHeight);
                if (spinner2_insider.getSelectedItem().equals("Cm")) {

                    metric_height = false;
                }
                if (spinner2_insider.getSelectedItem().equals("Inch")) {

                    metric_height = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Waist Spinner & Array in Inch and cm
        Spinner spinnerWaist = (Spinner) findViewById(R.id.spinnerWaist);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaist.setAdapter(adapter3);

        spinnerWaist.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner3_insider = (Spinner) findViewById(R.id.spinnerWaist);
                if (spinner3_insider.getSelectedItem().equals("Cm")) {

                    metric_waist = false;
                }
                if (spinner3_insider.getSelectedItem().equals("Inch")) {

                    metric_waist = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Neck Spinner & Array in Inch and cm
        Spinner spinnerNeck = (Spinner) findViewById(R.id.spinnerNeck);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNeck.setAdapter(adapter4);

        spinnerNeck.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner4_insider = (Spinner) findViewById(R.id.spinnerNeck);
                if (spinner4_insider.getSelectedItem().equals("Cm")) {

                    metric_neck = false;
                }
                if (spinner4_insider.getSelectedItem().equals("Inch")) {

                    metric_neck = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Hip Spinner & Array in Inch and cm
        Spinner spinnerHip = (Spinner) findViewById(R.id.spinnerHip);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHip.setAdapter(adapter5);

        spinnerHip.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner5_insider = (Spinner) findViewById(R.id.spinnerHip);
                if (spinner5_insider.getSelectedItem().equals("Cm")) {

                    metric_hip = false;
                }
                if (spinner5_insider.getSelectedItem().equals("Inch")) {

                    metric_hip = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        FemaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
                Results_clear.setText("");
                TextView catagory_clear = (TextView) findViewById(R.id.txtCategotyResult);
                catagory_clear.setText("");
                TextView textHip = (TextView) findViewById(R.id.txtHip);
                EditText editHip = (EditText) findViewById(R.id.edtHip);
                EditText editNeck = (EditText) findViewById(R.id.edtNeck);
                Spinner spinnerHip = (Spinner) findViewById(R.id.spinnerHip);
                textHip.setVisibility(View.VISIBLE);
                editNeck.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editHip.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editHip.setVisibility(View.VISIBLE);
                spinnerHip.setVisibility(View.VISIBLE);

            }
        });

        MaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
                Results_clear.setText("");
                TextView catagory_clear = (TextView) findViewById(R.id.txtCategotyResult);
                catagory_clear.setText("");
                TextView textHip = (TextView) findViewById(R.id.txtHip);
                EditText editHip = (EditText) findViewById(R.id.edtHip);
                EditText editNeck = (EditText) findViewById(R.id.edtNeck);
                Spinner spinnerHip = (Spinner) findViewById(R.id.spinnerHip);
                textHip.setVisibility(View.GONE);
                editHip.setVisibility(View.GONE);
                editNeck.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editHip.setImeOptions(EditorInfo.IME_ACTION_DONE);
                spinnerHip.setVisibility(View.GONE);

            }
        });

        //Calculate Button Functionalities
        Button button = (Button) findViewById(R.id.btnCalculate);
        button.setOnClickListener(mClickListener);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        ActivityBFUSNavy.this.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityBFUSNavy.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityBFUSNavy.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}
