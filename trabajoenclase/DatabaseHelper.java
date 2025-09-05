package com.example.trabajoenclase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HobbiesDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_HOBBIES = "hobbies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DIFICULTAD = "dificultad";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_HOBBIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_DIFICULTAD + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOBBIES);
        onCreate(db);
    }

    public long insertHobby(String nombre, String dificultad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_DIFICULTAD, dificultad);
        return db.insert(TABLE_HOBBIES, null, values);
    }

    public Cursor getAllHobbies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_HOBBIES, null, null, null, null, null, null);
    }

    public int updateHobby(long id, String nombre, String dificultad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_DIFICULTAD, dificultad);
        return db.update(TABLE_HOBBIES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int deleteHobby(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_HOBBIES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
