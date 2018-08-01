<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />
	<body>
	
	 
	  <c:url var="getItemIdByGroupId" value="/getItemIdByGroupId"></c:url>
	  <c:url var="getSubDeptList" value="/getSubDeptList"></c:url>
	  <c:url var="getInvoiceNo" value="/getInvoiceNo" />
      <c:url var="addItmeInIssueList" value="/addItmeInIssueList"></c:url>
	  <c:url var="editItemInIssueList" value="/editItemInIssueList"></c:url>
	  <c:url var="deleteItemFromIssueList" value="/deleteItemFromIssueList"></c:url>

	  <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


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
						<i class="fa fa-file-o"></i>Add Issue Receipt
					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Add Issue Receipt
							</h3>
							
							<div class="box-tool">
								  <a href="${pageContext.request.contextPath}/issueList">Issue List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>  
							</div>
							
						</div>
						
						
						<div class="box-content">

							<form id="submitMaterialStore" action="${pageContext.request.contextPath}/submitIssueReciept" method="post" >
							 
							
							<div class="box-content">
							
								<div class="col-md-2">Issue No*</div>
									<div class="col-md-3">
									
									<input id="issueNo" class="form-control"
								 placeholder="Issue No" value="1" name="issueNo" type="text" readonly>
									
									</div>
								<!-- <div class="col-md-2">Enquiry Remark</div>
									<div class="col-md-3">
									<input class="form-control" id="enqRemark" size="16"
									 placeholder="Enquiry Remark"		type="text" name="enqRemark"    />
									</div>
								 -->
				 
							</div><br>
							
							<div class="box-content">
							
								<div class="col-md-2">Issue Date*</div>
									<div class="col-md-3">
										<input id="issueDate" class="form-control date-picker"
								 placeholder="Issue Date"  name="issueDate" type="text" value="" onblur="getInvoiceNo()"  required>


									</div>
								
				 
							</div><br>
							
							  
							<hr/>
							
							<div class="box-content">
								
									<div class="col-md-2" >Select Group</div>
									<div class="col-md-3">
										<select   class="form-control chosen" name="groupId" onchange="getItemIdByGroupId()" tabindex="-1" id="groupId"  >
											<option   value="">Select Group</option>
											
											<c:forEach items="${itemGroupList}" var="itemGroupList"> 
														<option value="${itemGroupList.grpId}"> ${itemGroupList.grpCode} &nbsp;&nbsp; ${itemGroupList.grpDesc} </option>
											 </c:forEach>
											</select>
									</div>
									<input type="hidden" name=editIndex id="editIndex"   />
									
									<div class="col-md-2" >Select Item</div>
									<div class="col-md-3">
										<select   class="form-control chosen" name="itemId" tabindex="-1" id="itemId"  >
										 
											</select>
									</div>
									
								  
								</div><br> 
							
							<div class="box-content">
								 
								 <div class="col-md-2" >Select Department</div>
									<div class="col-md-3">
										<select   class="form-control chosen" name="deptId" onchange="getSubDeptListByDeptId()" tabindex="-1" id="deptId"  >
											<option   value="">Select Department</option>
											
											<c:forEach items="${deparmentList}" var="deparmentList"> 
														<option value="${deparmentList.deptId}">${deparmentList.deptCode} &nbsp;&nbsp;&nbsp; ${deparmentList.deptDesc} </option>
											 </c:forEach>
											</select>
									</div>
									
									<div class="col-md-2" >Select Sub Department</div>
									<div class="col-md-3">
										<select   class="form-control chosen" name="subDeptId" tabindex="-1" id="subDeptId"  >
											 
											</select>
									</div>
									 
								</div><br> 
								
								<div class="box-content">
								
								<div class="col-md-2" >Select Account Head</div>
									<div class="col-md-3">
										<select   class="form-control chosen" name="acc" tabindex="-1" id="acc"  >
											<option   value="">Select Account Head</option>
											
											<c:forEach items="${accountHeadList}" var="accountHeadList"> 
														<option value="${accountHeadList.accHeadId}"><c:out value="${accountHeadList.accHeadDesc}"></c:out> </option>
											 </c:forEach>
											</select>
									</div>
							
								<div class="col-md-2">Qty</div>
									<div class="col-md-3">
										<input type="text" name="qty" id="qty"
											placeholder="Qty" class="form-control"
											 pattern="\d+"  />
												 
									</div>


									</div>
								 
							 <br>
							<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
										<input type="button" class="btn btn-primary" value="Add Item" onclick="addItem()">  
									</div>
								</div><br><br>
							
								<div align="center" id="loader" style="display: none">

							<span>
								<h4>
									<font color="#343690">Loading</font>
								</h4>
							</span> <span class="l-1"></span> <span class="l-2"></span> <span
						class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
					<span class="l-6"></span>
				</div>
											
							
							
							<div class=" box-content">
					<div class="row">
						<div class="col-md-12 table-responsive">
							<table class="table table-bordered table-striped fill-head "
								style="width: 100%" id="table_grid">
								<thead>
									<tr>
										<th>Sr.No.</th>
										<th>Group Name</th>
										<th>Item Name</th> 
										<th>Department</th>
										<th>Sub Department</th>
										<th>Qty</th> 
										<th>Action</th> 
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
								</div>
								
							<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
										<input type="submit" class="btn btn-primary" value="Submit" onclick="check();">
