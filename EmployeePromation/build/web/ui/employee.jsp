
<%@page import="beans.PromotionRequest"%>
<%@page import="beans.Employee"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% Employee emp =new Employee();
        PromotionRequest pro = new PromotionRequest();
        if(pageContext.findAttribute("PromotionStatus") != null){
            pro = (PromotionRequest) pageContext.findAttribute("PromotionStatus");
        };
        emp = (Employee)pageContext.findAttribute("employee");
        Integer empId = emp.getEmpId(); %>
        <h3>Hello Mr. <% out.print(emp.getEmpFname()); %> </h3>
        <form action="GoalsAndAchievements" method="post">
            <input type='hidden' name='empId' <% out.print("value='"+empId+"'"); %>/>
            <% if (LocalDateTime.now().getMonthValue() == 1) { %>
                <p>My Goals For This Year</p>
                <textarea name="empGoals"></textarea>
                <input type="submit" value="submit" />
            <% } %>
            <%if (LocalDateTime.now().getMonthValue() == 11) { %>
                <p>My Achievements For This Year</p>
                <textarea name="empAchievements"></textarea>
                <input type="submit" value="submit" />
            <% } if(LocalDateTime.now().getMonthValue() != 11 || LocalDateTime.now().getMonthValue() != 1) { %>
                <p style="color: red;font-weight: bold">There is no Goals And Achievements to insert at this time !!</p>
            <% }%>

        </form>
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
                <input type="number" name="depId" />
                <input value="Send" type="submit" />
        </form>
    </body>
</html>
