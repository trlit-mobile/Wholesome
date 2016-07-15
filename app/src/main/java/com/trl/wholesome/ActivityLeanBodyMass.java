package com.trl.wholesome;

import android.app.Activity;
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

public class ActivityLeanBodyMass extends Activity {
    /** Called when the activity is first created. */
    
	boolean metric_weight=false, metric_height=false;
	float HeightTotal=0;
	float WeightTotal=0;
	float LBM=0;
	float LBM_percentage=0;
	ImageView imgViewBack;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lean_body_mask);

        
     // Initiate a generic request to load it with an ad     

        
        //Radio Button Functionalities
		RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
		RadioButton FemaleButton = (RadioButton) findViewById(R.id.radioFemale);
		imgViewBack= (ImageView) findViewById(R.id.imgViewBack);
		
		FemaleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView Results_clear = (TextView) findViewById(R.id.txtLeanMassResult);
	        	Results_clear.setText("");
	        	TextView percentage= (TextView)findViewById(R.id.txtLeanMassPercentageResult);
	        	percentage.setText("");
			}
		});
		
		MaleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView Results_clear = (TextView) findViewById(R.id.txtLeanMassResult);
	        	Results_clear.setText("");
	        	TextView category_clear= (TextView)findViewById(R.id.txtLeanMassPercentageResult);
	        	category_clear.setText("");
			}
		});
		imgViewBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				onBackPressed();

			}
		});
      
		//Weight Spinner & Array in LBS And KG
        Spinner spinner1=(Spinner)findViewById(R.id.spinnerWeight);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.lbs_kg_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				Spinner spinner1_insider = (Spinner)findViewById(R.id.spinnerWeight);
				if(spinner1_insider.getSelectedItem().equals("Kg")){
					
					metric_weight=false;
				}
				else if(spinner1_insider.getSelectedItem().equals("Lbs")){
					
					metric_weight=true;
				}
			}
       
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				}
		});
		
		//Waist Spinner & Array in Inch and cm
		 Spinner spinner2=(Spinner)findViewById(R.id.spinnerHeight);
	        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
					this, R.array.inch_cm_array, android.R.layout.simple_spinner_item);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter2);
			
			spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					Spinner spinner2_insider = (Spinner)findViewById(R.id.spinnerHeight);
					if(spinner2_insider.getSelectedItem().equals("Inch")){
						
						metric_height=true;;
					}
					else if(spinner2_insider.getSelectedItem().equals("Cm")){
						
						metric_height=false;
					}
				}
	       
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					}
			});
			
			//Calculate Button Functionalities
			Button button = (Button)findViewById(R.id.btnCalculate);
	        button.setOnClickListener(mClickListener);  
       
        
    }
	 private OnClickListener mClickListener = new OnClickListener() {
	        public void onClick(View v){
	        	
	        	TextView Results = (TextView) findViewById(R.id.txtLeanMassResult);
	        	//Results.setText("");
	        	TextView percentage= (TextView)findViewById(R.id.txtLeanMassPercentageResult);

				TextView txtLeanMassPercentage=(TextView)findViewById(R.id.txtLeanMassPercentage);
	        	//percentage.setText("");
				txtLeanMassPercentage.setVisibility(View.GONE);
	        	
	        	//Weight Calculation
	        	EditText WeightString= (EditText)findViewById(R.id.edtWeight);
	        	if (WeightString.getText().length() == 0){
	        		Toast.makeText(ActivityLeanBodyMass.this, "Enter Weight", Toast.LENGTH_SHORT).show();
	        		return;
	        	}
	        	else{
	        		WeightTotal = Float.parseFloat(WeightString.getText().toString());
	        	}
	          	if(metric_weight){
	        		WeightTotal = (float) (WeightTotal*0.45359237);
	        	}
	        	//WeightTotal = Math.round(WeightTotal);
	        	
	        	//Height Calculation
	        	EditText HeightString= (EditText)findViewById(R.id.edtWaist);
	        	
	            if (HeightString.getText().length() == 0){
	            	Toast.makeText(ActivityLeanBodyMass.this, "Enter Height", Toast.LENGTH_SHORT).show();
	            	return;
	            }
	            else{
	            	HeightTotal = Float.parseFloat(HeightString.getText().toString());
	            }
	            if(metric_height){
	            	HeightTotal = (float)(HeightTotal*2.54);
	            }
	            //HeightTotal = Math.round(HeightTotal);
	            
	            RadioButton MaleButton = (RadioButton) findViewById(R.id.radioMale);
	                    		        	
	        	if(MaleButton.isChecked()){
	        		if((WeightTotal<=0)||(HeightTotal<=0)){
	        			Toast.makeText(ActivityLeanBodyMass.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
	        			return;
	        		}
	        		else{
	        			LBM= (float) ((1.10*WeightTotal)-((128*WeightTotal*WeightTotal)/(HeightTotal*HeightTotal)));
	        			LBM_percentage=((LBM/WeightTotal)*100);
	        		}
	        	}
	        	else{
	        		if((WeightTotal<=0)||(HeightTotal<=0)){
	        			Toast.makeText(ActivityLeanBodyMass.this, "Enter NonZero Values", Toast.LENGTH_SHORT).show();
	        			return;
	        		}
	        		else{
	        			LBM= (float) ((1.07*WeightTotal)-((148*WeightTotal*WeightTotal)/(HeightTotal*HeightTotal)));
	        			LBM_percentage=((LBM/WeightTotal)*100);
	        		}
	        	}
	        	
	        	String LBMString= Float.toString(LBM);
	        	String LBM_per_string= Float.toString(LBM_percentage);
	        	String LBMFinal="0.0";
	        	String LBM_per_Final="0.0";
	        	if(LBMString.length()>=5){
	        		LBMFinal= LBMString.substring(0, 5);
	        	}
	        	else{
	        		LBMFinal=LBMString;
	        	}
	        	if(LBM_per_string.length()>=5){
	        		LBM_per_Final= LBM_per_string.substring(0, 5);
	        	}
	        	else{
	        		LBM_per_Final=LBM_per_string;
	        	}
	        	
	        	Results.setText("Your Lean Mass: "+LBMFinal);
	        	percentage.setText("Lean Mass Percentage: "+LBM_per_Final+" %");

				Spanned text  = Html.fromHtml("<p>Your Lean Mass is <b><h1>"+LBMFinal+"</h1></b></p>" +
						"<p>And your Lean Mass percentage is <b><h1>"+LBM_per_Final+"%</h1></b></p>");

				SweetAlertDialog sDialog = new SweetAlertDialog(ActivityLeanBodyMass.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.NO_SHARE_FLAG);
				sDialog.setTitleText("Wholesome!");
				sDialog.setContentText(text);
				sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
				sDialog.show();

	        }
	 };

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
		ActivityLeanBodyMass.this.finish();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) ActivityLeanBodyMass.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(ActivityLeanBodyMass.this.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.dispatchTouchEvent(ev);
	}
}