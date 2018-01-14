/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


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

import java.net.URI;
import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */

    public void increment(View view) {

        if (quantity == 100)
        {
        Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
        return;}

    quantity = quantity + 1;
    displayQuantity(quantity);}
    /**
     * This method is called when the minus button is clicked.
     */

    public void decrement(View view) {

        if (quantity == 1){
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
        return;}

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
//price of 1 cup of coffee
        int basePrice = 5;
        //if user wants cream, price is 1 USD on top
        if (addWhippedCream) {basePrice = basePrice + 1;}

        //if user wants chocolate, price is 2USD on top
        if (addChocolate) {basePrice = basePrice + 2;}


        int price = quantity * basePrice;
        return price;
    }
    /**
     * Creates order summary
     * @return orderSummary
     * @param priceOfOrder price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @param nameValue
     */
    private String createOrderSummary(String nameValue, int priceOfOrder, boolean addWhippedCream, boolean addChocolate) {
       String orderSummary = getString(R.string.order_name, nameValue) +
               "\n" + getString(R.string.add_cream, addWhippedCream) +
               "\n" + getString(R.string.add_choco, addChocolate)
               + "\n" + getString(R.string.quantity, quantity) + "\n" + getString(R.string.total, priceOfOrder);
       orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText submitName = (EditText) findViewById(R.id.insert_name_view);
        String nameValue = submitName.getText().toString();

        CheckBox checkStateCream = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        boolean checkedBoxStateCream = checkStateCream.isChecked();

        CheckBox checkStateChoco = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean checkedBoxStateChoco = checkStateChoco.isChecked();

        int price = calculatePrice(checkedBoxStateCream, checkedBoxStateChoco);
        String priceMessage = createOrderSummary(nameValue, price, checkedBoxStateCream, checkedBoxStateChoco);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}