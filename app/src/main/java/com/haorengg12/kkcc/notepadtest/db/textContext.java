package com.haorengg12.kkcc.notepadtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 黄黄k on 2017-11-10.
 */

public class textContext extends DataSupport {

    //序号
    private int textNum;
    //时间
    private String textYear;
    private String textMon;
    private String textDay;
    private String textHour;
    private String textMin;
    private String textSec;
    //基础信息
    private String textContext;
    private String textTag;//text的类别的标签
    private int vis = 8;//gone

    //getter & setter
    public int getTextNum() {
        return textNum;
    }

    public void setTextNum(int textNum) {
        this.textNum = textNum;
    }

    public String getTextYear() {
        return textYear;
    }

    public void setTextYear(String textYear) {
        this.textYear = textYear;
    }

    public String getTextMon() {
        return textMon;
    }

    public void setTextMon(String textMon) {
        this.textMon = textMon;
    }

    public String getTextDay() {
        return textDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public String getTextHour() {
        return textHour;
    }

    public void setTextHour(String textHour) {
        this.textHour = textHour;
    }

    public String getTextMin() {
        return textMin;
    }

    public void setTextMin(String textMin) {
        this.textMin = textMin;
    }

    public String getTextSec() {
        return textSec;
    }

    public void setTextSec(String textSec) {
        this.textSec = textSec;
    }

    public String getTextContext() {
        return textContext;
    }

    public void setTextContext(String textContext) {
        this.textContext = textContext;
    }

    public String getTextTag() {
        return textTag;
    }

    public void setTextTag(String textTag) {
        this.textTag = textTag;
    }

    public int getVis() {
        return vis;
    }

    public void setVis(int vis) {
        this.vis = vis;
    }
}
