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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trl.wholesome.views.SweetAlertDialog;

public class ActivitySmokingCost extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout relHeader;
    private ImageView imgViewBack;
    private ScrollView scrollView;
    private EditText edtNoCig;
    private EditText edtPricePack;
    private EditText edtNoCigInPack;
    private Spinner spinCostPerCig;
    private Button btnCalculate;
    private TextView txtTotalBodyFat;
    private TextView textResult;
    private boolean metric_week = false, metric_month = false, metric_year = false;
    private float Everyday_Total = 0;
    private float Price_Total = 0;
    private float Number_Total = 0;
    private float SCC = 0;
    private int days = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_smoking_cost);
        findViews();
    }

    private void findViews() {
        relHeader = (RelativeLayout) findViewById(R.id.relHeader);
        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        edtNoCig = (EditText) findViewById(R.id.edtNoCig);
        edtPricePack = (EditText) findViewById(R.id.edtPricePack);
        edtNoCigInPack = (EditText) findViewById(R.id.edtNoCigInPack);
        spinCostPerCig = (Spinner) findViewById(R.id.spinCostPerCig);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        txtTotalBodyFat = (TextView) findViewById(R.id.txtTotalBodyFat);
        textResult = (TextView) findViewById(R.id.textResult);

        btnCalculate.setOnClickListener(this);
        imgViewBack.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterCigTime = ArrayAdapter.createFromResource(
                this, R.array.time_array, android.R.layout.simple_spinner_item);
        adapterCigTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCostPerCig.setAdapter(adapterCigTime);
        spinCostPerCig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (spinCostPerCig.getSelectedItem().equals("Week")) {

                    metric_week = true;
                    metric_month = false;
                    metric_year = false;
                }
                if (spinCostPerCig.getSelectedItem().equals("Month")) {

                    metric_week = false;
                    metric_month = true;
                    metric_year = false;
                }
                if (spinCostPerCig.getSelectedItem().equals("Year")) {

                    metric_week = false;
                    metric_month = false;
                    metric_year = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgViewBack:
                onBackPressed();
                break;

            case R.id.btnCalculate:

                textResult.setText("");

                //Smoking Cigarettes everyday calculation
                if (edtNoCig.getText().length() == 0) {
                    Toast.makeText(ActivitySmokingCost.this, "Enter No. of Cigarattes you Smoke Everyday", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Everyday_Total = Float.parseFloat(edtNoCig.getText().toString());
                }

                //Price per Packet calculation
                EditText PriceString = (EditText) findViewById(R.id.edtPricePack);
                if (PriceString.getText().length() == 0) {
                    Toast.makeText(ActivitySmokingCost.this, "Enter Price per Pack", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Price_Total = Float.parseFloat(PriceString.getText().toString());
                }

                //Number of Cigarettes per Packet calculation
                EditText NumberString = (EditText) findViewById(R.id.edtNoCigInPack);
                if (NumberString.getText().length() == 0) {
                    Toast.makeText(ActivitySmokingCost.this, "Enter Quantity of Cigarettes in one Pack", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Number_Total = Float.parseFloat(NumberString.getText().toString());
                }

                //Days Calculation
                if (metric_week) {
                    days = 7;
                }
                if (metric_month) {
                    days = 30;
                }
                if (metric_year) {
                    days = 365;
                }
                if ((Everyday_Total <= 0) || (Price_Total <= 0) || (Number_Total <= 0)) {
                    Toast.makeText(ActivitySmokingCost.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                   SCC = (float) ((Everyday_Total) * (Price_Total / Number_Total) * (days));
                   // SCC=(float) ((Price_Total / Number_Total) );



                }
                String SCCValue = Float.toString(SCC);

                Spanned text  = null;


                if (metric_week) {
                    text = Html.fromHtml("<p>Your are wasting <b><h1>"+SCCValue+"</h1></b> weekly as smoking cost.</p>");
                    textResult.setText("You are spending "+SCCValue+" weekly.");
                }
                if (metric_month) {
                    text = Html.fromHtml("<p>Your are wasting <b><h1>"+SCCValue+"</h1></b> monthly as smoking cost.</p>");
                    textResult.setText("You are spending "+SCCValue+" monthly.");
                }
                if (metric_year) {
                    text = Html.fromHtml("<p>Your are wasting <b><h1>"+SCCValue+"</h1></b> yearly as smoking cost.</p>");
                    textResult.setText("You are spending "+SCCValue+" yearly.");
                }

                SweetAlertDialog sDialog = new SweetAlertDialog(ActivitySmokingCost.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG);
                sDialog.setTitleText("Wholesome!");
                sDialog.setContentText(text);
                sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
                sDialog.show();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        ActivitySmokingCost.this.finish();
    }
}

