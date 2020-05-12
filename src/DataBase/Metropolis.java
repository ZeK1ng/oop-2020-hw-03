package DataBase;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.*;

public class Metropolis extends AbstractTableModel {
    static String account = "ZeK1ng";
    static String password = "Pass_word1";
    static String server = "localhost";
    static String database = "hwDB";
    private Statement stmt;
    private Connection con;
    private List<List<String>> dt;
    private List<String> columns;

    public Metropolis() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection
                    ("jdbc:mysql://" + server, account, password);
            stmt = con.createStatement();
            stmt.executeQuery("USE " + database + ";");
            resetTable();
            dt = new ArrayList<List<String>>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises;");
            while (rs.next()) {
                String metropolis = rs.getString("metropolis");
                String continent = rs.getString("continent");
                String population = rs.getString("population");
                columns = new ArrayList<String>();
                columns.add(metropolis);
                columns.add(continent);
                columns.add(population);
                dt.add(columns);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Metropolis metrop = new Metropolis();

    }

    public void resetTable() throws SQLException {

        stmt.executeUpdate("DROP TABLE IF EXISTS metropolises;");
        stmt.executeUpdate("CREATE TABLE metropolises (\n" +
                "    metropolis CHAR(64),\n" +
                "    continent CHAR(64),\n" +
                "    population BIGINT\n" +
                ");");
        stmt.executeUpdate("INSERT INTO metropolises VALUES\n" +
                "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                "    (\"New York\",\"North America\",21295000),\n" +
                "\t(\"San Francisco\",\"North America\",5780000),\n" +
                "\t(\"London\",\"Europe\",8580000),\n" +
                "\t(\"Rome\",\"Europe\",2715000),\n" +
                "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                "\t(\"San Jose\",\"North America\",7354555),\n" +
                "\t(\"Rostov-on-Don\",\"Europe\",1052000);");

    }


    @Override
    public int getRowCount() {
        return dt.size();
    }

    @Override
    public int getColumnCount() {
        if(dt.size()==0) return 0 ;
        return dt.get(0).size();
    }

    @Override
    public String getColumnName(int column) {
        List<String> names = new ArrayList<>();
        names.add("Metropolis");
        names.add("Continent");
        names.add("Population");
        return names.get(column);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<String> currRow = dt.get(rowIndex);
        if (columnIndex < currRow.size()) return currRow.get(columnIndex);
        return null;
    }

    public void add(String metroInp, String contInp, String populInp) {
        try {
            if (metroInp.isEmpty() || contInp.isEmpty() || populInp.isEmpty()) return;
            if (populInp.charAt(0) == '-') return;
            String Sql_command = "INSERT INTO metropolises VALUES(?,?,?);";
            PreparedStatement stm = con.prepareStatement(Sql_command);
            stm.setString(1, metroInp);
            stm.setString(2, contInp);
            stm.setString(3, populInp);
            columns = new ArrayList<String>();
            columns.add(metroInp);
            columns.add(contInp);
            columns.add(populInp);
            dt.add(columns);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fireTableStructureChanged();
    }

    public void Catcher() throws SQLException {
        con.close();
    }

    public void search(String metroInp, String contInp, String populInp, Boolean popLargerThan, Boolean exactMatch) {
        String Sql_command = "Select * from  metropolises where ";
        if(populInp.isEmpty()){
            populInp = "0";
        }
        if (!metroInp.isEmpty()){
            Sql_command+="metropolis" ;
            if(exactMatch){
                Sql_command+=" = '"+metroInp+"'" ;
            }else{
                Sql_command+=" Like '%"+metroInp+"%'";
            }
        }
        if(!contInp.isEmpty()){
            if(!metroInp.isEmpty()){
                Sql_command +=" AND ";
            }
            Sql_command+="continent";
            if(exactMatch){
                Sql_command+=" = '"+contInp+"'" ;
            }else{
                Sql_command+=" Like '%"+contInp+"%'";
            }
        }
        if(!metroInp.isEmpty()|| !contInp.isEmpty()){
            Sql_command +=" AND ";
        }
        Sql_command+="population";
        if(popLargerThan){
            Sql_command+=" > "+populInp;
        }else{
            Sql_command+=" <= "+populInp;
        }

        Sql_command+=";";
        displaySearchResult(Sql_command);
    }

    private void displaySearchResult(String sql_command) {
        dt.clear();
        try{
            ResultSet rs = stmt.executeQuery(sql_command);
            while (rs.next()) {
                String metropolis = rs.getString("metropolis");
                String continent = rs.getString("continent");
                String population = rs.getString("population");
                columns = new ArrayList<String>();
                columns.add(metropolis);
                columns.add(continent);
                columns.add(population);
                dt.add(columns);
            }
        }catch(SQLException e){
                e.printStackTrace();
        }
        fireTableStructureChanged();
    }
}