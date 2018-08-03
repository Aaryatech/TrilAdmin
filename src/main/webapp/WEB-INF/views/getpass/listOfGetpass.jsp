<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getGetpassNonByVendId" value="/getGetpassNonByVendId"></c:url>
	<c:url var="getMixingAllListWithDate" value="/getMixingAllListWithDate"></c:url>


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

						<i class="fa fa-file-o"></i>Gate Pass Non Returnable List

					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Gate Pass Non Returnable List
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addGetpassHeader">
									Add Gate Pass Non Returnable</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>


						<div class="box-content">

							<div class="col-md-2">Select Gate Pass Vendor</div>
							<div class="col-md-3">

								<select name="vendId" id="vendId" class="form-control chosen"
									tabindex="6" required>
									<option value="0">All Vendors</option>
									<c:forEach items="${vendorList}" var="vendorList">
										<option value="${vendorList.vendorId}"><c:out
												value="${vendorList.vendorName}"></c:out>
										</option>
									</c:forEach>
								</select>

							</div>

							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
									<input type="button" class="btn btn-primary" value="Search"
										onclick="search()">
								</div>
							</div>
							<br>

						</div>

						<div class="box-content">

							<br /> <br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
										
										<th	style="text-align: left; padding: 0px; align-items: left;"
														width="10%"  align="left"><input type="checkbox" name="name1"
														value="0" /> &nbsp;&nbsp;&nbsp;Select All</th>
														
											<th class="col-sm-1">Sr no.</th>
											<th class="col-md-1">Vendor Name</th>
											<th class="col-md-1">Gate Pass No</th>
											<th class="col-md-1">Return Date</th>
											<th class="col-md-1">Remark</th>
											<th class="col-md-1">Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${passList}" var="passList"
											varStatus="count">
											<tr>
											
											<td	style="text-align: left; padding: 0px; align-items: center; align-content: center;"
															width="10%">&nbsp;&nbsp;<input type="checkbox"
															name="name1" value="${passList.gpId}" /></td>
															
												<td class="col-md-1"><c:out value="${count.index+1}" /></td>


												<td class="col-md-1"><c:out
														value="${passList.vendorName}" /></td>

												<td class="col-md-1"><c:out value="${passList.gpNo}" /></td>

												<td class="col-md-1"><c:out
														value="${passList.gpReturnDate}" /></td>

												<td class="col-md-1"><c:out value="${passList.remark1}" /></td>



												<td>
												
												<a href="javascript:genPdf(${ passList.gpId});"><abbr title="PDF"><i
															class="glyphicon glyphicon glyphicon-file"></i></abbr></a>
												
												<a
													href="${pageContext.request.contextPath}/editGetpassHeader/${passList.gpId}"><abbr
														title="Edit"><i class="fa fa-edit"></i></abbr></a> <a
													href="${pageContext.request.contextPath}/deleteGetpassHeader/${passList.gpId}"
													onClick="return confirm('Are you sure want to delete this record');"><span
														class="glyphicon glyphicon-remove"></span></a></td>

											</tr>
										</c:forEach>

									</tbody>

								</table>

	<br> <br>
										<button
											style="background-color: #008CBA; border: none; color: white; text-align: center; text-decoration: none; display: block; font-size: 12px; cursor: pointer; width: 50px; height: 30px; margin: auto;"
											onclick="commonPdf()">PDF</button>


							</div>
						</div>



					</div>
				</div>


			</div>
		</div>


		<div class=" box-content"></div>

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
	</script>


	<script type="text/javascript">
		function search() {

			var vendId = $("#vendId").val();
			$('#loader').show();

			$
					.getJSON(
							'${getGetpassNonByVendId}',

							{
								vendId : vendId,

								ajax : 'true'

							},
							function(data) {

								$('#table1 td').remove();
								$('#loader').hide();

								if (data == "") {
									alert("No records found !!");

								}

								$
										.each(
												data,
												function(key, itemList) {

													var tr = $('<tr></tr>');
													
													 tr.append($('<td width=10%></td>')
															.html('<input type="checkbox"  name="name1" value="'+ itemList.gpId +'"/>'));
													 
													
													
													tr.append($('<td></td>')
															.html(key + 1));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.vendorName));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.gpNo));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.gpReturnDate));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.remark1));
													tr
															.append($(
																	'<td></td>')
																	.html('<a href="javascript:genPdf('+itemList.gpId+');"><abbr'+
																			'title="PDF"><i class="glyphicon glyphicon glyphicon-file"></i></abbr></a> <a href="${pageContext.request.contextPath}/editGetpassHeader/'+itemList.gpId+'"><abbr'+
													'title="Edit"><i class="fa fa-edit"></i></abbr></a> <a href="${pageContext.request.contextPath}/deleteGetpassHeader/'
																					+ itemList.gpId
																					+ '"'
																					+ 'onClick="return confirm("Are you sure want to delete this record");"><span class="glyphicon glyphicon-remove"></span></a>'));
													$('#table1 tbody').append(
															tr);
												})

							});
		}
	</script>



<script type="text/javascript">
			function genPdf(id) {
			
				window.open('pdfForReport?url=nonReturnableGPDoc/'
						+ id );

			}
			
			
			function commonPdf() {

				var list = [];

				$("input:checkbox[name=name1]:checked").each(function() {
					list.push($(this).val());
				});

				window.open('pdfForReport?url=nonReturnableGPDoc/' + list);

			}
			
			
		</script>






</body>
</html>