package com.api.testing.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.*;


public class DBUtil {

  private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ResultSetMetaData rsmd;

    private static final Logger log = LogManager.getLogger(DBUtil.class);

    public static void getDBConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            log.error("Could not connect to DB:\n\"" + jdbcUrl + "\"\nUsing Username: \"" + dbUser + "\" and Password: \"" + dbPass + "\"");
            e.printStackTrace();
            Assertions.fail();
        }
    }

    
    public static ResultSet executeQuery(String query) {
        try {
//            log.info("query: "+query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }
        return resultSet;
    }

    public static void executeUpdate(String query) {
        try {
//            log.info("query: "+query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }
    }

    public static void addBatchQuery(String query) {
        try {
            statement.addBatch(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }
    }

    public static void executeBatch() {
        try {
            statement.executeBatch();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }
    }

    public static void executeMultiQuery(String query) {
        try {
            statement.addBatch(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail();
        }
    }

    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    public static List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<String>();
        try {
            rsmd = resultSet.getMetaData();
            int column = 1;
            while (column <= rsmd.getColumnCount()) {
                columnNames.add(rsmd.getColumnName(column++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return columnNames;
    }

    public static int rowCount() {
        int rowNumber = 0;
        try {
            resultSet.last();
            rowNumber = resultSet.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return rowNumber;
    }

    public static List<String> getDataOnAColumn(int columnNumber) {
        List<String> columnData = new ArrayList<>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                columnData.add(resultSet.getString(columnNumber).replaceFirst("\\s$", ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return columnData;
    }

    public static List<String> getUniqueDataOnAColumn(int columnNumber) {
        LinkedHashSet<String> columnDataSet = new LinkedHashSet<String>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                columnDataSet.add(resultSet.getString(columnNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        List<String> columnData=new ArrayList<>();
        columnData.addAll(columnDataSet);
        return columnData;
    }

    public static String getString(String columnLabel){
        String queryValue=null;
        try {
           queryValue= resultSet.getString(columnLabel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static String getString(int columnNumber){
        String queryValue=null;
        try {
            resultSet.first();
            queryValue = resultSet.getString(columnNumber);
        } catch (SQLException e) {
            try {
                resultSet.first();
                queryValue = String.valueOf(resultSet.getInt(columnNumber));
            } catch (SQLException ex) {
                ex.printStackTrace();
                Assertions.fail();
            }
        }
        return queryValue;
    }

    public static int getInt(String columnLabel){
        int queryValue=0;
        try {
            queryValue= resultSet.getInt(columnLabel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static int getInt(int columnNumber){
        int queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getInt(columnNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static long getLong(String columnLabel){
        long queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getLong(columnLabel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static long getLong(int columnNumber){
        long queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getLong(columnNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static double getDouble(String columnLabel){
        double queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getDouble(columnLabel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static double getDouble(int columnNumber){
        double queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getDouble(columnNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static float getFloat(String columnLabel){
        float queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getFloat(columnLabel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static float getFloat(int columnNumber){
        float queryValue=0;
        try {
            resultSet.first();
            queryValue= resultSet.getFloat(columnNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail();
        }
        return queryValue;
    }

    public static List<String> getUniqueDataOnAColumn(String columnLabel) {
        LinkedHashSet<String> columnDataSet = new LinkedHashSet<String>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                columnDataSet.add(resultSet.getString(columnLabel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        List<String> columnData=new ArrayList<>();
        columnData.addAll(columnDataSet);
        return columnData;
    }

    public static List<String> getDataOnAColumn_QueryWithBlankColumn(int columnNumber) {
        List<String> columnData = new ArrayList<>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (resultSet.getString(columnNumber).length() > 0) {
                    columnData.add(resultSet.getString(columnNumber));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return columnData;
    }

    public static List<String> getDataOnAColumn(String columnLabel) {
        List<String> columnData = new ArrayList<>();
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                columnData.add(resultSet.getString(columnLabel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return columnData;
    }

    public static List<String> getDataOnARow(int rowNumber) {
        List<String> rowList = new ArrayList<>();
        try {
            rsmd = resultSet.getMetaData();
            resultSet.absolute(rowNumber);
            int column = 1;
            while (column <= rsmd.getColumnCount()) {
                rowList.add(resultSet.getString(column++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return rowList;
    }

    public static List<List<String>> getListOfRowList() {
        List<List<String>> allRowList = new ArrayList<>();
        try {
            rsmd = resultSet.getMetaData();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                List<String> rowList = new ArrayList<>();
                int column = 1;
                while (column <= rsmd.getColumnCount()) {
                    rowList.add(resultSet.getString(column++));
                }
                allRowList.add(rowList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return allRowList;
    }

    public static List<Map<String, String>> getQueryResultMapList() {
        List<Map<String, String>> allRowMapList = new ArrayList<Map<String, String>>();
        try {
            rsmd = resultSet.getMetaData();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Map<String, String> rowMap = new HashMap<String, String>();
                int column = 1;
                while (column <= rsmd.getColumnCount()) {
                    rowMap.put(rsmd.getColumnName(column), resultSet.getString(column++));
                }
                allRowMapList.add(rowMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return allRowMapList;
    }

    public static Map<String, String> getQueryResultMap() {
        Map<String, String> rowMap = new HashMap<>();
        try {
            rsmd = resultSet.getMetaData();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int column = 1;
                while (column <= rsmd.getColumnCount()) {
                    rowMap.put(rsmd.getColumnName(column), resultSet.getString(column++));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return rowMap;
    }

    public static Map<String, String> getQueryResultMapWithFirstColumnKeySecondColumnValue() {
        Map<String, String> rowMap = new HashMap<>();
        try {
            rsmd = resultSet.getMetaData();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int column = 1;
                int column2 = 1;
                while (column <= rsmd.getColumnCount()) {
                    rowMap.put(resultSet.getString(column2), resultSet.getString(column));
                    column++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return rowMap;
    }

    public static int getCount(String tableName, String id) throws Exception {
        int count = 0;
        try {
                resultSet = executeQuery("SELECT COUNT(*) FROM " + dbName + "." + tableName + " where id ="+id+";");

                if (resultSet != null) {
                    resultSet.beforeFirst();
                    resultSet.last();
                    count = resultSet.getInt(1);
                }
            } catch (SQLException e) {
        e.printStackTrace();
        Assertions.fail();
    }
        return count;
    }

    public static int countRowsOfSpecificQuery(String query){
        resultSet = executeQuery(query);
        return rowCount();
    }
  
    public static String getValueByColumnNumber(String query, int columnNumber){
        try {

            resultSet = executeQuery(query);
            if(resultSet.first())
//                System.out.println(resultSet.getString(columnNumber));
//                System.out.println(resultSet.getBigDecimal(columnNumber));
//                System.out.println(resultSet.getBlob(columnNumber));
            return resultSet.getString(columnNumber);
        }catch(SQLException e){
            e.printStackTrace();
            Assertions.fail();
        }
        return null;
    }

}

