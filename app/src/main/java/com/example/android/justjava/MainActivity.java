package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
    if (quantity==100) {
        Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
    return;}

        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity==1  ) {
            Toast.makeText(this, "You cannot have less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;}
            quantity = quantity - 1;
            displayQuantity(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_field);
        String value = text.getText().toString();


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, value);


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + value);
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));



    }

    private  int calculatePrice( boolean addWhippedCream , boolean addChocolate){
        int baseprice =5;

        if(addWhippedCream){
            baseprice +=1 ;
        }
        if(addChocolate){
            baseprice +=2;
        }

        return quantity * baseprice;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

   /* private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }   */

    private String  createOrderSummary(int price  , boolean addWhippedCream , boolean addChocolate , String name) {
        String priceMessage = "Name:" + name;
        priceMessage = priceMessage + "\n quantity = " + quantity;
        priceMessage = priceMessage + "\n Add whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\n Add chocolate? " + addChocolate;
        priceMessage = priceMessage + "\n Total: " + price + "$";
        priceMessage = priceMessage + "\n " + getString(R.string.thank_you);
        return priceMessage;

    }
  /*  private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }  */
}
