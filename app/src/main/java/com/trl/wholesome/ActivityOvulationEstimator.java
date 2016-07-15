package com.trl.wholesome;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityOvulationEstimator extends AppCompatActivity {

    private int month_id = 1;
    private int year_id = 0;
    private int day = 1;
    private boolean leap_year = false;
    private boolean CalculationDone = false;
    private int ovulation = 0;
    private int month_days_array[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String month_name_array[] = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int cycle = 17;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Spinner day_spinner = (Spinner) findViewById(R.id.spinnerDay);
            String day_temp = day_spinner.getSelectedItem().toString();
            day = Integer.parseInt(day_temp);
            Spinner year_spinner = (Spinner) findViewById(R.id.spinnerYear);
            String year_temp = year_spinner.getSelectedItem().toString();
            year_id = Integer.parseInt(year_temp);
            Spinner cycle_spinner = (Spinner) findViewById(R.id.spinnerCycle);
            String cycle_temp = cycle_spinner.getSelectedItem().toString();
            cycle = Integer.parseInt(cycle_temp);

            int temp_year_id = year_id;
            int temp_month_id = month_id;
            //Validations
            if (leap_year) {
                if ((temp_month_id == 2) && (day > 29)) {
                    Toast.makeText(ActivityOvulationEstimator.this, "Enter Valid Date for February", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if ((temp_month_id == 2) && (day > 28)) {
                    Toast.makeText(ActivityOvulationEstimator.this, "Enter Valid Date for February of Non Leap Year", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (((temp_month_id == 4) || (temp_month_id == 6) || (temp_month_id == 9) || (temp_month_id == 11)) && (day > 30)) {
                Toast.makeText(ActivityOvulationEstimator.this, "Enter Valid Date for current month", Toast.LENGTH_SHORT).show();
                return;
            }

            //Calculation
            ovulation = day + cycle - 14;
            if (ovulation > month_days_array[temp_month_id - 1]) {
                ovulation = ovulation - month_days_array[temp_month_id - 1];
                temp_month_id = temp_month_id + 1;
                if (temp_month_id > 12) {
                    temp_month_id = 1;
                    temp_year_id = temp_year_id + 1;
                }
            }
            TextView results = (TextView) findViewById(R.id.txtResult);
            String tempText = Integer.toString(ovulation) + " " + month_name_array[temp_month_id - 1] + " " + Integer.toString(temp_year_id);
            results.setText(tempText);

            Spanned text  = Html.fromHtml("<p>The probable date of Ovulation based on your period cycle is <b><h1><font color=\"#FF0000\">" +tempText+"</font><h1></b></p>");

            SweetAlertDialog sDialog = new SweetAlertDialog(ActivityOvulationEstimator.this,
                    SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG);
            sDialog.setTitleText("Wholesome!");
            sDialog.setContentText(text);
            sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
            sDialog.show();

        }
    };
    private ImageView imgBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ovulation);
        ImageView imageView = (ImageView) findViewById(R.id.imgViewBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.month, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                TextView results = (TextView) findViewById(R.id.txtResult);
                results.setText("");

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
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.day, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        String day_string = spinner2.getSelectedItem().toString();
        day = Integer.parseInt(day_string);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView results = (TextView) findViewById(R.id.txtResult);
                results.setText("");

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

                TextView results = (TextView) findViewById(R.id.txtResult);
                results.setText("");

                Spinner spinner3_insider = (Spinner) findViewById(R.id.spinnerYear);
                if (spinner3_insider.getSelectedItem().equals("2010")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2011")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2012")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
                if (spinner3_insider.getSelectedItem().equals("2013")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2014")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2015")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2016")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
                if (spinner3_insider.getSelectedItem().equals("2017")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2018")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2019")) {
                    leap_year = false;
                }
                if (spinner3_insider.getSelectedItem().equals("2020")) {
                    leap_year = true;
                    month_days_array[1] = 29;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Spinner spinner4 = (Spinner) findViewById(R.id.spinnerCycle);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.cycle, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        String cycle_string = spinner4.getSelectedItem().toString();
        cycle = Integer.parseInt(cycle_string);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView results = (TextView) findViewById(R.id.txtResult);
                results.setText("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Button button = (Button) findViewById(R.id.imgCalculate);
        button.setOnClickListener(mClickListener);

        imgBack = (ImageView)findViewById(R.id.imgViewBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }
}