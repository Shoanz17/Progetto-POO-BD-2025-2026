package dao;

import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDAO {
    void creaGenere(Genere genere) throws SQLException;

    ArrayList<Genere> getListaGeneri() throws SQLException;
    ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) throws SQLException;
    ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws SQLException;

    ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws SQLException;
    void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi)throws SQLException;
}
