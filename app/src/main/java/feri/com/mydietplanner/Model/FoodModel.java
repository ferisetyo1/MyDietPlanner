package feri.com.mydietplanner.Model;

import android.util.Log;

import java.util.ArrayList;

public class FoodModel {
    private String nama,deskripsi,img_url,kategori;
    private double kalori, karbohidrat, protein, lemak ;
    private ArrayList<PenjualMakananModel> penjualMakananModels;

    public FoodModel() {
    }

    public FoodModel(String nama, String deskripsi, String img_url, String kategori, double kalori, ArrayList<PenjualMakananModel> penjualMakananModels) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.img_url = img_url;
        this.kategori = kategori;
        this.kalori = kalori;
        this.penjualMakananModels = penjualMakananModels;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<PenjualMakananModel> getPenjualMakananModels() {
        return penjualMakananModels;
    }

    public double getKalori() {
        return kalori;
    }

    public void setKalori(double kalori) {
        this.kalori = kalori;
    }

    public double getKarbohidrat() {
        return karbohidrat;
    }

    public void setKarbohidrat(double karbohidrat) {
        this.karbohidrat = karbohidrat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getLemak() {
        return lemak;
    }

    public void setLemak(double lemak) {
        this.lemak = lemak;
    }

    public void setPenjualMakananModels(ArrayList<PenjualMakananModel> penjualMakananModels) {
        this.penjualMakananModels = penjualMakananModels;
    }
}
