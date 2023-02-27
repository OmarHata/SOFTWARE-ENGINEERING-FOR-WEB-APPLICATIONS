/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Department;
import beans.Employee;
import beans.EmployeeCategory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet(urlPatterns = {"/PromotionsServlet"})
public class PromotionsServlet extends HttpServlet {

    String empId;
    Employee empObj;
    String pomotionValu;
    Integer catId;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        empId =  request.getParameter("empId");
        if(getEmpById(empId)){
            if(request.getParameter("promotionValue") != null){
                pomotionValu = request.getParameter("promotionValue");
                catId = Integer.parseInt(request.getParameter("empCatId"));
                hadelPromotion(empId,pomotionValu,catId);
                response.sendRedirect("index.html");
            }else{
                handelePromotion(empId);
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
            Logger.getLogger(PromotionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PromotionsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
     
    public void handelePromotion(String empId) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql = "INSERT INTO `promotion_req`( `emp_id`, `old_rank`, `approve_status`,`dep_id`) "
                 + "VALUES ('"+empId+"','"+empObj.getEmpRank()+"','Draft',"+empObj.getEmpDepartment().getDepId()+")";
        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }
    
    
    public void hadelPromotion(String empId , String pomotionValu ,int empCate) throws SQLException{
        Connection con = ConnectionDatabase.getConnections();
        String sql = new String();
        if(pomotionValu.equals("accept")){
            switch(empCate){
                case 1:
                    sql = "update promotion_req set promotion_req.director_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Process' WHERE emp_id = "+ empId;
                    break;
                case 2:
                    sql = "update promotion_req set promotion_req.manager_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Process' WHERE emp_id = "+ empId;
                    break;
                case 4:
                    sql = "update promotion_req set promotion_req.vp_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Process' WHERE emp_id = "+ empId;
                    break;
                case 5:            
                    sql = "update promotion_req set promotion_req.presedient_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Process' WHERE emp_id = "+ empId;
                    break;
            }
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();
        } else if(pomotionValu.equals("reject")){
            
            switch(empCate){
                case 1:
                    sql = "update promotion_req set promotion_req.director_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Declined' WHERE emp_id = "+ empId;
                    break;
                case 2:
                    sql = "update promotion_req set promotion_req.manager_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Declined' WHERE emp_id = "+ empId;
                    break;
                case 4:
                    sql = "update promotion_req set promotion_req.vp_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Declined' WHERE emp_id = "+ empId;
                    break;
                case 5:            
                    sql = "update promotion_req set promotion_req.presedient_approve = '"+pomotionValu+"' , promotion_req.approve_status = 'In Declined' WHERE emp_id = "+ empId;
                    break;
            }
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();
        }
    }
}
