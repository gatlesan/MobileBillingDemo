package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 7/29/2015.
 */
public class ScanItemsActivity extends Activity{
    Button btn_scan;
    ListView lv_items;
    LinearLayout layout_items;
    LinearLayout layout_footer;
    Button btn_checkout;
    Context context;
    private ArrayList<Integer> alItemNo;
    private ArrayList<String> alItemName;
    private ArrayList<Float> alItemsPrice;
    Float sum = 0.00f;
    private static final int scanner_act_code = 241;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items_layout);
        context = getApplicationContext();
        btn_scan = (Button)findViewById(R.id.scan_barcode_button);
        lv_items = (ListView) findViewById(R.id.lv_items);
        layout_items = (LinearLayout)findViewById(R.id.layout_items);
        layout_footer = (LinearLayout)findViewById(R.id.layout_footer);
        btn_checkout =(Button)findViewById(R.id.checkout);
        alItemNo = new ArrayList<Integer>();
        alItemName = new ArrayList<String>();
        alItemsPrice = new ArrayList<Float>();
        layout_items.setVisibility(View.INVISIBLE);
        layout_footer.setVisibility(View.INVISIBLE);
        btn_checkout.setVisibility(View.INVISIBLE);
        /*alItemNo.add(1);
        alItemNo.add(2);
        alItemNo.add(3);
        alItemNo.add(4);
        alItemNo.add(5);
        alItemName.add("Coffee powder");
        alItemName.add("Pepper");
        alItemName.add("Britania Rusk");
        alItemName.add("Dabur Honey");
        alItemName.add("Maggie");
        alItemsPrice.add(99.25f);
        alItemsPrice.add(231.50f);
        alItemsPrice.add(311.00f);
        alItemsPrice.add(430.75f);
        alItemsPrice.add(125.25f);*/

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent();
                intent.setClass(ScanItemsActivity.this, ScannerActivity.class);
                intent.putExtra("list_size",alItemNo.size());
                startActivityForResult(intent, scanner_act_code);
            }
        });
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBilltoSharedPref();
                Intent intent =  new Intent();
                intent.setClass(ScanItemsActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

    }
    private void saveBilltoSharedPref(){

        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int index = 0;
        int size = alItemNo.size();
        editor.putInt("bill_size", size);
        while(index<size){
            editor.putString("myItem"+index, alItemName.get(index));
            editor.putFloat("myPrice" + index, alItemsPrice.get(index));
            index++;
        }
        editor.putFloat("myTotal", sum);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(scanner_act_code == requestCode){
            if(resultCode == RESULT_OK){
                String item_name = data.getStringExtra("myItem");
                Float item_price = data.getFloatExtra("myPrice", 30.00f);

                layout_items.setVisibility(View.VISIBLE);
                layout_footer.setVisibility(View.VISIBLE);
                btn_checkout.setVisibility(View.VISIBLE);

                int list_size = alItemNo.size()+1;
                alItemNo.add(list_size);
                alItemName.add(item_name);
                alItemsPrice.add(item_price);


                lv_items.setAdapter(new ItemsAdapter(getApplicationContext(), alItemNo, alItemName, alItemsPrice));
                /*LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = li.inflate(R.layout.list_layout_footer, null);*/
                TextView total = (TextView)layout_footer.findViewById(R.id.total_value);
                sum=0f;
                int i=0;
                while(i<alItemsPrice.size()){
                    sum = sum+alItemsPrice.get(i++);
                }
                total.setText(sum.toString()+" Rs");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
