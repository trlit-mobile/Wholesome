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

/**
 * Created by mitesh on 15/5/16.
 */
public class ActivityBMI extends AppCompatActivity implements View.OnClickListener {

    ImageView imgViewBack;
    boolean metric_weight = false, metric_height = false;
    float HeightTotal = 0;
    float WeightTotal = 0;
    float bmi = 0;
    RadioButton MaleButton, FemaleButton;
    TextView Results_clear, category_clear,Results,category;
    Spinner spinnerWeight,spinnerHeight;
    String bmCategory=null;
    Button btnCalculate;
    EditText HeightString,WeightString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        init();


        FemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Results_clear.setText("");
                category_clear.setText("");
            }
        });

        MaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Results_clear.setText("");
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
                Spinner spinner1_insider = (Spinner)findViewById(R.id.spinnerWeight);
                if(spinner1_insider.getSelectedItem().equals("Kg")){

                    metric_weight=true;
                }
                if(spinner1_insider.getSelectedItem().equals("Lbs")){

                    metric_weight=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(adapter3);

        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner3_insider = (Spinner)findViewById(R.id.spinnerHeight);
                if(spinner3_insider.getSelectedItem().equals("Inch")){

                    metric_height=false;
                }
                if(spinner3_insider.getSelectedItem().equals("Cm")){

                    metric_height=true;
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
        Results_clear = (TextView) findViewById(R.id.txtBodyFatResult);
        category_clear = (TextView) findViewById(R.id.txtCategotyResult);
        spinnerWeight=(Spinner)findViewById(R.id.spinnerWeight);
        spinnerHeight= (Spinner) findViewById(R.id.spinnerHeight);
        Results = (TextView) findViewById(R.id.txtTotalBodyFat);
        category= (TextView)findViewById(R.id.txtCategoty);
        btnCalculate= (Button) findViewById(R.id.btnCalculate);
        HeightString= (EditText)findViewById(R.id.edtHeight);
        WeightString= (EditText)findViewById(R.id.edtWeight);

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

                if (WeightString.getText().length() == 0){
                    Toast.makeText(ActivityBMI.this, "Enter Weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    WeightTotal = Float.parseFloat(WeightString.getText().toString());
                }
                if(metric_weight){
                    WeightTotal = (float) (WeightTotal*2.20462262);
                }
                //WeightTotal = Math.round(WeightTotal);

                //Waist Calculation


                if (HeightString.getText().length() == 0){
                    Toast.makeText(ActivityBMI.this, "Enter Height", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    HeightTotal = Float.parseFloat(HeightString.getText().toString());
                }
                if(metric_height){
                    HeightTotal = (float)(HeightTotal*0.393700787);
                }
                if(MaleButton.isChecked()){
                    if((WeightTotal<=0)||(HeightTotal<=0)){
                        Toast.makeText(ActivityBMI.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        bmi= (((WeightTotal)/(HeightTotal*HeightTotal))*703);
                        if(bmi<17.5){bmCategory="Anoraxia";}
                        if((bmi>=17.5)&&(bmi<20.7)){bmCategory="Underweight";}
                        if((bmi>=20.7)&&(bmi<26.4)){bmCategory="Normal";}
                        if((bmi>=26.4)&&(bmi<27.8)){bmCategory="Marginally Overweight";}
                        if((bmi>=27.8)&&(bmi<=31.1)){bmCategory="Overweight";}
                        if(bmi>31.1){bmCategory="Obese";}
                    }
                }

                else{

                    if((WeightTotal<=0)||(HeightTotal<=0)){
                        Toast.makeText(ActivityBMI.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        bmi= (((WeightTotal)/(HeightTotal*HeightTotal))*703);
                        if(bmi<17.5){bmCategory="Anoraxia";}
                        if((bmi>=17.5)&&(bmi<19.1)){bmCategory="Underweight";}
                        if((bmi>=19.1)&&(bmi<25.8)){bmCategory="Normal";}
                        if((bmi>=25.8)&&(bmi<27.3)){bmCategory="Marginally Overweight";}
                        if((bmi>=27.3)&&(bmi<=32.3)){bmCategory="Overweight";}
                        if(bmi>32.3){bmCategory="Obese";}
                    }

                }
                String BMIValue= Float.toString(bmi);
                String BMIFinal="0.0";
                if(BMIValue.length()>=5){
                    BMIFinal = BMIValue.substring(0, 5);}
                else{
                    BMIFinal=BMIValue;
                }

                Results.setText("Your Body Mass Index is "+BMIFinal+" ");

                String extraText  = "";

                if(FemaleButton.isChecked()){
                    extraText = "<p><small>For Female :<br/>" +
                            "If BMI less than 17.5          - Anoraxia<br/>" +
                            "If BMI range 17.5 to 19.09     - Underweight<br/>" +
                            "If BMI range 19.09 to 25.79    - Normal<br/>" +
                            "If BMI range 25.8 to 27.29     - Marginally Overweight<br/>" +
                            "If BMI range 27.3 to 32.29     - Overweight<br/>" +
                            "If BMI greater than 32.3       - Obese</small></p>";
                    if(bmi<17.5){
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                    else if((bmi>=17.5)&&(bmi<19.1)){
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if((bmi>=19.1)&&(bmi<25.8)){
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if((bmi>=25.8)&&(bmi<27.3)){
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if((bmi>=27.3)&&(bmi<=32.3)){
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if(bmi>32.3){
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                }
                else{

                    extraText = "<p><small>For Male :<br/>" +
                            "If BMI less than 17.5      - Anoraxia<br/>" +
                            "If BMI range 17.5 to 20.69 - Underweight<br/>" +
                            "If BMI range 20.7 to 26.39 - Normal<br/>" +
                            "If BMI range 26.4 to 27.79 - Marginally Overweight<br/>" +
                            "If BMI range 27.8 to 31.09 - Overweight<br/>" +
                            "If BMI greater than 31.1   - Obese</small></p>";

                    if(bmi<17.5){
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                    else if((bmi>=17.5)&&(bmi<20.7)){
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if((bmi>=20.7)&&(bmi<26.4)){
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if((bmi>=26.4)&&(bmi<27.8)){
                        catColor  = "#00ff00";
                        category.setTextColor(Color.GREEN);
                    }
                    else if((bmi>=27.8)&&(bmi<=31.1)){
                        catColor  = "#ffae19";
                        category.setTextColor(Color.YELLOW);
                    }
                    else if(bmi>=31.1){
                        catColor  = "#ff0000";
                        category.setTextColor(Color.RED);
                    }
                }
                category.setText("You are considered in category "+bmCategory);

                Spanned text  = Html.fromHtml("<p>Your Body Mass Index is <b><h3>"+BMIFinal+"<h3></b></p>" +
                        "<p>You are considered in category <b><h1><font color=\""+catColor+"\">"+bmCategory+"</font></h1></b></p>" +
                        "" + extraText);


                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG)
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
        ActivityBMI.this.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityBMI.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityBMI.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}