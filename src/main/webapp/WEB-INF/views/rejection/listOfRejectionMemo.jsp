<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getListOfRejectionMemo" value="/getListOfRejectionMemo"></c:url>
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

						<i class="fa fa-file-o"></i>Rejection Memo List

					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Rejection Memo List
							</h3>
							<div class="box-tool">
								<a
									href="${pageContext.request.contextPath}/showRejectionMemo">
									Add Rejection Memo</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class="box-content">

							<div class="box-content">

								<div class="col-md-2">From Date*</div>
								<div class="col-md-3">
									<input id="fromDate" class="form-control date-picker"
										placeholder="From Date" value="${date}" name="fromDate"
										type="text" required>


								</div>
								<div class="col-md-1"></div>
								<div class="col-md-2">To Date*</div>
								<div class="col-md-3">
									<input id="toDate" class="form-control date-picker"
										placeholder="To Date" value="${date}" name="toDate"
										type="text" required>


								</div>


							</div>
							<br> <br>

							
						
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
									<input type="button" class="btn btn-primary" value="Search "
										onclick="search()">
								</div>
							</div>
							<br>

							<div align="center" id="loader" style="display: none">

								<span>
									<h4>
										<font color="#343690">Loading</font>
									</h4>
								</span> <span class="l-1"></span> <span class="l-2"></span> <span
									class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
								<span class="l-6"></span>
							</div>

							<br /> <br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
										<th	style="text-align: left; padding: 0px; align-items: left;"
														width="10%"><input type="checkbox" name="name1"
														value="0" /> &nbsp;&nbsp;&nbsp;Select All</th>
										
											<th class="col-sm-1">Sr no.</th>
											<th class="col-md-1">Rejection Date</th>
											<th class="col-md-1">Vendor Name</th>
											<th class="col-md-1">Mrn No</th>
											<th class="col-md-1">Dco No</th>
											<th class="col-md-1">Dco Date</th>
											<th class="col-md-1">Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${list}" var="list" varStatus="count">
											<tr>
											
												
										<td style="text-align: left; padding: 0px; align-items: center; align-content: center;"
															width="10%">&nbsp;&nbsp;<input type="checkbox"
															name="name1" value="${list.rejectionId}" /></td>
									
												<td class="col-md-1"><c:out value="${count.index+1}" /></td>


												<td class="col-md-1"><c:out
														value="${list.rejectionDate}" /></td>

												<td class="col-md-1"><c:out value="${list.vendorName}" /></td>

												<td class="col-md-1"><c:out value="${list.mrnNo}" /></td>

												<td class="col-md-1"><c:out value="${list.dcoId}" /></td>
												<td class="col-md-1"><c:out value="${list.dcoDate}" /></td>
											



												<td>
												<a href="javascript:genPdf(${ list.rejectionId});"><abbr title="PDF"><i
															class="glyphicon glyphicon glyphicon-file"></i></abbr></a>
													
												
												<a
													href="${pageContext.request.contextPath}/editReturnList/${list.rejectionId}"><abbr
														title="Edit"><i class="fa fa-edit"></i></abbr></a> <a
													href="${pageContext.request.contextPath}/deleteGetpassHeaderReturn/${list.rejectionId}"
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
			<footer>
				<p>2018 © AARYATECH SOLUTIONS</p>
			</footer>
		</div>


	</div>

	<!-- END Content -->

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
		function search() {

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			
			$('#loader').show();

			$
					.getJSON(
							'${getListOfRejectionMemo}',

							{

								fromDate : fromDate,
								toDate : toDate,
							
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
																.html('<input type="checkbox"  name="name1" value="'+ itemList.rejectionId +'"/>'));
														 
													
													
													tr.append($('<td></td>')
															.html(key + 1));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.rejectionDate));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.vendorName));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.mrnNo));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.dcoId));
													tr
													.append($(
															'<td></td>')
															.html(
																	itemList.dcoDate));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			'<a href="javascript:genPdf('+itemList.rejectionId+');"><abbr'+
													'title="PDF"><i class="glyphicon glyphicon glyphicon-file"></i></abbr></a> <a href="${pageContext.request.contextPath}/editRejectionMemo/'+itemList.rejectionId+'"><abbr'+
													'title="Edit"><i class="fa fa-edit"></i></abbr></a> <a href="${pageContext.request.contextPath}/deleteRejectionMemo/'
																					+ itemList.rejectionId
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
				
		
				window.open('pdfForReport?url=rejectionMemoDoc/'
						+ id );

			}
			
			
			function commonPdf() {

				var list = [];

				$("input:checkbox[name=name1]:checked").each(function() {
					list.push($(this).val());
				});

				window.open('pdfForReport?url=rejectionMemoDoc/' + list);

			}
			
			
		</script>
	

</body>
</html>