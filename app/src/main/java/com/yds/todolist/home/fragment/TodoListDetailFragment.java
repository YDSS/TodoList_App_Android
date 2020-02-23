package com.yds.todolist.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yds.todolist.R;
import com.yds.todolist.common.CommonVariables;
import com.yds.todolist.databinding.FragmentTodoDetailBinding;
import com.yds.todolist.datasource.TodoListDataSource;
import com.yds.todolist.home.model.TodoItem;
import com.yds.todolist.home.viewmodel.TodoListDetailViewModel;

import java.util.UUID;

public class TodoListDetailFragment extends Fragment implements View.OnClickListener {
//    private TextView mTitleView;
//    private TextView mDateView;
//    private TextView mDescView;
    public final static String TAG = TodoListDetailFragment.class.getSimpleName();

    private TodoListDetailFragment() {}

    public static TodoListDetailFragment newInstance(UUID todoItemId) {

        Bundle args = new Bundle();
        args.putSerializable(CommonVariables.TODO_ITEM_UUID, todoItemId);

        TodoListDetailFragment fragment = new TodoListDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTodoDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_detail, container, false);
//        mTitleView = binding.todoDetailItemTitle;
//        mDateView = binding.todoDetailItemDate;
//        mDescView = binding.todoDetailItemDesc;
        // get todo item id from fragment args
        UUID id = (UUID) getArguments().getSerializable(CommonVariables.TODO_ITEM_UUID);
        // set viewmodel
        TodoItem item = TodoListDataSource.getInstance(getActivity()).findTodoItemById(id);
        if (item != null) {
            TodoListDetailViewModel vm = new TodoListDetailViewModel();
            vm.setTodoItem(item);
            binding.setViewModel(vm);
        }
        else {
            Log.e(TAG, "todo item's id: " + id + "not found");
        }

        binding.getRoot().setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    /**
     * prevent touch event go through to List fragment
     */
    public void onClick(View v) {
        Log.d(TAG, "click detail fragment");
    }
}
