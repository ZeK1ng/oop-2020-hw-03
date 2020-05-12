package DataBase;

import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class MetropolisTest {


    private Metropolis mt;
    @Test
    public void test() throws SQLException {
        mt = new Metropolis();
        mt.resetTable();
        mt.search("", "", "", false, false);
        assertEquals(0,mt.getColumnCount());
        assertEquals(0, mt.getRowCount());
        mt.search("London", "Europe", "2000", false, false);
        assertEquals(0, mt.getRowCount());
        mt.search("London", "Eu", "200", true, false);
        assertEquals(1, mt.getRowCount());
        mt.search("", "Eu", "", true, false);
        assertTrue(mt.getColumnName(0).equals("Metropolis"));
        assertFalse(mt.isCellEditable(0, 0));
        assertEquals(null, mt.getValueAt(0, 10));
        assertTrue(mt.getValueAt(0, 0).equals("London"));
        mt.main(null);
        mt.search("London", "Europe", "2", true, true);
        mt.search("London", "Europe", "", true, true);
        mt.add("TBILISI", "Europe", "10000000");
        assertEquals(3, mt.getColumnCount());
        mt.add("", "", "");
        mt.add("AS","","");
        mt.add("as","ASda","");
        mt.add("asda","asda","-22");
        mt.Catcher();
        mt.add("budga","Europa","1");
    }




}
