package com.example.mr_coffee_app;


import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
    /*
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
    /* This method is called when the order button is clicked.
     */


    /**
     * This method calculates the total price.
     */
    public void submitOrder(View view){
        //find the user's name
        EditText nameField=(EditText)findViewById(R.id.name_Field) ;
        String value=nameField.getText().toString();
        //find out if user wants whipped cream topping
        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_checkbox) ;
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        //figure out if user wants chocolate topping
        CheckBox chocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage=createOrderSummary(value);
        displayPrice(price);
        displayMessage(priceMessage);

        // displayMessage(priceMessage);
        // displayPrice(quantity * coffeePrice);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for Coffee for " +value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }



    private void displayPrice(double number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(number));
    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        //price of 1 cup of coffee
        int basePrice=50;
        if(addWhippedCream){
            basePrice = basePrice+30;
        }
        if (addChocolate) {

            basePrice=basePrice+20;

        }
        return quantity * basePrice;
    }
    private  String createOrderSummary(String name)
    {
        String priceMessage= "Name:" + name;
        priceMessage+="Thank You!";

        return priceMessage;
    }
    private  void displayMessage(String message){
        TextView OrderSummaryTextView=(TextView)findViewById(R.id.order_summary_text);
        OrderSummaryTextView.setText(message);


    }
}