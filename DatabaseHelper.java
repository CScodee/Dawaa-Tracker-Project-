package com.example.dawaatracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "medicines.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE = "medicine";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DOSE = "dose";
    private static final String COL_TIMES = "times_per_day";
    private static final String COL_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_DOSE + " TEXT, "
                + COL_TIMES + " INTEGER, "
                + COL_NOTES + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // INSERT
    public long insertMedicine(Medicine m) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, m.getName());
        values.put(COL_DOSE, m.getDose());
        values.put(COL_TIMES, m.getTimesPerDay());
        values.put(COL_NOTES, m.getNotes());
        long result = db.insert(TABLE, null, values);
        db.close();
        return result;
    }

    // DELETE
    public int deleteMedicine(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // SELECT ALL
    public List<Medicine> getAllMedicines() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, null, null, null, null, COL_NAME + " ASC");
        List<Medicine> list = readCursor(cursor);
        db.close();
        return list;
    }

    // SEARCH by name
    public List<Medicine> searchMedicines(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, COL_NAME + " LIKE ?",
                new String[]{"%" + keyword + "%"}, null, null, COL_NAME + " ASC");
        List<Medicine> list = readCursor(cursor);
        db.close();
        return list;
    }

    private List<Medicine> readCursor(Cursor cursor) {
        List<Medicine> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            String dose = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOSE));
            int times = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TIMES));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
            list.add(new Medicine(id, name, dose, times, notes));
        }
        cursor.close();
        return list;
    }
}
