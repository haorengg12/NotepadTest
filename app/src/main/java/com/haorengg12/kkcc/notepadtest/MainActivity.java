package com.haorengg12.kkcc.notepadtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kk1:
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
                left_labels.collapse();//收起
                break;
            case R.id.kk2:
                Snackbar.make(v, "Data delete succeed!", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //撤销删除逻辑

                    }
                }).show();
                left_labels.collapse();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.plus://修改

                break;
            default:
        }
        return true;
    }


    private void initcontent() {
        //显示
        textContextList.clear();
        //应当用时间来判断（尚未修改）
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

    @Override
    protected void onResume() {
        super.onResume();
        initcontent();//刷新
        adapter.notifyDataSetChanged();//刷新
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setTitle("确定要退出么");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
}

