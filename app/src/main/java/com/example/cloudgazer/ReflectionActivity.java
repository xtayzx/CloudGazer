package com.example.cloudgazer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class ReflectionActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    private SeekBar thisSeekBar;
    private RadioGroup communitySelect;
    private int rateDayValue;
    EditText inputDate, inputTime, inputLocation, inputCloudDes, inputRateDay, inputDayDes;
    MyDatabase db;
    String inputCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_layout);

        thisSeekBar = (SeekBar)findViewById(R.id.seekBar);
        thisSeekBar.setOnSeekBarChangeListener(examSeekBarListener);

        communitySelect = (RadioGroup)findViewById(R.id.communitySelect);
        communitySelect.setOnCheckedChangeListener(this);

        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText)findViewById(R.id.inputTime);
        inputLocation = (EditText)findViewById(R.id.inputLocation);
        inputCloudDes = (EditText)findViewById(R.id.inputCloudDes);
        inputRateDay = (EditText)findViewById(R.id.rateDay);
        inputDayDes = (EditText) findViewById(R.id.inputDayDes);

        db = new MyDatabase(this);

    }

    public void submit (View view)
    {
        String date = inputDate.getText().toString();
        String time = inputTime.getText().toString();
        String location = inputLocation.getText().toString();
        String cloudDes = inputCloudDes.getText().toString();
        String rateDay = inputRateDay.getText().toString();
        String dayDes = inputDayDes.getText().toString();
        String communitySelect = inputCommunity;
        Toast.makeText(this, date +" "+ time+" "+ location+" "+ cloudDes+" "+ rateDay+" "+ dayDes+" "+ communitySelect, Toast.LENGTH_SHORT).show();
        long id = db.insertData(date, time, location, cloudDes,rateDay,dayDes, communitySelect);
        if (id < 0)
        {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
//        plantName.setText("");
//        plantType.setText("");
//        plantLocation.setText("");
//        plantLatin.setText("");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void home(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

//    public void viewResults(View view)
//    {
//        Intent intent = new Intent(this, RecyclerActivity.class);
//        startActivity(intent);
//    }

    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            rateDayValue = thisSeekBar.getProgress();
            //rateDay.setText(rateDayValue);
            inputRateDay.setText(String.valueOf(rateDayValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId() == thisSeekBar.getId()) {
            rateDayValue = seekBar.getProgress();
            inputRateDay.setText(rateDayValue);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //set color according to selected RadioButton
        switch (checkedId) {
            case R.id.yesRadioButton:
                inputCommunity = "yes";
                break;
            case R.id.noRadioButton:
                inputCommunity = "no";
                break;
        }
    }

//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//
//    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {

    }
}
