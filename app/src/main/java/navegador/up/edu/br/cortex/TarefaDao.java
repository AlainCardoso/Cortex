package navegador.up.edu.br.cortex;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TarefaDao {
    static ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
    static  Integer id = 0;


    public boolean salvar(Tarefa tarefa){

        SQLiteDatabase conn = Conn.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo",tarefa.getTitulo());
        values.put("descricao",tarefa.getDescricao());
        values.put("dataInicio",tarefa.getDataInicio());
        if(tarefa.getSequencial().equals("Unico")){
            values.put("dataFim","Evento unico");
        }else {
            values.put("dataFim",tarefa.getDataFim());
        }
        values.put("sequencial",tarefa.getSequencial());
        values.put("statusTarefas",tarefa.getStatusTarefa());
        if(tarefa.getId() == null){
            conn.insert("tarefa", null,values);
            return false;
        } else {
            conn.update("tarefa", values,"id = ?", new String [] {tarefa.getId().toString()});
            return true;
        }

    }

    public List<Tarefa> listar() {
        SQLiteDatabase conn = Conn.getInstance().getReadableDatabase();
        Cursor c = conn.query("tarefa",new String[]{"id","titulo","descricao","sequencial","dataInicio","dataFim","statusTarefas"},null,null,null,null,"id");
        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
        if(c.moveToFirst()){
            do{
                Tarefa tarefa = new Tarefa();
                tarefa.setId(c.getInt(0));
                tarefa.setTitulo(c.getString(1));
                tarefa.setDescricao(c.getString(2));
                tarefa.setSequencial(c.getString(3));
                tarefa.setDataInicio(c.getString(4));
                tarefa.setDataFim(c.getString(5));
                tarefa.setStatusTarefa(c.getInt(6));
                tarefas.add(tarefa);
            }while(c.moveToNext());
        }
        return  tarefas;
    }


    public void excluir(Tarefa tarefa){
        SQLiteDatabase coon = Conn.getInstance().getWritableDatabase();

        coon.delete("tarefa","id = ?", new String[]{tarefa.getId().toString()});
    }


}
