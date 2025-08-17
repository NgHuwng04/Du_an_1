package com.hungnn.du_an_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.BaiViet;
import com.hungnn.du_an_1.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<BaiViet> newsList;
    private OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClick(BaiViet baiViet);
    }

    public NewsAdapter(Context context, List<BaiViet> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void setOnNewsClickListener(OnNewsClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        BaiViet baiViet = newsList.get(position);
        
        holder.tvNewsTitle.setText(baiViet.getTieuDe());
        holder.tvAuthor.setText(baiViet.getTenTacGia());
        holder.tvDate.setText(baiViet.getNgayDang());
        
        // Tải hình ảnh động dựa trên tên file
        String imageName = baiViet.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            // Loại bỏ phần mở rộng .png hoặc .jpg để lấy tên drawable
            String drawableName = imageName.substring(0, imageName.lastIndexOf('.'));
            int imageResId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            if (imageResId != 0) {
                holder.imgNews.setImageResource(imageResId);
            } else {
                // Fallback nếu không tìm thấy hình ảnh
                holder.imgNews.setImageResource(R.drawable.news_placeholder);
            }
        } else {
            // Fallback nếu không có tên hình ảnh
            holder.imgNews.setImageResource(R.drawable.news_placeholder);
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNewsClick(baiViet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateNewsList(List<BaiViet> newList) {
        this.newsList = newList;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView tvNewsTitle, tvAuthor, tvDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
