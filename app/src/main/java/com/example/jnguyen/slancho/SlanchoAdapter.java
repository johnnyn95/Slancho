package com.example.jnguyen.slancho;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JNguyen on 5.2.2018.
 */

public class SlanchoAdapter extends RecyclerView.Adapter<SlanchoAdapter.SlanchoAdapterViewHolder>{

    String[] mWeatherData;
    public SlanchoAdapter(){

    }

    @Override
    public SlanchoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        //int layoutForListItem = R.layout.forecast_list_item;
        int layoutForListItem = R.layout.forecast_list_item_test;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,parent,shouldAttachToParentImmediately);
        return new SlanchoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlanchoAdapterViewHolder holder, int position) {
        String weatherInfo = mWeatherData[position];
        holder.tv_weather_data.setText(weatherInfo);
    }

    @Override
    public int getItemCount() {
        if(mWeatherData.length == 0){
            return 0;
        }else {
            return  mWeatherData.length;
        }

    }

    public void setWeatherData(String[] data){
        mWeatherData = data;
        notifyDataSetChanged();
    }

    public class SlanchoAdapterViewHolder extends RecyclerView.ViewHolder{

        //public final ImageView ic_weather;
        //public final TextView tv_Date;
        //public final TextView tv_Description;
        //public final TextView tv_MaxTemp;
        //public final TextView tv_MinTemp;
        public final TextView tv_weather_data;
        public SlanchoAdapterViewHolder(View view){
            super(view);
            //ic_weather = (ImageView) view.findViewById(R.id.ic_list_item) ;
            //tv_Date = view.findViewById(R.id.tv_date);
            //tv_Description = view.findViewById(R.id.tv_description);
            //tv_MaxTemp = view.findViewById(R.id.tv_max_temp);
            //tv_MinTemp = view.findViewById(R.id.tv_min_temp);
            tv_weather_data = view.findViewById(R.id.recyclerview_forecast);
        }
    }
}
