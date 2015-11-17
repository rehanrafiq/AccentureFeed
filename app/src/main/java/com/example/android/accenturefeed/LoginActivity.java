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

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    public ProgressDialog pdialog;
    public AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (isNetworkAvailable()==true){

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
                                                  textView.setText("user name or password is incorrect!");
                                                  pdialog.dismiss();
                                              } else {
                                                  Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                                                  intent.putExtra("username",name);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

}