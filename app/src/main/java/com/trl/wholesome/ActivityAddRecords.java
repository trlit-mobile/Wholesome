package com.trl.wholesome;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.fragements.DateDialogFragment2;

import java.util.Calendar;

/**
 * Created by mitesh on 1/6/16.
 */
public class ActivityAddRecords extends AppCompatActivity {

    private static final int DATE_DIALOG_ID = 0;

    private Button dateBtn, doneBtn, cancelBtn;
    private EditText caloriesEdit, descEdit;
    private int mYear;
    private int mMonth;
    private int mDay;
    DateDialogFragment2 frag;
    static Calendar now;
    String fromStr = "";
    TextView txtTitleCalories;
    int weekYear = 0;
    int dayWeek = 0;
    int yearM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromStr = extras.getString("FROM");
        }
        txtTitleCalories = (TextView) findViewById(R.id.textView2);
        if (fromStr.equalsIgnoreCase("BurnDiary")) {
            txtTitleCalories.setText("Calories Burned :");
        } else {
            txtTitleCalories.setText("Calories :");
        }

        now = Calendar.getInstance();
        mYear = now.get(Calendar.YEAR);
        mMonth = now.get(Calendar.MONTH);
        mDay = now.get(Calendar.DAY_OF_MONTH);
        weekYear = now.get(Calendar.WEEK_OF_YEAR);
        dayWeek = now.get(Calendar.DAY_OF_WEEK);
        yearM = mYear;
        String dayStr = mDay + "";
        if (mDay < 10) {
            dayStr = "0" + dayStr;
        }
        String date = "" + (mMonth + 1) + "-" + dayStr + "-" + mYear;

        dateBtn = (Button) findViewById(R.id.button_Date);
        dateBtn.setText(date);
        doneBtn = (Button) findViewById(R.id.button_done);
        cancelBtn = (Button) findViewById(R.id.button_canccel);
        caloriesEdit = (EditText) findViewById(R.id.addNotes_Calories);
        descEdit = (EditText) findViewById(R.id.addNotes_desc);



        doneBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (caloriesEdit.getText().toString().equals("")) {
                    Toast.makeText(ActivityAddRecords.this, "Enter Calories burned", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    SQLiteDatabase DB;
                    DB = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                    // String date =""+(mMonth+1)+"-"+mDay+"-"+mYear;
                    ContentValues cv = new ContentValues();
                    cv.put("mark", 0);
                    cv.put("date", dateBtn.getText().toString());
                    cv.put("week_of_year", weekYear);
                    cv.put("day_of_week", dayWeek);
                    cv.put("year", yearM);
                    cv.put("calories", Float.parseFloat(caloriesEdit.getText().toString()));
                    if (descEdit.getText().toString().equals("")) {
                        cv.put("desc", "N/A");
                    } else {
                        cv.put("desc", descEdit.getText().toString());
                    }
                    if (fromStr.equalsIgnoreCase("BurnDiary")) {
                        DB.insert("caloriesburned_pro_notes", null, cv);
                    } else {
                        DB.insert("caloriesburned_pro_intake_notes", null, cv);
                    }

                    DB.close();

                    hideSoftKeyBoard();

                    Intent i = new Intent();
                    setResult(Activity.RESULT_OK, i);
                    overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                hideSoftKeyBoard();
                overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
                finish();
            }
        });

        dateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog();
            }
        });
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction(); //get the fragment
        frag = DateDialogFragment2.newInstance(ActivityAddRecords.this, new DateDialogFragment2.DateDialogFragmentListener() {
            @Override
            public void updateChangedDate(int year, int month, int day) {
                String dayStr= day+"";
                if(day<10){
                    dayStr= "0"+dayStr;
                }
                String date =""+(month+1)+"-"+dayStr+"-"+year;
                dateBtn.setText(date);
                now.set(year, month, day);
                weekYear= now.get(Calendar.WEEK_OF_YEAR);
                dayWeek= now.get(Calendar.DAY_OF_WEEK);
                yearM= year;
            }
        }, now);

        frag.show(ft, "DateDialogFragment2");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }

}