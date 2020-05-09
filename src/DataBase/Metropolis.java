package DataBase;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class Metropolis extends AbstractTableModel {
    static String account = "ZeK1ng";
    static String password = "Pass_word1";
    static String server = "localhost";
    static String database = "hwDB";
    public Metropolis(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection
                    ( "jdbc:mysql://" + server, account ,password);
            Statement stmt = con.createStatement();
            stmt.executeQuery("USE " + database + ";");
            ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises;");
// System.out.println(rs.next());
            while(rs.next()) {
                String name = rs.getString("metropolis");
                long pop = rs.getLong("population");
                System.out.println(name + "\t" + pop);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Metropolis metrop = new Metropolis();

    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public void add(String metroInp, String contInp, String populInp) {
    }

    public void search(String metroInp, String contInp, String populInp, Boolean popLargerThan, Boolean exactMatch) {
    }
}
