package com.phunlh2001.pe_prm391_example_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phunlh2001.pe_prm391_example_1.data.AppDatabase;
import com.phunlh2001.pe_prm391_example_1.data.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername, editAddress;
    private Button btnAdd;
    private RecyclerView rcvUser;
    private TextView tvClear;

    private UserAdapter adapter;
    private List<User> mListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        adapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(User user) {
                handleClickUpdate(user);
            }

            @Override
            public void deleteUser(User user) {
                handleClickDelete(user);
            }
        });
        mListUser = new ArrayList<>();
        adapter.setData(mListUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        rcvUser.setAdapter(adapter);

        btnAdd.setOnClickListener(view -> addData());
        
        tvClear.setOnClickListener(view -> deleteAllData());

        loadData();
    }

    private void init() {
        editUsername = findViewById(R.id.edit_username);
        editAddress = findViewById(R.id.edit_address);
        btnAdd = findViewById(R.id.btn_add);
        rcvUser = findViewById(R.id.rcv_user);
        tvClear = findViewById(R.id.tv_clear);
    }

    private void addData() {
        String strName = editUsername.getText().toString().trim();
        String strAddress = editAddress.getText().toString().trim();

        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strAddress)) return;

        User user = new User(strName, strAddress);
        if (isUserExist(user)) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase.getInstance(this).userDAO().insert(user);
        Toast.makeText(this, "Add user successfully", Toast.LENGTH_SHORT).show();

        editUsername.setText("");
        editAddress.setText("");

        hideSoftKeyboard();

        loadData();
    }

    private void loadData() {
        mListUser = AppDatabase.getInstance(this).userDAO().getAll();
        adapter.setData(mListUser);
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isUserExist(@NonNull User user) {
        List<User> list = AppDatabase.getInstance(this).userDAO().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }

    private void handleClickUpdate(User user) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void handleClickDelete(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // delete here
                    AppDatabase.getInstance(this).userDAO().delete(user);
                    Toast.makeText(this, "Delete user successfully", Toast.LENGTH_SHORT).show();

                    loadData();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteAllData() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete all user")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // delete here
                    AppDatabase.getInstance(this).userDAO().deleteAll();
                    Toast.makeText(this, "Delete all user successfully", Toast.LENGTH_SHORT).show();

                    loadData();
                })
                .setNegativeButton("No", null)
                .show();
    }
}