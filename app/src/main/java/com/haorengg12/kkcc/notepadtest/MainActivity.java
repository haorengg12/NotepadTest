package com.haorengg12.kkcc.notepadtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private FloatingActionsMenu left_labels;
    private FloatingActionButton kk1;
    private FloatingActionButton kk2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kk1:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.kk2:
                left_labels.collapse();
                Toast.makeText(this, "22222222222222", Toast.LENGTH_SHORT).show();
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);//加入nav组件
        navview.setCheckedItem(R.id.setting);
        kk1 = (FloatingActionButton) findViewById(R.id.kk1);//小悬浮按钮1
        kk1.setIcon(R.drawable.ic_note_add_white_24dp);
        kk2 = (FloatingActionButton) findViewById(R.id.kk2); //小悬浮按钮2
        kk2.setIcon(R.drawable.ic_delete_white_24dp);
        left_labels = (FloatingActionsMenu) findViewById(R.id.left_labels);
        kk1.setOnClickListener(this);
        kk2.setOnClickListener(this);
        left_labels.setOnClickListener(this);

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
            case R.id.plus:

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

