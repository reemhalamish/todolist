package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class TodoListManagerActivity extends ActionBarActivity {

    // NOTICE: the "ADD" button is with an icon, I think it adds a little bit to the overall feeling :)
    final static int REQUEST_ADD_ITEM = 1;


    List<TodoOneItem> actualList = new ArrayList<TodoOneItem>();
    customAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lv_todoList = (ListView) findViewById(R.id.lv_todoList);
        aa = new customAdapter(this, R.layout.one_item_in_list, R.id.tv_item, actualList);
        lv_todoList.setAdapter(aa);
        registerForContextMenu(lv_todoList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_add:
                openDialogAdd();
                return true;

                    // anything else:
            default:
                return false;
        }
    }

    protected void openDialogAdd() {
        Intent addItemIntent = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(addItemIntent, REQUEST_ADD_ITEM);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){
        if (reqCode == 1) {
            if (resCode == RESULT_OK) {
                String itemText = data.getStringExtra("title");
                GregorianCalendar itemDueDateG =
                        (GregorianCalendar) data.getSerializableExtra("dueDate");
                TodoOneItem newbie = new TodoOneItem(itemText, itemDueDateG);
                actualList.add(newbie);
                aa.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if (v.getId() == R.id.lv_todoList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            TodoOneItem curItem = actualList.get(info.position);
            menu.setHeaderTitle(curItem.get_text());
            menu.add(menu.NONE, 0, 0, "delete");
            String callNumber = curItem.getCallNumber();
            if (callNumber != null) {
                menu.add(menu.NONE, 1, 1, "call "+callNumber);
            }
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        // 0 for delete, 1 for calling. [as written in onCreateContextMenu() ]

        switch (menuItemIndex) {
            case 0:
                openDialogDeleteItem(info.position);
                break;
            case 1:
                callItem(info.position);
                break;
        }
        return true;
    }

    protected void callItem(int position) {
        String phoneNumber = actualList.get(position).getCallNumber();
        Intent dial = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:"+phoneNumber));
        startActivity(dial);
    }

    protected void openDialogDeleteItem(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TodoOneItem item = actualList.get(index);
        String text = item.get_text();
        String msg = "You are about to DELETE " + text + ". Are you sure?";
        builder.setTitle("Really delete?");
        builder.setMessage(msg);
        // Set up the buttons
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actualList.remove(index);
                aa.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}