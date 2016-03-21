package com.juntcompany.fitmaker.Alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.juntcompany.fitmaker.R;

public class NotificationActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView messageView = (TextView)findViewById(R.id.text_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        messageView.setText("NotifyActivity : " + message);
    }
}
