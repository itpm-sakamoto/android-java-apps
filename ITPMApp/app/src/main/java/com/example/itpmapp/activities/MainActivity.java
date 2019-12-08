package com.example.itpmapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.itpmapp.R;
import com.example.itpmapp.databases.ITPMDataOpenHelper;
import com.example.itpmapp.pojo.TitleDataItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private MainListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 1.ListViewをレイアウトファイルから読み込む。
        mListView = findViewById(R.id.main_list);

        // 2.Adapterを作成する。
        mAdapter = new MainListAdapter(this, R.layout.layout_title_item, new ArrayList<TitleDataItem>());

        // 3.ListViewにAdapterをセットする。
        mListView.setAdapter(mAdapter);

        // 通常のクリックリスナーのセット
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TitleDataItem item = (TitleDataItem)adapterView.getItemAtPosition(position);
                Intent intent = EditActivity.createIntent(MainActivity.this, item.getId(), item.getTitle());
                startActivity(intent);
            }
        });

        // 長押しでのクリックリスナーのセット
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                TitleDataItem item = (TitleDataItem)adapterView.getItemAtPosition(position);
                // 1. データベースのインスタンスを取得する
                SQLiteDatabase db = new ITPMDataOpenHelper(MainActivity.this).getWritableDatabase();
                // 2. データベースからデータを削除する処理
                db.delete(ITPMDataOpenHelper.TABLE_NAME, ITPMDataOpenHelper._ID + "=" + item.getId(), null);
                // 3. データベースを閉じる
                db.close();
                // 4. 画面表示を更新する
                new AllDataLoadTask().execute();
                Toast.makeText(MainActivity.this, item.getTitle() + "を削除しました。", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AllDataLoadTask().execute();
    }

    /**
     * リスト表示用メソッド
     */
    private void displayDataList(List<TitleDataItem> titleDataItems) {
        // アダプター内のデータをリセットする（クリアにする）
        mAdapter.clear();

        // 新しいデータをアダプターに設定する（セットする）
        mAdapter.addAll(titleDataItems);

        // アダプターにデータが変更されたことを教えてあげる（通知する）
        mAdapter.notifyDataSetChanged();
    }

    private class MainListAdapter extends ArrayAdapter<TitleDataItem> {

        private LayoutInflater layoutInflater;
        private int resource;
        private List<TitleDataItem> dataList;

        public MainListAdapter(@NonNull Context context, int resource, @NonNull List<TitleDataItem> objects) {
            super(context, resource, objects);
            this.layoutInflater = LayoutInflater.from(context);
            this.resource = resource;
            this.dataList = objects;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Nullable
        @Override
        public TitleDataItem getItem(int position) {
            return dataList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TitleDataItem item = dataList.get(position);
            TextView titleTextView;
            if (convertView == null) {
                convertView = layoutInflater.inflate(resource, null);
                titleTextView = convertView.findViewById(R.id.title_text_view);
                convertView.setTag(titleTextView);
            } else {
                titleTextView = (TextView) convertView.getTag();
            }
            titleTextView.setText(item.getTitle());

            return convertView;
        }
    }

    private class AllDataLoadTask extends AsyncTask<Void, Void, List<TitleDataItem>> {

        @Override
        protected List<TitleDataItem> doInBackground(Void... voids) {
            // 表示に使うデータを入れるリスト
            List<TitleDataItem> itemList = new ArrayList<>();

            // 読み書き用のデータベースインスタンスを取得する
            SQLiteDatabase db = new ITPMDataOpenHelper(MainActivity.this).getWritableDatabase();

            // データベースから欲しいデータのカーソルを取り出す処理
            Cursor cursor = db.query(
                    ITPMDataOpenHelper.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // カーソルを使ってデータを取り出す処理
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ITPMDataOpenHelper._ID));
                String title = cursor.getString(cursor.getColumnIndex(ITPMDataOpenHelper.COLUMN_TITLE));
                itemList.add(new TitleDataItem(id, title));
            }

            // カーソルを閉じる
            cursor.close();

            // データベースを閉じる
            db.close();
            return itemList;
        }

        @Override
        protected void onPostExecute(List<TitleDataItem> titleDataItems) {
            // 画面表示の更新
            displayDataList(titleDataItems);
        }
    }
}
