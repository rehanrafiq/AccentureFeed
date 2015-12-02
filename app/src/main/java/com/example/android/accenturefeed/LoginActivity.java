package com.example.android.accenturefeed;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public ProgressDialog pdialog;
    public AlertDialog alertDialog;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1901;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        for facebook signin
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.login_activity);

//        for google signin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        for logging out from facebook
        LoginManager.getInstance().logOut();

//        checks internet
        if (isNetworkAvailable()==true){

//            google login button
            SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
            signInButton.setOnClickListener(this);

//            shows error
            info = (TextView)findViewById(R.id.info);

//            facebook login button
            loginButton = (LoginButton)findViewById(R.id.login_button);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                    startActivity(intent);
//                    info.setText(
//                            "User ID: "
//                                    + loginResult.getAccessToken().getUserId()
//                                    + "\n" +
//                                    "Auth Token: "
//                                    + loginResult.getAccessToken().getToken()
//                    );

                }

                @Override
                public void onCancel() {

                    info.setText("Login attempt canceled.");
                }

                @Override
                public void onError(FacebookException error) {
                    info.setText("Login attempt failed.");

                }
            });


            Button b1=(Button)findViewById(R.id.loginbutton);
        final EditText user =(EditText)findViewById(R.id.editTextentername);
        final EditText pass =(EditText)findViewById(R.id.edittextenterpassword);

        b1.setOnClickListener(new View.OnClickListener() {

                                  @Override
                                  public void onClick(View v) {
                                      String givenUsername = user.getText().toString();
                                      String givenPassword = pass.getText().toString();

                                      if (givenUsername.isEmpty() || givenPassword.isEmpty()) {
                                          TextView textView = (TextView) findViewById(R.id.error);
                                          textView.setText("Username & password required!");


                                      } else {
                                          pdialog = ProgressDialog.show(LoginActivity.this, "Authenticating", "Please wait...");

                                          sendLoginRequest(givenUsername, givenPassword);
                                      }
                                  }

                                  private void sendLoginRequest(final String givenUsername, final String givenPassword) {


                                      SendLoginRequestAsyncTask asyncTask = (SendLoginRequestAsyncTask) new SendLoginRequestAsyncTask(new SendLoginRequestAsyncTask.AsyncResponse() {


                                          @Override
                                          public void processFinish(String name) {
                                              if (name.isEmpty()) {

                                                  TextView textView = (TextView) findViewById(R.id.error);
                                                  textView.setTextColor(Color.parseColor("#d41a14"));
                                                  textView.setText("Username or Password Is Incorrect!");
                                                  pdialog.dismiss();
                                              } else {
                                                  Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                                                  intent.putExtra("username", name);
                                                  pdialog.dismiss();
                                                  startActivity(intent);
                                              }
                                          }
                                      }).execute(givenUsername, givenPassword);

                                  }
                              }
        );
    }

        else {

            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Check Your Internet Connection");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        for google
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
//        for facebook
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

//    for google to open next activity or not
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
            startActivityForResult(intent, 1);

        } else {
            // Signed out, show unauthenticated UI.
            info.setText("Login attempt failed.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
               Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                   @Override
                   public void onResult(Status status) {
                       mGoogleApiClient.clearDefaultAccountAndReconnect();
                   }
               });
//            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//                @Override
//                public void onResult(Status status) {
//                    mGoogleApiClient.disconnect();
//                }
//            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //    checks Internet is available or not
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

//    for google sign in button on click method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

//    google sign in method
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

// google connection failed method
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        info.setText("Login attempt failed.");
    }
}