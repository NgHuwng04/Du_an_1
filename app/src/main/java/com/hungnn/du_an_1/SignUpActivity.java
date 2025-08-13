package com.hungnn.du_an_1;

import android.content.ContentValues; // Import ContentValues
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase; // Import SQLiteDatabase
import com.hungnn.du_an_1.Database.DbHelper; // Import DbHelper

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    TextView tvLogin;
    private EditText edtEmailSignUp, edtUsernameSignUp, edtPasswordSignUp;
    private Button btnSignUp;
    private DbHelper dbHelper; // Khai báo DbHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Ánh xạ các Views từ layout
        tvLogin = findViewById(R.id.tvLogIn);
        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Khởi tạo DbHelper
        dbHelper = new DbHelper(this);

        // Thiết lập padding cho System Bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện khi nút Đăng ký được nhấp
        btnSignUp.setOnClickListener(v -> {
            String email = edtEmailSignUp.getText().toString().trim();
            String username = edtUsernameSignUp.getText().toString().trim();
            String password = edtPasswordSignUp.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = dbHelper.getWritableDatabase(); // Lấy cơ sở dữ liệu có thể ghi
                ContentValues values = new ContentValues();
                values.put("ho_ten", username);
                values.put("email", email);
                values.put("mat_khau", password);
                values.put("vai_tro", "khach_hang"); // Vai trò mặc định
                values.put("trang_thai", "hoat_dong"); // Trạng thái mặc định

                long result = db.insert("nguoi_dung", null, values); // Chèn vào cơ sở dữ liệu
                db.close(); // Đóng cơ sở dữ liệu

                if (result != -1) {
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}