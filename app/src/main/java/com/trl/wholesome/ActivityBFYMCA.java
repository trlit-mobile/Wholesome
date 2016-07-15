package com.trl.wholesome;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.views.SweetAlertDialog;

public class ActivityBFYMCA extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgViewBack;
    private boolean metric_weight = false;
    private boolean metric_waist = false;
    private float WaistTotal = 0;
    private float WeightTotal = 0;
    private float bmcYMCA = 0;
    private String bfCategory = null;

    private RadioButton MaleButton;
    private RadioButton FemaleButton;
    private Spinner spinnerWaist;
    private Spinner spinnerWeight;

    private Button btnCalculate;
    private TextView Results_clear;
    private TextView category_clear;
    private TextView Results;
    private TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_body_fat_ymca);
        init();

        FemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
                Results_clear.setText("");
                TextView category_clear = (TextView) findViewById(R.id.txtCategotyResult);
                category_clear.setText("");
            }
        });

        MaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
                Results_clear.setText("");
                TextView category_clear = (TextView) findViewById(R.id.txtCategotyResult);
                category_clear.setText("");
            }
        });


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.lbs_kg_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(adapter1);

        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner1_insider = (Spinner) findViewById(R.id.spinnerWeight);
                if (spinner1_insider.getSelectedItem().equals("Kg")) {

                    metric_weight = true;
                }
                if (spinner1_insider.getSelectedItem().equals("Lbs")) {

                    metric_weight = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //Waist Spinner & Array in Inch and cm
        spinnerWaist = (Spinner) findViewById(R.id.spinnerWaist);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaist.setAdapter(adapter3);

        spinnerWaist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner3_insider = (Spinner) findViewById(R.id.spinnerWaist);
                if (spinner3_insider.getSelectedItem().equals("Inch")) {

                    metric_waist = false;
                }
                if (spinner3_insider.getSelectedItem().equals("Cm")) {

                    metric_waist = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void init() {

        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        MaleButton = (RadioButton) findViewById(R.id.radioMale);
        FemaleButton = (RadioButton) findViewById(R.id.radioFemale);
        spinnerWeight = (Spinner) findViewById(R.id.spinnerWeight);
        spinnerWaist = (Spinner) findViewById(R.id.spinnerWaist);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        Results = (TextView) findViewById(R.id.txtTotalBodyFat);
        category = (TextView) findViewById(R.id.txtCategoty);
        Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
        category_clear = (TextView) findViewById(R.id.txtCategotyResult);

        imgViewBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgViewBack:
                onBackPressed();
                break;

            case R.id.btnCalculate:
                String catColor = "";
                Results_clear.setText("");
                category_clear.setText("");
                String extraText  = "";
                EditText WeightString = (EditText) findViewById(R.id.edtWeight);
                if (WeightString.getText().length() == 0) {
                    Toast.makeText(ActivityBFYMCA.this, "Enter Weight", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    WeightTotal = Float.parseFloat(WeightString.getText().toString());
                }
                if (metric_weight) {
                    WeightTotal = (float) (WeightTotal * 2.20462262);
                }

                EditText WaistString = (EditText) findViewById(R.id.edtWaist);

                if (WaistString.getText().length() == 0) {
                    Toast.makeText(ActivityBFYMCA.this, "Enter Waist", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    WaistTotal = Float.parseFloat(WaistString.getText().toString());
                }
                if (metric_waist) {
                    WaistTotal = (float) (WaistTotal * 0.393700787);
                }

                if (MaleButton.isChecked()) {
                    if ((WeightTotal <= 0) || (WaistTotal <= 0)) {
                        Toast.makeText(ActivityBFYMCA.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        extraText = "<p><small>For Male :<br/>" +
                                "If Body Fat less than 2    - Dangerous<br/>" +
                                "If Body Fat range 2 to 5.99 - Essential Fat<br/>" +
                                "If Body Fat range 6 to 13.99 - Athletes<br/>" +
                                "If Body Fat range 14 to 17.99 - Fitness<br/>" +
                                "If Body Fat range 18 to 25 - Acceptable<br/>" +
                                "If Body Fat greater than 25   - Obese</small></p>";
                        bmcYMCA = (float) (((((4.15 * (WaistTotal)) - (0.082 * (WeightTotal))) - 98.42) / WeightTotal) * 100);
                        if (bmcYMCA < 2) {
                            bfCategory = "Dangerous";
                        }
                        else if ((bmcYMCA >= 2) && (bmcYMCA < 6)) {
                            bfCategory = "Essential Fat";
                        }
                        else if ((bmcYMCA >= 6) && (bmcYMCA < 14)) {
                            bfCategory = "Athletes";
                        }
                        else if ((bmcYMCA >= 14) && (bmcYMCA < 18)) {
                            bfCategory = "Fitness";
                        }
                        else if ((bmcYMCA >= 18) && (bmcYMCA <= 25)) {
                            bfCategory = "Acceptable";
                        }
                        else if (bmcYMCA > 25) {
                            bfCategory = "Obese";
                        }
                    }
                } else {
                    if ((WaistTotal <= 0) || (WeightTotal <= 0)) {
                        Toast.makeText(ActivityBFYMCA.this, "Enter NonZero  Values", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        extraText = "<p><small>For Female :<br/>" +
                                "If Body Fat less than 10      - Dangerous<br/>" +
                                "If Body Fat range 10 to 13.99 - Essential Fat<br/>" +
                                "If Body Fat range 14 to 20.99 - Athletes<br/>" +
                                "If Body Fat range 21 to 24.99 - Fitness<br/>" +
                                "If Body Fat range 25 to 32 - Acceptable<br/>" +
                                "If Body Fat greater than 32   - Obese</small></p>";
                        bmcYMCA = (float) (((((4.15 * (WaistTotal)) - (0.082 * (WeightTotal))) - 76.76) / WeightTotal) * 100);
                        if (bmcYMCA < 10) {
                            bfCategory = "Dangerous";
                        }
                        else if ((bmcYMCA >= 10) && (bmcYMCA < 14)) {
                            bfCategory = "Essential Fat";
                        }
                        else if ((bmcYMCA >= 14) && (bmcYMCA < 21)) {
                            bfCategory = "Athletes";
                        }
                        else if ((bmcYMCA >= 21) && (bmcYMCA < 25)) {
                            bfCategory = "Fitness";
                        }
                        else if ((bmcYMCA >= 25) && (bmcYMCA <= 32)) {
                            bfCategory = "Acceptable";
                        }
                        else if (bmcYMCA > 32) {
                            bfCategory = "Obese";
                        }
                    }
                }
                String BMCValue = Float.toString(bmcYMCA);

                String BMCFinal = "0.0";
                if (BMCValue.length() >= 5) {
                    BMCFinal = BMCValue.substring(0, 5);
                } else {
                    BMCFinal = BMCValue;
                }

                //Results.setTextColor(Color.CYAN);
                Results.setText("Your total Body Fat is " + BMCFinal + "%");

                if (FemaleButton.isChecked()) {
                    if (bmcYMCA < 10) {
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                    else if ((bmcYMCA >= 10) && (bmcYMCA < 14)) {
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if ((bmcYMCA >= 14) && (bmcYMCA < 21)) {
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if ((bmcYMCA >= 21) && (bmcYMCA < 25)) {
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if ((bmcYMCA >= 25) && (bmcYMCA < 32)) {
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if (bmcYMCA >= 32) {
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                } else {
                    if (bmcYMCA < 2) {
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                    else if ((bmcYMCA >= 2) && (bmcYMCA < 6)) {
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if ((bmcYMCA >= 6) && (bmcYMCA < 14)) {
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if ((bmcYMCA >= 14) && (bmcYMCA < 18)) {
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if ((bmcYMCA >= 18) && (bmcYMCA < 25)) {
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if (bmcYMCA >= 25) {
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }

                }
                category.setText("You are considered in category " + bfCategory);

                Spanned text  = Html.fromHtml("<p>Your total body fat is <b><h3>"+BMCFinal+"%<h3></b></p>" +
                        "<p>You are considered in category <b><h1><font color=\""+catColor+"\">"+bfCategory+"</font></h1></b></p>" +
                        "" + extraText);

                new SweetAlertDialog(ActivityBFYMCA.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG)
                        .setTitleText("Wholesome!")
                        .setContentText(text)
                        .setCustomImage(R.drawable.imgpsh_fullsize)
                        .show();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        ActivityBFYMCA.this.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityBFYMCA.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityBFYMCA.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}