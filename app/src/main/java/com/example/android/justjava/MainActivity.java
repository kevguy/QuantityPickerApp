package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;
    boolean isAddWhippedCream = false;
    boolean isAddChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            quantity += 1;
        }

        int price = calculatePrice(quantity);
        display(quantity);
        displayPrice(price);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "1 cup of coffee is the minimum";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        int price = calculatePrice(quantity);
        display(quantity);
        displayPrice(price);
    }

    public void isWhippedCream(View view){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        isAddWhippedCream = whippedCreamCheckBox.isChecked();
        int price = calculatePrice(quantity);
        displayPrice(price);
    }

    public void isChocolate(View view){
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        isAddChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(quantity);
        displayPrice(price);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String username = nameEditText.getText().toString();
        int price = calculatePrice(quantity);

        String orderMessage = createOrderSummary(price, username);
        //Log.v("MainActivity", "The price is " + price);
        // Display message on screen
        //displayMessage(orderMessage);

        // Trigger email app with order summary
        sendEmail("JustJava order for " + username, orderMessage);

    }

    public void sendEmail(String subjectText, String bodyText){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));

        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(emailIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
            emailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Not supported";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
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
        int price = 0;

        if (isAddWhippedCream) {
            price += 1;
        }

        if (isAddChocolate) {
            price += 2;
        }

        price += 5;
        price *= quantity;

        return price;
    }


    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, String username){
        String summaryMessage = "";

        summaryMessage += "Name: ";
        summaryMessage = summaryMessage + username + "\n";
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