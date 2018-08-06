<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body onload="FocusOnInput()">


	<c:url var="checkVendCodeExist" value="/checkVendCodeExist"></c:url>
	<c:url var="getMixingAllListWithDate" value="/getMixingAllListWithDate"></c:url>
	<c:url var="exhibitorMobileNo" value="/exhibitorMobileNo"></c:url>

	<c:url var="isMobileNoExist" value="/isMobileNoExist"></c:url>


	<div class="container" id="main-container">

		<!-- BEGIN Sidebar -->
		<div id="sidebar" class="navbar-collapse collapse">

			<jsp:include page="/WEB-INF/views/include/navigation.jsp"></jsp:include>

			<div id="sidebar-collapse" class="visible-lg">
				<i class="fa fa-angle-double-left"></i>
			</div>
			<!-- END Sidebar Collapse Button -->
		</div>
		<!-- END Sidebar -->

		<!-- BEGIN Content -->
		<div id="main-content">
			<!-- BEGIN Page Title -->
			<div class="page-title">
				<div>
					<h1>

						<i class="fa fa-file-o"></i> 
									
									  Vendor

					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>
								 
								 <c:choose>
										<c:when test="${isEdit==1}">
										Edit Vendor
										</c:when>
										<c:otherwise>
										 Add Vendor 
										</c:otherwise>
									</c:choose>
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/vendorList">
									Vendor List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class=" box-content">
							<form id="addSupplier"
								action="${pageContext.request.contextPath}/insertVendor"
								method="post" >

								<div class="box-content">

									<div class="col-md-2">Vendor Code*</div>
									<div class="col-md-3">
									
									<c:choose>
										<c:when test="${isEdit==1}">
										<input id="vendorCode" class="form-control"
									placeholder="Vendor Code" style="text-align: left;"
									name="vendorCode" value="${editVendor.vendorCode}" type="text"
									readonly>  
										</c:when>
			  			 			<c:otherwise>
										<input id="vendorCode" class="form-control"
									placeholder="Vendor Code" maxlength="6" onchange="checkVendCodeExist()" onkeydown="upperCaseF(this)" style="text-align: left;"
									name="vendorCode" value="${editVendor.vendorCode}" type="text"
									required> 
										</c:otherwise>
									</c:choose>
										
									
									
									<input id="vendorId" class="form-control"
									name="vendorId" value="${editVendor.vendorId}" type="hidden">
								  
									</div>
									<div class="col-md-1"></div>
									 
								</div>
								<br> 
								
								<div class="box-content">

									<div class="col-md-2">  Name*</div>
									<div class="col-md-10">
										<input id="vendorName" class="form-control"
									placeholder="Vendor Name" style="text-align: left;"
									name="vendorName" type="text" value="${editVendor.vendorName}"
									required>
									</div>
								</div><br>
								
								<div class="box-content">

									<div class="col-md-2">  Address 1*</div>
									<div class="col-md-3">
										<input id="vendorAdd1" class="form-control"
									placeholder="Vendor Add 1" style="text-align: left;"
									name="vendorAdd1" value="${editVendor.vendorAdd1}" type="text"
									required>

									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">  Address 2*</div>
									<div class="col-md-3">
										<input id="vendorAdd2" class="form-control"
									placeholder="Vendor Add 2" style="text-align: left;"
									name="vendorAdd2" type="text" value="${editVendor.vendorAdd2}"
									required>


									</div> 
								</div>
								<br> 
								<div class="box-content">

									<div class="col-md-2">  Address 3*</div>
									<div class="col-md-3">
										<input id="vendorAdd3" class="form-control"
									placeholder="Vendor Add 3" style="text-align: left;"
									name="vendorAdd3" value="${editVendor.vendorAdd3}" type="text"
									required>

									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">  Address 4*</div>
									<div class="col-md-3">
										<input id="vendorAdd4" class="form-control"
									placeholder="Vendor Add 4" style="text-align: left;"
									name="vendorAdd4" type="text" value="${editVendor.vendorAdd4}"
									required>


									</div>


								</div>
								<br> 
								<div class="box-content">
								
								<div class="col-md-2">  City*</div>
									<div class="col-md-3">
										<input id="vendorCity" class="form-control"
									placeholder="Vendor City" style="text-align: left;"
									name="vendorCity" value="${editVendor.vendorCity}"  type="text"
									required>


									</div>
									
									<div class="col-md-1"></div>
									<div class="col-md-2">Select State*</div>
									<div class="col-md-3">
										<select class="form-control chosen" data-live-search="true"
									title="Please Select" name="stateId" id="stateId"
									onchange="getStateName()" required>

									<c:forEach items="${stateList}" var="stateList">
										<c:choose>
											<c:when test="${stateList.stateId==editVendor.vendorStateId}">
												<option value="${stateList.stateId}" selected>${stateList.stateName}</option>
											</c:when>
											<c:otherwise>
												<option value="${stateList.stateId}">${stateList.stateName}</option>
											</c:otherwise>
										</c:choose>


									</c:forEach>
								</select>


									</div>
							<input id="stateName" name="stateName" type="hidden">
							  
								</div>
								<br> 
								<div class="box-content"> 
								
								<div class="col-md-2">  Contact Person*</div>
									<div class="col-md-3">
										<input id="vendorContactPerson" class="form-control"
									placeholder="Vendor Contact Person" style="text-align: left;"
									name="vendorContactPerson" type="text"
									value="${editVendor.vendorContactPerson}"  required> 

									</div>
									
									<div class="col-md-1"></div>
									<div class="col-md-2">  Mobile*</div>
									<div class="col-md-3">
										<input id="vendorMobile" class="form-control"
									placeholder="Vendor Mobile" style="text-align: left;"
									name="vendorMobile" type="text" pattern="\d{10}"
									value="${editVendor.vendorMobile}" maxlength="10" required>


									</div>


								</div>
								<br> 
								<div class="box-content">

									<div class="col-md-2">  Email*</div>
									<div class="col-md-3">
										<input id="vendorEmail" class="form-control"
									placeholder="Vendor Email" style="text-align: left;"
									name="vendorEmail" value="${editVendor.vendorEmail}"
									type="email" required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">  Phone*</div>
									<div class="col-md-3">
										<input id="vendorPhone" class="form-control"
									placeholder="Vendor Phone" style="text-align: left;"
									name="vendorPhone" type="text"
									value="${editVendor.vendorPhone}" required>


									</div>


								</div>
								<br> 
								<div class="box-content">

									<div class="col-md-2">  Item*</div>
									<div class="col-md-3">
										<input id="vendorItem" class="form-control"
									placeholder="Vendor Item" style="text-align: left;"
									name="vendorItem" value="${editVendor.vendorItem}" type="text"
									required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">  Date*</div>
									<div class="col-md-3">
										<input id="vendorDate" class="form-control date-picker"
									placeholder="Vendor Date" style="text-align: left;"
									name="vendorDate" type="text" value="${editVendor.vendorDate}"
									required>

									</div>


								</div>
								<br> 
								<div class="box-content">

									<div class="col-md-2">  GST NO*</div>
									<div class="col-md-3">
									<input id="vendorGstNo" class="form-control"
									placeholder="Vendor Gst No" style="text-align: left;"
									name="vendorGstNo" maxlength="15" value="${editVendor.vendorGstNo}"
									type="text" required>
									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Select Type*</div>
									<div class="col-md-3">
										<select class="form-control chosen" data-live-search="true"
									title="Please Select" name="vendorType" id="vendorType"
									required>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>

								</select>
									</div>


								</div>
								<br> 
								<br>  

								<div class=" box-content">
									<div class="col-md-12" style="text-align: center">
									 <c:choose>
										<c:when test="${isEdit==1}">
										<input type="submit" class="btn btn-info" value="Submit"
											id="submit">
										</c:when>
										<c:otherwise>
										<input type="submit" class="btn btn-info" value="Submit"
											id="submit" disabled>
										</c:otherwise>
									</c:choose> 
									</div>
								</div>
							</form>


						</div>
					</div>


				</div>
			</div>
			<!-- END Main Content -->
			<footer>
				<p>2018 © TRAMBAK RUBBER</p>
			</footer>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<!--page specific plugin scripts-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>


	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>





	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>


	<script type="text/javascript">
			
	function checkVendCodeExist() {
		
		var vendorCode = $("#vendorCode").val(); 

		$.getJSON('${checkVendCodeExist}', {

			vendorCode : vendorCode,
			ajax : 'true',

		}, function(data) {
			
			if(data==0) 
			{
				document.getElementById("submit").disabled = false;  
			}
			else if(vendorCode=="" || vendorCode==null)
			{
				document.getElementById("submit").disabled = true; 
			}
			else
			{
				alert("Code Is Available ");
				document.getElementById("submit").disabled = true;
			}
	 
		});

	}
	function getStateName() {
		//alert("ala");
		var stateId = $('#stateId option:selected').text();
		//alert("stateId " +stateId);
		document.getElementById("stateName").value = stateId;

	}
	
		function passwordValidation() {

			var pass = document.getElementById("password").value;
			var pass1 = document.getElementById("rePassword").value;

			if (pass != "" && pass1 != "") {
				if (pass != pass1) {
					alert("Password Not Matched ");
					document.getElementById("submit").disabled = true;
				} else {
					document.getElementById("submit").disabled = false;

				}

			}
		}

		function check() {

			var companyTypeId = document.getElementById("companyTypeId").value;
			var location = document.getElementById("location").value;

			if (companyTypeId == "" || companyTypeId == null) {
				alert("Select Company Type");
			} else if (location == "" || location == null) {
				alert("Select Location");
			}
		}
	</script>

	<script type="text/javascript">
		function checkMobileNo() {

			var userMob = $('#usesrMob').val();

			if (userMob.length == 10) {

				$.getJSON('${exhibitorMobileNo}', {
					userMob : userMob,

					ajax : 'true'
				}, function(data) {

					/* 				document.getElementById("").value = data; */
					if (data.exhId == 0) {
						document.getElementById("submit").disabled = false;
					} else {
						document.getElementById("submit").disabled = true;
						alert("Number Already Registered");
					}
				});
			}
		}
		
		function upperCaseF(a){
		    setTimeout(function(){
		        a.value = a.value.toUpperCase();
		    }, 1);
		}
		function FocusOnInput()
		 {
		 document.getElementById("vendorCode").focus();
		 }
	</script>


</body>
</html>