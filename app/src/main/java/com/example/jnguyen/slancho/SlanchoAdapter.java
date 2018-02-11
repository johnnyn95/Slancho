package com.example.jnguyen.slancho;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JNguyen on 5.2.2018.
 */

public class SlanchoAdapter extends RecyclerView.Adapter<SlanchoAdapter.SlanchoAdapterViewHolder>{


    private String[] mWeatherData;

    private final SlanchoAdapterOnClickHandler mClickHandler;

    public interface SlanchoAdapterOnClickHandler {
        void onClick(String weather);
    }

    public SlanchoAdapter(SlanchoAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class SlanchoAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        //public final ImageView ic_weather;
        //public final TextView tv_Date;
        //public final TextView tv_Description;
        //public final TextView tv_MaxTemp;
        //public final TextView tv_MinTemp;
        public final TextView mWeatherTextView;


        public SlanchoAdapterViewHolder(View view){
            super(view);
            //ic_weather = (ImageView) view.findViewById(R.id.ic_list_item) ;
            //tv_Date = view.findViewById(R.id.tv_date);
            //tv_Description = view.findViewById(R.id.tv_description);
            //tv_MaxTemp = view.findViewById(R.id.tv_max_temp);
            //tv_MinTemp = view.findViewById(R.id.tv_min_temp);
            mWeatherTextView = (TextView) view.findViewById(R.id.rv_weather_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForTheDay = mWeatherData[adapterPosition];
            mClickHandler.onClick(weatherForTheDay);
        }
    }

    @Override
    public SlanchoAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.forecast_list_item_test;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,viewGroup,shouldAttachToParentImmediately);
        return new SlanchoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlanchoAdapterViewHolder SlanchoAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position];
        Log.d("adapter" , weatherForThisDay);
        SlanchoAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (mWeatherData == null) return 0;
        return mWeatherData.length;

    }

    public void setWeatherData(String[] data){
        mWeatherData = data;
        notifyDataSetChanged();
    }

}
