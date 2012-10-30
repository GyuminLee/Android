<?xml version="1.0" encoding="UTF-8"?>
<% String result = (String)request.getAttribute("result"); %>
<% int resultid = -1;
   if ((Integer)request.getAttribute("resultid") != null) {
	   resultid = (Integer)request.getAttribute("resultid");
   }
%>
<rss>
	<channel>
		<result><%= result %></result>
<% 
	if (resultid != -1) {
%>
		<resultid><%= resultid %></resultid>
<%
	}
%>
	</channel>
</rss>