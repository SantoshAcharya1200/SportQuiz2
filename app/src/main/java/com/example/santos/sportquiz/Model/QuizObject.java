package com.example.santos.sportquiz.Model;

/**
 * Created by santosh on 2/18/2018.
 */

public class QuizObject {
    private String imagePath;
    private String quizName;

    public QuizObject(String imagePath, String quizName) {
        this.imagePath = imagePath;
        this.quizName = quizName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getQuizName() {
        return quizName;
    }
}
