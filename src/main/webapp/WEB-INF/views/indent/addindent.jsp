<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<body>
	<%-- <jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include> --%>

	<c:url var="getSubDeptListByDeptId" value="/getSubDeptListByDeptId" />
	<c:url var="getgroupListByCatId" value="/getgroupListByCatId" />

	<c:url var="getIndentDetail" value="/getIndentDetail" />
	<c:url var="getInvoiceNo" value="/getInvoiceNo" />

	<c:url var="itemListByGroupId" value="/itemListByGroupId" />
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
						<i class="fa fa-file-o"></i>Add Indent
					</h1>

				</div>
			</div>
			<!-- END Page Title -->
			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i>Add Indent
							</h3>
							<div class="box-tool">
								<!-- <a href="">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> -->
							</div>

						</div>


						<div class="box-content">
							<form method="post" class="form-horizontal" id="validation-form">

								<div class="box-content">

									<label class="col-sm-3 col-lg-2 control-label">Indent
										Category </label>
									<div class="col-sm-6 col-lg-4 controls">

										<select id="ind_cat" name="ind_cat"
											class="form-control chosen" placeholder="Indent Category" onchange="getInvoiceNo()"
											data-rule-required="true">
											<option value="">Select Indent Category</option>
											<c:forEach items="${categoryList}" var="cat"
												varStatus="count">
												<option value="${cat.catId}"><c:out value="${cat.catDesc}"/></option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-3 col-lg-2 control-label">Date</label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="indent_date" onblur="getInvoiceNo()"
											size="16" type="text" name="indent_date" value="${date}"
											required data-rule-required="true" />
									</div>

								</div>
								<br/>

								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Indent
										Type</label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="indent_type" id="indent_type"
											data-rule-required="true" class="form-control chosen"
											placeholder="Type" data-rule-required="true">
											<option value="">Select Indent Type</option>
											<option value="1">Regular</option>
											<option value="2">Job Work</option>
											<option value="3">General</option>
											<option value="4">Other</option>
										</select>
									</div>


									<label class="col-sm-3 col-lg-2 control-label">Indent
										No.</label>
									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="indent_no" id="indent_no"
											class="form-control" placeholder="Indent No"
											data-rule-required="true" />
									</div>
								</div>
