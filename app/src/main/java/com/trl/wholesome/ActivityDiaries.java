package com.trl.wholesome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.trl.wholesome.adapters.AdapterBurned;
import com.trl.wholesome.adapters.AdapterIntake;

/**
 * Created by mitesh on 1/6/16.
 */
public class ActivityDiaries extends AppCompatActivity {

    private boolean isBurnedShowing = true;
    private Button btnDiaryBurned;
    private Button btnDiaryIntakes;

    private Button btnAddIntakes;
    private ImageView imgAdd;
    //private Button btnDeleteIntakes;
    private Button btnAddBurned;
    //private Button btnDeleteBurned;

    private LinearLayout linBurned;
    private LinearLayout linIntake;

    private ListView listViewBurned;
    private ListView listViewIntake;


    private AdapterBurned adapterBurned;
    private AdapterIntake adapterIntake;

    private static final int REQ_ADD_BURNED = 22;
    private static final int REQ_ADD_INTAKE = 21;

    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        btnDiaryBurned = (Button)findViewById(R.id.buttonBurned);
        btnDiaryIntakes = (Button)findViewById(R.id.buttonIntake);
        linBurned = (LinearLayout)findViewById(R.id.linearCaloriesBurned);
        linIntake = (LinearLayout)findViewById(R.id.linearCaloriesIntake);
        imgAdd  = (ImageView)findViewById(R.id.imgViewPlus);

        btnAddBurned = (Button)findViewById(R.id.btn_add_burned);
        //btnDeleteBurned = (Button)findViewById(R.id.btn_delete_burned);

        btnAddIntakes = (Button)findViewById(R.id.btn_add);
        //btnDeleteIntakes = (Button)findViewById(R.id.btn_delete);

        listViewBurned = (ListView)findViewById(R.id.list_burned_diary);
        listViewIntake = (ListView)findViewById(R.id.list_intake_diary);

        isBurnedShowing = true;
        btnDiaryBurned.setBackgroundResource(R.drawable.tab_active);
        btnDiaryIntakes.setBackgroundResource(R.drawable.tab_deactive);
        btnDiaryBurned.setTextColor(Color.WHITE);
        btnDiaryIntakes.setTextColor(Color.GRAY);
        linBurned.setVisibility(View.VISIBLE);
        linIntake.setVisibility(View.GONE);

        SQLiteDatabase DB;
        DB = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
        String sql = "select * from caloriesburned_pro_notes;";
        Cursor c = DB.rawQuery(sql,null);
        if(c.getCount()<=0){
            listViewBurned.setAdapter(null);
        }
        else {
            adapterBurned = new AdapterBurned(this);
            adapterBurned.notifyDataSetChanged();
            listViewBurned.setAdapter(adapterBurned);

            ColorDrawable sage = new ColorDrawable(Color.WHITE);
            listViewBurned.setDivider(sage);
            listViewBurned.setSmoothScrollbarEnabled(true);
        }
        c.close();

        ////
        String sql1 = "select * from caloriesburned_pro_intake_notes;";
        Cursor c1 = DB.rawQuery(sql1,null);
        if(c1.getCount()<=0){
            listViewIntake.setAdapter(null);
        }
        else {
            adapterIntake = new AdapterIntake(ActivityDiaries.this);
            adapterIntake.notifyDataSetChanged();
            listViewIntake.setAdapter(adapterIntake);

            ColorDrawable sage = new ColorDrawable(Color.WHITE);
            listViewIntake.setDivider(sage);
            listViewIntake.setSmoothScrollbarEnabled(true);
        }
        c1.close();
        DB.close();

