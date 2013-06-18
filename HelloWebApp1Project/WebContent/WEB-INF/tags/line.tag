<%@tag import="java.util.Map"%>
<%@ tag language="java" pageEncoding="EUC-KR" body-content="empty"%>
<%@ tag dynamic-attributes="attrs" %>
<FONT color=${attrs.color}>
<%
	java.util.Map attrs = (java.util.Map) jspContext.getAttribute("attrs");
	String str = (String)attrs.get("size");
	int size = Integer.parseInt(str);
	for (int cnt = 0; cnt < size; cnt++) {
		out.print("-");
	}
%>
</FONT>
