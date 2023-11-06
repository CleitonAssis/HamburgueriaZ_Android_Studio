package com.cleiton.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Representa os campos do xml
    private EditText editNome;
    private CheckBox checkOpcional1, checkOpcional2, checkOpcional3;
    private TextView textQuantidadePedido, textValorTotal;
    private int quantidadePedido = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recupera os campos do xml
        editNome = findViewById(R.id.editNome);
        checkOpcional1 = findViewById(R.id.checkOpcional1);
        checkOpcional2 = findViewById(R.id.checkOpcional2);
        checkOpcional3 = findViewById(R.id.checkOpcional3);
        Button buttonMenos = findViewById(R.id.buttonMenos);
        textQuantidadePedido = findViewById(R.id.textQuantidadePedido);
        Button buttonMais = findViewById(R.id.buttonMais);
        textValorTotal = findViewById(R.id.textValorTotal);
        Button buttonFazerPedido = findViewById(R.id.buttonFazerPedido);

        checkOpcional1.setOnClickListener(v ->
                updateQuantidadeView()
        );

        checkOpcional2.setOnClickListener(v ->
                updateQuantidadeView()
        );

        checkOpcional3.setOnClickListener(v ->
                updateQuantidadeView()
        );

        buttonMenos.setOnClickListener(v ->
                decrementarQuantidade()
        );

        buttonMais.setOnClickListener(v ->
                incrementarQuantidade()
        );

        buttonFazerPedido.setOnClickListener(v ->
                fazerPedido()
        );
    }

    private void incrementarQuantidade() {
        quantidadePedido ++;
        updateQuantidadeView();
    }

    private void decrementarQuantidade() {
        if (quantidadePedido > 0) {
            quantidadePedido --;
            updateQuantidadeView();

        }
    }

    private void updateQuantidadeView() {
        textQuantidadePedido.setText(String.valueOf(quantidadePedido));
        updateValorTotalView();
    }

    @SuppressLint("SetTextI18n")
    private void updateValorTotalView() {
        //Calcula o valor total do pedido
        double precoBase = 20.0;
        double precoBacon = checkOpcional1.isChecked() ? 2.0 : 0.0;
        double precoQueijo = checkOpcional2.isChecked() ? 2.0 : 0.0;
        double precoOnionRings = checkOpcional3.isChecked() ? 3.0 : 0.0;

        double valorTotal = quantidadePedido * (precoBase + precoBacon + precoQueijo + precoOnionRings);
        textValorTotal.setText("R$ " + valorTotal);
    }

    @SuppressLint("SetTextI18n")
    private void fazerPedido() {
        // Identifica o nome do cliente
        String nomeCliente = editNome.getText().toString();

        // Identifica os adicionais selecionados
        boolean temBacon = checkOpcional1.isChecked();
        boolean temQueijo = checkOpcional2.isChecked();
        boolean temOnionRings = checkOpcional3.isChecked();

        // Calcula o valor total do pedido
        double precoBase = 20.0;
        double precoBacon = temBacon ? 2.0 : 0.0;
        double precoQueijo = temQueijo ? 2.0 : 0.0;
        double precoOnionRings = temOnionRings ? 3.0 : 0.0;
        double valorTotal = quantidadePedido * (precoBase + precoBacon + precoQueijo + precoOnionRings);

        // Cria a mensagem de resumo do pedido
        String resumo = "Nome do cliente: " + nomeCliente + "\n" +
                "Tem Bacon? " + (temBacon ? "Sim" : "Não") + "\n" +
                "Tem Queijo? " + (temQueijo ? "Sim" : "Não") + "\n" +
                "Tem Onion Rings? " + (temOnionRings ? "Sim" : "Não") + "\n" +
                "Quantidade: " + quantidadePedido + "\n" +
                "Preço final: R$ " + valorTotal;

        // Cria a Intent para iniciar a activity "ResumoPedido" e passar o resumo e nomeCliente
        Intent intentResumoPedido = new Intent(MainActivity.this, ResumoPedido.class);
        intentResumoPedido.putExtra("resumoIntent", resumo);
        intentResumoPedido.putExtra("nomeClienteIntent", nomeCliente);
        startActivity(intentResumoPedido);

        // Limpa campos após envio da Intent
        editNome.setText("");
        checkOpcional1.setChecked(false);
        checkOpcional2.setChecked(false);
        checkOpcional3.setChecked(false);
        quantidadePedido = 0;
        textQuantidadePedido.setText(String.valueOf(0));
        textValorTotal.setText("R$ " + 0.0);
    }
}