package com.haorengg12.kkcc.notepadtest.ats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.haorengg12.kkcc.notepadtest.Main3Activity;
import com.haorengg12.kkcc.notepadtest.R;
import com.haorengg12.kkcc.notepadtest.db.textContext;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 黄黄k on 2017-11-12.
 */

public class contextAdapter extends RecyclerView.Adapter<contextAdapter.ViewHolder> {
    private List<textContext> mtextContext;
    private Context mContext;
    Calendar calendar;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNum;
        TextView context_text;
        TextView text_Time;
        CheckBox deleteSelect;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            textNum = (TextView) view.findViewById(R.id.textNum);
            context_text = (TextView) view.findViewById(R.id.context_text);
            text_Time = (TextView) view.findViewById(R.id.text_Time);
            deleteSelect = (CheckBox) view.findViewById(R.id.delete_select);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.context_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        textContext tex = mtextContext.get(viewType);
        if (tex.getVis() != 0) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    textContext context = mtextContext.get(position);
                    textContext textContexts = DataSupport.findLast(textContext.class);
                    Intent intent = new Intent(mContext, Main3Activity.class);
                    intent.putExtra(Main3Activity.TEXT_NUM, String.valueOf(context.getTextNum()));
                    mContext.startActivity(intent);
                }
            });
        } else {
//            删除事件逻辑
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.deleteSelect.isChecked()) {
                        holder.deleteSelect.setChecked(false);
                    } else {
                        holder.deleteSelect.setChecked(true);
                    }
                }
            });
        }
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        textContext tex = mtextContext.get(position);
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        if (tex.getTextDay().equals(String.valueOf(calendar.get(Calendar.DATE)))
                && tex.getTextMon().equals(String.valueOf(calendar.get(Calendar.MONTH) + 1))
                && tex.getTextYear().equals(String.valueOf(calendar.get(Calendar.YEAR)))) {
            holder.text_Time.setText("     " + tex.getTextHour() + ":" + tex.getTextMin());
        } else if (tex.getTextYear().equals(String.valueOf(calendar.get(Calendar.YEAR)))) {
            holder.text_Time.setText("     " + tex.getTextMon() + "/" + tex.getTextDay());
        } else {
            holder.text_Time.setText(tex.getTextYear() + "/" + tex.getTextMon() + "/" + tex.getTextDay());
        }
        holder.deleteSelect.setVisibility(tex.getVis());
        holder.textNum.setText(String.valueOf(tex.getTextNum()));
        holder.context_text.setText(tex.getTextContext());
    }

    public contextAdapter(List<textContext> mmtextContext) {
        mtextContext = mmtextContext;
    }

    @Override
    public int getItemCount() {
        return mtextContext.size();
    }
}
