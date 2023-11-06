package com.cleiton.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResumoPedido extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        // Recupera o Intent que iniciou esta atividade
        Intent intentResumoPedido = getIntent();

        // Verifica se o Intent contém as chave "resumoIntent" e "nomeClienteIntent"
        if (intentResumoPedido.hasExtra("resumoIntent") && intentResumoPedido.hasExtra("nomeClienteIntent")) {
            // Extrai o valor associado à chave "resumoIntent"

            String resumoPedido = intentResumoPedido.getStringExtra("resumoIntent");
            // Extrai o valor associado à chave "nomeCliente"
            String nomeCliente = intentResumoPedido.getStringExtra("nomeClienteIntent");

            // Exibe o valor no TextView textResumoPedido
            TextView textResumoPedido = findViewById(R.id.textResumoPedido);
            textResumoPedido.setText(resumoPedido);

            // Adiciona um ouvinte de clique ao botão "buttonConfirmarPedido"
            Button buttonConfirmarPedido = findViewById(R.id.buttonEnviarPedido);
            buttonConfirmarPedido.setOnClickListener(view ->
                    // Código para enviar o resumo do pedido por e-mail
                    enviarResumoPorEmail("Pedido de " + nomeCliente, "Resumo do Pedido\n".toUpperCase() + resumoPedido)
            );
            
        } else {
            // Caso alguma chave não conferir, exibe a msg de erro
            Toast.makeText(this, "Erro no envio do resumo do pedido !", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void enviarResumoPorEmail(String subject, String message) {
        // Cria uma Intent para enviar o e-mail
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"hamburgueriaz@email.com.br"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Inicia o aplicativo de e-mail
        startActivity(Intent.createChooser(emailIntent, "Enviar pedido por e-mail"));
        Toast.makeText(this, "Resumo do pedido criado com sucesso !", Toast.LENGTH_LONG).show();
        finish();
    }
}