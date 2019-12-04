package com.example.asynchronousexecution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CountTask countTask;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // ボタンをタップして非同期処理を開始
                countTask = new CountTask();
                // Listenerを設定
                countTask.setListener(createListener());
                countTask.execute(0);
            }
        });

        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                countTask.cancelTask();
                textView.setText(String.valueOf(0));
            }
        });
    }

    @Override
    protected void onDestroy() {
        countTask.setListener(null);
        super.onDestroy();
    }

    private CountTask.Listener createListener() {
        return new CountTask.Listener() {
            @Override
            public void onSuccess(int count) {
                textView.setText(String.valueOf(count));
            }
        };
    }
}
