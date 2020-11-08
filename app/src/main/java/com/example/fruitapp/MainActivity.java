package com.example.fruitapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn, btnSignUp;
    private EditText etSendString;
    private TextView tvShowString;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        //mDatabase = FirebaseDatabase.getInstance();
        //mRef = mDatabase.getReference("pradeep89");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);

             /*   String data = etSendString.getText().toString();
                mRef.child("user1").setValue(data);
                Toast.makeText(MainActivity.this, "Data sent", Toast.LENGTH_SHORT).show();*/


            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });
    }
}