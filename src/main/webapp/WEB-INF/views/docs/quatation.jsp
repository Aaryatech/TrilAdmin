<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Quation PDF</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
table {
	border-collapse: separate;
	border-color: black;
	font-size: 12;
	width: 100%;
	page-break-inside: auto !important;
	min-height: 500px;
}

p {
	color: black;
	font-family: arial;
	font-size: 70%;
	margin-top: 0;
	padding: 0;
	font-weight: bold;
}

.pn {
	color: black;
	font-family: arial;
	font-size: 10%;
	margin-top: 0;
	padding: 0;
	font-weight: normal;
}

h4 {
	color: black;
	font-family: sans-serif;
	font-size: 80%;
	font-weight: bold;
	padding-bottom: 10px;
	margin: 0;
}

h5 {
	color: black;
	font-family: sans-serif;
	font-size: 70%;
	font-weight: normal;
	padding-bottom: 10px;
	margin: 0;
}

h6 {
	color: black;
	font-family: arial;
	font-size: 60%;
	font-weight: normal;
	margin: 10%;
}

th {
	background-color: #6a9ef2;
	color: white;
}

hr {
	height: 1px;
	border: none;
	color: rgb(60, 90, 180);
	background-color: rgb(60, 90, 180);
}

.invoice-box table tr.information table td {
	padding-bottom: 0px;
	align-content: center;
}
</style>

</head>
<body>
	<div align="right">
		<h6> ${documentBean.docIsoSerialNumber}</h6>
	</div>

	<h4 align="center">${company.companyName}</h4>
	<!-- <hr style="height: 1px; border: none; color: black; background-color: black;"> -->

	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>
							<td valign="top" width="50%"> Quation No : ${editEnquiry.enqNo}
							</td>
							 
							<td valign="top" width="50%" align="right"> 
							
							Date : ${editEnquiry.enqDate}

							</td>

							 
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div><br> 
	<!-- <hr
		style="height: 1px; border: none; color: black; background-color: black;"> -->

	<!-- <h5>
		Dear Sirs,<br> Please submit your quotation for the following
		&nbsp;&nbsp;:
	</h5> -->
	<table align="center" border="1" cellspacing="0" cellpadding="1"
		id="table_grid" class="table table-bordered">
		<thead>
			<tr>
				<th width=7%>Sr. No.</th> 
				<th width=48%>Description</th>
				<th width=10%>UOM</th>
				<th width=10%>Quantity</th>
				<!-- <th width=15%>Drg.No. / IND.No.</th> -->

			</tr>
		</thead>
		<tbody>
 
			<c:forEach items="${editEnquiry.quatationDetailList}" var="item" varStatus="count">
				<tr>
					<td width="7%" align="center"><c:out value="${count.index+1}" /></td> 
					<td width="48%" align="left" style="padding: 5px;"><c:out
							value="${item.itemCode}" /></td>
					<td width="10%" align="left" style="padding: 5px;"><c:out
							value="${item.enqUom}" /></td>
					<td width="10%" align="right" style="padding: 5px;"><c:out value="${item.enqQty}" /></td>
					
					<%-- <c:choose>
						<c:when test="${item.indNo==null || item.indNo==''}">
						<td width="15%" align="center"><c:out
							value="-" /></td>
						</c:when>
						<c:otherwise>
						<td width="15%" align="center"><c:out
							value="${item.indNo}" /></td>
						</c:otherwise>
					</c:choose> --%>
					

				</tr>

			</c:forEach>

		</tbody>
	</table>

	<br>
	 
 

</body>
</html>