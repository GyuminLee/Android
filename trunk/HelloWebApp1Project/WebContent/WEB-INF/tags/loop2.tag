<%@ tag language="java" pageEncoding="EUC-KR" body-content="scriptless"%>
<%@ attribute name="start" type="java.lang.Integer" %>
<%@ attribute name="end" type="java.lang.Integer" %>
<%@ attribute name="var" required="true" rtexprvalue="false"%>
<%@ variable name-from-attribute="var" alias="number" variable-class="java.lang.Integer" scope="NESTED"%>
<% for(int i = start; i <= end; i++) {

	jspContext.setAttribute("number", i);
%>
	<jsp:doBody />
<%
   }
%>