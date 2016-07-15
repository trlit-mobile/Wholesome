package com.trl.wholesome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.views.SweetAlertDialog;


public class ActivityBloodAlcohol extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGrpGender;
    private RadioButton radioBtnMale;
    private RadioButton radioBtnFemale;
    private Spinner spinBeer;
    private Spinner spinWine;
    private Spinner spinHardLiquor;
    private Spinner spinHours;
    private Spinner spinMinutes;
    private Spinner spinWeight;
    private EditText editWeight;
    private Button btnCalculate;
    private TextView textResult;
    private ImageView imgViewBack;
    private int beer_total=0;
    private int wine_total=0;
    private int liquor_total=0;
    private int hoursValue=0;
    private int minutesValue=0;
    private boolean metric = false;
    private float hoursTotal=0f;
    private float AlcoholTotal=0f;
    private float WeightTotal=0f;

    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_alcohol);
        findViews();
    }

    private void findViews() {
        scroll = (ScrollView)findViewById(R.id.scrollView);
        radioGrpGender = (RadioGroup)findViewById(R.id.radioGrpGender);
        radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        spinBeer = (Spinner) findViewById(R.id.spinBeer);
        spinWine = (Spinner) findViewById(R.id.spinWine);
        spinHardLiquor = (Spinner) findViewById(R.id.spinHardLiquor);
        spinHours = (Spinner) findViewById(R.id.spinHours);
        spinMinutes = (Spinner) findViewById(R.id.spinMinutes);
        spinWeight = (Spinner) findViewById(R.id.spinWeight);
        editWeight = (EditText) findViewById(R.id.edtWeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        textResult = (TextView) findViewById(R.id.textResult);
        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);

        imgViewBack.setOnClickListener(this);
        radioBtnMale.setOnClickListener(this);
        radioBtnFemale.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.array_spinner_1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBeer.setAdapter(adapter1);
        spinBeer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinBeer.getSelectedItem().equals("0")) {

                    beer_total = 0;
                }
                if (spinBeer.getSelectedItem().equals("1")) {

                    beer_total = 1;
                }
                if (spinBeer.getSelectedItem().equals("2")) {

                    beer_total = 2;
                }
                if (spinBeer.getSelectedItem().equals("3")) {

                    beer_total = 3;
                }
                if (spinBeer.getSelectedItem().equals("4")) {

                    beer_total = 4;
                }
                if (spinBeer.getSelectedItem().equals("5")) {

                    beer_total = 5;
                }
                if (spinBeer.getSelectedItem().equals("6")) {

                    beer_total = 6;
                }
                if (spinBeer.getSelectedItem().equals("7")) {

                    beer_total = 7;
                }
                if (spinBeer.getSelectedItem().equals("8")) {

                    beer_total = 8;
                }
                if (spinBeer.getSelectedItem().equals("9")) {

                    beer_total = 9;
                }
                if (spinBeer.getSelectedItem().equals("10")) {

                    beer_total = 10;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinWine.setAdapter(adapter1);
        spinWine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinWine.getSelectedItem().equals("0")) {

                    wine_total = 0;
                }
                if (spinWine.getSelectedItem().equals("1")) {

                    wine_total = 1;
                }
                if (spinWine.getSelectedItem().equals("2")) {

                    wine_total = 2;
                }
                if (spinWine.getSelectedItem().equals("3")) {

                    wine_total = 3;
                }
                if (spinWine.getSelectedItem().equals("4")) {

                    wine_total = 4;
                }
                if (spinWine.getSelectedItem().equals("5")) {

                    wine_total = 5;
                }
                if (spinWine.getSelectedItem().equals("6")) {

                    wine_total = 6;
                }
                if (spinWine.getSelectedItem().equals("7")) {

                    wine_total = 7;
                }
                if (spinWine.getSelectedItem().equals("8")) {

                    wine_total = 8;
                }
                if (spinWine.getSelectedItem().equals("9")) {

                    wine_total = 9;
                }
                if (spinWine.getSelectedItem().equals("10")) {

                    wine_total = 10;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinHardLiquor.setAdapter(adapter1);
        spinHardLiquor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinHardLiquor.getSelectedItem().equals("0")) {

                    liquor_total = 0;
                }
                if (spinHardLiquor.getSelectedItem().equals("1")) {

                    liquor_total = 1;
                }
                if (spinHardLiquor.getSelectedItem().equals("2")) {

                    liquor_total = 2;
                }
                if (spinHardLiquor.getSelectedItem().equals("3")) {

                    liquor_total = 3;
                }
                if (spinHardLiquor.getSelectedItem().equals("4")) {

                    liquor_total = 4;
                }
                if (spinHardLiquor.getSelectedItem().equals("5")) {

                    liquor_total = 5;
                }
                if (spinHardLiquor.getSelectedItem().equals("6")) {

                    liquor_total = 6;
                }
                if (spinHardLiquor.getSelectedItem().equals("7")) {

                    liquor_total = 7;
                }
                if (spinHardLiquor.getSelectedItem().equals("8")) {

                    liquor_total = 8;
                }
                if (spinHardLiquor.getSelectedItem().equals("9")) {

                    liquor_total = 9;
                }
                if (spinHardLiquor.getSelectedItem().equals("10")) {

                    liquor_total = 10;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<CharSequence> adapterHours = ArrayAdapter.createFromResource(
                this, R.array.hours_array, android.R.layout.simple_spinner_item);
        adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHours.setAdapter(adapterHours);
        spinHours.setSelection(0);
        spinHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinHours.getSelectedItem().equals("0")) {

                    hoursValue = 0;
                }
                if (spinHours.getSelectedItem().equals("1")) {

                    hoursValue = 1;
                }
                if (spinHours.getSelectedItem().equals("2")) {

                    hoursValue = 2;
                }
                if (spinHours.getSelectedItem().equals("3")) {

                    hoursValue = 3;
                }
                if (spinHours.getSelectedItem().equals("4")) {

                    hoursValue = 4;
                }
                if (spinHours.getSelectedItem().equals("5")) {

                    hoursValue = 5;
                }
                if (spinHours.getSelectedItem().equals("6")) {

                    hoursValue = 6;
                }
                if (spinHours.getSelectedItem().equals("7")) {

                    hoursValue = 7;
                }
                if (spinHours.getSelectedItem().equals("8")) {

                    hoursValue = 8;
                }
                if (spinHours.getSelectedItem().equals("9")) {

                    hoursValue = 9;
                }
                if (spinHours.getSelectedItem().equals("10")) {

                    hoursValue = 10;
                }
                if (spinHours.getSelectedItem().equals("11")) {

                    hoursValue = 11;
                }
                if (spinHours.getSelectedItem().equals("12")) {

                    hoursValue = 12;
                }
                if (spinHours.getSelectedItem().equals("13")) {

                    hoursValue = 13;
                }
                if (spinHours.getSelectedItem().equals("14")) {

                    hoursValue = 14;
                }
                if (spinHours.getSelectedItem().equals("15")) {

                    hoursValue = 15;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<CharSequence> adapterMinutes = ArrayAdapter.createFromResource(
                this, R.array.min_array, android.R.layout.simple_spinner_item);
        adapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMinutes.setAdapter(adapterMinutes);
        spinMinutes.setSelection(0);
        spinMinutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinMinutes.getSelectedItem().equals("0")) {

                    minutesValue = 0;
                }
                if (spinHours.getSelectedItem().equals("5")) {

                    minutesValue = 5;
                }
                if (spinHours.getSelectedItem().equals("10")) {

                    minutesValue = 10;
                }
                if (spinHours.getSelectedItem().equals("15")) {

                    minutesValue = 15;
                }
                if (spinHours.getSelectedItem().equals("20")) {

                    minutesValue = 20;
                }
                if (spinHours.getSelectedItem().equals("25")) {

                    minutesValue = 25;
                }
                if (spinHours.getSelectedItem().equals("30")) {

                    minutesValue = 30;
                }
                if (spinHours.getSelectedItem().equals("35")) {

                    minutesValue = 35;
                }
                if (spinHours.getSelectedItem().equals("40")) {

                    minutesValue = 40;
                }
                if (spinHours.getSelectedItem().equals("45")) {

                    minutesValue = 45;
                }
                if (spinHours.getSelectedItem().equals("50")) {

                    minutesValue = 50;
                }
                if (spinHours.getSelectedItem().equals("55")) {

                    minutesValue = 55;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<CharSequence> adapterWeight = ArrayAdapter.createFromResource(
                this, R.array.lbs_kg_array, android.R.layout.simple_spinner_item);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWeight.setAdapter(adapterWeight);
        //spinWeight.setAdapter(adapterWeight);
        spinWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinWeight.getSelectedItem().equals("Kg")) {

                    metric = true;
                }
                if (spinWeight.getSelectedItem().equals("Lbs")) {

                    metric = false;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalculate:
                //Validation for empty values
                hoursValue = Integer.parseInt(spinHours.getSelectedItem().toString());
                minutesValue = Integer.parseInt(spinMinutes.getSelectedItem().toString());

                if (hoursValue ==0 && minutesValue == 0) {
                    Toast.makeText(ActivityBloodAlcohol.this, "Enter correct value for time after last drink.", Toast.LENGTH_SHORT).show();
                    textResult.setText("");
                    return;
                }else if(hoursValue == 0){
                    hoursTotal = Float.parseFloat(String.valueOf(minutesValue / 60));
                } else if(minutesValue == 0){
                    hoursTotal = Float.parseFloat(String.valueOf(hoursValue));
                }else{
                    hoursTotal = Float.parseFloat(String.valueOf(hoursValue + (minutesValue / 60)));
                }
                /*else {
                    if (minutesValue != 0) {
                        hoursTotal = Float.parseFloat(String.valueOf(hoursValue + (minutesValue / 60)));
                    } else {
                        hoursTotal = Float.parseFloat(String.valueOf(hoursValue));
                    }
                }*/

                if ((editWeight.getText().length() == 0)) {
                    Toast.makeText(ActivityBloodAlcohol.this, "Enter your weight.", Toast.LENGTH_SHORT).show();
                    textResult.setText("");
                    return;
                } else {
                    WeightTotal = Float.parseFloat(editWeight.getText().toString());
                }

                //Alcohol NON Zero Values Validation
                if ((beer_total == 0) && (wine_total == 0) && (liquor_total == 0)) {
                    Toast.makeText(ActivityBloodAlcohol.this, "Enter correct alcohol values", Toast.LENGTH_SHORT).show();
                    textResult.setText("");
                    return;
                } else {
                    AlcoholTotal = (float) ((beer_total * 0.6) + (wine_total * 0.6) + (liquor_total * 0.5));
                }
                if (metric) {
                    WeightTotal = (float) (WeightTotal * 2.25);
                }

                //WeightTotal = Math.round(WeightTotal);
                float bac = 0;
                if (radioBtnMale.isChecked()) {
                    if ((WeightTotal <= 0)) {
                        Toast.makeText(ActivityBloodAlcohol.this, "Enter correct value for weight", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        bac = (float) (((AlcoholTotal * 5.14) / (WeightTotal * 0.73)) - (0.015 * hoursTotal));
                    }
                }
                else if (radioBtnFemale.isChecked()) {
                    if ((WeightTotal <= 0)) {
                        Toast.makeText(ActivityBloodAlcohol.this, "Enter correct value for weight", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        bac = (float) (((AlcoholTotal * 5.14) / (WeightTotal * 0.66)) - (0.015 * hoursTotal));
                    }
                }


                String BacValue;

                //float p = (float)Math.pow(10,5);
                //bac = bac * p;
                //float tmp = Math.round(bac);
                //bac=tmp/p;


                BacValue = Float.toString(bac);
                String BACFinal = "0.0";
                if (BacValue.length() >= 4) {
                    BACFinal = BacValue.substring(0, 4);
                } else {
                    BACFinal = BacValue;
                }


                String color  ="";
                //Putting color variation as per BAC calculation
                if (bac < 0.09) {
                    color = "#00FF00";
                }
                else if (bac >= 0.09) {
                    color = "#FF0000";
                }
                textResult.setText("Your Blood Alcohol Concentration is " + BACFinal);

                Spanned text  = Html.fromHtml("<p>Your Blood Alcohol Concentration is <b><h1><font color=\""+color+"\">" + BACFinal+"</font><h1></b></p>" +
                        "<p> You are considered intoxicated if your BAC is more than 0.08. For below 21 years of age the legal limit is 0.02. </p>");

                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG)
                        .setTitleText("Wholesome!")
                        .setContentText(text)
                        .setCustomImage(R.drawable.imgpsh_fullsize)
                        .show();
                break;

            case R.id.imgViewBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        ActivityBloodAlcohol.this.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityBloodAlcohol.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityBloodAlcohol.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}