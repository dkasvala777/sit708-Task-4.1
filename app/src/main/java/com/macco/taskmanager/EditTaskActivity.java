package com.macco.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    EditText titleEditText, descriptionEditText, dueDateEditText;
    Button saveButton, deleteButton;
    TaskDatabaseHelper dbHelper;
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize the EditTexts and Buttons
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Get the task details from the intent
        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId", -1);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String dueDate = intent.getStringExtra("dueDate");

        // Populate the EditTexts with the task data
        titleEditText.setText(title);
        descriptionEditText.setText(description);
        dueDateEditText.setText(dueDate);

        // Initialize the database helper
        dbHelper = new TaskDatabaseHelper(this);

        // Handle saving the edited task
        saveButton.setOnClickListener(v -> {
            String updatedTitle = titleEditText.getText().toString();
            String updatedDescription = descriptionEditText.getText().toString();
            String updatedDueDate = dueDateEditText.getText().toString();

            // Validate that the fields are not empty
            if (updatedTitle.isEmpty() || updatedDescription.isEmpty() || updatedDueDate.isEmpty()) {
                Toast.makeText(EditTaskActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Update the task in the database
                dbHelper.updateTask(new Task(taskId, updatedTitle, updatedDescription, updatedDueDate));

                Toast.makeText(EditTaskActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();

                // Return to the main activity
                finish();
            }
        });

        // Handle deleting the task
        deleteButton.setOnClickListener(v -> {
            // Delete the task from the database
            dbHelper.deleteTask(taskId);

            Toast.makeText(EditTaskActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();

            // Return to the main activity
            finish();
        });
    }
}
