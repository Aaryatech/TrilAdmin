<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>PO PDF</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
table {
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


		<!--  -->

		<p style="text-align: left; font-weight: normal;">
			Original / Duplicate(Acnt)/Triplicate(Purch)/Stroes <span
				style="float: right;">${documentBean.docIsoSerialNumber}</span>
		</p>
		<!-- p -->


		<h4 align="center">${company.companyName}</h4>



		<h6 style="font-weight: bold; margin: 0px;" align="center">Delivery
			& Billing Addr.: ${company.factoryAdd}</h6>
		<h6 style="font-weight: normal; margin: 0px;" align="center">CIN
			NO : ${company.cinNumber}</h6>
		 
		<br> 
		
		 <span style="float: left; font-weight: bold; font-size: 13px;">GST&nbsp; NO&nbsp; : ${company.gstNumber}
		 <br>PAN &nbsp;NO&nbsp; : ${company.panNumber}
			</span>
		<p style="text-align: center; font-weight: bold; font-size: 13px; margin-left: 100px;"> 
			PURCHASE ORDER 
			
			<span style="float: right;">Order
				No.&nbsp; : ${item.poNo}<br>&nbsp;&nbsp; Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
				${item.poDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
		</p>

		<br>

		<!-- t -->


		<!-- t -->


		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1000px">

				<tr class="information">
					<td valign="top">
						<table width="1000px">
							<tr>
								<td width="900px	" valign="top"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px; font-weight: bold;">To,<br>
									${item.vendorName}<br>${item.vendorAdd1}
								</td>

								<td width="50%"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">

									<div class="invoice-box">
										<table cellpadding="0" cellspacing="0">
											<tr class="information">
												<td colspan="2" valign="top">
													<table>
														<tr>

															<td width="50%" valign="top">Party Cd &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${item.vendorCode}</td>

															 

														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div> Quotation No. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${item.vendQuation}<br> Quotation Dt. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${item.vendQuationDate}<br> 
									Indent No. & Date :
									${item.indNo}  ${item.indDate}
								</td>

							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div>


		<br>
		<h5>
			DEAR SIR,<br> WE HAVE PLEASURE IN PLACING/CONFIRMING OUR ORDER
			FOR UNDERMENTIONED GOODS.

		</h5>
		
		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="table-layout: fixed; display: block; height: 476px; width: 100%;"
			id="table_grid">
			<thead>
				<tr style="font-size: 15px;">
					<th height="5px" style="max-height: 10px; max-width: 30px;"
						width="30px">SR.</th>
					<th width=5%>ITEM</th>
					<th width=38%>DESCRIPTION</th>
					<th width=5%>UOM</th>
					<th align="right" width=7%>QTY</th>
					<th align="right" width=10%>RATE Rs.</th>
					<th align="right" width=10%>VALUE Rs.</th>
					<th align="center" width=30%>SCHEDULE</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="10" />
				<c:set var="total" value="0" />

				<c:forEach items="${item.pOReportDetailList}" var="row"
					varStatus="count">



					<c:choose>

						<c:when test="${totalRowCount eq maxRowCount}">

							<c:set var="totalRowCount" value="${totalRowCount+1}" />




							<!-- new page -->
			</tbody>
		</table>

		<!-- start of footer -->


		<p style="text-align: left; font-weight: normal;">
			<span style="float: right; margin-right: 110px;">TOTAL -
				<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
										value="${total}" /></span>
		</p>


		<br>

		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="2" valign="top">
						<table>
							<tr>
								<td width="50%" valign="top"
									style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Delivery
									Terms : ${item.deliveryDesc}<br> <br> Payment Terms :
									${item.pymtDesc} <br> <br> Dispatch Mode :
									${item.dispModeDesc}<br> <br>
									<p
										style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">*
										Ensure that your supplies are full filling current goverment
										rules/regulations as applicable.</p>
								</td>
								<td width="50%" valign="top"
									style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; border-right: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Packing/Forwarding
									&nbsp;&nbsp;&nbsp;- ${item.poPackRemark}/${item.poPackVal} <br> <br> GST
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
									GST ${item.poTaxPer} AS APPLICABLE. <br> <br> Freight/Transport
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.poFrtRemark}/${item.poFrtVal} <br> <br>
									Other Charges &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;- ${item.otherChargeAfterRemark}/${item.otherChargeAfter}

								</td>

							</tr>



						</table>
					</td>
				</tr>
			</table>
		</div>

		<br>
		<p
			style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
			REMARKS IF ANY : ${item.deliveryDesc }<br> 
		</p>



		<p
			style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">NOTE
			: PLEASE MENTION THE FOLLOWING</p>
 
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131;">


			<tr>
				<td colspan="6" width="50%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">
					<p
						style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
						1) ITEMCODE, PARTY CODE & PO.NO ON YOUR D/C.<br> 2) PL.
						PROVIDE YOUR TEST & INSP.CERTIFICATE -YES/NO<br> 3)
						INSPECTION SUBJECT TO OUR / YOUR END.<br> 4) EXPIRY DATE OF
						EACH ITEM.<br> 5) IF MTRL.REJECTED, PLS.ARRANGE TO COLLECT
						FROM OUR FACTORY SITE &nbsp;&nbsp;&nbsp;&nbsp;WITHIN 8 DAYS
						OTHERWISE WE WILL NIT RESPONSIBLE FOR ANY LOSS OR
						&nbsp;&nbsp;&nbsp;&nbsp;DAMAGE.

					</p>
				</td>
				<td colspan="6" width="50%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">
					<p
						style="color: #000; font-size: 10px; vertical-align: top; text-align: center; margin: 0px; font-weight: normal;">
						For ${company.companyName}<br> <br> <br> <br>
						( Reviewed & Approved )
				</td>
			</tr>


		</table>

		<br>

		<p
			style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold; padding-bottom: 5px"
			align="center">------------------------------------------------------------------------------------------*
			ORDER VALIDITY : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp; DAYS *
			-----------------------------------------------------------------------------------------------------
		</p>
 
		<h5 style="font-weight: normal; margin: 0px;" align="left">NASHIK
			OFFICE : ${company.officeAdd}</h5>

		<h5 style="font-weight: normal; margin: 0px;" align="left">PHONE:
			${company.officePhoneNumber}, FAX : ${company.officeFaxNumber}, Email : ${company.purchaseEmail}</h5>

		<h5 style="font-weight: bold; margin: 0px; padding-bottom: 5px" align="left" >WORKS:
			${company.factoryAdd}</h5>


		<!-- END Main Content -->


		<!-- end of footer -->








		<p style="text-align: left; font-weight: normal;">
			Original / Duplicate(Acnt)/Triplicate(Purch)/Stroes <span
				style="float: right;">${documentBean.docIsoSerialNumber}</span>
		</p>



		<h4 align="center">${company.companyName}</h4>

 

		<h6 style="font-weight: bold; margin: 0px;" align="center">Delivery
			& Billing Addr.:${company.factoryAdd}</h6>
		<h6 style="font-weight: normal; margin: 0px;" align="center">CIN
			NO : ${company.cinNumber}</h6>
			 

		<br>
		 
        <span style="float: left; font-weight: bold; font-size: 13px;">GST&nbsp; NO&nbsp; : ${company.gstNumber}
		 <br>PAN &nbsp;NO&nbsp; : ${company.panNumber}
			</span>
		<p
			style="text-align: center; font-weight: bold; font-size: 13px; margin-left: 100px;">
			PURCHASE ORDER <span style="float: right;">Order
				No.&nbsp; : ${item.poNo}<br>&nbsp;&nbsp; Date &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
				${item.poDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
		</p>

		<br>



		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0" width="1000px">

				<tr class="information">
					<td valign="top">
						<table width="1000px">
							<tr>
								<td width="900px	" valign="top"
									style="font-weight: bold; border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">To,<br>
									${item.vendorName}<br>${item.vendorAdd1}
								</td>

								<td width="50%"
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">

									<div class="invoice-box">
										<table cellpadding="0" cellspacing="0">
											<tr class="information">
												<td colspan="2" valign="top">
													<table>
														<tr>

															<td width="50%" valign="top">Party Cd &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :
																${item.vendorCode}</td>
 
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div> Quotation No. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${item.vendQuation}<br> Quotation Dt. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${item.vendQuationDate}<br> Indent No. & Date :
									${item.indNo} ${item.indDate}
								</td>

							</tr>

						</table>
					</td>
				</tr>
			</table>
		</div>


		<br>
		<h5>
			DEAR SIR,<br> WE HAVE PLEASURE IN PLACING/CONFIRMING OUR ORDER
			FOR UNDERMENTIONED GOODS.

		</h5>
		<table align="center" border="1" cellpadding="0" cellspacing="0"
			style="table-layout: fixed; display: block; height: 476px; width: 100%;"
			id="table_grid">
			<thead>
				<tr style="font-size: 15px;">
					<th height="5px" style="max-height: 10px; max-width: 30px;"
						width="30px">SR.</th>
					<th width=5%>ITEM</th>
					<th width=38%>DESCRIPTION</th>
					<th width=5%>UOM</th>
					<th align="right" width=7%>QTY</th>
					<th align="right" width=10%>RATE Rs.</th>
					<th align="right" width=10%>VALUE Rs.</th>
					<th align="center" width=30%>SCHEDULE</th>
				</tr>
			</thead>
			<tbody>




				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="10" />
				<c:set var="total" value="0" />




				<!-- end of new page -->

				</c:when>

				</c:choose>

				<c:set var="totalRowCount" value="${totalRowCount+1}" />

				<c:set var="total" value="${total+row.basicValue}" />
				<tr style="font-size: 13px;">
					<td height="5px" style="max-height: 5px" align="center"
						width="30px"><c:out value="${count.index+1}" /></td>
					<td align="center"><c:out value="${row.itemCode}" /></td>
					<td align="left" width=50% style="padding: 5px;"><c:out value="${row.itemDesc}" /></td>
					<td align="center"><c:out value="${row.itemUom}" /></td>
					<td align="right" style="padding: 5px;"><c:out value="${row.itemQty}" /></td>
					<td align="right" style="padding: 5px;">
					<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
										value="${row.itemRate}" /> </td>
					<td align="right" style="padding: 5px;">
					<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
										value="${row.basicValue}" /> </td>
					<td align="center"><c:out
							value="On / Before ${row.schDate} ${row.schRemark} " /></td>

				</tr>
	</c:forEach>

	</tbody>
	</table>



	<p style="text-align: left; font-weight: normal;">
		<span style="float: right; margin-right: 110px;">TOTAL -
			<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
										value="${total}" /></span>
	</p>


	<br>

	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top"
								width="50%" style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Delivery
								Terms : ${item.deliveryDesc}<br> <br> Payment Terms :
								${item.pymtDesc} <br> <br> Dispatch Mode :
								${item.dispModeDesc}<br> <br>
								<p
									style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">*
									Ensure that your supplies are full filling current goverment
									rules/regulations as applicable.</p>
							</td>
							<td width="50%" valign="top"
								style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; border-right: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Packing/Forwarding
								&nbsp;&nbsp;&nbsp;- ${item.poPackRemark}/${item.poPackVal} <br> <br> GST
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
								GST ${item.poTaxPer} AS APPLICABLE. <br> <br> Freight/Transport
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ${item.poFrtRemark}/${item.poFrtVal} <br> <br>
								Other Charges &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;- ${item.otherChargeAfterRemark}/${item.otherChargeAfter}

							</td>

						</tr>



					</table>
				</td>
			</tr>
		</table>
	</div>

	<br>
	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
		REMARKS IF ANY : ${item.deliveryDesc }<br>  
	</p>



	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">NOTE
		: PLEASE MENTION THE FOLLOWING</p>



	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-top: 1px solid #313131; border-right: 1px solid #313131;">


		<tr>
			<td colspan="6" width="50%"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">
				<p
					style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: normal;">
					1) ITEMCODE, PARTY CODE & PO.NO ON YOUR D/C.<br> 2) PL.
					PROVIDE YOUR TEST & INSP.CERTIFICATE -YES/NO<br> 3) INSPECTION
					SUBJECT TO OUR / YOUR END.<br> 4) EXPIRY DATE OF EACH ITEM.<br>
					5) IF MTRL.REJECTED, PLS.ARRANGE TO COLLECT FROM OUR FACTORY SITE
					&nbsp;&nbsp;&nbsp;&nbsp;WITHIN 8 DAYS OTHERWISE WE WILL NIT
					RESPONSIBLE FOR ANY LOSS OR &nbsp;&nbsp;&nbsp;&nbsp;DAMAGE.

				</p>
			</td>
			<td colspan="6" width="50%"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">
				<p
					style="color: #000; font-size: 10px; vertical-align: top; text-align: center; margin: 0px; font-weight: normal;">
					For ${company.companyName} <br> <br> <br> <br>
					( Reviewed & Approved )
			</td>
		</tr>


	</table>

	<br>

	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold; padding-bottom: 5px"
		align="center">------------------------------------------------------------------------------------------*
		ORDER VALIDITY : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; DAYS *
		-----------------------------------------------------------------------------------------------------
	</p>
 

	<h5 style="font-weight: normal; margin: 0px;" align="left">${company.officeAdd}</h5>

	<h5 style="font-weight: normal; margin: 0px;" align="left">PHONE:${company.officePhoneNumber}, FAX : ${company.officeFaxNumber}, Email : ${company.purchaseEmail}</h5>

	<h5 style="font-weight: bold; margin: 0px;" align="left">WORKS:
		${company.factoryAdd}</h5>


	<!-- END Main Content -->

	 


	</c:forEach>
</body>
</html>