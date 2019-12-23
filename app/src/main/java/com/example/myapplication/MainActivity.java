package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends LoggingActivity {

    private static final int REQUEST_CODE_CHEAT = 1;

    private static final String KEY_CURRENT_INDEX = "key_current_index";

    public static final Question[] QUESTION_BANK = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    private boolean isCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        final TextView questionString = findViewById(R.id.question_string);
        final Question currentQuestion = QUESTION_BANK[mCurrentIndex];
        questionString.setText(currentQuestion.getQuestionResId());

        Button trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(true);
            }
        });

        Button falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(false);
            }
        });

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % QUESTION_BANK.length;

                final Question currentQuestion = QUESTION_BANK[mCurrentIndex];
                questionString.setText(currentQuestion.getQuestionResId());

                isCheater = false;
            }
        });

        Button cheatButton = findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Question currentQuestion = QUESTION_BANK[mCurrentIndex];
                Intent intent =
                        CheatActivity.makeIntent(MainActivity.this, currentQuestion.isCorrectAnswer());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (resultCode == RESULT_OK && CheatActivity.correctAnswerWasShown(data)) {
                isCheater = true;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
    }

    private void onButtonClicked(boolean answer) {
        Question currentQuestion = QUESTION_BANK[mCurrentIndex];
        int toastMessage;

        if (isCheater) {
            toastMessage = R.string.judgment_toast;
        } else {
            toastMessage = (currentQuestion.isCorrectAnswer() == answer) ?
                    R.string.correct_toast :
                    R.string.incorrect_toast;
        }

        Toast.makeText(
                MainActivity.this,
                toastMessage,
                Toast.LENGTH_SHORT
        ).show();
    }
}