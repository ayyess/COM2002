package uk.ac.shef.com2002.grp4.databases;

import java.sql.*;

/**
 * <Doc here>
 * <p/>
 * Created on 04/11/2016.
 */
public class ConnectionManager{
    private static <RETURN> RETURN withConnection(With<Connection,RETURN,SQLException> with){
        //try with resources will auto close the connection
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac")) {
            return with.with(conn);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <RETURN> RETURN withTransaction(With<Connection,RETURN,SQLException> with){
        return withConnection((conn)-> {
            try {
                conn.setAutoCommit(false);
                RETURN ret = with.with(conn);
                conn.commit();
                return ret;
            }catch(SQLException ex){
                //if we fail, rollback, then rethrow so withConnection catches it
                conn.rollback();
                throw ex;
            }
        });
    }

    public static <RETURN> RETURN withStatement(String sql, With<PreparedStatement,RETURN,SQLException> with){
        return withConnection((conn)-> {
            //try with resources will auto close the statement
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                return with.with(stmt);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        });
    }
}

interface With<T,R,EXCEPTION_TYPE extends Exception >{
    R with(T t) throws EXCEPTION_TYPE;
}