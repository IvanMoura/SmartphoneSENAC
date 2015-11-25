package br.senac.pi.smartphones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ListView listaCelular;
Button abrirCadastro;
    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"modelo","fabricante","preco","_id"};

        CelularDb celularDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listaCelular= (ListView) findViewById(R.id.listViewCelulares);

        celularDb = new CelularDb(this);
        database = celularDb.getWritableDatabase();
        listaCelular.setOnItemClickListener(acao());


        abrirCadastro=(Button) findViewById(R.id.btnAbrirCadastro);
        abrirCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,CadastroActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    protected void onResume() {
        super.onResume();
       /* List<Celular> celular;
        BDHelper db = new BDHelper(MainActivity.this);
        ArrayAdapter<Celular> adapter;
        adapter = new ArrayAdapter<Celular>(this,android.R.layout.simple_dropdown_item_1line,db.seleciona());
        listaCelular.setAdapter(adapter);
    */

        Cursor celular = database.query("celular", campos, null, null, null, null,null);
        if (celular.getCount() > 0) {
            dataSource = new SimpleCursorAdapter(MainActivity.this, R.layout.row, celular, campos, new int[]{R.id.txtModelo, R.id.txtFabricante,R.id.txtPreco});
            listaCelular.setAdapter(dataSource);

        } else {
            Toast.makeText(MainActivity.this, "Nenhum registro cadastrado", Toast.LENGTH_LONG).show();
        }

    }




    private AdapterView.OnItemClickListener acao(){
        return new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ação sobre smartphone");
                builder.setMessage("Qual a ação desejada?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String codigo;
                        Cursor cursor = database.query("celular", campos, null, null, null, null, null);
                        cursor.moveToPosition(position);
                        codigo = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                        intent.putExtra("_id", codigo);
                        startActivity(intent);
                        finish();

                    }
                });

                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* long itemSelect = id;
                        Celular celular = new Celular();
                        celular.setId(itemSelect);
                        celularDb.delete(celular);
                    */

                        long itemSelect = id;
                        Celular celular = new Celular();
                        celular.setId(itemSelect);
                        celularDb.delete(celular);
                        onResume();




                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


}
        };
    }

}
