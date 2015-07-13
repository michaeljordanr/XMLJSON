package com.example.michael.xmljson;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        TextView txtFormat = (TextView) findViewById(R.id.txt_format);
        DataHandler.Format format = (DataHandler.Format) extras.getSerializable("format");
        if (format != null) {
            txtFormat.setText(format.toString());
        }

        TextView txtRequest = (TextView) findViewById(R.id.txt_request);
        String request = extras.getString("request");
        if (request != null) {
            txtRequest.setText(request);
        }

        TextView txtResponse = (TextView) findViewById(R.id.txt_response);
        String response = extras.getString("response");
        if (response != null) {
            txtResponse.setText(response);
        }
    }
}