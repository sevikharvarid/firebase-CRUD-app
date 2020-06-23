package com.example.faq_chatapp;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Question2> question2List);
    void onFirebaseLoadFailed(String message);
}
