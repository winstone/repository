<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String parentid=(String)request.getParameter("parentid");
       String diagramname=(String)request.getParameter("diagramname");
       String userName=(String)session.getAttribute("userName");
//	   String userName="user1";
	   long id=Data.getInstance().createPath(Long.parseLong(parentid),diagramname,1,userName);
	   
	   if(id==-1)
	   {
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	    
	   }
	   else
	   {
	     rp="<return status=\"true\"><id>"+id+"</id></return>";
	   }
	   
	out.print(rp);

 %>
