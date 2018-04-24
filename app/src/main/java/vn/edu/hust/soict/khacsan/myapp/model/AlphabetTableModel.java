package vn.edu.hust.soict.khacsan.myapp.model;

import android.content.Context;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.soict.khacsan.myapp.model.entity.ItemListKana;
import vn.edu.hust.soict.khacsan.myapp.model.entity.Kana;

public class AlphabetTableModel {

    public AlphabetTableModel(Context context) {

    }

    public ArrayList<ItemListKana> getAlphabetKana() {
        int id;
        ArrayList<ItemListKana> itemListKanas = new ArrayList<>();
        ArrayList<Kana> ListKanaItem = new ArrayList<>();

        List<Kana> kanas = SQLite.select().from(Kana.class).queryList();
        for (Kana kana : kanas) {
            id = kana.getId();
            ListKanaItem.add(kana);
            if ((id % 5 == 0 && id < 36) || (((id - 1) % 5 == 0) && id > 50 && id < 72) || id == 43) {
                itemListKanas.add(ItemListKana.newInstance(ListKanaItem, ItemListKana.FIVE_ITEM));
                ListKanaItem = new ArrayList<>();
            } else if (id > 71 && ((id + 1) % 5 == 0) || id == 38 || id == 46) {
                itemListKanas.add(ItemListKana.newInstance(ListKanaItem, ItemListKana.THREE_ITEM));
                ListKanaItem = new ArrayList<>();
            }
        }

        return itemListKanas;
    }

//    public ArrayList<ItemListKana> getAlphabetKana() {
//        int id;
//        Kana kana;
//        ArrayList<ItemListKana> itemListKanas = new ArrayList<>();
//        ArrayList<Kana> ListKanaItem = new ArrayList<>();
//        Cursor cursor = minaDatabase.getDataTableKana();
//        if (cursor.moveToFirst()) {
//            do {
//                kana = new Kana(cursor.getInt(cursor.getColumnIndex("id")),
//                        cursor.getString(cursor.getColumnIndex("romaji")),
//                        cursor.getString(cursor.getColumnIndex("hira")),
//                        cursor.getString(cursor.getColumnIndex("kata")),
//                        cursor.getString(cursor.getColumnIndex("groupe")),
//                        cursor.getString(cursor.getColumnIndex("hira_full")),
//                        cursor.getString(cursor.getColumnIndex("kata_full")),
//                        cursor.getString(cursor.getColumnIndex("example")),
//                        cursor.getInt(cursor.getColumnIndex("favorite")));
//                ListKanaItem.add(kana);
//                id = kana.getId();
//                if ((id % 5 == 0 && id < 36) || (((id - 1) % 5 == 0) && id > 50 && id < 72) || id == 43) {
//                    itemListKanas.add(ItemListKana.newInstance(ListKanaItem, ItemListKana.FIVE_ITEM));
//                    ListKanaItem = new ArrayList<>();
//                } else if (id > 71 && ((id + 1) % 5 == 0) || id == 38 || id == 46) {
//                    itemListKanas.add(ItemListKana.newInstance(ListKanaItem, ItemListKana.THREE_ITEM));
//                    ListKanaItem = new ArrayList<>();
//                }
//            } while (cursor.moveToNext());
//        }
//        return itemListKanas;
//    }

//    public ArrayList<Kana> getListKana() {
//
//        ArrayList<Kana> kanas = new ArrayList<>();
//
//        Cursor cursor = minaDatabase.getDataTableKana();
//        if (cursor.moveToFirst()) {
//            do {
//                kanas.add(new Kana(cursor.getInt(cursor.getColumnIndex("id")),
//                        cursor.getString(cursor.getColumnIndex("romaji")),
//                        cursor.getString(cursor.getColumnIndex("hira")),
//                        cursor.getString(cursor.getColumnIndex("kata")),
//                        cursor.getString(cursor.getColumnIndex("groupe")),
//                        cursor.getString(cursor.getColumnIndex("hira_full")),
//                        cursor.getString(cursor.getColumnIndex("kata_full")),
//                        cursor.getString(cursor.getColumnIndex("example")),
//                        cursor.getInt(cursor.getColumnIndex("favorite"))));
//            } while (cursor.moveToNext());
//        }
//        return kanas;
//    }
}
