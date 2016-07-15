package com.trl.wholesome.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trl.wholesome.ActivityDiaries;
import com.trl.wholesome.R;
import com.trl.wholesome.fragements.DateDialogFragment3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mitesh on 1/6/16.
 */
public class AdapterIntake extends BaseAdapter{

    private List<Integer> mark = new ArrayList<Integer>();
    private List<String> date = new ArrayList<String>();
    private List<String> calories = new ArrayList<String>();
    private List<String> desc = new ArrayList<String>();
    private List<Integer> idList = new ArrayList<Integer>();

    private Context context;

    private int clickedPos=0;
    private int clickedPosCalories=0;
    private int clickedPosDesc=0;

    private int mYear=0;
    private int mMonth=0;
    private int mDay=0;

    private int weekYear=0;
    private int dayWeek=0;
    private int yearM=0;
    private Calendar now;
    private final int DATE_DIALOG_ID = 1;
    private final int ADD_NOTES = 2;
    private final int CALORIES_DIALOG=3;
    private final int DESC_DIALOG=4;

    private DateDialogFragment3 frag;

    private ActivityDiaries activity;

    public AdapterIntake(Context context){

        this.context = context;

        this.activity = ((ActivityDiaries)context);

        now = Calendar.getInstance();

        mark.clear();
        date.clear();
        calories.clear();
        desc.clear();
        idList.clear();
        SQLiteDatabase db = context.openOrCreateDatabase("CaloriesBurned_Pro.db",Context.MODE_PRIVATE,null);
        String sql = "select * from caloriesburned_pro_intake_notes;";
        Cursor c = db.rawQuery(sql,null);
        if(c.getCount()>0){
            if(c.moveToFirst()){
                do{
                    idList.add(c.getInt(c.getColumnIndex("id")));
                    mark.add(c.getInt(c.getColumnIndex("mark")));
                    date.add(c.getString(c.getColumnIndex("date")));
                    calories.add(c.getString(c.getColumnIndex("calories")));
                    desc.add(c.getString(c.getColumnIndex("desc")));
                    Log.d("desc : "+c.getString(c.getColumnIndex("desc")),
                            "Week : "+c.getString(c.getColumnIndex("week_of_year")));
                }while(c.moveToNext());
            }
        }
        c.close();
        db.close();

    }

    @Override
    public int getCount() {
        return mark.size();
    }

    @Override
    public Object getItem(int i) {
        return mark.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ViewHolder viewHolder=null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_diary_intake,null);
            viewHolder = new ViewHolder();

            viewHolder.check_box = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.DatePicker = (Button) convertView.findViewById(R.id.btn_datepicker);
            viewHolder.editCaloriesBurned = (EditText) convertView.findViewById(R.id.edit_caloriesBurned);
            viewHolder.Description = (EditText) convertView.findViewById(R.id.edit_desc);
            viewHolder.imgDelete = (ImageView) convertView.findViewById(R.id.imageViewTrash);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.DatePicker.setEnabled(false);
        viewHolder.Description.setEnabled(false);
        viewHolder.editCaloriesBurned.setEnabled(false);

        //display values stored in database
        if(mark.get(position)==1){
            viewHolder.check_box.setChecked(true);
        }else{
            viewHolder.check_box.setChecked(false);
        }
        viewHolder.DatePicker.setText(date.get(position).trim());
        viewHolder.editCaloriesBurned.setText(calories.get(position));
        viewHolder.Description.setText(desc.get(position));

        final int pos = position;
        final ViewHolder viewHolderTemp= viewHolder;
        /*viewHolder.DatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickedPos = pos;
                Log.d("SelectedId From:", ""+idList.get(clickedPos));
                String dateText = date.get(pos);
                int count=0;
                int oldIndex=0;
                for (int i=0;i<dateText.length();i++){

                    if(dateText.charAt(i)=='-'){
                        count++;
                        if(count==1){
                            //Log.d("Month :",dateText.substring(oldIndex,i));
                            mMonth= Integer.parseInt(dateText.substring(oldIndex,i))-1;
                        }
                        if(count==2){
                            //Log.d("Day", dateText.substring(oldIndex+1, i));
                            mDay= Integer.parseInt(dateText.substring(oldIndex+1, i));
                            mYear= Integer.parseInt(dateText.substring(i+1).trim());
                            //Log.d("Year",dateText.substring(i+1));
                        }

                        oldIndex=i;
                    }

                }
                now.set(mYear, mMonth, mDay);
                showDialog();
            }
        });*/
        viewHolder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SQLiteDatabase db;
                db = context.openOrCreateDatabase("CaloriesBurned_Pro.db",Context.MODE_PRIVATE,null);
                ContentValues cv= new ContentValues();
                if(isChecked){
                    cv.put("mark", 1);
                }
                else{
                    cv.put("mark", 0);
                }

