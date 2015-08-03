package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/*import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.map.ObjectMapper;
import javax.ws.rs.core.MediaType;*/
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


//import com.sun.jersey.api.client.Client;


/**
 * Created by Administrator on 7/29/2015.
 */
public class ScannerActivity extends Activity {

    static final String REST_URI = "http://localhost:8080/NewProj1";
    static final String INCH_TO_FEET = "/ProductService/getPrice/";
    private int send_index;
    private ArrayList<String> alItemName;
    private ArrayList<Float> alItemsPrice;
    private Handler main_thread;
    private ProgressDialog ringProgressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, 0);
        setContentView(R.layout.scanner_layout);
        send_index = getIntent().getIntExtra("list_size",0);
        main_thread = new Handler();
        alItemName = new ArrayList<String>();
        alItemsPrice = new ArrayList<Float>();
        alItemName.add("Coffee powder");
        alItemName.add("Pepper");
        alItemName.add("Britania Rusk");
        alItemName.add("Dabur Honey");
        alItemName.add("Maggie");
        alItemName.add("Bourbon");
        alItemName.add("Dairy Milk");
        alItemName.add("Temptations");
        alItemName.add("Little Hearts");
        alItemName.add("Milk Bikis");
        alItemsPrice.add(99.25f);
        alItemsPrice.add(231.50f);
        alItemsPrice.add(311.00f);
        alItemsPrice.add(430.75f);
        alItemsPrice.add(125.25f);
        alItemsPrice.add(21.25f);
        alItemsPrice.add(50.50f);
        alItemsPrice.add(31.00f);
        alItemsPrice.add(30.75f);
        alItemsPrice.add(25.00f);

        processBarcodeScan();
        initiateScanner();
    }

    private void initiateScanner(){
        {
            ringProgressDialog =
                    ProgressDialog.show(ScannerActivity.this, "Please wait ...", "Scanner is in progress ...", true);
            ringProgressDialog.setCancelable(false);
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        //Do the time intensive process here

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ringProgressDialog.dismiss();
                    main_thread.post(new Runnable() {
                        @Override
                        public void run() {
                            find_store_button.setVisibility(View.INVISIBLE);
                            store_name_tv.setText("D Mart");
                            store_name_tv.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }).start();*/
        }
    }
    private void processBarcodeScan(){
        new Thread(new Runnable() {
            //int x = 11223344;

            //String barcode = new String(x+1+"");
            Product p = null;
            @Override
            public void run() {
                //do web query
                //p = getProductDetails(barcode);

                getProductDetails();
                /*try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }).start();
    }

    /*private Product getProductDetails(){
        if(send_index>9){
            send_index = send_index%10;
        }
        Product product = new Product();
        product.setName(alItemName.get(send_index));
        product.setPrice(alItemsPrice.get(send_index));
        return product;
    }*/
    private void getProductDetails(){
        if(send_index>4){
            send_index = send_index%5;
        }
        long barCode = 111110000011101l+send_index;
        RequestParams params = new RequestParams();
        //params.put("barcode",barCode);
        SyncHttpClient client = new SyncHttpClient();
        client.get("http://dmartwerservice.cfapps.io/rest/barcode/getPrice?barcode="+barCode, new AsyncHttpResponseHandler() {
            Product product = new Product();

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode + "  " + new String(responseBody));
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    String productName = obj.getString("productName");
                    Float productPrice = Float.parseFloat(obj.getString("productPrice"));
                    product.setName(productName);
                    product.setPrice(productPrice);
                    updateUIOnMainThread(product);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Fail " + statusCode + "  " + new String(responseBody));
            }
        });
    }
    private void updateUIOnMainThread(final Product p){
        main_thread.post(new Runnable() {
            @Override
            public void run() {
                ringProgressDialog.dismiss();
                Intent data = new Intent();
                data.putExtra("myItem", p.getName());
                data.putExtra("myPrice", (float)p.getPrice());
                setResult(RESULT_OK, data);
                finish();

            }
        });
    }
    /*private static String getResponse(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).type("application/json").get(String.class );
    }*/
}
