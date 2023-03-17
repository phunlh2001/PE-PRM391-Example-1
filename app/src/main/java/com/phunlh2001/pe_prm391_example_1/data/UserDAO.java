package com.phunlh2001.pe_prm391_example_1.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User... user);

    @Update
    void update(User... user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE username=:username")
    List<User> checkUser(String username);
}
