package word.game.sudaraje.firstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sudaraje on 1/16/2018.
 */

public class Session {
    private SharedPreferences prefs;

    public String isFbLoggedIn() {
        String isFbLoggedIn = prefs.getString("fbLoggedIn","");
        return isFbLoggedIn;
    }

    public void setFbLoggedIn(boolean fbLoggedIn) {
        prefs.edit().putString("fbLoggedIn", String.valueOf(fbLoggedIn)).commit();
    }

    public String isGoogleLoggedIn() {
        String googleLoggedIn = prefs.getString("googleLoggedIn","");
        return googleLoggedIn;
    }

    public void setGoogleLoggedIn(boolean googleLoggedIn) {
        prefs.edit().putString("googleLoggedIn", String.valueOf(googleLoggedIn)).commit();

    }

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsename(String usename) {
        prefs.edit().putString("username", usename).commit();
    }

    public String getUsename() {
        String usename = prefs.getString("username","");
        return usename;
    }

    public void setAccesstoken(String accessToken) {
        prefs.edit().putString("accessToken", accessToken).commit();
    }
    public String getAccesstoken() {
        String accessToken = prefs.getString("accessToken","");
        return accessToken;
    }
    public boolean deleteToken(){
        prefs.edit().remove("accessToken").commit();
        prefs.edit().remove("username").commit();
        prefs.edit().remove("googleLoggedIn").commit();
        prefs.edit().remove("fbLoggedIn").commit();

        if(prefs.getString("accessToken","").equals("") && prefs.getString("username","").equals("")){
            return true;
        }
        else{
            return false;
        }
    }


}
