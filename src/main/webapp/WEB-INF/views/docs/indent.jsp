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
	font-family: arial;
	font-weight: bold;
	font-size: 90%;
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
	font-family: arial;
	font-size: 95%;
	font-weight: bold;
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
	/* background-color: #6a9ef2; */
	color: black;
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


		 
<div align="left">
			<h5>${documentBean.docIsoSerialNumber}</h5>
		</div>

		<h4 align="center" align="center" style=" font-family: arial; font-weight: bold; font-size: 120%;">${company.companyName}</h4>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" >
				<tr class="information">
					<td colspan="3" valign="top">
						<table>
							<tr>

<td width="20%"></td>

								<td width="58%" valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 95%;">&nbsp;&nbsp;&nbsp;&nbsp;PUR. REQUISITION / INDENT</td>
 
							 	<td width="23%" align="left">Indent No.&nbsp;&nbsp;:
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
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; ">Indenting
									Dept. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.accHeadDesc} <br> For
									Development &nbsp;&nbsp;&nbsp;- <c:choose>
										<c:when test="${item.indIsdev==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose><br> Indent Type
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.typeName}
								</td>

								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000;">

									Department &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;- ${item.deptDesc} <br> Sub department
									&nbsp;&nbsp;&nbsp;- ${item.subDeptDesc}<br> Monthly &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;	-
									<c:choose>
										<c:when test="${item.indIsmonthly==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose>



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


		 <div align="left">
			<h5>${documentBean.docIsoSerialNumber}</h5>
		</div>


		<h4 align="center" align="center" align="center" style=" font-family: arial; font-weight: bold; font-size: 120%;">${company.companyName}</h4>


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">
				<tr class="information">
					<td colspan="3" valign="top">
						<table>
							<tr>

								<td width="20%"></td>

								<td width="58%" valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 95%;">&nbsp;&nbsp;&nbsp;&nbsp;PUR. REQUISITION / INDENT</td>
  
							 	<td width="23%" align="left">Indent No.&nbsp;&nbsp;:
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
					<td colspan="2" valign="top">
						<table>
							<tr>
								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; ">Indenting
									Dept. &nbsp;&nbsp;&nbsp;&nbsp;- ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ${item.accHeadDesc} <br> For
									Development &nbsp;&nbsp;&nbsp;- <c:choose>
										<c:when test="${item.indIsdev==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose><br> Indent Type
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ${item.typeName}
								</td>

								<td width="50%" valign="top"
									style="border-left: 0px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000;">

									Department &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;- ${item.deptDesc} <br> Sub department
									&nbsp;&nbsp;&nbsp;  - ${item.subDeptDesc}<br> Monthly &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;	-
									<c:choose>
										<c:when test="${item.indIsmonthly==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose>
 
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
					<td width="5%" align="center"><c:out value="${count.index+1}" /></td>
					<td   align="left" style="padding: 10px;"><c:out value="${row.indItemDesc}" /></td>
					<td width="6%" align="center"><c:out value="${row.indItemUom}" /></td>
					<td width="6%" align="right" style="padding: 10px;"><c:out value="${row.indQty}" /></td>
					<td width="15%" align="center"><c:out
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
									style="padding: 8px; color: #000;  ">

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
									style="padding: 8px; color: #000;  font-weight: bold;">

									Requisitioned By</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Stores</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Dept.Head</td>

								<td width="25%" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

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