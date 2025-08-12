package com.hungnn.du_an_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp;
    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ánh xạ các Views từ layout
        tvSignUp = findViewById(R.id.tvSignUp);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // Thiết lập padding cho System Bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện nhấp chuột cho TextView "Sign Up"
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện khi nút Đăng nhập được nhấp
        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString().trim();
            String password = edtPasswordLogin.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                // Lấy SharedPreferences để lấy dữ liệu đã lưu
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                String savedEmail = sharedPreferences.getString("email", ""); // "" là giá trị mặc định nếu không tìm thấy
                String savedPassword = sharedPreferences.getString("password", ""); // "" là giá trị mặc định nếu không tìm thấy

                // So sánh thông tin nhập vào với dữ liệu đã lưu
                if (email.equals(savedEmail) && password.equals(savedPassword)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển đến màn hình chính hoặc màn hình tiếp theo (ví dụ: MainActivity)
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     startActivity(intent);
                     finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}