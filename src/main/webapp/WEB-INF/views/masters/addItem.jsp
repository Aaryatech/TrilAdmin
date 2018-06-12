

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

 

</head>
<body>
 --%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<c:url var="editFrSupplier" value="/editFrSupplier"></c:url>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.js"></script>

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
				 
				<form name="frm_search" id="frm_search" method="post" action="${pageContext.request.contextPath}/insertItem"
				 > 
					 
						<div class="col-md -3">
							
								<div class="col1title" align="left"><h3>Add Item</h3></div>
								 
						</div>
						
					<div class="colOuter">
						<div class="col-md-2">
							<div class="col1title" align="left">Item Code*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemCode" class="form-control"
								placeholder="Item Code" value="${editItem.itemCode}" style="text-align: left;" name="itemCode" type="text" required>
								<input id="itemId" class="form-control"
								  name="itemId" value="${editItem.itemId}" type="hidden" >

						</div>
						<div class="col-md-1">
							 
						</div>

						<div class="col-md-2">
							<div class="col1title" align="left">Item Description*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemDesc" class="form-control"
								placeholder="Item Code" value="${editItem.itemDesc}" style="text-align: left;" name="itemDesc" type="text" required>
						</div>
					 
					</div>
					
					<div class="colOuter">
						 
						
						<div class="col-md-2">
							<div class="col1title" align="left">UOM*: </div>
						</div>
						<div class="col-md-3">
							<input id="uom" class="form-control"
								placeholder="UOM" value="${editItem.itemUom}" style="text-align: left;" name="uom" type="text" required>
						
						</div>
						<div class="col-md-1"> </div>
						<div class="col-md-2">
							<div class="col1title" align="left">Item Date*: </div>
						</div>
						 
						<div class="col-md-2">
							<input id="fromdatepicker" class="texboxitemcode texboxcal"
								 placeholder="Item Date" value="${editItem.itemDate}" name="itemDate" type="text" required>
								 
						</div>
						
						
						 
				 
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item OP Rate*: </div>
						</div>
						<div class="col-md-3">
							<input id="opRate" class="form-control"
								placeholder="Item OP Rate" name="opRate" value="${editItem.itemOpRate}" pattern="[+-]?([0-9]*[.])?[0-9]+" style="text-align: left;" title="Enter in Number Formate"  type="text" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item OP Qty*: </div>
						</div>
						<div class="col-md-3">
							<input id="opQty" class="form-control"
								placeholder="Item OP Qty" name="opQty" value="${editItem.itemOpQty}"  style="text-align: left;" title="Enter in Number Formate"  type="number" required>

						</div>
						 
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item CL Rate*: </div>
						</div>
						<div class="col-md-3">
							<input id="clRate" class="form-control"
								placeholder="Item CL Rate" name="clRate" value="${editItem.itemClRate}" pattern="[+-]?([0-9]*[.])?[0-9]+" style="text-align: left;" title="Enter in Number Formate"  type="text" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item CL Qty*: </div>
						</div>
						<div class="col-md-3">
							<input id="clQty" class="form-control"
								placeholder="Item OP Qty" name="clQty" value="${editItem.itemClQty}" style="text-align: left;"  title="Enter in Number Formate" type="number" required>

						</div>
						 
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Min Level*: </div>
						</div>
						<div class="col-md-3">
							<input id="minLevel" class="form-control"
								placeholder="Item Min Level" name="minLevel" value="${editItem.itemMinLevel}"  style="text-align: left;" title="Enter in Number Formate"  type="number" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item Max Level*: </div>
						</div>
						<div class="col-md-3">
							<input id="maxLevel" class="form-control"
								placeholder="Item Max Level" name="maxLevel" value="${editItem.itemMaxLevel}" style="text-align: left;" title="Enter in Number Formate"  type="number" required>

						</div>
						 
					</div>
					 
					 <div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Rod Level*: </div>
						</div>
						<div class="col-md-3">
							<input id="rodLevel" class="form-control"
								placeholder="Item Rod Level" name="rodLevel" value="${editItem.itemRodLevel}" style="text-align: left;" title="Enter in Number Formate"  type="number" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item Weight*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemWeight" class="form-control"
								placeholder="Item Weight" name="itemWeight" value="${editItem.itemWt}" pattern="[+-]?([0-9]*[.])?[0-9]+" style="text-align: left;" title="Enter in Number Formate"  type="text" required>

						</div>
						 
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Location*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemLocation" class="form-control"
								placeholder="Item Rod Level" name="itemLocation" value="${editItem.itemLocation}"  style="text-align: left;"    type="text" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item Abc*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemAbc" class="form-control"
								placeholder="Item Abc" name="itemAbc"   style="text-align: left;" value="${editItem.itemAbc}"   type="text" required>

						</div>
						 
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Life*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemLife" class="form-control"
								placeholder="Item Life" name="itemLife"   style="text-align: left;"   value="${editItem.itemLife}" type="text" required>

						</div>
						 
						 <div class="col-md-1"> </div>
						 
						<div class="col-md-2">
							<div class="col1title" align="left">Item Schd*: </div>
						</div>
						<div class="col-md-3">
							<input id="itemSchd" class="form-control"
								placeholder="Item Schd" name="itemSchd"   style="text-align: left;"   value="${editItem.itemSchd}" type="text" required>

						</div>
						 
					</div>
					
					<!-- data-live-search="true" -->
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Is Critical*: </div>
						</div>
						  
						<div class="col-md-2">
						<select class="selectpicker"  title="Please Select" 
															name="isCritical" id="isCritical"   required> 
														 <c:choose>
														 	<c:when test="${editItem.itemIsCritical==0}">
														 			<option value="0" selected>NO</option>
																	<option value="1">YES</option>
														 	</c:when>
														 	<c:when test="${editItem.itemIsCritical==1}">
														 			<option value="0" >NO</option>
																	<option value="1" selected>YES</option>
														 	</c:when>
														 	<c:otherwise>
														 		<option value="0" >NO</option>
																	<option value="1">YES</option>
														 	</c:otherwise>
														 
														 </c:choose>
																  
															 </select>
															 </div>
						 
						 <div class="col-md-2"> </div>
						 
						 <div class="col-md-2">
							<div class="col1title" align="left">Item Is Capital*: </div>
						</div>
						 
						<div class="col-md-2">
						<select class="selectpicker"   title="Please Select" 
															name="isCapital" id="isCapital"  required> 
														  <c:choose>
														 	<c:when test="${editItem.itemIsCapital==0}">
														 			<option value="0" selected>NO</option>
																	<option value="1">YES</option>
														 	</c:when>
														 	<c:when test="${editItem.itemIsCapital==1}">
														 			<option value="0" >NO</option>
																	<option value="1" selected>YES</option>
														 	</c:when>
														 	<c:otherwise>
														 		<option value="0" >NO</option>
																	<option value="1">YES</option>
														 	</c:otherwise>
														 
														 </c:choose>
																  
															 </select>
															 </div>
						  
					</div>
					
					<div class="colOuter">
					
					<div class="col-md-2">
							<div class="col1title" align="left">Item Con*: </div>
						</div>
						  
						<div class="col-md-2">
						<select class="selectpicker"  title="Please Select" 
															name="itemCon" id="itemCon"  required> 
														 
																 
																 <c:choose>
														 	<c:when test="${editItem.itemIsCons==0}">
														 			<option value="0" selected>NO</option>
																	<option value="1">YES</option>
														 	</c:when>
														 	<c:when test="${editItem.itemIsCons==1}">
														 			<option value="0" >NO</option>
																	<option value="1" selected>YES</option>
														 	</c:when>
														 	<c:otherwise>
														 		<option value="0" >NO</option>
																	<option value="1">YES</option>
														 	</c:otherwise>
														 
														 </c:choose>
																 
															 </select>
															 </div>
						 
						 <div class="col-md-2"> </div>
						 
						  
					</div>
					 
					    
					<div class="colOuter">
						<div align="center">
							<input name="submit" class="buttonsaveorder" value="Submit"
								type="submit" align="center">
								<!-- <input type="button" class="buttonsaveorder" value="Cancel" id="cancel" onclick="cancel1()" disabled> -->
						</div>
				 
					</div>
					
					  

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
						
						document.getElementById("suppId").value=data.suppId;
						document.getElementById("suppName").value=data.suppName;  
						document.getElementById("suppAdd").value=data.suppAddr;
						document.getElementById("city").value=data.suppCity;
						document.getElementById("mob").value=data.mobileNo;
						document.getElementById("email").value=data.email;
						document.getElementById("gstnNo").value=data.gstnNo;
						document.getElementById("panNo").value=data.panNo;
						document.getElementById("liceNo").value=data.suppFdaLic;
						document.getElementById("creditDays").value=data.suppCreditDays;
						document.getElementById("isSameState").value=data.isSameState; 
						document.getElementById("cancel").disabled=false;
					});

 
	   

}

function cancel1() {

    //alert("cancel");
	document.getElementById("suppId").value="";
	document.getElementById("suppName").value="";  
	document.getElementById("suppAdd").value="";
	document.getElementById("city").value="";
	document.getElementById("mob").value="";
	document.getElementById("email").value="";
	document.getElementById("gstnNo").value="";
	document.getElementById("panNo").value="";
	document.getElementById("liceNo").value="";
	document.getElementById("creditDays").value="";
	document.getElementById("isSameState").value=""; 
	document.getElementById("cancel").disabled=false;

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

</body>
</html>
