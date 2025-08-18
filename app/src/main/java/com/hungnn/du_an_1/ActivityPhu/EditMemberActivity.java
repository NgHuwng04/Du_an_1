package com.hungnn.du_an_1.ActivityPhu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.Database.DbHelper;
import com.hungnn.du_an_1.Model.NguoiDung;
import com.hungnn.du_an_1.R;

public class EditMemberActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText edtUsername, edtEmail, edtPassword;
    private TextView tvRole, tvStatus;
    private Button btnSave, btnCancel;
    private NguoiDung member;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        initViews();
        loadMemberData();
        setClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvRole = findViewById(R.id.tvRole);
        tvStatus = findViewById(R.id.tvStatus);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dbHelper = new DbHelper(this);
    }

    private void loadMemberData() {
        // Lấy dữ liệu khách hàng từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            member = new NguoiDung(
                intent.getIntExtra("user_id", -1),
                intent.getStringExtra("username"),
                intent.getStringExtra("email"),
                intent.getStringExtra("password"),
                intent.getStringExtra("role"),
                intent.getStringExtra("status")
            );

            // Hiển thị dữ liệu lên các EditText
            edtUsername.setText(member.getHoTen());
            edtEmail.setText(member.getEmail());
            edtPassword.setText(member.getMatKhau());
            
            // Hiển thị vai trò và trạng thái (chỉ đọc)
            String roleText = "Vai trò: " + (member.getVaiTro().equals("khach_hang") ? "Khách hàng" : "Chủ cửa hàng");
            tvRole.setText(roleText);
            
            String statusText = "Trạng thái: " + (member.getTrangThai().equals("hoat_dong") ? "Hoạt động" : "Bị khóa");
            tvStatus.setText(statusText);
        }
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
            
            ContentValues values = new ContentValues();
            values.put("ho_ten", username);
            values.put("email", email);
            values.put("mat_khau", password);
            // Không thay đổi vai_tro và trang_thai

            // Cập nhật thông tin khách hàng
            int rowsAffected = db.update("nguoi_dung", values, "ma_nguoi_dung = ?", 
                                       new String[]{String.valueOf(member.getMaNguoiDung())});

            if (rowsAffected > 0) {
                Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                
                // Trả về kết quả để cập nhật danh sách
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
