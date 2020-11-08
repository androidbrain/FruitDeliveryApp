package com.example.fruitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private EditText etPhone, etPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ////////
               /* Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();*/
                /////////////

                final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
                progressDialog.setMessage("Please waiting..");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(etPhone.getText().toString()).exists()) {

                            progressDialog.dismiss();
                            User user = snapshot.child(etPhone.getText().toString()).getValue(User.class);

                            if (user.getPassword().equals(etPassword.getText().toString())) {
                                //  Toast.makeText(SignInActivity.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.currentUser = user;

                               startActivity(intent);
                               finish();

                            } else {
                                Toast.makeText(SignInActivity.this, "Sign In Failed !!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User don't exists", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}