package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface AbstractDomainObject extends Serializable {

    String vratiNazivTabele();

    List<AbstractDomainObject> vratiListu(ResultSet rs) throws Exception;

    String vratiKoloneZaUbacivanje();

    String vratiVrednostiZaUbacivanje();

    String vratiPrimarniKljuc();

    AbstractDomainObject vratiObjekatRS(ResultSet rs) throws Exception;

    String vratiVrednostiZaIzmenu();
}
