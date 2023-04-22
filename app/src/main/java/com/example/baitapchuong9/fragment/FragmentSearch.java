package com.example.baitapchuong9.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapchuong9.AddActivity;
import com.example.baitapchuong9.R;
import com.example.baitapchuong9.adapter.RecycleViewAdapter;
import com.example.baitapchuong9.dal.SQLiteHelper;
import com.example.baitapchuong9.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btnSearch,btnTK;
    private SearchView searchView;
    private EditText eFrom,eTo;
    private Spinner spCategory;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter=new RecycleViewAdapter();
        db=new SQLiteHelper(getContext());
        List<Item> list=db.getAll();
        adapter.setList(list);
        tvTong.setText("Tong tien: "+tinhtong(list));
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list=db.searchByTitle(s);;
                adapter.setList(list);
                tvTong.setText("Tong tien: "+tinhtong(list));
                return true;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnTK.setOnClickListener(this);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cate=spCategory.getItemAtPosition(i).toString();
                List<Item> list;
                if (cate.equalsIgnoreCase("all")){
                    list=db.getAll();

                }
                else {
                    list=db.searchByCategory(cate);
                }
                adapter.setList(list);
                tvTong.setText("Tong tien: "+tinhtong(list));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recycleView);
        tvTong=view.findViewById(R.id.tvTong);
        btnSearch=view.findViewById(R.id.btnSearch);
        btnTK=view.findViewById(R.id.btnTk);
        searchView=view.findViewById(R.id.search);
        eFrom=view.findViewById(R.id.eFrom);
        eTo=view.findViewById(R.id.eTo);
        spCategory=view.findViewById(R.id.spCategory);
        String arr[]=getResources().getStringArray(R.array.category);
        String arr1[]=new String[arr.length+1];
        arr1[0]="All";
        for (int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));
    }

    @Override
    public void onClick(View view) {
        if (view==eFrom){
            final Calendar c=Calendar.getInstance();
            int year= c.get(Calendar.YEAR);
            int month= c.get(Calendar.MONTH);
            int day= c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                    eFrom.setText(dd+"/"+mm+"/"+y);
                }
            },year,month,day);
            dialog.show();
        }
        if (view==eTo){
            final Calendar c=Calendar.getInstance();
            int year= c.get(Calendar.YEAR);
            int month= c.get(Calendar.MONTH);
            int day= c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                    eTo.setText(dd+"/"+mm+"/"+y);
                }
            },year,month,day);
            dialog.show();
        }

        if (view==btnSearch){
            String fromDate=eFrom.getText().toString();
            String toDate=eTo.getText().toString();
            if(fromDate !=null && toDate!=null){
                List<Item> list=db.searchByDateFromTo(fromDate,toDate);
                adapter.setList(list);
                tvTong.setText("Tong tien: "+tinhtong(list));
            }
        }
        if(view==btnTK){
            List<Item> list=db.getStatistic();
            adapter.setList(list);
            tvTong.setText("Tong tien: "+tinhtong(list));
        }

    }
    private int tinhtong(List<Item> list){
        int sum=0;
        for(Item i:list){
            sum+=Integer.parseInt(i.getPrice());
        }
        return  sum;
    }
}
