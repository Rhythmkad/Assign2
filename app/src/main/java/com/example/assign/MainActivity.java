package com.example.assign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView teamCanada_tv, teamUsa_tv;
    private Button canada_increaseBtn, usa_increaseBtn, canada_decreaseBtn, usa_decreaseBtn, resetBtn;
    private RadioGroup radioGroupCanada, radioGroupUsa; //radioGroup for both the teams
    private RadioButton radioButtonCanada, radioButtonUsa; // radioButtons for both the teams
    private final int maxScore = 25;  // maximum score value set to final, means it cannot be changed
    private int teamCanadaScore; // Canada team current score
    private int teamUsaScore; // Usa team current score
    private String getWhichTeamWon = ""; // string to store which team won the match, to show it as toast message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Initializing Ids */
        initializeIds();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        // changing score
        changeScore();
    }

    /* Initializing Ids*/
    public void initializeIds(){
        teamCanada_tv = findViewById(R.id.score_1);
        teamUsa_tv = findViewById(R.id.score_2);
        canada_increaseBtn = findViewById(R.id.increase_score1);
        canada_decreaseBtn = findViewById(R.id.decrease_score1);
        usa_increaseBtn = findViewById(R.id.increase_score2);
        usa_decreaseBtn = findViewById(R.id.decrease_score2);
        radioGroupCanada = findViewById(R.id.radio_group1);
        radioGroupUsa = findViewById(R.id.radio_group2);
        resetBtn = findViewById(R.id.resetScore);



    }

    /* changing score through radio buttons */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void changeScore(){
        // increase decrease score button's clickListeners
        canada_increaseBtn.setOnClickListener(this);
        canada_decreaseBtn.setOnClickListener(this);
        usa_increaseBtn.setOnClickListener(this);
        usa_decreaseBtn.setOnClickListener(this);

        // reset button's onClickListener that receive callbacks when a button is tapped
        resetBtn.setOnClickListener(this);

        // here if any of the team reached the maximum score than it will show which team
        // has won the match ,else it initialize the radioButtons
        if (teamCanadaScore == maxScore || teamUsaScore == maxScore){
            Toast.makeText(MainActivity.this, getWhichTeamWon, Toast.LENGTH_SHORT).show();
        }else{
            /* Radio group methods for selecting score.*/
            radioGroupCanada.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    radioButtonCanada = findViewById(id);
                }
            });

            radioGroupUsa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    radioButtonUsa = findViewById(id);
                    }
            });
        }
    }

    // reset button's on click method
    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        // checking if radioButtons are null or not, because if they are null then
        // application will crash, so to protect application from crashing, we will show user
        // a Toast message
        if (radioButtonCanada != null || radioButtonUsa != null) {
            switch (view.getId()) {
                // checking case according to ID
                // checking if conditions individually so that each radiobutton value should not be null
                // and teamScore should not be equal to maximum, if one of the team score is maximum
                // that means game has ended and we don't want to increase or decrease the score after that
                case R.id.increase_score1:
                    if (radioButtonCanada != null && teamCanadaScore !=maxScore && teamUsaScore !=maxScore) {
                        // adding score to team1
                        teamCanadaScore = Integer.parseInt(radioButtonCanada.getText().toString()) + teamCanadaScore;
                        if (teamCanadaScore >= maxScore){
                            teamCanadaScore = maxScore;
                            // if team score is equal to maximum score or greater;
                            // than this will set the text to maximum score
                            getWhichTeamWon = "Team Canada Won the match";
                            teamCanada_tv.setText(String.valueOf(teamCanadaScore));
                            Toast.makeText(this, getWhichTeamWon,Toast.LENGTH_SHORT).show();
                        }else{
                            teamCanada_tv.setText(String.valueOf(teamCanadaScore));
                        }
                    }
                    break;
                case R.id.increase_score2:
                    if (radioButtonUsa != null && teamCanadaScore !=maxScore && teamUsaScore !=maxScore) {
                        // adding score to team2
                        teamUsaScore = Integer.parseInt(radioButtonUsa.getText().toString()) + teamUsaScore;
                        // if team score is equal to maximum score or greater;
                        // than this will set the text to maximum score
                        if (teamUsaScore >= maxScore){
                            teamUsaScore = maxScore;
                            getWhichTeamWon = "Team USA Won the match";
                            Toast.makeText(this, getWhichTeamWon,Toast.LENGTH_SHORT).show();
                        }
                        teamUsa_tv.setText(String.valueOf(teamUsaScore));
                    }
                    break;
                case R.id.decrease_score1:
                    if (radioButtonCanada != null && teamCanadaScore !=maxScore && teamUsaScore !=maxScore) {
                        // decreasing score of team1
                        teamCanadaScore = teamCanadaScore - Integer.parseInt(radioButtonCanada.getText().toString());//checking if score when subtracting is less than 0, then set score to 0
                        if (teamCanadaScore < 0) {
                            teamCanadaScore = 0; //setting score to zero, because we don't want score to be in negative value
                        }
                        teamCanada_tv.setText(String.valueOf(teamCanadaScore));
                    }
                    break;
                case R.id.decrease_score2:
                    if (radioButtonUsa != null && teamCanadaScore !=maxScore && teamUsaScore !=maxScore) {
                        // decreasing score of team2
                        teamUsaScore = teamUsaScore - Integer.parseInt(radioButtonUsa.getText().toString());
                        //checking if score when subtracting is less than 0, then set score to 0
                        if (teamUsaScore < 0) {
                            teamUsaScore = 0;  //setting score to zero, because we don't want score to be in negative value
                        }
                        teamUsa_tv.setText(String.valueOf(teamUsaScore));
                    }
                    break;
                case R.id.resetScore:
                    // resetting the scores to 0, checking both the teams score first and
                    // then checking radioButton individually because there might be
                    // a possibility when one team score = 0 then we can get null in either of the radioButton objects
                    // that leads to application crash
                    if(teamCanadaScore != 0 || teamUsaScore != 0){
                        if (radioButtonCanada != null){
                            radioGroupCanada.clearChildFocus(radioButtonCanada);
                            resetScores(radioGroupCanada, teamCanada_tv);
                            if (radioButtonUsa != null){
                                // here if user has just selected the USA score radio button but
                                // hasn't increase/decrease the score yet, and try to reset everything..
                                // than this reset the USA radio buttons also
                                resetScores(radioGroupUsa, teamUsa_tv);
                            }
                        }else{
                            resetScores(radioGroupUsa, teamUsa_tv);
                        }
                        teamUsaScore = 0;
                        teamCanadaScore = 0;
                    }
                    else{
                        // toast message when both the teams scores are 0
                        Toast.makeText(this,"Scores are already 0",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }else{
            Toast.makeText(this,"Please Select Scores first",Toast.LENGTH_SHORT).show();
        }

        // calling method to compare the score and change the textViews
        comparingScores();
    }

    // this method will reset all the values and radio buttons when called
    // it's a common method to avoid writing same code multiple times
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void resetScores(RadioGroup radioGroup, TextView textView){
        radioGroup.clearCheck();
        radioGroup.clearFocus();
        //setting text view value to 0
        textView.setText(getText(R.string.value_0));
        //setting text view text color to black
        textView.setTextColor(getColor(R.color.black));
    }

    // Changing text color according to team scores
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void comparingScores() {
        if (teamCanadaScore > teamUsaScore) {
            // changing text color according to which team is currently scoring
            teamCanada_tv.setTextColor(getColor(R.color.holo_green_dark));
            teamUsa_tv.setTextColor(getColor(R.color.holo_red_dark));
        } else if (teamCanadaScore < teamUsaScore) {
            // changing text color according to which team is currently scoring
            teamCanada_tv.setTextColor(getColor(R.color.holo_red_dark));
            teamUsa_tv.setTextColor(getColor(R.color.holo_green_dark));
        } else {
            // changing text color if both the teams have equal score
            teamCanada_tv.setTextColor(getColor(R.color.black));
            teamUsa_tv.setTextColor(getColor(R.color.black));
        }
    }
}