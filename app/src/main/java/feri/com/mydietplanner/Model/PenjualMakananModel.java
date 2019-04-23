package feri.com.mydietplanner.Model;

public class PenjualMakananModel {
    private String nama;
    private int Lat,Long;
    private long harga;

    public PenjualMakananModel() {
    }

    public PenjualMakananModel(String nama, int lat, int aLong, long harga) {
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

    public int getLat() {
        return Lat;
    }

    public void setLat(int lat) {
        Lat = lat;
    }

    public int getLong() {
        return Long;
    }

    public void setLong(int aLong) {
        Long = aLong;
    }
}
