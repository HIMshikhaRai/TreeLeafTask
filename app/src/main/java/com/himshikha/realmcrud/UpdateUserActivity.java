package com.himshikha.realmcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.himshikha.realmcrud.models.User;

import io.realm.Realm;

public class UpdateUserActivity extends AppCompatActivity {
    Spinner genderSpinner;
    TextInputEditText name, email, address, description;
    Button btnUpdate;
    
    Realm realm;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        initializeUI();
        initializeVariables();
        
        getUserData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputValid())
                    updateUserData();
            }
        });
    }

    private void updateUserData() {
        final User user = new User();
        user.setId(userId);
        user.setName(name.getText().toString());
        user.setAddress(address.getText().toString());
        user.setDescription(description.getText().toString());
        user.setEmail(email.getText().toString());
        user.setGender(genderSpinner.getSelectedItemPosition());

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm mRealm) {
                mRealm.insertOrUpdate(user);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                showToast("User successfully updated !!!");
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                showToast(error.getMessage());
            }
        });
    }
    private boolean isInputValid() {
        int error = 0;

        if (name.getText().toString().isEmpty()) {
            error++;
            name.setError("Field is required!");
        }

        if (email.getText().toString().isEmpty()) {
            error++;
            email.setError("Field is required!");
        }

        if (address.getText().toString().isEmpty()) {
            error++;
            address.setError("Field is required!");
        }

        if (description.getText().toString().isEmpty()) {
            error++;
            description.setError("Field is required!");
        }

        return error == 0;
    }
    
    private void getUserData() {
        User user = realm.where(User.class).equalTo("id",userId).findFirst();
        
        name.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
        description.setText(user.getDescription());
        genderSpinner.setSelection(user.getGender());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initializeVariables() {
        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        userId =intent.getIntExtra("user_id",0);
    }

    private void initializeUI() {
        name = findViewById(R.id.name_text_field);
        email = findViewById(R.id.email_text_field);
        address = findViewById(R.id.address_text_field);
        description = findViewById(R.id.description_text_field);
        genderSpinner = findViewById(R.id.gender_spinner);
        btnUpdate = findViewById(R.id.btn_update);
        getSupportActionBar().setTitle("Update User");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
