

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



</head>
<body>
	--%>

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<c:url var="editFrSupplier" value="/editFrSupplier"></c:url>

	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.css" />
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.js"></script>

	<!--datepicker-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#fromdatepicker").datepicker({
				dateFormat : 'dd-mm-yy'
			});
		});
		$(function() {
			$("#todatepicker").datepicker({
				dateFormat : 'dd-mm-yy'
			});
		});
	</script>
	<!--datepicker-->

	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<c:url var="findAddOnRate" value="/getAddOnRate" />
		<c:url var="findItemsByCatId" value="/getFlavourBySpfId" />
		<c:url var="findAllMenus" value="/getAllTypes" />
		<jsp:include page="/WEB-INF/views/include/logo.jsp"></jsp:include>


		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">

				<!--leftNav-->

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />
				</jsp:include>



				<div class="sidebarright">

					<form name="frm_search" id="frm_search" method="post"
						action="${pageContext.request.contextPath}/insertVendor">

						<div class="col-md -3">

							<div class="col1title" align="left">
								<h3>Add Vendor</h3>
							</div>

						</div>

						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Code*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorCode" class="form-control"
									placeholder="Vendor Code" style="text-align: left;"
									name="vendorCode" value="${editVendor.vendorCode}" type="text"
									required> <input id="vendorId" class="form-control"
									name="vendorId" value="${editVendor.vendorId}" type="hidden">

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Name*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorName" class="form-control"
									placeholder="Vendor Name" style="text-align: left;"
									name="vendorName" type="text" value="${editVendor.vendorName}"
									required>


							</div>


						</div>


						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Add 1*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorAdd1" class="form-control"
									placeholder="Vendor Add 1" style="text-align: left;"
									name="vendorAdd1" value="${editVendor.vendorAdd1}" type="text"
									required>

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Add 2*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorAdd2" class="form-control"
									placeholder="Vendor Add 2" style="text-align: left;"
									name="vendorAdd2" type="text" value="${editVendor.vendorAdd2}"
									required>


							</div>


						</div>

						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Add 3*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorAdd3" class="form-control"
									placeholder="Vendor Add 3" style="text-align: left;"
									name="vendorAdd3" value="${editVendor.vendorAdd3}" type="text"
									required>

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Add 4*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorAdd4" class="form-control"
									placeholder="Vendor Add 4" style="text-align: left;"
									name="vendorAdd4" type="text" value="${editVendor.vendorAdd4}"
									required>


							</div>


						</div>

						<div class="colOuter">


							<div class="col-md-2">
								<div class="col1title" align="left">Select Vendor State*:</div>
							</div>

							<div class="col-md-2">
								<select class="selectpicker" data-live-search="true"
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


							<div class="col-md-2"></div>
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Contact
									Person*:</div>
							</div>

							<div class="col-md-3">
								<input id="vendorContactPerson" class="form-control"
									placeholder="Vendor Contact Person" style="text-align: left;"
									name="vendorContactPerson" type="text"
									value="${editVendor.vendorContactPerson}" required>

							</div>
						</div>

						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor City*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorCity" class="form-control"
									placeholder="Vendor City" style="text-align: left;"
									name="vendorCity" value="${editVendor.vendorCity}" type="text"
									required>

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Mobile*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorMobile" class="form-control"
									placeholder="Vendor Mobile" style="text-align: left;"
									name="vendorMobile" type="text"
									value="${editVendor.vendorMobile}" required>


							</div>


						</div>


						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Email*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorEmail" class="form-control"
									placeholder="Vendor Email" style="text-align: left;"
									name="vendorEmail" value="${editVendor.vendorEmail}"
									type="text" required>

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Phone*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorPhone" class="form-control"
									placeholder="Vendor Phone" style="text-align: left;"
									name="vendorPhone" type="text"
									value="${editVendor.vendorPhone}" required>


							</div>
						</div>
						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Item*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorItem" class="form-control"
									placeholder="Vendor Item" style="text-align: left;"
									name="vendorItem" value="${editVendor.vendorItem}" type="text"
									required>

							</div>
							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Date*:</div>
							</div>
							<div class="col-md-3">
								<input id="fromdatepicker" class="form-control"
									placeholder="Vendor Date" style="text-align: left;"
									name="vendorDate" type="text" value="${editVendor.vendorDate}"
									required>


							</div>
						</div>

						<div class="colOuter">
							<div class="col-md-2">
								<div class="col1title" align="left">Vendor Gst NO*:</div>
							</div>
							<div class="col-md-3">
								<input id="vendorGstNo" class="form-control"
									placeholder="Vendor Gst No" style="text-align: left;"
									name="vendorGstNo" value="${editVendor.vendorGstNo}"
									type="text" required>

							</div>

							<div class="col-md-1"></div>
							<div class="col-md-2">
								<div class="col1title" align="left">Select Vendor Type*:</div>
							</div>

							<div class="col-md-2">
								<select class="selectpicker" data-live-search="true"
									title="Please Select" name="vendorType" id="vendorType"
									required>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>

								</select>
							</div>
						</div>
				</div>
				<div class="colOuter">
					<div align="center">
						<input name="submit" class="buttonsaveorder" value="Submit"
							type="submit" align="center">
						<!-- <input type="button" class="buttonsaveorder" value="Cancel" id="cancel" onclick="cancel1()" disabled> -->
					</div>

				</div>
				<%-- 
							<div id="table-scroll" class="table-scroll">
								<div id="faux-table" class="faux-table" aria="hidden"></div>
								<div class="table-wrap table-wrap-custbill">
									<table id="table_grid1" class="main-table small-td">
										<thead>
											<tr class="bgpink">
												<th class="col-sm-1">Sr no.</th>
												<th class="col-md-2">Group Code</th>
												<th class="col-md-2">Group Description</th>
												<th class="col-md-2">Group Value</th>
												<th class="col-md-2">Category</th>
												<th class="col-md-1">Action</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${itemGroupList}" var="itemGroupList"
												varStatus="count">
												<tr>
													<td class="col-md-1"><c:out value="${count.index+1}" /></td>


													<td class="col-md-1"><c:out
															value="${itemGroupList.grpCode}" /></td>

													<td class="col-md-1"><c:out
															value="${itemGroupList.grpDesc}" /></td>

													<td class="col-md-1"><c:out
															value="${itemGroupList.grpValueyn}" /></td>

													<td class="col-md-1"><c:out
															value="${itemGroupList.catDesc}" /></td>


													<td><a
														href="${pageContext.request.contextPath}/editItemGroup/${itemGroupList.grpId}"><abbr
															title="Edit"><i class="fa fa-edit"></i></abbr></a>&nbsp;&nbsp;
														<a
														href="${pageContext.request.contextPath}/deleteItemGroup/${itemGroupList.grpId}"><abbr
															title="Delete"><i onclick="del('+key+')"
																class="fa fa-trash"></i></abbr></a></td>

												</tr>
											</c:forEach>

										</tbody>

									</table>
								</div>
							</div> --%>
				</form>


			</div>
			<!--tabNavigation-->
			<!--<div class="order-btn"><a href="#" class="saveOrder">SAVE ORDER</a></div>-->
			<%-- <div class="order-btn textcenter">
						<a
							href="${pageContext.request.contextPath}/showBillDetailProcess/${billNo}"
							class="buttonsaveorder">VIEW DETAILS</a>
						<!--<input name="" class="buttonsaveorder" value="EXPORT TO EXCEL" type="button">-->
					</div> --%>


		</div>
		<!--rightSidebar-->

	</div>
	<!--fullGrid-->
	</div>
	<!--rightContainer-->

	</div>
	<!--wrapper-end-->
	<!--easyTabs-->
	<!--easyTabs-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--easyTabs-->


	<script>
		function edit(suppId) {

			$('#loader').show();

			$
					.getJSON(
							'${editFrSupplier}',

							{

								suppId : suppId,
								ajax : 'true'

							},
							function(data) {

								document.getElementById("suppId").value = data.suppId;
								document.getElementById("suppName").value = data.suppName;
								document.getElementById("suppAdd").value = data.suppAddr;
								document.getElementById("city").value = data.suppCity;
								document.getElementById("mob").value = data.mobileNo;
								document.getElementById("email").value = data.email;
								document.getElementById("gstnNo").value = data.gstnNo;
								document.getElementById("panNo").value = data.panNo;
								document.getElementById("liceNo").value = data.suppFdaLic;
								document.getElementById("creditDays").value = data.suppCreditDays;
								document.getElementById("isSameState").value = data.isSameState;
								document.getElementById("cancel").disabled = false;
							});

		}

		function cancel1() {

			//alert("cancel");
			document.getElementById("suppId").value = "";
			document.getElementById("suppName").value = "";
			document.getElementById("suppAdd").value = "";
			document.getElementById("city").value = "";
			document.getElementById("mob").value = "";
			document.getElementById("email").value = "";
			document.getElementById("gstnNo").value = "";
			document.getElementById("panNo").value = "";
			document.getElementById("liceNo").value = "";
			document.getElementById("creditDays").value = "";
			document.getElementById("isSameState").value = "";
			document.getElementById("cancel").disabled = false;

		}
		(function() {
			var fauxTable = document.getElementById("faux-table");
			var mainTable = document.getElementById("table_grid");
			var clonedElement = table_grid.cloneNode(true);
			var clonedElement2 = table_grid.cloneNode(true);
			clonedElement.id = "";
			clonedElement2.id = "";
			fauxTable.appendChild(clonedElement);
			fauxTable.appendChild(clonedElement2);
		})();
	</script>
	<script>
		function getStateName() {
			//alert("ala");
			var stateId = $('#stateId option:selected').text();
			//alert("stateId " +stateId);
			document.getElementById("stateName").value = stateId;

		}
	</script>

</body>
</html>
