<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/xml; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<result>
	<code><%= request.getAttribute("CODE") %></code>
	<message><%= request.getAttribute("MESSAGE") %></message>
</result>