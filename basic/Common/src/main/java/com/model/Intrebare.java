package com.model;

import com.util.IgnoreTable;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class Intrebare {
    private int id;
    private String content;

    @IgnoreTable
    private String correctAnswer;

    private TestCultura testCultura;

    public Intrebare(int id, String content, String correctAnswer, TestCultura testCultura) {
        this.id = id;
        this.content = content;
        this.correctAnswer = correctAnswer;
        this.testCultura = testCultura;
    }

    public Intrebare(String content, String correctAnswer, TestCultura testCultura) {
        this.content = content;
        this.correctAnswer = correctAnswer;
        this.testCultura = testCultura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public TestCultura getTestCultura() {
        return testCultura;
    }

    public void setTestCultura(TestCultura testCultura) {
        this.testCultura = testCultura;
    }

    @Override
    public String toString() {
        return "Intrebare{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", testCultura=" + testCultura +
                '}';
    }
}
