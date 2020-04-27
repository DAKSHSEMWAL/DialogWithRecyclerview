package com.daksh.dialogwithrecyclerview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<PropertiesData> modelList;

    private OnItemClickListener mItemClickListener;


    public PropertiesAdapter(Context context, ArrayList<PropertiesData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<PropertiesData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_properties, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final PropertiesData model = getItem(position);
            final ViewHolder vh = (ViewHolder) holder;

            vh.propertiesaddress.setText(model.getLocation());
            Glide.with(mContext).load(model.getImage()).thumbnail(Glide.with(mContext).load(R.mipmap.loader)).into(vh.propertiesview);


            vh.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(vh.itemView, position, model));


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Contract(pure = true)
    private PropertiesData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, PropertiesData model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView propertiesview;
        private TextView propertiesaddress;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.propertiesview = itemView.findViewById(R.id.propertiesview);
            this.propertiesaddress = itemView.findViewById(R.id.propertiesaddress);

        }
    }

}

