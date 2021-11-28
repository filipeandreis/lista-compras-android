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

public class FormularioListas extends AppCompatActivity {
    TextView txtNomeLista;
    EditText editText_NomeLista;
    Button btn_poliform_lista;
    Listas editarLista, lista;
    Database bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_listas);

        lista = new Listas();
        bdHelper = new Database(FormularioListas.this);

        Intent intent = getIntent();
        editarLista = (Listas) intent.getSerializableExtra("lista-escolhida");

        initFormulario();

        if (editarLista != null) {
            btn_poliform_lista.setText("Alterar");

            txtNomeLista.setText(editarLista.getNomeLista().concat(" | ").concat(editarLista.getData()));
            editText_NomeLista.setText(editarLista.getNomeLista());

            lista.setId(editarLista.getId());

        } else {
            btn_poliform_lista.setText("Cadastrar nova lista");
        }

        btn_poliform_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_NomeLista.getText().toString().length() > 0 && !editText_NomeLista.getText().toString().equals("")) {
                    lista.setNomeLista(editText_NomeLista.getText().toString());


                    if (btn_poliform_lista.getText().toString().equals("Cadastrar nova lista")) {
                        Toast.makeText(FormularioListas.this, "Cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                        bdHelper.salvarLista(lista);
                        bdHelper.close();
                        voltarInicio();
                    } else {
                        Toast.makeText(FormularioListas.this, "Alterada com sucesso!", Toast.LENGTH_LONG).show();
                        bdHelper.alterarLista(lista);
                        bdHelper.close();
                        voltarInicio();
                    }
                } else {
                    Toast.makeText(FormularioListas.this, "Preencha o nome!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void initFormulario() {
        txtNomeLista = findViewById(R.id.txtNomeLista);
        editText_NomeLista = findViewById(R.id.editText_NomeLista);

        btn_poliform_lista = findViewById(R.id.btn_poliformlista);
    }

    public void voltarInicio() {
        Intent intent = new Intent(FormularioListas.this, ListasActivity.class);
        startActivity(intent);
        finish();
        limpaActivity();
    }

    public void limpaActivity() {
        Intent intent = new Intent(getApplicationContext(), ListasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
