package com.theavalanche.eskimo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEventActivity extends ActionBarActivity {

    public static final String TAG = "AddEventActivity";

    private LinearLayout userListView;
    private EditText etTitle;
    private EditText etDesc;
    private Button bStartDate;
    private Button bEndDate;
    private Button bInviteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        userListView = (LinearLayout) findViewById(R.id.llUsers);
        etTitle = (EditText) findViewById(R.id.etEventTitle);
        etDesc = (EditText) findViewById(R.id.etEventDesc);
        bStartDate = (Button) findViewById(R.id.bStartDate);
        bEndDate = (Button) findViewById(R.id.bEndDate);
        bInviteUser = (Button) findViewById(R.id.bInviteUser);

        //Sample code to add users to view
        List<View> userViews = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < 10; i++){
            View view =  inflater.inflate(R.layout.row_user, new LinearLayout(this));
            userViews.add(view);
            userListView.addView(view);
        }

        bStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDateTimePicker(bStartDate);
            }
        });

        bEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDateTimePicker(bEndDate);
            }
        });
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US).format(Calendar.getInstance().getTime());
        bStartDate.setText(timeStamp);
        bEndDate.setText(timeStamp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save_event:

                saveEvent();
                return true;
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEvent(){
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();
        String startDate = bStartDate.getText().toString();
        String endDate = bEndDate.getText().toString();
        // TODO Save event on server
    }

    private void makeDateTimePicker(final Button button){
        // Create the dialog
        final Dialog mDateTimeDialog = new Dialog(this);
        // Inflate the root layout
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
        // Grab widget instance
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
        // Check is system is set to use 24h time (this doesn't seem to work as expected though)
        final String timeS = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
        final boolean is24h = !(timeS == null || timeS.equals("12"));

        // Update demo TextViews when the "OK" button is clicked
        mDateTimeDialogView.findViewById(R.id.SetDateTime).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mDateTimePicker.clearFocus();

                String date = mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH) + 1) + "/"
                        + mDateTimePicker.get(Calendar.DAY_OF_MONTH);

                String time;
                if (mDateTimePicker.is24HourView()) {
                    time = mDateTimePicker.get(Calendar.HOUR_OF_DAY) + ":" + mDateTimePicker.get(Calendar.MINUTE);
                } else {
                    time = mDateTimePicker.get(Calendar.HOUR) + ":" + mDateTimePicker.get(Calendar.MINUTE) + " "
                            + (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
                }

                button.setText(date + " " + time);
                mDateTimeDialog.dismiss();
            }
        });

        // Cancel the dialog when the "Cancel" button is clicked
        mDateTimeDialogView.findViewById(R.id.CancelDialog).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mDateTimeDialog.cancel();
            }
        });

        // Reset Date and Time pickers when the "Reset" button is clicked
        mDateTimeDialogView.findViewById(R.id.ResetDateTime).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mDateTimePicker.reset();
            }
        });

        // Setup TimePicker
        mDateTimePicker.setIs24HourView(is24h);
        // No title on the dialog window
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the dialog content view
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        // Display the dialog
        mDateTimeDialog.show();
    }
}
