package com.admin.snapandsplit.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.admin.snapandsplit.HomeActivity;
import com.facebook.login.LoginManager;
import com.admin.snapandsplit.R;
import com.admin.snapandsplit.login.login.LoginActivity;
import com.admin.snapandsplit.utils.Constants;
import com.admin.snapandsplit.utils.UpdateFirebaseLogin;
import com.admin.snapandsplit.utils.Utilities;

public class MainActivity extends AppCompatActivity {

    private final String TAG = Utilities.getTag (this);

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

  //  MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        fragmentManager = getSupportFragmentManager ();
        fragmentTransaction = fragmentManager.beginTransaction ();
       // addMapFragment ();

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        getSupportActionBar ().setTitle ("Snap And Split");


    }

 /*   private void addMapFragment() {

        mainActivityFragment = new MainActivityFragment ();
        fragmentTransaction.add (R.id.mapView, mainActivityFragment);
        fragmentTransaction.commit ();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout :
                UpdateFirebaseLogin.unauth ();
                SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit ();
                //editor.remove ("uid");
                //editor.remove ("provider");
                editor.remove ("user");
                editor.commit ();
                LoginManager.getInstance ().logOut ();
                Intent i = new Intent (MainActivity.this, LoginActivity.class);
                startActivity (i);
                finish ();
                break;

            case R.id.settings :
                //  startActivity (new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
