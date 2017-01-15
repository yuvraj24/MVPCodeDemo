package com.droid.mastermvp.presenterimpl;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.droid.mastermvp.presenter.LoginPresenter;
import com.droid.mastermvp.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuvrajpandey on 15/01/17.
 */
public class LoginPresenterImpl implements LoginPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    BaseView baseView;
    BaseView.LoginView loginView;
    Activity activity;

    public LoginPresenterImpl(Activity activity, BaseView baseView, BaseView.LoginView loginView) {
        this.baseView = baseView;
        this.loginView = loginView;
        this.activity = activity;
    }

    @Override
    public void validateLogin(String mUsername, String mPassword) {
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword) && !isPasswordValid(mPassword)) {
            baseView.showFailure(0);
        } else
            // Check for a valid email address.
            if (TextUtils.isEmpty(mUsername)) {
                baseView.showFailure(1);
            } else if (!isEmailValid(mUsername)) {
                baseView.showFailure(2);
            } else {
                baseView.showProgress();
                baseView.showSuccess(0);
            }
    }

    /**
     * Represents an login/registration task used to authenticate the user.
     */

    @Override
    public void authenticateLoginCredentials(final String mUsername, final String mPassword) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isValid = false;
                for (String credential : DUMMY_CREDENTIALS) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(mUsername)) {
                        // Account exists, return true if the password matches.
                        if (pieces[1].equals(mPassword)) {
                            isValid = true;
                            break;
                        }
                    }
                }

                if (isValid) {
                    baseView.showSuccess(1);
                } else {
                    baseView.showFailure(3);
                }
                baseView.hideProgress();
            }
        }, 3000);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(activity,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        loginView.addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}
