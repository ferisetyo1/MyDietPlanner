package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import feri.com.mydietplanner.Activity.MainActivity;
import feri.com.mydietplanner.Fragment.detail_foodFragment;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.R;

public class AdapterFavorit extends RecyclerView.Adapter<AdapterFavorit.MyViewHolder> {

    private Context context;
    private List<HorizontalFoodModel> HW;

    public AdapterFavorit(Context ctx, List<HorizontalFoodModel> hw) {

        context = ctx;
        HW = hw;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater mInflater = LayoutInflater.from(context);
        v = mInflater.inflate(R.layout.item_favorit,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        HorizontalFoodModel mb = HW.get(position);
        final String foodKey=mb.getFoodKey();
        Glide.with(context).load(mb.getImg_url()).into(holder.iv);
        holder.tv.setText(mb.getNama());
        holder.kal.setText(mb.getKalori()+"");
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("foodkey",foodKey);
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
        return HW.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv,kal;
        ImageView iv;
        CardView cv;
        public MyViewHolder(View itemView) {
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.cv_fav);
            tv = (TextView) itemView.findViewById(R.id.txt_nama_favorit);
            kal = (TextView) itemView.findViewById(R.id.kalori_favorit);
            iv = (ImageView) itemView.findViewById(R.id.img_favorit);

        }
    }
}