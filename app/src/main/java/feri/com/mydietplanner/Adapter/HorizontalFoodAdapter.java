package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import feri.com.mydietplanner.Activity.MainActivity;
import feri.com.mydietplanner.Fragment.detail_foodFragment;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.Model.VerticalFoodModel;
import feri.com.mydietplanner.R;


public class HorizontalFoodAdapter extends RecyclerView.Adapter<HorizontalFoodAdapter.HorizontalViewHolder> {
    Context context;
    ArrayList<HorizontalFoodModel> listFood;

    public HorizontalFoodAdapter(Context context, ArrayList<HorizontalFoodModel> listFood){
        this.context = context;
        this.listFood = listFood;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_horizontal, viewGroup, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        String namaMakanan = this.listFood.get(position).getNama();
        String img_url = this.listFood.get(position).getImg_url();
        int kalori = this.listFood.get(position).getKalori();
        final String foodkey = this.listFood.get(position).getFoodKey();
        final HorizontalViewHolder cvh = holder;
        Log.d("namaMakanan", this.listFood.get(position).getFoodKey());
        holder.txtMakanan.setText(namaMakanan);
        holder.txtKalori.setText(String.valueOf(kalori)+" Kal");
        Glide.with(context).load(img_url).into(holder.imgMakanan);
        holder.cv_hfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("foodkey",foodkey);
                Fragment fragment=new detail_foodFragment();
                fragment.setArguments(bundle);
                ((MainActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public void addItem(ArrayList<HorizontalFoodModel> mData) {
        this.listFood = mData;
        notifyDataSetChanged();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView txtMakanan, txtKalori;
        ImageView imgMakanan;
        CardView cv_hfood;

        public HorizontalViewHolder(View itemView){
            super(itemView);

            txtMakanan = itemView.findViewById(R.id.nama_makanan);
            txtKalori = itemView.findViewById(R.id.kalori_makanan);
            imgMakanan = itemView.findViewById(R.id.food_image);
            cv_hfood=itemView.findViewById(R.id.cv_hfood);
        }
    }
}
