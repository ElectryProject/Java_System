package org.example.service;

public class FeedbackServiceFactory {

    private FeedbackServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static FeedbackService create(){
        return new FeedbackServiceImpl();
    }
}
