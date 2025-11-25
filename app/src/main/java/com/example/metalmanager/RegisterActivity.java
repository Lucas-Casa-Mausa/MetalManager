package com.example.metalmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metamanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextInputEditText etName, etCnpj, etPhone;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setupListeners();
        setupMasks();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        etName = findViewById(R.id.etName);
        etCnpj = findViewById(R.id.etCnpj);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSave();
            }
        });
    }

    private void setupMasks() {
        // Máscara para CNPJ: XX.XXX.XXX/XXXX-XX
        etCnpj.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String mask = "##.###.###/####-##";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    return;
                }

                String unmasked = s.toString().replaceAll("[^\\d]", "");
                String masked = applyMask(unmasked, mask);

                isUpdating = true;
                etCnpj.setText(masked);
                etCnpj.setSelection(masked.length());
                isUpdating = false;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Máscara para Telefone: (XX) XXXXX-XXXX
        etPhone.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    return;
                }

                String unmasked = s.toString().replaceAll("[^\\d]", "");
                String masked = applyPhoneMask(unmasked);

                isUpdating = true;
                etPhone.setText(masked);
                etPhone.setSelection(masked.length());
                isUpdating = false;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private String applyMask(String text, String mask) {
        StringBuilder masked = new StringBuilder();
        int textIndex = 0;

        for (int i = 0; i < mask.length() && textIndex < text.length(); i++) {
            if (mask.charAt(i) == '#') {
                masked.append(text.charAt(textIndex));
                textIndex++;
            } else {
                masked.append(mask.charAt(i));
            }
        }

        return masked.toString();
    }

    private String applyPhoneMask(String phone) {
        if (phone.length() <= 2) {
            return phone;
        } else if (phone.length() <= 7) {
            return "(" + phone.substring(0, 2) + ") " + phone.substring(2);
        } else if (phone.length() <= 11) {
            return "(" + phone.substring(0, 2) + ") " +
                    phone.substring(2, phone.length() - 4) + "-" +
                    phone.substring(phone.length() - 4);
        } else {
            return "(" + phone.substring(0, 2) + ") " +
                    phone.substring(2, 7) + "-" +
                    phone.substring(7, 11);
        }
    }

    private void handleSave() {
        String name = etName.getText().toString().trim();
        String cnpj = etCnpj.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Validar campos
        if (name.isEmpty()) {
            etName.setError("Digite o nome");
            etName.requestFocus();
            return;
        }

        if (cnpj.isEmpty()) {
            etCnpj.setError("Digite o CNPJ");
            etCnpj.requestFocus();
            return;
        }

        if (!isValidCnpj(cnpj)) {
            etCnpj.setError("CNPJ inválido");
            etCnpj.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Digite o telefone");
            etPhone.requestFocus();
            return;
        }

        if (!isValidPhone(phone)) {
            etPhone.setError("Telefone inválido");
            etPhone.requestFocus();
            return;
        }

        // Salvar cliente
        saveClient(name, cnpj, phone);
    }

    private void saveClient(String name, String cnpj, String phone) {
        // TODO: Implementar salvamento no banco de dados ou API

        Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();

        // Limpar campos
        etName.setText("");
        etCnpj.setText("");
        etPhone.setText("");

        // Voltar para tela anterior ou fechar
        finish();
    }

    private boolean isValidCnpj(String cnpj) {
        // Remove máscara
        String cleanCnpj = cnpj.replaceAll("[^\\d]", "");

        // CNPJ deve ter 14 dígitos
        return cleanCnpj.length() == 14;
    }

    private boolean isValidPhone(String phone) {
        // Remove máscara
        String cleanPhone = phone.replaceAll("[^\\d]", "");

        // Telefone deve ter 10 ou 11 dígitos
        return cleanPhone.length() == 10 || cleanPhone.length() == 11;
    }
}