<%@tag import="java.util.Map"%>
<%@ tag language="java" pageEncoding="EUC-KR" body-content="empty"
	dynamic-attributes="attrs"%>
<%
	Map attrs = (Map)jspContext.getAttribute("attrs");
	String str = (String)attrs.get("size");
	String[] colors = {"red" , "green" , "blue" };
	int size = Integer.parseInt(str);
	for (int i = 0; i < size; i++) {
%>
		<FONT color="<%= colors[i%colors.length] %>">
<% 
		out.print("=");
%>		
		</FONT>
<%		
	}
%>
