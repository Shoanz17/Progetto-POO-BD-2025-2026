package dao;

import model.CampoNonValidoException;
import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDAO {
    void creaGenere(Genere genere) throws SQLException;

    ArrayList<Genere> getListaGeneri() throws SQLException, CampoNonValidoException;
    ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) throws SQLException, CampoNonValidoException;
    ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws SQLException, CampoNonValidoException;

    ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws SQLException, CampoNonValidoException;
    void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi)throws SQLException;
}
