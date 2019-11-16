package com.example.monster_picture_book;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mMonsterListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMonsterListText = findViewById(R.id.monster_list_text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1.表示に使うデータを入れるリスト
        ArrayList<MonsterDataItem> itemList = new ArrayList<>();

        // 2.読み込み専用のデータベースインスタンスを取得する処理
        SQLiteDatabase db = new MonsterDataOpenHelper(this).getReadableDatabase();

        // 3.データベースから欲しいデータのカーソルを取り出す処理
        Cursor cursor = db.query(
                MonsterDataOpenHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // 4.カーソルを使ってデータを取り出す処理
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MonsterDataOpenHelper._ID));
            String no = cursor.getString(cursor.getColumnIndex(MonsterDataOpenHelper.COLUMN_NO));
            String name = cursor.getString(cursor.getColumnIndex(MonsterDataOpenHelper.COLUMN_NAME));
            itemList.add(new MonsterDataItem(id, no, name));
        }

        // 5.カーソルを閉じる
        cursor.close();

        // 6.取り出したデータを表示用に一つの文に加工
        StringBuilder sb = new StringBuilder();
        for (MonsterDataItem item : itemList) {
            sb.append(item.toString()).append("\n");
        }

        // 7.表示
        mMonsterListText.setText(sb);
    }
}
