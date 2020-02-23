package com.yds.todolist.home.model;

import android.content.ContentValues;

import androidx.annotation.Nullable;

import com.yds.todolist.database.schema.TodoDbSchema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class TodoItem {
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private UUID mId;
    private String mTitle;
    private String mDesc;
    private Date mDate;
    /**
     * how long the user plans to do this todo item
     */
    private int mDuration;
    /**
     * current status of this todo item
     */
    private TodoState mStatus;

    public TodoItem() {
        this.mStatus = TodoState.CREATED;
    }

    public TodoItem(@Nullable UUID id, @Nullable String title, @Nullable String desc, @Nullable Date date) {
        this.mStatus = TodoState.CREATED;
        this.mId = id;
        this.mTitle = title;
        this.mDesc = desc;
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateFormat() {
        if (mDate == null) {
            return "Null";
        }

        return new SimpleDateFormat(DATE_FORMAT).format(mDate);
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public TodoState getStatus() {
        return mStatus;
    }

    public void setStatus(TodoState mStatus) {
        this.mStatus = mStatus;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    /**
     * status type
     */
    public enum TodoState {
        CREATED,
        DOING,
        PENDING,
        DONE,
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TodoDbSchema.TodoTable.Cols.UUID, this.mId.toString());
        values.put(TodoDbSchema.TodoTable.Cols.TITLE, this.mTitle);
        values.put(TodoDbSchema.TodoTable.Cols.DESC, this.mDesc);
        values.put(TodoDbSchema.TodoTable.Cols.DATE, this.getDateFormat());

        return values;
    }
}
