package com.mobilebilling.stores.mobilebilling;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


public class CheckoutActivity extends ActionBarActivity {
    private int bank_id = 0;
    public static  int axis_bank = 112;
    public static  int citi_bank = 141;
    public static  int hdfc_bank = 152;
    public static  int icici_bank = 173;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_checkout);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.axis:
                if (checked)
                    bank_id = axis_bank;
                    Toast.makeText(CheckoutActivity.this, "Axis Bank selected", Toast.LENGTH_LONG).show();
                    break;
            case R.id.citi:
                if (checked)
                    bank_id = citi_bank;
                    Toast.makeText(CheckoutActivity.this, "Citi Bank selected", Toast.LENGTH_LONG).show();
                    break;
            case R.id.hdfc:
                if (checked)
                    bank_id = hdfc_bank;
                    Toast.makeText(CheckoutActivity.this, "Hdfc Bank selected", Toast.LENGTH_LONG).show();
                    break;
            case R.id.icici:
                if (checked)
                    bank_id = icici_bank;
                    Toast.makeText(CheckoutActivity.this, "Icici Bank selected", Toast.LENGTH_LONG).show();
                    break;
        }
    }

    public void onSubmitClicked(View view){
        Intent intent = new Intent();
        intent.setClass(CheckoutActivity.this, PaymentActivity.class);
        intent.putExtra("bank", bank_id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
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
