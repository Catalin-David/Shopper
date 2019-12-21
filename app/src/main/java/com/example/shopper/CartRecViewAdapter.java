package com.example.shopper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopper.Models.GroceryItem;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder>{
    private static final String TAG = "CartRecViewAdapter";

    public interface GetTotalPrice{
        void onGettingTotalPriceResult(double price);
    }
    public interface DeleteCartItem{
        void onDeletingResult(GroceryItem item);
    }

    private DeleteCartItem deleteCartItem;

    private GetTotalPrice getTotalPrice;
    private Fragment fragment;

    public CartRecViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private ArrayList<GroceryItem> items = new ArrayList<>();

    public CartRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_rec_view_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: started");

        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()));

        deleteCartItem = (DeleteCartItem) fragment;

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("Delete item from cart?")
                        .setMessage("Are you sure you want to delete " + items.get(position).getName() + "?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCartItem.onDeletingResult(items.get(position));
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName, txtPrice, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void calculatePrice(){
        Log.d(TAG, "calculatePrice: started");
        try {
            getTotalPrice = (GetTotalPrice) fragment;
        }catch (ClassCastException e){
            e.printStackTrace();
        }

        double total = 0;
        for(GroceryItem item:items){
            total = total + item.getPrice();
        }

        getTotalPrice.onGettingTotalPriceResult(total);
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        calculatePrice();
        notifyDataSetChanged();
    }
}
