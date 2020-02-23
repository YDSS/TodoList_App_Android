package com.yds.todolist.home.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.yds.todolist.home.model.TodoItem;

public class TodoListDetailViewModel extends BaseObservable {
    private TodoItem mTodoItem;

    public void setTodoItem(TodoItem item) {
        mTodoItem = item;
        notifyChange();
    }

    @Bindable
    public String getTitle() {
        return mTodoItem.getTitle();
    }

    @Bindable
    public String getDate() {
        return mTodoItem.getDateFormat();
    }

    @Bindable
    public String getDesc() {
        return mTodoItem.getDesc();
    }
}
