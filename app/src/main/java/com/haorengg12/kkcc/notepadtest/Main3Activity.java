package com.haorengg12.kkcc.notepadtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
    private Button deleteButton;
    private static final String TAG = "Main3Activity";

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
        deleteButton = (Button) findViewById(R.id.delete_button);
        delete();
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
                textContext test = new textContext();
                if (!"".equals(editText.getText().toString()) && textNum == null && findfirst != null) {
                    //othertimes
                    List<textContext> list2 = DataSupport.order("textNum desc").find(textContext.class);
                    list2 = list2.subList(0, 1);
                    for (textContext kk : list2) {
                        test.setTextNum(kk.getTextNum() + 1);
                        getTime(test);
                    }
                    test.save();
                } else if (!"".equals(editText.getText().toString()) && findfirst != null && textNum != null) {
                    //update
                    List<textContext> list2 = DataSupport.order("textNum desc").find(textContext.class);
                    list2 = list2.subList(0, 1);
                    for (textContext kk : list2) {
                        test.setTextNum(kk.getTextNum() + 1);
                    }
                    getTime(test);
                    test.updateAll("textNum=?", textNum);
                } else if (!"".equals(editText.getText().toString()) && textNum == null && findfirst == null) {
                    //first
                    test.setTextNum(1);
                    getTime(test);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.delete_menu).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public void delete() {
        //删除方法
        if (textNum != null) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Main3Activity.this);
                    dialog.setCancelable(true);
                    dialog.setTitle("Worring！");
                    dialog.setMessage("确定要删除么？\n（本次删除不可复原）");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataSupport.deleteAll(textContext.class, "textNum=?", textNum);
                            onBackPressed();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    public void getTime(textContext test) {
        //获取时间方法
        test.setVis(0);
        test.setTextContext(editText.getText().toString());
        test.setTextYear(String.valueOf(calendar.get(Calendar.YEAR)));
        if (String.valueOf(calendar.get(Calendar.MONTH)).length() == 1) {
            test.setTextMon(0 + String.valueOf(calendar.get(Calendar.MONTH)));
        } else {
            test.setTextMon(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        }
        if (String.valueOf(calendar.get(Calendar.DATE)).length() == 1) {
            test.setTextDay(0 + String.valueOf(calendar.get(Calendar.DATE)));
        } else {
            test.setTextDay(String.valueOf(calendar.get(Calendar.DATE)));
        }
        if (String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)).length() == 1) {
            test.setTextHour(0 + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        } else {
            test.setTextHour(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        }
        if (String.valueOf(calendar.get(Calendar.MINUTE)).length() == 1) {
            test.setTextMin(0 + String.valueOf(calendar.get(Calendar.MINUTE)));
        } else {
            test.setTextMin(String.valueOf(calendar.get(Calendar.MINUTE)));
        }
    }
}
