package com.ace.hangman.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    EditText guessInput;
    TextView current;
    TextView wrongGuesses;
    TextView mainMessage;
    ImageView hangman;
    Button submit;
    TextView livesLeft;
    int lives = 5;
    String wrongLetters = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hangman = (ImageView)findViewById(R.id.ivHangman);
        mainMessage = (TextView)findViewById(R.id.tvMainMessage);
        current = (TextView)findViewById(R.id.tvCorrectGuesses);
        wrongGuesses = (TextView)findViewById(R.id.tvWrongLetters);
        guessInput = (EditText)findViewById(R.id.etGuess);
        submit = (Button)findViewById(R.id.bSubmit);
        livesLeft = (TextView)findViewById(R.id.tvLivesLeft);
        String[] wordBank = {"according", "act", "actually", "Africa", "alike", "apart", "ate", "attention", "bank", "basic", "beat", "blow", "bone", "bread", "careful", "chair", "chief", "Christmas", "church", "cloth", "cloud", "column", "compare", "contain", "continued", "cost", "cotton", "count", "dance", "describe", "desert", "dinner", "doctor", "dollar", "drop", "dropped", "ear", "east", "electric", "element", "enjoy", "equal", "exercise", "experiment", "familiar", "farther", "fear", "forth", "gas", "giving", "gray", "grown", "hardly", "hat", "hill", "hurt", "imagine", "include", "indeed", "Johnny", "joined", "key", "kitchen", "knowledge", "law", "lie", "major", "met", "metal", "movement", "nation", "nature", "nine", "none", "office", "older", "onto", "original", "paragraph", "parent", "particular", "path", "Paul", "Peter", "pick", "president", "pressure", "process", "public", "quick", "report", "rope", "rose", "row", "safe", "salt", "Sam", "scale", "sell", "separate", "sheep", "shoe", "shore", "simply", "sing", "sister", "sitting", "sold", "soldier", "solve", "speech", "spend", "steel", "string", "student", "studied", "sugar", "television", "term", "throughout", "tired", "total", "touch", "trade", "truck", "twice", "type", "uncle", "unless", "useful", "value", "verb", "visit", "wear", "wheel", "William", "wing", "wire", "won", "wonder", "worker", "yard"};
        final HashSet<Character> guesses = new HashSet<Character>();


        Random generator = new Random();
        int wordBankIndex = generator.nextInt(wordBank.length);
        final String secretWord = wordBank[wordBankIndex];
        final HashSet<Character> secretHashSet = new HashSet<Character>();
        for(int i = 0; i < secretWord.length(); i++){
            secretHashSet.add(secretWord.charAt(i));
        }

        currentDisplay(guesses, secretWord);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentGuess = guessInput.getText().toString().toLowerCase().trim();

                //Check if the input is a single alpha numeric character
                if (currentGuess.matches("[a-zA-Z]") && currentGuess.length() == 1){
                    Character charCurrentGuess = currentGuess.charAt(0);
                    if (secretHashSet.contains(charCurrentGuess)) {
                        current.setText(current.getText().toString() + currentGuess);
                        guesses.add(charCurrentGuess);
                        //Toast.makeText(getApplicationContext(), "The words is " + secretWord, Toast.LENGTH_SHORT).show();
                        currentDisplay(guesses, secretWord);
                    }
                    else {
                        lives -= 1;
                        wrongLetters += currentGuess + " ";
                        wrongGuesses.setText(wrongLetters);

                        //Advance the drawing 1 step
                        if(lives > 0 ){
                            if(lives > 1) {
                                livesLeft.setText("You have " + lives + " lives remaining");
                            }
                            else{
                                livesLeft.setText("You have " + lives + " life remaining");
                            }
                        }
                        else{
                            mainMessage.setText("GAME OVER");
                            livesLeft.setText("");
                            hangman.setImageResource(R.drawable.game_over);
                            guessInput.setEnabled(false);
                            wrongGuesses.setText("The word was " + secretWord);
                            submit.setText(R.string.resart);
                            submit.setOnClickListener(null);
                            submit.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    return false;
                                }
                            });
                        }


                    }

                }
                //Tell the user to enter correct input
                else {
                    Toast.makeText(getApplicationContext(),"Enter a one letter guess!", Toast.LENGTH_SHORT).show();
                }

                //Closes the keyboard so we can see the rest of the screeen
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(guessInput.getWindowToken(), 0);

                //Clears the edit text for teh next input
                guessInput.setText("");
            }

        });
    }

    /**
     * This method takes a hash set of the guesses so far and and the secret words
     * and appropriately sets the text of the correct letters text view
     * @param guesses - A char hash set containin all the guesses so far
     * @param secretWord - the words users are guessing
     */
    private void currentDisplay(HashSet<Character> guesses, String secretWord){

        String state = "";

        for (int i = 0; i < secretWord.length(); i++ ){
            if (guesses.contains(secretWord.charAt(i))){
                state += secretWord.charAt(i) + " ";

            }
            else {
                state +=  "- ";

            }

        }
        current.setText(state);

    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

