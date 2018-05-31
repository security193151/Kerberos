package DataBase;

import java.io.IOException;
import java.sql.*;

public class DataBase {

	public static String search(String ID)throws IOException, SQLException{
		String user_pwd=null;
        // ����������
        String driver = "com.mysql.cj.jdbc.Driver";

        // URLָ��Ҫ���ʵ����ݿ���db
        String url = "jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";

        // MySQL����ʱ���û���
        String user = "root"; 

        // MySQL����ʱ������
        String password = "123456";

        try { 
         // ������������
         Class.forName(driver);

         // �������ݿ�
         Connection conn = DriverManager.getConnection(url, user, password);

         if(!conn.isClosed()) 
          System.out.println("Succeeded connecting to the Database!");

         // statement����ִ��SQL���
         Statement statement = conn.createStatement();

         // Ҫִ�е�SQL���
 		StringBuilder sql_builder = new StringBuilder("select * from tb where id='");
 		sql_builder.append(ID);
 		sql_builder.append("'");
 		String sql = sql_builder.toString();
 	//	System.out.println(sql);
         // �����
         ResultSet rs = statement.executeQuery(sql);
         while(rs.next()) {
          user_pwd= rs.getString("pwd");
          // ����ʹ��ISO-8859-1�ַ�����name����Ϊ�ֽ����в�������洢�µ��ֽ������С�
          // Ȼ��ʹ��GB2312�ַ�������ָ�����ֽ�����
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