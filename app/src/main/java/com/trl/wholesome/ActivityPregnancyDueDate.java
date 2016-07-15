package com.trl.wholesome;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.views.SweetAlertDialog;

public class ActivityPregnancyDueDate extends AppCompatActivity implements View.OnClickListener {

    int month_id;
    int year_id;
    int day;
    int month_days = 0;
    String month_string = "";
    boolean leap_year = false;
    boolean CalculationDone = false;
    int conception_calc = 0, risk_calc_from = 0, risk_calc_to = 0, organ_calc_from = 0, organ_calc_to = 0, trimester1_calc = 0, trimester2_calc = 0, survival_calc = 0, duedate_calc = 0, heartbeat_calc = 0;
    int month_days_array[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String month_name_array[] = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private ImageView imgPageBack;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Spinner day_spinner = (Spinner) findViewById(R.id.spinnerDay);
            String day_temp = day_spinner.getSelectedItem().toString();
            day = Integer.parseInt(day_temp);
            Spinner year_spinner = (Spinner) findViewById(R.id.spinnerYear);
            String year_temp = year_spinner.getSelectedItem().toString();
            year_id = Integer.parseInt(year_temp);

            TextView conception = (TextView) findViewById(R.id.txtConceptionResult);
            TextView risk = (TextView) findViewById(R.id.txtHighestFetalRiskResult);
            TextView organ = (TextView) findViewById(R.id.txtOrgansBeginFronResult);
            TextView trimester1 = (TextView) findViewById(R.id.txtEndofTrimesterResult);
            TextView survival = (TextView) findViewById(R.id.txtBodySurviveResult);
            TextView heartbeat = (TextView) findViewById(R.id.txtFirstHeartBeatResult);
            TextView trimester2 = (TextView) findViewById(R.id.txtEndofTrimester2Result);
            TextView duedate = (TextView) findViewById(R.id.txtDueDateResult);

            String strConception = "";
            String strRisk = "";
            String strOrgan = "";
            String strTrimester1 = "";
            String strSurvival = "";
            String strHeartbeat = "";
            String strTrimester2 = "";
            String strDuedate = "";

            //Validations
            if (leap_year) {
                if ((month_id == 2) && (day > 29)) {
                    Toast.makeText(ActivityPregnancyDueDate.this, "Enter Valid Date for February", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if ((month_id == 2) && (day > 28)) {
                    Toast.makeText(ActivityPregnancyDueDate.this, "Enter Valid Date for February of Non Leap Year", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (((month_id == 4) || (month_id == 6) || (month_id == 9) || (month_id == 11)) && (day > 30)) {
                Toast.makeText(ActivityPregnancyDueDate.this, "Enter Valid Date for current month", Toast.LENGTH_SHORT).show();
                return;
            }


            //Conception Calculation
            conception_calc = day + 14;
            int conception_year = year_id;
            int conception_month = month_id;
            if (conception_calc > month_days_array[conception_month - 1]) {
                conception_calc = conception_calc - month_days_array[conception_month - 1];
                conception_month = conception_month + 1;
                if (conception_month > 12) {
                    conception_month = 1;
                    conception_year = conception_year + 1;
                }
            }
            strConception = Integer.toString(conception_calc) + " " + month_name_array[conception_month - 1] + " " + Integer.toString(conception_year);
            conception.setTextColor(Color.parseColor("#F0800A"));
            conception.setText(Integer.toString(conception_calc) + " " + month_name_array[conception_month - 1] + " " + Integer.toString(conception_year));

            //Highest Fetal Risk Calculation
            risk_calc_from = day + 35;
            risk_calc_to = day + 70;


            ///checking for February month----HFR From
            CalculationDone = false;
            int hfr_year_from = year_id;
            int hfr_month_from = month_id;
            while (CalculationDone == false) {
                if (risk_calc_from > month_days_array[hfr_month_from - 1]) {
                    risk_calc_from = risk_calc_from - month_days_array[hfr_month_from - 1];
                    hfr_month_from = hfr_month_from + 1;
                    if (hfr_month_from > 12) {
                        hfr_month_from = 1;
                        hfr_year_from = hfr_year_from + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }

            //Checking for HFR To--
            CalculationDone = false;
            int hfr_year_to = year_id;
            int hfr_month_to = month_id;
            while (CalculationDone == false) {
                if (risk_calc_to > month_days_array[hfr_month_to - 1]) {
                    risk_calc_to = risk_calc_to - month_days_array[hfr_month_to - 1];
                    hfr_month_to = hfr_month_to + 1;
                    if (hfr_month_to > 12) {
                        hfr_month_to = 1;
                        hfr_year_to = hfr_year_to + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }
            strRisk = Integer.toString(risk_calc_from) + " " + month_name_array[hfr_month_from - 1] + " " + Integer.toString(hfr_year_from) + " to " +
                    Integer.toString(risk_calc_to) + " " + month_name_array[hfr_month_to - 1] + " " + Integer.toString(hfr_year_to);
            strOrgan = Integer.toString(risk_calc_from) + " " + month_name_array[hfr_month_from - 1] + " " + Integer.toString(hfr_year_from) + " to " +
                    Integer.toString(risk_calc_to) + " " + month_name_array[hfr_month_to - 1] + " " + Integer.toString(hfr_year_to);
            risk.setTextColor(Color.parseColor("#F50202"));
            organ.setTextColor(Color.parseColor("#7B02F5"));
            risk.setText(Integer.toString(risk_calc_from) + " " + month_name_array[hfr_month_from - 1] + " " + Integer.toString(hfr_year_from) + " to " +
                    Integer.toString(risk_calc_to) + " " + month_name_array[hfr_month_to - 1] + " " + Integer.toString(hfr_year_to));
            organ.setText(Integer.toString(risk_calc_from) + " " + month_name_array[hfr_month_from - 1] + " " + Integer.toString(hfr_year_from) + " to " +
                    Integer.toString(risk_calc_to) + " " + month_name_array[hfr_month_to - 1] + " " + Integer.toString(hfr_year_to));


            //End of 1st Trimester Calculation
            trimester1_calc = day + 84;
            CalculationDone = false;
            int trimester1_year = year_id;
            int trimester1_month = month_id;
            while (CalculationDone == false) {
                if (trimester1_calc > month_days_array[trimester1_month - 1]) {
                    trimester1_calc = trimester1_calc - month_days_array[trimester1_month - 1];
                    trimester1_month = trimester1_month + 1;
                    if (trimester1_month > 12) {
                        trimester1_month = 1;
                        trimester1_year = trimester1_year + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }
            strTrimester1 = Integer.toString(trimester1_calc) + " " + month_name_array[trimester1_month - 1] + " " + Integer.toString(trimester1_year);
            trimester1.setTextColor(Color.parseColor("#85720B"));
            trimester1.setText(" " + Integer.toString(trimester1_calc) + " " + month_name_array[trimester1_month - 1] + " " + Integer.toString(trimester1_year));


            //Survival Calculation
            survival_calc = day + 161;
            CalculationDone = false;
            int survival_year = year_id;
            int survival_month = month_id;
            while (CalculationDone == false) {
                if (survival_calc > month_days_array[survival_month - 1]) {
                    survival_calc = survival_calc - month_days_array[survival_month - 1];
                    survival_month = survival_month + 1;
                    if (survival_month > 12) {
                        survival_month = 1;
                        survival_year = survival_year + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }
            strSurvival = Integer.toString(survival_calc) + " " + month_name_array[survival_month - 1] + " " + Integer.toString(survival_year);
            survival.setTextColor(Color.parseColor("#D9D200"));
            survival.setText(" " + Integer.toString(survival_calc) + " " + month_name_array[survival_month - 1] + " " + Integer.toString(survival_year));


            //End of 2nd Trimester
            trimester2_calc = day + 188;
            CalculationDone = false;
            int trimester2_year = year_id;
            int trimester2_month = month_id;
            while (CalculationDone == false) {
                if (trimester2_calc > month_days_array[trimester2_month - 1]) {
                    trimester2_calc = trimester2_calc - month_days_array[trimester2_month - 1];
                    trimester2_month = trimester2_month + 1;
                    if (trimester2_month > 12) {
                        trimester2_month = 1;
                        trimester2_year = trimester2_year + 1;
                    }
                } else {
                    CalculationDone = true;
                }
           }
            strTrimester2 = Integer.toString(trimester2_calc) + " " + month_name_array[trimester2_month - 1] + " " + Integer.toString(trimester2_year);
            trimester2.setTextColor(Color.parseColor("#85720B"));
            trimester2.setText(" " + Integer.toString(trimester2_calc) + " " + month_name_array[trimester2_month - 1] + " " + Integer.toString(trimester2_year));

            //1st Heartbeat Calculation
            heartbeat_calc = day + 43;
            CalculationDone = false;
            int heartbeat_year = year_id;
            int heartbeat_month = month_id;
            while (CalculationDone == false) {
                if (heartbeat_calc > month_days_array[heartbeat_month - 1]) {
                    heartbeat_calc = heartbeat_calc - month_days_array[heartbeat_month - 1];
                    heartbeat_month = heartbeat_month + 1;
                    if (heartbeat_month > 12) {
                        heartbeat_month = 1;
                        heartbeat_year = heartbeat_year + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }
            strHeartbeat = Integer.toString(heartbeat_calc) + " " + month_name_array[heartbeat_month - 1] + " " + Integer.toString(heartbeat_year);
            heartbeat.setTextColor(Color.parseColor("#F0800A"));
            heartbeat.setText(" " + Integer.toString(heartbeat_calc) + " " + month_name_array[heartbeat_month - 1] + " " + Integer.toString(heartbeat_year));


            //Due Date Calculation
            duedate_calc = day + 277;
            CalculationDone = false;
            int duedate_year = year_id;
            int duedate_month = month_id;
            while (CalculationDone == false) {
                if (duedate_calc > month_days_array[duedate_month - 1]) {
                    duedate_calc = duedate_calc - month_days_array[duedate_month - 1];
                    duedate_month = duedate_month + 1;
                    if (duedate_month > 12) {
                        duedate_month = 1;
                        duedate_year = duedate_year + 1;
                    }
                } else {
                    CalculationDone = true;
                }
            }
            strDuedate = Integer.toString(duedate_calc) + " " + month_name_array[duedate_month - 1] + " " + Integer.toString(duedate_year);
            duedate.setTextColor(Color.parseColor("#0B990B"));
            duedate.setText(" " + Integer.toString(duedate_calc) + " " + month_name_array[duedate_month - 1] + " " + Integer.toString(duedate_year));

            Spanned text  = Html.fromHtml("<p>Probable due date is <b><h1>"+strDuedate+"</h1></b></p>" +
                    "<p><small>Probable date of conception is <br/><b>"+strConception+"</b><br/><br/>" +
                    "Probable duration of fatal risk is <br/><b>"+strRisk+"</b><br/><br/>" +
                    "Probable date of organs begin to form is <br/><b>"+strOrgan+"</b><br/><br/>" +
                    "Probable date of first trimester's end is <br/><b>"+strTrimester1+"</b><br/><br/>" +
                    "Probable date of baby's servival outside the womb is <br/><b>"+strSurvival+"</b><br/><br/>" +
                    "Probable date of 1st heart beat is <br/><b>"+strHeartbeat+"</b></small></p>");

            new SweetAlertDialog(ActivityPregnancyDueDate.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE,
                    SweetAlertDialog.NO_SHARE_FLAG)
                    .setTitleText("Wholesome!")
                    .setContentText(text)
                    .setCustomImage(R.drawable.imgpsh_fullsize)
                    .show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pregnancy_due_date);
        findViews();
        onClickHandler();
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.month, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TextView conception = (TextView) findViewById(R.id.txtConceptionResult);
                TextView risk = (TextView) findViewById(R.id.txtHighestFetalRiskResult);
                TextView organ = (TextView) findViewById(R.id.txtOrgansBeginFronResult);
                TextView trimester1 = (TextView) findViewById(R.id.txtEndofTrimesterResult);
                TextView survival = (TextView) findViewById(R.id.txtBodySurviveResult);
                TextView heartbeat = (TextView) findViewById(R.id.txtFirstHeartBeatResult);
                TextView duedate = (TextView) findViewById(R.id.txtDueDateResult);
                conception.setText("");
                risk.setText("");
                organ.setText("");
                trimester1.setText("");
                survival.setText("");
                heartbeat.setText("");
                duedate.setText("");

//                Spinner spinner1_insider = (Spinner) findViewById(R.id.spinnerMonth);
                if (spinner1.getSelectedItem().equals("January")) {
                    month_id = 1;
                } else if (spinner1.getSelectedItem().equals("February")) {
                    month_id = 2;
                } else if (spinner1.getSelectedItem().equals("March")) {
                    month_id = 3;

                } else if (spinner1.getSelectedItem().equals("April")) {
                    month_id = 4;

                } else if (spinner1.getSelectedItem().equals("May")) {
                    month_id = 5;

                } else if (spinner1.getSelectedItem().equals("June")) {
                    month_id = 6;

                } else if (spinner1.getSelectedItem().equals("July")) {
                    month_id = 7;

                } else if (spinner1.getSelectedItem().equals("August")) {
                    month_id = 8;

                } else if (spinner1.getSelectedItem().equals("September")) {
                    month_id = 9;

                } else if (spinner1.getSelectedItem().equals("October")) {
                    month_id = 10;

                } else if (spinner1.getSelectedItem().equals("November")) {
                    month_id = 11;

                } else {
                    month_id = 12;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        String day_string = spinner2.getSelectedItem().toString();
        day = Integer.parseInt(day_string);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                TextView conception = (TextView) findViewById(R.id.txtConceptionResult);
                TextView risk = (TextView) findViewById(R.id.txtHighestFetalRiskResult);
                TextView organ = (TextView) findViewById(R.id.txtOrgansBeginFronResult);
                TextView trimester1 = (TextView) findViewById(R.id.txtEndofTrimesterResult);
                TextView survival = (TextView) findViewById(R.id.txtBodySurviveResult);
                TextView heartbeat = (TextView) findViewById(R.id.txtFirstHeartBeatResult);
                TextView duedate = (TextView) findViewById(R.id.txtDueDateResult);
                conception.setText("");
                risk.setText("");
                organ.setText("");
                trimester1.setText("");
                survival.setText("");
                heartbeat.setText("");
                duedate.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Spinner spinner3 = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.year, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        String year = spinner3.getSelectedItem().toString();
        year_id = Integer.parseInt(year);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView conception = (TextView) findViewById(R.id.txtConceptionResult);
                TextView risk = (TextView) findViewById(R.id.txtHighestFetalRiskResult);
                TextView organ = (TextView) findViewById(R.id.txtOrgansBeginFronResult);
                TextView trimester1 = (TextView) findViewById(R.id.txtEndofTrimesterResult);
                TextView survival = (TextView) findViewById(R.id.txtBodySurviveResult);
                TextView heartbeat = (TextView) findViewById(R.id.txtFirstHeartBeatResult);
                TextView duedate = (TextView) findViewById(R.id.txtDueDateResult);
                conception.setText("");
                risk.setText("");
                organ.setText("");
                trimester1.setText("");
                survival.setText("");
                heartbeat.setText("");
                duedate.setText("");

                Spinner spinner3_insider = (Spinner) findViewById(R.id.spinnerYear);
                if (spinner3_insider.getSelectedItem().equals("2010")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2011")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2012")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
                else if (spinner3_insider.getSelectedItem().equals("2013")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2014")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2015")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2016")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
                else if (spinner3_insider.getSelectedItem().equals("2017")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2018")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2019")) {
                    leap_year = false;
                }
                else if (spinner3_insider.getSelectedItem().equals("2020")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button button = (Button) findViewById(R.id.imgCalculate);
        button.setOnClickListener(mClickListener);
    }

    private void onClickHandler() {
        imgPageBack.setOnClickListener(this);
    }

    private void findViews() {
        imgPageBack = (ImageView) findViewById(R.id.imgViewBack);
    }

    @Override
    public void onClick(View v) {
        if (v == imgPageBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }
}