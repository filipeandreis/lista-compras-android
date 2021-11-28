package ftec.listadecompras.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ftec.listadecompras.model.Listas;
import ftec.listadecompras.model.Listas;
import ftec.listadecompras.model.Produtos;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE = "bdprodutos";
    private static final int VERSION = 1;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public Database(Context context) { //Cria instacia no bd SQLite
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String listas = "CREATE TABLE listas(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomelista TEXT NOT NULL, data TEXT NOT NULL);";
        String produtos = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, listaid INTEGER NOT NULL, nomeproduto TEXT NOT NULL, quantidade INTEGER, FOREIGN KEY(listaid) REFERENCES listas(id));";

        sqLiteDatabase.execSQL(listas);
        sqLiteDatabase.execSQL(produtos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String produto = "DROP TABLE IF EXISTS produtos";
        String listas = "DROP TABLE IF EXISTS listas";

        sqLiteDatabase.execSQL(produto);
        sqLiteDatabase.execSQL(listas);
    }

    // Listas

    public void salvarLista(Listas lista) {
        ContentValues dados = new ContentValues();

        dados.put("nomelista", lista.getNomeLista());
        dados.put("data", df.format(Calendar.getInstance().getTime()));

        getWritableDatabase().insert("listas", null, dados);
    }

    public void alterarLista(Listas lista) {
        ContentValues dados = new ContentValues();

        dados.put("nomelista", lista.getNomeLista());

        String[] args = {lista.getId().toString()};
        getWritableDatabase().update("listas", dados, "id=?", args);
    }

    public void deletarLista(Listas lista) {
        String[] args = {lista.getId().toString()};

        getWritableDatabase().delete("produtos", "listaid=?", args);
        getWritableDatabase().delete("listas", "id=?", args);
    }

    public ArrayList<Listas> getListas() {
        String[] columns = {"id", "nomelista", "data"};
        Cursor cursor = getWritableDatabase().query("listas", columns, null, null, null, null, null, null);
        ArrayList<Listas> listas = new ArrayList<Listas>();

        while (cursor.moveToNext()) {
            Listas lista = new Listas();
            lista.setId(cursor.getInt(0));
            lista.setNomeLista(cursor.getString(1));
            lista.setData(df.format(Calendar.getInstance().getTime()));

            listas.add(lista);
        }
        return listas;
    }

    // Produtos
    public void salvarProduto(Produtos produto) {
        ContentValues dados = new ContentValues();

        dados.put("listaid", produto.getListaId());
        dados.put("nomeproduto", produto.getNomeProduto());
        dados.put("quantidade", produto.getQuantidade());

        getWritableDatabase().insert("produtos", null, dados);
    }

    public void alterarProduto(Produtos produto) {
        ContentValues dados = new ContentValues();

        dados.put("listaid", produto.getListaId());
        dados.put("nomeproduto", produto.getNomeProduto());
        dados.put("quantidade", produto.getQuantidade());

        String[] args = {produto.getId().toString()};
        getWritableDatabase().update("produtos", dados, "id=?", args);
    }

    public void deletarProduto(Produtos produto) {
        String[] args = {produto.getId().toString()};
        getWritableDatabase().delete("produtos", "id=?", args);
    }

    public ArrayList<Produtos> getProdutos(Integer listaid) {
        String[] args = {listaid.toString()};

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM produtos WHERE listaid=?", args);
        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        while (cursor.moveToNext()) {
            Produtos produto = new Produtos();
            produto.setId(cursor.getInt(0));
            produto.setListaId(cursor.getInt(1));
            produto.setNomeProduto(cursor.getString(2));
            produto.setQuantidade(cursor.getInt(3));

            produtos.add(produto);
        }
        return produtos;
    }
}
