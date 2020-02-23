package com.yds.todolist.datasource;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yds.todolist.contentprovider.TodosProvider;
import com.yds.todolist.database.schema.TodoDbSchema;
import com.yds.todolist.home.model.TodoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TodoListDataSource {
    public final static String TAG = TodoListDataSource.class.getSimpleName();
    private static Context mContext;
    private static TodoListDataSource instance;
    private static Uri TODO_URI = TodosProvider.getTodosUri();

    private TodoListDataSource() {
    }

    public static TodoListDataSource getInstance(@Nullable Context ctx) {
        if (mContext == null) {
            mContext = ctx;
        }
        if (instance == null) {
            instance = new TodoListDataSource();
        }

        return instance;
    }

    public ArrayList<TodoItem> getTodoItems() {
        String[] projections = {
                TodoDbSchema.TodoTable.Cols.UUID,
                TodoDbSchema.TodoTable.Cols.TITLE,
                TodoDbSchema.TodoTable.Cols.DATE,
                TodoDbSchema.TodoTable.Cols.DESC,
        };

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(TODO_URI, projections, null, null, null, null);
        ArrayList todoitems = new ArrayList();
        while (cursor.moveToNext()) {
            TodoItem item = new TodoItem();
            item.setId(UUID.fromString(cursor.getString(0)));
            item.setTitle(cursor.getString(1));
            Date date = null;
            try {
                date = new SimpleDateFormat(TodoItem.DATE_FORMAT).parse(cursor.getString(2));
            }
            catch (Exception ex) {
                Log.e(TAG, "parse date failed");
                Log.d(TAG, ex.toString());
            }
            item.setDesc(cursor.getString(3));
            item.setDate(date);

            todoitems.add(item);
        }

        return todoitems;
    }

    public void addTodoItem(TodoItem item) {
        ContentResolver resolver = mContext.getContentResolver();
        resolver.insert(TODO_URI, item.toContentValues());
    }

    /**
     * @param id
     * @return TodoItem | null
     */
    @Nullable
    public TodoItem findTodoItemById(UUID id) {
        ArrayList<TodoItem> todoItems = getTodoItems();
        TodoItem result = null;
        for (TodoItem item: todoItems) {
            if (item.getId().equals(id)) {
                result = item;
                break;
            }
        }

        return result;
    }
}
