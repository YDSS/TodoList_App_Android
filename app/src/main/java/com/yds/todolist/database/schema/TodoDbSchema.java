package com.yds.todolist.database.schema;

public class TodoDbSchema {
    public static final class TodoTable {
        public static final String NAME = "todos";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESC = "description";
            public static final String DATE = "date";
        }
    }
}