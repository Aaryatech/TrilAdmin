<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<style>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 50px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
<body>

	<c:url var="updateEnabledAndDisabled" value="/updateEnabledAndDisabled"></c:url>
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
			<!-- <div class="page-title">
				<div>
					<h1>

						<i class="fa fa-file-o"></i>Item List

					</h1>
				</div>
			</div> --><br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Indent Sub Document List
							</h3>
							<%-- <div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem"> Add
									Item</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div> --%>

						</div>
						
						<c:set value="0" var="isEdit"></c:set>
								<c:set value="0" var="isDelete"></c:set>
									<c:forEach items="${sessionScope.newModuleList}" var="allModuleList" >
															<c:choose>
																<c:when test="${allModuleList.moduleId==sessionScope.sessionModuleId}">
																	  <c:forEach items="${allModuleList.subModuleJsonList}" var="subModuleJsonList" >
																	  		<c:choose>
																			  	<c:when test="${subModuleJsonList.subModuleId==sessionScope.sessionSubModuleId}">
																			  		  <c:choose>
																			  				<c:when test="${subModuleJsonList.editReject eq 'visible'}">
																			  				<c:set value="1" var="isEdit"></c:set>
																			  				</c:when>
																			  			</c:choose>
																			  		 <c:choose>
																			  				<c:when test="${subModuleJsonList.deleteRejectApprove eq 'visible'}">
																			  				<c:set value="1" var="isDelete"></c:set>
																			  				</c:when>
																			  			</c:choose>
																			  	</c:when>
																		  	</c:choose>
																	  </c:forEach>
																</c:when> 
															</c:choose>
														 
														</c:forEach>

						<div class="box-content">
						
							<div class="col-md-9"></div>
								<label for="search" class="col-md-3" id="search"> <i
									class="fa fa-search" style="font-size: 20px"></i> <input
									type="text" id="myInput" onkeyup="myFunction()"
									placeholder="Search.." title="Type in a name">
								</label>
								
							<br /> <br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
											<th style="width:2%;">Sr No</th>
											<th  >Document Name</th> 
											<th class="col-md-2" style="text-align: right;">Limit</th>
											<th style="width:5%; text-align: right;" >Action</th>
											<th class="col-md-2" style="text-align: right;">Enabled/Disabled</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${list}" var="list"
											varStatus="count">
											<tr>
												<td style="width:2%; vertical-align: middle;"><c:out value="${count.index+1}" /></td>
												<td style=" vertical-align: middle;"><c:out
														value="${list.subDocName}" /></td>
												 
												<td   align="right" style=" vertical-align: middle;"><input style="text-align:right; width:120px" type="text" id="limit${list.subDocId}" name="limit${list.subDocId}" value="${list.categoryPostfix}"  
												class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+" disabled> </td>
												
												<td   align="right" style=" vertical-align: middle;"><span class='glyphicon glyphicon-edit' onclick="edit(${list.subDocId})" id="edit${list.subDocId}"></span>
																
		                                                    <span  style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit(${list.subDocId})" id="ok${list.subDocId}"></span> </td>
												<td align="right"  > 
													<c:choose>
														<c:when test="${list.printPostfix==1}">
															<label class="switch">
															  <input type="checkbox" onchange="enabledDesabledLimit(${list.subDocId})" id="enabled${list.subDocId}" checked>
															  <span class="slider round"></span>
															</label>
														</c:when> 
														<c:when test="${list.printPostfix==0}">
															<label class="switch">
															  <input type="checkbox" onchange="enabledDesabledLimit(${list.subDocId})" id="enabled${list.subDocId}" >
															  <span class="slider round"></span>
															</label>
														</c:when> 
													</c:choose> 
												</td>
											</tr>
										</c:forEach>
									</tbody>

								</table>

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
		function enabledDesabledLimit(subDocId) {
			 
			var enabled;
			var limitValue=parseFloat(document.getElementById("limit"+subDocId).value);
			
			if(document.getElementById("enabled"+subDocId).checked == true){
				 
				enabled=1;
			}else{
				 
				enabled=0;
			}
			
			  $.getJSON('${updateEnabledAndDisabled}', {
				  
				enabled : enabled,
				subDocId : subDocId,
				limitValue : limitValue,
				ajax : 'true',

			}, function(data) {

				 

				if (data.error == false) {
					 alert("Successfully Changed")
				}
				
				 
			});  

		}
		
		function edit(key)
		{
			 
			document.getElementById("limit"+key).disabled = false;
			document.getElementById("edit"+key).style.visibility="hidden";
			document.getElementById("ok"+key).style.visibility="visible";
			 
		}
		function submit(key)
		{
			 
			var limitValue=parseFloat(document.getElementById("limit"+key).value);
			
			if(limitValue>0){
				
				document.getElementById("limit"+key).disabled = true;
				document.getElementById("edit"+key).style.visibility="visible";
				document.getElementById("ok"+key).style.visibility="hidden";
				enabledDesabledLimit(key);
				
			}else{
				alert("Enter Valid Value");
			}
			
			
		  
			
		}
		function exportExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			window.open('${pageContext.request.contextPath}/itemListPdf/');

		}
	</script>
	<script>
function myFunction() {
  var input, filter, table, tr, td,td1, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1];
    td1 = tr[i].getElementsByTagName("td")[2];
    if (td || td1) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      }else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
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