package fr.letroll.framework;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    DBHelper dBHelper;
    Context context;
    String dbname;
    SQLiteDatabase db;

    public DBAdapter(Context context,String name,String createQuery,SQLiteDatabase db,int version) {
        this.context = context;
        this.dbname = name;
        this.db=db;
        dBHelper = new DBHelper(context, dbname, createQuery, version);
    }
    
    public DBAdapter(Context context,String name,String[] createQuerys,SQLiteDatabase db,int version) {
        this.context = context;
        this.dbname = name;
        this.db=db;
        dBHelper = new DBHelper(context, dbname, createQuerys, version);
    }

    public DBAdapter(Context context,String name,String createQuery) {
        this.context = context;
        this.dbname = name;
        dBHelper = new DBHelper(context, dbname, createQuery, 1);
    }
    
    
    public DBAdapter(Context context,String name,String createQuery[]) {
        this.context = context;
        this.dbname = name;
        dBHelper = new DBHelper(context, dbname, createQuery, 1);
    }
    
    public int size() {
        return (int) DatabaseUtils.queryNumEntries(db, dbname);

    }

    public DBAdapter open() {
        db = dBHelper.getWritableDatabase();
        return this;
    }

    public boolean state() {
        return db.isOpen();
    }

    public void close() {
        if (state())
            db.close();
    }

    public void Truncate() {
        db.execSQL("DELETE FROM "+dbname);
    }
}