<!-- 										<button type="button" class="btn">Cancel</button>
 -->									</div>
								</div><br><br>
						
							
							

						</form>
						</div>	
						</div>
					</div>
				</div>
				
				<footer>
			<p>2017 © MONGINIS.</p>
			</footer>
			</div>
			
			<!-- END Main Content -->
			

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		
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
		
		function getItemIdByGroupId() {

			var grpId = document.getElementById("groupId").value;

			$.getJSON('${getItemIdByGroupId}', {

				grpId : grpId,
				ajax : 'true'
			}, function(data) {

				var html = '<option value="">Select ItemGroup</option>';

				var len = data.length;
				for (var i = 0; i < len; i++) {
					html += '<option value="' + data[i].itemId + '">'
							+ data[i].itemCode + '&nbsp;&nbsp;&nbsp;'+data[i].itemDesc+'</option>';
				}
				html += '</option>';
				$('#itemId').html(html);
				$("#itemId").trigger("chosen:updated");
			});
		}
		
		function getSubDeptListByDeptId() {

			var deptId = document.getElementById("deptId").value;

			$.getJSON('${getSubDeptList}', {

				deptId : deptId,
				ajax : 'true'
			}, function(data) {

				var html = '<option value="">Select Sub Department</option>';

				var len = data.length;
				for (var i = 0; i < len; i++) {
					html += '<option value="' + data[i].subDeptId + '">'
							+ data[i].subDeptCode +'&nbsp;&nbsp;&nbsp;s'+data[i].subDeptDesc+'</option>';
				}
				html += '</option>';
				$('#subDeptId').html(html);
				$("#subDeptId").trigger("chosen:updated");
			});
		}
		
		function addItem() {
			  
			 
				var itemId = $("#itemId").val();
				var itemName = $("#itemId option:selected").text();
				var qty = $("#qty").val();
				var groupId = $("#groupId").val();
				var groupName = $("#groupId option:selected").text();
				var deptId = $("#deptId").val();
				var deptName = $("#deptId option:selected").text();
				var subDeptId = $("#subDeptId").val();
				var subDeptName = $("#subDeptId option:selected").text();
				var acc = $("#acc").val();
				var accName = $("#acc option:selected").text();
				var editIndex = $("#editIndex").val();
				
				if(validation()==true){	
					
				
				$('#loader').show();

				$
						.getJSON(
								'${addItmeInIssueList}',

								{
									 
									itemId : itemId, 
									qty : qty,
									groupId : groupId,
									deptId : deptId,
									subDeptId : subDeptId,
									editIndex : editIndex,
									acc : acc,
									itemName : itemName,
									groupName :groupName,
									deptName : deptName,
									subDeptName : subDeptName,
									accName : accName,
									ajax : 'true'

								},
								function(data) {

									$('#table_grid td').remove();
									$('#loader').hide();

									if (data == "") {
										alert("No records found !!");

									}
								 
  
								  $.each(
												data,
												function(key, itemList) {
												

													var tr = $('<tr></tr>'); 
												  	tr.append($('<td></td>').html(key+1)); 
												  	tr.append($('<td></td>').html(itemList.groupName)); 
												  	tr.append($('<td></td>').html(itemList.itemName));
												  	tr.append($('<td></td>').html(itemList.deptName));
												  	tr.append($('<td></td>').html(itemList.subDeptName));
												  	tr.append($('<td></td>').html(itemList.itemIssueQty));
												  	tr.append($('<td></td>').html('<span class="glyphicon glyphicon-edit" id="edit'+key+'" onclick="edit('+key+');"> </span><span class="glyphicon glyphicon-remove"  onclick="del('+key+')" id="del'+key+'"></span>'));
												    $('#table_grid tbody').append(tr);
 
												})  
												
									document.getElementById("qty").value= "";
									document.getElementById("groupId").value= "";
									$('#groupId').trigger("chosen:updated");
									document.getElementById("deptId").value= ""; 
									$('#deptId').trigger("chosen:updated");
									document.getElementById("itemId").value= "";
									$('#itemId').trigger("chosen:updated");
									document.getElementById("subDeptId").value= "";
									$('#subDeptId').trigger("chosen:updated");
									document.getElementById("acc").value= "";
									$('#acc').trigger("chosen:updated");
									document.getElementById("editIndex").value="";
								});

			 
		}
				
				
			
	}
		
		function edit(key)
		{
			//alert(key);
			$('#loader').show();

			$
					.getJSON(
							'${editItemInIssueList}',

							{
								 
								index : key, 
								ajax : 'true'

							},
							function(data) {

								 
								$('#loader').hide();
								document.getElementById("editIndex").value=key;
								document.getElementById("qty").value=data.itemIssueQty;
								document.getElementById("groupId").value=data.itemGroupId;
								$('#groupId').trigger("chosen:updated");
								document.getElementById("deptId").value=data.deptId;
								$('#deptId').trigger("chosen:updated");
								document.getElementById("acc").value=data.accHead;
								$('#acc').trigger("chosen:updated");
								
								$.getJSON('${getItemIdByGroupId}', {

									grpId : data.itemGroupId,
									ajax : 'true'
								}, function(data1) {

									var html = '<option value="">Select ItemGroup</option>';

									var len = data1.length;
									for (var i = 0; i < len; i++) {
										if(data1[i].itemId==data.itemId)
											{
											html += '<option value="' + data1[i].itemId + '" selected>'
											+ data1[i].itemCode + '</option>';
											}
										else
											{
											html += '<option value="' + data1[i].itemId + '">'
											+ data1[i].itemCode + '</option>';
											}
										
									}
									html += '</option>';
									$('#itemId').html(html);
									$("#itemId").trigger("chosen:updated");
								});
								
								$.getJSON('${getSubDeptList}', {

									deptId : data.deptId,
									ajax : 'true'
								}, function(data2) {

									var html = '<option value="">Select Sub Department</option>';

									var len = data2.length;
									for (var i = 0; i < len; i++) {
										
										if(data2[i].subDeptId==data.subDeptId)
											{
											html += '<option value="' + data2[i].subDeptId + '" selected>'
											+ data2[i].subDeptCode + '</option>';
											}
										else
											{
											html += '<option value="' + data2[i].subDeptId + '">'
											+ data2[i].subDeptCode + '</option>';
											}
										
									}
									html += '</option>';
									$('#subDeptId').html(html);
									$("#subDeptId").trigger("chosen:updated");
								});
								
								
							});
			 
		}
		
		 
		function del(key)
		{
			
			var key=key;
			$('#loader').show();
			$
			.getJSON(
					'${deleteItemFromIssueList}',

					{
						 
						index : key,
						ajax : 'true'

					},
					function(data) {
						
						$('#table_grid td').remove();
						$('#loader').hide();

						if (data == "") {
							alert("No records found !!");

						}
					 

					  $.each(
									data,
									function(key, itemList) {
									 
										var tr = $('<tr></tr>'); 
									  	tr.append($('<td></td>').html(key+1)); 
									  	tr.append($('<td></td>').html(itemList.groupName)); 
									  	tr.append($('<td></td>').html(itemList.itemName));
									  	tr.append($('<td></td>').html(itemList.deptName));
									  	tr.append($('<td></td>').html(itemList.subDeptName));
									  	tr.append($('<td></td>').html(itemList.itemIssueQty));
									  	tr.append($('<td></td>').html('<span class="glyphicon glyphicon-edit" id="edit'+key+'" onclick="edit('+key+');"> </span><span class="glyphicon glyphicon-remove"  onclick="del('+key+')" id="del'+key+'"></span>'));
									    $('#table_grid tbody').append(tr);
									  	
									})
						
					});
			
			
		}
	</script>
