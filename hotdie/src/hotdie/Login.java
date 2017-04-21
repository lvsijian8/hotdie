package hotdie;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement stat;
    ResultSet rs = null;

    public void init() throws ServletException {// 初始化代码仅进行一次的数据库连接操作
        mysqlUser.User();
        try {
            Class.forName(mysqlUser.driver);
            con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        String password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
        boolean mark = false;
        String user_password11 = "";
        String sql = "SELECT user_password FROM user WHERE user_name='" + username + "';";    //从user表里查询列名为"user_password"的值
        try {
            if (mysqlUser.checkConnection(con) || con == null || con.isClosed())//检查链接是否正常
                con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                user_password11 = "" + rs.getString("user_password");    //把查询到的"user_password"值给user_password11
            }
            if (user_password11.equals(password)) {
                mark = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mark) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String username1 = "登录成功";
            String docType = "<!DOCTYPE html> \n";
            out.println(docType +
                    "<html>\n" +
                    "<head><username>" + "</username></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<br><br><h1 align=\"center\">" + username1 + "</h1>\n" +
                    "<br><div  style=\"text-align:center;\"><a href=\"index.jsp\">点击首页</a></div>" +
                    "</body></html>");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String username1 = "密码错误，请重新登陆";
            String docType = "<!DOCTYPE html> \n";
            out.println(docType +
                    "<html>\n" +
                    "<head><username>" + "</username></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<br><br><h1 align=\"center\">" + username1 + "</h1>\n" +
                    "<br><div  style=\"text-align:center;\"><a href=\"login.html\">点击返回登录页面</a></div>" +
                    "</body></html>");

        }

    }

    public void destroy() {// 终止化代码,关闭数据库连接
        mysqlUser.over(rs,stat,con);
    }
}
