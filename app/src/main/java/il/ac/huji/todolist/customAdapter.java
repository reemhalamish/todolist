package il.ac.huji.todolist;


import android.view.View;
import android.widget.ArrayAdapter;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Reem on 14/03/2015.
 */
public class customAdapter extends ArrayAdapter<String> {
    protected int _resurce;
    protected List<String> _objects;
    protected int _itemId;
    protected Context _context;

    public customAdapter(Context context,
                         int resource,
                         int itemId,
                         List<String> objects) {
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
        todoItem.setText(_objects.get(position));
        if (position % 2 == 0) { // even
            todoItem.setTextColor(_context.getResources().getColor(R.color.evenItemColor));
        } else {
            todoItem.setTextColor(_context.getResources().getColor(R.color.oddItemColor));
        }
        return view;
    }
}
