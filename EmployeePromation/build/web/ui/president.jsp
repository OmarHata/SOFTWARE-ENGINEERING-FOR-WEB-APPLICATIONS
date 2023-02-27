
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.PromotionRequest"%>
<%@page import="beans.Employee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        .empTable tr th{
            padding: 0px 10px;
        }
        .empTable td{
            padding: 5px;
            border-bottom:  1px solid black;
        }
    </style>
    <body>
        <%  pageContext.setAttribute("variables", request.getAttribute("employeeList")); 
            pageContext.setAttribute("promotionListVar", request.getAttribute("promotionList")); 
            List<PromotionRequest> promotionSize = new ArrayList();
            promotionSize = (List<PromotionRequest>) request.getAttribute("promotionList");
            Employee emp =new Employee();
            emp = (Employee)pageContext.findAttribute("employee");
            Integer empId = emp.getEmpId();
        PromotionRequest pro = new PromotionRequest();
        if(pageContext.findAttribute("PromotionStatus") != null){
            pro = (PromotionRequest) pageContext.findAttribute("PromotionStatus");
        };%>
        
        <h1>Employee list</h1>
        <table style="text-align: center" class="empTable">
            <th>Emp Id</th>
            <th>Emp F Name</th>
            <th>Emp L Name</th>
            <th>Emp Departmnet </th>
            <th>Emp JobTitle </th>
            <th>Emp Category </th>
            <th>Emp Rank</th>
            <th>Emp Salary</th>
            <c:forEach items="${variables}" var="cat">
                <%
                    for (Employee bean : (List<Employee>) request.getAttribute("employeeList")) {
                        out.println("<tr >");
                        out.println("<td>" + bean.getEmpId() + "</td>");
                        out.println("<td>" + bean.getEmpFname() + "</td>");
                        out.println("<td>" + bean.getEmpLname() + "</td>");
                        out.println("<td>" + bean.getEmpDepartment().getDepName() + "</td>");
                        out.println("<td>" + bean.getEmpJobTitle() + "</td>");
                        out.println("<td>" + bean.getEmpJobCategory().getCatName() + "</td>");
                        out.println("<td>" + bean.getEmpRank() + "</td>");
                        out.println("<td>" + bean.getEmpSalary().toString() + "</td>");
                        out.println("</tr>");
                    }
                %> 
            </c:forEach>
        </table>

        <form action="ManagerServlet" >
            <p>Add Employee the performance evaluation</p>
            <label >Emp Evaluation
                <input type="number" name="empEvaluation" required="true" />
            </label>
            <label>Emp Id
                <input type="number" name="empId" required="true" />
            </label>
            <input type="submit" />
        </form>
            <input id="showBtn" type="button" value="Show Promotions List" onclick="shoDialog()" />   
            
            <div id="promotions" style="display: none;position: fixed ; width: 70%;height: 70vh;top: 50%;
                                        left: 50%;transform: translate(-50%,-50%);background-color: white;border: 1px solid black">
                <button id="closeBtn" >x</button>
                <h1>Promotion List</h1>
                <form action="PromotionsServlet">
                    <table style="text-align: center" class="empTable">
                        <th>Employee Id</th>
                        <th>Promotion Id</th>

                        <c:forEach items="${promotionListVar}" var="var">
                            <%int counter = 0;
                                for (PromotionRequest bean : (List<PromotionRequest>) request.getAttribute("promotionList")) {
                                    
                                    out.println("<tr >");
                                    out.println("<td>" + bean.getEmpId() + "</td>");
                                    out.println("<td>" + bean.getpId() + "</td>");
                                    out.println("</tr>");
                                    counter++;
                                }
                            %> 
                        </c:forEach>
                    </table>
                        <input type='hidden' name='empCatId' <% out.print("value='"+emp.getEmpJobCategory().getCatId()+"'"); %>/>
                        <input type="number" name="empId" required="true" />
                        
                        <input type='radio' required  name="promotionValue" value='accept' />
                        <label>accept</label>
                        
                        <input type='radio' required  name="promotionValue" value='reject' />
                        <label>denied</label>
                        <input type='submit' value="send" />
                </form>
            </div>
                        
        <%if(pro.getStatus() == null){ %>
            <form action="PromotionsServlet" method="post">
                <input type='hidden' name='empId' <% out.print("value='"+empId+"'"); %>/>
                <input value="Send Promotions" type="submit" />
            </form>
        <%}%>
        <%if(pro.getStatus() != null){
            out.print("<p> Youre Status For Promotion is "+pro.getStatus()+" you cannt send more promotions</p>");
        }%>
        
        <form action="ChangeDepartment" method="post">
            <p>if you need to change department</p>
                <input type='hidden' name='empId' <% out.print("value='"+empId+"'"); %>/>
                <input required type="number" name="depId" />
                <input value="cahnge Department" type="submit" />
        </form>
    </body>
    <script>
        var pro = document.getElementById('promotions');
        function shoDialog(){
            pro.style.display = 'block';
        }
        function closeDialog(){
            pro.style.display = 'none';
        }
        var butn = document.getElementById('showBtn').addEventListener("click",shoDialog)
        var closeButn = document.getElementById('closeBtn').addEventListener("click",closeDialog)
    </script>
</html>
