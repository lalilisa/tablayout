package com.example.baitapchuong9.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.baitapchuong9.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDB = "CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "category TEXT,"+
                "price TEXT,"+
                "date TEXT)";
        db.execSQL(sqlCreateDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = sqLiteDatabase.query("items",
                null,null,null,null,null,order);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }
    public void addItem(Item i){
        String sql = "INSERT INTO items(title,category,price,date)"+
                "VALUES(?,?,?,?)";
        String[] args = {i.getTitle(), i.getCategory(), i.getPrice(), i.getDate()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql,args);
    }
    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }
    public void updateItem(Item i) {
        String sql = "UPDATE items SET title = ?, category=? ,price=? ,date=? WHERE id = ?";
        String[] args = {i.getTitle(), i.getCategory(), i.getPrice(),
                i.getDate(),String.valueOf(i.getId())};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql,args);
    }
    public void deleteItem(int id){
        String sql = "DELETE FROM items WHERE id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }
    public List<Item> searchByTitle(String key) {
        List<Item> list= new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }
    public List<Item> searchByDateFromTo(String from,String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = { from.trim(),to.trim()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }
    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {category};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String cate = rs.getString(2);
            String price = rs.getString(3);
            String date  = rs.getString(4);
            list.add(new Item(id,title,cate,price,date));
        }
        return list;
    }
    public List<Item> getStatistic(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<Item> listBook = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("items",new String[]{"id","title","category","MAX(price) AS price","date"},
                null, null,"category",null,"price DESC");
        while(cursor!=null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String price = cursor.getString(3);
            String date = cursor.getString(4);
            Item item = new Item(id,title,category,price,date);
            listBook.add(item);
        }
        return listBook;
    }
}
