<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getMixingListWithDate" value="/getMixingListWithDate"></c:url>
	<c:url var="getMixingAllListWithDate" value="/getMixingAllListWithDate"></c:url>

	<c:url var="getItemListExportToExcel" value="/getItemListExportToExcel" />
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

						<i class="fa fa-file-o"></i>Item List

					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Item List
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem"> Add
									Item</a>&nbsp;&nbsp;&nbsp; <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>&nbsp;&nbsp;&nbsp;
							</div>

						</div>

						<div class="box-content">
						
							<div class="col-md-9"></div>
								<label for="search" class="col-md-3" id="search"> <i
									class="fa fa-search" style="font-size: 20px"></i> <input
									type="text" id="myInput" onkeyup="myFunction()"
									placeholder="Search.." title="Type in a name">
								</label>
								
							<br /> <br />
							
							<form id="validation-form" method="post">
							<input type="hidden" name="itemId" id="itemId" value="0">
							<input type="hidden" name="docType" id="docType" value="0">
							
							
							<div class="form-group">
									
									<label class="col-sm-3 col-lg-2 control-label">To Date</label>
									<div class="col-sm-5 col-lg-3 controls">
										<input class="form-control date-picker" id="date" size="16"
											type="text" name="date" required value="${toDate}" />
									</div>

									<div
										class="col-sm-25 col-sm-offset-3 col-lg-30 col-lg-offset-0">
										<input type="submit" value="Submit"  class="btn btn-primary">
									</div>
								</div>
							
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
											<th style="width:2%;">Sr No</th>
											<th style="width:3%;">Code</th>
											<th class="col-md-5">Item Description</th>  
											<th  style="width:3%;">UOM</th>
											<th class="col-md-2" style="text-align: center;">Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${itemList}" var="itemList"
											varStatus="count">
											<tr>
												<td style="width:2%;"><c:out value="${count.index+1}" /></td>
												<td style="width:3%;" ><c:out
														value="${itemList.itemCode}" /></td>
												 <td class="col-md-5"><c:out
														value="${itemList.itemDesc}" /></td> 
												<td  style="width:3%;"><c:out value="${itemList.itemUom}" /></td>
												<td class="col-md-2"><a
													href="#" onclick="getQueryItemDetail(${itemList.itemId},1)"
													data-toggle="tooltip" title="Indent Items"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp; 
														<a
													href="${pageContext.request.contextPath}/deleteItem/${itemList.itemId}"
													onClick="return confirm('Are you sure want to delete this record');"
													data-toggle="tooltip" title="Delete"><span
														class="glyphicon glyphicon-remove"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;
														<a
													href="${pageContext.request.contextPath}/editItem/${itemList.itemId}"
													data-toggle="tooltip" title="Edit"><span
														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;</td>
											</tr>
										</c:forEach>
									</tbody>

								</table>

							</div>
							
							</form>
							
							
							<div class=" box-content">
									<div class="col-md-12" style="text-align: center">
<!-- 
								<input type="button" id="expExcel" class="btn btn-primary"
							value="EXPORT TO Excel" onclick="exportToExcel();">
							<button class="btn btn-primary" value="PDF" id="PDFButton"
					disabled="disabled" onclick="genPdf()">PDF</button> -->

									</div>
								</div>
							
							
						</div>

					</div>
				</div>
 
			</div>
			<footer>
			<p>2018 © TRAMBAK RUBBER</p>
		</footer>
		</div>

 
		<!-- END Main Content -->
		
 
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
	
		function getQueryItemDetail(itemId,docType) {
			
			alert("Item " +itemId+"doc Type  " +docType);
			document.getElementById("itemId").value=itemId;
			document.getElementById("docType").value=docType;
			
		    var form = document.getElementById("validation-form")
		    form.action ="${pageContext.request.contextPath}/getQueryItemDetail";
		    form.submit();
			//window.open("${pageContext.request.contextPath}/getQueryItemDetail");
			//document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			window.open('${pageContext.request.contextPath}/itemListPdf/');

		}
	</script>
	<script>
function myFunction() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1];
    //td1 = tr[i].getElementsByTagName("td")[2];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }  
    
   /*  if (td1) {
        if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }   */
  }
}
</script>
</body>
</html>