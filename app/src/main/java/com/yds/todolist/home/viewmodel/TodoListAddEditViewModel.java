package com.yds.todolist.home.viewmodel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.yds.todolist.BR;
import com.yds.todolist.home.fragment.TodoDetailAddAndEditFragment;
import com.yds.todolist.home.model.TodoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoListAddEditViewModel extends BaseObservable {
    public static final String TAG = TodoListAddEditViewModel.class.getSimpleName();

    private TodoItem mTodoItem;
    private TodoDetailAddAndEditFragment.STATE mState;
    private Context mContext;

    public TodoListAddEditViewModel(Context ctx) {
        this.mContext = ctx;
    }

    public void setData(TodoItem todoItem, TodoDetailAddAndEditFragment.STATE state) {
        this.mTodoItem = todoItem;
        this.mState = state;
    }

    @Bindable
    public String getTitle() {
        return mTodoItem.getTitle();
    }

    public void setTitle(String title) {
        mTodoItem.setTitle(title);
        notifyPropertyChanged(com.yds.todolist.BR.title);
    }

    @Bindable
    public String getDesc() {
        return mTodoItem.getDesc();
    }

    public void setDesc(String desc) {
        mTodoItem.setDesc(desc);
        notifyPropertyChanged(com.yds.todolist.BR.desc);
    }

    @Bindable
    public String getDate() {
        return mTodoItem.getDateFormat();
    }

    public void setDate(String date) {
        try {
            mTodoItem.setDate(new SimpleDateFormat(TodoItem.DATE_FORMAT).parse(date));
            notifyPropertyChanged(com.yds.todolist.BR.date);
        }
        catch (Exception ex) {
            Log.e(TAG, "parse date string failed");
            Log.e(TAG, ex.toString());
        }
    }

    public void setDate(Date date) {
        mTodoItem.setDate(date);
        notifyPropertyChanged(com.yds.todolist.BR.date);
    }

    @Bindable
    public boolean isAdding() {
        return mState == TodoDetailAddAndEditFragment.STATE.ADD;
    }

    public void onClickConfirmBtn(View v) {
        Toast.makeText(
                v.getContext(),
                "Title: " + mTodoItem.getTitle() + "\n" +
                        "Date: " + mTodoItem.getDateFormat() + "\n" +
                        "Desc: " + mTodoItem.getDesc(),
                Toast.LENGTH_SHORT
        ).show();
    }

    public void onClickCancelBtn(View v) {
        Context ctx = v.getContext();
        // TODO: back to home page
    }

    public void onClickEditDateBtn(View v) {
        popupDateandTimePickerDialog();
    }

    /**
     * popup Datepicker and Timepicker dialog to set date
     */
    private void popupDateandTimePickerDialog() {
        final Calendar curDate = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();

        // pop datepicker dialog
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month, dayOfMonth);
                // pop timepicker dialog
                new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDate.set(Calendar.MINUTE, minute);
                        Log.d(TAG, "selected time is " + selectedDate.getTime());
                        setDate(selectedDate.getTime());
                    }
                }, curDate.get(Calendar.HOUR_OF_DAY), curDate.get(Calendar.MINUTE), false).show();
            }
        }, curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH)).show();
    }
}
