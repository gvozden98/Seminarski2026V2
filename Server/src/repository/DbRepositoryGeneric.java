package repository;

import domain.AbstractDomainObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DbRepositoryGeneric implements DbRepository<AbstractDomainObject> {

    @Override
    public List<AbstractDomainObject> getAll() throws Exception {
        throw new UnsupportedOperationException("Koristi getAll(param, uslov).");
    }

    @Override
    public List<AbstractDomainObject> getAll(AbstractDomainObject param, String uslov) throws Exception {
        String sql = "SELECT * FROM " + param.vratiNazivTabele() + dodajUslov(uslov);
        try (Statement statement = getConnection().createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            return param.vratiListu(rs);
        }
    }

    @Override
    public AbstractDomainObject get(AbstractDomainObject param, String uslov) throws Exception {
        List<AbstractDomainObject> lista = getAll(param, uslov);
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }

    @Override
    public PreparedStatement add(AbstractDomainObject param) throws Exception {
        String sql = "INSERT INTO " + param.vratiNazivTabele()
                + " (" + param.vratiKoloneZaUbacivanje() + ") VALUES ("
                + param.vratiVrednostiZaUbacivanje() + ")";
        PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        return ps;
    }

    @Override
    public void edit(AbstractDomainObject param) throws Exception {
        String sql = "UPDATE " + param.vratiNazivTabele()
                + " SET " + param.vratiVrednostiZaIzmenu()
                + " WHERE " + param.vratiPrimarniKljuc();
        try (Statement statement = getConnection().createStatement()) {
            int brojIzmenjenihRedova = statement.executeUpdate(sql);
            if (brojIzmenjenihRedova == 0) {
                throw new Exception("Sistem ne moze da izmeni zapis.");
            }
        }
    }

    @Override
    public void delete(AbstractDomainObject param) throws Exception {
        String sql = "DELETE FROM " + param.vratiNazivTabele()
                + " WHERE " + param.vratiPrimarniKljuc();
        try (Statement statement = getConnection().createStatement()) {
            int brojObrisanihRedova = statement.executeUpdate(sql);
            if (brojObrisanihRedova == 0) {
                throw new Exception("Sistem ne moze da obrise zapis.");
            }
        }
    }

    private String dodajUslov(String uslov) {
        if (uslov == null || uslov.trim().isEmpty()) {
            return "";
        }
        return " " + uslov.trim();
    }

    private Connection getConnection() throws Exception {
        return DbConnectionFactory.getInstance().getConnection();
    }
}
