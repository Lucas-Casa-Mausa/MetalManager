package com.example.metalmanager;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceOrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private ServiceOrderAdapter orderAdapter;
    private List<ServiceOrder> orderList;
    private FloatingActionButton fabAdd;
    private TextView tvEmptyList;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initializeViews();
        setupRecyclerView();
        setupListeners();
        loadOrders();
    }

    private void initializeViews() {
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        fabAdd = findViewById(R.id.fabAdd);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Marcar o item "Ordens de serviço" como selecionado
        bottomNavigation.setSelectedItemId(R.id.nav_orders);
    }

    private void setupRecyclerView() {
        orderList = new ArrayList<>();

        orderAdapter = new ServiceOrderAdapter(orderList, new ServiceOrderAdapter.OnServiceOrderClickListener() {
            @Override
            public void onOrderClick(ServiceOrder order) {
                openEditOrder(order);
            }

            @Override
            public void onDeleteClick(ServiceOrder order) {
                showDeleteConfirmation(order);
            }
        });

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrders.setAdapter(orderAdapter);
    }

    private void setupListeners() {
        // Botão adicionar
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Criar tela de cadastro de ordem de serviço
                Toast.makeText(ServiceOrderListActivity.this,
                        "Tela de cadastro em desenvolvimento", Toast.LENGTH_SHORT).show();
            }
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_dashboard) {
                    // TODO: Navegar para Dashboard
                    Toast.makeText(ServiceOrderListActivity.this,
                            "Dashboard em desenvolvimento", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_clients) {
                    Intent intent = new Intent(ServiceOrderListActivity.this, ClientListActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_orders) {
                    return true; // Já estamos nesta tela
                }

                return false;
            }
        });
    }

    private void loadOrders() {
        // TODO: Carregar ordens do banco de dados
        // Por enquanto, vamos adicionar algumas ordens de exemplo
        orderList.clear();
        orderList.add(new ServiceOrder(1, "Pendente", "Manutenção de equipamento industrial com troca de peças"));
        orderList.add(new ServiceOrder(2, "Em Andamento", "Instalação de nova linha de produção"));
        orderList.add(new ServiceOrder(3, "Concluído", "Reparo de solda em estrutura metálica"));
        orderList.add(new ServiceOrder(4, "Pendente", "Inspeção técnica trimestral"));
        orderList.add(new ServiceOrder(5, "Cancelado", "Substituição de motor elétrico - projeto cancelado pelo cliente"));

        // Ordenar por ID decrescente (mais recentes primeiro)
        Collections.sort(orderList, new Comparator<ServiceOrder>() {
            @Override
            public int compare(ServiceOrder o1, ServiceOrder o2) {
                return Integer.compare(o2.getId(), o1.getId());
            }
        });

        updateUI();
    }

    private void updateUI() {
        if (orderList.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
            recyclerViewOrders.setVisibility(View.GONE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
            recyclerViewOrders.setVisibility(View.VISIBLE);
        }
        orderAdapter.notifyDataSetChanged();
    }

    private void openEditOrder(ServiceOrder order) {
        // TODO: Abrir tela de edição
        Toast.makeText(this, "Editar OS #" + order.getId(), Toast.LENGTH_SHORT).show();
    }

    private void showDeleteConfirmation(ServiceOrder order) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Ordem de Serviço")
                .setMessage("Deseja realmente excluir a OS com status '" + order.getStatus() + "'?")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    deleteOrder(order);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void deleteOrder(ServiceOrder order) {
        // TODO: Remover do banco de dados
        orderList.remove(order);
        updateUI();
        Toast.makeText(this, "Ordem de serviço excluída", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarregar lista quando voltar para a tela
        loadOrders();
    }
}