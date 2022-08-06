package com.askvsk.sl003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    EditText personCnt,totalBill;
    TextView finalAmt, yourSplit, otherSplit,splitLabel, tipLabel,amtSplit,Tip;
    SeekBar splitBar, tipBar;
    Button resetButton ;

    int nPersonCnt = 1,nSplitMaxValue, nTipMaxValue, splitBarProgress, tipBarProgress;
    double dTotalBill = 0,dAmtSplit = 0, dTip = 0, dFinalAmt =0 , dYourSplit =0, dOtherSplit =0, dYourExtra =0, dOtherTip=0;

    TipConstants tc = new TipConstants();

    public void init()
    {
        tipLabel = (TextView) findViewById(R.id.tipLabel) ;
        splitLabel = (TextView) findViewById(R.id.splitLabel);
        personCnt = (EditText) findViewById(R.id.personCnt);
        totalBill = (EditText) findViewById(R.id.totalBill);
        amtSplit = (TextView) findViewById(R.id.amtSplit);
        Tip = (TextView) findViewById(R.id.Tip);
        splitBar = (SeekBar) findViewById(R.id.splitBar);
        nSplitMaxValue=splitBar.getMax();
        splitBar.setProgress(tc.splitMax);
        splitBarProgress = tc.splitMax;
        tipBar = (SeekBar) findViewById(R.id.tipBar);
        nTipMaxValue=tipBar.getMax();
        tipBar.setProgress(tc.tipDefault);
        tipBarProgress = tc.tipDefault;
        finalAmt = (TextView) findViewById(R.id.finalAmt);
        yourSplit = (TextView) findViewById(R.id.yourSplit);
        otherSplit = (TextView) findViewById(R.id.otherSplit);
        resetButton = findViewById(R.id.resetButton);

    }

    public void process()
    {
        changeListners();
    }


    public void mainCalc()
    {
        dAmtSplit = (dTotalBill * splitBarProgress ) /100;
        amtSplit.setText(Double.toString(dAmtSplit));
        dTip = (dTotalBill * tipBarProgress ) /100;
        dOtherTip = (dAmtSplit * tipBarProgress ) /100;
        Tip.setText(Double.toString(dTip));
        dFinalAmt = dTotalBill + ((dTotalBill * tipBarProgress ) /100);;
        finalAmt.setText(Double.toString(dFinalAmt));

        dYourExtra = dTotalBill - dAmtSplit;
        dOtherSplit = (dAmtSplit + dOtherTip)/nPersonCnt;
        otherSplit.setText(Double.toString(round(dOtherSplit)));
        dYourSplit = dYourExtra + dOtherSplit + ((dYourExtra * tipBarProgress ) /100);
        yourSplit.setText(Double.toString(round(dYourSplit)));
    }


    private void changeListners() {

        personCnt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                }
            }
        });

        personCnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(personCnt.getText().toString()))
                    nPersonCnt = Integer.parseInt(personCnt.getText().toString());
                Log.i( "INFO" , " Person Count is -------   "+nPersonCnt);
                mainCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        totalBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //totalBill.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(totalBill.getText().toString()))
                    dTotalBill= Double.parseDouble(totalBill.getText().toString());
                Log.i( "INFO" , " Total Bill is -------   "+dTotalBill);
                mainCalc();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mainCalc();
            }
        });


        amtSplit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(amtSplit.getText().toString()))
                    dAmtSplit= Double.parseDouble(amtSplit.getText().toString());
                Log.i( "INFO" , " Amount to Split is -------   "+dAmtSplit);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(Tip.getText().toString()))
                    dTip= Double.parseDouble(Tip.getText().toString());
                Log.i( "INFO" , " Amount to Split is -------   "+dTip);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        splitBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                splitBarProgress = progress;
                dAmtSplit = (dTotalBill * splitBarProgress ) /100;
                splitLabel.setText(Integer.toString(splitBarProgress)+"%");
                amtSplit.setText(Double.toString(dAmtSplit));
                mainCalc();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mainCalc();
            }
        });

        tipBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipBarProgress = progress;
                dTip = (dTotalBill * tipBarProgress ) /100;
                tipLabel.setText(Integer.toString(tipBarProgress)+"%");
                Tip.setText(Double.toString(dTip));
                mainCalc();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mainCalc();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personCnt.setText("");
                nPersonCnt = tc.personDefault;
                totalBill.setText("");
                dTotalBill = tc.defaultVal;
                amtSplit.setText("");
                dAmtSplit = tc.defaultVal;
                Tip.setText("");
                dTip =  tc.defaultVal;
                finalAmt.setText("");
                yourSplit.setText("");
                otherSplit.setText("");
                splitBar.setProgress(tc.splitMax);
                tipBar.setProgress(tc.tipDefault);
                splitLabel.setText(Integer.toString(tc.splitMax)+"%");
                tipLabel.setText(Integer.toString(tc.tipDefault)+"%");
                splitBarProgress = tc.splitMax;
                tipBarProgress =  tc.tipDefault;


            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        process();

    }
}
