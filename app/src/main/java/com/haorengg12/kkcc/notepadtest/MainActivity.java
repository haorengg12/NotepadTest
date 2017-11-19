package com.haorengg12.kkcc.notepadtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.haorengg12.kkcc.notepadtest.ats.contextAdapter;
import com.haorengg12.kkcc.notepadtest.db.textContext;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private FloatingActionsMenu left_labels;
    private FloatingActionButton kk1;
    private FloatingActionButton kk2;
    private List<textContext> textContextList = new ArrayList<>();
    private contextAdapter adapter;
    Calendar calendar;
    private CheckBox deleteSelect;
    MenuItem delete_menu;
    private static final String TAG = "MainActivity";
    private static final int UPDATE_TEXT = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    initcontent2();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kk1:
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
                left_labels.collapse();//收起
                break;
            case R.id.kk2:
                //删除
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        handler.sendMessage(msg);
                    }
                }).start();
                this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                toolbar:
                invalidateOptionsMenu();
//                Snackbar.make(v, "Data delete succeed!", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
//                    @Overri de
//                    public void onClick(View v) {
//                        //撤销删除逻辑
//
//                    }
//                }).show();
//                left_labels.collapse();//collapse
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用于防止点击桌面快捷方式重新运行
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        setContentView(R.layout.activity_main);
        Connector.getDatabase();//如何判断是否第一次运行
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        initcontent();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.context_recy);
        deleteSelect = (CheckBox) findViewById(R.id.delete_select);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new contextAdapter(textContextList);
        recyclerView.setAdapter(adapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final NavigationView navview = (NavigationView) findViewById(R.id.nav_view);//加入nav组件
        navview.setCheckedItem(R.id.setting);
        kk1 = (FloatingActionButton) findViewById(R.id.kk1);//小悬浮按钮1
        kk1.setIcon(R.drawable.ic_note_add_white_24dp);
        kk2 = (FloatingActionButton) findViewById(R.id.kk2); //小悬浮按钮2
        kk2.setIcon(R.drawable.ic_delete_white_24dp);
        left_labels = (FloatingActionsMenu) findViewById(R.id.left_labels);
        kk1.setOnClickListener(this);
        kk2.setOnClickListener(this);
        //大FAB的点击事件
//        left_labels.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
//            @Override
//            public void onMenuExpanded() {
//                Toast.makeText(MainActivity.this, "dsadsad", Toast.LENGTH_SHORT).show();
//                Snackbar.make(left_labels, "dsadsad", Snackbar.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onMenuCollapsed() {
//
//            }
//        });
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                return true;
            }
        });
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);
        }
    }

    private void initcontent() {
        textContextList.clear();
        //show
        List<textContext> textContexts = DataSupport.order("textNum desc").find(textContext.class);
        if (textContexts != null) {
            //判断是否为空，防止闪退
            for (textContext tt : textContexts) {
                textContext text = new textContext();
                text.setTextNum(tt.getTextNum());
                text.setTextContext(tt.getTextContext());
                text.setTextYear(tt.getTextYear());
                text.setTextMon(tt.getTextMon());
                text.setTextDay(tt.getTextDay());
                text.setTextHour(tt.getTextHour());
                text.setTextMin(tt.getTextMin());
                textContextList.add(text);
            }
        }
    }

    private void initcontent2() {
        textContextList.clear();
        //show
        List<textContext> textContexts = DataSupport.order("textNum desc").find(textContext.class);
        if (textContexts != null) {
            //判断是否为空，防止闪退
            for (textContext tt : textContexts) {
                textContext text = new textContext();
                text.setTextNum(tt.getTextNum());
                text.setTextContext(tt.getTextContext());
                text.setTextYear(tt.getTextYear());
                text.setTextMon(tt.getTextMon());
                text.setTextDay(tt.getTextDay());
                text.setTextHour(tt.getTextHour());
                text.setTextMin(tt.getTextMin());
                text.setVis(tt.getVis());
                textContextList.add(text);
            }
        }
        adapter.notifyDataSetChanged();//刷新
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
        toolbar:
        invalidateOptionsMenu();
        initcontent();//刷新
        adapter.notifyDataSetChanged();//刷新
    }

    //    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setCancelable(true);
//        dialog.setTitle("确定要退出么");
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        dialog.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.delete_menu:
                //删除逻辑
                textContext text = new textContext();
                if (deleteSelect.isChecked()) {
                    String kk = String.valueOf(text.getTextNum());
                    DataSupport.deleteAll(textContext.class, "textNum=?", kk);
                }
                break;
            default:
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        textContext findfirst = DataSupport.findFirst(textContext.class);
        textContext findlast = DataSupport.findLast(textContext.class);
        delete_menu = menu.findItem(R.id.delete_menu);//获取全局实例（只可以放这里）
        if (findfirst != null) {
            Log.d(TAG, "onPrepareOptionsMenu: " + findfirst + "  " + findlast);
            if (findfirst.getTextNum() != findlast.getTextNum()) {
                //逻辑用于防止一次运行和二次运行出错（只有一个的第二次运行不显示删除menu）
                if (findViewById(R.id.delete_select).getVisibility() != View.GONE) {
                    //如果第一次运行，找不到控件，Object为空
                    delete_menu.setVisible(true);
                } else {
                    delete_menu.setVisible(false);
                }
            } else {
                delete_menu.setVisible(false);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

}

