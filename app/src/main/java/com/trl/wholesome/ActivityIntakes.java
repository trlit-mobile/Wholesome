package com.trl.wholesome;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trl.wholesome.views.MyProgressDialog;
import com.trl.wholesome.views.SweetAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mitesh on 28/5/16.
 */
public class ActivityIntakes extends AppCompatActivity {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    public static final int REQUEST_ADD_VEG_FRUIT = 3;
    public static final int REQUEST_EDIT_VEG_FRUIT = 4;

    TextView txtTitleCountryVeg, txtTitleName, txtTitleCalServing;
    Spinner spinnerCountryVeg, spinnerDishName, spinnerCategory;
    TextView txtAnsCalories;
    String country_array[];
    String britishDishName_array[], caribbeanDishName_array[], chineseDishName_array[],
            frenchDishName_array[], greekDishName_array[], indianDishName_array[], italianDishName_array[],
            mediterraneanDishName_array[], mexicanDishName_array[], morroccanDishName_array[], spanishDishName_array[],
            thaiDishName_array[], usaDishName_array[], australianDishName_array[];

    String britishCalories_array[], caribbeanCalories_array[], chineseCalories_array[],
            frenchCalories_array[], greekCalories_array[], indianCalories_array[], italianCalories_array[],
            mediterraneanCalories_array[], mexicanCalories_array[], morroccanCalories_array[], spanishCalories_array[],
            thaiCalories_array[], usaCalories_array[], australianCalories_array[];

    ImageView btnAddtoFav, btnFavorite, btnShare;

    public static ArrayList<String> countryList = new ArrayList<String>();

    public static ArrayList<String> briDishNameList = new ArrayList<String>();
    public static ArrayList<String> ausDishNameList = new ArrayList<String>();
    public static ArrayList<String> carDishNameList = new ArrayList<String>();
    public static ArrayList<String> chiDishNameList = new ArrayList<String>();
    public static ArrayList<String> freDishNameList = new ArrayList<String>();
    public static ArrayList<String> greDishNameList = new ArrayList<String>();
    public static ArrayList<String> indDishNameList = new ArrayList<String>();
    public static ArrayList<String> itaDishNameList = new ArrayList<String>();
    public static ArrayList<String> medDishNameList = new ArrayList<String>();
    public static ArrayList<String> mexDishNameList = new ArrayList<String>();
    public static ArrayList<String> morDishNameList = new ArrayList<String>();
    public static ArrayList<String> spaDishNameList = new ArrayList<String>();
    public static ArrayList<String> thaDishNameList = new ArrayList<String>();
    public static ArrayList<String> usaDishNameList = new ArrayList<String>();

    public static ArrayList<String> briDishCalList = new ArrayList<String>();
    public static ArrayList<String> ausDishCalList = new ArrayList<String>();
    public static ArrayList<String> carDishCalList = new ArrayList<String>();
    public static ArrayList<String> chiDishCalList = new ArrayList<String>();
    public static ArrayList<String> freDishCalList = new ArrayList<String>();
    public static ArrayList<String> greDishCalList = new ArrayList<String>();
    public static ArrayList<String> indDishCalList = new ArrayList<String>();
    public static ArrayList<String> itaDishCalList = new ArrayList<String>();
    public static ArrayList<String> medDishCalList = new ArrayList<String>();
    public static ArrayList<String> mexDishCalList = new ArrayList<String>();
    public static ArrayList<String> morDishCalList = new ArrayList<String>();
    public static ArrayList<String> spaDishCalList = new ArrayList<String>();
    public static ArrayList<String> thaDishCalList = new ArrayList<String>();
    public static ArrayList<String> usaDishCalList = new ArrayList<String>();

    public static Map<String, String> briSortedMap = new TreeMap<String, String>();
    public static Map<String, String> ausSortedMap = new TreeMap<String, String>();
    public static Map<String, String> carSortedMap = new TreeMap<String, String>();
    public static Map<String, String> chiSortedMap = new TreeMap<String, String>();
    public static Map<String, String> freSortedMap = new TreeMap<String, String>();
    public static Map<String, String> greSortedMap = new TreeMap<String, String>();
    public static Map<String, String> indSortedMap = new TreeMap<String, String>();
    public static Map<String, String> itaSortedMap = new TreeMap<String, String>();
    public static Map<String, String> medSortedMap = new TreeMap<String, String>();
    public static Map<String, String> mexSortedMap = new TreeMap<String, String>();
    public static Map<String, String> morSortedMap = new TreeMap<String, String>();
    public static Map<String, String> spaSortedMap = new TreeMap<String, String>();
    public static Map<String, String> thaSortedMap = new TreeMap<String, String>();
    public static Map<String, String> usaSortedMap = new TreeMap<String, String>();

    String VegName_array[], FruName_array[];
    String VegCal_array[], FruCal_array[];

    public static Map<String, String> vegSortedMap = new TreeMap<String, String>();
    public static Map<String, String> fruSortedMap = new TreeMap<String, String>();

    public static ArrayList<String> vegItemNameList = new ArrayList<String>();
    public static ArrayList<String> fruItemNameList = new ArrayList<String>();

    public static ArrayList<String> vegItemCalList = new ArrayList<String>();
    public static ArrayList<String> fruItemCalList = new ArrayList<String>();

    MyProgressDialog pdLoading = null;

    //DataInsertTimer insert_timer = new DataInsertTimer(500, 100);
    //DataInsertTimer2 insert_timer_2 = new DataInsertTimer2(500, 100);
    //InitialTimer initial_timer = new InitialTimer(500, 100);
    int selection = 0;
    ArrayList<String> favouriteActivities = new ArrayList<String>();
    ArrayList<Integer> favouriteIndexList = new ArrayList<Integer>();
    ArrayList<String> favCountryVegList = new ArrayList<String>();
    ArrayList<Integer> favCountryVegIndexList = new ArrayList<Integer>();
    ArrayList<String> favCategoryList = new ArrayList<String>();
    int countryVegSelection = 0, dishSelection = 0;
    boolean FROM_FAVORITE = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageView imgBack;

