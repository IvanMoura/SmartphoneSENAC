package br.senac.pi.smartphones;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CadastroActivity extends AppCompatActivity {
EditText modelo,fabricante,preco;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
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
   modelo=(EditText) findViewById(R.id.edtModelo);
   fabricante=(EditText) findViewById(R.id.edtFabricante);
   preco=(EditText) findViewById(R.id.edtPreco);
   findViewById(R.id.btnCadastrar).setOnClickListener(cadastrar());



    }


    private View.OnClickListener cadastrar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CelularDb db = new CelularDb(CadastroActivity.this);
                Celular celular = new Celular();
                celular.setModelo(modelo.getText().toString());
                celular.setFabricante(fabricante.getText().toString());
                celular.setPreco(Double.parseDouble(preco.getText().toString()));

                if(db.save(celular) != -1){
                    Toast.makeText(CadastroActivity.this,"Cadastro realizado com sucesso!",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(CadastroActivity.this, "Falha ao cadastrar",Toast.LENGTH_LONG).show();
                }
                finish();

            }
        };
    }




}
