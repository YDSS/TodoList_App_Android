package com.yds.todolist.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.yds.todolist.R;
import com.yds.todolist.common.CommonVariables;
import com.yds.todolist.databinding.FragmentTodoAddAndEditBinding;
import com.yds.todolist.datasource.TodoListDataSource;
import com.yds.todolist.home.model.TodoItem;
import com.yds.todolist.home.viewmodel.TodoListAddEditViewModel;

import java.util.UUID;

public class TodoDetailAddAndEditFragment extends Fragment {

    /**
     * current status of the fragment, adding an item or editing
      */
    public enum STATE {
        ADD,
        EDIT,
    }

    /**
     * current status
     */
    private STATE status;

    public static TodoDetailAddAndEditFragment newInstance(@Nullable UUID todoItemId) {

        Bundle args = new Bundle();

        TodoDetailAddAndEditFragment fragment = new TodoDetailAddAndEditFragment();
        /**
         * determine initialize with which state,
         *  if no item id passed, it's adding state,
         *  otherwise editing state
         */
        if (todoItemId == null) {
            fragment.setStatus(STATE.ADD);
        }
        else {
            fragment.setStatus(STATE.EDIT);
            args.putSerializable(CommonVariables.TODO_ITEM_UUID, todoItemId);
        }

        fragment.setArguments(args);

        return fragment;
    }

    public STATE getStatus() {
        return status;
    }

    /**
     * the status can only be changed by the fragment itself,
     *  so it should always stay private
     * @param status
     */
    private void setStatus(STATE status) {
        this.status = status;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTodoAddAndEditBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_add_and_edit, container, false);

        // get todo item id, if has
        TodoItem todoItem;
        if (getStatus() == STATE.EDIT) {
            Bundle args = getArguments();
            UUID todoItemId = (UUID) args.getSerializable(CommonVariables.TODO_ITEM_UUID);
            todoItem = TodoListDataSource.getInstance(getActivity()).findTodoItemById(todoItemId);
        }
        else {
            todoItem = new TodoItem();
        }
        TodoListAddEditViewModel viewModel = new TodoListAddEditViewModel(this.getActivity());
        viewModel.setData(todoItem, getStatus());
        // set viewModel
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}
