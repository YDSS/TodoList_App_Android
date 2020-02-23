package com.yds.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yds.todolist.common.FragmentNavigator;
import com.yds.todolist.datasource.TodoListDataSource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from data source
        ArrayList todoItems = TodoListDataSource.getInstance(this).getTodoItems();
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.todo_list_container);
//        if (fragment == null) {
//            // create todo list fragment
//            fragment = TodoListFragment.newInstance(todoItems);
//            fm.beginTransaction()
//                    .replace(R.id.todo_list_container, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
        FragmentNavigator.getInstance().initializeHomePage(this, todoItems);

        bindEvents();
    }

    private void bindEvents() {
        this.findViewById(R.id.btn_add_todo_item).setOnClickListener(onClickFloatingAddBtn(this));
    }

    /**
     * listener for clicking floating add button, jump to add page
     * @param context
     * @return
     */
    private View.OnClickListener onClickFloatingAddBtn(final AppCompatActivity context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNavigator.getInstance().naviToAddOrEditPage(context, null);
            }
        };
    }
}
