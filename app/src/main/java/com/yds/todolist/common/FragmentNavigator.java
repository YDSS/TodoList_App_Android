package com.yds.todolist.common;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yds.todolist.R;
import com.yds.todolist.home.fragment.TodoDetailAddAndEditFragment;
import com.yds.todolist.home.fragment.TodoListDetailFragment;
import com.yds.todolist.home.fragment.TodoListFragment;
import com.yds.todolist.home.viewmodel.TodoListAddEditViewModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * FragmentNavigator is a center to manage the navigation between fragments
 */
public class FragmentNavigator {
    private static FragmentNavigator instance = null;

    private FragmentNavigator() {}

    public static FragmentNavigator getInstance() {
        if (instance == null) {
            instance = new FragmentNavigator();
        }

        return instance;
    }


    /**
     * navigate to home page, i.e. list page
     * @param context
     * @param todoItems list items
     */
    public void initializeHomePage(FragmentActivity context, ArrayList todoItems) {
        FragmentManager fm = context.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.todo_list_container);
        if (fragment == null) {
            // create todo list fragment
            fragment = TodoListFragment.newInstance(todoItems);
            fm.beginTransaction()
                    .replace(R.id.todo_list_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * navigate to todo detail page,
     * @param id
     */
    public void naviToDetailPage(UUID id, FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();

        // create a detail page fragment
        Fragment fragment = TodoListDetailFragment.newInstance(id);
        FragmentTransaction trans = ft
                .replace(R.id.todo_list_container, fragment)
                .addToBackStack(null);
        trans
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(
                        R.anim.slip_in_from_right,
                        R.anim.slip_out_from_right
                );
        ft.commit();
    }

    public void naviToAddOrEditPage(FragmentActivity context, @Nullable UUID todoItemId) {
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // create a add or edit fragment
        TodoDetailAddAndEditFragment fragment = TodoDetailAddAndEditFragment.newInstance(todoItemId);

        FragmentTransaction trans = ft
                .replace(R.id.todo_list_container, fragment)
                .addToBackStack(null);
        trans
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(
                        R.anim.slip_in_from_right,
                        R.anim.slip_out_from_right
                );
        ft.commit();
    }
}
