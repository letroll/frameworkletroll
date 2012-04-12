package fr.letroll.framework.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class Adapter {
    Helper dBHelper;
    Context context;
    String dbname;
    SQLiteDatabase db;

    public Adapter(Context context,String name,String createQuery,SQLiteDatabase db,int version) {
        this.context = context;
        this.dbname = name;
        this.db=db;
        dBHelper = new Helper(context, dbname, createQuery, version);
    }
    
    public Adapter(Context context,String name,String[] createQuerys,SQLiteDatabase db,int version) {
        this.context = context;
        this.dbname = name;
        this.db=db;
        dBHelper = new Helper(context, dbname, createQuerys, version);
    }

    public Adapter(Context context,String name,String createQuery) {
        this.context = context;
        this.dbname = name;
        dBHelper = new Helper(context, dbname, createQuery, 1);
    }
    
    
    public Adapter(Context context,String name,String createQuery[]) {
        this.context = context;
        this.dbname = name;
        dBHelper = new Helper(context, dbname, createQuery, 1);
    }
    
    public int size() {
        return (int) DatabaseUtils.queryNumEntries(db, dbname);

    }

    public Adapter open() {
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
