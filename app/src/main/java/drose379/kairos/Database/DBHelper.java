package drose379.kairos.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static String DbName = "owner_ids";
    private String table = "owner_id";
    private String col = "id";

    public DBHelper(Context context) {
        super(context,DbName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String tableQuery = "CREATE TABLE " + table + " (" + col + " INTEGER PRIMARY KEY);";
        database.execSQL(tableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldschema,int newschema) {

    }
}
