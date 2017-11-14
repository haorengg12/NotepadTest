package com.haorengg12.kkcc.notepadtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.haorengg12.kkcc.notepadtest.db.textContext;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Main3Activity extends AppCompatActivity {
    public static final String TEXT_NUM = "1";
    Calendar calendar;
    private TextInputEditText editText;
    private String textNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        textNum = intent.getStringExtra("1");
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (TextInputEditText) findViewById(R.id.input_text);
        textContext findfirst = DataSupport.findFirst(textContext.class);
        //设置二次编辑逻辑
        if (findfirst != null && textNum != null) {
            List<textContext> textContexts = DataSupport.where("textNum=?", textNum).find(textContext.class);
            if (textContexts != null) {
                for (textContext texttNum : textContexts) {
                    editText.setCursorVisible(false);
                    editText.setText(texttNum.getTextContext());
                    editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            editText.setCursorVisible(true);
                            editText.setSelection(editText.length());
                            return false;
                        }
                    });
                }
            }
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_36dp);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //save
                textContext findfirst = DataSupport.findFirst(textContext.class);
                if (!"".equals(editText.getText().toString()) && textNum == null && findfirst != null) {
                    //othertimes
                    textContext test = new textContext();
                    List<textContext> list2 = DataSupport.order("textNum desc").find(textContext.class);
                    list2 = list2.subList(0, 1);
                    for (textContext kk : list2) {
                        test.setTextNum(String.valueOf(Integer.valueOf(kk.getTextNum()) + 1));
                    }
                    test.setTextContext(editText.getText().toString());
                    test.setTextYear(String.valueOf(calendar.get(Calendar.YEAR)));
                    test.setTextMon(String.valueOf(Integer.valueOf(calendar.get(Calendar.MONTH)) + 1));
                    test.setTextDay(String.valueOf(calendar.get(Calendar.DATE)));
                    test.setTextHour(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                    test.setTextMin(String.valueOf(calendar.get(Calendar.MINUTE)));
                    test.setTextSec(String.valueOf(calendar.get(Calendar.SECOND)));
                    test.save();
                } else if (!"".equals(editText.getText().toString()) && findfirst != null && textNum != null) {
                    //update
                    textContext test = new textContext();
                    List<textContext> list2 = DataSupport.order("textNum desc").find(textContext.class);
                    list2 = list2.subList(0, 1);
                    for (textContext kk : list2) {
                        test.setTextNum(String.valueOf(Integer.valueOf(kk.getTextNum()) + 1));
                    }
                    test.setTextContext(editText.getText().toString());
                    test.setTextYear(String.valueOf(calendar.get(Calendar.YEAR)));
                    test.setTextMon(String.valueOf(Integer.valueOf(calendar.get(Calendar.MONTH)) + 1));
                    test.setTextDay(String.valueOf(calendar.get(Calendar.DATE)));
                    test.setTextHour(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                    test.setTextMin(String.valueOf(calendar.get(Calendar.MINUTE)));
                    test.setTextSec(String.valueOf(calendar.get(Calendar.SECOND)));
                    test.updateAll("textNum=?", textNum);
                } else if (!"".equals(editText.getText().toString()) && textNum == null && findfirst == null) {
                    //first
                    textContext test = new textContext();
                    test.setTextNum("1");
                    test.setTextContext(editText.getText().toString());
                    test.setTextYear(String.valueOf(calendar.get(Calendar.YEAR)));
                    test.setTextMon(String.valueOf(Integer.valueOf(calendar.get(Calendar.MONTH)) + 1));
                    test.setTextDay(String.valueOf(calendar.get(Calendar.DATE)));
                    test.setTextHour(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                    test.setTextMin(String.valueOf(calendar.get(Calendar.MINUTE)));
                    test.setTextSec(String.valueOf(calendar.get(Calendar.SECOND)));
                    test.save();
                }
                super.onBackPressed();
                break;
            default:
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
}
