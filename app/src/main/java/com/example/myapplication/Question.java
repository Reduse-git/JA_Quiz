package com.example.myapplication;

public class Question {
    private int questionResId;
    private boolean correctAnswer;

    public Question(int questionResId, boolean correctAnswer) {
        this.questionResId = questionResId;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionResId() {
        return questionResId;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }
}