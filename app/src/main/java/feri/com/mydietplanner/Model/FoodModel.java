package feri.com.mydietplanner.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class FoodModel {
    private String nama,FoodKey;
    private Long harga;

    public FoodModel() {
    }

    public FoodModel(String nama, Long harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getFoodKey() {
        return FoodKey;
    }

    public void setFoodKey(String foodKey) {
        FoodKey = foodKey;
    }
}
