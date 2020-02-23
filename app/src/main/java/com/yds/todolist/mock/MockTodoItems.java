package com.yds.todolist.mock;

import com.yds.todolist.home.model.TodoItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MockTodoItems {

    public static ArrayList<TodoItem> mock() {
        return MockTodoItems.mock(20);
    }

    public static ArrayList<TodoItem> mock(int length) {
        ArrayList list = new ArrayList<TodoItem>();
        for (int i = 0; i < length; i++) {
            TodoItem todo = new TodoItem(
                    UUID.randomUUID(),
                    MockTodoItems.generateTitle(),
                    MockTodoItems.generateDesc(),
                    MockTodoItems.generateDate()
            );
//            todo.setTitle(MockTodoItems.generateTitle());
//            todo.setDuration(MockTodoItems.generateDuration());

            list.add(todo);
        }

        return list;
    }

    public static TodoItem mockATodoItem() {
        return new TodoItem(
                UUID.randomUUID(),
                MockTodoItems.generateTitle(),
                MockTodoItems.generateDesc(),
                MockTodoItems.generateDate()
        );
    }

    private static String generateTitle() {
        return new StringBuilder().append("Mock Title ").append(new Random().nextInt()).toString();
    }

    private static String generateDesc() {
        return new StringBuilder().append("Mock Description ").append(new Random().nextInt()).toString();
    }

    private static Date generateDate() {
        return new Date();
    }

    private static int generateDuration() {
        return 1;
    }
}
