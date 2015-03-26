package il.ac.huji.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Reem on 25/03/2015.
 */
public class AddDialogActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle unused){
        super.onCreate(unused);
        setTitle(getResources().getString(R.string.addDialogTitle));
        setContentView(R.layout.dialog_adding_item);
        final EditText edtText =(EditText) findViewById(R.id.et_add_dialog_text);
        final DatePicker dp =   (DatePicker) findViewById(R.id.dp_add_dialog_date);
        Button btnAdd =         (Button) findViewById(R.id.btn_add_dialog_add);
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtText.getText().toString();
                int d_days = dp.getDayOfMonth();
                int d_months = dp.getMonth();
                int d_years = dp.getYear();
                GregorianCalendar dueDate = new GregorianCalendar(d_years, d_months, d_days);
                String d_string = DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(dueDate.getTime());

                if (cbxNoDue.isChecked()) {
                    d_string = "";
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("text", text);
                returnIntent.putExtra("dueDate", d_string);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
