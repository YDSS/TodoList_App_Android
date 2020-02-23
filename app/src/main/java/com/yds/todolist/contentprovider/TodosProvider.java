package com.yds.todolist.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yds.todolist.database.helper.TodoBaseHelper;
import com.yds.todolist.database.schema.TodoDbSchema;

public class TodosProvider extends ContentProvider {
    public final static String TAG = TodosProvider.class.getSimpleName();
    private Context mContext;
    private SQLiteDatabase mDB;
    // AUTHORITIES of todo item Provider
    private final static String AUTHORITIES = "com.yds.todolist.todos";
    // provider code of every table
    public final static int TODOS_CODE = 0;

    private final static UriMatcher mMatcher;
    // register AUTHORITIES for every table
    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITIES, TodoDbSchema.TodoTable.NAME, TODOS_CODE);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        try {
            mDB = new TodoBaseHelper(mContext).getWritableDatabase();
        } catch (Exception ex) {
            Log.e(TAG, "open db failed");
            Log.e(TAG, ex.toString());
            return false;
        }

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);

        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        mDB.insert(tableName, null, values);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        return mDB.delete(tableName, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        return mDB.update(tableName, values, selection, selectionArgs);
    }

    @Nullable
    public String getTableName(Uri uri) {
        String tableName = null;

        switch (mMatcher.match(uri)) {
            case TODOS_CODE:
                tableName = TodoDbSchema.TodoTable.NAME;
                break;
        }

        return tableName;
    }

    public static Uri getTodosUri() {
        return Uri.parse("content://" + AUTHORITIES + "/" + TodoDbSchema.TodoTable.NAME);
    }
}
