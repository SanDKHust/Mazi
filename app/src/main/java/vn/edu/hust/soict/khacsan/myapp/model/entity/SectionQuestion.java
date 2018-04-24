package vn.edu.hust.soict.khacsan.myapp.model.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

public class SectionQuestion extends SectionEntity<Question> {
    public SectionQuestion(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionQuestion(Question question) {
        super(question);
    }
}
