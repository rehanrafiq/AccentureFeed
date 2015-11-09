package com.example.android.accenturefeed;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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
                                                  //     Toast.makeText(getApplicationContext(), "Username or password is incorrect.", Toast.LENGTH_LONG).show();

                                              } else {
                                                  Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
                                                  Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
                                      }).execute(givenUsername, givenPassword);

                                  }


                              }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up loginbutton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}