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
	font-size: 12;
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
h6  {
    color: black;
    font-family: arial;
    font-size: 80%;
}

th {
	background-color: #6a9ef2;
	color: white;
	
}
hr { height:3px; border:none; color:rgb(60,90,180); background-color:rgb(60,90,180); }

</style>

</head>
<body>
<div align="right"> <h5> COM-F-01 REV.00 DT.01-05-2018   &nbsp;&nbsp;&nbsp;&nbsp; ${indent.indMDate} </h5></div>

<h3 align="center">TRAMBAK  &nbsp;&nbsp;RUBBER  &nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h3>
<p align="center">GOODS RECEIPT NOTE</p>


<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top">
								Party Code : M00075<br>
								M.C.R. INDUSTRIES</td>



							<td align="right">GRN NO : <br>
							Date : 03/05/2018
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
							<td valign="top" width="33%" align="left">
								Gate Entry No : 05<br>
								Gate Entry Dt. : 01/05/2018</td>



							<td align="left" width="33%">Invoice No : <br>
							Invoice Dt: 
							</td>
							
							
							<td align="left"  width="33%">D/C NO. - 266 <br>
							D/C Dt. - 31/03/2018
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

<hr style="height:1px; border:none; color:black; background-color:black;">

	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr >
				<th >SR.</th>
				<th >ITEM</th>
				<th >DESCRIPTION</th>
				<th >EX.DUTY</th>
				<th >CHLN.QTY</th>
				<th >RECT.QTY</th>
				<th >WT/SIZE</th>
			</tr>
		</thead>
		<tbody>
			
			
			<tr>
					<td width="0" align="center"><c:out value="1" /></td>
					<td width="0" align="center"><c:out value="J00893" /></td>
					<td width="40%" align="center"><c:out value="ENGINEERING SERVICES FOR T5 SPLICER M/C BREAKDOWN PLC PROGRAMMING FROM MONK AUTOMATION" /></td>
					<td width="0" align="center"><c:out value="JOB" /></td>
					<td width="0" align="right"><c:out value="1.00" /></td>
					<td width="0" align="center"><c:out value="02/04/2018   
					0.000 /  0.00
					" /></td>

				</tr>
			<%-- <c:forEach items="${indDetailList}" var="item" varStatus="count">
				<tr>
					<td width="0" align="center"><c:out value="${count.index+1}" /></td>
					<td width="0" align="center"><c:out value="${item.itemId}" /></td>
					<td width="0" align="center"><c:out value="${item.indItemDesc}" /></td>
					<td width="0" align="center"><c:out value="${item.indItemUom}" /></td>
					<td width="0" align="center"><c:out value="${item.indQty}" /></td>
					<td width="0" align="center"><c:out value="${item.indItemSchddt}" /></td>

				</tr>

			</c:forEach> --%>
				
		</tbody>
	</table>
	
	<br><br>
	
	
<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top" width="33%" align="left">
								Transport &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <br>
								Vehicle No &nbsp;&nbsp;&nbsp;&nbsp; -  <br>
								LR/R.R. No &nbsp;&nbsp;&nbsp;&nbsp;-  <br>
								Remark&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - 
								
								
								
								</td>



							<td align="left" width="60%" >
							Date -  
							</td>
							
							
							
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
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

								Prepared By</td>
								
							<td width="50%" valign="top" align="center"
								style=" padding: 8px; color: #000; font-size: 12px; font-weight: bold;">

								Checked By</td>

						
						</tr>

					</table>
				</td>
			</tr>
		</table>
	</div>


	<!-- END Main Content -->

</body>
</html>