package il.ac.huji.todolist;

import android.view.View;
import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


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
        TextView todoItem = (TextView) view.findViewById(R.id.tv_item);
        TodoOneItem curItem = _objects.get(position);
        todoItem.setText(curItem.get_text());
        TextView todoDate = (TextView) view.findViewById(R.id.tv_date);
        GregorianCalendar dueDate = curItem.get_date();
        if (dueDate == null){
            todoDate.setText("no due date");
        } else {
            String dateToShow = DateFormat.getDateInstance(DateFormat.SHORT).format(dueDate.getTime());
            todoDate.setText(dateToShow);
        }
        // ~*~ handle colors ~*~
        GregorianCalendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DATE, -1);

        // "a.compareTo(b)" method will yield positive if b is in the past of a
        if (dueDate == null || yesterday.compareTo(dueDate) <= 0) { // due date not passed
            todoItem.setTextColor(_context.getResources().getColor(R.color.dueDateOkay));
            todoDate.setTextColor(_context.getResources().getColor(R.color.dueDateOkay));
        } else {
            todoItem.setTextColor(_context.getResources().getColor(R.color.dueDatePassed));
            todoDate.setTextColor(_context.getResources().getColor(R.color.dueDatePassed));
        }

        return view;
    }
}
