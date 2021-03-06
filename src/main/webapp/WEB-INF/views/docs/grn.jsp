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
<title>GRN</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

 <style type="text/css">
 table {
	border-collapse: collapse;
	font-family: arial;
	font-weight: bold;
	font-size: 90%;
	width:100%;
    page-break-inside: auto !important ;
    
} 
p  {
    color: black;
    font-family: arial;
    font-size: 70%;
	margin-top: 0;
	padding: 0;
	font-weight: bold;

}
h5 {
	color: black;
	font-family: arial;
	font-size: 95%;
	font-weight: bold;
	padding-bottom: 10px;
	margin: 0;
}
h6  {
    color: black;
    font-family: arial;
    font-size: 80%;
}

th {
	 
	color: black;
	
}
hr { height:3px; border:none; color:rgb(60,90,180); background-color:rgb(60,90,180); }

</style>

</head>
<body>

	<c:forEach items="${list}" var="item" varStatus="count">


<div align="center"> <%-- <h5>${documentBean.docIsoSerialNumber}</h5> --%>

<h0 align="center" style=" font-family: arial; font-weight: bold; font-size: 120%;">${company.companyName}</h0><h6 style="font-weight: bold; margin: 0px;" align="center">(Formally
						Known As Trambak Rubber Industrial Limited)</h6>
 </div>
 
 
 
 
 
<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>
							<td valign="top" width="20%" style=" font-family: arial; font-weight: bold; font-size: 110%;">
								Party Code :&nbsp;&nbsp; ${item.vendorCode}<br>
								${item.vendorName}
								</td>

<td valign="center" width="60%" align="center" style=" font-family: arial; font-weight: bold; font-size: 110%;">
GOODS RECEIPT NOTE
								</td>

							<td align="right" width="20" style=" font-family: arial; font-weight: bold; font-size: 110%;">GRN NO : &nbsp;&nbsp;${item.mrnNo }<br>
							Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp; ${item.mrnDate }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>



<hr style="height:1px; border:none; color:black; background-color:black;">






<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>
							<td valign="top" width="33%" align="left" style=" font-family: arial; font-weight: bold; font-size: 110%;">
								Gate Entry No : ${item.gateEntryNo }<br>
								Gate Entry Dt. : ${item.gateEntryDate }</td>



							<td align="left" width="33%" style=" font-family: arial; font-weight: bold; font-size: 110%;">Invoice No : ${item.billNo }<br>
							Invoice Dt&nbsp;&nbsp;: ${item.billDate }
							</td>
							
							
							<td align="left"  width="33%" style=" font-family: arial; font-weight: bold; font-size: 110%;">D/C No. - ${item.docNo } <br>
							D/C Dt.&nbsp;&nbsp;- ${item.docDate }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

<hr style="height:1px; border:none; color:black; background-color:black;">

	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered" style="table-layout: fixed; display: block; height: 300px; width: 100%;">
		<thead>
			<tr style=" font-family: arial; font-weight: bold; font-size: 110%;">
				<th >SR.</th>
				<th >ITEM</th>
				<th >DESCRIPTION</th>
				<th >CHLN.QTY</th>
				<th >RECT.QTY</th>
				<th >WT/SIZE</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="3" />
				<c:set var="total" value="0" />

				<c:forEach items="${item.mrnReportDetailList}" var="row"
					varStatus="count">



					<c:choose>

						<c:when test="${totalRowCount eq maxRowCount}">

							<c:set var="totalRowCount" value="${totalRowCount+1}" />
			
			<%-- <c:forEach items="${item.mrnReportDetailList}" var="row"
					varStatus="count">
			
			<tr>
					<td width="0" align="center"><c:out value="${count.index+1}" /></td>
					<td width="0" align="center" ><c:out value="${row.itemCode}" /></td>
					<td width="50%" align="left" style="padding: 10px;"><c:out value="${row.itemDesc}" /></td>
					<td width="0" align="right" style="padding: 10px;"><c:out value="${row.mrnQty}" /></td>
					<td width="0" align="right" style="padding: 10px;"><c:out value="${row.rejectQty }" /></td>
					<td width="0" align="center"><c:out value="NA" /></td>

				</tr>
			</c:forEach> --%>
				
		</tbody>
	</table>
	
	<br>
	
	
<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top" width="400px" align="left">
								Transport &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ${item.transport } 
								</td>
 
							<td align="left" width="400px" >
							Vehicle No &nbsp;&nbsp;&nbsp;&nbsp; - </td>
							<td align="left" width="400px" >
							Remark&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.remark1 }</td>
							 
							
						</tr>
						<tr>
							<td valign="top" width="400px" align="left">
								 
								LR/R.R. No &nbsp;&nbsp;&nbsp;&nbsp;-  ${item.lrNo }  
								 
								</td>
 
							<td align="left" width="400px" >
							Date -   ${item.lrDate } &nbsp;</td>
							 
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	
	
	
	<br><br>



