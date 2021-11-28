package ftec.listadecompras;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ftec.listadecompras.BDHelper.Database;
import ftec.listadecompras.model.Listas;
import ftec.listadecompras.model.Produtos;

public class ProdutosActivity extends AppCompatActivity {
    ListView lista;
    Button btnCadastrar;
    Database bdHelper;
    ArrayList<Produtos> listview_produtos;
    Produtos produto;
    ArrayAdapter<Produtos> adapter;
    Listas listaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        Intent intent = getIntent();
        listaSelecionada = (Listas) intent.getSerializableExtra("listaselecionada");

        TextView titulo = findViewById(R.id.txtTitulo);
        titulo.setText(listaSelecionada.getNomeLista().concat(" | ").concat(listaSelecionada.getData()));

        initFormulario();

        btnCadastrar.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ProdutosActivity.this, FormularioProdutos.class);

                intent.putExtra("listaid", listaSelecionada.getId());

                startActivity(intent);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(position);

                Intent i = new Intent(ProdutosActivity.this, FormularioProdutos.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                intent.putExtra("listaid", listaSelecionada.getId());

                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produtos) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    private void initFormulario() {
        bdHelper = new Database(ProdutosActivity.this);
        btnCadastrar = findViewById(R.id.btn_cadastrar);

        lista = findViewById(R.id.listview_produtos);
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Excluir");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                bdHelper = new Database(ProdutosActivity.this);
                bdHelper.deletarProduto(produto);
                bdHelper.close();
                carregarProduto();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto() {
        bdHelper = new Database(ProdutosActivity.this);
        listview_produtos = bdHelper.getProdutos(listaSelecionada.getId());
        bdHelper.close();

        if (listview_produtos != null) {
            adapter = new ArrayAdapter<Produtos>(ProdutosActivity.this, android.R.layout.simple_list_item_1, listview_produtos);
            lista.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, FormularioProdutos.class));
        super.onDestroy();
    }
}
