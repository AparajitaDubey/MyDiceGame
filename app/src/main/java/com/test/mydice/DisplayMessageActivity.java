package com.test.mydice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final Random RANDOM = new Random();
    private ImageView diceImageView1,diceImageView2,diceImageView3;
    private TextView computerResult,userResult,decision;
    private Button rollDices;
    private Button playBtn;
    private Button playAgainButton;
    int compScore=0;
    int userScore=0;
    Spinner sp;
    private String number = null;
    private TextView textMessage1,textMessage2,textMessage3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        rollDices = (Button) findViewById(R.id.rollDices);
        playBtn = (Button) findViewById(R.id.playBtn);
        diceImageView1 = findViewById(R.id.imageView1);
        diceImageView2 = findViewById(R.id.imageView2);
        diceImageView3 = findViewById(R.id.imageView3);
        sp = findViewById(R.id.sp);

        textMessage1 = findViewById(R.id.textMessage);
        textMessage2 = findViewById(R.id.textMsgUsr);
        textMessage3 = findViewById(R.id.textMsgPlay);
        userResult = (TextView) findViewById(R.id.resultNumUser);
        computerResult =(TextView) findViewById(R.id.resultNumComp);
        decision = (TextView) findViewById(R.id.result);
        playAgainButton = findViewById(R.id.playAgain);

        //Spinner logic :Dropdown for user to select number from 1 to 6
        ArrayAdapter <CharSequence>  adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        /*playBtn.setVisibility(View.VISIBLE);*/

        //Making user playing part invisible
        diceImageView3.setVisibility(View.INVISIBLE);
        sp.setVisibility(View.INVISIBLE);
        playBtn.setVisibility(View.INVISIBLE);


        rollDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Random value generated for computer dice
                int dice1= randomDiceValue();
                int dice2= randomDiceValue();

                int res1 = getResources().getIdentifier("dice"+dice1, "drawable", "com.test.mydice");
                int res2 = getResources().getIdentifier("dice"+dice2, "drawable", "com.test.mydice");

                //Set dice image to the random no. generated
                diceImageView1.setImageResource(res1);
                diceImageView2.setImageResource(res2);

                //Function to calculate final score for computer
                compScore = calculatefinalScore(dice1,dice2);

                //Make computer score visible
                computerResult.setVisibility(View.VISIBLE);
                TextView computerResult = (TextView)findViewById(R.id.resultNumComp);
                computerResult.setText("Computer Scored : " + compScore);

                //Disable the button after the computer result is displayed
                rollDices.setVisibility(View.INVISIBLE);
                textMessage1.setVisibility(View.INVISIBLE);
                textMessage2.setVisibility(View.VISIBLE);
                textMessage3.setVisibility(View.VISIBLE);
                diceImageView3.setVisibility(View.VISIBLE);
                sp.setVisibility(View.VISIBLE);
                playBtn.setVisibility(View.VISIBLE);
            }
        }

        );
        // code for play again button
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFinalActivity();
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Random value generated for user
                int dice3= randomDiceValue();

                int res3 = getResources().getIdentifier("dice"+dice3, "drawable", "com.test.mydice");

                //Set dice image to the random no. generated
                diceImageView3.setImageResource(res3);

                //Making Play button invisible so that user could not further roll the dice
                playBtn.setVisibility(View.INVISIBLE);

                //Calculate user score
                if(number !=null) {
                    int num = Integer.parseInt(number);
                    userScore = calculatefinalScore(num, dice3);
                }

                userResult.setVisibility(View.VISIBLE);
                TextView userResult = (TextView)findViewById(R.id.resultNumUser);
                userResult.setText("User Scored : " + userScore);

                //function to make decision for the win
                decision.setVisibility(View.VISIBLE);
                checkResult();
                playAgainButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public static int randomDiceValue(){
       return RANDOM.nextInt(6)+1;

    }


    //Function to calculate final score
    public int calculatefinalScore(int v1,int v2){

        int sum = v1 + v2 ;

       int value = sum % 6;

        return value;
    }

    //Spinner functions
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            number = parent.getItemAtPosition(position).toString();
    }

    //Spinner functions
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Function to make decision for the win
    public void checkResult(){
        if(compScore<userScore){
            decision.setText("You Win!!!!");
        }else if(compScore>userScore){
            decision.setText("Computer Won!!!!");
        }else{
            decision.setText("No one wins.Play Again!!!!");
        }

    }

    public void startFinalActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
