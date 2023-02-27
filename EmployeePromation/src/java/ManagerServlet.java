/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Department;
import beans.Employee;
import beans.EmployeeCategory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/ManagerServlet"})
public class ManagerServlet extends HttpServlet {
    
    String empId;
    String empEvaluation;
    Employee empObj ;
    RequestDispatcher dispatcher;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        empEvaluation =  request.getParameter("empEvaluation");
        empId =  request.getParameter("empId");
        try (PrintWriter out = response.getWriter()) {
            if("1".equals(handleEmpEvaluation(empId, empEvaluation))){
                response.sendRedirect("index.html");
            }
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String handleEmpEvaluation(String empId,String empEv) throws SQLException{
        if(getEmpById(empId)){
            Connection con = ConnectionDatabase.getConnections();
            String sql =   "INSERT INTO `evaluation`( `emp_id`, `evaluation_date`, `evaluation`) VALUES ('"+empObj.getEmpId()+"',CURDATE(),'"+empEv+"')";
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();
            if(Integer.parseInt(empEv) >= 60){
                Float performanceIncrease = 0.1f * (Float.parseFloat(empEv)/100) * empObj.getEmpSalary();
                String sql2 = "UPDATE employee set emp_salary = "+(empObj.getEmpSalary()+performanceIncrease)+" where emp_id = "+empObj.getEmpId();
                PreparedStatement statement2 = con.prepareCall(sql2);
                statement2.executeUpdate();
                return "1";
            } else {
                return "2";
            }
        }else{
            return "3";
        }
    }
    
    public boolean getEmpById(String empId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql="SELECT *\n" +
                    "FROM `employee`\n" +
                    "INNER JOIN employeecategory \n" +
                    "ON employee.emp_J_category= employeecategory.category_id\n" +
                    "INNER JOIN departments \n" +
                    "ON employee.emp_dep= departments.dep_id\n" +
                    "WHERE employee.emp_id = '"+this.empId +"'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
                empObj = new Employee();
                Department dep = new Department();
                EmployeeCategory cat = new EmployeeCategory();
                empObj.setEmpId(Integer.parseInt( empId));
                empObj.setEmpFname(resultSet.getString("emp_fname"));
                empObj.setEmpLname(resultSet.getString("emp_lname"));
                cat.setCatName(resultSet.getString("category_name"));
                cat.setCatId(resultSet.getInt("category_id"));
                empObj.setEmpJobCategory(cat);
                empObj.setEmpJobTitle(resultSet.getString("emp_j_title"));
                empObj.setEmpSalary(resultSet.getFloat("emp_salary"));
                empObj.setEmpRank(resultSet.getInt("emp_rank"));
                dep.setDepId(resultSet.getInt("dep_id"));
                dep.setDepName(resultSet.getString("dep_name"));
                empObj.setEmpDepartment(dep);
                return true;
        }
        return false;
    }
}
