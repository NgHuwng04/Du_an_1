package com.hungnn.du_an_1.ActivityPhu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.R;

public class AddMemberActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText edtUsername, edtEmail, edtPassword;
    private Button btnSave, btnCancel;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        initViews();
        setClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dbHelper = new DbHelper(this);
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveMemberData());
    }

    private void saveMemberData() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra email hợp lệ
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu
        if (password.length() < 3) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            
            // Kiểm tra email đã tồn tại chưa
            android.database.Cursor cursor = db.query("nguoi_dung", 
                new String[]{"email"}, 
                "email = ?", 
                new String[]{email}, 
                null, null, null);
            
            if (cursor.getCount() > 0) {
                Toast.makeText(this, "Email đã tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
                return;
            }
            cursor.close();
            
            ContentValues values = new ContentValues();
            values.put("ho_ten", username);
            values.put("email", email);
            values.put("mat_khau", password);
            values.put("vai_tro", "khach_hang"); // Mặc định là khách hàng
            values.put("trang_thai", "hoat_dong"); // Mặc định là hoạt động

            // Thêm khách hàng mới
            long result = db.insert("nguoi_dung", null, values);

            if (result != -1) {
                Toast.makeText(this, "Thêm khách hàng thành công!", Toast.LENGTH_SHORT).show();
                
                // Trả về kết quả để cập nhật danh sách
                Intent resultIntent = new Intent();
                resultIntent.putExtra("added", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Thêm khách hàng thất bại", Toast.LENGTH_SHORT).show();
            }
            
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
