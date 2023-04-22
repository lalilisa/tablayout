package com.example.baitapchuong9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.baitapchuong9.dal.SQLiteHelper;
import com.example.baitapchuong9.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner sp;
    private EditText eTitle,ePrice,eDate;
    private Button btnUpdate,btnCancel,btnRemove;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btnUpdate.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent=getIntent();
        item=(Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        eDate.setText(item.getDate());
        ePrice.setText(item.getPrice());
        int p=0;
        for (int i=0;i<sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p=i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initView() {
        sp=findViewById(R.id.spCategory);
        eTitle=findViewById(R.id.tvTitle);
        ePrice=findViewById(R.id.tvPrice);
        eDate=findViewById(R.id.tvDate);
        btnCancel=findViewById(R.id.btnCancel);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnRemove=findViewById(R.id.btnRemove);
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
            DatePickerDialog dialog=new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
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
                }
            },year,month,day);
            dialog.show();
        }
        if (view==btnCancel){
            finish();
        }
        if (view==btnUpdate){
            String t=eTitle.getText().toString();
            String p=ePrice.getText().toString();
            String c=sp.getSelectedItem().toString();
            String d=eDate.getText().toString();
            if(t!=null && p.matches("\\d+")){
                int id=item.getId();
                Item item=new Item(id,t,c,p,d);
                SQLiteHelper db=new SQLiteHelper(this);
                db.updateItem(item);
                finish();
            }
        }
        if (view==btnRemove){
            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc muốn xóa mục này không?");
            builder.setIcon(R.drawable.ic_delete);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteHelper db=new SQLiteHelper(getApplicationContext());
                    db.deleteItem(item.getId());
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }
}