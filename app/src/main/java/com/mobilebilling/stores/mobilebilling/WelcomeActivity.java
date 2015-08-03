package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class WelcomeActivity extends Activity {
    private Button find_store_button;
    private TextView store_name_tv;
    private Handler main_thread;
    private static ProgressDialog ringProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        main_thread = new Handler();
        store_name_tv = (TextView)findViewById(R.id.store_name);
        store_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to next page
                Intent scanAct = new Intent();
                scanAct.setClass(WelcomeActivity.this, ScanItemsActivity.class);
                startActivity(scanAct);
            }
        });
        find_store_button = (Button)findViewById(R.id.find_store_button);
        find_store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringProgressDialog =
                        ProgressDialog.show(WelcomeActivity.this, "Please wait ...", "Finding the nearest store ...", true);
                ringProgressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Do the WS call here
                            getStoreNameFromWS(12,34);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
            }
        });
    }

    private void getStoreNameFromWS(int longi, int latti){
        RequestParams params = new RequestParams();
        params.put("longi",12);
        params.put("lati",34);
        SyncHttpClient client = new SyncHttpClient();
        client.get("http://dmartwerservice.cfapps.io/rest/estores/getEStore?", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode + "  " + new String(responseBody));
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    String storeName = obj.getString("storeName");
                    updateUIonMainThread(storeName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Fail " + statusCode + "  " + new String(responseBody));
            }
        });
    }

    private void updateUIonMainThread(final String storeName){
        ringProgressDialog.dismiss();
        main_thread.post(new Runnable() {
            @Override
            public void run() {
                find_store_button.setVisibility(View.INVISIBLE);
                store_name_tv.setText(storeName);
                store_name_tv.setVisibility(View.VISIBLE);
            }
        });
    }

}
