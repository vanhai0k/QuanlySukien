package com.example.quanlysukien.GiaoDien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.MainActivity;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.UserEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {

    EditText txt_password,txt_username;
    TextView login;
    CheckBox savepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        txt_password = findViewById(R.id.txt_password);
        txt_username = findViewById(R.id.txt_username);
        savepass = findViewById(R.id.savePasswordCheckBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        savepass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Lưu mật khẩu
                    String password = txt_password.getText().toString();
                    String username = txt_username.getText().toString();
                    if (!username.isEmpty() && !password.isEmpty()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", password);
                        editor.putString("username", username);
                        editor.putBoolean("savePassword", true);
                        editor.apply();
                    } else {
                        Toast.makeText(getBaseContext(), "Hãy nhập Username và Password trước khi lưu!!!", Toast.LENGTH_SHORT).show();
                        savepass.setChecked(false); // Không lưu nếu mật khẩu chưa được nhập
                    }

                } else {
                    // Xóa mật khẩu đã lưu
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("password");
                    editor.remove("username");
                    editor.putBoolean("savePassword", false);
                    editor.apply();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isPasswordSaved = sharedPreferences.getBoolean("savePassword", false);
        if (isPasswordSaved) {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (!savedPassword.isEmpty()) {
                txt_password.setText(savedPassword);
                txt_username.setText(savedUsername);
                savepass.setChecked(true);
            } else {
                Toast.makeText(this, "Nhập Username, Password trước khi lưu!!!", Toast.LENGTH_SHORT).show();
                savepass.setChecked(false); // Không check nếu mật khẩu chưa được lưu
            }
        }
        else {
            txt_password.setText(null);
            txt_username.setText(null);
            savepass.setChecked(false);
        }
        txt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Save credentials whenever the username changes
                saveCredentials();
            }
        });

        txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Save credentials whenever the password changes
                saveCredentials();
            }
        });
    }
    private void saveCredentials() {
        String password = txt_password.getText().toString();
        String username = txt_username.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.putString("username", username);
        editor.putBoolean("savePassword", true);
        editor.apply();
    }
    public void loginUser(){
        String username = txt_username.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        UserEvent userModal = new UserEvent(username,password);
        if (username.isEmpty()){
            Toast.makeText(this, "Nhập Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Nhập password", Toast.LENGTH_SHORT).show();
            return;
        }
        API.api.login(userModal).enqueue(new Callback<UserEvent>() {
            @Override
            public void onResponse(Call<UserEvent> call, Response<UserEvent> response) {
                if (response.isSuccessful()){
                    UserEvent userEvent = response.body();
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", userEvent.getPassword());
                    editor.putString("username", userEvent.getUsername());
                    editor.putString("_id", userEvent.getId());
                    editor.putString("email", userEvent.getEmail());
                    editor.putString("dob", userEvent.getDob());
                    editor.putString("sex", userEvent.getSex());
                    editor.putString("image", userEvent.getImage());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    if (response.code() == 401){
                        Toast.makeText(Login_Activity.this, "Tên người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code() == 403){
                        Toast.makeText(Login_Activity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<UserEvent> call, Throwable t) {

            }
        });
    }
}