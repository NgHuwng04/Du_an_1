package com.hungnn.du_an_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import android.view.Gravity;

import com.hungnn.du_an_1.ActivityPhu.CartActivity;
import com.hungnn.du_an_1.ActivityPhu.FavoritesActivity;
import com.hungnn.du_an_1.ActivityPhu.HistoryActivity;
import com.hungnn.du_an_1.ActivityPhu.ProfileActivity;
import com.hungnn.du_an_1.ActivityPhu.HotNewsActivity;
import com.hungnn.du_an_1.ActivityPhu.MemberManagementActivity;
import com.hungnn.du_an_1.ActivityPhu.AllProductsActivity;
import com.hungnn.du_an_1.LoginActivity;
import com.hungnn.du_an_1.Model.SanPham;
import com.hungnn.du_an_1.Utils.LogoutManager;
import com.hungnn.du_an_1.Utils.UserManager;
import com.hungnn.du_an_1.adapter.ProductAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private LinearLayout tabIphone, tabSamsung, tabXiaomi, tabOppo;
    private TextView tvIphone, tvSamsung, tvXiaomi, tvOppo;
    private View lineIphone, lineSamsung, lineXiaomi, lineOppo;
    private ImageView iconHome, iconFavorites, iconProfile, iconHistory;
    private ImageButton btnCart;
    private ImageButton btnMenu;
    private PopupWindow menuPopupWindow;
    private TextView tvSeemore;
    
    // Danh sách tất cả sản phẩm
    private List<SanPham> allProducts;
    private List<SanPham> currentFilteredProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Kiểm tra đăng nhập
        checkLoginStatus();

        initViews();
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo danh sách sản phẩm
        initializeProducts();
        
        // Hiển thị sản phẩm iPhone mặc định
        filterProductsByCategory(1);
        
        // Thiết lập adapter
        productAdapter = new ProductAdapter(this, currentFilteredProducts);
        recyclerViewProducts.setAdapter(productAdapter);

        setCategoryClickListeners();
        setNavigationIconClickListeners();
        setCartClickListener();
        setMenuClickListener();
        setSeeMoreClickListener();
    }

    private void initViews() {
        tabIphone = findViewById(R.id.tab_iphone);
        tabSamsung = findViewById(R.id.tab_samsung);
        tabXiaomi = findViewById(R.id.tab_xiaomi);
        tabOppo = findViewById(R.id.tab_oppo);
        tvIphone = (TextView) tabIphone.getChildAt(0);
        tvSamsung = (TextView) tabSamsung.getChildAt(0);
        tvXiaomi = (TextView) tabXiaomi.getChildAt(0);
        tvOppo = (TextView) tabOppo.getChildAt(0);
        lineIphone = tabIphone.getChildAt(1);
        lineSamsung = tabSamsung.getChildAt(1);
        lineXiaomi = tabXiaomi.getChildAt(1);
        lineOppo = tabOppo.getChildAt(1);
        iconHome = findViewById(R.id.icon_home);
        iconFavorites = findViewById(R.id.icon_favorites);
        iconProfile = findViewById(R.id.icon_profile);
        iconHistory = findViewById(R.id.icon_history);
        btnCart = findViewById(R.id.btnCart);
        btnMenu = findViewById(R.id.btnMenu);
        tvSeemore = findViewById(R.id.tvSeemore);
    }

    //Hàm tạo danh sách mẫu hiển thị recycleView trong activity_main
    private void initializeProducts() {
        allProducts = new ArrayList<>();
        
        // Sử dụng tên drawable (hãy thêm ảnh vào res/drawable với các tên dưới đây).
        int img16ProMax = getDrawableIdByName("ic_iphone16_pro_max");
        int img15ProMax = getDrawableIdByName("ic_iphone15_pro_max");
        int img14ProMax = getDrawableIdByName("ic_iphone14_pro_max");
        //
        int imgSamSungA16 = getDrawableIdByName("ic_samsung_galaxy_a16");
        int imgSamSungS24 = getDrawableIdByName("ic_samsung_galaxy_s24");
        int imgSamSungZFold7 = getDrawableIdByName("ic_samsung_galaxy_zfold7");
        //
        int imgXiaomi14TPro = getDrawableIdByName("ic_xiaomi_14t_pro");
        int imgXiaomi15Ultra = getDrawableIdByName("ic_xiaomi_15_ultra");
        int imgXiaomiPocoX7 = getDrawableIdByName("ic_xiaomi_poco_x7_pro");
        //
        int imgOppoFindN5 = getDrawableIdByName("ic_oppo_find_n5");
        int imgOppoReno14 = getDrawableIdByName("ic_oppo_reno14");
        int imgOppoReno10Pro = getDrawableIdByName("ic_oppo_reno10_pro");


        // Sản phẩm iPhone (maDanhMuc = 1)
        allProducts.add(new SanPham(1, "IPhone 16 Pro Max 256GB", "IPhone 16 Pro Max sử dụng IOS 18, Cáp sạc USB-C (1m), Kích thước màn hình 6.9 inches", 29990000, 10, 1, img16ProMax));
        allProducts.add(new SanPham(2, "IPhone 15 Pro Max 256GB", "IPhone 15 Pro Max sử dụng IOS 17, Cáp sạc USB-C, Kích thước màn hình 6.7 inches", 27690000, 10, 1, img15ProMax));
        allProducts.add(new SanPham(3, "IPhone 14 Pro Max 128GB", "IPhone 14 Pro Max sử dụng IOS 16, Cáp sạc Lightning, Kích thước màn hình 6.7 inches", 25590000, 10, 1, img14ProMax));

        // Sản phẩm Samsung (maDanhMuc = 2)
        allProducts.add(new SanPham(4, "Samsung Galaxy A16 5G 8GB 128GB", "Samsung Galaxy A16 5G 8GB 128GB Công nghệ màn hình SuperAmoled, Tần số quét 90HZ, độ sáng tối đa 800nits, Kích thước màn hình 6.7 inches", 26990000, 10, 2, imgSamSungA16));
        allProducts.add(new SanPham(5, "Samsung Galaxy S24 FE 5G 8GB 128GB", "Samsung Galaxy S24 FE 5G 8GB 128GB sử dụng Android 14, 120HZ, Pin 4700 mAH , Kích thước màn hình 6.7 inches", 12590000, 10, 2, imgSamSungS24));
        allProducts.add(new SanPham(6, "Samsung Galaxy Z Fold7 12GB 256GB", "Samsung Galaxy Z Fold7 12GB 256GB sử dụng Android, Loại CPU 8 nhân 4.47GHz 3.5GHz ,Kích thước màn hình 8 inches", 44990000, 10, 2, imgSamSungZFold7));

        // Sản phẩm Xiaomi (maDanhMuc = 3)
        allProducts.add(new SanPham(7, "Xiaomi 14T Pro 12GB 512GB", "Xiaomi 14T Pro 12GB 512GB sử dụng công nghệ màn hình Amoled, công nghệ NFC, Kích thước màn hình 6.67 inches", 14870000, 10, 3, imgXiaomi14TPro));
        allProducts.add(new SanPham(8, "Xiaomi 15 Ultra 5G 16GB 1TB", "Xiaomi 15 Ultra 5G 16GB 1TB sử dụng hệ điều hành Xiaomi HyperOS 2, Pin 5410mAH, Kích thước màn hình 6.73 inches", 33590000, 10, 3, imgXiaomi15Ultra));
        allProducts.add(new SanPham(9, "Xiaomi POCO X7 Pro 5G 12GB 256GB", "Xiaomi POCO X7 Pro 5G 12GB 256GB sử dụng công nghệ màn hình Amoled, Hệ điều hành Android 14, Kích thước màn hình 6.67 inches", 15990000, 10, 3, imgXiaomiPocoX7));

        // Sản phẩm OPPO (maDanhMuc = 4)
        allProducts.add(new SanPham(10, "OPPO FIND N5", "OPPO FIND N5 sử dụng ColorOS 15, nền tảng Android 15, Tần số: 120Hz, Kích thước màn hình 8.12 inches", 44180000, 10, 4, imgOppoFindN5));
        allProducts.add(new SanPham(11, "OPPO Reno14 F 5G 8GB 256GB", "OPPO Reno14 F 5G 8GB 256GB sử dụng ColorOS 15, Android 15, 8 nhân tối đa 2.2GHz, Kích thước màn hình 6.57 inches", 10300000, 10, 4, imgOppoReno14));
        allProducts.add(new SanPham(12, "OPPO Reno10 Pro+ 5G 12GB 256GB", "OPPO Reno10 Pro+ 5G 12GB 256GB sử dụng Android 13, độ phân giải màn hình 1240x2772 pixels, Kích thước màn hình 6.74 inch", 10990000, 10, 4, imgOppoReno10Pro));

    }

    private int getDrawableIdByName(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }

    // Hàm lọc sản phẩm theo danh mục
    private void filterProductsByCategory(int maDanhMuc) {
        currentFilteredProducts = new ArrayList<>();
        for (SanPham sanPham : allProducts) {
            if (sanPham.getMaDanhMuc() == maDanhMuc) {
                currentFilteredProducts.add(sanPham);
            }
        }
        
        // Cập nhật adapter với danh sách đã lọc
        if (productAdapter != null) {
            productAdapter.updateProducts(currentFilteredProducts);
        }
    }

    private void setMenuClickListener() {
        btnMenu.setOnClickListener(v -> {
            if (menuPopupWindow != null && menuPopupWindow.isShowing()) {
                menuPopupWindow.dismiss();
                return;
            }
            View content = LayoutInflater.from(this).inflate(R.layout.menu_popup, null, false);
            menuPopupWindow = new PopupWindow(content, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            menuPopupWindow.setOutsideTouchable(true);
            menuPopupWindow.setElevation(16f);

            // Lấy thông tin vai trò người dùng
            UserManager userManager = UserManager.getInstance(this);
            boolean isAdmin = userManager.isAdmin();

            // Ẩn/hiện các mục menu dựa trên vai trò
            View memberManagement = content.findViewById(R.id.popup_my_member);
            View productStorage = content.findViewById(R.id.popup_product_storage);
            View billManager = content.findViewById(R.id.popup_bill_manager);
            View doanhThu = content.findViewById(R.id.popup_doanhthu);
            View danhMuc = content.findViewById(R.id.popup_category);

            if (!isAdmin) {
                // Ẩn các mục trong menu nếu không phải là admin
                memberManagement.setVisibility(View.GONE);
                productStorage.setVisibility(View.GONE);
                billManager.setVisibility(View.GONE);
                doanhThu.setVisibility(View.GONE);
                danhMuc.setVisibility(View.GONE);
            }

            content.findViewById(R.id.popup_hot_news).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "Tin tức mới nhất", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HotNewsActivity.class);
                startActivity(intent);
                menuPopupWindow.dismiss();
            });
            
            if (isAdmin) {
                content.findViewById(R.id.popup_my_member).setOnClickListener(x -> {
                    Intent intent = new Intent(MainActivity.this, MemberManagementActivity.class);
                    startActivity(intent);
                    menuPopupWindow.dismiss();
                });
                
                content.findViewById(R.id.popup_product_storage).setOnClickListener(x -> {
                    Toast.makeText(MainActivity.this, "Quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                    menuPopupWindow.dismiss();
                });
                
                content.findViewById(R.id.popup_bill_manager).setOnClickListener(x -> {
                    Toast.makeText(MainActivity.this, "Quản lý đơn hàng", Toast.LENGTH_SHORT).show();
                    menuPopupWindow.dismiss();
                });
                content.findViewById(R.id.popup_category).setOnClickListener(x -> {
                    Toast.makeText(MainActivity.this, "Quản lý danh mục", Toast.LENGTH_SHORT).show();
                    menuPopupWindow.dismiss();
                });
                content.findViewById(R.id.popup_doanhthu).setOnClickListener(x -> {
                    Toast.makeText(MainActivity.this, "Thống kê doanh thu", Toast.LENGTH_SHORT).show();
                    menuPopupWindow.dismiss();
                });
            }
            
            content.findViewById(R.id.popup_my_offers).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "My offers", Toast.LENGTH_SHORT).show();
                menuPopupWindow.dismiss();
            });
            
            content.findViewById(R.id.popup_setting).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                menuPopupWindow.dismiss();
            });
            
            content.findViewById(R.id.popup_logout).setOnClickListener(x -> {
                // Thực hiện đăng xuất
                performLogout();
                menuPopupWindow.dismiss();
            });

            // Hiển thị neo tại btnMenu, lệch nhẹ để đẹp
            int[] location = new int[2];
            btnMenu.getLocationInWindow(location);
            menuPopupWindow.showAtLocation(btnMenu, Gravity.NO_GRAVITY, location[0] - 100, location[1] + btnMenu.getHeight() + 2);
        });
    }

    private void setCartClickListener() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNavigationIconClickListeners() {
        iconHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightIcon(iconHome);
            }
        });
        iconFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        iconHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        highlightIcon(iconHome);
    }

    private void highlightIcon(ImageView selectedIcon) {
        iconHome.setAlpha(0.6f);
        iconFavorites.setAlpha(0.6f);
        iconProfile.setAlpha(0.6f);
        iconHistory.setAlpha(0.6f);
        selectedIcon.setAlpha(1.0f);
    }

    private void setCategoryClickListeners() {
        tabIphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(0);
            }
        });
        tabSamsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(1);
            }
        });
        tabXiaomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(2);
            }
        });
        tabOppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory(3);
            }
        });
    }

    private void selectCategory(int categoryIndex) {
        resetAllTabs();
        int maDanhMuc = 0;
        
        switch (categoryIndex) {
            case 0: // IPHONE
                tvIphone.setTextColor(0xFFFA4A0C);
                tvIphone.setTypeface(null, android.graphics.Typeface.BOLD);
                lineIphone.setBackgroundColor(0xFFFA4A0C);
                maDanhMuc = 1;
                break;
            case 1: // SAMSUNG
                tvSamsung.setTextColor(0xFFFA4A0C);
                tvSamsung.setTypeface(null, android.graphics.Typeface.BOLD);
                lineSamsung.setBackgroundColor(0xFFFA4A0C);
                maDanhMuc = 2;
                break;
            case 2: // XIAOMI
                tvXiaomi.setTextColor(0xFFFA4A0C);
                tvXiaomi.setTypeface(null, android.graphics.Typeface.BOLD);
                lineXiaomi.setBackgroundColor(0xFFFA4A0C);
                maDanhMuc = 3;
                break;
            case 3: // OPPO
                tvOppo.setTextColor(0xFFFA4A0C);
                tvOppo.setTypeface(null, android.graphics.Typeface.BOLD);
                lineOppo.setBackgroundColor(0xFFFA4A0C);
                maDanhMuc = 4;
                break;
        }
        
        // Lọc và hiển thị sản phẩm theo danh mục được chọn
        if (maDanhMuc > 0) {
            filterProductsByCategory(maDanhMuc);
        }
    }

    private void resetAllTabs() {
        tvIphone.setTextColor(0xFF000000);
        tvIphone.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvSamsung.setTextColor(0xFF000000);
        tvSamsung.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvXiaomi.setTextColor(0xFF000000);
        tvXiaomi.setTypeface(null, android.graphics.Typeface.NORMAL);
        tvOppo.setTextColor(0xFF000000);
        tvOppo.setTypeface(null, android.graphics.Typeface.NORMAL);
        lineIphone.setBackgroundColor(0x00000000);
        lineSamsung.setBackgroundColor(0x00000000);
        lineXiaomi.setBackgroundColor(0x00000000);
        lineOppo.setBackgroundColor(0x00000000);
    }

    /**
     * Thiết lập click listener cho nút "See more"
     */
    private void setSeeMoreClickListener() {
        tvSeemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình hiển thị tất cả sản phẩm
                Intent intent = new Intent(MainActivity.this, AllProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Kiểm tra trạng thái đăng nhập
     */
    private void checkLoginStatus() {
        UserManager userManager = UserManager.getInstance(this);
        if (!userManager.isLoggedIn()) {
            // Nếu chưa đăng nhập, chuyển về LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Thực hiện đăng xuất tài khoản
     */
    private void performLogout() {
        // Sử dụng LogoutManager để thực hiện đăng xuất
        LogoutManager.performLogout(this);
    }
}