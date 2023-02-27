/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/GoalsAndAchievements"})
public class GoalsAndAchievements extends HttpServlet {
    private String goals;
    private String achievements;
    private String empId;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        achievements =  request.getParameter("empAchievements");
        goals =  request.getParameter("empGoals");
        empId =  request.getParameter("empId");
        try (PrintWriter out = response.getWriter()) {
            if(goals != null){
                sendGoals(goals,empId);
                response.setContentType("text/html;charset=UTF-8");
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Change Department</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>Employee Goals Has been Send </h1>");
                            out.println("</body>");
                            out.println("</html>");
            } else if(achievements != null){
                sendAchievements(achievements,empId);
                response.setContentType("text/html;charset=UTF-8");
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Change Department</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>Employee Achivements Has been Send </h1>");
                            out.println("</body>");
                            out.println("</html>");
                
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GoalsAndAchievements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GoalsAndAchievements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    public void sendGoals(String goals,String empId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql =   "UPDATE `employee` SET `emp_goals`='"+goals+"' WHERE emp_id = "+empId;
        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }
    public void sendAchievements(String Achievements,String empId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql =   "UPDATE `employee` SET `emp_achievements`='"+Achievements+"' WHERE emp_id = "+empId;
        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }
}
