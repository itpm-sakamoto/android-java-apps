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

        ArrayList<MonsterDataItem> itemList = new ArrayList<>();
        SQLiteDatabase db = new MonsterDataOpenHelper(this).getReadableDatabase();
        db.beginTransaction();
        try (Cursor cursor = db.query(MonsterDataOpenHelper.TABLE_NAME, null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MonsterDataOpenHelper._ID));
                String no = cursor.getString(cursor.getColumnIndex(MonsterDataOpenHelper.COLUMN_NO));
                String name = cursor.getString(cursor.getColumnIndex(MonsterDataOpenHelper.COLUMN_NAME));
                itemList.add(new MonsterDataItem(id, no, name));
            }
            db.endTransaction();
        }

        StringBuilder sb = new StringBuilder();
        for (MonsterDataItem item : itemList) {

            sb.append(item.toString()).append("\n");
        }

        mMonsterListText.setText(sb);
    }
}
