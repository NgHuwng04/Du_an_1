package com.hungnn.du_an_1.ActivityPhu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.NguoiDung;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.MemberAdapter;
import com.hungnn.du_an_1.DAO.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class MemberManagementActivity extends AppCompatActivity implements MemberAdapter.OnMemberActionListener {

    private RecyclerView recyclerViewMembers;
    private ImageButton btnBack;
    private Button btnAddMember;
    private MemberAdapter memberAdapter;
    private List<NguoiDung> membersList;
    private UserDAO userDAO;
    
    private static final int EDIT_MEMBER_REQUEST = 1001;
    private static final int ADD_MEMBER_REQUEST = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_management);

        initViews();
        setupRecyclerView();
        loadSampleData();
        setClickListeners();
    }

    private void initViews() {
        recyclerViewMembers = findViewById(R.id.recyclerViewMembers);
        btnBack = findViewById(R.id.btnBack);
        btnAddMember = findViewById(R.id.btnAddMember);
        
        // Khởi tạo UserDAO
        userDAO = new UserDAO(this);
    }

    private void setupRecyclerView() {
        membersList = new ArrayList<>();
        memberAdapter = new MemberAdapter(this, membersList);
        memberAdapter.setOnMemberActionListener(this);
        
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMembers.setAdapter(memberAdapter);
    }

    private void loadSampleData() {
        // Mở database
        userDAO.open();
        
        // Lấy danh sách khách hàng từ database
        List<NguoiDung> usersFromDB = userDAO.getCustomersOnly();
        
        // Xóa dữ liệu cũ và thêm dữ liệu mới từ database
        membersList.clear();
        membersList.addAll(usersFromDB);
        
        // Đóng database
        userDAO.close();
        
        memberAdapter.notifyDataSetChanged();
    }

    private void refreshData() {
        loadSampleData();
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình thêm khách hàng
                Intent intent = new Intent(MemberManagementActivity.this, AddMemberActivity.class);
                startActivityForResult(intent, ADD_MEMBER_REQUEST);
            }
        });
    }

    @Override
    public void onEditMember(NguoiDung member) {
        // Chuyển sang màn hình chỉnh sửa khách hàng
        Intent intent = new Intent(this, EditMemberActivity.class);
        intent.putExtra("user_id", member.getMaNguoiDung());
        intent.putExtra("username", member.getHoTen());
        intent.putExtra("email", member.getEmail());
        intent.putExtra("password", member.getMatKhau());
        intent.putExtra("role", member.getVaiTro());
        intent.putExtra("status", member.getTrangThai());
        startActivityForResult(intent, EDIT_MEMBER_REQUEST);
    }

    @Override
    public void onDeleteMember(NguoiDung member, int position) {
        // Mở database
        userDAO.open();
        
        // Xóa khách hàng khỏi database
        boolean success = userDAO.deleteUser(member.getMaNguoiDung());
        
        if (success) {
            Toast.makeText(this, "Đã xóa khách hàng: " + member.getHoTen(), Toast.LENGTH_SHORT).show();
            // Refresh dữ liệu sau khi xóa
            refreshData();
        } else {
            Toast.makeText(this, "Xóa khách hàng thất bại", Toast.LENGTH_SHORT).show();
        }
        
        // Đóng database
        userDAO.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách khi quay lại màn hình
        refreshData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == EDIT_MEMBER_REQUEST && resultCode == RESULT_OK) {
            // Nếu chỉnh sửa thành công, refresh lại dữ liệu
            if (data != null && data.getBooleanExtra("updated", false)) {
                refreshData();
                Toast.makeText(this, "Đã cập nhật thông tin khách hàng", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADD_MEMBER_REQUEST && resultCode == RESULT_OK) {
            // Nếu thêm thành công, refresh lại dữ liệu
            if (data != null && data.getBooleanExtra("added", false)) {
                refreshData();
                Toast.makeText(this, "Đã thêm khách hàng mới", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
