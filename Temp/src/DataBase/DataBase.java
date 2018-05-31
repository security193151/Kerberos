package DataBase;

import java.io.IOException;
import java.sql.*;

public class DataBase {

	public static String search(String ID)throws IOException, SQLException{
		String user_pwd=null;
        // 驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";

        // URL指向要访问的数据库名db
        String url = "jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";

        // MySQL配置时的用户名
        String user = "root"; 

        // MySQL配置时的密码
        String password = "123456";

        try { 
         // 加载驱动程序
         Class.forName(driver);

         // 连续数据库
         Connection conn = DriverManager.getConnection(url, user, password);

         if(!conn.isClosed()) 
          System.out.println("Succeeded connecting to the Database!");

         // statement用来执行SQL语句
         Statement statement = conn.createStatement();

         // 要执行的SQL语句
 		StringBuilder sql_builder = new StringBuilder("select * from tb where id='");
 		sql_builder.append(ID);
 		sql_builder.append("'");
 		String sql = sql_builder.toString();
 	//	System.out.println(sql);
         // 结果集
         ResultSet rs = statement.executeQuery(sql);
         while(rs.next()) {
          user_pwd= rs.getString("pwd");
          // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
          // 然后使用GB2312字符集解码指定的字节数组
          user_pwd = new String(user_pwd.getBytes("ISO-8859-1"),"GB2312");
         }
         rs.close();
         conn.close();
        } catch(ClassNotFoundException e) {
         System.out.println("Sorry,can`t find the Driver!"); 
         e.printStackTrace();
        } catch(SQLException e) {
         e.printStackTrace();
        } catch(Exception e) {
         e.printStackTrace();
        }
		return user_pwd; 
} 
}