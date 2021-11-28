package com.example.socialize;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String myuid;
    ActionBar actionBar;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Profile Activity");
        firebaseAuth= FirebaseAuth.getInstance();

        navigationView=findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("Home");
        HomeFragment fragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment,"");
        fragmentTransaction.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    actionBar.setTitle("Socialize");
                    HomeFragment fragment=new HomeFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,fragment,"");
                    fragmentTransaction.commit();

                    return true;
                case R.id.nav_profile:
                    actionBar.setTitle("Profile");
                    com.example.socialize.ProfileFragment fragment1=new com.example.socialize.ProfileFragment();
                    FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content,fragment1);
                    fragmentTransaction1.commit();
                    return true;
                case R.id.nav_users:
                    actionBar.setTitle("Users");
                    UsersFragment fragment2=new UsersFragment();
                    FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content,fragment2,"");
                    fragmentTransaction2.commit();
                    return true;
                case R.id.nav_chat:
                    actionBar.setTitle("Chats");
                    com.example.socialize.ChatListFragment listFragment=new com.example.socialize.ChatListFragment();
                    FragmentTransaction fragmentTransaction3=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content,listFragment,"");
                    fragmentTransaction3.commit();
                    return true;
                case R.id.nav_addblogs:
                    actionBar.setTitle("Post");
                    AddBlogsFragment fragment4=new AddBlogsFragment();
                    FragmentTransaction fragmentTransaction4=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content,fragment4,"");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };
}
