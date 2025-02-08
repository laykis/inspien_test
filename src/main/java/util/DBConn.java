package util;

import com.example.generated.MTRecruitingTestServicesResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {

    public static Connection getConnection(MTRecruitingTestServicesResponse.DBCONNINFO info) throws ClassNotFoundException {

        String host = info.getHOST();
        String port = info.getPORT();
        String sid = info.getSID();
        String user = info.getUSER();
        String password = info.getPASSWORD();

        String jdbcUrl = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        Connection conn = null;

        try{
            Class.forName("oracle.jdbc.OracleDriver");

            conn = DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }
}
