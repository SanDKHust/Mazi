package vn.edu.hust.soict.khacsan.myapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.R;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Item;
import vn.edu.hust.soict.khacsan.myapp.view.adapter.ItemAdapter;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MaterialSearchView mSearchView;
    private ItemAdapter mAdapter;
    private List<Item> mListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);

        mListItem = new ArrayList<>();

        mListItem.add(Item.newInstanceTitle("Khóa học nhập môn"));
        mListItem.add(Item.newInstanceContent("Bảng chữ cái"));
        mListItem.add(Item.newInstanceContent("50 bài Minna"));
        mListItem.add(Item.newInstanceContent("Ngữ pháp và kanji cơ bản"));

        mListItem.add(Item.newInstanceTitle("Khóa học kanji"));
        mListItem.add(Item.newInstanceContent("512 từ kanji cơ bản"));
        mListItem.add(Item.newInstanceContent("1945 từ kanja nâng cao"));

        mListItem.add(Item.newInstanceTitle("Luyện thi JLPT"));
        mListItem.add(Item.newInstanceContent("Luyện thi JLPT N5->N1"));
        mListItem.add(Item.newInstanceContent("Thi thử JLPT"));

        mListItem.add(Item.newInstanceTitle("Video bài giảng"));
        mListItem.add(Item.newInstanceContent("Học tiếng nhập qua video bài giảng"));

        mListItem.add(Item.newInstanceTitle("Khóa học giao tiếp"));
        mListItem.add(Item.newInstanceContent("1000 mẫu câu giao tiếp"));

        mListItem.add(Item.newInstanceTitle("Ứng dụng hay"));
        mListItem.add(Item.newInstanceContent("Ghi nhớ từ vựng hiệu quả"));

        mListItem.add(Item.newInstanceTitle("Cài đặt và nâng cấp"));
        mListItem.add(Item.newInstanceContent("Cài đặt"));
        mListItem.add(Item.newInstanceContent("Xóa quảng cáo"));
        mListItem.add(Item.newInstanceContent("Khôi phục vật phẩm đã mua"));

        mAdapter = new ItemAdapter(mListItem);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_main);
        mRecyclerView = findViewById(R.id.recyclerView_main);
        mSearchView = findViewById(R.id.search_view_main);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search_main);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(position == 1) startActivity(new Intent(this,ActivityAlphabetTable.class));
    }
}
