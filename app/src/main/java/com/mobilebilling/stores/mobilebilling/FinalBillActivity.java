package com.mobilebilling.stores.mobilebilling;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class FinalBillActivity extends ActionBarActivity {
    private TextView wc;
    private Button scan;
    private Button checkout;
    private Context context;
    private static final String DEFAULT_ITEM = "Item not found";
    private static final Float DEFAULT_PRICE = 0.00f;
    private ArrayList<Integer> alItemNo;
    private ArrayList<String> alItemName;
    private ArrayList<Float> alItemsPrice;
    private ListView lv_items;
    private TextView text_listItems;
    TextView total;
    Float sum = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items_layout);
        context = getApplicationContext();
        wc = (TextView)findViewById(R.id.welcome_text);
        scan = (Button)findViewById(R.id.scan_barcode_button);
        checkout = (Button)findViewById(R.id.checkout);
        wc.setVisibility(View.GONE);
        scan.setVisibility(View.INVISIBLE);
        checkout.setText("Payment Successful");
       /* text_listItems = (TextView)findViewById(R.id.tv_listItems);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)text_listItems.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        text_listItems.setLayoutParams(params);*/
        lv_items = (ListView) findViewById(R.id.lv_items);
        total = (TextView)findViewById(R.id.total_value);

        alItemNo = new ArrayList<Integer>();
        alItemName = new ArrayList<String>();
        alItemsPrice = new ArrayList<Float>();
        getBillFromPreferences();
        populateList();
    }

    private void getBillFromPreferences(){
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int index = 0;
        int size = sharedPref.getInt("bill_size", 0);
        while(index < size){
            alItemName.add(sharedPref.getString("myItem"+index, DEFAULT_ITEM));
            alItemsPrice.add(sharedPref.getFloat("myPrice" + index, DEFAULT_PRICE));
            alItemNo.add(++index);
        }
        sum = sharedPref.getFloat("myTotal", 100.00f);

    }
    private void populateList(){
        lv_items.setAdapter(new ItemsAdapter(getApplicationContext(), alItemNo, alItemName, alItemsPrice));
        total.setText(sum.toString() + " Rs");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
