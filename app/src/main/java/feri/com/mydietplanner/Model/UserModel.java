package feri.com.mydietplanner.Model;

public class UserModel {
    private String nama,email,bio, alamat,telp,img_url;
    private int berat, tinggi, umur;
    private double bmi;

    public UserModel() {
    }

    public UserModel(String nama, String email, String bio, String alamat, String telp, String img_url, int berat, int tinggi, int umur, double bmi) {
        this.nama = nama;
        this.email = email;
        this.bio = bio;
        this.alamat = alamat;
        this.telp = telp;
        this.img_url = img_url;
        this.berat = berat;
        this.tinggi = tinggi;
        this.umur = umur;
        this.bmi = bmi;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
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
