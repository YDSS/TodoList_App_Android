package com.yds.todolist.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yds.todolist.database.schema.TodoDbSchema;
import com.yds.todolist.home.model.TodoItem;
import com.yds.todolist.mock.MockTodoItems;


public class TodoBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todos.db";
    public static final int version = 1;

    public TodoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TodoDbSchema.TodoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TodoDbSchema.TodoTable.Cols.UUID+ "," +
                TodoDbSchema.TodoTable.Cols.TITLE + "," +
                TodoDbSchema.TodoTable.Cols.DESC + "," +
                TodoDbSchema.TodoTable.Cols.DATE +
                ")"
        );
        // mock and insert a todo item
        TodoItem item = MockTodoItems.mockATodoItem();
        db.insert(TodoDbSchema.TodoTable.NAME, null, item.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
