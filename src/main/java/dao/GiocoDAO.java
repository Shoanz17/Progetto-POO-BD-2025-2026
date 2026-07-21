package dao;

import model.EdizioneGioco;
import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoDAO {
    ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException;

    ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws SQLException;

    void updateTitolo(int idGioco, String titolo) throws SQLException;
    void updateCategoriaGioco(int idGioco, String nomeCategoria) throws SQLException;
    void updatePegiGioco(int idGioco, int pegi) throws SQLException;
    void updateGeneriGioco(int idGioco, ArrayList<Genere> nuoviGeneri) throws SQLException;
    ArrayList<Gioco> getListaGiochi() throws SQLException;
}