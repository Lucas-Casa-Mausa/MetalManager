package com.example.metalmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServiceOrderAdapter extends RecyclerView.Adapter<ServiceOrderAdapter.ServiceOrderViewHolder> {

    private List<ServiceOrder> orderList;
    private OnServiceOrderClickListener listener;

    public interface OnServiceOrderClickListener {
        void onOrderClick(ServiceOrder order);
        void onDeleteClick(ServiceOrder order);
    }

    public ServiceOrderAdapter(List<ServiceOrder> orderList, OnServiceOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServiceOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_order, parent, false);
        return new ServiceOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOrderViewHolder holder, int position) {
        ServiceOrder order = orderList.get(position);

        holder.tvOrderStatus.setText(order.getStatus());
        holder.tvOrderDescription.setText(order.getDescricaoServico());

        // Click no card para editar
        holder.cardOrder.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order);
            }
        });

        // Click no botão excluir
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<ServiceOrder> newList) {
        this.orderList = newList;
        notifyDataSetChanged();
    }

    static class ServiceOrderViewHolder extends RecyclerView.ViewHolder {
        CardView cardOrder;
        TextView tvOrderStatus;
        TextView tvOrderDescription;
        ImageButton btnDelete;

        public ServiceOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardOrder = itemView.findViewById(R.id.cardOrder);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDescription = itemView.findViewById(R.id.tvOrderDescription);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}