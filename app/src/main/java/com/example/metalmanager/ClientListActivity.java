package com.example.metalmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metalmanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClients;
    private ClientAdapter clientAdapter;
    private List<Client> clientList;
    private List<Client> filteredList;
    private TextInputEditText etSearch;
    private FloatingActionButton fabAdd;
    private TextView tvEmptyList;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        initializeViews();
        setupRecyclerView();
        setupListeners();
        loadClients();
    }

    private void initializeViews() {
        recyclerViewClients = findViewById(R.id.recyclerViewClients);
        etSearch = findViewById(R.id.etSearch);
        fabAdd = findViewById(R.id.fabAdd);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Marcar o item "Clientes" como selecionado
        bottomNavigation.setSelectedItemId(R.id.nav_clients);
    }

    private void setupRecyclerView() {
        clientList = new ArrayList<>();
        filteredList = new ArrayList<>();

        clientAdapter = new ClientAdapter(filteredList, new ClientAdapter.OnClientClickListener() {
            @Override
            public void onClientClick(Client client) {
                openEditClient(client);
            }

            @Override
            public void onDeleteClick(Client client) {
                showDeleteConfirmation(client);
            }
        });

        recyclerViewClients.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewClients.setAdapter(clientAdapter);
    }

    private void setupListeners() {
        // Botão adicionar
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientListActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Busca em tempo real
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterClients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_dashboard) {
                    // TODO: Navegar para Dashboard
                    Toast.makeText(ClientListActivity.this,
                            "Dashboard em desenvolvimento", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_clients) {
                    return true; // Já estamos nesta tela
                } else if (itemId == R.id.nav_orders) {
                    Intent intent = new Intent(ClientListActivity.this, ServiceOrderListActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            }
        });
    }

    private void loadClients() {
        // TODO: Carregar clientes do banco de dados
        // Por enquanto, vamos adicionar alguns clientes de exemplo
        clientList.clear();
        clientList.add(new Client(1, "Empresa ABC Ltda", "12.345.678/0001-90", "(11) 98765-4321"));
        clientList.add(new Client(2, "Metalúrgica XYZ", "98.765.432/0001-10", "(11) 91234-5678"));
        clientList.add(new Client(3, "Indústria 123", "11.222.333/0001-44", "(11) 99999-8888"));

        filteredList.clear();
        filteredList.addAll(clientList);
        updateUI();
    }

    private void filterClients(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(clientList);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Client client : clientList) {
                if (client.getName().toLowerCase().contains(lowerQuery) ||
                        client.getCnpj().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(client);
                }
            }
        }

        updateUI();
    }

    private void updateUI() {
        if (filteredList.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
            recyclerViewClients.setVisibility(View.GONE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
            recyclerViewClients.setVisibility(View.VISIBLE);
        }
        clientAdapter.notifyDataSetChanged();
    }

    private void openEditClient(Client client) {
        // TODO: Abrir tela de edição
        Intent intent = new Intent(ClientListActivity.this, RegisterActivity.class);
        intent.putExtra("CLIENT_ID", client.getId());
        intent.putExtra("CLIENT_NAME", client.getName());
        intent.putExtra("CLIENT_CNPJ", client.getCnpj());
        intent.putExtra("CLIENT_PHONE", client.getPhone());
        startActivity(intent);
    }

    private void showDeleteConfirmation(Client client) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Cliente")
                .setMessage("Deseja realmente excluir " + client.getName() + "?")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    deleteClient(client);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void deleteClient(Client client) {
        // TODO: Remover do banco de dados
        clientList.remove(client);
        filteredList.remove(client);
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarregar lista quando voltar para a tela
        loadClients();
    }
}