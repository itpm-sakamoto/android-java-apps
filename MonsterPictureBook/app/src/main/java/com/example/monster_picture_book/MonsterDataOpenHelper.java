package com.example.monster_picture_book;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MonsterDataOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "monsterDB.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_NAME = "monsterdb";
    static final String _ID = "_id";
    static final String COLUMN_NO = "monster_no";
    static final String COLUMN_NAME = "monster_name";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NO + " TEXT, " +
            COLUMN_NAME + " TEXT)";
    private static final String INIT_TABLE = "INSERT INTO " + TABLE_NAME + " VALUES " +
            "(1, '001', 'スライム'),(2, '002', 'ドラキー'),(3, '003', 'スライムベス')," +
            "(4, '004', 'ゴースト'),(5, '005', 'モーモン'),(6, '006', 'しましまキャット')," +
            "(7, '007', 'メーダ'),(8, '008', 'いたずらもぐら'),(9, '009', 'リリパット')," +
            "(10, '010', 'バブルスライム')";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    MonsterDataOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(INIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
