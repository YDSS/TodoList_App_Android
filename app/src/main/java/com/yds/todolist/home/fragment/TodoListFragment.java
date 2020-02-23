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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yds.todolist.R;
import com.yds.todolist.common.FragmentNavigator;
import com.yds.todolist.databinding.FragmentTodoListBinding;
import com.yds.todolist.databinding.RowTodoItemBinding;
import com.yds.todolist.home.model.TodoItem;
import com.yds.todolist.home.viewmodel.TodoListItemViewModel;

import java.util.ArrayList;
import java.util.UUID;

public class TodoListFragment extends Fragment {
    public String TAG = TodoListFragment.class.getSimpleName();
    private final static String TODO_ITEM_LIST = "todo_item_list";

    private RecyclerView mTodoListView;
    private TodoListAdapter mAdapter;


    private TodoListFragment() {}

    public static TodoListFragment newInstance(ArrayList todoItems) {

        Bundle args = new Bundle();
        args.putSerializable(TODO_ITEM_LIST, todoItems);

        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initialize layout
//        View layout = inflater.inflate(R.layout.fragment_todo_list, container, false);
        FragmentTodoListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false);
        // find list
        mTodoListView = binding.todoListRecyclerView;
        // set layoutManager unless recyclerView can't work
        mTodoListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // get model from fragment args
//        ArrayList mTodoItems = TodoListDataSource.getInstance().getTodoItems();
        ArrayList todoItems = (ArrayList) getArguments().getSerializable(TODO_ITEM_LIST);
        // set adapter
        mAdapter = new TodoListAdapter(todoItems);
        mTodoListView.setAdapter(mAdapter);

        return binding.getRoot();
    }

    private class TodoListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView mTitleView;
//        private TextView mDateView;
        private RowTodoItemBinding mBinding;

        public TodoListHolder(RowTodoItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.getRoot().setOnClickListener(this);
            mBinding.setViewModel(new TodoListItemViewModel());
        }

        public void bind(TodoItem item) {
            mBinding.getViewModel().setTodoItem(item);
        }

        @Override
        public void onClick(View v) {
            UUID id = mBinding.getViewModel().getId();
            Log.d(TAG, "navigate to todo item " + id);

//            naviToDetailPage(id);
            FragmentNavigator.getInstance().naviToDetailPage(
                    id,
                    getActivity().getSupportFragmentManager()
            );
        }
    }

    private class TodoListAdapter extends RecyclerView.Adapter<TodoListHolder> {
        private ArrayList<TodoItem> mTodoItems;

        public TodoListAdapter(ArrayList<TodoItem> todoItems) {
            mTodoItems = todoItems;
        }

        @NonNull
        @Override
        public TodoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // get inflater from this fragment cause every fragment has its own inflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            RowTodoItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_todo_item, parent, false);
            TodoListHolder holder = new TodoListHolder(binding);

            return holder;
        }

        @Override
        public void onBindViewHolder(TodoListHolder holder, int position) {
            TodoItem item = mTodoItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mTodoItems.size();
        }
    }

    /**
     * navigate to todo detail page,
     *
     *  still not sure that is this resonable that TodoListFrament hold instance
     *  of TodoListDetailFragment
     *
     *  manage the fragment stack, the stack can only have 1 or 2 fragments, one is list fragment,
     *  another is detail fragment. When a new detail fragment opens, the previous
     *  detail fragment (if have) should be removed
     * @param id
     */
//    private void naviToDetailPage(UUID id) {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        // create a detail page fragment
//        Fragment currentDetailFragment = TodoListDetailFragment.newInstance(id);
//        FragmentTransaction trans = ft
//                .replace(R.id.todo_list_container, currentDetailFragment)
//                .addToBackStack(null);
//        trans
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .setCustomAnimations(
//                        R.anim.slip_in_from_right,
//                        R.anim.slip_out_from_right
//                );
//        ft.commit();
//    }
}
