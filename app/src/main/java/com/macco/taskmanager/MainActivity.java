package com.macco.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    TaskDatabaseHelper dbHelper;
    Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and Database Helper
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new TaskDatabaseHelper(this);

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch tasks and set up the adapter
        loadTasks();

        // Set up button to add a new task
        addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            // Launch AddTaskActivity to add a new task
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    // Method to load tasks from the database and update the RecyclerView
    private void loadTasks() {
        // Fetch the tasks from the database
        List<Task> tasks = dbHelper.getAllTasks();

        // Create and set the adapter with the updated task list
        taskAdapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload tasks whenever MainActivity comes into focus
        loadTasks();
    }
}
