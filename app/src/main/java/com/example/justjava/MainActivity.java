package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int numberOfCoffees = 1;
    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {

        if(numberOfCoffees >= 101){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        return;}
        numberOfCoffees++;
        display(numberOfCoffees);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {

        if(numberOfCoffees <= 0){
            Toast.makeText(this, "You cannot have less than 0 coffee", Toast.LENGTH_SHORT).show();
        return;
        }
        numberOfCoffees--;
        display(numberOfCoffees);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        Log.v("MainActivity", "Has Whipped Cream? : " + hasWhippedCream);

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        Log.v("MainActivity", "Has Chocolate? : " + hasChocolate);

        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

        int price = calculatePrice(numberOfCoffees, hasWhippedCream, hasChocolate);
        String Message = createOrdersummary (name, numberOfCoffees, hasWhippedCream, hasChocolate );


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT , Message );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //c
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_tv);
        quantityTextView.setText("" + number);
    }


    /**
     * This method elsedisplays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_tv);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     * @return the price
     */
    private int calculatePrice(int quantity, boolean addwhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addwhippedCream )
            basePrice += 1;
        if (addChocolate)
            basePrice += 2;
        return quantity * basePrice;
    }

    /**
     * This method displays the order summary on the screen.
     */
    public String createOrdersummary(String name, int quantity, boolean addwhippedCream, boolean addChocolate) {
        String message = "Name : " + name  + "\n" + "Add whipped cream? " + addwhippedCream + "\n"
            + "Add Chocolate? " + addChocolate + "\n"
            + "Quantity : " + numberOfCoffees +
            "\n" + "Total : $" + calculatePrice(numberOfCoffees, addwhippedCream, addChocolate) + "\n" + "Thank You!";
        return message;
    }

    }