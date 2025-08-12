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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private LinearLayout tabIphone, tabSamsung, tabXiaomi, tabOppo;
    private TextView tvIphone, tvSamsung, tvXiaomi, tvOppo;
    private View lineIphone, lineSamsung, lineXiaomi, lineOppo;
    private ImageView iconHome, iconFavorites, iconProfile, iconHistory;
    private ImageButton btnCart;
    private ImageButton btnMenu;
    private PopupWindow menuPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setCategoryClickListeners();
        setNavigationIconClickListeners();
        setCartClickListener();
        setMenuClickListener();
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

            content.findViewById(R.id.popup_my_member).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "My Member", Toast.LENGTH_SHORT).show();
                menuPopupWindow.dismiss();
            });
            content.findViewById(R.id.popup_my_offers).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "My offers", Toast.LENGTH_SHORT).show();
                menuPopupWindow.dismiss();
            });
            content.findViewById(R.id.popup_setting).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                menuPopupWindow.dismiss();
            });
            content.findViewById(R.id.popup_logout).setOnClickListener(x -> {
                Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
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
        switch (categoryIndex) {
            case 0:
                tvIphone.setTextColor(0xFFFA4A0C);
                tvIphone.setTypeface(null, android.graphics.Typeface.BOLD);
                lineIphone.setBackgroundColor(0xFFFA4A0C);
                break;
            case 1:
                tvSamsung.setTextColor(0xFFFA4A0C);
                tvSamsung.setTypeface(null, android.graphics.Typeface.BOLD);
                lineSamsung.setBackgroundColor(0xFFFA4A0C);
                break;
            case 2:
                tvXiaomi.setTextColor(0xFFFA4A0C);
                tvXiaomi.setTypeface(null, android.graphics.Typeface.BOLD);
                lineXiaomi.setBackgroundColor(0xFFFA4A0C);
                break;
            case 3:
                tvOppo.setTextColor(0xFFFA4A0C);
                tvOppo.setTypeface(null, android.graphics.Typeface.BOLD);
                lineOppo.setBackgroundColor(0xFFFA4A0C);
                break;
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
}