package com.example.baitapchuong9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.baitapchuong9.dal.SQLiteHelper;
import com.example.baitapchuong9.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner sp;
    private EditText eTitle,ePrice,eDate;
    private Button btnAdd,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp=findViewById(R.id.spCategory);
        eTitle=findViewById(R.id.tvTitle);
        ePrice=findViewById(R.id.tvPrice);
        eDate=findViewById(R.id.tvDate);
        btnCancel=findViewById(R.id.btnCancel);
        btnAdd=findViewById(R.id.btnAdd);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if (view==eDate){
            final Calendar c=Calendar.getInstance();
            int year= c.get(Calendar.YEAR);
            int month= c.get(Calendar.MONTH);
            int day= c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(AddActivity.this, (datePicker, y, m, d) -> {
                String mm="",dd="";
                if (m<10){
                    mm="0"+(m+1);
                }
                else mm=(m+1)+"";
                if (d<10){
                    dd="0"+d;
                }
                else dd=d+"";
                eDate.setText(dd+"/"+mm+"/"+y);
            },year,month,day);
            dialog.show();
        }
        if (view==btnCancel){
            finish();
        }
        if (view==btnAdd){
            String t=eTitle.getText().toString();
            String p=ePrice.getText().toString();
            String c=sp.getSelectedItem().toString();
            String d=eDate.getText().toString();
            if(t!=null && p.matches("\\d+")){
                Item item=new Item(t,c,p,d);
                SQLiteHelper db=new SQLiteHelper(this);
                db.addItem(item);
                finish();
            }
            else {
                Toast.makeText(this,"Bạn phải nhập số",Toast.LENGTH_SHORT);
            }
        }
    }
}