        imgBack = (ImageView)findViewById(R.id.imgViewBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnDiaryBurned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!isBurnedShowing){
                   isBurnedShowing = true;
                   linBurned.setVisibility(View.VISIBLE);
                   linIntake.setVisibility(View.GONE);
                   btnDiaryBurned.setBackgroundResource(R.drawable.tab_active);
                   btnDiaryIntakes.setBackgroundResource(R.drawable.tab_deactive);
                   btnDiaryBurned.setTextColor(Color.WHITE);
                   btnDiaryIntakes.setTextColor(Color.GRAY);

               }
            }
        });

        btnDiaryIntakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBurnedShowing){
                    isBurnedShowing = false;
                    linBurned.setVisibility(View.GONE);
                    linIntake.setVisibility(View.VISIBLE);
                    btnDiaryBurned.setBackgroundResource(R.drawable.tab_deactive);
                    btnDiaryIntakes.setBackgroundResource(R.drawable.tab_active);
                    btnDiaryBurned.setTextColor(Color.GRAY);
                    btnDiaryIntakes.setTextColor(Color.WHITE);

                }
            }
        });

        btnAddBurned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityDiaries.this, ActivityAddRecords.class);
                i.putExtra("FROM", "BurnDiary");
                startActivityForResult(i, REQ_ADD_BURNED);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBurnedShowing) {
                    Intent i = new Intent(ActivityDiaries.this, ActivityAddRecords.class);
                    i.putExtra("FROM", "BurnDiary");
                    overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                    startActivityForResult(i, REQ_ADD_BURNED);
                }else{
                    Intent i = new Intent(ActivityDiaries.this, ActivityAddRecords.class);
                    i.putExtra("FROM", "IntaakeDiary");
                    overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                    startActivityForResult(i, REQ_ADD_INTAKE);
                }

            }
        });

        /*btnDeleteBurned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db;
                db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                String sql = "select * from caloriesburned_pro_notes;";
                Cursor c = db.rawQuery(sql,null);
                if(c.getCount()<=0){
                    Toast.makeText(ActivityDiaries.this, "Please, Add a note first", Toast.LENGTH_LONG).show();
                    c.close();
                    db.close();
                    return;
                }else{
                    db.delete("caloriesburned_pro_notes", "mark=1", null);
                    c.close();
                    db.close();

                    adapterBurned = new AdapterBurned(ActivityDiaries.this);
                    adapterBurned.notifyDataSetChanged();;
                    listViewBurned.setAdapter(adapterBurned);

                }
            }
        });*/

        btnAddIntakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityDiaries.this, ActivityAddRecords.class);
                i.putExtra("FROM", "IntaakeDiary");
                startActivityForResult(i, REQ_ADD_INTAKE);
            }
        });

        /*btnDeleteIntakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db;
                db = openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                String sql = "select * from caloriesburned_pro_intake_notes;";
                Cursor c = db.rawQuery(sql,null);
                if(c.getCount()<=0){
                    Toast.makeText(ActivityDiaries.this, "Please, Add a note first", Toast.LENGTH_LONG).show();
                    c.close();
                    db.close();
                    return;
                }else{
                    db.delete(", "mark=1", null);
                    c.close();
                    db.close();

                    //update adapter here

                    adapterIntake = new AdapterIntake(ActivityDiaries.this);
                    adapterIntake.notifyDataSetChanged();
                    listViewIntake.setAdapter(adapterIntake);
                }
            }
        });*/
    }

    public void refreshBurnAdapter(){
        adapterBurned = new AdapterBurned(ActivityDiaries.this);
        adapterBurned.notifyDataSetChanged();;
        listViewBurned.setAdapter(adapterBurned);
    }

    public void refreshIntakeAdapter(){
        adapterIntake = new AdapterIntake(ActivityDiaries.this);
        adapterIntake.notifyDataSetChanged();
        listViewIntake.setAdapter(adapterIntake);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_ADD_BURNED:
                if(resultCode== Activity.RESULT_OK){
                    refreshBurnAdapter();
                }
                break;
            case REQ_ADD_INTAKE:
                if(resultCode==Activity.RESULT_OK){
                    refreshIntakeAdapter();
                }
                break;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold_top, R.anim.exit_in_left);
        finish();
    }
}
