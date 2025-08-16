package com.hungnn.du_an_1.ActivityPhu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungnn.du_an_1.Model.BaiViet;
import com.hungnn.du_an_1.R;
import com.hungnn.du_an_1.adapter.NewsAdapter;
import com.hungnn.du_an_1.ActivityPhu.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HotNewsActivity extends AppCompatActivity {

    private RecyclerView rvNews;
    private NewsAdapter newsAdapter;
    private List<BaiViet> newsList;
    private List<BaiViet> allNewsList; // Danh sách gốc để tìm kiếm
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_news);

        initViews();
        setupRecyclerView();
        loadSampleData();
        setupClickListeners();
    }

    private void initViews() {
        rvNews = findViewById(R.id.rvNews);
        etSearch = findViewById(R.id.etSearch);
        ImageButton btnBack = findViewById(R.id.btnBack);
        
        btnBack.setOnClickListener(v -> finish());
        
        // Thiết lập tìm kiếm
        setupSearch();
    }

    private void setupRecyclerView() {
        newsList = new ArrayList<>();
        allNewsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.setAdapter(newsAdapter);
        
        newsAdapter.setOnNewsClickListener(baiViet -> {
            // Chuyển sang màn hình chi tiết bài viết
            Intent intent = new Intent(HotNewsActivity.this, NewsDetailActivity.class);
            intent.putExtra("baiViet", baiViet);
            startActivity(intent);
        });
    }

    private void loadSampleData() {
        // Dữ liệu mẫu dựa trên hình ảnh và thêm nhiều bài viết khác
        allNewsList.add(new BaiViet(1, 
            "Đây là thiết kế Galaxy S25 FE: Vẻ ngoài quen thuộc, thiết kế mỏng hơn",
            "Loạt ảnh rò rỉ mới nhất cho thấy điện thoại Galaxy S25 FE sắp ra mắt của Samsung sẽ có một vài thay đổi về thiết kế với viền màn hình và thân máy mỏng hơn.\n" +
                    "\n" +
                    "Theo các báo cáo trước đây cho biết, Samsung đang phát triển một thành viên mới của dòng Galaxy S25, đó là Galaxy S25 FE. Như tên gọi cho thấy, điện thoại này là phiên bản kế nhiệm của S24 FE được khá nhiều người ưa chuộng. Mới đây, hình ảnh render của điện thoại vừa được chia sẻ trên mạng và nhờ đó mà chúng ta có được cái nhìn cận cảnh về thiết kế sản phẩm.Đây là thiết kế Galaxy S25 FE: Vẻ ngoài quen thuộc, thiết kế mỏng hơn (cập nhật ngày 15/8/2025)\n" +
                    "Cụ thể, mẫu điện thoại thông minh Fan Edition tiếp theo của Samsung vừa được phát hiện trong một bài báo của AndroidHeadlines. Nguồn tin tiết lộ Galaxy S25 FE có 4 tùy chọn màu sắc từ mọi góc nhìn, đó là đen, xanh băng, trắng và đen.Ở mặt trước, Galaxy S25 FE có viền màn hình mỏng (mặc dù không đồng đều) cùng với một lỗ đục nằm chính giữa để chứa camera selfie. Những tin đồn ban đầu cho thấy Galaxy S25 FE sẽ mỏng hơn so với phiên bản tiền nhiệm, và điều này cũng có thể thấy trong hình ảnh rò rỉ mới nhất. Chiếc điện thoại Fan Edition mới của Samsung dự kiến sẽ chỉ dày 7.4mm.",
            "15/08/2025 14:51",
            1, 
            "Hải Nam",
            "baiviet1.png"));
            
        allNewsList.add(new BaiViet(2, 
            "So sánh Sony Xperia 1 VII vs Samsung Galaxy S25 Ultra: Cuộc đối đầu không khoan nhượng của hai ông lớn!",
            "Trong bài viết dưới đây sẽ so sánh Sony Xperia 1 VII vs Samsung S25 Ultra để xem sự khác biệt giữa hai điện thoại này là gì và người dùng nên mua điện thoại nào hơn.\n" +
                    "\n" +
                    "Ở thời điểm hiện tại, Xperia 1 VII và Samsung S25 Ultra là hai trong số những điện thoại cao cấp, chất lượng đáng mua nhất. Cả hai đều sở hữu thiết kế cực kỳ cao cấp, cấu hình phần cứng mạnh mẽ và có nhiều tính năng thú vị để người dùng có trải nghiệm được nâng lên tầm cao mới. Vậy khi đặt lên bàn cân so sánh thì đâu mới là lựa chọn tốt hơn cho người dùng? Hãy cùng Sforum.vn tìm hiểu qua bài viết dưới đây.Tổng quan về Sony Xperia 1 VII và Samsung Galaxy S25 Ultra\n" +
                    "Galaxy S25 Ultra là phiên bản kế nhiệm trực tiếp của Galaxy S24 Ultra, chính thức ra mắt vào ngày 23/1/2025. Đây là flagship tốt nhất của Samsung ở thời điểm hiện tại, gây ấn tượng với thiết kế cao cấp, màn hình đẹp cho chất lượng hiển thị tuyệt vời, hiệu năng mạnh mẽ, camera chất lượng và thời lượng pin dài. Mặt khác, Xperia 1 VII vừa chính thức ra mắt vào đầu tháng 5/2025 với thiết kế không thay đổi nhiều so với thế hệ tiền nhiệm, nhưng đi kèm những nâng cấp ấn tượng về cấu hình phần cứng. Máy dùng chip Snapdragon 8 Elite mạnh mẽ có màn hình OLED Bravia cao cấp cho chất lượng hiển thị tuyệt vời và hệ thống camera chất lượng cao nên có thể cạnh tranh sòng phẳng với bất kỳ đối thủ nào ở phân khúc smartphone siêu cao cấp.\n" +
                    "\n" +
                    "So sánh Sony Xperia 1 VII vs Samsung S25 Ultra chi tiết\n" +
                    "Dưới đây, chúng ta sẽ so sánh hai điện thoại này ở nhiều khía cạnh như thiết kế, màn hình, hiệu năng, camera và thời lượng pin.\n" +
                    "\n" +
                    "Về thiết kế: Mỗi máy một vẻ\n" +
                    "Đầu tiên, chúng ta sẽ so sánh Sony Xperia 1 VII và Galaxy S25 Ultra về khía cạnh thiết kế. Nhìn chung, hai điện thoại này có ngôn ngữ thiết kế hoàn toàn khác biệt nhau và đều toác lên vẻ cao cấp, sang trọng nên việc thấy điện thoại nào đẹp hơn sẽ phụ thuộc vào sở thích, \"gu\" thẩm mỹ của mỗi người.",
            "15/08/2025 10:00", 
            1,
            "Hải Nam", 
            "baiviet2.png"));
            
        allNewsList.add(new BaiViet(3, 
            "So sánh Sony Xperia 1 VII vs iPhone 16 Pro Max: Cuộc chiến đỉnh cao giữa hai “ông hoàng” smartphone",
            "Bài so sánh Sony Xperia 1 VII vs iPhone 16 Pro Max dưới đây sẽ giúp bạn hiểu rõ sự khác biệt giữa hai điện thoại này để từ đó có được quyết định mua sắm phù hợp.\n" +
                    "\n" +
                    "Trong phân khúc smartphone siêu cao cấp ở thời điểm hiện tại, Sony Xperia 1 VII và iPhone 16 Pro Max là hai trong số những cái tên đáng chú ý nhất. Cả hai đều sở hữu thiết kế cao cấp, cấu hình phần cứng cực kỳ mạnh mẽ và có nhiều tính năng phần mềm hữu ích. Vậy khi đặt lên bàn cân so sánh thì đâu mới là lựa chọn tốt hơn cho người dùng? Tổng quan về Sony Xperia 1 VII và iPhone 16 Pro Max\n" +
                    "iPhone 16 Pro Max là mẫu iPhone chất lượng nhất của Apple ở thời điểm hiện tại, ra mắt vào tháng 9/2024. Điện thoại này gây ấn tượng với thiết kế cao cấp khi sở hữu khung viền titan cứng cáp, màn hình đục lỗ hiện đại cho chất lượng hiển thị tuyệt vời. Bên trong, máy dùng chip A18 Pro 3nm mạnh mẽ, có hệ thống 3 camera 48MP (chính) + 48MP (siêu rộng) + 12MP (tiềm vọng 5x) chất lượng và thời lượng pin đủ dùng trong cả ngày dài. Trong khi đó, Xperia 1 VII vừa chính thức ra mắt vào đầu tháng 5/2025 với thiết kế không thay đổi nhiều so với thế hệ tiền nhiệm, nhưng đi kèm những nâng cấp ấn tượng về cấu hình phần cứng. Máy dùng chip Snapdragon 8 Elite mạnh mẽ có màn hình OLED Bravia cao cấp cho chất lượng hiển thị tuyệt vời và hệ thống camera chất lượng cao nên có thể cạnh tranh sòng phẳng với bất kỳ đối thủ nào ở phân khúc smartphone siêu cao cấp.",
            "15/08/2025 07:05", 
            1,
            "Hải Nam", 
            "baiviet3.png"));
            
        allNewsList.add(new BaiViet(4, 
            "Redmi Note 15 Pro sẽ thách thức mọi giới hạn về độ bền và khả năng chống nước",
            "Rò rỉ mới nhất tiết lộ thiết kế loạt smartphone Redmi Note 15 Pro sẽ có độ bền và khả năng kháng nước ấn tượng.\n" +
                    "\n" +
                    "Trong vài tuần qua, chúng ta đã liên tục được nhìn thấy thông tin rò rỉ về các điện thoại Redmi Note 15 Series xuất hiện trên các trang mạng. Hôm nay, Xiaomi đã \"nhá hàng\" về độ bền cũng như khả năng kháng nước của Redmi Note 15 Pro. Hiện tại, Xiaomi vẫn chưa tiết lộ những thay đổi hoặc cải tiến nào mà hãng dự định mang đến cho dòng Redmi Note 15. Tuy nhiên, công ty cho biết loạt smartphone này sẽ mang đến chuẩn mực mới về độ bền và khả năng chống nước. Do đó, sẽ không ngạc nhiên khi một trong các điện thoại Redmi Note 15 Pro sẽ đạt chứng nhận IP69K, giúp người dùng yên tâm hơn trong quá trình sử dụng.\n" +
                    "\n" +
                    "Redmi Note 15 Pro Series lộ teaser chính thức, thiết kế được hé lộ (cập nhật ngày 13/8/2025)\n" +
                    "Như có thể thấy ở trên, thiết kế của dòng Redmi Note 15 Pro có vẻ tương tự như dòng Note 14 Pro. Do đó, có khả năng các điện thoại này sẽ có mặt lưng kính cao cấp ở mặt sau và gây ấn tượng với màn hình đục lỗ hiện đại ở mặt trước cũng như cụm camera hình vuông khá lớn ở mặt sau. Các báo cáo gần đây tiết lộ rằng, Redmi Note 15 Pro+ sẽ có màn hình OLED cong bốn cạnh, hỗ trợ độ phân giải 1.5K. Cụm ba camera sau bao gồm cảm biến chính 50 megapixel hỗ trợ chống rung quang học (OIS) và camera tele 50 megapixel.\n" +
                    "\n" +
                    "Redmi Note 15 Pro+ được đồn đoán sẽ trang bị chip Snapdragon 7s Gen 3 và pin dung lượng lớn 7,000mAh, hỗ trợ sạc 90W. Máy cũng có hệ thống loa kép và kết nối vệ tinh.",
            "14/08/2025 09:30",
            2,
            "Minh Tuấn", 
            "baiviet4.png"));
            
        allNewsList.add(new BaiViet(5, 
            "Đã iOS 26 và iPadOS 26 public beta 3, bạn đã cập nhật chưa?",
            "Bản thử nghiệm mới nhất của hệ điều hành iOS 26 và iPadOS 26 mang đến một số thay đổi, tinh chỉnh về giao diện người dùng cũng như các tính năng cho iPhone.\n" +
                    "\n" +
                    "Apple hôm nay đã phát hành bản public beta 3 của iOS 26 và iPadOS 26, qua đó cho phép những người đăng ký tham gia chương trình public beta của hãng được thử nghiệm trước các tính năng mới. Hiện tại, những người thử nghiệm bản beta đã đăng ký trên trang web của Apple có thể tải xuống bản thử nghiệm mới nhất của iOS 26\u200C và \u200CiPadOS 26\u200C bằng cách mở ứng dụng Cài đặt, đi tới phần Cài đặt chung, nhấn vào Cập nhật phần mềm và chọn tùy chọn \u200CiOS 26\u200C hoặc \u200CiPadOS 26\u200C Public Beta.\n" +
                    "\n" +
                    "Bản public beta thứ ba của iOS/iPadOS 26 được phát hành chỉ sau vài ngày kể từ khi công ty tung ra bản beta thứ 6 dành cho các nhà phát triển. Apple dự kiến sẽ phát hành phiên bản chính thức của iOS 26 và iPadOS 26 vào tháng 9. Bản public beta thứ 3 của iOS 26 dự kiến sẽ tương tự hoặc giống với những gì chúng ta thấy trong bản beta dành cho nhà phát triển thứ 6 của iOS 26. Nó đi kèm với bản dựng iOS 26 beta dành cho nhà phát triển thứ 6 đã cập nhật: 23A5318f.\n" +
                    "\n" +
                    "Được biết, \u200CiOS 26\u200C và \u200CiPadOS 26\u200C sở hữu thiết kế Liquid Glass của Apple, với tính thẩm mỹ thị giác tập trung vào độ trong suốt. Các biểu tượng, nút menu, thanh điều hướng,...phản chiếu và khúc xạ ánh sáng với hiệu ứng động tinh tế. Có các menu pop-up ở một số khu vực, thanh tab thu nhỏ lại và mọi thứ đều có giao diện tròn trịa hơn.\n" +
                    "\n" +
                    "Hệ điều hành này còn mang đến các tính năng mới như Visual Intelligence cho ảnh chụp màn hình, theo dõi đơn hàng được cập nhật trong ứng dụng Wallet, các tính năng mới trong Reminders và Live Translation cho các ứng dụng Tin nhắn, Điện thoại và FaceTime,...",
            "15/08/2025 11:15",
            3,
            "Lan Anh", 
            "baiviet5.png"));


        // Sao chép dữ liệu vào danh sách hiển thị
        newsList.addAll(allNewsList);
        newsAdapter.notifyDataSetChanged();
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterNews(s.toString());
            }
        });
    }

    private void filterNews(String query) {
        newsList.clear();
        if (query.isEmpty()) {
            newsList.addAll(allNewsList);
        } else {
            for (BaiViet baiViet : allNewsList) {
                if (baiViet.getTieuDe().toLowerCase().contains(query.toLowerCase()) ||
                    baiViet.getTenTacGia().toLowerCase().contains(query.toLowerCase())) {
                    newsList.add(baiViet);
                }
            }
        }
        newsAdapter.notifyDataSetChanged();
    }

    private void setupClickListeners() {
        // Có thể thêm các click listener khác ở đây
    }
}
