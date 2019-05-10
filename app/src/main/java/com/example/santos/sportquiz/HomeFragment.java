package com.example.santos.sportquiz;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
//import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.santos.sportquiz.Interface.RankingCallBack;
import com.example.santos.sportquiz.Model.QuestionScore;
import com.example.santos.sportquiz.Model.QuizObject;
import com.example.santos.sportquiz.Model.Ranking;
import com.example.santos.sportquiz.ViewHolder.RankingViewHolder;
import com.example.santos.sportquiz.common.common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    View myFragment;
    LinearLayoutManager layoutManager;
    Button playagain,logout;
    private TextView playerName, playerScore;

    private Button quizStartButton;
    Ranking model;
    FirebaseDatabase database;
    DatabaseReference questionScore, rankingTbl;
    int sum = 0;


    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTbl = database.getReference("Ranking");



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        logout = view.findViewById(R.id.LogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();


                Intent intent1 = new Intent(HomeFragment.this.getActivity(),MainActivity.class);
                startActivity(intent1);

            }
        });

        playerName = view.findViewById(R.id.player_name);
        playagain = view.findViewById(R.id.PlayAgain);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(),Home.class);
                startActivity(intent);

            }
        });



            playerScore = view.findViewById(R.id.player_score);






        questionScore.orderByChild("user").equalTo(common.currentUser.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    QuestionScore ques = data.getValue(QuestionScore.class);
                    sum += Integer.parseInt(ques.getScore());
                    String current_score = Integer.toString(sum);
                    playerName.setText(common.currentUser.getUserName());
                    playerScore.setText( current_score);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







                //   RecyclerView selectedQuizRecyclerView = view.findViewById(R.id.selected_quizzes);
        //   GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        //   selectedQuizRecyclerView.setLayoutManager(gridLayoutManager);
        //   selectedQuizRecyclerView.setHasFixedSize(true);

        //  QuizAdapter mAdapter = new QuizAdapter(getActivity(), getTestData(), R.layout.selected_quiz_layout);
        //  selectedQuizRecyclerView.setAdapter(mAdapter);
        return view;

    }









        // public List<QuizObject> getTestData() {
        //     List<QuizObject> testData = new ArrayList<>();
        //     testData.add(new QuizObject("", "General Knowledge"));
        //     testData.add(new QuizObject("", "Entertainment"));
        //     testData.add(new QuizObject("", "History"));
        //     testData.add(new QuizObject("", "Sports"));
        //     return testData;
        // }

}
