<%@ tag language="java" pageEncoding="EUC-KR" body-content="scriptless"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%>
<%@ attribute name="start" type="java.lang.Integer" required="true"%>
<%@ attribute name="end" type="java.lang.Integer" required="true"%>
<%@ variable name-from-attribute="var" alias="number" variable-class="java.lang.Integer" scope="NESTED"%>
<% for (int cnt = start; cnt <= end; cnt++) {
	jspContext.setAttribute("number", (Integer)cnt); %>
	<jsp:doBody />
<% } %>

