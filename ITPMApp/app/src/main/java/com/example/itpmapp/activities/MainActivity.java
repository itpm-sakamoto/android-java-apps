package com.example.itpmapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.itpmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 1.ListViewをレイアウトファイルから読み込む。
        mListView = findViewById(R.id.main_list);

        // 2.Adapterを作成する。
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        // 3.ListViewにAdapterをセットする。
        mListView.setAdapter(mAdapter);

        // 通常のクリックリスナーのセット
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = String.valueOf(adapterView.getItemAtPosition(position));
                Intent intent = EditActivity.createIntent(MainActivity.this, title);
                startActivity(intent);
            }
        });

        // 長押しでのクリックリスナーのセット
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = String.valueOf(adapterView.getItemAtPosition(position));
                mAdapter.remove(title);
                Toast.makeText(MainActivity.this, title + "を削除しました。", Toast.LENGTH_SHORT).show();
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
        displayDataList();
    }

    /**
     * リスト表示用メソッド
     */
    private void displayDataList() {
        // 1.アダプター内のデータをリセットする（クリアにする）
        mAdapter.clear();

        // 2.新しいデータをアダプターに設定する（セットする）
        List<String> dataList = Arrays.asList("ホーム", "事業内容", "企業情報", "採用情報", "お問い合わせ");
        mAdapter.addAll(dataList);

        // 3.アダプターにデータが変更されたことを教えてあげる（通知する）
        mAdapter.notifyDataSetChanged();
    }
}
