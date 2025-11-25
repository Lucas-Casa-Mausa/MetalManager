package com.example.metalmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metamanager.R;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> clientList;
    private OnClientClickListener listener;

    public interface OnClientClickListener {
        void onClientClick(Client client);
        void onDeleteClick(Client client);
    }

    public ClientAdapter(List<Client> clientList, OnClientClickListener listener) {
        this.clientList = clientList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);

        holder.tvClientName.setText(client.getName());
        holder.tvClientCnpj.setText(client.getCnpj());

        // Click no card para editar
        holder.cardClient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClientClick(client);
            }
        });

        // Click no botão excluir
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(client);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        CardView cardClient;
        TextView tvClientName;
        TextView tvClientCnpj;
        ImageButton btnDelete;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            cardClient = itemView.findViewById(R.id.card);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvClientCnpj = itemView.findViewById(R.id.tvClientCnpj);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}