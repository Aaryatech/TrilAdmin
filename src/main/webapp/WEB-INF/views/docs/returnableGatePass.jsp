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
<title>Non Returnable Gate Pass</title>
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
	display: block;
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

.set-height td {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height t {
	position: relative;
	overflow: hidden;
	height: 2em;
}

.set-height p {
	position: absolute;
	margin: .1em;
	left: 0;
	top: 0;
}
</style>

</head>
<body>


	<c:forEach items="${list}" var="item" varStatus="count">


		<div align="right">
			<h5>COM-F-01 REV.00 DT.01-05-2018</h5>
		</div>

		<h3 align="center">TRAMBAK &nbsp;&nbsp;RUBBER
			&nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h3>
		<p align="center">OUTWARD MATERIAL GATE PASS - RETURNABLE</p>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="2" valign="top">
						<table>
							<tr>
								<td valign="top">GP No. : ${item.gpNo}<br> To,<br>
									${item.vendorName} ,<br> ${item.vendorAdd1}
								</td>

								<td align="right"><br> Date : ${item.gpReturnDate }</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>

<br>
<br>

		<p	style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
			Sending with following materials personally / Vehicle No. - <br>
			as per details given below. <br>

		</p>

<br>


		<table align="center" border="1" cellspacing="0" cellpadding="1"
			id="table_grid" class="table table-bordered">
			<thead>
				<tr>
					<th>SR.</th>
					<th>ITEM NO.</th>
					<th>DESCRIPTION</th>
					<th>UOM</th>
					<th>QTY</th>
					<th>DLV.SCH / REMARK</th>
				</tr>
			</thead>
			<tbody>


				<c:forEach items="${item.gatepassReportDetailList}" var="row"
					varStatus="count">

					<tr>
						<td width="0" align="center"><c:out value="${count.index+1}" /></td>
						<td width="0" align="center"><c:out value="${row.itemCode}" /></td>
						<td width="40%" align="center"><c:out value="${row.itemDesc}" /></td>
						<td width="0" align="center"><c:out value="${row.itemUom}" /></td>
						<td width="0" align="right"><c:out value="${row.gpQty}" /></td>
						<td width="0" align="center"><c:out value="${row.gpReturnDate}" /></td>

					</tr>
				</c:forEach>

			</tbody>
		</table>

		<br>
				
			<p
			style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
			Reason : ${item.remark1} <br>
		</p>
				
		<br>
		<br>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="4" valign="top">
						<table>
							<tr>
								<td width="25" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Receiver Signature</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Prepared By</td>
									
										<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Approved By</td>
										<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Authorised By</td>


							</tr>

						</table>
					</td>
				</tr>
			</table>
			
			<hr	style="height: 1px; border: none; color: black; background-color: black;">
			
			
		</div>
	</c:forEach>

	<!-- END Main Content -->

</body>
</html>