<br/>
								<div class="box-content">


									<label class="col-sm-3 col-lg-2 control-label">Account
										Head</label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="acc_head" id="acc_head"
											class="form-control chosen" placeholder="Account Head"
											data-rule-required="true">
											<option value="">Select Account Head</option>
											<c:forEach items="${accountHeadList}" var="accHead"
												varStatus="count">
												<option value="${accHead.accHeadId}"><c:out value="${accHead.accHeadDesc}"/></option>
											</c:forEach>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Machine
										Specific</label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="machine_specific" id="machine_specific"
											onchange="showDept()" class="form-control chosen"
											placeholder="Is Machine Specific" data-rule-required="true">
											<option selected value="0">No</option>
											<option value="1">Yes</option>
										</select>
									</div>
								</div>
								<br/>
								
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">For
										Development </label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="is_dev" id="is_dev" class="form-control"
											placeholder="Is Dev" data-rule-required="true">
											<option value="0">No</option>
											<option value="1">Yes</option>

										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">For
										Monthly </label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="is_monthly" id="is_monthly" class="form-control"
											placeholder="Is Monthly" data-rule-required="true">
											<option value="0">No</option>
											<option value="1">Yes</option>
										</select>
									</div>
								</div>
								<br/>

								<div class="box-content" style="display: none" id="deptDiv">
									<label class="col-sm-3 col-lg-2 control-label">Department
									</label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="dept" id="dept" class="form-control chosen"
											placeholder="Department" data-rule-required="true">
											<c:forEach items="${deparmentList}" var="dept"
												varStatus="count">
												<option value="${dept.deptId}"><c:out value="${dept.deptDesc}"/></option>
											</c:forEach>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Sub
										Department </label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="sub_dept" id="sub_dept"
											class="form-control chosen" placeholder="Sub Department"
											data-rule-required="true">

										</select>
									</div>

								</div>
							
								
								<hr />
								<span style="text-align: left; font-weight: bold;font-size: 20px;">Add Item</span>
								
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Group </label>
									<div class="col-sm-6 col-lg-10 controls">

										<select name="group" id="group" class="form-control chosen"
											placeholder="Group" data-rule-required="true">
										</select>
									</div>
									<!-- <label class="col-sm-3 col-lg-2 control-label">Quantity</label>
									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="quantity" id="quantity"
											class="form-control" placeholder="Quantity"
											data-rule-required="true" />
									</div> -->
								</div>
								<br>

								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Item
										Name </label>
									<div class="col-sm-6 col-lg-10 controls">

										<select id="item_name" name="item_name"
											class="form-control chosen" placeholder="Item Name"
											data-rule-required="true">

										</select>
									</div>
								</div>
								<br>
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Quantity</label>
									<div class="col-sm-6 col-lg-2 controls">
										<input type="text" name="quantity" id="quantity"
											class="form-control" placeholder="Quantity"
											data-rule-required="true" data-rule-number="true" />
									</div>
									<!-- </div>

								<div class="form-group"> -->

									<label class="col-sm-3 col-lg-2 control-label">Schedule
										Days</label>
									<div class="col-sm-3 col-lg-2 controls">
										<input type="text" name="sch_days" id="sch_days"
											class="form-control" placeholder="Schedule Days"
											data-rule-required="true" data-rule-number="true" />
									</div>
									<label class="col-sm-3 col-lg-1 control-label">Remark</label>
									<div class="col-sm-6 col-lg-2 controls">

										<input type="text" name="remark" id="remark"
											class="form-control" placeholder="Remark"
											data-rule-required="true" />
									</div>

								<!-- </div>

								<div class="box-content">
									<div class="col-md-12" style="text-align: center"> -->

										<input type="button" onclick="insertIndentDetail()"
											class="btn btn-info" value="Submit">
										




										<!-- 										<input type="button" onclick="validateQty()" class="btn btn-info" value="Submit">
 -->
									<!-- </div> -->
								</div>
								<div class="col-md-9"></div>
								<label for="search" class="col-md-3" id="search"> <i
									class="fa fa-search" style="font-size: 20px"></i> <input
									type="text" id="myInput" onkeyup="myFunction()"
									placeholder="Search.." title="Type in a name">
								</label>
								<div class="clearfix"></div>
								<div id="table-scroll" class="table-scroll">

									<!-- <div id="faux-table" class="faux-table" aria="hidden">
										<table id="table2" class="main-table">
											<thead>
												<tr class="bgpink">
													<th width="140" style="width: 30px" align="left">Sr No</th>
													<th width="138" align="left">Code</th>
													<th width="120" align="left">Name</th>
													<th width="100" align="left">Qty</th>
													<th width="60" align="left">UOM</th>
													<th width="100" align="left">Days</th>
													<th width="120" align="left">Date</th>
													<th width="80" align="left">Current Stock</th>
												</tr>
											</thead>
										</table>

									</div> -->
									<div class="table-wrap">

										<table id="table1" class="table table-advance">
											<thead>
												<tr class="bgpink">
													<th class="col-md-1" style="text-align: center;">Sr No</th>
													<th class="col-md-1" style="text-align: center;">Item
														Code</th>
													<th class="col-md-3" style="text-align: center;">Item
														Name</th>
													<th class="col-md-1" style="text-align: center;">UOM</th>
													<th class="col-md-2" style="text-align: center;">Current
														Stock</th>

													<th class="col-md-1" style="text-align: center;">Requested
														Qty</th>
													<th class="col-md-1" style="text-align: center;">ScheduleDays</th>
													<th class="col-md-1" style="text-align: center;">Schedule
														Date</th>
														<th class="col-md-1" style="text-align: center;">Action
														</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12" style="text-align: center">

								<input type="button"
									
									onclick="insertIndent()" class="btn btn-info" value="Submit">
									</div>
									</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END Main Content -->
		<footer>
			<p>2018© Trimbak Rubber</p>
		</footer>

		<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
			class="fa fa-chevron-up"></i></a>
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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/common.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
	<script type="text/javascript">
function insertIndent(){
	//alert("inside Indent Insetr");
 var form = document.getElementById("validation-form");
 var indentDate=$('#indent_date').val();
 var indNo	=$('#indent_no').val();

 //alert(indentDate);
 if(indentDate=="" || indentDate==null){
	 	alert("Please Select Valid Indent Date");
 }else  if(indNo=="" || indNo==null){
	 	alert("Please provide Indent No");
 }
 else{
	  	form.action ="${pageContext.request.contextPath}/saveIndent";
	    form.submit();
 }
}
</script>
	<script type="text/javascript">
	function showDept() {
		var mac_spec = document.getElementById("machine_specific").value;
//alert("Machine Specific "+mac_spec);
if(mac_spec==1){
document.getElementById('deptDiv').style.display = "block";
} 
if(mac_spec==0){
	document.getElementById('deptDiv').style.display = "none";
}
	}
	</script>
	<script type="text/javascript">
$(document).ready(function() {
	
    $('#dept').change(
            function() {
            	
                $.getJSON('${getSubDeptListByDeptId}', {
                    deptId : $(this).val(),
                    ajax : 'true'
                }, function(data) {
                
                    var len = data.length;

					$('#sub_dept')
				    .find('option')
				    .remove()
				    .end()
				// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
                    for ( var i = 0; i < len; i++) {
                            
                                
                        $("#sub_dept").append(
                                $("<option></option>").attr(
                                    "value", data[i].subDeptId).text(data[i].deptDesc)
                            );
                    }

                    $("#sub_dept").trigger("chosen:updated");
                });
            });
});
</script>

	<script type="text/javascript">
$(document).ready(function() {
	
    $('#group').change(
            function() {
            	
                $.getJSON('${itemListByGroupId}', {
                    grpId : $(this).val(),
                    ajax : 'true'
                }, function(data) {
                
                    var len = data.length;

					$('#item_name')
				    .find('option')
				    .remove()
				    .end()
				// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
                    for ( var i = 0; i < len; i++) {
                            
                                
                        $("#item_name").append(
                                $("<option></option>").attr(
                                    "value", data[i].itemId).text(data[i].itemDesc)
                            );
                    }

                    $("#item_name").trigger("chosen:updated");
                });
            });
});
</script>



	<script type="text/javascript">
