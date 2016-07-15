package com.trl.wholesome;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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

public class ActivityWaistToHip extends Activity {
    /**
     * Called when the activity is first created.
     */

    boolean metric_waist = false, metric_hip = false;
    float WaistTotal = 0;
    float HipTotal = 0;
    float Ratio = 0;

    private ImageView imgViewBack;
    private OnClickListener mClickListener = new OnClickListener() {
        public void onClick(View v) {

            TextView Results = (TextView) findViewById(R.id.txtHipRatioResult);
            Results.setText("");
            TextView health_risk = (TextView) findViewById(R.id.txtHealthRiskResult);
            health_risk.setText("");
            TextView body_shape = (TextView) findViewById(R.id.txtBodyShapeResult);
            body_shape.setText("");

            //Waist Calculation
            EditText WaistString = (EditText) findViewById(R.id.edtWeight);
            if (WaistString.getText().length() == 0) {
                Toast.makeText(ActivityWaistToHip.this, "Enter Waist", Toast.LENGTH_SHORT).show();
                return;
            } else {
                WaistTotal = Float.parseFloat(WaistString.getText().toString());
            }
            if (metric_waist) {
                WaistTotal = (float) (WaistTotal * 2.54);
            }
            //WaistTotal = Math.round(WaistTotal);

            //Hip Calculation
            EditText HipString = (EditText) findViewById(R.id.edtHip);
            if (HipString.getText().length() == 0) {
                Toast.makeText(ActivityWaistToHip.this, "Enter Hip", Toast.LENGTH_SHORT).show();
                return;
            } else {
                HipTotal = Float.parseFloat(HipString.getText().toString());
            }
            if (metric_hip) {
                HipTotal = (float) (HipTotal * 2.54);
            }
            //HipTotal = Math.round(HipTotal);

            //Calculation of Waist to Hip ratio
            if ((HipTotal <= 0) || (WaistTotal <= 0)) {
                Toast.makeText(ActivityWaistToHip.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Ratio = (float) (WaistTotal / HipTotal);
            }

            //Radio Button Functionalities
            RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
            RadioButton FemaleButton = (RadioButton) findViewById(R.id.radioFemale);

            String Ratio_String = Float.toString(Ratio);
            String RatioFinal = "0.0";
            if (Ratio_String.length() >= 4) {
                RatioFinal = Ratio_String.substring(0, 4);
            } else {
                RatioFinal = Ratio_String;
            }

            String textHealthRisk = "";
            String textBodyShape = "";
            String textColor = "";

            if (MaleButton.isChecked()) {
                if (Ratio <= 0.95) {
                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setTextColor(Color.parseColor("#208700"));
                    Results.setTextColor(Color.parseColor("#208700"));
                    body_shape.setTextColor(Color.parseColor("#208700"));
                    health_risk.setText("Estimated Health Risk : Low");
                    body_shape.setText("Estimated Body Shape : Pear");

                    textHealthRisk = "Low";
                    textBodyShape = "Pear";
                    textColor = "#208700";
                }
                if ((Ratio > 0.95) && (Ratio <= 1.0)) {
                    health_risk.setTextColor(Color.parseColor("#C68000"));
                    body_shape.setTextColor(Color.parseColor("#C68000"));
                    Results.setTextColor(Color.parseColor("#C68000"));
                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setText("Estimated Health Risk : Moderate");
                    body_shape.setText("Estimated Body Shape : Avocado");

                    textHealthRisk = "Moderate";
                    textBodyShape = "Avocado";
                    textColor = "#C68000";
                }
                if (Ratio > 1.0) {
                    health_risk.setTextColor(Color.RED);
                    body_shape.setTextColor(Color.RED);
                    Results.setTextColor(Color.RED);
                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setText("Estimated Health Risk : High");
                    body_shape.setText("Estimated Body Shape : Apple");

                    textHealthRisk = "High";
                    textBodyShape = "Apple";
                    textColor = "#FF0000";
                }
            }
            else if (FemaleButton.isChecked()) {
                if (Ratio <= 0.80) {
                    health_risk.setTextColor(Color.parseColor("#208700"));
                    body_shape.setTextColor(Color.parseColor("#208700"));
                    Results.setTextColor(Color.parseColor("#208700"));
                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setText("Estimated Health Risk : Low");
                    body_shape.setText("Estimated Body Shape : Pear");

                    textHealthRisk = "Low";
                    textBodyShape = "Pear";
                    textColor = "#208700";
                }
                if ((Ratio > 0.80) && (Ratio <= 0.85)) {
                    //Yellow
                    health_risk.setTextColor(Color.parseColor("#C68000"));
                    body_shape.setTextColor(Color.parseColor("#C68000"));
                    Results.setTextColor(Color.parseColor("#C68000"));
                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setText("Estimated Health Risk : Moderate");
                    body_shape.setText("Estimated Body Shape : Avocado");

                    textHealthRisk = "Moderate";
                    textBodyShape = "Avocado";
                    textColor = "#C68000";
                }
                if (Ratio > 0.85) {
                    health_risk.setTextColor(Color.RED);
                    body_shape.setTextColor(Color.RED);
                    Results.setTextColor(Color.RED);

                    Results.setText("Waist to Hip Ratio : " + RatioFinal);
                    health_risk.setText("Estimated Health Risk : High");
                    body_shape.setText("Estimated Body Shape : Apple");

                    textHealthRisk = "High";
                    textBodyShape = "Apple";
                    textColor = "#FF0000";
                }
            }

            Spanned text  = Html.fromHtml("<p>Your Waist to Hip ratio is <b><h1><font color=\""+textColor+"\">"+RatioFinal+"</font></h1></b></p>" +
                    "<p>Your estimated Health Risk is <b><h1><font color=\""+textColor+"\">"+textHealthRisk+"</font></h1></b></p>" +
                    "<p>Your estimated body shape is <b><h1><font color=\""+textColor+"\">"+textBodyShape+"</font></h1></b></p>");

            SweetAlertDialog sDialog = new SweetAlertDialog(ActivityWaistToHip.this,
                    SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG);
            sDialog.setTitleText("Wholesome!");
            sDialog.setContentText(text);
            sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
            sDialog.show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_waist_to_hip);

        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);


        //Waist Spinner & Array in Inch and cm
        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerWeight);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner1_insider = (Spinner) findViewById(R.id.spinnerWeight);
                if (spinner1_insider.getSelectedItem().equals("Cm")) {

                    metric_waist = false;
                }
                if (spinner1_insider.getSelectedItem().equals("Inch")) {

                    metric_waist = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        imgViewBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //Waist Spinner & Array in Inch and cm
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerHip);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner2_insider = (Spinner) findViewById(R.id.spinnerHip);
                if (spinner2_insider.getSelectedItem().equals("Cm")) {

                    metric_hip = false;
                }
                if (spinner2_insider.getSelectedItem().equals("Inch")) {

                    metric_hip = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //Radio Button Functionalities
        RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
        RadioButton FemaleButton = (RadioButton) findViewById(R.id.radioFemale);

        FemaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results = (TextView) findViewById(R.id.txtHipRatioResult);
                Results.setText("");
                TextView health_risk = (TextView) findViewById(R.id.txtHealthRiskResult);
                health_risk.setText("");
                TextView body_shape = (TextView) findViewById(R.id.txtBodyShapeResult);
                body_shape.setText("");
            }
        });

        MaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Results = (TextView) findViewById(R.id.txtHipRatioResult);
                Results.setText("");
                TextView health_risk = (TextView) findViewById(R.id.txtHealthRiskResult);
                health_risk.setText("");
                TextView body_shape = (TextView) findViewById(R.id.txtBodyShapeResult);
                body_shape.setText("");
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
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityWaistToHip.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityWaistToHip.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}