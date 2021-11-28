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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ftec.listadecompras.BDHelper.Database;
import ftec.listadecompras.model.Listas;

public class ListasActivity extends AppCompatActivity {
    ListView listas;
    Button btnCadastrar;
    Database bdHelper;
    ArrayList<Listas> listview_listas;
    Listas lista;
    ArrayAdapter<Listas> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        initFormulario();

        btnCadastrar.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ListasActivity.this, FormularioListas.class);
                startActivity(intent);
            }
        });

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Listas listaEscolhida = (Listas) adapter.getItemAtPosition(position);

                Intent intent = new Intent(ListasActivity.this, ProdutosActivity.class);
                intent.putExtra("listaid", listaEscolhida.getId());

                startActivity(intent);
            }
        });

        listas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                lista = (Listas) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    private void initFormulario() {
        bdHelper = new Database(ListasActivity.this);
        btnCadastrar = findViewById(R.id.btn_cadastrar_lista);

        listas = findViewById(R.id.listview_listas);
        registerForContextMenu(listas);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Excluir");
        MenuItem menuEdit = menu.add("Editar");

        menuDelete.setOnMenuItemClickListener(menuItem -> {
            bdHelper = new Database(ListasActivity.this);
            bdHelper.deletarLista(lista);
            bdHelper.close();
            carregarLista();
            return true;
        });

        menuEdit.setOnMenuItemClickListener(menuItem -> {
            Intent i = new Intent(ListasActivity.this, FormularioListas.class);
            i.putExtra("lista-escolhida", lista);
            startActivity(i);

            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    public void carregarLista() {
        bdHelper = new Database(ListasActivity.this);
        listview_listas = bdHelper.getListas();
        bdHelper.close();

        if (listview_listas != null) {
            adapter = new ArrayAdapter<>(ListasActivity.this, android.R.layout.simple_list_item_1, listview_listas);
            listas.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, FormularioListas.class));
        super.onDestroy();
    }
}
