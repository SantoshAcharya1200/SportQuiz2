package com.example.santos.sportquiz;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.graphics.Color.rgb;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setBackgroundColor(rgb(128,128,128));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.quiz_mart:
                        selectedFragment = CategoryFragment.newInstance();
                        break;

                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance();
                        break;

                    case R.id.ladder_board:
                        selectedFragment = RankingFragment.newInstance();
                        break;

                //    case R.id.settings:
                //        selectedFragment = CategoryFragment.newInstance();
                //        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }


        });
        setDefaultfragment();
    }


    public void setDefaultfragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, CategoryFragment.newInstance());
        transaction.commit();

    }
}
