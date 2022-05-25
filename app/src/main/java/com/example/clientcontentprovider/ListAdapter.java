package com.example.clientcontentprovider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListItem> users;
    private LayoutInflater inflater;
    private Context context;

    public ListAdapter(List<ListItem> users, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.users = users;
    }

    @Override
    public int getItemCount(){return users.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(users.get(position));
        holder.lyItem.setOnClickListener(view -> {
            ListItem item = getItemAtPosition(position);
            Intent intent = new Intent(context, UpdateUser.class);
            intent.putExtra("uid", String.valueOf(item.getUid()));
            intent.putExtra("firstName", item.getFirstName());
            intent.putExtra("lastName", item.getLastName());
            context.startActivity(intent);
        });
    }

    public ListItem getItemAtPosition(int position){
        return users.get(position);
    }

    public void setItems(List<ListItem> items){users= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        ImageView iconImage;
        TextView lblFirstName, lblLastName;
        LinearLayout lyItem;

        ViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            iconImage = itemView.findViewById(R.id.iconImageView);
            lblFirstName = itemView.findViewById(R.id.lblFirstName);
            lblLastName = itemView.findViewById(R.id.lblLastName);
            lyItem = itemView.findViewById(R.id.lyItem);
        }

        void bindData(final ListItem item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            lblFirstName.setText(item.getFirstName());
            lblLastName.setText(item.getLastName());
        }
    }
}
