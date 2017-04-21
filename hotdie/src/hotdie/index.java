package hotdie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class index extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement stat;
    Statement stat2;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String sql = "SELECT title FROM word";
    String sql2 = "SELECT title FROM item";

    public void init() throws ServletException {// 初始化代码仅进行一次的数据库连接操作
        mysqlUser.User();
        try {
            //System.out.println(mysqlUser.url+"?user="+mysqlUser.user+"&password="+mysqlUser.password+"&useUnicode=true&characterEncoding=UTF-8");
            Class.forName(mysqlUser.driver);
            con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
            //con = DriverManager.getConnection(mysqlUser.url+"?user="+mysqlUser.user+"&password="+mysqlUser.password+"&useUnicode=true&characterEncoding=UTF-8");
            //String url="jdbc:mysql://lvsijian.cn:3306/hotdie?user="+mysqlUser.user+"&password="+mysqlUser.password+"&useUnicode=true&characterEncoding=UTF-8";
            //con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String word[] = new String[50];
        String item[] = new String[50];
        int mark = 0;
        int mark1 = 0;
        try {
            if (mysqlUser.checkConnection(con) || con == null || con.isClosed())//检查链接是否正常
                con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
            stat = con.createStatement();
            stat2 = con.createStatement();
            rs = stat.executeQuery(sql);
            rs2 = stat2.executeQuery(sql2);
            while (rs.next()) {
                word[mark++] = "" + rs.getString("title");    //把查询到的"user_password"值给user_password11
            }

            while (rs2.next()) {
                item[mark1++] = "" + rs2.getString("title");    //把查询到的"user_password"值给user_password11
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("num", mark);
        for (int i = mark; i >= 0; i--) {
            request.setAttribute("word" + i, word[i]);
        }
        request.setAttribute("num2", mark1);
        for (int i = mark1; i >= 0; i--) {
            request.setAttribute("item" + i, item[i]);
        }
        request.getRequestDispatcher("index1.jsp").forward(request, response);
    }

    public void destroy() {// 终止化代码,关闭数据库连接
        try {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            if (rs2 != null)
                rs2.close();
            if (stat2 != null)
                stat2.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
