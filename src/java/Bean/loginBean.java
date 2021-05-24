/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;


@ManagedBean
@ApplicationScoped
public class loginBean {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public loginBean() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String checkLogin() {
        if (username.equals("") || password.equals("")) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in all  fields!!", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return "failure";
        }
        try {

            String DB_URL = "jdbc:postgresql://localhost:5432/mydatabase";
            String USER = "postgres";
            String PASS = "rabiarose";

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String Q = "select * from public.Diet";

            pstmt = conn.prepareStatement(Q);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            boolean found = rs.next();

            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }

            if (!found) {
                throw new Exception("You are no autherized to access the system");
            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            
            return "failure";
        }
        return "success";

    }

}
