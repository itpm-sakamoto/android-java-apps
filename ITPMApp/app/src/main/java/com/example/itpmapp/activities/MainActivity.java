package com.example.itpmapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.itpmapp.R;
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
//    private ArrayAdapter<String> mAdapter;
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
//        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        mAdapter = new MainListAdapter(this, R.layout.layout_title_item, new ArrayList<TitleDataItem>());

        // 3.ListViewにAdapterをセットする。
        mListView.setAdapter(mAdapter);

        // 通常のクリックリスナーのセット
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TitleDataItem item = (TitleDataItem)adapterView.getItemAtPosition(position);
                Intent intent = EditActivity.createIntent(MainActivity.this, item.getTitle());
//                String title = String.valueOf(adapterView.getItemAtPosition(position));
//                Intent intent = EditActivity.createIntent(MainActivity.this, title);
                startActivity(intent);
            }
        });

        // 長押しでのクリックリスナーのセット
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                TitleDataItem item = (TitleDataItem)adapterView.getItemAtPosition(position);
                mAdapter.remove(item);
//                String title = String.valueOf(adapterView.getItemAtPosition(position));
//                mAdapter.remove(title);
                Toast.makeText(MainActivity.this, item.getTitle() + "を削除しました。", Toast.LENGTH_SHORT).show();
                return true;
//                return false;
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
        displayDataList();
    }

    /**
     * リスト表示用メソッド
     */
    private void displayDataList() {
        // 1.アダプター内のデータをリセットする（クリアにする）
        mAdapter.clear();

        // 2.新しいデータをアダプターに設定する（セットする）
//        List<String> dataList = Arrays.asList("ホーム", "事業内容", "企業情報", "採用情報", "お問い合わせ");
        List<TitleDataItem> dataList = Arrays.asList(
                new TitleDataItem(1, "ホーム"),
                new TitleDataItem(2, "事業内容"),
                new TitleDataItem(3, "企業情報"),
                new TitleDataItem(4, "採用情報"),
                new TitleDataItem(5, "お問い合わせ")
        );
        mAdapter.addAll(dataList);

        // 3.アダプターにデータが変更されたことを教えてあげる（通知する）
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
}
