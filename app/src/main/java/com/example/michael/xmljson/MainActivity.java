package com.example.michael.xmljson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String URL = "http://code.softblue.com.br:8080/web/GerarNumeros";

    private EditText edtMin;
    private EditText edtMax;
    private EditText edtQtde;
    private RadioGroup grpFormat;
    private TextView txtNumeros;
    private ProgressBar progress;

    private DataHandler.Format format;
    private String requestStr;
    private String responseStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edtMin = (EditText) findViewById(R.id.edt_min);
        edtMax = (EditText) findViewById(R.id.edt_max);
        edtQtde = (EditText) findViewById(R.id.edt_qtde);
        grpFormat = (RadioGroup) findViewById(R.id.grp_format);
        txtNumeros = (TextView) findViewById(R.id.txt_numeros);
        progress = (ProgressBar) findViewById(R.id.progress);
    }

    public void verDetalhes(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("format", format);
        intent.putExtra("request", requestStr);
        intent.putExtra("response", responseStr);
        startActivity(intent);
    }

    public void gerar(View view) {
        Integer min = Integer.valueOf(edtMin.getText().toString());
        Integer max = Integer.valueOf(edtMax.getText().toString());
        Integer qtde = Integer.valueOf(edtQtde.getText().toString());
        int checkedFormatId = grpFormat.getCheckedRadioButtonId();
        format = checkedFormatId == R.id.opt_xml ? DataHandler.Format.XML : DataHandler.Format.JSON;

        InvokeWebServiceTask task = new InvokeWebServiceTask();
        task.execute(min, max, qtde, format);
    }

    private class InvokeWebServiceTask extends AsyncTask<Object, Void, List<Integer>> {
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            txtNumeros.setVisibility(View.GONE);
        }

        @Override
        protected List<Integer> doInBackground(Object... params) {
            try {
                Integer min = (Integer) params[0];
                Integer max = (Integer) params[1];
                Integer qtde = (Integer) params[2];
                DataHandler.Format format = (DataHandler.Format) params[3];

                DataHandler handler = DataHandlerFactory.newDataHandler(format);
                Data data = new Data(min, max, qtde);
                requestStr = handler.convertToString(data);

                HttpClient http = new DefaultHttpClient();

                List<NameValuePair> getParams = new ArrayList<>();
                getParams.add(new BasicNameValuePair("tipo", format.toString()));
                getParams.add(new BasicNameValuePair("dados", requestStr));

                String getParamsStr = URLEncodedUtils.format(getParams, HTTP.UTF_8);
                HttpGet get = new HttpGet(URL + "?" + getParamsStr);

                HttpResponse response = http.execute(get);
                responseStr = EntityUtils.toString(response.getEntity());

                return handler.extractNumbers(responseStr);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Integer> numbers) {
            StringBuilder sb = new StringBuilder();

            for (int number : numbers) {
                sb.append(number).append(System.lineSeparator());
            }

            txtNumeros.setText(sb.toString());

            progress.setVisibility(View.GONE);
            txtNumeros.setVisibility(View.VISIBLE);
        }
    }
}