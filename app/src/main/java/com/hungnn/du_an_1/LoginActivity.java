package com.hungnn.du_an_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor; // Import Cursor
import android.database.sqlite.SQLiteDatabase; // Import SQLiteDatabase
import com.hungnn.du_an_1.Database.DbHelper; // Import DbHelper

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp;
    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLogin;
    private DbHelper dbHelper; // Khai báo DbHelper

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

        // Khởi tạo DbHelper
        dbHelper = new DbHelper(this);

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
                SQLiteDatabase db = dbHelper.getReadableDatabase(); // Lấy cơ sở dữ liệu có thể đọc
                String[] projection = {"email", "mat_khau"};
                String selection = "email = ? AND mat_khau = ?";
                String[] selectionArgs = {email, password};

                Cursor cursor = db.query("nguoi_dung", projection, selection, selectionArgs, null, null, null);

                if (cursor.getCount() > 0) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                db.close();
            }
        });
    }
}