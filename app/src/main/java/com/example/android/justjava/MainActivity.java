package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;
    Boolean isAddWhippedCream = false;
    Boolean isAddChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity += 1;
        int price = calculatePrice(quantity);
        display(quantity);
        displayPrice(price);
    }

    public void decrement(View view) {
        if (quantity != 0)
        {
            quantity -= 1;
        }
        int price = calculatePrice(quantity);
        display(quantity);
        displayPrice(price);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice(quantity);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);

        isAddWhippedCream = whippedCreamCheckBox.isChecked();
        isAddChocolate = chocolateCheckBox.isChecked();

        String orderMessage = createOrderSummary(price, isAddWhippedCream, isAddChocolate);
        //Log.v("MainActivity", "The price is " + price);
        displayMessage(orderMessage);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given quantity value on the screen.CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        Boolean isAddWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean isAddChocolate = chocolateCheckBox.isChecked();
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity) {
        return quantity * 5;
    }

    private String createOrderSummary(int price, Boolean isAddWhippedCream, Boolean isAddChocolate){
        String summaryMessage = "";

        summaryMessage += "Name: Kevguy\n";
        summaryMessage = summaryMessage + "Quantity: " + quantity + "\n";

        summaryMessage += "Add whipped cream? ";
        summaryMessage = summaryMessage + isAddWhippedCream + "\n";
        summaryMessage += "Add chocolate? ";
        summaryMessage = summaryMessage + isAddChocolate + "\n";

        summaryMessage = summaryMessage + "Total: $" + price + "\n";
        summaryMessage += "Thank you!";

        return summaryMessage;
    }
}