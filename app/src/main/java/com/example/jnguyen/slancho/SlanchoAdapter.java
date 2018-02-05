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

    @Override
    public SlanchoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,parent,shouldAttachToParentImmediately);
        return new SlanchoAdapterViewHolder(view);
    }

    class SlanchoAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView ic_weather;
        TextView tv_Date;
        TextView tv_Description;
        TextView tv_MaxTemp;
        TextView tv_MinTemp;

        public SlanchoAdapterViewHolder(View view){
            super(view);
            ic_weather = (ImageView) view.findViewById(R.id.ic_list_item) ;
            tv_Date = view.findViewById(R.id.tv_date);
            tv_Description = view.findViewById(R.id.tv_description);
            tv_MaxTemp = view.findViewById(R.id.tv_max_temp);
            tv_MinTemp = view.findViewById(R.id.tv_min_temp);

        }
    }
}
