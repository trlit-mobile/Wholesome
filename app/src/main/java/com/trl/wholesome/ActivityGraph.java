package com.trl.wholesome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import java.util.Calendar;

/**
 * GraphViewDemo creates some dummy data to demonstrate the GraphView component.
 *
 */
public class ActivityGraph extends Activity {
	int weekYear=0;
	int currentYear=0;
	double caloriesBurnedArray[]= new double[]{0,0,0,0,0,0,0};
	double caloriesIntakeArray[]= new double[]{0,0,0,0,0,0,0};

	private boolean isRecordExist = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.activity_graphs);
		
		Calendar cal= Calendar.getInstance();
		currentYear= cal.get(Calendar.YEAR);
		weekYear= cal.get(Calendar.WEEK_OF_YEAR);
		
		SQLiteDatabase db;
		db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
		Cursor c1= db.rawQuery("select * from caloriesburned_pro_notes where year="+currentYear+" AND week_of_year="+weekYear+"", null);
		if(c1.getCount()>0){
			if(c1.moveToFirst()){
				do{
					int dayWeek= c1.getInt(c1.getColumnIndex("day_of_week"));
					double caloriesBurned= Double.parseDouble(c1.getString(c1.getColumnIndex("calories")));
					caloriesBurnedArray[dayWeek-1]= caloriesBurnedArray[dayWeek-1] + caloriesBurned;
				}while(c1.moveToNext());
			}
		}
		c1.close();
		int tempWeekyear= weekYear-1;
		if(tempWeekyear>=0){
			Cursor c2= db.rawQuery("select * from caloriesburned_pro_notes where year="+currentYear+" AND week_of_year="+tempWeekyear+"", null);
			if(c2.getCount()>0){
				if(c2.moveToFirst()){
					do{
						int dayWeek= c2.getInt(c2.getColumnIndex("day_of_week"));
						double caloriesBurned= Double.parseDouble(c2.getString(c2.getColumnIndex("calories")));
						if(dayWeek==1){
							caloriesBurnedArray[dayWeek-1]= caloriesBurnedArray[dayWeek-1] + caloriesBurned;
						}
					}while(c2.moveToNext());
				}
			}
			c2.close();
			
			Cursor c= db.rawQuery("select * from caloriesburned_pro_intake_notes where year="+currentYear+" AND week_of_year="+tempWeekyear+"", null);
			if(c.getCount()>0){
				if(c.moveToFirst()){
					do{
						int dayWeek= c.getInt(c.getColumnIndex("day_of_week"));
						double caloriesIntake= Double.parseDouble(c.getString(c.getColumnIndex("calories")));
						if(dayWeek==1){
							caloriesIntakeArray[dayWeek-1]= caloriesIntakeArray[dayWeek-1] + caloriesIntake;
						}
					}while(c.moveToNext());
				}
			}
			c.close();
		}
		
		
		Cursor c= db.rawQuery("select * from caloriesburned_pro_intake_notes where year="+currentYear+" AND week_of_year="+weekYear+"", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				do{
					int dayWeek= c.getInt(c.getColumnIndex("day_of_week"));
					double caloriesIntake= Double.parseDouble(c.getString(c.getColumnIndex("calories")));
					Log.d("dayWeek : "+dayWeek, "Calories : "+caloriesIntake+"");
					caloriesIntakeArray[dayWeek-1]= caloriesIntakeArray[dayWeek-1] + caloriesIntake;
				}while(c.moveToNext());
			}
		}
		c.close();
		db.close();

		for(int i=0;i<caloriesBurnedArray.length;i++){

			if(caloriesBurnedArray[i]!=0){
				isRecordExist = true;
				break;
			}
		}

		for(int i=0;i<caloriesIntakeArray.length;i++){
			if(caloriesIntakeArray[i]!=0){
				isRecordExist = true;
				break;
			}
		}

		if(!isRecordExist){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("Your diary doesn't have any burned/intake records exist for this week. Please, add records to check the burned vs intake curve.");
			builder1.setCancelable(true);

			builder1.setPositiveButton(
					"Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
							finish();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();
		}

		
		/*
		 * init series data
		 */
		// sin curve
		int num = 7;
		GraphViewData[] data = new GraphViewData[num];
		for (int i=0; i<caloriesIntakeArray.length; i++) {
			data[i] = new GraphViewData(i, caloriesIntakeArray[i]);//Math.sin(v));
		}
		GraphViewSeries seriesSin = new GraphViewSeries("Intake Curve", new GraphViewStyle(Color.rgb(200, 50, 00), 3), data);

		// cos curve
		data = new GraphViewData[num];
		for (int i=0; i<caloriesBurnedArray.length; i++) {
			data[i] = new GraphViewData(i, caloriesBurnedArray[i]);//Math.cos(v));
		}
		GraphViewSeries seriesCos = new GraphViewSeries("Calories Burned Curve", new GraphViewStyle(Color.rgb(90, 250, 00), 3), data);

		for(int i=0;i<caloriesBurnedArray.length;i++){
			Log.i("CaloriesBurned "+i, caloriesBurnedArray[i]+"");
		}
		for(int i=0;i<caloriesIntakeArray.length;i++){
			Log.i("CaloriesIntake "+i, caloriesIntakeArray[i]+"");
		}
		
		// graph with dynamically genereated horizontal and vertical labels
		LineGraphView graphView;
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		
		// graph with dynamically genereated horizontal and vertical labels
		graphView = new LineGraphView(
				this
				, ""
		);
		// add data
		graphView.addSeries(seriesCos);
		graphView.addSeries(seriesSin);
		// set legend
		graphView.setHorizontalLabels((new String[] {"Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}));
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setLegendWidth(200);
		// set view port, start=2, size=10
		//graphView.setViewPort(0, 10);
		graphView.setScrollable(true);
		graphView.setScalable(false);
		//layout = (LinearLayout) findViewById(R.id.graph2);
		layout.addView(graphView);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
		finish();
	}
}