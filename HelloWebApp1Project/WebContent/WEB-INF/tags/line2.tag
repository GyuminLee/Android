<%@ tag language="java" pageEncoding="EUC-KR" body-content="empty"%>
<%@ attribute name="size" type="java.lang.Integer" required="true"%>
<%@ attribute name="color" type="java.lang.String" required="true"%>
<FONT color="${color}">
<%
	for (int i = 0; i < size; i++) {
		out.print("=");
	}
%>
</FONT>