package ro.pub.cs.systems.eim.colocviu1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Colocviu1_1MainActivity extends AppCompatActivity {
    private int count = 0;
    private List<String> cardinalPoints = new ArrayList<>();
    private Button buttonNorth;
    private Button buttonWest;
    private Button buttonEast;
    private Button buttonSouth;
    private Button buttonNav;

    private EditText editText;

    private IntentFilter intentFilter = new IntentFilter();

    CardinalButtonOnClickListener cardinalButtonOnClickListener = new CardinalButtonOnClickListener();
    class CardinalButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.northButton) {
                cardinalPoints.add("North");
            }

            if (view.getId() == R.id.buttonEast) {
                cardinalPoints.add("East");
            }

            if (view.getId() == R.id.buttonWest) {
                cardinalPoints.add("West");
            }

            if (view.getId() == R.id.buttonSouth) {
                cardinalPoints.add("South");
            }
            count++;
            editText.setText(String.join(", ", cardinalPoints));
            if (count >= 4) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_1Service.class);
                intent.putExtra(Constants.CARDINAL_EDIT_TEXT, editText.getText().toString());
                intent.putExtra(Constants.CLICK_COUNT, count);
                startService(intent);
            }
        }
    }

    NavButtonClickListener navButtonClickListener = new NavButtonClickListener();
    class NavButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_1SecondaryActivity.class);
            intent.putExtra(Constants.CARDINAL_EDIT_TEXT, editText.getText().toString());
            intent.putExtra(Constants.CLICK_COUNT, count);
            editText.setText("");
            cardinalPoints.clear();
            count = 0;
            startActivityForResult(intent, Constants.SECOND_APPLICATION_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_1_main);

        buttonNorth = findViewById(R.id.northButton);
        buttonNorth.setOnClickListener(cardinalButtonOnClickListener);

        buttonWest = findViewById(R.id.buttonWest);
        buttonWest.setOnClickListener(cardinalButtonOnClickListener);

        buttonEast = findViewById(R.id.buttonEast);
        buttonEast.setOnClickListener(cardinalButtonOnClickListener);

        buttonSouth = findViewById(R.id.buttonSouth);
        buttonSouth.setOnClickListener(cardinalButtonOnClickListener);

        buttonNav = findViewById(R.id.buttonNav);
        buttonNav.setOnClickListener(navButtonClickListener);

        editText = findViewById(R.id.editText);

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.CARDINAL_EDIT_TEXT) != null) {
                editText.setText(savedInstanceState.getString(Constants.CARDINAL_EDIT_TEXT));
            }

            if (savedInstanceState.getString(Constants.CLICK_COUNT) != null) {
                count = Integer.parseInt(savedInstanceState.getString(Constants.CLICK_COUNT));
            }
        }

        intentFilter.addAction(Constants.ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SECOND_APPLICATION_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this,"Register button was clicked!", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,"Cancel button was clicked!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.CARDINAL_EDIT_TEXT, editText.getText().toString());
        savedInstanceState.putString(Constants.CLICK_COUNT, String.valueOf(count));
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString(Constants.CARDINAL_EDIT_TEXT) != null) {
            editText.setText(savedInstanceState.getString(Constants.CARDINAL_EDIT_TEXT));
        }

        if (savedInstanceState.getString(Constants.CLICK_COUNT) != null) {
            count = Integer.parseInt(savedInstanceState.getString(Constants.CLICK_COUNT));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(),"Service result: " +
                intent.getStringExtra(Constants.BROADCAST_INTENT_KEY), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}

