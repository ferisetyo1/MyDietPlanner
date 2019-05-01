package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import feri.com.mydietplanner.Activity.CekBMIandTipsActivity;
import feri.com.mydietplanner.R;

public class HomeFragment extends Fragment {
    View v;

    Button cekbmi, foodlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_home, container, false);

        cekbmi=v.findViewById(R.id.cekbmi);
        cekbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cektipsandbmi();
            }
        });


        return v;
    }

    public void cektipsandbmi() {
        startActivity(new Intent(getContext(), CekBMIandTipsActivity.class));
    }

}
