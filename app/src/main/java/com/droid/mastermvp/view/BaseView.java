package com.droid.mastermvp.view;

import java.util.List;

/**
 * Created by yuvrajpandey on 15/01/17.
 */
public interface BaseView {

    /*This interface basically defines the common methods that
     gonna be used to interact with view i.e Activity */

    void showSuccess(int type);

    void showFailure(int type);

    void showProgress();

    void hideProgress();

    /* The section  defines the Login specific methods */
    public interface LoginView {
        void addEmailsToAutoComplete(List<String> emails);
    }
}
