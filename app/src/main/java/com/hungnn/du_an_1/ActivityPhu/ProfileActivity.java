package com.hungnn.du_an_1.ActivityPhu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.Utils.UserManager;

public class ProfileActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userManager = UserManager.getInstance(this);

        com.google.android.material.appbar.MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvRole = findViewById(R.id.tvRole);

        tvName.setText(userManager.getUserName());
        tvEmail.setText(userManager.getUserEmail());
        String role = userManager.isAdmin() ? "Chủ cửa hàng" : "Khách hàng";
        tvRole.setText("Vai trò: " + role);

    }
}
