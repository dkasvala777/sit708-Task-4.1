package com.macco.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskManager.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, "
                + "description TEXT, "
                + "due_date TEXT)";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    // Method to add a new task to the database
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("due_date", task.getDueDate());

        db.insert("tasks", null, values);
        db.close();
    }

    // Method to update a task in the database
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("due_date", task.getDueDate());

        db.update("tasks", values, "id = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    // Method to delete a task from the database
    public void deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    // Method to get all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String dueDate = cursor.getString(cursor.getColumnIndex("due_date"));

                taskList.add(new Task(id, title, description, dueDate));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
