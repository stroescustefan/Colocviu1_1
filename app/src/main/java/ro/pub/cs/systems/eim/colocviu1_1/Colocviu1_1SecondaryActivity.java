package ro.pub.cs.systems.eim.colocviu1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Colocviu1_1SecondaryActivity extends AppCompatActivity {
    private EditText editTextSecondary;
    private Button registerButton;
    private Button cancelButton;
    ButtonClickListener buttonClickListener = new ButtonClickListener();
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_1MainActivity.class);
            if (view.getId() == R.id.registerButton) {
                setResult(Activity.RESULT_OK);
            } else if (view.getId() == R.id.cancelButton) {
                setResult(Activity.RESULT_CANCELED);
            }

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_1_secondary);

        editTextSecondary = findViewById(R.id.editTextSecondary);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(buttonClickListener);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        editTextSecondary.setText(data.getString(Constants.CARDINAL_EDIT_TEXT) + " Click counts: "
                + data.getInt(Constants.CLICK_COUNT));
    }
}
