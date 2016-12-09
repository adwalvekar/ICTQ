package in.adityawalvekar.ictq;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "YIKES";
    String t, d, b, u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("server");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        User user = dataSnapshot.getValue(User.class);
                        if(!user.position.equals("Student")){
                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.dialogbox);
                            final EditText title, date, body, url;
                            title = (EditText) dialog.findViewById(R.id.dialogTitle);
                            date =(EditText) dialog.findViewById(R.id.dialogSubText);
                            url = (EditText) dialog.findViewById(R.id.dialogURL);
                            body = (EditText) dialog.findViewById(R.id.dialogBody);
                            Button submit, cancel;
                            submit = (Button) dialog.findViewById(R.id.submit);
                            cancel = (Button)dialog.findViewById(R.id.cancel);
                            dialog.setTitle("Add Information");
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    t = title.getText().toString();
                                    b = body.getText().toString();
                                    d = date.getText().toString();
                                    u = url.getText().toString();
                                    SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    int n = sharedpref.getInt("no", 0);
                                    Info info = new Info(t,d,b,u);
                                    ref.child("info").child(Integer.toString(++n)).setValue(info);
                                    dialog.dismiss();
                                }
                            });

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                        else{
                            Snackbar snackbar = Snackbar
                                    .make(view, "You cannot post notifications", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(),"Failed to Load Data",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        String name;
        name= user.getEmail();
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View hView =  nv.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.navTVmain);
        nav_user.setText(name);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("Information");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new Information();
        transaction.replace(R.id.container,fragment); // newInstance() is a static factory method.
        transaction.commit();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_info) {
            setTitle("Information");
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = new Information();
            transaction.replace(R.id.container,fragment); // newInstance() is a static factory method.
            transaction.commit();
        } else if (id == R.id.nav_notification) {
            setTitle("Notifications");
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = new Notif();
            transaction.replace(R.id.container,fragment); // newInstance() is a static factory method.
            transaction.commit();

        }else if (id == R.id.nav_schedule) {
            setTitle("Schedule");
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = new Schedule();
            transaction.replace(R.id.container,fragment); // newInstance() is a static factory method.
            transaction.commit();

        }else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
