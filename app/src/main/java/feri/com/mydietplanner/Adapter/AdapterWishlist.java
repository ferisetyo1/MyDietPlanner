package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.R;

public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.MyViewHolder> {

    private Context context;
    private List<HorizontalFoodModel> HW;

    public AdapterWishlist(Context ctx, List<HorizontalFoodModel> hw) {

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

        holder.tv.setText(mb.getNama());
        holder.kal.setText(mb.getKalori());


    }

    @Override
    public int getItemCount() {
        return HW.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv,kal;
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.txt_nama_favorit);
            kal = (TextView) itemView.findViewById(R.id.kalori_favorit);
            iv = (ImageView) itemView.findViewById(R.id.img_favorit);

        }
    }
}