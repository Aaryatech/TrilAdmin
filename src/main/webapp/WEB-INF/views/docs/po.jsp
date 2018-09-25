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
				style="float: right;">COM-F-01 REV.00 DT.01-05-2018 </span>
		</p>
		<!-- p -->


		<h4 align="center">TRAMBAK &nbsp;&nbsp;RUBBER
			&nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h4>



		<h6 style="font-weight: bold; margin: 0px;" align="center">Delivery
			& Billing Addr.: A-81 to A-87 & A-106 to A-112, Sinnar
			Tal.Indl.Estate, SINNAR - 422112</h6>
		<h6 style="font-weight: normal; margin: 0px;" align="center">CIN
			NO : U99999MH1986PLC042032</h6>

		<br>

		<p
			style="text-align: center; font-weight: bold; font-size: 13px; margin-left: 100px;">
			PURCHASE ORDER <span style="float: right;">Order
				No.&nbsp;&nbsp;: ${item.poNo}<br> Date&nbsp;&nbsp;:
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
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">To,<br>
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

															<td width="50%" valign="top">Party Cd :
																${item.vendId}</td>

															<td width="50%" valign="top" align="left"
																style="font-size: 11px">GST&nbsp; NO&nbsp; -
																${item.vendorGstNo} <br> PAN &nbsp;NO&nbsp; -
																${item.vendId}
															</td>

														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div> Quotation No. : ${item.vendQuation}<br> Quotation Dt. : <br>
									${item.vendQuationDate}<br> Indent No & Dt :
									${item.vendQuationDate}
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
				<tr>
					<th height="5px" style="max-height: 10px; max-width: 30px;"
						width="30px">SR.</th>
					<th width=5%>ITEM</th>
					<th width=30%>DESCRIPTION</th>
					<th width=5%>UOM</th>
					<th align="right" width=10%>QTY</th>
					<th align="right" width=15%>RATE Rs.</th>
					<th align="right" width=15%>VALUE Rs.</th>
					<th align="center" width=25%>SCHEDULE</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="12" />
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
			<span style="float: right; margin-right: 160px;">TOTAL -
				${total}</span>
		</p>


		<br>

		<div class="invoice-box">
			<table cellpadding="0" cellspacing="0">

				<tr class="information">
					<td colspan="2" valign="top">
						<table>
							<tr>
								<td valign="top"
									style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Delivery
									Terms : ${item.deliveryDesc}<br> <br> Payment Terms :
									${item.pymtDesc} <br> <br> Dispatch Mode :
									${item.dispModeDesc}<br> <br>
									<p
										style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">*
										Ensure that your supplies are full filling current goverment
										rules/regulations as applicable.</p>
								</td>
								<td valign="top"
									style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; border-right: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Packing/Forwarding
									&nbsp;&nbsp;&nbsp;- NIL <br> <br> GST
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
									GST AS APPLICABLE. <br> <br> Freight/Transport
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - NIL <br> <br>
									Other Charges &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;- NIL

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
			REMARKS IF ANY : ${item.deliveryDesc }<br> <br> <br> <br>
			<br>

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
						For TRAMBAK RUBBER INDUSTRIES LTD. <br> <br> <br> <br>
						( Reviewed & Approved )
				</td>
			</tr>


		</table>

		<br>

		<p
			style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;"
			align="center">------------------------------------------------------------------------------------------------------*
			ORDER VALIDITY : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp; DAYS *
			-----------------------------------------------------------------------------------------------
		</p>

		<br>


		<h5 style="font-weight: normal; margin: 0px;" align="left">NASHIK
			OFFICE : "MARUTI", 3 & 4, TAGORE NAGAR, OPP. AMBEDKAR NAGAR, NASHIK</h5>

		<h5 style="font-weight: normal; margin: 0px;" align="left">PHONE:
			0253-2410069, FAX : 0253-2417180, Email : purchase@trambakrubber.com</h5>

		<h5 style="font-weight: bold; margin: 0px;" align="left">WORKS:
			A-83, A-86, A-107, A-110, Sinnar Tal. Indl. Estate, SINNAR - 422 103</h5>


		<!-- END Main Content -->


		<!-- end of footer -->








		<p style="text-align: left; font-weight: normal;">
			Original / Duplicate(Acnt)/Triplicate(Purch)/Stroes <span
				style="float: right;">COM-F-01 REV.00 DT.01-05-2018 </span>
		</p>



		<h4 align="center">TRAMBAK &nbsp;&nbsp;RUBBER
			&nbsp;&nbsp;INDUSTRIES &nbsp;&nbsp; LTD.</h4>



		<h6 style="font-weight: bold; margin: 0px;" align="center">Delivery
			& Billing Addr.: A-81 to A-87 & A-106 to A-112, Sinnar
			Tal.Indl.Estate, SINNAR - 422112</h6>
		<h6 style="font-weight: normal; margin: 0px;" align="center">CIN
			NO : U99999MH1986PLC042032</h6>

		<br>

		<p
			style="text-align: center; font-weight: bold; font-size: 13px; margin-left: 100px;">
			PURCHASE ORDER <span style="float: right;">Order
				No.&nbsp;&nbsp;: ${item.poNo}<br> Date&nbsp;&nbsp;:
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
									style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">To,<br>
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

															<td width="50%" valign="top">Party Cd :
																${item.vendId}</td>

															<td width="50%" valign="top" align="left"
																style="font-size: 11px">GST&nbsp; NO&nbsp; -
																${item.vendId} <br> PAN &nbsp;NO&nbsp; -
																${item.vendId}
															</td>

														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div> Quotation No. : ${item.vendQuation}<br> Quotation Dt. : <br>
									${item.vendQuationDate}<br> Indent No & Dt :
									${item.vendQuationDate}
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
				<tr>
					<th height="5px" style="max-height: 10px; max-width: 30px;"
						width="30px">SR.</th>
					<th width=5%>ITEM</th>
					<th width=30%>DESCRIPTION</th>
					<th width=5%>UOM</th>
					<th align="right" width=10%>QTY</th>
					<th align="right" width=15%>RATE Rs.</th>
					<th align="right" width=15%>VALUE Rs.</th>
					<th align="center" width=25%>SCHEDULE</th>
				</tr>
			</thead>
			<tbody>




				<c:set var="totalRowCount" value="0" />
				<c:set var="maxRowCount" value="12" />
				<c:set var="total" value="0" />




				<!-- end of new page -->

				</c:when>

				</c:choose>

				<c:set var="totalRowCount" value="${totalRowCount+1}" />

				<c:set var="total" value="${total+row.basicValue}" />
				<tr>
					<td height="5px" style="max-height: 5px" align="center"
						width="30px"><c:out value="${count.index+1}" /></td>
					<td align="center"><c:out value="${row.itemCode}" /></td>
					<td align="left" width=50%><c:out value="${row.itemDesc}" /></td>
					<td align="center"><c:out value="${row.itemUom}" /></td>
					<td align="right" style="padding: 5px;"><c:out value="${row.itemQty}" /></td>
					<td align="right" style="padding: 5px;"><c:out value="${row.itemRate}" /></td>
					<td align="right" style="padding: 5px;"><c:out value="${row.basicValue}" /></td>
					<td align="center"><c:out
							value="On / Before ${row.schDate} ${row.schRemark} " /></td>

				</tr>
	</c:forEach>

	</tbody>
	</table>



	<p style="text-align: left; font-weight: normal;">
		<span style="float: right; margin-right: 160px;">TOTAL -
			${total}</span>
	</p>


	<br>

	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top"
								style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Delivery
								Terms : ${item.deliveryDesc}<br> <br> Payment Terms :
								${item.pymtDesc} <br> <br> Dispatch Mode :
								${item.dispModeDesc}<br> <br>
								<p
									style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;">*
									Ensure that your supplies are full filling current goverment
									rules/regulations as applicable.</p>
							</td>
							<td valign="top"
								style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; border-right: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Packing/Forwarding
								&nbsp;&nbsp;&nbsp;- NIL <br> <br> GST
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
								GST AS APPLICABLE. <br> <br> Freight/Transport
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - NIL <br> <br>
								Other Charges &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;- NIL

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
		REMARKS IF ANY : ${item.deliveryDesc }<br> <br> <br> <br>
		<br>

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
					For TRAMBAK RUBBER INDUSTRIES LTD. <br> <br> <br> <br>
					( Reviewed & Approved )
			</td>
		</tr>


	</table>

	<br>

	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;"
		align="center">------------------------------------------------------------------------------------------------------*
		ORDER VALIDITY : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; DAYS *
		----------------------------------------------------------------------------------------------
	</p>

	<br>


	<h5 style="font-weight: normal; margin: 0px;" align="left">NASHIK
		OFFICE : "MARUTI", 3 & 4, TAGORE NAGAR, OPP. AMBEDKAR NAGAR, NASHIK</h5>

	<h5 style="font-weight: normal; margin: 0px;" align="left">PHONE:
		0253-2410069, FAX : 0253-2417180, Email : purchase@trambakrubber.com</h5>

	<h5 style="font-weight: bold; margin: 0px;" align="left">WORKS:
		A-83, A-86, A-107, A-110, Sinnar Tal. Indl. Estate, SINNAR - 422 103</h5>


	<!-- END Main Content -->

	<br>


	</c:forEach>
</body>
</html>