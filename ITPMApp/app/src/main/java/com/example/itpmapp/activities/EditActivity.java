package com.example.itpmapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.example.itpmapp.R;

public class EditActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "key_title";
    private EditText mTitleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitleEditText = findViewById(R.id.title_edit_text);

        String title = getIntent().getStringExtra(KEY_TITLE);
        if (title != null) {
            mTitleEditText.setText(title);
        } else {
            mTitleEditText.setText("");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent createIntent(Context context, String title) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(KEY_TITLE, title);
        return intent;
    }

}
