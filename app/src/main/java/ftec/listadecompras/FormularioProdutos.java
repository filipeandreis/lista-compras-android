package ftec.listadecompras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ftec.listadecompras.BDHelper.Database;
import ftec.listadecompras.model.Listas;
import ftec.listadecompras.model.Produtos;

public class FormularioProdutos extends AppCompatActivity {
    TextView txtNomeProduto;
    EditText editText_NomeProd, editText_Quantidade;
    Button btn_poliform;
    Produtos editarProduto, produto;
    Database bdHelper;
    Integer listaId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produtos);

        produto = new Produtos();
        bdHelper = new Database(FormularioProdutos.this);

        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");
        listaId = Integer.parseInt(intent.getSerializableExtra("listaid").toString());

        initFormulario();

        if (editarProduto != null) {
            btn_poliform.setText("Salvar");

            txtNomeProduto.setText("Editar - " + editarProduto.getNomeProduto());
            editText_NomeProd.setText(editarProduto.getNomeProduto());
            editText_Quantidade.setText(editarProduto.getQuantidade() + "");

            produto.setId(editarProduto.getId());

        } else {
            btn_poliform.setText("Cadastrar produto");
        }

        btn_poliform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_NomeProd.getText().toString().length() > 0 && !editText_NomeProd.getText().toString().equals("")) {
                    if (editText_Quantidade.getText().toString().length() > 0 && !editText_Quantidade.getText().toString().equals("")) {

                        produto.setListaId(listaId);
                        produto.setNomeProduto(editText_NomeProd.getText().toString());
                        produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));

                        if (btn_poliform.getText().toString().equals("Cadastrar produto")) {
                            Toast.makeText(FormularioProdutos.this, "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                            bdHelper.salvarProduto((produto));
                            bdHelper.close();
                            voltarInicio();
                        } else {
                            Toast.makeText(FormularioProdutos.this, "Alterado com Sucesso!", Toast.LENGTH_LONG).show();
                            bdHelper.alterarProduto(produto);
                            bdHelper.close();
                            voltarInicio();
                        }
                    } else {
                        Toast.makeText(FormularioProdutos.this, "Preencha a quantidade!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(FormularioProdutos.this, "Preencha o Nome!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initFormulario() {
        txtNomeProduto = findViewById(R.id.txtNomeProduto);
        editText_NomeProd = findViewById(R.id.editText_NomeProduto);
        editText_Quantidade = findViewById(R.id.editText_Quantidade);
        btn_poliform = findViewById(R.id.btn_poliform);
    }

    public void voltarInicio() {
        Intent intent = new Intent(FormularioProdutos.this, ProdutosActivity.class);
        startActivity(intent);
        finish();
        limpaActivity();
    }

    public void limpaActivity() {
        Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