<script type="text/javascript">
function validation()
{
	var itemId = $("#itemId").val();
	var qty = $("#qty").val();
	var groupId = $("#groupId").val();
	var deptId = $("#deptId").val();
	var subDeptId = $("#subDeptId").val();
	
	var isValid = true;
	if(itemId=="" || itemId==null)
	{
	isValid = false;
	alert("Please Select Item ");
	}
	
	if(groupId=="" || groupId==null)
	{
	isValid = false;
	alert("Please Select Group ");
	}
	
	if(deptId=="" || deptId==null)
	{
	isValid = false;
	alert("Please Select Department ");
	}
	
	if(subDeptId=="" || subDeptId==null)
	{
	isValid = false;
	alert("Please Select SubGroup  ");
	}
	
	else if(isNaN(qty) || qty < 0 || qty=="")
	{
	isValid = false;
	alert("Please enter Quantity");
	}
	
	  
return isValid;
	
}
 
</script>
	<script type="text/javascript">

function getInvoiceNo() {

	var date = $("#issueDate").val();

	$.getJSON('${getInvoiceNo}', {

		catId:1,
		docId:6,
		date : date,
		ajax : 'true',

	}, function(data) { 
		
	document.getElementById("issueNo").value=data.code;  
	
	});

}

</script>
								
							
	
</body>
</html>