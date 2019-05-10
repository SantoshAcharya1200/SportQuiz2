package com.example.santos.sportquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santos.sportquiz.Model.User;
import com.example.santos.sportquiz.common.common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    EditText edtUser, edtPassword;
    TextView signInLink;
    Button btnsignup, btnsignin;
    FirebaseDatabase database;
    DatabaseReference users;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //firebase'
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");


        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);

        btnsignin = (Button) findViewById(R.id.btnsignin);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        signInLink = findViewById(R.id.link_to_registration);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = new Intent(MainActivity.this, SignUpActivity.class);
             //   signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInIntent);
                finish();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent homeIntent3 = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(homeIntent3);
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
            }
        });


    }


    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {
                    if (!user.isEmpty()) {
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if (login.getPassword().equals(pwd)) {
                            Intent homeActivity = new Intent(MainActivity.this, Home.class);

                            common.currentUser = login;
                            startActivity(homeActivity);
                            finish();
                        } else
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(MainActivity.this, "please fill up information", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(MainActivity.this, "User doesnt exists", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =mAuth.getCurrentUser();

        if (common.currentUser != null) {

            Intent intent1 = new Intent(MainActivity.this, Home.class);

            startActivity(intent1);

        }
    }

        //    AlertDialog.Builder alertDialog= new AlertDialog.Builder(MainActivity.this);
        //    alertDialog.setTitle("Sign up");
        //    alertDialog.setMessage("please fill up information");






        //    alertDialog.setView(sign_up_layout);
        //    alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        //    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
        //        @Override
        //        public void onClick(DialogInterface dialogInterface, int i) {

        //        }
        //    });

        //    alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
        //        @Override
        //       public void onClick(DialogInterface dialogInterface, int i) {




    }
