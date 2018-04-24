package vn.edu.hust.soict.khacsan.myapp.controller;

import android.content.Context;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.myapp.model.AlphabetTableModel;
import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemListKana;

public class AlphabetController {
    private AlphabetTableModel model;

    public AlphabetController(Context context) {
        this.model = new AlphabetTableModel(context);
    }

    public ArrayList<ItemListKana> getAlphabetKanas(){
        return model.getAlphabetKana();
    }

}
