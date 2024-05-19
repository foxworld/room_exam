package kr.co.ksnet.room_exam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mTodoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mTodoEditText = findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // UI 갱신
        viewModel.getAll().observe(this, todos -> {
            mResultTextView.setText(todos.toString());
        });

        // 버튼클릭시 insert
        findViewById(R.id.add_button).setOnClickListener(v -> {
            viewModel.insert(new Todo(mTodoEditText.getText().toString()));

            //db.todoDao().insert(new Todo(mTodoEditText.getText().toString()));
            //mResultTextView.setText(db.todoDao().getAll().toString());
        });
    }
}