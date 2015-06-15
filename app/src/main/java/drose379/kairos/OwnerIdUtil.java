package drose379.kairos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

import drose379.kairos.Database.DBHelper;

public class OwnerIdUtil {

    public static int getOwnerID(Context context) {
        int ownerId;

        DBHelper dbHelper = new DBHelper(context);

        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        Cursor result = dbRead.rawQuery("SELECT * FROM owner_id",null);

        if (result.moveToFirst()) {
            ownerId = result.getInt(result.getColumnIndex("id"));
            Log.i("ownerID",result.getColumnName(0));
        } else {
            Random rand = new Random();
            ownerId = rand.nextInt(100000000);
            SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
            dbWrite.execSQL("INSERT INTO owner_id (id) VALUES" + "(" + ownerId + ")");
        }

        return ownerId;
    }

}
