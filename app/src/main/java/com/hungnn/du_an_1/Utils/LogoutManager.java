package com.hungnn.du_an_1.Utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.hungnn.du_an_1.LoginActivity;
import com.hungnn.du_an_1.Utils.UserManager;

/**
 * Utility class để quản lý chức năng đăng xuất chung cho toàn bộ ứng dụng
 */
public class LogoutManager {
    
    /**
     * Thực hiện đăng xuất tài khoản
     * @param activity Activity hiện tại
     */
    public static void performLogout(Activity activity) {
        if (activity == null) return;
        
        // Xóa thông tin người dùng
        UserManager userManager = UserManager.getInstance(activity);
        userManager.clearUserInfo();
        
        // Hiển thị thông báo xác nhận
        Toast.makeText(activity, "Đang đăng xuất...", Toast.LENGTH_SHORT).show();
        
        // Chuyển về màn hình Login
        Intent intent = new Intent(activity, LoginActivity.class);
        
        // Xóa stack activity để người dùng không thể quay lại bằng nút back
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        activity.startActivity(intent);
        activity.finish(); // Đóng Activity hiện tại
    }
    
    /**
     * Thực hiện đăng xuất với thông báo tùy chỉnh
     * @param activity Activity hiện tại
     * @param message Thông báo tùy chỉnh
     */
    public static void performLogout(Activity activity, String message) {
        if (activity == null) return;
        
        // Xóa thông tin người dùng
        UserManager userManager = UserManager.getInstance(activity);
        userManager.clearUserInfo();
        
        // Hiển thị thông báo tùy chỉnh
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        
        // Chuyển về màn hình Login
        Intent intent = new Intent(activity, LoginActivity.class);
        
        // Xóa stack activity để người dùng không thể quay lại bằng nút back
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        activity.startActivity(intent);
        activity.finish(); // Đóng Activity hiện tại
    }
}
