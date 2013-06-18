<%@ tag language="java" pageEncoding="EUC-KR" body-content="empty"%>
<%@ attribute name="num1" type="java.lang.Integer" required="true" %>
<%@ attribute name="num2" type="java.lang.Integer" required="true" %>
<%@ variable name-given="maximum" variable-class="java.lang.Integer" scope="AT_END"%>
<%
	int result;
	if (num1 > num2) {
		result = num1;
	} else {
		result = num2;
	}
	jspContext.setAttribute("maximum", (Integer)result);
%>