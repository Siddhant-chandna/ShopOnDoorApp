package com.example.shopondoor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.activities.DetailActivity;
import com.example.shopondoor.models.ViewAllModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> viewAllModelList;
    FirebaseFirestore db;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder,int position) {
        db=FirebaseFirestore.getInstance();

        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.price.setText(viewAllModelList.get(position).getPrice());
        db.collection("Discount").document("J3YBD9f1L0nBeI9Fr0s1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    holder.discount.setText(documentSnapshot.getString("discount"));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("detail", viewAllModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,description,price,discount;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.view_all_img);
            name=itemView.findViewById(R.id.view_all_name);
            description=itemView.findViewById(R.id.view_all_des);
            price=itemView.findViewById(R.id.view_all_price);
            discount=itemView.findViewById(R.id.view_all_discount);
        }
    }
}
