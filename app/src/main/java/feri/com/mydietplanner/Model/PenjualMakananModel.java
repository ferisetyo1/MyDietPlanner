package feri.com.mydietplanner.Model;

public class PenjualMakananModel {
    private String nama;
    private double Lat,Long;
    private long harga;

    public PenjualMakananModel() {
    }

    public PenjualMakananModel(String nama, double lat, double aLong, long harga) {
        this.nama = nama;
        Lat = lat;
        Long = aLong;
        this.harga = harga;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }
}
