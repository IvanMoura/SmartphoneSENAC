package br.senac.pi.smartphones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aluno on 06/11/2015.
 */
public class CelularDb extends SQLiteOpenHelper{


    private static final String NOME_BANCO = "celular.sqlite";
    private static final int VERSAO_BANCO = 1;

    public CelularDb(Context context){

        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE IF NOT EXISTS celular(_id integer primary key autoincrement, modelo text, fabricante text, preco double);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){


    }
    public long save(Celular celular){
        long id = celular.getId();
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("modelo",celular.getModelo());
            values.put("fabricante",celular.getFabricante());
            values.put("preco",celular.getPreco());
            if (id != 0){
                String _id = String.valueOf(celular.getId());
                String[] whereargs = new String[]{_id};

                int count = db.update("celular",values, "_id=?", whereargs);
                return count;
            }else{

                id = db.insert("celular","",values);
                return id;
            }
        }finally {
            db.close();
        }
    }

    public int delete(Celular celular){
        SQLiteDatabase db = getWritableDatabase();
        try{

            int count = db.delete("celular","_id=?",new String[]{String.valueOf(celular.getId())});

            return count;
        }finally {
            //db.close();
        }
    }

    public List<Celular> lista(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor cursor = db.query("celular", null, null, null, null, null, null, null);
            return list(cursor);
        }finally {
            db.close();
        }
    }

    public List<Celular> list(Cursor cursor){
        List<Celular> listCelular = new ArrayList<Celular>();
        if (cursor.moveToFirst()){
            do {
                Celular celular = new Celular();
                listCelular.add(celular);

                celular.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                celular.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
                celular.setFabricante(cursor.getString(cursor.getColumnIndex("fabricante")));
                celular.setPreco(cursor.getDouble(cursor.getColumnIndex("preco")));

            }while (cursor.moveToNext());
        }
        return listCelular;
    }
}
