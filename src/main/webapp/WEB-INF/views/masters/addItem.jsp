<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>


	<c:url var="getgroupIdByCatId" value="/getgroupIdByCatId"></c:url>
	<c:url var="getSubGroupIdByGroupId" value="/getSubGroupIdByGroupId"></c:url>
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

						<i class="fa fa-file-o"></i> Add Item

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
									<c:when test="${edit==1}">Edit Exhibitor</c:when>
									<c:otherwise>Add Item</c:otherwise>
								</c:choose>
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/getItemList">
									Item List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class=" box-content">
							<form id="addSupplier"
								action="${pageContext.request.contextPath}/insertItem"
								method="post">

								<div class="box-content">

									<div class="col-md-2">Item Code*</div>
									<div class="col-md-3">
										<input id="itemCode" class="form-control"
											placeholder="Item Code" value="${editItem.itemCode}"
											style="text-align: left;" name="itemCode" type="text"
											required> <input id="itemId" class="form-control"
											name="itemId" value="${editItem.itemId}" type="hidden">

									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Item Description*</div>
									<div class="col-md-3">
										<input id="itemDesc" class="form-control"
											placeholder="Item Code" value="${editItem.itemDesc}"
											style="text-align: left;" name="itemDesc" type="text"
											required>
									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">UOM*</div>
									<div class="col-md-3">
										<input id="uom" class="form-control" placeholder="UOM"
											value="${editItem.itemUom}" style="text-align: left;"
											name="uom" type="text" required>

									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Item Date*</div>
									<div class="col-md-3">
										<input id="itemDate" class="form-control date-picker"
											placeholder="Item Date" value="${editItem.itemDate}"
											name="itemDate" type="text" required>


									</div>

								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Select Category*</div>
									<div class="col-md-3">
										<select class="form-control chosen"
											onchange="getgroupIdByCatId()" name="catId" id="catId"
											required>
											<option value="">select</option>
											<c:forEach items="${categoryList}" var="categoryList">
												<c:choose>
													<c:when test="${categoryList.catId==editItem.catId}">
														<option value="${categoryList.catId}" selected>${categoryList.catDesc}</option>
													</c:when>
													<c:otherwise>
														<option value="${categoryList.catId}">${categoryList.catDesc}</option>
													</c:otherwise>
												</c:choose>


											</c:forEach>
										</select>

									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">Select Group*</div>
									<div class="col-md-3">
										<select class="form-control chosen"
											onchange="getSubGroupIdByGroupId()" name="grpId" id="grpId"
											required>
											<c:forEach items="${getItemGroupList}" var="getItemGroupList">
												<c:choose>
													<c:when test="${getItemGroupList.grpId==editItem.grpId}">
														<option value="${getItemGroupList.grpId}" selected>${getItemGroupList.grpCode}</option>
													</c:when>
													<c:otherwise>
														<option value="${getItemGroupList.grpId}">${getItemGroupList.grpCode}</option>
													</c:otherwise>
												</c:choose>


											</c:forEach>

										</select>


									</div>

								</div>
								<br>

								<div class="box-content">


									<div class="col-md-2">Select Sub-Group*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="subGrpId"
											id="subGrpId" required>

											<c:forEach items="${getItemSubGrpList}"
												var="getItemSubGrpList">
												<c:choose>
													<c:when
														test="${getItemSubGrpList.subgrpId==editItem.subGrpId}">
														<option value="${getItemSubGrpList.subgrpId}" selected>${getItemSubGrpList.subgrpDesc}</option>
													</c:when>
													<c:otherwise>
														<option value="${getItemSubGrpList.subgrpId}">${getItemSubGrpList.subgrpDesc}</option>
													</c:otherwise>
												</c:choose>


											</c:forEach>

										</select>


									</div>

								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item OP Rate*</div>
									<div class="col-md-3">
										<input id="opRate" class="form-control"
											placeholder="Item OP Rate" name="opRate"
											value="${editItem.itemOpRate}"
											pattern="[+-]?([0-9]*[.])?[0-9]+" style="text-align: left;"
											title="Enter in Number Formate" type="text" required>

									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item OP Qty*</div>
									<div class="col-md-3">
										<input id="opQty" class="form-control"
											placeholder="Item OP Qty" name="opQty"
											value="${editItem.itemOpQty}" style="text-align: left;"
											title="Enter in Number Formate" type="number" required>


									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item CL Rate*</div>
									<div class="col-md-3">
										<input id="clRate" class="form-control"
											placeholder="Item CL Rate" name="clRate"
											value="${editItem.itemClRate}"
											pattern="[+-]?([0-9]*[.])?[0-9]+" style="text-align: left;"
											title="Enter in Number Formate" type="text" required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item CL Qty*</div>
									<div class="col-md-3">
										<input id="clQty" class="form-control"
											placeholder="Item OP Qty" name="clQty"
											value="${editItem.itemClQty}" style="text-align: left;"
											title="Enter in Number Formate" type="number" required>


									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item Min Level*</div>
									<div class="col-md-3">
										<input id="minLevel" class="form-control"
											placeholder="Item Min Level" name="minLevel"
											value="${editItem.itemMinLevel}" style="text-align: left;"
											title="Enter in Number Formate" type="number" required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item Max Level*</div>
									<div class="col-md-3">
										<input id="maxLevel" class="form-control"
											placeholder="Item Max Level" name="maxLevel"
											value="${editItem.itemMaxLevel}" style="text-align: left;"
											title="Enter in Number Formate" type="number" required>


									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item Rod Level*</div>
									<div class="col-md-3">
										<input id="rodLevel" class="form-control"
											placeholder="Item Rod Level" name="rodLevel"
											value="${editItem.itemRodLevel}" style="text-align: left;"
											title="Enter in Number Formate" type="number" required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item Weight*</div>
									<div class="col-md-3">
										<input id="itemWeight" class="form-control"
											placeholder="Item Weight" name="itemWeight"
											value="${editItem.itemWt}" pattern="[+-]?([0-9]*[.])?[0-9]+"
											style="text-align: left;" title="Enter in Number Formate"
											type="text" required>


									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item Location*</div>
									<div class="col-md-3">
										<input id="itemLocation" class="form-control"
											placeholder="Item Rod Level" name="itemLocation"
											value="${editItem.itemLocation}" style="text-align: left;"
											type="text" required>


									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item Abc*</div>
									<div class="col-md-3">
										<input id="itemAbc" class="form-control"
											placeholder="Item Abc" name="itemAbc"
											style="text-align: left;" value="${editItem.itemAbc}"
											type="text" required>


									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item Life*</div>
									<div class="col-md-3">
										<input id="itemLife" class="form-control"
											placeholder="Item Life" name="itemLife"
											style="text-align: left;" value="${editItem.itemLife}"
											type="text" required>

									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item Schd*</div>
									<div class="col-md-3">
										<input id="itemSchd" class="form-control"
											placeholder="Item Schd" name="itemSchd"
											style="text-align: left;" value="${editItem.itemSchd}"
											type="text" required>


									</div>


								</div>
								<br>
								<div class="box-content">

									<div class="col-md-2">Item Is Critical*</div>
									<div class="col-md-3">
										<select class="form-control chosen" title="Please Select"
											name="isCritical" id="isCritical" required>
											<c:choose>
												<c:when test="${editItem.itemIsCritical==0}">
													<option value="0" selected>NO</option>
													<option value="1">YES</option>
												</c:when>
												<c:when test="${editItem.itemIsCritical==1}">
													<option value="0">NO</option>
													<option value="1" selected>YES</option>
												</c:when>
												<c:otherwise>
													<option value="0">NO</option>
													<option value="1">YES</option>
												</c:otherwise>

											</c:choose>

										</select>
									</div>

									<div class="col-md-1"></div>
									<div class="col-md-2">Item Is Capital*</div>
									<div class="col-md-3">
										<select class="form-control chosen" title="Please Select"
											name="isCapital" id="isCapital" required>
											<c:choose>
												<c:when test="${editItem.itemIsCapital==0}">
													<option value="0" selected>NO</option>
													<option value="1">YES</option>
												</c:when>
												<c:when test="${editItem.itemIsCapital==1}">
													<option value="0">NO</option>
													<option value="1" selected>YES</option>
												</c:when>
												<c:otherwise>
													<option value="0">NO</option>
													<option value="1">YES</option>
												</c:otherwise>

											</c:choose>

										</select>

									</div>


								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Item Con*</div>
									<div class="col-md-3">
										<select class="form-control chosen" title="Please Select"
											name="itemCon" id="itemCon" required>


											<c:choose>
												<c:when test="${editItem.itemIsCons==0}">
													<option value="0" selected>NO</option>
													<option value="1">YES</option>
												</c:when>
												<c:when test="${editItem.itemIsCons==1}">
													<option value="0">NO</option>
													<option value="1" selected>YES</option>
												</c:when>
												<c:otherwise>
													<option value="0">NO</option>
													<option value="1">YES</option>
												</c:otherwise>

											</c:choose>

										</select>
									</div>

									<div class="col-md-1"></div>

								</div>

								<br>

								<div class=" box-content">
									<div class="col-md-12" style="text-align: center">


										<input type="submit" class="btn btn-info" value="Submit"
											id="submit">

									</div>
								</div>
							</form>


						</div>
					</div>


				</div>
			</div>
			<!-- END Main Content -->
			<footer>
				<p>2018 © AARYATECH SOLUTIONS</p>
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
		function getgroupIdByCatId() {

			var catId = document.getElementById("catId").value;

			$.getJSON('${getgroupIdByCatId}', {

				catId : catId,
				ajax : 'true'
			}, function(data) {

				var html = '<option value="">Select Category</option>';

				var len = data.length;
				for (var i = 0; i < len; i++) {
					html += '<option value="' + data[i].grpId + '">'
							+ data[i].grpCode + '</option>';
				}
				html += '</option>';
				$('#grpId').html(html);
				$("#grpId").trigger("chosen:updated");
			});
		}

		function getSubGroupIdByGroupId() {

			var grpId = document.getElementById("grpId").value;

			$.getJSON('${getSubGroupIdByGroupId}', {

				grpId : grpId,
				ajax : 'true'
			}, function(data) {

				var html = '<option value="">Select Sub-Category</option>';

				var len = data.length;
				for (var i = 0; i < len; i++) {
					html += '<option value="' + data[i].subgrpId + '">'
							+ data[i].subgrpDesc + '</option>';
				}
				html += '</option>';
				$('#subGrpId').html(html);
				$("#subGrpId").trigger("chosen:updated");
			});
		}
	</script>


</body>
</html>