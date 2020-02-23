package com.yds.todolist.home.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.yds.todolist.home.model.TodoItem;

import java.util.UUID;

public class TodoListItemViewModel extends BaseObservable {
    private TodoItem mTodoItem;

    public TodoItem getTodoItem() {
        return mTodoItem;
    }

    public void setTodoItem(TodoItem mTodoItem) {
        this.mTodoItem = mTodoItem;
//        notifyPropertyChanged(com.yds.todolist.BR.viewModel);
        notifyChange();
    }

    public UUID getId() {
        return mTodoItem.getId();
    }

    @Bindable
    public String getTitle() {
        return mTodoItem.getTitle();
    }

    @Bindable
    public String getDate() {
        return mTodoItem.getDateFormat();
    }
}
