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
			<table cellpadding="0" cellspacing="0" width="1000px"> 
				<tr class="information">
					<td valign="top">
						<table width="1000px">
							<tr>
								<td width="300px"  valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;"> 
								</td>
								<td width="600px"  valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">PUR. REQUISITION / INDENT
								</td>
								<td width="300px"  valign="top" align="left" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">
								</td> 
							</tr> 
						</table>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1000px">

				<tr class="information">
					<td valign="top">
						<table width="1000px"> 
							<tr>
								<td  width="600px"  valign="top" align="left" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;"> Indent No. : ${item.indMNo}
								</td>
								 
								  
								<td width="600px"  valign="top" align="right" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">Date : ${item.indMDate}
								</td>
								 

							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div> 
		 
		 <div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1200px">

				<tr class="information">
					<td valign="top">
						<table width="1200px">
							<tr>
								<td width="600px" valign="top"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-family: arial; font-weight: bold; font-size: 110%;">Indenting
									Dept. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.accHeadDesc} <br> For
									Development &nbsp;&nbsp;&nbsp;- <c:choose>
										<c:when test="${item.indIsdev==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose><br> Indent Type
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.typeName}
								</td>

								<td width="600px"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-family: arial; font-weight: bold; font-size: 110%;">
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
		<c:set var="pageCount" value="1" />
		<c:set var="totalPage" value="${item.indentReportDetailList.size()/7}" />
		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="table-layout: fixed; display: block; height: 700px; width: 100%;"
			id="table_grid">
			<thead>
				<tr style="font-size: 110%;">
					<th>Sr.</th>

					<th width="40%">Description</th>
					<th>UOM</th>
					<th>Max Level</th>
					<th>Stock In Store </th>
					<th>Qty. Req.</th>
					<th>When Req. / Stock/ Avg.</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="7" />
				<c:set var="total" value="0" />

				<c:forEach items="${item.indentReportDetailList}" var="row"
					varStatus="count">



					<c:choose>

						<c:when test="${totalRowCount eq maxRowCount}">

							<c:set var="totalRowCount" value="${totalRowCount+1}" />




							<!-- new page -->
			</tbody>
		</table>

		<!-- start of footer -->
		
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
			 
						<table width="1000px">
							<tr >
							<c:set var="logRecordFind" value="0"></c:set>
							<c:forEach items="${getLogRecordList}" var="getLogRecordList" >
							<c:choose>
								<c:when test="${getLogRecordList.docTranId==item.indMId}"> 
								<c:set var="logRecordFind" value="1"></c:set>
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Requisitioned By <br> ${getLogRecordList.usrName} </td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Stores <br> ${getLogRecordList.usrName}</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Dept.Head <br> ${getLogRecordList.app1UserName}</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Approved By <br> ${getLogRecordList.app2UserName}</td>
									</c:when>
								</c:choose>
							</c:forEach> 
							
							<c:choose>
								<c:when test="${logRecordFind==0}"> 
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Requisitioned By <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Stores <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Dept.Head <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Approved By <br>  -</td>
								
								</c:when>
							</c:choose>
								  
							</tr>

						</table> 

			<hr
				style="height: 1px; border: none; color: black; background-color: black;">

		</div><br>
 		<c:set var="pageCount" value="${pageCount+1}" />


		 <div align="left">
			<h5>${documentBean.docIsoSerialNumber}</h5>
		</div>


		<h4 align="center" align="center" align="center" style=" font-family: arial; font-weight: bold; font-size: 120%;">${company.companyName}</h4>
 
 		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1000px">

				<tr class="information">
					<td valign="top">
						<table width="1000px">
							<tr>
								<td width="300px"  valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;"> 
								</td>
								<td width="600px"  valign="top" align="center" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">PUR. REQUISITION / INDENT
								</td>
								<td width="300px"  valign="top" align="left" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">
								</td> 
							</tr> 
						</table>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1000px">

				<tr class="information">
					<td valign="top">
						<table width="1000px"> 
							<tr>
								<td  width="600px"  valign="top" align="left" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;"> Indent No. : ${item.indMNo}
								</td>
								  
								<td width="600px"  valign="top" align="right" 
								style=" font-family: arial; font-weight: bold; font-size: 110%;">Date : ${item.indMDate}
								</td>
								 

							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div> 
		 
		 <div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1200px">

				<tr class="information">
					<td valign="top">
						<table width="1200px">
							<tr>
								<td width="600px" valign="top"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-family: arial; font-weight: bold; font-size: 110%;">Indenting
									Dept. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.catDesc}<br> Account Head
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.accHeadDesc} <br> For
									Development &nbsp;&nbsp;&nbsp;- <c:choose>
										<c:when test="${item.indIsdev==1}">YES</c:when><c:otherwise>NO</c:otherwise>
									</c:choose><br> Indent Type
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.typeName}
								</td>

								<td width="600px"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-family: arial; font-weight: bold; font-size: 110%;">
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

			<c:choose>
				<c:when test="${(totalPage+(1-(totalPage%1))%1)==pageCount}">
				<table align="center" border="1" cellpadding="0" cellspacing="0"
						style="table-layout: fixed; display: block; height: 640px; width: 100%;"
						id="table_grid">
				</c:when>
				<c:otherwise>
				<table align="center" border="1" cellpadding="0" cellspacing="0"
				style="table-layout: fixed; display: block; height: 700px; width: 100%;"
				id="table_grid">
				</c:otherwise>
			</c:choose>
		
			<thead>
				<tr style="font-size: 110%;">
					<th>Sr.</th>

					<th width="40%">Description</th>
					<th>UOM</th>
					<th>Max Level</th>
					<th>Stock In Store </th>
					<th>Qty. Req.</th>
					<th>When Req. / Stock/ Avg.</th>
				</tr>
			</thead>
			<tbody>




				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="7" />
				<c:set var="total" value="0" />




				<!-- end of new page -->

				</c:when>

				</c:choose>

				<c:set var="totalRowCount" value="${totalRowCount+1}" />

				 
				<tr style="font-size: 110%;">
					<td width="5%" align="center"><c:out value="${count.index+1}" /></td>
					<td   align="left" style="padding: 10px;"><c:out value="${row.indItemDesc}" /></td>
					<td width="6%" align="center"><c:out value="${row.indItemUom}" /></td>
					<td width="6%" align="right" style="padding: 10px;"><c:out value="${row.itemMaxLevel}" /></td>
					<td width="6%" align="right" style="padding: 10px;"><c:out value="${row.curntStock}" /></td>
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
			 
						<table width="1000px">
							<tr >
							<c:set var="logRecordFind" value="0"></c:set>
							<c:forEach items="${getLogRecordList}" var="getLogRecordList" >
							<c:choose>
								<c:when test="${getLogRecordList.docTranId==item.indMId}"> 
								<c:set var="logRecordFind" value="1"></c:set>
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Requisitioned By <br> ${getLogRecordList.usrName} </td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Stores <br> ${getLogRecordList.usrName}</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Dept.Head <br> ${getLogRecordList.app1UserName}</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Approved By <br> ${getLogRecordList.app2UserName}</td>
									</c:when>
								</c:choose>
							</c:forEach> 
							
							<c:choose>
								<c:when test="${logRecordFind==0}"> 
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Requisitioned By <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Stores <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Dept.Head <br>  -</td>
								
								<td width="300px" valign="top" align="center"
									style="padding: 8px; color: #000;  font-weight: bold;">

									Approved By <br>  -</td>
								
								</c:when>
							</c:choose>
								  
							</tr>

						</table> 
			<hr
				style="height: 1px; border: none; color: black; background-color: black;">

		</div>
		
		<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="1" valign="top">
						<table>
							<tr>
								<td width="25%" valign="top" align="left"
									style="padding: 8px; color: #000;  ">

									 If any abnormality found such as damaged and quality problem to the Container/Consumable Etc. Observed during the Monthly be noted below and will be informed the Department Head</td>


							</tr>

						</table>
					</td>
				</tr>
			</table> 
	</c:forEach>

	<!-- END Main Content -->

</body>
</html>