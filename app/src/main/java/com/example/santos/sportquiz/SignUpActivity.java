package com.example.santos.sportquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santos.sportquiz.MainActivity;
import com.example.santos.sportquiz.Model.User;
import com.example.santos.sportquiz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {


    EditText edtNewUser, edtNewPassword, edtNewEmail;
    Button signup;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up_layout);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        edtNewUser = findViewById(R.id.edtNewUser);
        edtNewEmail = findViewById(R.id.edtNewEmail);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        signup = findViewById(R.id.signup);


        TextView linkToLogin = findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(edtNewUser.getText().toString(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString());

                if(user.getEmail().isEmpty()){
                    edtNewEmail.setError("please enter email");
                    edtNewEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
                    edtNewEmail.setError("please enter valid email");
                    edtNewEmail.requestFocus();
                    return;

                }


                if(user.getUserName().isEmpty()){
                    edtNewUser.setError("please enter username");
                    edtNewUser.requestFocus();
                    return;
                }
                if(user.getPassword().isEmpty()){
                    edtNewPassword.setError("please enter password");
                    edtNewPassword.requestFocus();
                    return;
                }
                if(user.getPassword().length()<6){
                    edtNewPassword.setError("password must be atleast 6 characters");
                    edtNewPassword.requestFocus();
                    return;
                }



                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists()) {
                            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                        }



                        else {
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(SignUpActivity.this, "User registration success", Toast.LENGTH_LONG).show();


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent homeIntent1 = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(homeIntent1);
                finish();

                //      dialogInterface.dismiss();


            }






            //     });
            //      alertDialog.show();

        });
    }
}
