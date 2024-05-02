package com.example.freefin2;

import static java.lang.String.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

   // private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private FreeFinLogRepo repository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository= FreeFinLogRepo.getRepository(getApplication());

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }
private void verifyUser(){
    String username= binding.editTextUsername.getText().toString();
    if(username.isEmpty()){
        toastmaker("Username can not be blank!");
        return;
    }
    LiveData<FreeFinUser> userObserver = repository.getUserByUsername(username);
    userObserver.observe(this,user ->{
        if(user!=null){
            String password = binding.editTextPassword.getText().toString();
            if(password.equals(user.getPassword())){
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,Context.MODE_PRIVATE);
                SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
                sharedPrefEditor.apply();
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),user.getId()));
            }else{
                toastmaker("invalid password");
                binding.editTextPassword.setSelection(0);
            }
        }else{
            toastmaker(String.format("%s is not a valid username!",username));
            binding.editTextUsername.setSelection(0);
        }
    });
}

    private void toastmaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
        }

}