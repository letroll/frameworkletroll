package fr.letroll.framework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    
    private final Context context; 
    
    String createQuery,name;
    String[] createQuerys;
    int version;
    
    public DBHelper(Context context, String name,String createQuery, int version) {
        super(context, name,null, version);
        this.name=name;
        this.context = context;
        this.createQuery = createQuery;
        this.version = version;
    }
    
    public DBHelper(Context context, String name,String[] createQuerys, int version) {
        super(context, name,null, version);
        this.name=name;
        this.context = context;
        this.createQuerys = createQuerys;
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(createQuery!=null){
            db.execSQL(createQuery);   
        }else{
            for(int i=0;i<createQuerys.length;i++)
                db.execSQL(createQuerys[i]);
        }
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Notification.toastc(context, "Update of database version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+name);
        onCreate(db);
    }

    public String getName() {
        return name;
    }

}
