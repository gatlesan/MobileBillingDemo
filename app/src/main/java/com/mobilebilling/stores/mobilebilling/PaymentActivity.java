package com.mobilebilling.stores.mobilebilling;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;


public class PaymentActivity extends ActionBarActivity {
    private int selected_bank;
    private WebView bank_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bank_view = (WebView)findViewById(R.id.bank_webview);
        selected_bank = getIntent().getIntExtra("bank", 0);
        callBankWebsite(selected_bank);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }
    private void callBankWebsite(int selected_bank){
        if(selected_bank == CheckoutActivity.axis_bank){
            bank_view.loadUrl("http://www.axisbank.com/pre-login/internetbanking_prelogin.aspx");
        }else if(selected_bank == CheckoutActivity.citi_bank){
            bank_view.loadUrl("http://www.online.citibank.co.in/products-services/online-services/internet-banking.htm");
        }else if(selected_bank == CheckoutActivity.hdfc_bank){
            bank_view.loadUrl("https://netbanking.hdfcbank.com/netbanking/");
        }else if(selected_bank == CheckoutActivity.icici_bank){
            bank_view.loadUrl("http://www.icicibank.com/Personal-Banking/insta-banking/internet-banking/index.page");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_success) {
            Toast.makeText(PaymentActivity.this, "Payment Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(PaymentActivity.this, FinalBillActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
