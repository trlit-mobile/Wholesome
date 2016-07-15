package com.trl.wholesome;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ActivityMainDashboard extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgCalculators;
    private ImageView imgBurnDiary;
    private ImageView imgIntake;
    private ImageView imgCharts;

    private RelativeLayout relCalculators;
    private RelativeLayout relDiary;
    private RelativeLayout relIntake;
    private RelativeLayout relCharts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard_2);

        createDataBase();
        findViews();
    }

    private void findViews() {
        imgCalculators = (ImageView) findViewById(R.id.imgCalculators);
        imgBurnDiary = (ImageView) findViewById(R.id.imgBurnDiary);
        imgIntake = (ImageView) findViewById(R.id.imgIntake);
        imgCharts = (ImageView) findViewById(R.id.imgCharts);

        relCalculators = (RelativeLayout)findViewById(R.id.relInsider1);
        relIntake = (RelativeLayout)findViewById(R.id.relInsider2);
        relDiary = (RelativeLayout)findViewById(R.id.relInsider3);
        relCharts = (RelativeLayout)findViewById(R.id.relInsider4);

        relCalculators.setOnClickListener(this);
        relDiary.setOnClickListener(this);
        relIntake.setOnClickListener(this);
        relCharts.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relInsider1:
                Intent intentCalculators = new Intent(this, ActivityCalculators.class);
                startActivity(intentCalculators);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.relInsider3:
                Intent intentDiary = new Intent(this, ActivityDiaries.class);
                startActivity(intentDiary);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.relInsider2:
                Intent intentIntake = new Intent(this, ActivityIntakes.class);
                startActivity(intentIntake);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
            case R.id.relInsider4:
                Intent intentCharts = new Intent(this, ActivityGraph.class);
                startActivity(intentCharts);
                overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                break;
        }
    }


    private void createDataBase() {
        SQLiteDatabase db;
        db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists "
                + "caloriesburned_pro_favoutite_table"
                + "(activity_name text not null, activity_index number);");
        db.execSQL("create table if not exists "
                + "caloriesburned_pro_favoutite_intake_table"
                + "(name text not null, name_index number, category text, country_veg text, country_veg_index number);");
        db.execSQL("create table if not exists "
                + "caloriesburned_pro_activity_table"
                + "(activity_name text not null, met_value text);");

        db.execSQL("create table if not exists "
                + "caloriesburned_db_update_table"
                + "(is_db_updated number);");

        Cursor cIn = db.rawQuery("select * from caloriesburned_db_update_table", null);
        if (cIn.getCount() <= 0) {
            ContentValues cv1 = new ContentValues();
            cv1.put("is_db_updated", 0);
            db.insert("caloriesburned_db_update_table", null, cv1);
        }
        cIn.close();

        db.execSQL("create table if not exists " +
                "caloriesburned_pro_notes(id integer primary key autoincrement, mark number, " +
                "date text, calories real, desc text, week_of_year number, day_of_week text, year number)");
        db.execSQL("create table if not exists " +
                "caloriesburned_pro_intake_notes(id integer primary key autoincrement, mark number, " +
                "date text, calories real, desc text, week_of_year number, day_of_week text, year number)");
        db.execSQL("create table if not exists "
                + "CALORIES_PRO_REMEMBER_WEIGHT_TABLE"
                + "(idRem integer primary key autoincrement, weight text not null, weightUnit text not null);");

        db.execSQL("create table if not exists "
                + "COUNTRIES_PRO"
                + "(idCountry integer primary key autoincrement, countryName text not null);");
        db.execSQL("create table if not exists "
                + "BRITISH_FOOD_TABLE_PRO"
                + "(idBri integer primary key autoincrement, dishNameBri text not null, dishCaloriesBri text not null);");
        db.execSQL("create table if not exists "
                + "AUSTRALIAN_FOOD_TABLE_PRO"
                + "(idAus integer primary key autoincrement, dishNameAus text not null, dishCaloriesAus text not null);");
        db.execSQL("create table if not exists "
                + "CARIBBEAN_FOOD_TABLE_PRO"
                + "(idcar integer primary key autoincrement, dishNameCar text not null, dishCaloriesCar text not null);");
        db.execSQL("create table if not exists "
                + "CHINESE_FOOD_TABLE_PRO"
                + "(idChi integer primary key autoincrement, dishNameChi text not null, dishCaloriesChi text not null);");
        db.execSQL("create table if not exists "
                + "FRENCH_FOOD_TABLE_PRO"
                + "(idFre integer primary key autoincrement, dishNameFre text not null, dishCaloriesFre text not null);");
        db.execSQL("create table if not exists "
                + "GREEK_FOOD_TABLE_PRO"
                + "(idGre integer primary key autoincrement, dishNameGre text not null, dishCaloriesGre text not null);");
        db.execSQL("create table if not exists "
                + "INDIAN_FOOD_TABLE_PRO"
                + "(idInd integer primary key autoincrement, dishNameInd text not null, dishCaloriesInd text not null);");
        db.execSQL("create table if not exists "
                + "ITALIAN_FOOD_TABLE_PRO"
                + "(idIta integer primary key autoincrement, dishNameIta text not null, dishCaloriesIta text not null);");
        db.execSQL("create table if not exists "
                + "MEDITERRANEAN_FOOD_TABLE_PRO"
                + "(idMed integer primary key autoincrement, dishNameMed text not null, dishCaloriesMed text not null);");
        db.execSQL("create table if not exists "
                + "MEXICAN_FOOD_TABLE_PRO"
                + "(idMex integer primary key autoincrement, dishNameMex text not null, dishCaloriesMex text not null);");
        db.execSQL("create table if not exists "
                + "MORROCCAN_FOOD_TABLE_PRO"
                + "(idMor integer primary key autoincrement, dishNameMor text not null, dishCaloriesMor text not null);");
        db.execSQL("create table if not exists "
                + "SPANISH_FOOD_TABLE_PRO"
                + "(idSpa integer primary key autoincrement, dishNameSpa text not null, dishCaloriesSpa text not null);");
        db.execSQL("create table if not exists "
                + "THAI_FOOD_TABLE_PRO"
                + "(idTha integer primary key autoincrement, dishNameTha text not null, dishCaloriesTha text not null);");
        db.execSQL("create table if not exists "
                + "USA_FOOD_TABLE_PRO"
                + "(idUsa integer primary key autoincrement, dishNameUsa text not null, dishCaloriesUsa text not null);");
        db.execSQL("create table if not exists "
                + "VEGETABLES_CALORIES_PRO"
                + "(idVeg integer primary key autoincrement, vegName text not null, vegCalories text not null);");
        db.execSQL("create table if not exists "
                + "FRUIT_CALORIES_PRO"
                + "(idFruit integer primary key autoincrement, fruitName text not null, fruitCalories text not null);");

        db.close();
    }
}
