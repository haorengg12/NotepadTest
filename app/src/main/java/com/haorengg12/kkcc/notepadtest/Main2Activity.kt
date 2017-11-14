package com.haorengg12.kkcc.notepadtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.haorengg12.kkcc.notepadtest.db.textContext
import kotlinx.android.synthetic.main.activity_main2.*
import org.litepal.crud.DataSupport
import java.util.*

class Main2Activity : AppCompatActivity() {
    private var calendar: Calendar? = null
    val TEXT_NUM = "text_num"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val textNum: String = Intent.getIntentOld("text_num").toString()
        input_text.setText(textNum)
        //testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"))
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_36dp)
        }
//        cc123.setOnClickListener { view-> Snackbar.make(view,"dadsd",Snackbar.LENGTH_SHORT).show() }
        val textContexts = DataSupport.findLast(textContext::class.java)
        if (textContexts != null) {
            //判断是否为空，防止闪退
            input_text.setText(textContexts.textContext)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //逻辑
                if ("" != input_text.getText().toString()) {

                    val kk = DataSupport.findLast(textContext::class.java)
                    val test = textContext()
                    val calendar = GregorianCalendar()
                    if (kk != null) {
                        //序号
                        test.textNum = kk.textNum.toInt() + 1
                    } else {
                        test.textNum = 1
                    }
                    test.textContext = input_text.getText().toString()
                    test.textYear = calendar.get(Calendar.YEAR).toString()
                    test.textMon = calendar.get((Calendar.MONTH).toInt() + 1).toString()
                    test.textDay = calendar.get(Calendar.DATE).toString()
                    test.textHour = calendar.get(Calendar.HOUR).toString()
                    test.textMin = calendar.get(Calendar.MINUTE).toString()
                    test.textSec = calendar.get(Calendar.SECOND).toString()
                    test.save()
                }
                super.onBackPressed()//调用防止重复创建活动，配套使用
            }
            else -> return true
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

}
