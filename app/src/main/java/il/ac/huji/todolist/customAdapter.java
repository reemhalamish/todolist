package il.ac.huji.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Reem on 14/03/2015.
 */
public class customAdapter extends ArrayAdapter<TodoOneItem> {
    protected int _resurce;
    protected List<TodoOneItem> _objects;
    protected int _itemId;
    protected Context _context;

    public customAdapter(Context context,
                         int resource,
                         int itemId,
                         List<TodoOneItem> objects) {
        super(context, resource, itemId, objects);
        this._context = context;
        this._resurce = resource;
        this._itemId = itemId;
        this._objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.one_item_in_list, parent, false);
        TodoOneItem curItem = _objects.get(position);
        TextView txtTodoItem = (TextView) view.findViewById(R.id.tv_item);
        txtTodoItem.setText(curItem.get_text());
        TextView txtTodoDueDate = (TextView) view.findViewById(R.id.tv_date);
        GregorianCalendar dueDate = curItem.get_date();
        if (dueDate == null){
            txtTodoDueDate.setText("no due date");
        } else {
            String dateToShow = DateFormat.getDateInstance(DateFormat.SHORT).format(dueDate.getTime());
            txtTodoDueDate.setText(dateToShow);
        }
        // ~*~ handle colors ~*~
        GregorianCalendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DATE, -1);

        // "a.compareTo(b)" method will yield positive if b is in the past of a
        if (dueDate == null || yesterday.compareTo(dueDate) <= 0) { // due date not passed
            txtTodoItem.setTextColor(_context.getResources().getColor(R.color.dueDateOkay));
            txtTodoDueDate.setTextColor(_context.getResources().getColor(R.color.dueDateOkay));
        } else {
            txtTodoItem.setTextColor(_context.getResources().getColor(R.color.dueDatePassed));
            txtTodoDueDate.setTextColor(_context.getResources().getColor(R.color.dueDatePassed));
        }

        return view;
    }
}
