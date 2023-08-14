package com.example.sqlitedemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitedemo.model.Item;

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
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT,category TEXT,price TEXT,date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //get all items and order by desc
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor cs = st.query("items", null, null, null, null, null, order);
        while (cs != null && cs.moveToNext()){
            int id = cs.getInt(0);
            String title = cs.getString(1);
            String category = cs.getString(2);
            String price = cs.getString(3);
            String date = cs.getString(4);
            list.add(new Item(id, title,category, price, date));
        }
        return list;
    }

    //add
    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items",null, values);
    }

    //lay cac item theo date
    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while(cs != null && cs.moveToNext()){
            int id = cs.getInt(0);
            String title = cs.getString(1);
            String category = cs.getString(2);
            String price = cs.getString(3);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    //update
    public int update(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("items", values, whereClause, whereArgs);
    }

    //delete
    public  int delete(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("items", whereClause, whereArgs);
    }

    //search theo ten
    public List<Item> searchByTitle(String key){
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while(cs != null && cs.moveToNext()){
            int id = cs.getInt(0);
            String title = cs.getString(1);
            String category = cs.getString(2);
            String price = cs.getString(3);
            String date = cs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    //search theo loai
    public List<Item> searchByCategory(String c){
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {c};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while(cs != null && cs.moveToNext()){
            int id = cs.getInt(0);
            String title = cs.getString(1);
            String category = cs.getString(2);
            String price = cs.getString(3);
            String date = cs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    //search theo ngay
    public List<Item> searchByDateFromTo(String from, String to){
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cs = st.query("items", null, whereClause, whereArgs, null, null, null);
        while(cs != null && cs.moveToNext()){
            int id = cs.getInt(0);
            String title = cs.getString(1);
            String category = cs.getString(2);
            String price = cs.getString(3);
            String date = cs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
}
