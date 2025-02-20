package com.example.myrecipebook.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myrecipebook.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DelegationActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private Button buttonSendSMS;

    // Sample grocery list with prices and locations
    private HashMap<String, String[]> groceryList;

    // Request Code for SMS permission
    private static final int REQUEST_SMS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegation);

        // Initialize the EditText and Button
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonSendSMS = findViewById(R.id.buttonSendSMS);

        // Sample grocery list data
        groceryList = new HashMap<>();
        groceryList.put("Apples", new String[]{"$2.00", "Produce Section"});
        groceryList.put("Chicken Breast", new String[]{"$5.00", "Meat Section"});
        groceryList.put("Quinoa", new String[]{"$3.00", "Grains Aisle"});
        groceryList.put("Broccoli", new String[]{"$1.50", "Produce Section"});
        groceryList.put("Soy Sauce", new String[]{"$2.50", "Condiments Aisle"});

        // Check and request SMS permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
        }

        // Set OnClickListener for Send SMS button
        buttonSendSMS.setOnClickListener(v -> {
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            String message = createMessage();

            if (!phoneNumber.isEmpty() && !message.isEmpty()) {
                sendSMS(phoneNumber, message);
            } else {
                Toast.makeText(DelegationActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to create the message containing grocery items, prices, and locations
    private String createMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Hi Lily,\n\n");
        messageBuilder.append("Could you please pick up the following items for me?\n\n");

        for (String item : groceryList.keySet()) {
            String[] details = groceryList.get(item);
            messageBuilder.append(item)
                    .append(" - Price: ")
                    .append(details[0])
                    .append(" (")
                    .append(details[1])
                    .append(")\n");
        }

        messageBuilder.append("\nThank you!");
        return messageBuilder.toString();
    }

    // Method to send SMS
    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            // Handle message length exceeding the limit of 160 characters
            if (message.length() > 160) {
                // Divide message if it's longer than 160 characters
                ArrayList<String> messages = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phoneNumber, null, messages, null, null);
            } else {
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            }

            // Log success for debugging
            Log.d("DelegationActivity", "SMS sent to: " + phoneNumber);
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            // Log failure for debugging
            Log.e("DelegationActivity", "Failed to send SMS: " + e.getMessage());
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
        }
    }

    // Handling the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}