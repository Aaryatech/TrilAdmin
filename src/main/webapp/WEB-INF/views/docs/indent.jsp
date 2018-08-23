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
<title>Indent PDF</title>
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


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="1" valign="top">
						<table>
							<tr>
								<td align="right">COM-F-01 REV.00 DT.01-05-2018<br>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>


		<h4 align="center">TRAMBAK &nbsp;&nbsp;RUBBER
			&nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h4>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" >
				<tr class="information">
					<td colspan="3" valign="top">
						<table>
							<tr>

<td width="20%"></td>

								<td width="60%" valign="top" align="center" 
									style="font-weight: bold; ">&nbsp;&nbsp;&nbsp;&nbsp;PUR. REQUISITION / INDENT</td>


							 	<td width="20%" align="left">Indent No.&nbsp;&nbsp;:
									${item.indMNo}<br> Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
									${item.indMDate}
								</td> 
								
								
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>



		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="1" valign="top">
						<table>
							<tr>
								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">Indenting
									Dept. &nbsp;&nbsp;&nbsp;&nbsp;- ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ${item.accHeadDesc} <br> For
									Department &nbsp;&nbsp;&nbsp;- <br> Indent Type
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
								</td>

								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">

									Department &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;- <br> Sub department
									&nbsp;&nbsp;&nbsp; &nbsp;- <br> Monthly &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;	-



								</td>
 
							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div>


		<br>


		<table align="center" border="1" cellspacing="0" cellpadding="1"
			id="table_grid" class="table table-bordered">
			<thead>
				<tr>
					<th>Sr.</th>

					<th width="40%">Description</th>
					<th>UOM</th>
					<th>Qty. Req.</th>
					<th>When Req. / Stock/ Avg.</th>

				</tr>
			</thead>
			<tbody>

				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="5" />

				<c:forEach items="${item.indentReportDetailList}" var="row"
					varStatus="count">

					<c:choose>

						<c:when test="${totalRowCount eq maxRowCount}">

							<c:set var="totalRowCount" value="${totalRowCount+1}" />

							<div style="page-break-after: always;"></div>



							<!-- new page -->
			</tbody>
		</table>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="1" valign="top">
						<table>
							<tr>
								<td align="right">COM-F-01 REV.00 DT.01-05-2018<br>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>


		<h4 align="center">TRAMBAK &nbsp;&nbsp;RUBBER
			&nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h4>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">
				<tr class="information">
					<td colspan="3" valign="top">
						<table>
							<tr>

								<td width="33%"></td>


								<td width="33%" valign="bottom" align="center"
									style="font-weight: bold;">PUR. REQUISITION / INDENT</td>


								<td width="33%" align="right">Indent No.&nbsp;&nbsp;:
									${item.indMNo}<br> Date&nbsp;&nbsp;:
									${item.indMDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>



		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="2" valign="top">
						<table>
							<tr>
								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">Indenting
									Dept. &nbsp;&nbsp;&nbsp;- ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;- ${item.accHeadDesc} <br> For
									Department &nbsp;&nbsp;&nbsp;- <br> Indent Type
									&nbsp;&nbsp;&nbsp;-
								</td>

								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">

									Department &nbsp;&nbsp;&nbsp;- <br> Sub department
									&nbsp;&nbsp;&nbsp;- <br> Monthly &nbsp;&nbsp;&nbsp;-

								</td>

							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div>


		<br>


		<table align="center" border="1" cellspacing="0" cellpadding="1"
			id="table_grid" class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th >Description</th>
					<th>UOM</th>
					<th>Qty. Req.</th>
					<th>When Req. / Stock/ Avg.</th>

				</tr>
			</thead>
			<tbody>

				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="5" />


				<!-- end of new page -->

				</c:when>

				</c:choose>


				<tr>
					<td width="0" align="center"><c:out value="${count.index+1}" /></td>
					<td width="30%" align="left"><c:out value="${row.indItemDesc}" /></td>
					<td width="0" align="center"><c:out value="${row.indItemUom}" /></td>
					<td width="0" align="right"><c:out value="${row.indQty}" /></td>
					<td width="0" align="center"><c:out
							value="${row.indItemSchddt}" /></td>

				</tr>




				</c:forEach>

			</tbody>
		</table>

		<br>
		


	<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="1" valign="top">
						<table>
							<tr>
								<td width="25%" valign="top" align="left"
									style="padding: 8px; color: #000; font-size: 12px; ">

									Purpose &nbsp;&nbsp;&nbsp;- </td>


							</tr>

						</table>
					</td>
				</tr>
			</table>
<br><br>
		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="4" valign="top">
						<table>
							<tr>
								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Requisitioned By</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Stores</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Dept.Head</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

									Approved By</td>

							</tr>

						</table>
					</td>
				</tr>
			</table>

			<hr
				style="height: 1px; border: none; color: black; background-color: black;">

		</div>
	</c:forEach>

	<!-- END Main Content -->

</body>
</html>