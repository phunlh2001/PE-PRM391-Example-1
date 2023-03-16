package com.phunlh2001.pe_prm391_example_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phunlh2001.pe_prm391_example_1.data.AppDatabase;
import com.phunlh2001.pe_prm391_example_1.data.User;

public class UpdateActivity extends AppCompatActivity {

    private EditText editUsername, editAddress;
    private Button btnUpdate;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editUsername = findViewById(R.id.edit_username);
        editAddress = findViewById(R.id.edit_address);
        btnUpdate = findViewById(R.id.btn_update);

        mUser = (User) getIntent().getExtras().get("user");
        if (mUser != null) {
            editUsername.setText(mUser.getUsername());
            editAddress.setText(mUser.getAddress());
        }

        btnUpdate.setOnClickListener(view -> updateUser());
    }

    private void updateUser() {
        String strName = editUsername.getText().toString().trim();
        String strAddress = editAddress.getText().toString().trim();

        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strAddress)) return;

        // update data
        mUser.setUsername(strName);
        mUser.setAddress(strAddress);

        AppDatabase.getInstance(this).userDAO().update(mUser);
        Toast.makeText(this, "Update user successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}