$(document).ready(function() {
	
    $('#ind_cat').change(
            function() {
            	
                $.getJSON('${getgroupListByCatId}', {
                    catId : $(this).val(),
                    ajax : 'true'
                }, function(data) {
                
                    var len = data.length;

					$('#group')
				    .find('option')
				    .remove()
				    .end()
				// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
                    for ( var i = 0; i < len; i++) {
                            
                                
                        $("#group").append(
                                $("<option></option>").attr(
                                    "value", data[i].grpId).text(data[i].grpDesc)
                            );
                    }

                    $("#group").trigger("chosen:updated");
                });
            });
});
</script>

	<script type="text/javascript">
	function insertIndentDetail() {
		
		 var itemId=$('#item_name').val();
		 var qty=$('#quantity').val();
		 var remark=$('#remark').val();
		 var schDay=$('#sch_days').val();
		 var itemName=$("#item_name option:selected").html();
		 
		 var indentDate=$('#indent_date').val();
		 
		
		$.getJSON('${getIndentDetail}', {
			itemId : itemId,
			qty : qty,
			remark : remark,
			itemName : itemName,
			schDay : schDay,
			indentDate : indentDate,
			key : -1,
			ajax : 'true',

		}, function(data) {
			//alert(data);
			var len = data.length;
			$('#table1 td').remove();
			$.each(data,function(key, trans) {
			var tr = $('<tr></tr>');
			tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(key+1));
		  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.itemCode));
		  	tr.append($('<td class="col-md-3" style="text-align: center;"></td>').html(trans.itemName));
		  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.uom));
		  	tr.append($('<td class="col-md-2" style="text-align: center;"></td>').html(trans.curStock));

		  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.qty));
		  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.schDays));
		  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.date));
		  	
		  	/* tr
			.append($(
					'<td class="col-md-1" style="text-align: center;"></td>')
					.html(
							"<input type=button style='text-align:center; width:40px' class=form-control name=delete_indent_item"
									+ trans.itemId+ "id=delete_indent_item"
									+ trans.itemId
									+ " onclick='deleteIndentItem("+trans.itemId+","+key+")'  />"));
 */
		  	
		  	tr
			.append($(
					'<td class="col-md-1" style="text-align: center;"></td>')
					.html(
							"<a href='#' class='action_btn'onclick=deleteIndentItem("+trans.itemId+","+key+")><abbr title='Delete'><i class='fa fa-trash-o  fa-lg'></i></abbr></a>"));
		  	
			$('#table1 tbody').append(tr);
			})
			});
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
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}
</script>

<script type="text/javascript">
function deleteIndentItem(itemId,key){
	
	// var itemId=$('#item_name').val();
	 var qty=$('#quantity').val();
	 var remark=$('#remark').val();
	 var schDay=$('#sch_days').val();
	 var itemName=$("#item_name option:selected").html();
	 
	 var indentDate=$('#indent_date').val();
	
	$.getJSON('${getIndentDetail}', {
		itemId : itemId,
		qty : qty,
		remark : remark,
		itemName : itemName,
		schDay : schDay,
		indentDate : indentDate,
		key : key,
		ajax : 'true',

	}, function(data) {
		//alert(data);
		var len = data.length;
		$('#table1 td').remove();
		$.each(data,function(key, trans) {
		var tr = $('<tr></tr>');
		tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(key+1));
	  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.itemCode));
	  	tr.append($('<td class="col-md-3" style="text-align: center;"></td>').html(trans.itemName));
	  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.uom));
	  	tr.append($('<td class="col-md-2" style="text-align: center;"></td>').html(trans.curStock));

	  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.qty));
	  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.schDays));
	  	tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(trans.date));
	  	
	  /* 	tr
		.append($(
				'<td class="col-md-1" style="text-align: center;"></td>')
				.html(
						"<input type=button style='text-align:center;' class=form-control name=delete_indent_item"
								+ trans.itemId+ "id=delete_indent_item"
								+ trans.itemId
								+ " onclick='deleteIndentItem("+trans.itemId+","+key+")'  />")); */
								
								tr
								.append($(
										'<td class="col-md-1" style="text-align: center;"></td>')
										.html(
												"<a href='#' class='action_btn'onclick=deleteIndentItem("+trans.itemId+","+key+")><abbr title='Delete'><i class='fa fa-trash-o  fa-lg'></i></abbr></a>"));
		$('#table1 tbody').append(tr);
		})
		});
	
	
	
	
}
</script>
<script type="text/javascript">

function getInvoiceNo() {
	
	var date = $("#indent_date").val(); 
	var catId = $("#ind_cat").val(); 

	$.getJSON('${getInvoiceNo}', {

		catId:catId,
		docId:1,
		date : date,
		ajax : 'true',

	}, function(data) { 
		
	document.getElementById("indent_no").value=data.code;  
	
	});

}

</script>
</body>
</html>

