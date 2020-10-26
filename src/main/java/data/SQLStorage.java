package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shape.*;

public class SQLStorage {

    private static String databaseUrl = "jdbc:mysql://localhost:3306/vat";
    private Connection connection;


    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    private static Connection connect() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(
                databaseUrl,
                USER,
                PASS
        );
    }

    private PreparedStatement sqlInsertQueryBuilder(String sql, int listId, Shape shape) throws Exception {
        connection = connect();
        PreparedStatement stmt = connection.prepareStatement(
                sql);
        stmt.setInt(1, listId);
        int count = 2;
        for(HashMap.Entry<String, String> entry : shape.getShapeFields().entrySet()){
            stmt.setDouble(count,shape.getShapeData().getOrDefault(entry.getKey(),0.0));
            count++;
        }

        stmt.executeUpdate();
        return stmt;
    }

    private ResultSet sqlSelectQueryBuilder(String sql, int listId) throws Exception {
        connection = connect();

        PreparedStatement sqlCone = connection.prepareStatement(sql);
        sqlCone.setInt(1, listId);

        return sqlCone.executeQuery();
    }
    public boolean saveList(ArrayList<Shape> shapes, String listName) throws Exception {
        try{
            connection = connect();
            PreparedStatement stmt = connection.prepareStatement(
                    "Insert into vt_shapelist(name) values(?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, listName);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                for (Shape shape : shapes) {
                    switch (shape.shapeType){
                        case "Cone":
                            this.sqlInsertQueryBuilder("Insert into vt_cone(listId, radius, height) values(?,?,?)", rs.getInt(1), shape);
                            break;
                        case "Cube":
                            this.sqlInsertQueryBuilder("Insert into vt_cube(listId, sideLength) values(?,?)", rs.getInt(1), shape);
                            break;
                        case "Cylinder":
                            this.sqlInsertQueryBuilder("Insert into vt_cylinder(listId, height, radius) values(?,?,?)", rs.getInt(1), shape);
                            break;
                        case "Pyramid":
                            this.sqlInsertQueryBuilder("Insert into vt_pyramid(listId, baseWidth, baseLength, height) values(?,?,?,?)", rs.getInt(1), shape);
                            break;
                        case "Sphere":
                            this.sqlInsertQueryBuilder("Insert into vt_sphere(listId, radius) values(?,?)", rs.getInt(1), shape);
                            break;
                        default:
                            return false;
                    }
                }
                return true;
            }

        }catch (Exception ex){
            return false;
        }finally {
            connection.close();
        }
        return false;
    }

    public static boolean checkConnection() {
        try {
            connect();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public ObservableList<ShapeList> loadLists() throws Exception {
        connection = connect();

        final ObservableList<ShapeList> data = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vt_shapelist");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(new ShapeList(resultSet.getInt("id"),resultSet.getString("name")));
            }

            return data;
        }catch (Exception ex){

        }finally {
            connection.close();
        }
        return data;
    }

    public ArrayList<Shape> loadShapesList(int listId) throws Exception {
        connection = connect();

        ArrayList<Shape> shapes = new ArrayList<>();

        try {
            ArrayList<String> shapesNames = new ArrayList<String>();
            shapesNames.add("Cone");
            shapesNames.add("Cube");
            shapesNames.add("Cylinder");
            shapesNames.add("Pyramid");
            shapesNames.add("Sphere");

            for (String shape : shapesNames) {
                switch (shape){
                    case "Cone":
                        ResultSet coneResultSet = sqlSelectQueryBuilder("SELECT * FROM `vt_cone` WHERE `listId` = ?",listId);
                        while (coneResultSet.next()) {
                            shapes.add(new Cone(coneResultSet.getDouble("radius"), coneResultSet.getDouble("height")));
                        }
                        break;
                    case "Cube":
                        ResultSet cubeResultSet = sqlSelectQueryBuilder("SELECT * FROM `vt_cube` WHERE `listId` = ?",listId);
                        while (cubeResultSet.next()) {
                            shapes.add(new Cube(cubeResultSet.getDouble("sideLength")));
                        }
                        break;
                    case "Cylinder":
                        ResultSet cylinderResultSet = sqlSelectQueryBuilder("SELECT * FROM `vt_cylinder` WHERE `listId` = ?",listId);
                        while (cylinderResultSet.next()) {
                            shapes.add(new Cylinder(cylinderResultSet.getDouble("height"),cylinderResultSet.getDouble("radius")));
                        }
                        break;
                    case "Pyramid":
                        ResultSet pyramidResultSet = sqlSelectQueryBuilder("SELECT * FROM `vt_pyramid` WHERE `listId` = ?",listId);
                        while (pyramidResultSet.next()) {
                            shapes.add(new Pyramid(pyramidResultSet.getDouble("baseWidth"),pyramidResultSet.getDouble("baseLength"),pyramidResultSet.getDouble("height")));
                        }
                        break;
                    case "Sphere":
                        ResultSet sphereResultSet = sqlSelectQueryBuilder("SELECT * FROM `vt_sphere` WHERE `listId` = ?",listId);
                        while (sphereResultSet.next()) {
                            shapes.add(new Sphere(sphereResultSet.getDouble("radius")));
                        }
                        break;
                    default:
                }
            }
        }catch (Exception ex){

        }finally {
            connection.close();
        }

        return shapes;
    }
}