<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
						
						<c:set var="logRecordFind" value="0"></c:set>
						<c:forEach items="${getLogRecordList}" var="getLogRecordList" >
							<c:choose>
								<c:when test="${getLogRecordList.docTranId==item.mrnId}"> 
								<c:set var="logRecordFind" value="1"></c:set>
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Prepared By <br> ${getLogRecordList.usrName} </td>
								
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Checked By <br> ${getLogRecordList.usrName}</td>
								
							 
									</c:when>
								</c:choose>
							</c:forEach>
							
							<c:choose>
								<c:when test="${logRecordFind==0}"> 
								
								<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Prepared By</td>
								
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;   font-weight: bold;">

								Checked By</td>
								
								</c:when>
							</c:choose>
							 
						
						</tr>

					</table>
				</td>
			</tr>
		</table>
														<hr	style="height: 1px; border: none; color: black; background-color: black;">
		
	</div><br>
	
	<div align="left"> <h5>${documentBean.docIsoSerialNumber}</h5></div>

<h3 align="center" style=" font-family: arial; font-weight: bold; font-size: 120%;">${company.companyName}</h3>
 
<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>
							<td valign="top" width="20%" style=" font-family: arial; font-weight: bold; font-size: 110%;">
								Party Code :&nbsp;&nbsp; ${item.vendorCode}<br>
								${item.vendorName}
								</td>

<td valign="top" width="60%" align="center" style="font-weight: bold;  ">
GOODS RECEIPT NOTE
								</td>

							<td align="left" width="20" style=" font-family: arial; font-weight: bold; font-size: 110%;">GRN NO : &nbsp;&nbsp;${item.mrnNo }<br>
							Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp; ${item.mrnDate }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>



<hr style="height:1px; border:none; color:black; background-color:black;">






<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>
							<td valign="top" width="33%" align="left" style=" font-family: arial; font-weight: bold; font-size: 110%;">
								Gate Entry No : ${item.gateEntryNo }<br>
								Gate Entry Dt. : ${item.gateEntryDate }</td>



							<td align="left" width="33%" style=" font-family: arial; font-weight: bold; font-size: 110%;">Invoice No : ${item.billNo }<br>
							Invoice Dt&nbsp;&nbsp;: ${item.billDate }
							</td>
							
							
							<td align="left"  width="33%" style=" font-family: arial; font-weight: bold; font-size: 110%;">D/C No. - ${item.docNo } <br>
							D/C Dt.&nbsp;&nbsp;- ${item.docDate }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered" style="table-layout: fixed; display: block; height: 300px; width: 100%;">
		<thead>
			<tr style=" font-family: arial; font-weight: bold; font-size: 110%;">
				<th >SR.</th>
				<th >ITEM</th>
				<th >DESCRIPTION</th>
				<th >CHLN.QTY</th>
				<th >RECT.QTY</th>
				<th >WT/SIZE</th>
			</tr>
		</thead>
		<tbody>
		
		<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="3" />
				<c:set var="total" value="0" />




				<!-- end of new page -->

				</c:when>

				</c:choose>
				
				<c:set var="totalRowCount" value="${totalRowCount+1}" />
				
				<tr style=" font-family: arial; font-weight: bold; font-size: 110%;">
					<td width="0" align="center"><c:out value="${count.index+1}" /></td>
					<td width="0" align="center" style="padding: 10px;"><c:out value="${row.itemCode}" /></td>
					<td width="70%" align="left" style="padding: 10px;"><c:out value="${row.itemDesc}" /></td>
					<td width="0" align="right" style="padding: 10px;"><c:out value="${row.mrnQty}" /></td>
					<td width="0" align="right" style="padding: 10px;"><c:out value="${row.rejectQty }" /></td>
					<td width="0" align="center"><c:out value="NA" /></td>

				</tr>
		</c:forEach>
		</tbody>
		</table>
		 
		<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
					
						<tr>
							<td valign="top" width="400px" align="left">
								Transport &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- ${item.transport } 
								</td>
 
							<td align="left" width="400px" >
							Vehicle No &nbsp;&nbsp;&nbsp;&nbsp; - </td>
							<td align="left" width="400px" >
							Remark&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.remark1 }</td>
							 
							
						</tr>
						<tr>
							<td valign="top" width="400px" align="left">
								 
								LR/R.R. No &nbsp;&nbsp;&nbsp;&nbsp;-  ${item.lrNo }  
								 
								</td>



							<td align="left" width="400px" >
							Date -   ${item.lrDate } &nbsp;</td>
							
							
							
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	
	
	
	<br><br>



<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<c:set var="logRecordFind" value="0"></c:set>
						<c:forEach items="${getLogRecordList}" var="getLogRecordList" >
							<c:choose>
								<c:when test="${getLogRecordList.docTranId==item.mrnId}"> 
								<c:set var="logRecordFind" value="1"></c:set>
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Prepared By <br> ${getLogRecordList.usrName} </td>
								
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Checked By <br> ${getLogRecordList.usrName}</td>
								
							 
									</c:when>
								</c:choose>
							</c:forEach>
							
							<c:choose>
								<c:when test="${logRecordFind==0}"> 
								
								<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;  font-weight: bold;">

								Prepared By <br> - </td>
								
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000;   font-weight: bold;">

								Checked By <br> -</td>
								
								</c:when>
							</c:choose>

						
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