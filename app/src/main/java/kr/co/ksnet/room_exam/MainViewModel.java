package kr.co.ksnet.room_exam;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private AppDatabase db;
    public  LiveData<List<Todo>> todos;
    public String newTodo;

    public MainViewModel(@NotNull Application application) {
        super(application);
        this.db = Room.databaseBuilder(application, AppDatabase.class, "todo-db")
                //.allowMainThreadQueries()
                .build();
        todos = getAll();
    }

    public void insert(String todo) {

        new InsertAsyncTask(db.todoDao()).execute(new Todo(todo));
    }

    public LiveData<List<Todo>> getAll(){
        return db.todoDao().getAll();
    }

    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao mTodoDao;
        public InsertAsyncTask(TodoDao todoDao) {
            this.mTodoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mTodoDao.insert(todos[0]);
            return null;
        }

    }
}
