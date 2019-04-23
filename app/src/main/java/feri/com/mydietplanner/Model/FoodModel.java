package feri.com.mydietplanner.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class FoodModel {
    private String nama,FoodKey,deskripsi,img_url;
    private int kalori;

    public FoodModel() {
    }

    public FoodModel(String nama, String deskripsi, String img_url, int kalori) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.img_url = img_url;
        this.kalori = kalori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoodKey() {
        return FoodKey;
    }

    public void setFoodKey(String foodKey) {
        FoodKey = foodKey;
    }
}