                db.update("caloriesburned_pro_intake_notes", cv, "id"+"="+idList.get(pos)+"", null);
                db.close();

            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db;
                db = context.openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                db.delete("caloriesburned_pro_intake_notes", "id"+"="+idList.get(pos)+"", null);
                db.close();
                Log.e("imgDelete", "pos = "+pos);
                ((ActivityDiaries)context).refreshIntakeAdapter();
            }
        });


        /*viewHolder.editCaloriesBurned.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()== MotionEvent.ACTION_UP){
                    clickedPosCalories= pos;

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setTitle("Update");

                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,10,10);
                    input.setLayoutParams(lp);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    input.setText(calories.get(pos));
                    alert.setView(input);
                    alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            SQLiteDatabase db_temp;
                            db_temp = context.openOrCreateDatabase("CaloriesBurned_Pro.db",Context.MODE_PRIVATE,null);
                            ContentValues cv= new ContentValues();
                            String str="N/A";
                            if(input.getText().toString().equals("")){
                                str="N/A";
                            }else{
                                str= input.getText().toString();
                            }
                            cv.put("calories", str);
                            db_temp.update("caloriesburned_pro_intake_notes", cv, "id"+"="+idList.get(pos)+"", null);

                            calories.clear();
                            String sql = "select * from caloriesburned_pro_intake_notes;";
                            Cursor c = db_temp.rawQuery(sql,null);
                            if(c.getCount()>0){
                                if(c.moveToFirst()){
                                    do{
                                        calories.add(c.getString(c.getColumnIndex("calories")));
                                    }while(c.moveToNext());
                                }
                            }
                            c.close();
                            db_temp.close();
                            viewHolderTemp.editCaloriesBurned.setText(str);
                            ((ActivityDiaries)context).refreshIntakeAdapter();
                        }
                    });
                    alert.setNegativeButton("Cancel", null);
                    AlertDialog alert11 = alert.create();
                    alert11.show();
                    return true;

                }

                return false;
            }
        });*/

        /*viewHolder.Description.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()== MotionEvent.ACTION_UP){
                    clickedPosDesc= pos;
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(desc.get(pos));
                    alert.setView(input);
                    input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            SQLiteDatabase db_temp;
                            db_temp = context.openOrCreateDatabase("CaloriesBurned_Pro.db",Context.MODE_PRIVATE,null);
                            ContentValues cv= new ContentValues();
                            String str= "N/A";
                            if(input.getText().toString().equals("")){
                                str="N/A";
                            }else{
                                str= input.getText().toString();
                            }
                            cv.put("desc", str);
                            db_temp.update("caloriesburned_pro_intake_notes", cv, "id"+"="+idList.get(pos)+"", null);

                            desc.clear();
                            String sql = "select * from caloriesburned_pro_intake_notes;";
                            Cursor c = db_temp.rawQuery(sql,null);
                            if(c.getCount()>0){
                                if(c.moveToFirst()){
                                    do{
                                        desc.add(c.getString(c.getColumnIndex("desc")));
                                    }while(c.moveToNext());
                                }
                            }
                            c.close();
                            hideSoftKeyBoard();
                            db_temp.close();
                            viewHolderTemp.Description.setText(str);
                            ((ActivityDiaries)context).refreshIntakeAdapter();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hideSoftKeyBoard();
                        }
                    });
                    AlertDialog alert11 = alert.create();
                    alert11.show();
                    return true;
                }
                return false;
            }
        });*/

        return convertView;
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static class ViewHolder
    {
        public CheckBox check_box;
        public Button DatePicker;
        public EditText editCaloriesBurned;
        public EditText Description;
        public ImageView imgDelete;
    }

    public void showDialog() {

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction(); //get the fragment
        frag = DateDialogFragment3.newInstance(context, new DateDialogFragment3.DateDialogFragmentListener(){
            public void updateChangedDate(int year, int month, int day){
                //editBillDate.setText(String.valueOf(month+1)+"-"+String.valueOf(day)+"-"+String.valueOf(year));
                String dayStr= day+"";
                if(day<10){
                    dayStr= "0"+dayStr;
                }
                now.set(year, month, day);
                weekYear= now.get(Calendar.WEEK_OF_YEAR);
                dayWeek= now.get(Calendar.DAY_OF_WEEK);
                yearM= year;
                String date =""+(month+1)+"-"+dayStr+"-"+year;
                ContentValues cv = new ContentValues();
                cv.put("date",date);
                cv.put("week_of_year", weekYear);
                cv.put("day_of_week", dayWeek);
                cv.put("year", yearM);

                SQLiteDatabase db;
                db = context.openOrCreateDatabase("CaloriesBurned_Pro.db", Context.MODE_PRIVATE, null);
                db.update("caloriesburned_pro_notes", cv, "id"+"="+idList.get(clickedPos)+"", null);
                db.close();

                notifyDataSetChanged();
                ((ActivityDiaries)context).refreshIntakeAdapter();

            }
        }, now);

        frag.show(ft, "DateDialogFragment");
    }
}
