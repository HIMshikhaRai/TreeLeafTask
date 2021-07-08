package com.himshikha.realmcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.himshikha.realmcrud.adapters.UserListAdapter;
import com.himshikha.realmcrud.models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements UserListAdapter.OnUserItemClickedListener {
    FloatingActionButton fabAddUser;
    RecyclerView userRecyclerView;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        initializeVariables();

        showDataFrmDb();

        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDataFrmDb() {
        RealmResults<User> users = realm.where(User.class).findAll();

        if (users.size() > 0) {
            UserListAdapter userListAdapter = new UserListAdapter(MainActivity.this, users,MainActivity.this);
            userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            userRecyclerView.setAdapter(userListAdapter);
        }

    }

    private void initializeVariables() {
        realm = Realm.getDefaultInstance();
    }

    private void initializeUI() {
        fabAddUser = findViewById(R.id.fab_add_user);
        userRecyclerView = findViewById(R.id.user_recycler_view);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setLogo(R.drawable.home);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDataFrmDb();
    }

    @Override
    public void onUserItemCliked(int id) {
        Intent intent = new Intent(MainActivity.this, UpdateUserActivity.class);
        intent.putExtra("user_id", id);
        startActivity(intent);
    }
}
