package word.game.sudaraje.firstapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sudaraje on 1/15/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
//    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        this.callbackManager = CallbackManager.Factory.create();
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        if(!loggedIn)
            startActivity(intent);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            ProfileTracker mProfileTracker;
            String userName;
            @Override
            public void onSuccess(LoginResult loginResult) {

                final String accessToken = loginResult.getAccessToken().getToken();

                // Facebook Graph Search
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    String Name = object.getString("name");
                                    Session session = new Session(getApplicationContext());
                                    session.setUsename(Name);
                                    session.setAccesstoken(accessToken.toString());
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            //handleSignInResult(task);
//        }
    }

}
