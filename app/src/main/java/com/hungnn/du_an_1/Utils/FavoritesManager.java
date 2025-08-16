package com.hungnn.du_an_1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hungnn.du_an_1.Model.SanPham;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class để quản lý danh sách sản phẩm yêu thích
 */
public class FavoritesManager {
    
    private static final String PREF_NAME = "favorites_pref";
    private static final String KEY_FAVORITES = "favorites_list";
    private static final String TAG = "FavoritesManager";
    
    private static FavoritesManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    
    private FavoritesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }
    
    public static synchronized FavoritesManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesManager(context.getApplicationContext());
        }
        return instance;
    }
    
    /**
     * Thêm sản phẩm vào danh sách yêu thích
     */
    public boolean addToFavorites(SanPham sanPham) {
        try {
            List<SanPham> favorites = getFavorites();
            
            // Kiểm tra xem sản phẩm đã có trong danh sách chưa
            for (SanPham existing : favorites) {
                if (existing.getMaSanPham() == sanPham.getMaSanPham()) {
                    return false; // Sản phẩm đã có trong danh sách
                }
            }
            
            favorites.add(sanPham);
            saveFavorites(favorites);
            Log.d(TAG, "Đã thêm sản phẩm vào yêu thích: " + sanPham.getTenSanPham());
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi thêm vào yêu thích: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Xóa sản phẩm khỏi danh sách yêu thích
     */
    public boolean removeFromFavorites(int maSanPham) {
        try {
            List<SanPham> favorites = getFavorites();
            boolean removed = false;
            
            for (int i = favorites.size() - 1; i >= 0; i--) {
                if (favorites.get(i).getMaSanPham() == maSanPham) {
                    favorites.remove(i);
                    removed = true;
                    break;
                }
            }
            
            if (removed) {
                saveFavorites(favorites);
                Log.d(TAG, "Đã xóa sản phẩm khỏi yêu thích: " + maSanPham);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi xóa khỏi yêu thích: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Kiểm tra xem sản phẩm có trong danh sách yêu thích không
     */
    public boolean isFavorite(int maSanPham) {
        try {
            List<SanPham> favorites = getFavorites();
            for (SanPham sanPham : favorites) {
                if (sanPham.getMaSanPham() == maSanPham) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi kiểm tra yêu thích: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lấy danh sách sản phẩm yêu thích
     */
    public List<SanPham> getFavorites() {
        try {
            String json = sharedPreferences.getString(KEY_FAVORITES, "[]");
            Type type = new TypeToken<ArrayList<SanPham>>(){}.getType();
            List<SanPham> favorites = gson.fromJson(json, type);
            return favorites != null ? favorites : new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy danh sách yêu thích: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lưu danh sách yêu thích vào SharedPreferences
     */
    private void saveFavorites(List<SanPham> favorites) {
        try {
            String json = gson.toJson(favorites);
            sharedPreferences.edit().putString(KEY_FAVORITES, json).apply();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lưu danh sách yêu thích: " + e.getMessage());
        }
    }
    
    /**
     * Xóa toàn bộ danh sách yêu thích
     */
    public void clearAllFavorites() {
        try {
            sharedPreferences.edit().remove(KEY_FAVORITES).apply();
            Log.d(TAG, "Đã xóa toàn bộ danh sách yêu thích");
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi xóa danh sách yêu thích: " + e.getMessage());
        }
    }
    
    /**
     * Lấy số lượng sản phẩm yêu thích
     */
    public int getFavoritesCount() {
        return getFavorites().size();
    }
}
