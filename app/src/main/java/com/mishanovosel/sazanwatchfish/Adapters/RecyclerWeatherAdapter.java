package com.mishanovosel.sazanwatchfish.Adapters;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mishanovosel.sazanwatchfish.InterfaceMap.MapConstant;
import com.mishanovosel.sazanwatchfish.R;
import com.mishanovosel.sazanwatchfish.RecyclerModel;
import com.mishanovosel.sazanwatchfish.databinding.RecyclerContentBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RecyclerWeatherAdapter  extends RecyclerView.Adapter<RecyclerWeatherAdapter.MyViewHolder> {

    //private List<RecyclerModel> postList;
    private List<RecyclerModel> weathertList;
    private LayoutInflater layoutInflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {

       // private final PostRowItemBinding binding;
        private final RecyclerContentBinding binding;

        public MyViewHolder(final RecyclerContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RecyclerWeatherAdapter(List<RecyclerModel> weathertList) {
        this.weathertList = weathertList;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RecyclerContentBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.recycler_content, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setRecyclerModel(weathertList.get(position));
    }

    @Override
    public int getItemCount() {
        return weathertList.size();
    }


    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView imageView, String v) {

        Log.e(TAG, "v");//TO DO Check internet available
        Picasso.get().load(MapConstant.HTTP_OPENWEATHERMAP + v).resize(100, 100)
                .centerCrop().into(imageView);

    }



}
