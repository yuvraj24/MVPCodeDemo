package com.droid.mastermvp.presenter;

/**
 * Created by yuvrajpandey on 15/01/17.
 */
public interface LoginPresenter {

    /*This Interface holds the method to access all the presenter methods*/

    void validateLogin(String mUsername, String mPassword);

    void authenticateLoginCredentials(String mUsername, String mPassword);
}