    private Button btnCalculate;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public void shareToFB(){

        String text  = null;
        if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
            text  = "You can get "+txtAnsCalories.getText().toString()+" calories (kcal) from 1 serving of "+spinnerDishName.getSelectedItem().toString()+".\n" +
                    spinnerDishName.getSelectedItem().toString()+" is food dish belongs to country "+spinnerCountryVeg.getSelectedItem().toString()+".\n" +
                    "You can check calories get from your favorite food by using Wholesome app http://bit.ly/29T5lYl";
        }
        else if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Vegetable")) {
            text  = "Vegetables like "+spinnerDishName.getSelectedItem().toString()+" contains "+txtAnsCalories.getText().toString()+" calories per 100 gms.\n" +
                    "You can check calories get from your favorite food by using Wholesome app http://bit.ly/29T5lYl";
        }
        else if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Fruit")) {
            text  = "Fruits like "+spinnerDishName.getSelectedItem().toString()+" contains "+txtAnsCalories.getText().toString()+" calories.\n" +
                    "You can check calories get from your favorite food by using Wholesome app http://bit.ly/29T5lYl";
        }



        /*Date d = new Date();
        SimpleDateFormat format3 = new SimpleDateFormat("d MMM yyyy");
        String dispDate = format3.format(d);
        SimpleDateFormat format4 = new SimpleDateFormat("MM-dd-yyyy");
        final String dispDate2 = format4.format(d);
        Calendar cal = Calendar.getInstance();
        final int weekYear = cal.get(Calendar.WEEK_OF_YEAR);
        final int yearM = cal.get(Calendar.YEAR);
        final int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        String calories = "", strItem = "";
        if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
            strItem = "Dish : " + spinnerDishName.getSelectedItem().toString() + "\n" +
                    "Country : " + spinnerCountryVeg.getSelectedItem().toString();
            calories = "Calries (kcal)/Serving : " + txtAnsCalories.getText().toString();
        } else {
            if (spinnerCountryVeg.getSelectedItem().toString().equalsIgnoreCase("Vegetable")) {
                strItem = "Vegetable : " + spinnerDishName.getSelectedItem().toString();
                calories = "Calries / 100g : " + txtAnsCalories.getText().toString();
            } else {
                strItem = "Fruit : " + spinnerDishName.getSelectedItem().toString();
                calories = "Calries : " + txtAnsCalories.getText().toString();
            }
        }
        final String dispStr = "Date : " + dispDate + "\n" +
                strItem + "\n" +
                calories;*/

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Wholesome")
                    .setContentDescription(text)

                    .setContentUrl(Uri.parse("https://www.facebook.com/Wholesome-642595235905404/"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    public void shareToIntakeDiary(){

        Date d = new Date();
        SimpleDateFormat format3 = new SimpleDateFormat("d MMM yyyy");
        String dispDate = format3.format(d);
        SimpleDateFormat format4 = new SimpleDateFormat("MM-dd-yyyy");
        final String dispDate2 = format4.format(d);
        Calendar cal = Calendar.getInstance();
        final int weekYear = cal.get(Calendar.WEEK_OF_YEAR);
        final int yearM = cal.get(Calendar.YEAR);
        final int dayWeek = cal.get(Calendar.DAY_OF_WEEK);

        SQLiteDatabase DB;
        DB = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        // String date =""+(mMonth+1)+"-"+mDay+"-"+mYear;
        ContentValues cv = new ContentValues();
        cv.put("mark", 0);
        cv.put("date", dispDate2);
        cv.put("calories", txtAnsCalories.getText().toString());
        cv.put("desc", spinnerDishName.getSelectedItem().toString());
        cv.put("week_of_year", weekYear);
        cv.put("year", yearM);
        cv.put("day_of_week", dayWeek);
        DB.insert("caloriesburned_pro_intake_notes", null, cv);
        DB.close();
        Toast.makeText(ActivityIntakes.this, "Added to Intake Diary.", Toast.LENGTH_SHORT).show();
    }


    private class PrepareListAsync extends AsyncTask<Void, Integer, String>
    {
        protected void onPreExecute (){
            Log.d("PreExceute","On pre Exceute......");
            pdLoading = new MyProgressDialog(ActivityIntakes.this);
            pdLoading.setMessage("Please, wait... Fetching data...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        protected String doInBackground(Void...arg0) {
            Log.d("DoINBackGround","On doInBackground...");
            dataInsert1();
            dataInsert2();

            return "You are at PostExecute";
        }



        protected void onPostExecute(String result) {
            if (pdLoading != null) {
                if (pdLoading.isShowing()) {
                    pdLoading.dismiss();
                }
                pdLoading = null;
            }
            ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, countryList);
            adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCountryVeg.setAdapter(adapterCountry);
            spinnerCategory.setSelection(0);

            clickEvents();

        }
    }

    private void clickEvents(){

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        btnFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SQLiteDatabase db3;
                db3 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                String sql = "Select * from caloriesburned_pro_favoutite_intake_table";
                Cursor c = db3.rawQuery(sql, null);
                favouriteActivities.clear();
                favouriteIndexList.clear();
                favCategoryList.clear();
                favCountryVegIndexList.clear();
                favCountryVegList.clear();
                countryVegSelection = 0;
                dishSelection = 0;
                if (c.getCount() <= 0) {
                    Toast.makeText(ActivityIntakes.this, "Favourite list is empty", Toast.LENGTH_LONG).show();
                    c.close();
                    db3.close();
                    return;
                } else {
                    int i = 0;
                    if (c.moveToFirst()) {
                        do {
                            String act = c.getString(c.getColumnIndex("name"));
                            int index = c.getInt(c.getColumnIndex("name_index"));
                            String countryVeg = c.getString(c.getColumnIndex("country_veg"));
                            int countryVeg_index = c.getInt(c.getColumnIndex("country_veg_index"));
                            String category = c.getString(c.getColumnIndex("category"));
                            favouriteActivities.add(i, act);
                            favouriteIndexList.add(i, index);
                            favCategoryList.add(i, category);
                            favCountryVegIndexList.add(i, countryVeg_index);
                            favCountryVegList.add(i, countryVeg);
                            i++;
                        } while (c.moveToNext());
                    }
                    c.close();
                    CharSequence items[] = new CharSequence[favouriteActivities.size()];
                    favouriteActivities.toArray(items);

                    AlertDialog.Builder alert = new AlertDialog.Builder(ActivityIntakes.this);
                    alert.setTitle("Favorite Intakes");
                    alert.setSingleChoiceItems(items, selection, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            selection = which;
                        }
                    });
                    alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            FROM_FAVORITE = true;
                            countryVegSelection = favCountryVegIndexList.get(selection);
                            dishSelection = favouriteIndexList.get(selection);
                            if (favCategoryList.get(selection).equalsIgnoreCase("Dishes")) {
                                if (spinnerCategory.getSelectedItemPosition() == 0) {
                                    if (spinnerCountryVeg.getSelectedItemPosition() == countryVegSelection) {
                                        spinnerDishName.setSelection(dishSelection);
                                        FROM_FAVORITE = false;
                                    } else {
                                        spinnerCountryVeg.setSelection(countryVegSelection);
                                    }
                                } else {
                                    spinnerCategory.setSelection(0);
                                }
                            } else {
                                if (spinnerCategory.getSelectedItemPosition() == 1) {
                                    if (spinnerCountryVeg.getSelectedItemPosition() == countryVegSelection) {
                                        FROM_FAVORITE = false;
                                        spinnerDishName.setSelection(dishSelection);
                                    } else {
                                        spinnerCountryVeg.setSelection(countryVegSelection);
                                    }
                                } else {
                                    spinnerCategory.setSelection(1);
                                }
                                spinnerCategory.setSelection(1);
                            }

                        }
                    });
                    alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Log.d("Selected", "" + selection);
                            SQLiteDatabase db5;
                            db5 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                            db5.delete("caloriesburned_pro_favoutite_intake_table",
                                    "name_index=" + favouriteIndexList.get(selection) + " AND name='" + favouriteActivities.get(selection) + "' " +
                                            "AND country_veg='" + favCountryVegList.get(selection) + "'", null);
                            db5.close();
                            selection = 0;
                        }
                    });
                    alert.show();

                    db3.close();
                }
            }
        });

        btnAddtoFav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityIntakes.this);
                alert.setTitle("Wholesome");
                alert.setMessage("Do you want to add this intake to favourite ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        SQLiteDatabase db2;
                        String category = spinnerCategory.getSelectedItem().toString();
                        String countryVeg = spinnerCountryVeg.getSelectedItem().toString();
                        int countryVeg_index = spinnerCountryVeg.getSelectedItemPosition();
                        String name = spinnerDishName.getSelectedItem().toString();
                        int name_index = spinnerDishName.getSelectedItemPosition();

                        db2 = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        name = name.replace("'", "\"");
                        Cursor c_new = db2.rawQuery("select * from caloriesburned_pro_favoutite_intake_table where name='" + name + "' AND name_index=" + name_index + "", null);
                        if (c_new.getCount() > 0) {
                            Toast.makeText(ActivityIntakes.this, "Already exist in favourite list", Toast.LENGTH_LONG).show();
                            c_new.close();
                            db2.close();
                            return;
                        } else {
                            ContentValues cv = new ContentValues();
                            cv.put("name", name);
                            cv.put("name_index", name_index);
                            cv.put("category", category);
                            cv.put("country_veg", countryVeg);
                            cv.put("country_veg_index", countryVeg_index);
                            db2.insert("caloriesburned_pro_favoutite_intake_table", null, cv);
                            Toast.makeText(ActivityIntakes.this, "Added to favourite.", Toast.LENGTH_LONG).show();
                            c_new.close();
                            db2.close();
                        }
                    }
                });
                alert.setNegativeButton("No", null);
                alert.show();
            }
        });


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
                    ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, countryList);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                    spinnerCountryVeg.setSelection(0);
                    txtTitleCountryVeg.setText("Country");
                    txtTitleName.setText("Dish Name");
                    txtTitleCalServing.setText("Calories (kcal)/Serving :  ");
                } else {
                    ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(
                            ActivityIntakes.this, R.array.vf_array, android.R.layout.simple_spinner_item);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                    spinnerCountryVeg.setSelection(0);
                    txtTitleCountryVeg.setText("Vegetable / Fruit");
                    txtTitleName.setText("Name");
                    txtTitleCalServing.setText("Calories / 100g :  ");
                }
                if (FROM_FAVORITE) {
                    spinnerCountryVeg.setSelection(countryVegSelection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinnerCountryVeg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
                    if (spinnerCountryVeg.getSelectedItem().toString().equals("Australia")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, ausDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(ausDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("British")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, briDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(briDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Caribbean")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, carDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        if (carDishCalList.size() > 0) {
                            txtAnsCalories.setText(carDishCalList.get(0));
                        } else {
                            txtAnsCalories.setText("");
                        }
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Chinese")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, chiDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(chiDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("French")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, freDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(freDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Greek")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, spaDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(greDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Indian")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, indDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(indDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Italian")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, itaDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(itaDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Mediterranean")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, medDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(medDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Mexican")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, mexDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(mexDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Moroccan")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, morDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(morDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Spanish")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, spaDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(spaDishCalList.get(0));

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Thai")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, thaDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(thaDishCalList.get(0));
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("USA")) {
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, usaDishNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(usaDishCalList.get(0));
                    }
                } else {
                    if (spinnerCountryVeg.getSelectedItem().equals("Vegetable")) {
                        txtTitleCalServing.setText("Calories / 100g :  ");
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, vegItemNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(vegItemCalList.get(0));
                    } else if (spinnerCountryVeg.getSelectedItem().equals("Fruit")) {
                        txtTitleCalServing.setText("Calories :  ");
                        ArrayAdapter<String> adapterDish = new ArrayAdapter<String>(ActivityIntakes.this, android.R.layout.simple_spinner_item, fruItemNameList);
                        adapterDish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDishName.setAdapter(adapterDish);
                        txtAnsCalories.setText(fruItemCalList.get(0));
                    }
                }
                if (FROM_FAVORITE) {
                    spinnerDishName.setSelection(dishSelection);
                    FROM_FAVORITE = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        imgBack = (ImageView)findViewById(R.id.imgViewBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        spinnerDishName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
                    String selectedItem = spinnerDishName.getSelectedItem().toString();
                    selectedItem = selectedItem.replaceAll("'", "\"");
                    Log.d("Selected Dish", selectedItem);
                    if (spinnerCountryVeg.getSelectedItem().toString().equals("Australia")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from AUSTRALIAN_FOOD_TABLE_PRO where dishNameAus='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesAus")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("British")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from BRITISH_FOOD_TABLE_PRO where dishNameBri='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesBri")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Caribbean")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from CARIBBEAN_FOOD_TABLE_PRO where dishNameCar='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesCar")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Chinese")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from CHINESE_FOOD_TABLE_PRO where dishNameChi='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesChi")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("French")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from FRENCH_FOOD_TABLE_PRO where dishNameFre='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesFre")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Greek")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from GREEK_FOOD_TABLE_PRO where dishNameGre='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesGre")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Indian")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from INDIAN_FOOD_TABLE_PRO where dishNameInd='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesInd")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Italian")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from ITALIAN_FOOD_TABLE_PRO where dishNameIta='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesIta")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Mediterranean")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from MEDITERRANEAN_FOOD_TABLE_PRO where dishNameMed='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesMed")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Mexican")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from MEXICAN_FOOD_TABLE_PRO where dishNameMex='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesMex")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Moroccan")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from MORROCCAN_FOOD_TABLE_PRO where dishNameMor='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesMor")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Spanish")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from SPANISH_FOOD_TABLE_PRO where dishNameSpa='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesSpa")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Thai")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from THAI_FOOD_TABLE_PRO where dishNameTha='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesTha")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("USA")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from USA_FOOD_TABLE_PRO where dishNameUsa='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("dishCaloriesUsa")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    }
                } else {
                    String selectedItem = spinnerDishName.getSelectedItem().toString();
                    selectedItem = selectedItem.replaceAll("'", "\"");
                    if (spinnerCountryVeg.getSelectedItem().toString().equals("Vegetable")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from VEGETABLES_CALORIES_PRO where vegName='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("vegCalories")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();

                    } else if (spinnerCountryVeg.getSelectedItem().toString().equals("Fruit")) {
                        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                        Cursor c = db.rawQuery("select * from FRUIT_CALORIES_PRO where fruitName='" + selectedItem + "'", null);
                        if (c.moveToFirst()) {
                            do {
                                txtAnsCalories.setText(c.getString(c.getColumnIndex("fruitCalories")));
                            } while (c.moveToNext());
                        }
                        c.close();
                        db.close();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spanned text  = null;
                if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Dishes")) {
                    text  = Html.fromHtml("<p>You can get <b><h1>"+txtAnsCalories.getText().toString()+"</h1></b> calories (kcal) from 1 serving of "+spinnerDishName.getSelectedItem().toString()+".</p>" +
                            "<p><b>"+spinnerDishName.getSelectedItem().toString()+"</b> is food dish belongs to country "+spinnerCountryVeg.getSelectedItem().toString()+".</p>");
                }
                else if (spinnerCountryVeg.getSelectedItem().toString().equalsIgnoreCase("Vegetable")) {
                    text  = Html.fromHtml("<p>Vegetables like <b>"+spinnerDishName.getSelectedItem().toString()+"</b> contains <b>"+txtAnsCalories.getText().toString()+" calories</b> per 100 gms.</p>");
                }
                else if (spinnerCountryVeg.getSelectedItem().toString().equalsIgnoreCase("Fruit")) {
                    text  = Html.fromHtml("<p>Fruits like <b>"+spinnerDishName.getSelectedItem().toString()+"</b> contains <b>"+txtAnsCalories.getText().toString()+" calories</b></p>");
                }

                SweetAlertDialog sDialog = new SweetAlertDialog(ActivityIntakes.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE, SweetAlertDialog.CALORIES_INTAKE_SHARE_FLAG);
                sDialog.setTitleText("Wholesome!");
                sDialog.setContentText(text);
                sDialog.setCustomImage(R.drawable.imgpsh_fullsize);
                sDialog.setShareIntakeFBVisible(true);
                sDialog.setShareIntakeVisible(true);
                sDialog.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake);

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

        txtAnsCalories = (TextView) findViewById(R.id.textAnswer);
        txtTitleCalServing = (TextView) findViewById(R.id.textCaloriesServing);
        txtTitleCountryVeg = (TextView) findViewById(R.id.textViewCountryVeg);
        txtTitleName = (TextView) findViewById(R.id.textViewDishName);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerCountryVeg = (Spinner) findViewById(R.id.spinnerCountryVeg);
        spinnerDishName = (Spinner) findViewById(R.id.spinnerName);
        btnAddtoFav = (ImageView) findViewById(R.id.imageFavorite);
        btnFavorite = (ImageView) findViewById(R.id.imgViewGetFav);
        btnShare = (ImageView) findViewById(R.id.imgViewShare);
        btnCalculate = (Button)findViewById(R.id.btnCalculate);

        country_array = getResources().getStringArray(R.array.country_array);
        britishDishName_array = getResources().getStringArray(R.array.British_dish_name_array);
        britishCalories_array = getResources().getStringArray(R.array.British_Calories_array);
        caribbeanDishName_array = getResources().getStringArray(R.array.Caribbean_dish_name_array);
        caribbeanCalories_array = getResources().getStringArray(R.array.Caribbean_Calories_array);
        chineseDishName_array = getResources().getStringArray(R.array.Chinese_dish_name_array);
        chineseCalories_array = getResources().getStringArray(R.array.Chinese_Calories_array);
        frenchDishName_array = getResources().getStringArray(R.array.French_dish_name_array);
        frenchCalories_array = getResources().getStringArray(R.array.French_Calories_array);
        greekDishName_array = getResources().getStringArray(R.array.Greek_dish_name_array);
        greekCalories_array = getResources().getStringArray(R.array.Greek_Calories_array);
        indianDishName_array = getResources().getStringArray(R.array.Indian_dish_name_array);
        indianCalories_array = getResources().getStringArray(R.array.Indian_Calories_array);
        italianDishName_array = getResources().getStringArray(R.array.Italian_dish_name_array);
        italianCalories_array = getResources().getStringArray(R.array.Italian_Calories_array);
        mediterraneanDishName_array = getResources().getStringArray(R.array.Mediterranean_dish_name_array);
        mediterraneanCalories_array = getResources().getStringArray(R.array.Mediterranean_Calories_array);
        mexicanDishName_array = getResources().getStringArray(R.array.Mexican_dish_name_array);
        mexicanCalories_array = getResources().getStringArray(R.array.Mexican_Calories_array);
        morroccanDishName_array = getResources().getStringArray(R.array.Moroccan_dish_name_array);
        morroccanCalories_array = getResources().getStringArray(R.array.Moroccan_Calories_array);
        spanishDishName_array = getResources().getStringArray(R.array.Spanish_dish_name_array);
        spanishCalories_array = getResources().getStringArray(R.array.Spanish_Calories_array);
        thaiDishName_array = getResources().getStringArray(R.array.Thai_dish_name_array);
        thaiCalories_array = getResources().getStringArray(R.array.Thai_Calories_array);
        usaDishName_array = getResources().getStringArray(R.array.USA_dish_name_array);
        usaCalories_array = getResources().getStringArray(R.array.USA_Calories_array);
        australianDishName_array = getResources().getStringArray(R.array.Australia_dish_name_array);
        australianCalories_array = getResources().getStringArray(R.array.Australia_Calories_array);

        VegName_array = getResources().getStringArray(R.array.Vegetables_Array);
        VegCal_array = getResources().getStringArray(R.array.veg_calories);
        FruName_array = getResources().getStringArray(R.array.Fruit_array);
        FruCal_array = getResources().getStringArray(R.array.fruit_calories);


        new PrepareListAsync().execute();

        //initial_timer.start();





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActivityIntakes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.trl.wholesome/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActivityIntakes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.trl.wholesome/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*public class DataInsertTimer extends CountDownTimer {

        public DataInsertTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub




        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }

    }*/

    public void dataInsert1(){
        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        /////////////////////////////////////////////////////////////////////
        Cursor cCountry = db.rawQuery("select * from COUNTRIES_PRO", null);
        if (cCountry.getCount() <= 0) {
            for (int i = 0; i < country_array.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put("countryName", country_array[i]);
                db.insert("COUNTRIES_PRO", null, cv);
                countryList.add(country_array[i]);
            }
        } else {
            countryList.clear();
            if (cCountry.moveToFirst()) {
                do {
                    countryList.add(cCountry.getString(cCountry.getColumnIndex("countryName")));
                } while (cCountry.moveToNext());
            }
        }
        cCountry.close();
        /////////////////////////////////////////////////////////////////////
        Cursor cAus = db.rawQuery("select * from AUSTRALIAN_FOOD_TABLE_PRO", null);
        if (cAus.getCount() <= 0) {
            for (int i = 0; i < australianDishName_array.length; i++) {
                Log.d("inserted Dish", australianDishName_array[i]);
                ContentValues cv = new ContentValues();
                String dishName = australianDishName_array[i].replaceAll("'", "\"");
                String dishCal = australianCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameAus", dishName);
                cv.put("dishCaloriesAus", dishCal);
                db.insert("AUSTRALIAN_FOOD_TABLE_PRO", null, cv);
                ausSortedMap.put(dishName, dishCal);
            }
        } else {
            ausDishNameList.clear();
            ausDishCalList.clear();
            if (cAus.moveToFirst()) {
                do {
                    ausSortedMap.put(cAus.getString(cAus.getColumnIndex("dishNameAus")),
                            cAus.getString(cAus.getColumnIndex("dishCaloriesAus")));
                } while (cAus.moveToNext());
            }
        }
        cAus.close();
        //////////////////////////////////////////////////////////////////
        Cursor cBri = db.rawQuery("select * from BRITISH_FOOD_TABLE_PRO", null);
        if (cBri.getCount() <= 0) {
            for (int i = 0; i < britishDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = britishDishName_array[i].replaceAll("'", "\"");
                String dishCal = britishCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameBri", dishName);
                cv.put("dishCaloriesBri", dishCal);
                db.insert("BRITISH_FOOD_TABLE_PRO", null, cv);
                briSortedMap.put(dishName, dishCal);
            }
        } else {
            briDishNameList.clear();
            briDishCalList.clear();
            if (cBri.moveToFirst()) {
                do {
                    briSortedMap.put(cBri.getString(cBri.getColumnIndex("dishNameBri")),
                            cBri.getString(cBri.getColumnIndex("dishCaloriesBri")));
                } while (cBri.moveToNext());
            }
        }
        cBri.close();
        ////////////////////////////////////////////////////////////////////
        Cursor cCar = db.rawQuery("select * from CARIBBEAN_FOOD_TABLE_PRO", null);
        if (cCar.getCount() <= 0) {
            for (int i = 0; i < caribbeanDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = caribbeanDishName_array[i].replaceAll("'", "\"");
                String dishCal = caribbeanCalories_array[i].replaceAll("'", "\"");
                Log.d("Added to Db", dishName);
                cv.put("dishNameCar", dishName);
                cv.put("dishCaloriesCar", dishCal);
                db.insert("CARIBBEAN_FOOD_TABLE_PRO", null, cv);
                carSortedMap.put(dishName, dishCal);
            }
        } else {
            carDishNameList.clear();
            carDishCalList.clear();
            if (cCar.moveToFirst()) {
                do {
                    carSortedMap.put(cCar.getString(cCar.getColumnIndex("dishNameCar")),
                            cCar.getString(cCar.getColumnIndex("dishCaloriesCar")));
                } while (cCar.moveToNext());
            }
        }
        cCar.close();
        //////////////////////////////////////////////////////////////////
        Cursor cChi = db.rawQuery("select * from CHINESE_FOOD_TABLE_PRO", null);
        if (cChi.getCount() <= 0) {
            for (int i = 0; i < chineseDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = chineseDishName_array[i].replaceAll("'", "\"");
                String dishCal = chineseCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameChi", dishName);
                cv.put("dishCaloriesChi", dishCal);
                db.insert("CHINESE_FOOD_TABLE_PRO", null, cv);
                chiSortedMap.put(dishName, dishCal);
            }
        } else {
            chiDishNameList.clear();
            chiDishCalList.clear();
            if (cChi.moveToFirst()) {
                do {
                    chiSortedMap.put(cChi.getString(cChi.getColumnIndex("dishNameChi")),
                            cChi.getString(cChi.getColumnIndex("dishCaloriesChi")));
                } while (cChi.moveToNext());
            }
        }
        cChi.close();
        /////////////////////////////////////////////////////////////////
        Cursor cFre = db.rawQuery("select * from FRENCH_FOOD_TABLE_PRO", null);
        if (cFre.getCount() <= 0) {
            for (int i = 0; i < frenchDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = frenchDishName_array[i].replaceAll("'", "\"");
                String dishCal = frenchCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameFre", dishName);
                cv.put("dishCaloriesFre", dishCal);
                db.insert("FRENCH_FOOD_TABLE_PRO", null, cv);
                freSortedMap.put(dishName, dishCal);
            }
        } else {
            freDishNameList.clear();
            freDishCalList.clear();
            if (cFre.moveToFirst()) {
                do {
                    freSortedMap.put(cFre.getString(cFre.getColumnIndex("dishNameFre")),
                            cFre.getString(cFre.getColumnIndex("dishCaloriesFre")));
                } while (cFre.moveToNext());
            }
        }
        cFre.close();
        ////////////////////////////////////////////////////////////////
        Cursor cGre = db.rawQuery("select * from GREEK_FOOD_TABLE_PRO", null);
        if (cGre.getCount() <= 0) {
            for (int i = 0; i < greekDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = greekDishName_array[i].replaceAll("'", "\"");
                String dishCal = greekCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameGre", dishName);
                cv.put("dishCaloriesGre", dishCal);
                db.insert("GREEK_FOOD_TABLE_PRO", null, cv);
                greSortedMap.put(dishName, dishCal);
            }
        } else {
            greDishNameList.clear();
            greDishCalList.clear();
            if (cGre.moveToFirst()) {
                do {
                    greSortedMap.put(cGre.getString(cGre.getColumnIndex("dishNameGre")),
                            cGre.getString(cGre.getColumnIndex("dishCaloriesGre")));
                } while (cGre.moveToNext());
            }
        }
        cGre.close();
        /////////////////////////////////////////////////////////////////
        Cursor cInd = db.rawQuery("select * from INDIAN_FOOD_TABLE_PRO", null);
        if (cInd.getCount() <= 0) {
            for (int i = 0; i < indianDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = indianDishName_array[i].replaceAll("'", "\"");
                String dishCal = indianCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameInd", dishName);
                cv.put("dishCaloriesInd", dishCal);
                db.insert("INDIAN_FOOD_TABLE_PRO", null, cv);
                indSortedMap.put(dishName, dishCal);
            }
        } else {
            indDishNameList.clear();
            indDishCalList.clear();
            if (cInd.moveToFirst()) {
                do {
                    indSortedMap.put(cInd.getString(cInd.getColumnIndex("dishNameInd")),
                            cInd.getString(cInd.getColumnIndex("dishCaloriesInd")));
                } while (cInd.moveToNext());
            }
        }
        cInd.close();
        //////////////////////////////////////////////////////////////////
        Cursor cIta = db.rawQuery("select * from ITALIAN_FOOD_TABLE_PRO", null);
        if (cIta.getCount() <= 0) {
            for (int i = 0; i < italianDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = italianDishName_array[i].replaceAll("'", "\"");
                String dishCal = italianCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameIta", dishName);
                cv.put("dishCaloriesIta", dishCal);
                db.insert("ITALIAN_FOOD_TABLE_PRO", null, cv);
                itaSortedMap.put(dishName, dishCal);
            }
        } else {
            itaDishNameList.clear();
            itaDishCalList.clear();
            if (cIta.moveToFirst()) {
                do {
                    itaSortedMap.put(cIta.getString(cIta.getColumnIndex("dishNameIta")),
                            cIta.getString(cIta.getColumnIndex("dishCaloriesIta")));
                } while (cIta.moveToNext());
            }
        }
        cIta.close();
        //////////////////////////////////////////////////////////////////
        Cursor cMex = db.rawQuery("select * from MEXICAN_FOOD_TABLE_PRO", null);
        if (cMex.getCount() <= 0) {
            for (int i = 0; i < mexicanDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = mexicanDishName_array[i].replaceAll("'", "\"");
                String dishCal = mexicanCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameMex", dishName);
                cv.put("dishCaloriesMex", dishCal);
                db.insert("MEXICAN_FOOD_TABLE_PRO", null, cv);
                mexSortedMap.put(dishName, dishCal);
            }
        } else {
            mexDishNameList.clear();
            mexDishCalList.clear();
            if (cMex.moveToFirst()) {
                do {
                    mexSortedMap.put(cMex.getString(cMex.getColumnIndex("dishNameMex")),
                            cMex.getString(cMex.getColumnIndex("dishCaloriesMex")));
                } while (cMex.moveToNext());
            }
        }
        cMex.close();
        /////////////////////////////////////////////////////////////////////////
        Cursor cMed = db.rawQuery("select * from MEDITERRANEAN_FOOD_TABLE_PRO", null);
        if (cMed.getCount() <= 0) {
            for (int i = 0; i < mediterraneanDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = mediterraneanDishName_array[i].replaceAll("'", "\"");
                String dishCal = mediterraneanCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameMed", dishName);
                cv.put("dishCaloriesMed", dishCal);
                db.insert("MEDITERRANEAN_FOOD_TABLE_PRO", null, cv);
                medSortedMap.put(dishName, dishCal);
            }
        } else {
            medDishNameList.clear();
            medDishCalList.clear();
            if (cMed.moveToFirst()) {
                do {
                    medSortedMap.put(cMed.getString(cMed.getColumnIndex("dishNameMed")),
                            cMed.getString(cMed.getColumnIndex("dishCaloriesMed")));
                } while (cMed.moveToNext());
            }
        }
        cMed.close();
        ////////////////////////////////////////////////////////////////////
        Cursor cMor = db.rawQuery("select * from MORROCCAN_FOOD_TABLE_PRO", null);
        if (cMor.getCount() <= 0) {
            for (int i = 0; i < morroccanDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = morroccanDishName_array[i].replaceAll("'", "\"");
                String dishCal = morroccanCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameMor", dishName);
                cv.put("dishCaloriesMor", dishCal);
                db.insert("MORROCCAN_FOOD_TABLE_PRO", null, cv);
                morSortedMap.put(dishName, dishCal);
            }
        } else {
            morDishNameList.clear();
            morDishCalList.clear();
            if (cMor.moveToFirst()) {
                do {
                    morSortedMap.put(cMor.getString(cMor.getColumnIndex("dishNameMor")),
                            cMor.getString(cMor.getColumnIndex("dishCaloriesMor")));
                } while (cMor.moveToNext());
            }
        }
        cMor.close();

        Cursor cSpa = db.rawQuery("select * from SPANISH_FOOD_TABLE_PRO", null);
        if (cSpa.getCount() <= 0) {
            for (int i = 0; i < spanishDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = spanishDishName_array[i].replaceAll("'", "\"");
                String dishCal = spanishCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameSpa", dishName);
                cv.put("dishCaloriesSpa", dishCal);
                db.insert("SPANISH_FOOD_TABLE_PRO", null, cv);
                spaSortedMap.put(dishName, dishCal);
            }
        } else {
            spaDishNameList.clear();
            spaDishCalList.clear();
            if (cSpa.moveToFirst()) {
                do {
                    spaSortedMap.put(cSpa.getString(cSpa.getColumnIndex("dishNameSpa")),
                            cSpa.getString(cSpa.getColumnIndex("dishCaloriesSpa")));
                } while (cSpa.moveToNext());
            }
        }
        cSpa.close();
        ///////////////////////////////////////////////////////////////
        Cursor cTha = db.rawQuery("select * from THAI_FOOD_TABLE_PRO", null);
        if (cTha.getCount() <= 0) {
            for (int i = 0; i < thaiDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = thaiDishName_array[i].replaceAll("'", "\"");
                String dishCal = thaiCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameTha", dishName);
                cv.put("dishCaloriesTha", dishCal);
                db.insert("THAI_FOOD_TABLE_PRO", null, cv);
                thaSortedMap.put(dishName, dishCal);
            }
        } else {
            thaDishNameList.clear();
            thaDishCalList.clear();
            if (cTha.moveToFirst()) {
                do {
                    thaSortedMap.put(cTha.getString(cTha.getColumnIndex("dishNameTha")),
                            cTha.getString(cTha.getColumnIndex("dishCaloriesTha")));
                } while (cTha.moveToNext());
            }
        }
        cTha.close();
        //////////////////////////////////////////////////////////////
        Cursor cUsa = db.rawQuery("select * from USA_FOOD_TABLE_PRO", null);
        if (cUsa.getCount() <= 0) {
            for (int i = 0; i < usaDishName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String dishName = usaDishName_array[i].replaceAll("'", "\"");
                String dishCal = usaCalories_array[i].replaceAll("'", "\"");
                cv.put("dishNameUsa", dishName);
                cv.put("dishCaloriesUsa", dishCal);
                db.insert("USA_FOOD_TABLE_PRO", null, cv);
                usaSortedMap.put(dishName, dishCal);
            }
        } else {
            usaDishNameList.clear();
            usaDishCalList.clear();
            if (cUsa.moveToFirst()) {
                do {
                    usaSortedMap.put(cUsa.getString(cUsa.getColumnIndex("dishNameUsa")),
                            cUsa.getString(cUsa.getColumnIndex("dishCaloriesUsa")));
                } while (cUsa.moveToNext());
            }
        }
        cUsa.close();
        db.close();

        @SuppressWarnings("rawtypes")
        Iterator ausIte = ausSortedMap.keySet().iterator();
        while (ausIte.hasNext()) {
            String key = (String) ausIte.next();
            String value = (String) ausSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            ausDishNameList.add(key);
            ausDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator briIte = briSortedMap.keySet().iterator();
        while (briIte.hasNext()) {
            String key = (String) briIte.next();
            String value = (String) briSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            briDishNameList.add(key);
            briDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator carIte = carSortedMap.keySet().iterator();
        while (carIte.hasNext()) {
            String key = (String) carIte.next();
            String value = (String) carSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            carDishNameList.add(key);
            carDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator chiIte = chiSortedMap.keySet().iterator();
        while (chiIte.hasNext()) {
            String key = (String) chiIte.next();
            String value = (String) chiSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            chiDishNameList.add(key);
            chiDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator freIte = freSortedMap.keySet().iterator();
        while (freIte.hasNext()) {
            String key = (String) freIte.next();
            String value = (String) freSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            freDishNameList.add(key);
            freDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator greIte = greSortedMap.keySet().iterator();
        while (greIte.hasNext()) {
            String key = (String) greIte.next();
            String value = (String) greSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            greDishNameList.add(key);
            greDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator indIte = indSortedMap.keySet().iterator();
        while (indIte.hasNext()) {
            String key = (String) indIte.next();
            String value = (String) indSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            indDishNameList.add(key);
            indDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator itaIte = itaSortedMap.keySet().iterator();
        while (itaIte.hasNext()) {
            String key = (String) itaIte.next();
            String value = (String) itaSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            itaDishNameList.add(key);
            itaDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator medIte = medSortedMap.keySet().iterator();
        while (medIte.hasNext()) {
            String key = (String) medIte.next();
            String value = (String) medSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            medDishNameList.add(key);
            medDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator mexIte = mexSortedMap.keySet().iterator();
        while (mexIte.hasNext()) {
            String key = (String) mexIte.next();
            String value = (String) mexSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            mexDishNameList.add(key);
            mexDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator morIte = morSortedMap.keySet().iterator();
        while (morIte.hasNext()) {
            String key = (String) morIte.next();
            String value = (String) morSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            morDishNameList.add(key);
            morDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator spaIte = spaSortedMap.keySet().iterator();
        while (spaIte.hasNext()) {
            String key = (String) spaIte.next();
            String value = (String) spaSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            spaDishNameList.add(key);
            spaDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator thaIte = thaSortedMap.keySet().iterator();
        while (thaIte.hasNext()) {
            String key = (String) thaIte.next();
            String value = (String) thaSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            thaDishNameList.add(key);
            thaDishCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator usaIte = usaSortedMap.keySet().iterator();
        while (usaIte.hasNext()) {
            String key = (String) usaIte.next();
            String value = (String) usaSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            usaDishNameList.add(key);
            usaDishCalList.add(value);
        }
    }

    public void dataInsert2(){
        SQLiteDatabase db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        /////////////////////////////////////////////////////////////////////
        Cursor cVeg = db.rawQuery("select * from VEGETABLES_CALORIES_PRO", null);
        if (cVeg.getCount() <= 0) {

            for (int i = 0; i < VegName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String itemName = VegName_array[i].replaceAll("'", "\"");
                String itemCal = VegCal_array[i].replaceAll("'", "\"");
                cv.put("vegName", itemName);
                cv.put("vegCalories", itemCal);
                db.insert("VEGETABLES_CALORIES_PRO", null, cv);
                vegSortedMap.put(itemName, itemCal);
            }
        } else {
            vegItemNameList.clear();
            vegItemCalList.clear();
            if (cVeg.moveToFirst()) {
                do {
                    vegSortedMap.put(cVeg.getString(cVeg.getColumnIndex("vegName")),
                            cVeg.getString(cVeg.getColumnIndex("vegCalories")));
                } while (cVeg.moveToNext());
            }
        }
        cVeg.close();
        Cursor cFru = db.rawQuery("select * from FRUIT_CALORIES_PRO", null);
        if (cFru.getCount() <= 0) {
            for (int i = 0; i < FruName_array.length; i++) {
                ContentValues cv = new ContentValues();
                String itemName = FruName_array[i].replaceAll("'", "\"");
                String itemCal = FruCal_array[i].replaceAll("'", "\"");
                cv.put("fruitName", itemName);
                cv.put("fruitCalories", itemCal);
                db.insert("FRUIT_CALORIES_PRO", null, cv);
                fruSortedMap.put(itemName, itemCal);
            }

        } else {
            fruItemNameList.clear();
            fruItemCalList.clear();
            if (cFru.moveToFirst()) {
                do {
                    fruSortedMap.put(cFru.getString(cFru.getColumnIndex("fruitName")),
                            cFru.getString(cFru.getColumnIndex("fruitCalories")));
                } while (cFru.moveToNext());
            }
        }
        cFru.close();
        db.close();


        @SuppressWarnings("rawtypes")
        Iterator vegIte = vegSortedMap.keySet().iterator();
        while (vegIte.hasNext()) {
            String key = (String) vegIte.next();
            String value = (String) vegSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            vegItemNameList.add(key);
            vegItemCalList.add(value);
        }

        @SuppressWarnings("rawtypes")
        Iterator fruIte = fruSortedMap.keySet().iterator();
        while (fruIte.hasNext()) {
            String key = (String) fruIte.next();
            String value = (String) fruSortedMap.get(key);
            key = key.replaceAll("\"", "'");
            fruItemNameList.add(key);
            fruItemCalList.add(value);
        }
    }

    /*public class DataInsertTimer2 extends CountDownTimer {

        public DataInsertTimer2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub


	        *//*ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(
                    ActivityIntakes.this, R.array.vf_array, android.R.layout.simple_spinner_item);
			adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerCountryVeg.setAdapter(adapterCountry);*//*
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }
    }*/

    /*public class InitialTimer extends CountDownTimer {

        public InitialTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            insert_timer.start();
            insert_timer_2.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryList);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                }
                break;
            case REQUEST_EDIT:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryList);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                }
                break;
            case REQUEST_ADD_VEG_FRUIT:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.vf_array, android.R.layout.simple_spinner_item);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                }
                break;
            case REQUEST_EDIT_VEG_FRUIT:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.vf_array, android.R.layout.simple_spinner_item);
                    adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountryVeg.setAdapter(adapterCountry);
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    private static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }
}
