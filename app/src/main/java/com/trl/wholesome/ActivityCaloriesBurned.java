package com.trl.wholesome;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.trl.wholesome.views.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityCaloriesBurned extends Activity implements View.OnClickListener {

    ImageView imgViewBack;
    boolean metric_weight = false;
    float WeightTotal = 0;
    float TimeTotal = 0;
    float metValue = 0;
    float cbc = 0;
    Spinner spinnerWeight, spinnerHours,spinnerMinutes,spinnerActivity;
    TextView Results_clear;
    Button btnCalculate;
    EditText WeightString;

    private ImageView imgShare;
    private ImageView imgGetFavorite;
    private ImageView imgAddToFavorite;

    String CBFinal = "0";

    private int selection =0;
    private ArrayList<String> favouriteActivities= new ArrayList<String>();
    private ArrayList<Integer> favouriteIndexList= new ArrayList<Integer>();

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calories_burned);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        init();

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.lbs_kg_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(adapter1);

        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner1_insider = (Spinner)findViewById(R.id.spinnerWeight);
                if(spinner1_insider.getSelectedItem().equals("Kg")){

                    metric_weight=false;
                }
                if(spinner1_insider.getSelectedItem().equals("Lbs")){

                    metric_weight=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(adapter2);


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.min_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(adapter3);


        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.activity_array, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapter4);
        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Spinner spinner4_insider = (Spinner)findViewById(R.id.spinnerActivity);
                if((spinner4_insider.getSelectedItem().equals("Sleeping"))){

                    metValue= (float) 0.80;
                }
                else if((spinner4_insider.getSelectedItem().equals("Writing"))||(spinner4_insider.getSelectedItem().equals("Talking on Phone"))||(spinner4_insider.getSelectedItem().equals("Sitting - Resting"))||(spinner4_insider.getSelectedItem().equals("Reading"))){

                    metValue= (float) 1.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Standing"))){

                    metValue= (float) 1.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Singing - Sitting"))||(spinner4_insider.getSelectedItem().equals("Sex - Foreplay"))||(spinner4_insider.getSelectedItem().equals("Playing Board Games"))||(spinner4_insider.getSelectedItem().equals("Card Playing"))){

                    metValue= (float) 1.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Studying"))){

                    metValue= (float) 1.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Singing - Standing"))||(spinner4_insider.getSelectedItem().equals("Touring / Travelling"))||(spinner4_insider.getSelectedItem().equals("Packing Suitcase"))||(spinner4_insider.getSelectedItem().equals("Driving"))||(spinner4_insider.getSelectedItem().equals("Walking slow(1-2 mi/hr)"))){

                    metValue= (float) 2.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Mild Stretching"))||(spinner4_insider.getSelectedItem().equals("Shopping"))||(spinner4_insider.getSelectedItem().equals("Ironing"))){

                    metValue= (float) 2.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Putting away Groceries"))||(spinner4_insider.getSelectedItem().equals("Push Stroller with Child"))||(spinner4_insider.getSelectedItem().equals("Horseback Riding - Walking"))||(spinner4_insider.getSelectedItem().equals("Hair Styling"))||(spinner4_insider.getSelectedItem().equals("Football - Playing Catch"))||(spinner4_insider.getSelectedItem().equals("Cooking"))||(spinner4_insider.getSelectedItem().equals("Croquet"))||(spinner4_insider.getSelectedItem().equals("Brush Teeth"))||(spinner4_insider.getSelectedItem().equals("Billards"))||(spinner4_insider.getSelectedItem().equals("Walking average pace(2-2.5 mi/hr)"))){

                    metValue= (float) 2.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Playing Piano"))||(spinner4_insider.getSelectedItem().equals("Housework"))||(spinner4_insider.getSelectedItem().equals("Hatha Yoga"))){

                    metValue= (float) 2.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Bicycling Stationary, very light"))||(spinner4_insider.getSelectedItem().equals("Surfing"))||(spinner4_insider.getSelectedItem().equals("Playing Guitar"))||(spinner4_insider.getSelectedItem().equals("Loading / Unloading Car"))||(spinner4_insider.getSelectedItem().equals("Frisbee Playing"))||(spinner4_insider.getSelectedItem().equals("Fishing"))||(spinner4_insider.getSelectedItem().equals("Bowling"))||(spinner4_insider.getSelectedItem().equals("Dancing(slow)"))||(spinner4_insider.getSelectedItem().equals("Golf, using power cart"))){

                    metValue= (float) 3.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Vacuuming"))||(spinner4_insider.getSelectedItem().equals("Snowmobiling"))||(spinner4_insider.getSelectedItem().equals("Lifting Weight - General"))||(spinner4_insider.getSelectedItem().equals("Archery"))){

                    metValue= (float) 3.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Weaving Cloth"))||(spinner4_insider.getSelectedItem().equals("Pilates - Beginner"))||(spinner4_insider.getSelectedItem().equals("Hang Gliding"))||(spinner4_insider.getSelectedItem().equals("Carpentry / Workshop"))||(spinner4_insider.getSelectedItem().equals("Carrying an Infant"))||(spinner4_insider.getSelectedItem().equals("Walking briskly(1 mi/20 min)"))||(spinner4_insider.getSelectedItem().equals("Weight lifting, water aerobics"))||(spinner4_insider.getSelectedItem().equals("Golf, not carrying clubs"))){

                    metValue= (float) 3.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Table tennis"))||(spinner4_insider.getSelectedItem().equals("Stretching"))||(spinner4_insider.getSelectedItem().equals("Racking Lawn"))||(spinner4_insider.getSelectedItem().equals("Paddle Boat"))||(spinner4_insider.getSelectedItem().equals("Coaching - Team sports"))){

                    metValue= (float) 3.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Gymnastics, general"))|(spinner4_insider.getSelectedItem().equals("Showering"))||(spinner4_insider.getSelectedItem().equals("Sex - Intercourse"))||(spinner4_insider.getSelectedItem().equals("Elder Care, Disabled Adult"))||(spinner4_insider.getSelectedItem().equals("Walking very briskly(1 mi/18 min)"))||(spinner4_insider.getSelectedItem().equals("Dancing(moderately fast)"))||(spinner4_insider.getSelectedItem().equals("Bicycling less than 10 mph, leisurely"))){

                    metValue= (float) 4.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Insanity Workout- Recovery"))||(spinner4_insider.getSelectedItem().equals("Painting"))||(spinner4_insider.getSelectedItem().equals("Situps / Crunches - Moderate"))||(spinner4_insider.getSelectedItem().equals("Mowing - Push"))||(spinner4_insider.getSelectedItem().equals("Mopping"))||(spinner4_insider.getSelectedItem().equals("Jumping jacks - Moderate"))||(spinner4_insider.getSelectedItem().equals("Canoeing 2mph"))||(spinner4_insider.getSelectedItem().equals("Calisthenic Exercise-Moderate"))||(spinner4_insider.getSelectedItem().equals("Basketball Shooting-Basket"))||(spinner4_insider.getSelectedItem().equals("Badminton"))){

                    metValue= (float) 4.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Pilates - Intermediate"))||(spinner4_insider.getSelectedItem().equals("Farming / Feeding Live Stock"))||(spinner4_insider.getSelectedItem().equals("Swimming Slow"))||(spinner4_insider.getSelectedItem().equals("Golf, carrying clubs"))){

                    metValue= (float) 4.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Skate Boarding"))||(spinner4_insider.getSelectedItem().equals("Softball / Baseball"))||(spinner4_insider.getSelectedItem().equals("Skating Moderate"))||(spinner4_insider.getSelectedItem().equals("Pushup- Moderate"))||(spinner4_insider.getSelectedItem().equals("Kayaking"))||(spinner4_insider.getSelectedItem().equals("Hunting"))||(spinner4_insider.getSelectedItem().equals("Cricket"))){

                    metValue= (float) 4.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Shake Weight Workout"))||(spinner4_insider.getSelectedItem().equals("Bicycling Stationary, light"))||(spinner4_insider.getSelectedItem().equals("Snorkeling"))||(spinner4_insider.getSelectedItem().equals("Power Yoga"))||(spinner4_insider.getSelectedItem().equals("Hopscotch / Dodgeball"))||(spinner4_insider.getSelectedItem().equals("Cleaning Gutters"))||(spinner4_insider.getSelectedItem().equals("Walking very briskly(1 mi/15 min)"))||(spinner4_insider.getSelectedItem().equals("Ashtanga Yoga"))||(spinner4_insider.getSelectedItem().equals("Tennis Most Doubles"))||(spinner4_insider.getSelectedItem().equals("Dancing(more rapid)"))){

                    metValue= (float) 5.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Garden"))){

                    metValue= (float) 5.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Shoveling Snow"))||(spinner4_insider.getSelectedItem().equals("Skiing - Water"))||(spinner4_insider.getSelectedItem().equals("Construction - Remodelling"))){

                    metValue= (float) 5.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Lifting Weight - Vigorous"))||(spinner4_insider.getSelectedItem().equals("Horse Grooming"))||(spinner4_insider.getSelectedItem().equals("Fencing"))||(spinner4_insider.getSelectedItem().equals("Boxing - Punching bag"))||(spinner4_insider.getSelectedItem().equals("Chop Wood"))||(spinner4_insider.getSelectedItem().equals("Aerobic Low Impact"))){

                    metValue= (float) 5.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Paint Ball"))||(spinner4_insider.getSelectedItem().equals("Walking up hill(3.5 mi/hour)"))||(spinner4_insider.getSelectedItem().equals("Swimming leisurely-not laps"))||(spinner4_insider.getSelectedItem().equals("Bicycling 10-11.9 mph, light"))||(spinner4_insider.getSelectedItem().equals("Roofing"))||(spinner4_insider.getSelectedItem().equals("Track and Field (High jump, Long jump)"))||(spinner4_insider.getSelectedItem().equals("Pilates - Advanced"))||(spinner4_insider.getSelectedItem().equals("Jogging Slow(1 mile/13-14 min)"))||(spinner4_insider.getSelectedItem().equals("Ice or roller skating"))||(spinner4_insider.getSelectedItem().equals("Tennis Doubles(if you run a lot)"))){

                    metValue= (float) 6.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Rearrange Furniture"))||(spinner4_insider.getSelectedItem().equals("Horseback Riding - Trotting"))){

                    metValue= (float) 6.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Zumba Low Intensity"))||(spinner4_insider.getSelectedItem().equals("Rowing Machine - Moderate"))||(spinner4_insider.getSelectedItem().equals("Racquetball Casual"))||(spinner4_insider.getSelectedItem().equals("Bikram / Hot Yoga"))||(spinner4_insider.getSelectedItem().equals("Basketball Officiating"))||(spinner4_insider.getSelectedItem().equals("Back Packing"))||(spinner4_insider.getSelectedItem().equals("Hiking"))||(spinner4_insider.getSelectedItem().equals("Aerobic High Impact"))){

                    metValue= (float) 6.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Insanity Workout- Cardio"))||(spinner4_insider.getSelectedItem().equals("Horseback Riding - Trotting"))){

                    metValue= (float) 6.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Bicycling Stationary, moderate"))||(spinner4_insider.getSelectedItem().equals("Soccer, Casual, General"))||(spinner4_insider.getSelectedItem().equals("Dancing(vigorous)"))||(spinner4_insider.getSelectedItem().equals("Some exercise apparatuses"))){

                    metValue= (float) 7.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Zumba Medium Intensity"))||(spinner4_insider.getSelectedItem().equals("Calisthenic Exercise-Vigorous"))){

                    metValue= (float) 7.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Situps / Crunches - Vigorous"))||(spinner4_insider.getSelectedItem().equals("Repelling"))||(spinner4_insider.getSelectedItem().equals("Jumping jacks - Vigorous"))||(spinner4_insider.getSelectedItem().equals("Hockey"))||(spinner4_insider.getSelectedItem().equals("Horseback Riding - Galloping"))||(spinner4_insider.getSelectedItem().equals("Basketball(half-court)"))){

                    metValue= (float) 7.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Swimming back stroke-general"))||(spinner4_insider.getSelectedItem().equals("Swimming side stroke-general"))||(spinner4_insider.getSelectedItem().equals("Running 5 mph(12-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Pushup- Vigorous"))||(spinner4_insider.getSelectedItem().equals("Lacrosse"))||(spinner4_insider.getSelectedItem().equals("Frisbee, Ultimate"))||(spinner4_insider.getSelectedItem().equals("Bicycling 12-13.9 mph, moderate"))||(spinner4_insider.getSelectedItem().equals("Swimming laps moderate to fast"))||(spinner4_insider.getSelectedItem().equals("Aerobic calisthenics"))||(spinner4_insider.getSelectedItem().equals("Jogging(1 mile/12 min)"))||(spinner4_insider.getSelectedItem().equals("Volleyball Beach"))){

                    metValue= (float) 8.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Vinyasa Yoga"))||(spinner4_insider.getSelectedItem().equals("Skiing - DownHill"))||(spinner4_insider.getSelectedItem().equals("Rowing Machine - Vigorous"))||(spinner4_insider.getSelectedItem().equals("Football - Touch"))){

                    metValue= (float) 8.25;
                }
                else if((spinner4_insider.getSelectedItem().equals("Zumba High Intensity"))||(spinner4_insider.getSelectedItem().equals("Aerobic step, 6-8 inch step"))){

                    metValue= (float) 8.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Skating Vigorous"))||(spinner4_insider.getSelectedItem().equals("Canoeing 4mph"))){

                    metValue= (float) 8.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running cross-country"))||(spinner4_insider.getSelectedItem().equals("Running 5.2 mph(11.5-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Football - Full Contact"))){

                    metValue= (float) 9.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Rope Jumping"))||(spinner4_insider.getSelectedItem().equals("Racquetball Competitive"))||(spinner4_insider.getSelectedItem().equals("Judo - Martial Arts"))||(spinner4_insider.getSelectedItem().equals("Tennis Singles, Squash"))){

                    metValue= (float) 9.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Tae Bo"))||(spinner4_insider.getSelectedItem().equals("Bicycling Stationary, vigorous"))||(spinner4_insider.getSelectedItem().equals("Swimming breast stroke-general"))||(spinner4_insider.getSelectedItem().equals("Bicycling 14-15.9 mph, vigorous"))||(spinner4_insider.getSelectedItem().equals("Aerobic step, 10-12 inch step"))||(spinner4_insider.getSelectedItem().equals("Rugby"))||(spinner4_insider.getSelectedItem().equals("Running 6 mph(10-minute mile)"))){

                    metValue= (float) 10.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Rock Climbing"))||(spinner4_insider.getSelectedItem().equals("Basketball(full-court)"))){

                    metValue= (float) 10.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 6.7 mph(9-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Skiing - CrossCountry"))||(spinner4_insider.getSelectedItem().equals("Elliptical Trainer"))){

                    metValue= (float) 10.75;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 7 mph(8.5-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Swimming butterfly-general"))){

                    metValue= (float) 11.0;;
                }
                else if((spinner4_insider.getSelectedItem().equals("Handball"))||(spinner4_insider.getSelectedItem().equals("Boxing - in Ring"))){

                    metValue= (float) 11.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 7.5 mph(8-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Bicycling Stationary, very vigorous"))||(spinner4_insider.getSelectedItem().equals("Bicycling 16-19 mph, racing"))){

                    metValue= (float) 12.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Insanity Workout- High Intensity"))){

                    metValue= (float) 13.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 8 mph(7.5-minute mile)"))){

                    metValue= (float) 13.5;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 8.6 mph(7-minute mile)"))){

                    metValue= (float) 14.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 9 mph(6.5-minute mile)"))||(spinner4_insider.getSelectedItem().equals("Running up stairs"))){

                    metValue= (float) 15.0;
                }
                else if((spinner4_insider.getSelectedItem().equals("Bicycling 20-25 mph, racing"))||(spinner4_insider.getSelectedItem().equals("Running 10 mph(6-minute mile)"))){

                    metValue= (float) 16;
                }
                else if((spinner4_insider.getSelectedItem().equals("Running 10.6 mph(5.5-minute mile)"))){

                    metValue= (float) 18.0;
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
        spinnerWeight = (Spinner) findViewById(R.id.spinnerWeight);
        spinnerHours = (Spinner) findViewById(R.id.spinnerHours);
        spinnerMinutes= (Spinner) findViewById(R.id.spinnerMinutes);
        spinnerActivity= (Spinner) findViewById(R.id.spinnerActivity);
        Results_clear = (TextView) findViewById(R.id.txtTotalCaloriesBurnedResult);
        btnCalculate= (Button) findViewById(R.id.btnCalculate);
        WeightString= (EditText)findViewById(R.id.edtWeight);
        imgGetFavorite= (ImageView) findViewById(R.id.imgViewGetFav);
        imgAddToFavorite= (ImageView) findViewById(R.id.imageFavorite);
        imgShare= (ImageView) findViewById(R.id.imageShare);

        imgViewBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
        imgAddToFavorite.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgGetFavorite.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgViewBack:

                onBackPressed();
                break;

            case R.id.btnCalculate:


                Results_clear.setText("");

                //Weight Calculation

                if (WeightString.getText().length() == 0){
                    Toast.makeText(ActivityCaloriesBurned.this, "Enter Weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    WeightTotal = Float.parseFloat(WeightString.getText().toString());
                }
                if(metric_weight){
                    WeightTotal = (float) (WeightTotal*0.45359237);
                }
                //WeightTotal = Math.round(WeightTotal);

                //Time Calculation
                Spinner time_hour= (Spinner)findViewById(R.id.spinnerHours);
                String hours= time_hour.getSelectedItem().toString();
                float h = Float.parseFloat(hours);

                Spinner time_min= (Spinner)findViewById(R.id.spinnerMinutes);
                String min= time_min.getSelectedItem().toString();
                float m = Float.parseFloat(min);

                TimeTotal= (h*60)+ m;

                if(TimeTotal==0){
                    Toast.makeText(ActivityCaloriesBurned.this, "Enter Proper Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                String CBValue=null;
                CBFinal = null;
                //Calculate Calories Burned
                cbc= (float)((TimeTotal)*((metValue*3.5*WeightTotal)/200));
                CBValue=Float.toString(cbc);

                if(CBValue.length()>=6){
                    CBFinal=CBValue.substring(0, 6);
                }
                else
                {
                    CBFinal= CBValue;
                }

                TextView Results = (TextView) findViewById(R.id.txtTotalCaloriesBurned);
                Results.setText("You have burned "+CBFinal+" calories.");

                Spanned text  = Html.fromHtml("<p>You have burned <b><h1>"+CBFinal+" calories</h1></b></p>");


                SweetAlertDialog sDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.CALORIES_BURNED_SHARE_FLAG);
                sDialog.setTitleText("Wholesome!");
                sDialog.setContentText(text);
                sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
                sDialog.setShareBurnDiaryVisible(true);
                sDialog.setShareFBVisible(true);
                sDialog.show();
                break;

            case R.id.imageFavorite:
                addToFavorite();
                break;
            /*case R.id.imageShare:
                shareClick();
                break;*/
            case R.id.imgViewGetFav:
                getFavorite();
                break;

        }
    }

    private void getFavorite(){
        SQLiteDatabase db3;
        db3 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        String sql = "Select * from caloriesburned_pro_favoutite_table";
        Cursor c= db3.rawQuery(sql, null);
        favouriteActivities.clear();
        favouriteIndexList.clear();
        if(c.getCount()<=0){
            Toast.makeText(ActivityCaloriesBurned.this, "Favourite list is empty", Toast.LENGTH_LONG).show();
            c.close();
            db3.close();
            return;
        }
        else{
            int i=0;
            if(c.moveToFirst()){
                do{
                    String act= c.getString(c.getColumnIndex("activity_name"));
                    int index= c.getInt(c.getColumnIndex("activity_index"));
                    favouriteActivities.add(i,act);
                    favouriteIndexList.add(i,index);
                    i++;
                }while(c.moveToNext());
            }
            c.close();
            CharSequence items[]= new CharSequence[favouriteActivities.size()];
            favouriteActivities.toArray(items);

            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCaloriesBurned.this);
            alert.setTitle("Favorite Activities");
            alert.setSingleChoiceItems(items, selection, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    selection= which;
                }
            });
            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    int setSelection= favouriteIndexList.get(selection);
                    spinnerActivity.setSelection(setSelection);
                }
            });
            alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Log.d("Selected", ""+selection);
                    SQLiteDatabase db5;
                    db5 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                    db5.delete("caloriesburned_pro_favoutite_table", "activity_index="+favouriteIndexList.get(selection), null);
                    db5.close();
                    selection=0;
                }
            });
            alert.show();

            db3.close();
        }
    }

    private void addToFavorite(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCaloriesBurned.this);
        alert.setTitle("Wholesome");
        alert.setMessage("Do you want to add this activity to favourite ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                SQLiteDatabase db2;
                String activity = spinnerActivity.getSelectedItem().toString();
                int position_index= spinnerActivity.getSelectedItemPosition();
                db2 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                activity= activity.replace("'", "\"");
                Cursor c_new= db2.rawQuery("select * from caloriesburned_pro_favoutite_table where activity_name='"+activity+"'", null);
                if(c_new.getCount()>0){
                    Toast.makeText(ActivityCaloriesBurned.this, "Activity already exist in favourite list", Toast.LENGTH_LONG).show();
                    c_new.close();
                    db2.close();
                    return;
                }
                else{
                    ContentValues cv= new ContentValues();
                    cv.put("activity_name", activity);
                    cv.put("activity_index", position_index);
                    db2.insert("caloriesburned_pro_favoutite_table", null, cv);
                    Toast.makeText(ActivityCaloriesBurned.this, "Activity added to favourite.", Toast.LENGTH_LONG).show();
                    c_new.close();
                    db2.close();
                }
            }
        });
        alert.setNegativeButton("No", null);
        alert.show();
    }

    public void shareFBClick(){
        String caloriesBurned= CBFinal;
        final String actName= spinnerActivity.getSelectedItem().toString();
        String hrs= spinnerHours.getSelectedItem().toString();
        final String min= spinnerMinutes.getSelectedItem().toString();
        Date d= new Date();
        SimpleDateFormat format3 = new SimpleDateFormat("d MMM yyyy");
        String dispDate= format3.format(d);
        SimpleDateFormat format4 = new SimpleDateFormat("MM-dd-yyyy");
        final String dispDate2= format4.format(d);
        Calendar cal= Calendar.getInstance();
        final int weekYear= cal.get(Calendar.WEEK_OF_YEAR);
        final int yearM= cal.get(Calendar.YEAR);
        final int dayWeek= cal.get(Calendar.DAY_OF_WEEK);
        /*if(caloriesBurned==null || caloriesBurned.equals("")){
            caloriesBurned="Calories Burned : 0";
        }*/

        final String strCaloriesBurned = caloriesBurned;
        final String time  = hrs+" Hours "+min+" Minutes";
        final String activityName  = actName;

        final String dispStr= "Date : "+dispDate+"\n" +
                "Activity : "+actName+"\n" +
                "Time Duration :"+hrs+" Hours "+min+" Minutes"+"\n" +
                caloriesBurned+"\n";

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Wholesome")
                    .setContentDescription(
                            "I have burned "+strCaloriesBurned+" calories by "+activityName+" for "+time+" hours. What's yours..?\n" +
                                    "Check by downloading Wholesome app using http://bit.ly/29T5lYl link.")

                    .setContentUrl(Uri.parse("https://www.facebook.com/Wholesome-642595235905404/"))

                     //.setContentUrl(Uri.parse("https://developers.facebook.com/apps/627870190698632/"))
                    //.setContentUrl(Uri.parse("http://bit.ly/29T5lYl"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    public void shareBurnDiaryClick(){
        String caloriesBurned= CBFinal;
        final String actName= spinnerActivity.getSelectedItem().toString();
        String hrs= spinnerHours.getSelectedItem().toString();
        final String min= spinnerMinutes.getSelectedItem().toString();
        Date d= new Date();
        SimpleDateFormat format3 = new SimpleDateFormat("d MMM yyyy");
        String dispDate= format3.format(d);
        SimpleDateFormat format4 = new SimpleDateFormat("MM-dd-yyyy");
        final String dispDate2= format4.format(d);
        Calendar cal= Calendar.getInstance();
        final int weekYear= cal.get(Calendar.WEEK_OF_YEAR);
        final int yearM= cal.get(Calendar.YEAR);
        final int dayWeek= cal.get(Calendar.DAY_OF_WEEK);
        /*if(caloriesBurned==null || caloriesBurned.equals("")){
            caloriesBurned="Calories Burned : 0";
        }*/

        final String strCaloriesBurned = caloriesBurned;
        final String time  = hrs+" Hours "+min+" Minutes";
        final String activityName  = actName;

        final String dispStr= "Date : "+dispDate+"\n" +
                "Activity : "+actName+"\n" +
                "Time Duration :"+hrs+" Hours "+min+" Minutes"+"\n" +
                caloriesBurned+"\n";

        double calories= Float.parseFloat(CBFinal);
        SQLiteDatabase DB;
        DB = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        // String date =""+(mMonth+1)+"-"+mDay+"-"+mYear;
        ContentValues cv = new ContentValues();
        cv.put("mark",0);
        cv.put("date",dispDate2);
        cv.put("calories", calories);
        cv.put("desc",actName);
        cv.put("year",yearM);
        cv.put("week_of_year", weekYear);
        cv.put("day_of_week", dayWeek);
        DB.insert("caloriesburned_pro_notes",null,cv);
        DB.close();
        Toast.makeText(ActivityCaloriesBurned.this, "Added to Burn Diary", Toast.LENGTH_SHORT).show();
    }

    /*public void shareClick(){
        String caloriesBurned= CBFinal;
        final String actName= spinnerActivity.getSelectedItem().toString();
        String hrs= spinnerHours.getSelectedItem().toString();
        final String min= spinnerMinutes.getSelectedItem().toString();
        Date d= new Date();
        SimpleDateFormat format3 = new SimpleDateFormat("d MMM yyyy");
        String dispDate= format3.format(d);
        SimpleDateFormat format4 = new SimpleDateFormat("MM-dd-yyyy");
        final String dispDate2= format4.format(d);
        Calendar cal= Calendar.getInstance();
        final int weekYear= cal.get(Calendar.WEEK_OF_YEAR);
        final int yearM= cal.get(Calendar.YEAR);
        final int dayWeek= cal.get(Calendar.DAY_OF_WEEK);
        *//*if(caloriesBurned==null || caloriesBurned.equals("")){
            caloriesBurned="Calories Burned : 0";
        }*//*

        final String dispStr= "Date : "+dispDate+"\n" +
                "Activity : "+actName+"\n" +
                "Time Duration :"+hrs+" Hours "+min+" Minutes"+"\n" +
                caloriesBurned+"\n";

        final Dialog dialogA = new Dialog(ActivityCaloriesBurned.this);
        dialogA.setContentView(R.layout.share_dialog);
        dialogA.show();
        final ImageView imgFB= (ImageView)dialogA.findViewById(R.id.imgDailyDairyFacebook);
        final ImageView imgDiary= (ImageView) dialogA.findViewById(R.id.imDailyDiaryabuttongeShare);
        imgDiary.setBackgroundResource(R.drawable.burn_diary_button);

        final String strCaloriesBurned = caloriesBurned;
        final String time  = hrs+" Hours "+min+" Minutes";
        final String activityName  = actName;

        imgFB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Wholesome")
                            .setContentDescription(
                                    "I have burned "+strCaloriesBurned+" calories by "+activityName+" for "+time+" hours. What's yours..?\n" +
                                            "Check by downloading Wholesome app using below link.")

                            .setContentUrl(Uri.parse("https://developers.facebook.com/apps/627870190698632/dashboard/"))
                            .build();

                    shareDialog.show(linkContent);
                }
                dialogA.dismiss();


            }
        });
        imgDiary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCaloriesBurned.this);
                alert.setTitle("Wholesome");
                alert.setMessage("Do you want to add this activity in Burn Diary?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        // TODO Auto-generated method stub
                        double cal= Float.parseFloat(CBFinal);
                        SQLiteDatabase DB;
                        DB = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        // String date =""+(mMonth+1)+"-"+mDay+"-"+mYear;
                        ContentValues cv = new ContentValues();
                        cv.put("mark",0);
                        cv.put("date",dispDate2);
                        cv.put("calories", cal);
                        cv.put("desc",actName);
                        cv.put("year",yearM);
                        cv.put("week_of_year", weekYear);
                        cv.put("day_of_week", dayWeek);
                        DB.insert("caloriesburned_pro_notes",null,cv);
                        DB.close();
                        Toast.makeText(ActivityCaloriesBurned.this, "Added to Burn Diary", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        dialogA.cancel();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        // TODO Auto-generated method stub
                        dialog1.dismiss();
                        dialogA.cancel();
                    }
                });
                alert.show();
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        ActivityCaloriesBurned.this.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ActivityCaloriesBurned.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivityCaloriesBurned.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}