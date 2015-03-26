package il.ac.huji.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.GregorianCalendar;

/**
 * Created by Reem on 26/03/2015.
 */
public class AddNewTodoItemActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle unused){
        super.onCreate(unused);
        setTitle(getResources().getString(R.string.addDialogTitle));
        setContentView(R.layout.dialog_adding_item);
        final EditText edtNewItem =(EditText) findViewById(R.id.et_add_dialog_text);
        final DatePicker datePicker =   (DatePicker) findViewById(R.id.dp_add_dialog_date);
        Button btnOK =         (Button) findViewById(R.id.btn_add_dialog_add);
        Button btnCancel =      (Button) findViewById(R.id.btn_add_dialog_cancel);
        final CheckBox cbxNoDue =        (CheckBox) findViewById(R.id.cbx_add_dialog_no_due);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtNewItem.getText().toString();
                int d_days = datePicker.getDayOfMonth();
                int d_months = datePicker.getMonth();
                int d_years = datePicker.getYear();
                GregorianCalendar dueDate = new GregorianCalendar(d_years, d_months, d_days);
                if (cbxNoDue.isChecked()) {
                    dueDate = null;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", text);
                returnIntent.putExtra("dueDate", dueDate);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
