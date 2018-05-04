package vn.edu.hust.soict.khacsan.myapp.model.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MinaDatabase.class, name = "grammar")
public class Grammar extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private int id;
    @Column(name = "lesson_id")
    private int lesson_id;
    @Column(name = "name")
    private String name;
    @Column(name = "uname")
    private String uname;
    @Column(name = "content")
    private String content;
    @Column(name = "tag")
    private String tag;
    @Column(name = "favorite")
    private int favorite;
    @ColumnIgnore
    private boolean expanded = false;
    public Grammar() {
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
