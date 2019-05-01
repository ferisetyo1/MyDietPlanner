package feri.com.mydietplanner.Model;

import java.util.ArrayList;

public class VerticalFoodModel {
    String title;
    ArrayList <HorizontalFoodModel> listFood;

    public VerticalFoodModel() {
    }

    public VerticalFoodModel(String title, ArrayList<HorizontalFoodModel> listFood) {
        this.title = title;
        this.listFood = listFood;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<HorizontalFoodModel> getArrayList() {
        return listFood;
    }

    public void setArrayList(ArrayList<HorizontalFoodModel> arrayList) {
        this.listFood = arrayList;
    }
}
