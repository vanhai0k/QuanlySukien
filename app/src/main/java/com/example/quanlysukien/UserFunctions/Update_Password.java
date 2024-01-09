package com.example.quanlysukien.UserFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.R;
import com.example.quanlysukien.model.UserEvent;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Password extends AppCompatActivity {

    ImageView back;
    Button btnupdate;
    TextInputEditText ed_passcu,ed_passnew,nhaplai;
    String password,iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        nhaplai = findViewById(R.id.nhaplai);
        btnupdate = findViewById(R.id.btnupdate);
        ed_passnew = findViewById(R.id.ed_passnew);
        ed_passcu = findViewById(R.id.ed_passcu);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        iduser = sharedPreferences.getString("_id",null);
        password = sharedPreferences.getString("password",null);


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentPassword = ed_passcu.getText().toString();
                String newPassword = ed_passnew.getText().toString();
                String confirmPassword = nhaplai.getText().toString();

                if (newPassword.length() < 6 ){
                    Toast.makeText(getBaseContext(), "Weak password please re-enter!!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEvent userModal = new UserEvent();
                userModal.setCurrentPassword(currentPassword);
                userModal.setNewPassword(newPassword);
                userModal.setConfirmPassword(confirmPassword);
                if (!currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    API.api.updatePassword(iduser, userModal).enqueue(new Callback<UserEvent>() {
                        @Override
                        public void onResponse(Call<UserEvent> call, Response<UserEvent> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Updated password successfully", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                if (response.code() == 409){
                                    Toast.makeText(Update_Password.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getBaseContext(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    Log.e("passs", "Cập nhật mật khẩu thất bại: " + response.message());
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UserEvent> call, Throwable t) {

                        }
                    });
                }else {
                    // Hiển thị thông báo lỗi khi dữ liệu không hợp lệ
                    Toast.makeText(getBaseContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}