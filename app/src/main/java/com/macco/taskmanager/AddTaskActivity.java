package com.macco.taskmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    EditText titleEditText, descriptionEditText;
    TextView dueDateTextView;
    Button saveButton;
    TaskDatabaseHelper dbHelper;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        dbHelper = new TaskDatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dueDateTextView = findViewById(R.id.dueDateTextView);
        saveButton = findViewById(R.id.saveButton);

        calendar = Calendar.getInstance();
        dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDueDate();
        };

        dueDateTextView.setOnClickListener(view -> {
            new DatePickerDialog(AddTaskActivity.this, dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        saveButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String dueDate = dueDateTextView.getText().toString();

            if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(AddTaskActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Task task = new Task(title, description, dueDate);
                dbHelper.addTask(task);
                Toast.makeText(AddTaskActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void updateDueDate() {
        dueDateTextView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.YEAR));
    }
}