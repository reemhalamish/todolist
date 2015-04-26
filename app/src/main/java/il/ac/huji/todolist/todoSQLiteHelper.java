package il.ac.huji.todolist;

/**
 * this class is the main gate between the SQLite database and the MainActivity
 * Created by Reem on 26/04/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class todoSQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "todo_db";
    // tasks table name

    // tasks Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DUE = "due";
    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_DUE};


    public todoSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create todo_table
        String CREATE_TODO_TABLE = "CREATE TABLE "+DATABASE_NAME+" ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT, "+
                KEY_DUE + " INTEGER )";

        // create todo_table
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older todo_table if existed
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);

        // create fresh todo_table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", update, delete) todo_tasks + get all tasks
     */
    public void addTask(TodoOneItem item){
        Log.d("addTask", item.get_text());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.get_text()); // get title
        values.put(KEY_DUE, item.get_date().getTimeInMillis()); // get author

        // 3. insert
        db.insert(DATABASE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Get All tasks
    public List<TodoOneItem> getAllTasks() {
        List<TodoOneItem> tasks = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + DATABASE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build a task and add it to list
        TodoOneItem task;
        if (cursor.moveToFirst()) {
            do {
                int taskId = Integer.parseInt(cursor.getString(0));
                String titleOfTask = cursor.getString(1);
                long dueDateAsLong = Long.parseLong(cursor.getString(2));
                GregorianCalendar dueDateAsCalendar = new GregorianCalendar();
                dueDateAsCalendar.setTimeInMillis(dueDateAsLong);
                task = new TodoOneItem(titleOfTask, dueDateAsCalendar, taskId);
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        Log.d("getAlltasks()", tasks.toString());

        // return the tasks list
        return tasks;
    }

    // Updating single task
    public int updateTask(TodoOneItem task) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.get_text()); // get title
        values.put(KEY_DUE, task.get_date().getTimeInMillis()); // get due date

        // 3. updating row
        int i = db.update(DATABASE_NAME, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(task.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single task
    public void deleteTask(TodoOneItem task) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(DATABASE_NAME,
                KEY_ID+" = ?",
                new String[] { String.valueOf(task.getId()) });

        // 3. close
        db.close();

        Log.d("deleteTask", task.get_text());

    }
}
