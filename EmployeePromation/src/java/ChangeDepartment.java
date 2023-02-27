/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Department;
import beans.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.EmployeeCategory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/ChangeDepartment"})
public class ChangeDepartment extends HttpServlet {

    String depId;
    Employee empObj = null;
    String empId;
    String empCatId;
    String transferredValue;
    String sendFrom;
    Integer transfeerdId ,newDepartment ,transfeerdEmpId;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        depId = request.getParameter("depId");
        empId = request.getParameter("empId");
        empCatId = request.getParameter("empCatId");
        transferredValue = request.getParameter("transferredValue");
        sendFrom = request.getParameter("sendFrom");
        if (getEmpById(empId)) {
            Connection con = ConnectionDatabase.getConnections();
            String sql = null;
            
            switch (empCatId) {
                case "1":
                    if (transferredValue.equals("accept")) {
                        if (sendFrom.equals("director_two_answer")) {
                            sql = "UPDATE `transferred_req` SET "
                                    + "`director_two_answer`='accept',"
                                    + "`transfeerd_status`='In Process'"
                                    + " WHERE emp_id = " + empId;
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                            if(checkCahngeDepartment()){
                                updateTransfeerdReq(transfeerdId);
                                updateEmployeeDep(newDepartment,transfeerdEmpId) ;
                            }
                        } else if (sendFrom.equals("director_one_answer")) {
                            sql = "UPDATE `transferred_req` SET "
                                    + "`director_one_answer`='accept',"
                                    + "`transfeerd_status`='In Process'"
                                    + " WHERE emp_id = " + empId;
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                            if(checkCahngeDepartment()){
                                updateTransfeerdReq(transfeerdId);
                                updateEmployeeDep(newDepartment,transfeerdEmpId) ;
                            }
                        }

                    } else if (transferredValue.equals("reject")) {
                        if (sendFrom.equals("director_two_answer")) {
                            sql = "UPDATE `transferred_req` SET "
                                    + "`director_two_answer`='reject',"
                                    + "`transfeerd_status`='Declined'"
                                    + " WHERE emp_id = " + empId;
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                        } else if (sendFrom.equals("director_one_answer")) {
                            sql = "UPDATE `transferred_req` SET "
                                    + "`director_one_answer`='reject',"
                                    + "`transfeerd_status`='Declined'"
                                    + " WHERE emp_id = " + empId;
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                        }
                    }
                    break;
                case "2":
                    if (transferredValue.equals("accept")) {
                        sql = "UPDATE `transferred_req` SET "
                                + "`manager_answer`='accept',"
                                + "`transfeerd_status`='In Process'"
                                + " WHERE emp_id = " + empId;
                        PreparedStatement statement = con.prepareCall(sql);
                        statement.executeUpdate();
                       if(checkCahngeDepartment()){
                            updateTransfeerdReq(transfeerdId);
                            updateEmployeeDep(newDepartment,transfeerdEmpId) ;
                        }
                    } else if (transferredValue.equals("reject")) {
                        sql = "UPDATE `transferred_req` SET "
                                + "`manager_answer`='reject',"
                                + "`transfeerd_status`='Declined'"
                                + " WHERE emp_id = " + empId;
                        PreparedStatement statement = con.prepareCall(sql);
                        statement.executeUpdate();
                    }
                    break;
                case "3":
                    if (getDepById(depId)) {
                        SetToTransfer();
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Change Department</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>The Change Department Has been Send </h1>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    } else {
                        response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
                            /* TODO output your page here. You may use following sample code. */
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Change Department</title>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>No Changes has Been sent Wrong  Department id </h1>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    }
                    break;
                    case "4":
                    if (transferredValue.equals("accept")) {
                        sql = "UPDATE `transferred_req` SET "
                                + "`vp_answer`='accept',"
                                + "`transfeerd_status`='In Process'"
                                + " WHERE emp_id = " + empId;
                        PreparedStatement statement = con.prepareCall(sql);
                        statement.executeUpdate();
                        if(checkCahngeDepartment()){
                            updateTransfeerdReq(transfeerdId);
                            updateEmployeeDep(newDepartment,transfeerdEmpId) ;
                        }
                        
                    } else if (transferredValue.equals("reject")) {
                        sql = "UPDATE `transferred_req` SET "
                                + "`vp_answer`='reject',"
                                + "`transfeerd_status`='Declined'"
                                + " WHERE emp_id = " + empId;
                        PreparedStatement statement = con.prepareCall(sql);
                        statement.executeUpdate();
                    }
                    break;
            }
            response.sendRedirect("index.html");
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getEmpById(String empId) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT *\n"
                + "FROM `employee`\n"
                + "INNER JOIN employeecategory \n"
                + "ON employee.emp_J_category= employeecategory.category_id\n"
                + "INNER JOIN departments \n"
                + "ON employee.emp_dep= departments.dep_id\n"
                + "WHERE employee.emp_id = '" + this.empId + "'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            empObj = new Employee();
            Department dep = new Department();
            EmployeeCategory cat = new EmployeeCategory();
            empObj.setEmpId(Integer.parseInt(empId));
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

    public boolean getDepById(String depId) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM `departments` WHERE dep_id = " + depId;;
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Department dep = new Department();
            dep.setDepId(resultSet.getInt("dep_id"));
            dep.setDepName(resultSet.getString("dep_name"));;
            return true;
        }
        return false;
    }

    public void SetToTransfer() throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "INSERT INTO `transferred_req`(`transfeerd_status`, `from_dp_id`, `to_dep_id`, `emp_id`) "
                + "VALUES ('Draft','" + empObj.getEmpDepartment().getDepId() + "','" + depId + "','" + empObj.getEmpId() + "')";
        PreparedStatement statment = con.prepareStatement(sql);
        statment.executeUpdate();
    }
    
    

    
    public boolean checkCahngeDepartment() throws SQLException{
        
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM transferred_req WHERE manager_answer = 'accept' "
                + "AND director_one_answer = 'accept'"
                + " AND director_two_answer = 'accept' "
                + "AND vp_answer = 'accept' "; 
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            transfeerdId = resultSet.getInt("id");
            newDepartment = resultSet.getInt("to_dep_id");
            transfeerdEmpId = resultSet.getInt("emp_id");
            return true;
        }
        return false;
    }
    
    public void updateTransfeerdReq(int transfeerdId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql = "UPDATE `transferred_req` SET  transfeerd_status = 'Approved' WHERE id = "+ transfeerdId; 
        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }
    
    public void updateEmployeeDep(int newDepartment , int transfeerdEmpId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql = "UPDATE `employee`  SET  emp_dep = "+newDepartment+"  WHERE   emp_id = "+transfeerdEmpId; 
        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }
}
