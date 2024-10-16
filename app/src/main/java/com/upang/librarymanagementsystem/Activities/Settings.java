package com.upang.librarymanagementsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upang.librarymanagementsystem.Api.Client.RetrofitClient;
import com.upang.librarymanagementsystem.Api.Model.ProfileResponse;
import com.upang.librarymanagementsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    TextView btnLogOut;
    ImageButton btnBackSetting;
    TextView btnEditProfile;
    TextView btnChangePassword;
    TextView btnTerms;
    TextView btnPrivacy;
    TextView btnAboutLMS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(Settings.this);
            }
        });

        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ChangePassword.class);
                startActivity(intent);
                finish();
            }
        });

        btnTerms = findViewById(R.id.btnTerms);

        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, TermsAndCondition.class);
                startActivity(intent);
                finish();
            }
        });

        btnPrivacy = findViewById(R.id.btnPrivacy);

        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, PrivacyPolicy.class);
                startActivity(intent);
                finish();
            }
        });

        btnAboutLMS = findViewById(R.id.btnAboutLMS);

        btnAboutLMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AboutLMS.class);
                startActivity(intent);
                finish();
            }
        });

        btnBackSetting = findViewById(R.id.btnBackSetting);

        btnBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AccountPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void logOut(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("auth_token");
        editor.apply();

        Intent intent = new Intent(Settings.this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void signOut(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);

        if (token != null){
            Call<Void> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .logout(token);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("auth_token");
                    editor.apply();

                    Intent intent = new Intent(context, LoginPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Toast.makeText(Settings.this, "Sign out Failed", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}