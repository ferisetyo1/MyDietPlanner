package feri.com.mydietplanner.Model;

public class UserModel {
    private String nama,email;
    private int berat, tinggi, umur;

    public UserModel() {
    }

    public UserModel(String nama, String email, int berat, int tinggi, int umur) {
        this.nama = nama;
        this.email = email;
        this.berat = berat;
        this.tinggi = tinggi;
        this.umur = umur;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getTinggi() {
        return tinggi;
    }

    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }
}
