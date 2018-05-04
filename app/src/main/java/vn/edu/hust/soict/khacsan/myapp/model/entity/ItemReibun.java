package vn.edu.hust.soict.khacsan.myapp.model.entity;

import vn.edu.hust.soict.khacsan.myapp.model.database.Reibun;

public class ItemReibun {
    private Reibun mReibunType0,mReibunTyppe1;

    public ItemReibun(Reibun mReibunType0, Reibun mReibunTyppe1) {
        this.mReibunType0 = mReibunType0;
        this.mReibunTyppe1 = mReibunTyppe1;
    }

    public Reibun getmReibunType0() {
        return mReibunType0;
    }

    public void setmReibunType0(Reibun mReibunType0) {
        this.mReibunType0 = mReibunType0;
    }

    public Reibun getmReibunTyppe1() {
        return mReibunTyppe1;
    }

    public void setmReibunTyppe1(Reibun mReibunTyppe1) {
        this.mReibunTyppe1 = mReibunTyppe1;
    }
}
