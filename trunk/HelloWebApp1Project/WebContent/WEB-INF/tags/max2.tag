<%@ tag language="java" pageEncoding="EUC-KR" body-content="empty"%>
<%@ attribute name="num1" type="java.lang.Integer" required="true"%>
<%@ attribute name="num2" type="java.lang.Integer" required="true"%>
<%@ variable  name-given="maximum" variable-class="java.lang.Integer" scope="AT_END"%>
<%
	if (num1 > num2) {
		jspContext.setAttribute("maximum", num1);
	} else {
		jspContext.setAttribute("maximum", num2);
	}
%>