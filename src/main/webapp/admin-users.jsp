<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Users - Deepa Mart</title>

<style>
*{box-sizing:border-box;}

body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
}

.header{
    background:#232f3e;
    color:white;
    padding:18px 30px;
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.logo{
    font-size:28px;
    font-weight:bold;
}

.back{
    background:white;
    color:#232f3e;
    padding:10px 18px;
    border-radius:20px;
    text-decoration:none;
    font-weight:bold;
}

.main{
    max-width:1000px;
    margin:auto;
    padding:30px;
}

h1{
    color:#222;
}

.table-box{
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 2px 8px #ccc;
}

table{
    width:100%;
    border-collapse:collapse;
}

th,td{
    padding:14px;
    text-align:left;
    border-bottom:1px solid #ddd;
}

th{
    background:#232f3e;
    color:white;
}

.role{
    font-weight:bold;
}

.empty{
    text-align:center;
    padding:30px;
}
</style>
</head>

<body>

<div class="header">
    <div class="logo">🛒 Deepa Mart</div>
    <a href="admin-dashboard.jsp" class="back">← Dashboard</a>
</div>

<div class="main">

<h1>👥 Manage Users</h1>

<div class="table-box">

<%
List<Map<String,Object>> users =
    (List<Map<String,Object>>) request.getAttribute("users");

if(users == null || users.isEmpty()){
%>

<div class="empty">
    <h2>No users found</h2>
</div>

<%
}else{
%>

<table>

<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Role</th>
</tr>

<%
for(Map<String,Object> user : users){
%>

<tr>
    <td><%=user.get("id")%></td>
    <td><%=esc(String.valueOf(user.get("name")))%></td>
   <td class="role"><%=user.get("role")%></td>
</tr>

<%
}
%>

</table>

<%
}
%>

</div>

</div>

</body>
<%!
public String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#39;");
}
%>
</html>