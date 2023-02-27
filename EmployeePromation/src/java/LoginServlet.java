/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import beans.Department;
import beans.Employee;
import beans.EmployeeCategory;
import beans.PromotionRequest;
import beans.Transferred;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    public List<Employee> employeeList;
    public List<PromotionRequest> promotionsList;
    public List<Transferred> transferredOutList;
    public List<Transferred> transferredInList;

    String userName;
    String password;
    Employee empObj = new Employee();
    PromotionRequest pro = new PromotionRequest();
    RequestDispatcher dispatcher;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        userName = request.getParameter("username").trim();
        password = request.getParameter("password").trim();
        employeeList = new ArrayList<>();
        promotionsList = new ArrayList<>();
        transferredOutList = new ArrayList<>();
        transferredInList = new ArrayList<>();

        if (this.checkUserLogin(userName, password)) {
            if (empObj.getEmpJobCategory().getCatId() == 2 || empObj.getEmpJobCategory().getCatId() == 1 || empObj.getEmpJobCategory().getCatId() == 4 || empObj.getEmpJobCategory().getCatId() == 5) {
                getAllEmployeeByDepNo(empObj.getEmpDepartment().getDepId());
                request.setAttribute("employeeList", employeeList);
                request.setAttribute("promotionList", promotionsList);
                request.setAttribute("employee", empObj);
                switch (empObj.getEmpJobCategory().getCatId()) {
                    case 1:
                        getAllPromotionReq(empObj.getEmpDepartment().getDepId(), "director_approve");
                        getAllOutTranferredReq(empObj.getEmpDepartment().getDepId(), "director_one_answer");
                        getAllInTranferredReq(empObj.getEmpDepartment().getDepId(), "director_two_answer");
                        request.setAttribute("transferredList", transferredOutList);
                        request.setAttribute("transferredInList", transferredInList);
                        if (checkEmpPromotionStatus(empObj.getEmpId())) {
                            request.setAttribute("PromotionStatus", pro);
                        } else {
                            request.setAttribute("PromotionStatus", null);
                        }
                        dispatcher = request.getRequestDispatcher("ui/director.jsp");
                        dispatcher.forward(request, response);
                        break;
                    case 2:
                        getAllPromotionReq(empObj.getEmpDepartment().getDepId(), "manager_approve");
                        getAllOutTranferredReq(empObj.getEmpDepartment().getDepId(), "manager_answer");
                        request.setAttribute("transferredList", transferredOutList);
                        if (checkEmpPromotionStatus(empObj.getEmpId())) {
                            request.setAttribute("PromotionStatus", pro);
                        } else {
                            request.setAttribute("PromotionStatus", null);
                        }
                        dispatcher = request.getRequestDispatcher("ui/manager.jsp");
                        dispatcher.forward(request, response);
                        break;
                    case 4:
                        getAllPromotionReq(empObj.getEmpDepartment().getDepId(), "vp_approve");
                        getAllOutTranferredReq(empObj.getEmpDepartment().getDepId(), "vp_answer");
                        request.setAttribute("transferredList", transferredOutList);
                        if (checkEmpPromotionStatus(empObj.getEmpId())) {
                            request.setAttribute("PromotionStatus", pro);
                        } else {
                            request.setAttribute("PromotionStatus", null);
                        }

                        dispatcher = request.getRequestDispatcher("ui/vice president.jsp");
                        dispatcher.forward(request, response);
                        break;
                    case 5:
                        getAllPromotionReq(empObj.getEmpDepartment().getDepId(), "presedient_approve");
                        checkEmpPromotionStatus(empObj.getEmpId());
                        dispatcher = request.getRequestDispatcher("ui/president.jsp");
                        dispatcher.forward(request, response);
                        break;
                }
            } else if (empObj.getEmpJobCategory().getCatId() == 3) {
                if (checkEmpPromotionStatus(empObj.getEmpId())) {
                    request.setAttribute("PromotionStatus", pro);
                    if (pro.getManagerApp().equals("accept") && pro.getDirectorApp().equals("accept")
                            && pro.getVpApp().equals("accept") && pro.getPresedentApp().equals("accept")) {
                        int oldRank = pro.getOldRank();
                        Connection cons = ConnectionDatabase.getConnections();
                        if (oldRank == 5) {
                            Connection con = ConnectionDatabase.getConnections();
                            String sql = null;
                            switch (empObj.getEmpJobCategory().getCatId()) {
                                case 1:
                                    sql = "UPDATE `employee` SET `emp_rank`=" + 1 + " ,`emp_J_category`= " + 4;
                                    cahnageEmpCategory();
                                    break;
                                case 2:
                                    sql = "UPDATE `employee` SET `emp_rank`=" + 1 + " ,`emp_J_category`= " + 1;
                                    cahnageEmpCategory();
                                    break;
                                case 3:
                                    sql = "UPDATE `employee` SET `emp_rank`=" + 1 + " ,`emp_J_category`= " + 2;
                                    cahnageEmpCategory();
                                    break;
                            }
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                        } else {
                            Connection con = ConnectionDatabase.getConnections();
                            String sql = "UPDATE `employee` SET `emp_rank` = " + (oldRank + 1);
                            PreparedStatement statement = con.prepareCall(sql);
                            statement.executeUpdate();
                        }
                        String sql = "DELETE FROM `promotion_req` WHERE id=" + pro.getpId();
                        PreparedStatement statement = cons.prepareCall(sql);
                        statement.executeUpdate();
                    }
                } else {
                    request.setAttribute("PromotionStatus", null);
                }
                request.setAttribute("employee", empObj);
                dispatcher = request.getRequestDispatcher("ui/employee.jsp");
                dispatcher.forward(request, response);
            }

        } else {
            response.setContentType("text/html;charset=UTF-8");
            /* TODO output your page here. You may use following sample code. */
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Change Department</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Username or Password incorrect </h1>");
                out.println("</body>");
                out.println("</html>");
            }
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

    public void getAllEmployeeByDepNo(int depNo) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT *\n"
                + "FROM `employee`\n"
                + "INNER JOIN employeecategory\n"
                + "ON employee.emp_J_category= employeecategory.category_id\n"
                + "INNER JOIN departments \n"
                + "ON employee.emp_dep= departments.dep_id\n"
                + "where departments.dep_id = " + depNo;
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Employee empObj = new Employee();
            Department dep = new Department();
            EmployeeCategory cat = new EmployeeCategory();
            empObj.setEmpId(resultSet.getInt("emp_id"));
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

            employeeList.add(empObj);

        }
    }

    public boolean checkUserLogin(String username, String password) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT *\n"
                + "FROM `employee`\n"
                + "INNER JOIN employeecategory \n"
                + "ON employee.emp_J_category= employeecategory.category_id\n"
                + "INNER JOIN departments \n"
                + "ON employee.emp_dep= departments.dep_id\n"
                + "WHERE employee.login_username = '" + this.userName + "' " + "AND employee.login_password = '" + this.password + "'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Department dep = new Department();
            EmployeeCategory cat = new EmployeeCategory();
            empObj.setEmpId(resultSet.getInt("emp_id"));
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

    public boolean checkEmpPromotionStatus(int empId) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM promotion_req WHERE emp_Id = " + empId;
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            pro.setStatus(resultSet.getString("approve_status"));
            pro.setpId(resultSet.getInt("id"));
            pro.setManagerApp(resultSet.getString("manager_approve"));
            pro.setDirectorApp(resultSet.getString("director_approve"));
            pro.setVpApp(resultSet.getString("vp_approve"));
            pro.setPresedentApp(resultSet.getString("presedient_approve"));
            pro.setOldRank(resultSet.getInt("old_rank"));
            return true;
        }
        return false;
    }

    public void getAllPromotionReq(int depNo, String approviePersonName) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM promotion_req WHERE dep_id = " + depNo + " AND ISNULL(" + approviePersonName + ") AND approve_status != 'In Declined'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            PromotionRequest pro = new PromotionRequest();
            pro.setpId(resultSet.getInt("id"));
            pro.setEmpId(resultSet.getInt("emp_id"));
            pro.setDepId(resultSet.getInt("dep_id"));
            pro.setOldRank(resultSet.getInt("old_rank"));
            pro.setNewRank(resultSet.getInt("new_rank"));
            pro.setManagerApp(resultSet.getString("manager_approve"));
            pro.setDirectorApp(resultSet.getString("director_approve"));
            pro.setVpApp(resultSet.getString("vp_approve"));
            pro.setPresedentApp(resultSet.getString("presedient_approve"));
            pro.setStatus(resultSet.getString("approve_status"));
            this.promotionsList.add(pro);

        }
    }

    public void cahnageEmpCategory() throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = null;
        switch (empObj.getEmpJobCategory().getCatId()) {
            case 1:
                sql = "UPDATE `promotion_req` SET `approve_status` ='' WHERE id = " + pro.getpId();
                break;
            case 2:
                sql = "UPDATE `promotion_req` SET `approve_status` ='' WHERE id = " + pro.getpId();
                break;
            case 3:
                sql = "UPDATE `promotion_req` SET `approve_status` ='' WHERE id = " + pro.getpId();
                break;
        }

        PreparedStatement statement = con.prepareCall(sql);
        statement.executeUpdate();
    }

    private void getAllOutTranferredReq(int depNo, String transfeerdPersonName) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM transferred_req WHERE from_dp_id = " + depNo + " AND ISNULL(" + transfeerdPersonName + ") AND transfeerd_status != 'In Declined'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Transferred trans = new Transferred();
            trans.setId(resultSet.getInt("id"));
            trans.setManagerAnswer(resultSet.getString("manager_answer"));
            trans.setDirectorOneAnswer(resultSet.getString("director_one_answer"));
            trans.setDirectorTwoAnswer(resultSet.getString("director_two_answer"));
            trans.setVpAnswer(resultSet.getString("vp_answer"));
            trans.setTransfeerdStatus(resultSet.getString("transfeerd_status"));
            trans.setFromDpId(resultSet.getInt("from_dp_id"));
            trans.setToDepId(resultSet.getInt("to_dep_id"));
            trans.setEmpId(resultSet.getInt("emp_id"));
            this.transferredOutList.add(trans);

        }
    }

    private void getAllInTranferredReq(int depNo, String transfeerdPersonName) throws SQLException {
        Connection con = ConnectionDatabase.getConnections();
        String sql = "SELECT * FROM transferred_req WHERE to_dep_id = " + depNo + " AND ISNULL(" + transfeerdPersonName + ") AND transfeerd_status != 'In Declined'";
        PreparedStatement statement = con.prepareCall(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Transferred trans = new Transferred();
            trans.setId(resultSet.getInt("id"));
            trans.setManagerAnswer(resultSet.getString("manager_answer"));
            trans.setDirectorOneAnswer(resultSet.getString("director_one_answer"));
            trans.setDirectorTwoAnswer(resultSet.getString("director_two_answer"));
            trans.setVpAnswer(resultSet.getString("vp_answer"));
            trans.setTransfeerdStatus(resultSet.getString("transfeerd_status"));
            trans.setFromDpId(resultSet.getInt("from_dp_id"));
            trans.setToDepId(resultSet.getInt("to_dep_id"));
            trans.setEmpId(resultSet.getInt("emp_id"));
            this.transferredInList.add(trans);

        }
    }
}
