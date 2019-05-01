package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.Model.VerticalFoodModel;
import feri.com.mydietplanner.R;

public class VerticalFoodAdapter extends RecyclerView.Adapter<VerticalFoodAdapter.VerticalViewHolder> {
    Context context;
    ArrayList<VerticalFoodModel> list;

    private LayoutInflater layoutInflater;

    public VerticalFoodAdapter (Context context, ArrayList<VerticalFoodModel> list){
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_food_vertical, viewGroup, false);
        VerticalViewHolder vh = new VerticalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder verticalViewHolder, int i) {
        final VerticalFoodModel verticalFoodModel = list.get(i);
        String title = verticalFoodModel.getTitle();
        ArrayList<HorizontalFoodModel> singleItem = verticalFoodModel.getArrayList();

        verticalViewHolder.txtTitle.setText(title);
        HorizontalFoodAdapter horizontalFoodAdapter = new HorizontalFoodAdapter(context, singleItem);

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        verticalViewHolder.recyclerView.setAdapter(horizontalFoodAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView txtTitle;
        Button btnMore;

        public VerticalViewHolder(View itemView){
            super(itemView);

            recyclerView = itemView.findViewById(R.id.rv_horizontal);
            txtTitle = itemView.findViewById(R.id.title_vertical);
            btnMore = itemView.findViewById(R.id.btnMore);
        }

    }
}
