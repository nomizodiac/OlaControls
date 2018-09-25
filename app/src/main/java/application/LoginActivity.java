package application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.invotyx.olacontrols.R;


public class LoginActivity extends AppCompatActivity {


    Button login_btn;
    EditText user_name_field;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_btn = findViewById(R.id.login_button);
        user_name_field = findViewById(R.id.username);
        final String username = user_name_field.getText().toString();


        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                       MainActivity.class);
                startActivity(myIntent);
            }
        });

    }

}

