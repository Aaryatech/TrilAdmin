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




	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top">
								Original    /                Duplicate(Acnt)/Triplicate(Purch)/Stroes</td>



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



	<h6 style="font-weight: bold; margin: 0px;" align="center">Delivery
		& Billing Addr.: A-81 to A-87 & A-106 to A-112, Sinnar
		Tal.Indl.Estate, SINNAR - 422112</h6>
	<h6 style="font-weight: normal; margin: 0px;" align="center">CIN
		NO : U99999MH1986PLC042032</h6>




	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">
			<tr class="information">
				<td colspan="3" valign="top">
					<table>
						<tr>

							<td width="33%"></td>


							<td width="33%" valign="bottom" align="center"
								style="font-weight: bold;">PURCHASE ORDER</td>


							<td width="33%" align="right">Order No.&nbsp;&nbsp;:
								NSK/1819/222<br> Date&nbsp;&nbsp;:
								01/07/2018&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

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
								style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">To,<br>
								VAIBHAV INDUSTRIES<br> SINNAR-422103
							</td>



							<td width="50%"
								style="border-left: 1px solid #313131; border-top: 1px solid #313131; border-bottom: 1px solid #313131; border-right: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">



								<div class="invoice-box">
									<table cellpadding="0" cellspacing="0">
										<tr class="information">
											<td colspan="2" valign="top">
												<table>
													<tr>

														<td width="50%" valign="top">Party Cd : B00043</td>


														<td width="50%" valign="top" align="left"
															style="font-size: 11px">GST&nbsp; NO&nbsp; -
															27AABCT78G98G <br> PAN &nbsp;NO&nbsp; - AD75FG88V
														</td>

													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div> Quotation No. :<br> Quotation Dt. : <br> <br>
								Indent No & Dt : M00004
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
	<%-- <table align="center" border="1" 
		style="table-layout: fixed; display: block; height: 460px; width: 100%;"
		id="table_grid" >
		<thead>
			<tr >
				<th height="10px" style="max-height: 10px; max-width: 10px;" width=1%>SR.</th>
			<!-- 	<th width=5%>ITEM</th>
				<th width=30%>DESCRIPTION</th>
				<th width=5%>UOM</th>
				<th align="right" width=10%>QTY</th>
				<th align="right" width=15%>RATE Rs.</th>
				<th align="right" width=15%>VALUE Rs.</th>
				<th align="left" width=25%>SCHEDULE</th> -->
			</tr>
		</thead> 
		<tbody>

			<tr >
				<td  height="5px" style="max-height: 5px" align="center"><c:out value="1" /></td>
				<td align="center"><c:out value="HW0169" /></td>
				<td align="center"><c:out
						value="OIL LEVEL INDICATOR LENGTH:130 M,1/2 BOLT TIGHTEN" /></td>
				<td align="center"><c:out value="NO" /></td>
				<td align="right"><c:out value="2.00" /></td>
				<td align="right"><c:out value="1210.00" /></td>
				<td align="right"><c:out value="2420.00" /></td>
				<td align="center"><c:out value="On / Before 06/07/2018" /></td>

			</tr>

			<tr class="set-height">
				<td align="center"><c:out value="" /></td>
				<td align="center"><c:out value="" /></td>
				<td align="center"><c:out value="" /></td>
				<td align="center"><c:out value="" /></td>
				<td align="right"></td>
				<td align="right" style="border-left: 0px; border-top: 0px"><c:out
						value="Total-" /></td>
				<td align="right" style="border-left: 0px; border-top: 0px"><c:out
						value="2420.00" /></td>
				<td align="center"><c:out value="" /></td>

			</tr>


		</tbody>
	</table> --%>

	<br>

<table style="border: 1px solid black">
    <thead>
        <tr>
            <td>Header</td>
        </tr>
    </thead>
    <tbody style="display: block; border: 1px solid black; height: 300px; "	>
        <tr>
            <td>cell 1/1</td>
            <td>cell 1/2</td>
        </tr>
        <tr>
            <td>cell 2/1</td>
            <td>cell 2/2</td>
        </tr>
        <tr>
            <td>cell 3/1</td>
            <td>cell 3/2</td>
        </tr>
    </tbody>
</table>




	<div class="invoice-box">
		<table cellpadding="0" cellspacing="0">

			<tr class="information">
				<td colspan="2" valign="top">
					<table>
						<tr>
							<td valign="top"
								style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">Delivery
								Terms : FREE DELIVERY AT NASHIK OFFICE<br> <br>
								Payment Terms : 60 DAYS <br> <br> Dispatch Mode : YOUR
								OWN. <br> <br>
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
		REMARKS IF ANY : <br>
		<br> <br>
		<br><br>
		
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
					&nbsp;&nbsp;&nbsp;&nbsp;WITHIN 8 DAYS OTHERWISE WE WILL NIT RESPONSIBLE FOR ANY LOSS OR
					&nbsp;&nbsp;&nbsp;&nbsp;DAMAGE.

				</p>
			</td>
			<td colspan="6" width="50%"
				style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 8px; color: #000; font-size: 12px;">
				<p
					style="color: #000; font-size: 10px; vertical-align: top; text-align: center; margin: 0px; font-weight: normal;">
					For TRAMBAK RUBBER INDUSTRIES LTD. <br>
					<br>
					<br>
					<br> ( Reviewed & Approved )
			</td>
		</tr>


	</table>

	<br>

	<p
		style="color: #000; font-size: 10px; text-align: left; margin: 0px; font-weight: bold;"
		align="center">------------------------------------------------------------------------------------------------------*
		ORDER VALIDITY : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp; DAYS *
		------------------------------------------------------------------------------------------------
	</p>

	<br>


	<h5 style="font-weight: normal; margin: 0px;" align="left">NASHIK
		OFFICE : "MARUTI", 3 & 4, TAGORE NAGAR, OPP. AMBEDKAR NAGAR, NASHIK</h5>

	<h5 style="font-weight: normal; margin: 0px;" align="left">PHONE:
		0253-2410069, FAX : 0253-2417180, Email : purchase@trambakrubber.com</h5>

	<h5 style="font-weight: bold; margin: 0px;" align="left">WORKS:
		A-83, A-86, A-107, A-110, Sinnar Tal. Indl. Estate, SINNAR - 422 103</h5>


	<!-- END Main Content -->

</body>
</html>