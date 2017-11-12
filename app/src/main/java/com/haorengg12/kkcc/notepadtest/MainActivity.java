package com.haorengg12.kkcc.notepadtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
                left_labels.collapse();//收起
                break;
            case R.id.kk2:
                Snackbar.make(v, "Data delete succeed!", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //撤销删除逻辑

                        //撤销删除逻

                    }
                }).show();//忘记写.show()两次
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
        left_labels.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
//                Toast.makeText(MainActivity.this, "dsadsad", Toast.LENGTH_SHORT).show();
//                Snackbar.make(left_labels, "dsadsad", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuCollapsed() {

            }
        });
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

