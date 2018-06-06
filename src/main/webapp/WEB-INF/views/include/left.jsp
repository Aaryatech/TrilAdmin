 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.time.LocalTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<style type="text/css">
.topSlide2 {
   
    padding: 20px 60px 20px 20px;
}
</style>
 	<div class="sidebarhome">
	<div class="content mCustomScrollbar">
		<ul>
		 
		 
					<%-- <c:if test="${info.accessRight==1}"> --%>
			
					<li><a href="#" onClick="openNav4()"><div class="img">
						<i class="fa fa-file-o icon"></i>
					</div>
					<div class="title">
						<span>Masters</span>
					</div></a></li>
					<%-- </c:if> --%>
			  
					<%-- <li><a href="${pageContext.request.contextPath}/purchaseBill"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						 Purchase  <span></span>
					</div></a></li> --%>
					
				 
					<%-- <li><a href="${pageContext.request.contextPath}/showAllTempAndSettleBill"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						  Bills  <span></span>
					</div></a></li> --%>
					
					  
						<%-- <li><a href="${pageContext.request.contextPath}/returnManf"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						Return Manufacture<span></span>
					</div></a></li> --%>
					
					<%-- <li><a href="${pageContext.request.contextPath}/getCurrentStock"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						Get Current Stock<span></span>
					</div></a></li> --%>
					
					<%-- <li><a href="${pageContext.request.contextPath}/showItemStock"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						Insert Or Edit Stock<span></span>
					</div></a></li> --%>
					
					<%-- <li><a href="${pageContext.request.contextPath}/creditNoteLeakageQtyReport"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						CRN Item Leakage Qty Report<span></span>
					</div></a></li> --%>
					 
		<!-- 			
			<li><a href="#" onClick="openNav3()"><div class="img">
						<i class="fa fa-file-o icon"></i>
					</div>
					<div class="title">
						<span>Report</span>
					</div></a></li>
					 -->
					<%-- <li><a href="${pageContext.request.contextPath}/logout"><div
						class="img">
						<i class="fa fa-file-o icon"></i>
					</div>
					<div class="title">
						Logout<span></span>
					</div></a></li> --%>
					
					<%-- <li><a href="${pageContext.request.contextPath}/creditNote"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						Credit Note <span></span>
					</div></a></li>
					
						<li><a href="${pageContext.request.contextPath}/vehInfo"><div
						class="img">
						<i class="fa fa-file-pdf-o icon"></i>
					</div>
					<div class="title">
						Vehicle Information <span></span>
					</div></a></li> --%>
					
					
					
					
			<c:if test="${info.accessRight==1}">	
					
			<li><a href="${pageContext.request.contextPath}/showeditprofile" ><div class="img">
						
						<img
							src="${pageContext.request.contextPath}/resources/images/nav-editprofile.png"
							alt="img">
					</div>
					<div class="title">
						Edit <span>Profile</span>
					</div></a>
		   </li>
		   </c:if>
		</ul>
		</div>
	</div>
	<!--leftNav-->

	<!--navInner-->

	<!--1nav-->
	<div id="mySidenav" class="sidenav">
		<div class="topSlide">
			<div class="topSlide2">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav()"><img
					src="${pageContext.request.contextPath}/resources/images/closebtn.png"
					alt="monginis"></a>
				<div class="listarea">
					<ul>

						<c:forEach var="menu" items="${menuList}" varStatus="loop">
							<c:set var="menuToTime" value="${menu.toTime}" />
							<c:set var="menuFromTime" value="${menu.fromTime}" />
							<c:set var="menuDesc" value="${menu.menuDesc}" />

							<c:set var="frId" value="${menu.frId}" />
							<c:set var="isSameDayApplicable"
								value="${menu.isSameDayApplicable}" />
							<c:set var="settingType" value="${menu.settingType}" />

							<c:set var="catId" value="${menu.catId}" />

							<%
								ZoneId z = ZoneId.of("Asia/Calcutta");
									LocalTime now = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

									String menuToTiming = (String) pageContext.getAttribute("menuToTime");
									String menuFromTiming = (String) pageContext.getAttribute("menuFromTime");

									String menuDesc = (String) pageContext.getAttribute("menuDesc");

									
									SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
									SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
									Date fromTime12Hrs = _24HourSDF.parse(menuFromTiming);
									Date toTime12Hrs = _24HourSDF.parse(menuToTiming);

									// System.out.println(_24HourDt);
									//  System.out.println(_12HourSDF.format(_24HourDt));

									pageContext.setAttribute("fromTime", _12HourSDF.format(fromTime12Hrs));
									pageContext.setAttribute("toTime", _12HourSDF.format(toTime12Hrs));

									int isSameDayApplicable = (int) pageContext.getAttribute("isSameDayApplicable");
									int catId = (int) pageContext.getAttribute("catId");
									int settingType = (int) pageContext.getAttribute("settingType");

									System.out.println("\n\n\nMenu To Timing" + menuToTiming);
									System.out.println("Menu From Timing" + menuFromTiming);

									LocalTime toTime = LocalTime.parse(menuToTiming);
									LocalTime fromTime = LocalTime.parse(menuFromTiming);

									Boolean isLate = now.isAfter(toTime);
									Boolean isEarly = now.isBefore(fromTime);

									System.out.println("\nLocal time" + now + "Is Early :" + isLate);
									System.out.println("Local time" + now + "Is Late :" + isLate);

									/* 	try {
										    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										    final Date dateObj = sdf.parse(time);
										    System.out.println(dateObj);
										    System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
										} catch (final ParseException e) {
										    e.printStackTrace();
										} */
									Boolean isSameDay = fromTime.isBefore(toTime);
									Boolean isValid = false;

									if (isSameDay) {
										System.out.println("in isSameDay if loop");

										if (!isLate && !isEarly) {

											isValid = true;
										}
									} else {										
										System.out.println("in isSameDay else loop");

									
										if (now.isAfter(fromTime)) {
											isValid = true;
										}else if( toTime.isAfter(now)){
											isValid = true;
										}
									}

									if (isValid) {
							%>

							<li>
								<%
									if (catId != 5) {

												if (isSameDayApplicable == 3) {
													// if
													if (settingType != 1) {
								%>
							<abbr title='${menu.menuDesc}'>			<div class="listareaBoxBlue"></abbr>
									<a class="listareaBoximg"
										href="${pageContext.request.contextPath}/showRegularSpCakeOrder/${loop.index}">



										<%
											//else
															} else {
										%>
									<abbr title='${menu.menuDesc}'><a class="listareaBox"
												href="${pageContext.request.contextPath}/showRegularSpCakeOrder/${loop.index}"><div class="listareaBoximg"></abbr>
											

												<%
													}

																} else {
																	if (settingType != 1)

																	{

																		//if
												%>
											<abbr title='${menu.menuDesc}'><a class="listareaBoxBlue"
														href="${pageContext.request.contextPath}/showSavouries/${loop.index}">	<div class="listareaBoximg"></abbr>
													
														<%
															} else {
														%>
													<abbr title='${menu.menuDesc}'>	<a class="listareaBox"
																href="${pageContext.request.contextPath}/showSavouries/${loop.index}">	<div class="listareaBoximg"></abbr>
														
																<%
																	}

																				}
																			} else if (catId == 5) {

																				// if
																				if (settingType != 1)
																				{
																				
																%> 
															<abbr title='${menu.menuDesc}'><a class="listareaBoxBlue"
																href="${pageContext.request.contextPath}/showSpCakeOrder/${loop.index}">	<div class="listareaBoximg"></abbr>
																
																
																	<%
																	//else
																	}else{
																		%> 
																	<abbr title='${menu.menuDesc}'><a class="listareaBox"
																		href="${pageContext.request.contextPath}/showSpCakeOrder/${loop.index}">	<div class="listareaBoximg"></abbr>
																		
																		
																			<%
																		
																	}
												
																		

																				}
																	%> <img
																	src="${pageContext.request.contextPath}/resources/images/${menu.menuImage}"
																	alt="monginis"> <img
																	src="${pageContext.request.contextPath}/resources/images/${menu.selectedMenuImage}"
																	alt="monginis">
															</div>
																<h2>
																	<c:out value='${menu.menuTitle}' />
																</h2>
																<h3>
																	Booking -
																	<c:out value='${fromTime}' />
																	To
																	<c:out value='${toTime}' />
																</h3>
														</a>
							</li>


							<%
								} else {
							%>


							<li>

								<div class="listareaBox">

									<a class="listareaBoximg"> <%-- <img
										src="${pageContext.request.contextPath}/resources/images/${menu.menuImage}"
										alt="monginis"> <img
										src="${pageContext.request.contextPath}/resources/images/${menu.selectedMenuImage}"
										alt="monginis"> --%></a>

									<%--
									<h2>
										<c:out value='${menu.menuTitle}' />
									</h2>
								 	<h3>
										Booking - <c:out value='${fromTime}' /> To 
											<c:out value='${toTime}' />
									</h3> --%>
									</a>
								</div>

							</li>
							<%
								}
							%>
						</c:forEach>




					</ul>
				</div>

			</div>
		</div>
	</div>
	<!--1nav-->



	<!--2nav-->
	<div id="mySidenav1" class="sidenav">
		<div class="topSlide">
			<div class="topSlide2">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav1()"><img
					src="${pageContext.request.contextPath}/resources//images/closebtn.png"
					alt="monginis"></a>
				<div class="listarea">
				
				
					<ul>
						<li class="small-box">
						
						<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">GRN</div>
						</div>
						
						
						<a href="${pageContext.request.contextPath}/showGrn"><i class="fa fa-plus-circle icon"></i> Request GRN </a>
						<div class="clearfix"></div>
						<a href="${pageContext.request.contextPath}/displayGrn"><i class="fa fa-search-plus icon"></i> View GRN </a>
												<div class="clearfix"></div>
						
												<a href="${pageContext.request.contextPath}/showManGrn"><i class="fa fa-search-plus icon"></i> Manual GRN </a>
								
						</div>
						</li>
	 						
	 						<li class="small-box">
	 						
	 							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">GVN</div>
						</div>
						
						
						<a href="${pageContext.request.contextPath}/showGvn"><i	class="fa fa-plus-circle icon"></i> Request GVN 
									</a>
									<a href="${pageContext.request.contextPath}/displayGvn"><i class="fa fa-search-plus icon"></i> View GVN </a>
								
						</div>
						
						</li>

						<%-- <li><div class="listareaBox">
								<a class="listareaBoximg"> <img
									src="${pageContext.request.contextPath}/resources/images/icon8.png"
									alt="monginis"> <img
									src="${pageContext.request.contextPath}/resources/images/icon8-h.png"
									alt="monginis"></a>
								<h2>GRN2</h2>
								<h3>Booking Up to 12.30 PM</h3>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Add <i class="fa fa-pencil"></i></a>
								</div>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Upload <i
										class="fa fa-cloud-upload"></i></a>
								</div>
							</div></li>

						<li><div class="listareaBox">
								<a class="listareaBoximg"> <img
									src="${pageContext.request.contextPath}/resources/images/icon9.png"
									alt="monginis"> <img
									src="${pageContext.request.contextPath}/resources/images/icon9-h.png"
									alt="monginis"></a>
								<h2>GVN</h2>
								<h3>At Any Time Till Shop Closing</h3>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Add <i class="fa fa-pencil"></i></a>
								</div>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Upload <i
										class="fa fa-cloud-upload"></i></a>
								</div>
							</div></li> --%>

					</ul>
				</div>

			</div>
		</div>
	</div>
	<!--2nav-->

	<!--4nav-->
 	<div id="mySidenav4" class="sidenav">
		<div class="topSlide">
			<div class="topSlide2">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav4()"><img
					src="${pageContext.request.contextPath}/resources//images/closebtn.png"
					alt="monginis"></a>
				<div class="listarea">
					<ul>
						<li class="small-box">
						
							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">Masters</div>
						</div>
						<a href="${pageContext.request.contextPath}/addCategory"><i class="fa fa-files-o icon"></i>Add Category</a>
						<a href="${pageContext.request.contextPath}/addDispachMode"><i class="fa fa-files-o icon"></i>Add Dispach Mode</a>
						<a href="${pageContext.request.contextPath}/addDepartment"><i class="fa fa-files-o icon"></i>Add Department</a>
						<a href="${pageContext.request.contextPath}/addSubDepartment"><i class="fa fa-files-o icon"></i>Add Sub Department</a>
						<a href="${pageContext.request.contextPath}/addDeliveryTerm"><i class="fa fa-files-o icon"></i>Add Delivery Term</a>
						<a href="${pageContext.request.contextPath}/addItemGroup"><i class="fa fa-files-o icon"></i>Add Item Group</a>
						<a href="${pageContext.request.contextPath}/addItemSubGroup"><i class="fa fa-files-o icon"></i>Add Item Sub Group</a> 
						<a href="${pageContext.request.contextPath}/addPaymentTerm"><i class="fa fa-files-o icon"></i>Add Payment Term</a>
						<a href="${pageContext.request.contextPath}/addTaxForm"><i class="fa fa-files-o icon"></i>Add Tax Form</a>
								
						</div>
						
					</li>
							
	 						 
						 
							
							 
						
					 

						

					</ul>
				</div>

			</div>
		</div>
	</div> 
	<!--4nav-->
<%-- 	<div id="mySidenav3" class="sidenav">
		<div class="topSlide">
			<div class="topSlide2">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav3()"><img
					src="${pageContext.request.contextPath}/resources//images/closebtn.png"
					alt="monginis"></a>
				<div class="listarea">
					<ul>
						<li class="small-box">
						
							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">Purchase Reports</div>
						</div>
						<a href="${pageContext.request.contextPath}/viewBillWisePurchaseReport"><i class="fa fa-files-o icon"></i> Bill Wise Purchase Report</a>
						 <a href="${pageContext.request.contextPath}/viewDateWisePurchaseReport"><i class="fa fa-files-o icon"></i> Date Wise Purchase Report</a>
						 <a href="${pageContext.request.contextPath}/viewItemWisePurchaseReport"><i class="fa fa-files-o icon"></i>Item Wise Purchase Report</a>
								
						</div>
						
					</li>
							<li class="small-box">
						
							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">Bill Reports</div>
						</div>
						<a href="${pageContext.request.contextPath}/viewBillWiseConsumptionReport"><i class="fa fa-files-o icon"></i> Bill Wise Consumption</a>
					<a href="${pageContext.request.contextPath}/viewDatewiseConsumptionReport"><i class="fa fa-files-o icon"></i> Date Wise Consumption Report</a>
					 <a href="${pageContext.request.contextPath}/viewCustomerWiseConsumptionReport"><i class="fa fa-files-o icon"></i> Customer Wise Consumption</a>
						 <a href="${pageContext.request.contextPath}/viewCategoryWiseReport"><i class="fa fa-files-o icon"></i> Category Wise Consumption</a>
							 <a href="${pageContext.request.contextPath}/viewCategoryWiseConsumptionReport"><i class="fa fa-files-o icon"></i> Category Wise Item Consumption</a>
						  <a href="${pageContext.request.contextPath}/viewCustomerWiseConsumptionReport"><i class="fa fa-files-o icon"></i> Customer Wise Consumption Report</a>
								
						</div>
						
					</li>
							<li class="big-box">
						
							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">CRN Note And Vehicle Reports</div>
						</div>
					<a href="${pageContext.request.contextPath}/viewCreditNoteReport"><i class="fa fa-files-o icon"></i> Credit Note Report</a>
						  <a href="${pageContext.request.contextPath}/viewVehicleWiseReport"><i class="fa fa-files-o icon"></i> Vehicle Wise Report</a>
								  <a href="${pageContext.request.contextPath}/creditNoteLeakageQtyReport"><i class="fa fa-files-o icon"></i> CRN Leakage Qty  Report</a>
						</div>
						
					</li>

					</ul>
				</div>

			</div>
		</div>
	</div>  --%>
	<!--4nav-->

	<!--3nav-->
	<%-- <div id="mySidenav3" class="sidenav">

		<div class="topSlide">
			<div class="topSlide2 textcen">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav3()"><img
					src="${pageContext.request.contextPath}/resources/images/closebtn.png"
					alt="monginis"></a>
				<div class="profileinsite">

					<div class="profileinsiteLeft">

						<div class="profile">
							<div class="profilefildset">Franchisee Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="franchisee Name"
									name="fr_name" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Franchisee Profile Pic</div>
							<div class="profileinput">
								<div class="editimg">
									<div class="editpics">
										<div class="fileUpload">
											<span><i class="fa fa-pencil"></i></span> <input type="file"
												class="upload" />
										</div>
									</div>
									<img
										src="${pageContext.request.contextPath}/resources/images/editimg.jpg"
										alt="img">
								</div>
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">City</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="City Name"
									name="fr_city" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Email id</div>
							<div class="profileinput">
								<input class="texboxitemcode" name="fr_email"
									placeholder="example@gmail.com" type="text">
							</div>
						</div>



					</div>

					<div class="profileinsiteRight">
						<div class="profile">
							<div class="profilefildset">Mobile No.</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="9876543201"
									name="fr_mobile" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Owner Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Owner Name"
									name="fr_owner" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Shop Opening Date</div>
							<div class="profileinput mardis">26 Feb. 2017</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Edit Password</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Edit Password"
									name="fr_password" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Confirm Password</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Confirm Password"
									name="fr_password" type="text" data-rule-equalTo="#fr_password">
							</div>
						</div>


						<div class="profile">
							<div class="profileinput">
								<input name="" class="buttonsaveorder" value="SUBMIT"
									type="button">
							</div>
						</div>

					</div>

					<div class="messages messagesErr">err message</div>
					<div class="messages messagesInfo">info message</div>
					<div class="messages messagesSuccess">success message</div>


				</div>
			</div>
		</div>
	</div> --%>
	<!--3nav-->

	<!--navInner-->
	<script>
		function openNav() {
			document.getElementById("mySidenav").style.width = "100%";
		}

		function closeNav() {
			document.getElementById("mySidenav").style.width = "0";
		}
		function openNav1() {
			document.getElementById("mySidenav1").style.width = "100%";
		}

		function closeNav1() {
			document.getElementById("mySidenav1").style.width = "0";
		}
		function openNav3() {
			document.getElementById("mySidenav3").style.width = "100%";
		}

		function closeNav3() {
			document.getElementById("mySidenav3").style.width = "0";
		}
		function openNav4() {
			document.getElementById("mySidenav4").style.width = "100%";
		}

		function closeNav4() {
			document.getElementById("mySidenav4").style.width = "0";
		}
	</script>

	<script
		src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.imgpath').click(function() {
				$(this).hide();
				$('.videoWrapper').show();
				$(".sproutvideo-player")[0].src += "?autoplay=1";
			});
			var owl = $("#owl-example");
			owl.owlCarousel({
				items : 2,
				loop : false,
				autoplay : false,
			});

		});
	</script>

	<script type="text/javascript">
		function showWindow(fromTime, toTime) {
			confirm("Timeout:\n You can place order from " + fromTime + " To "
					+ toTime);
		}
	</script>


	<!-- custom scrollbar plugin added by kalpesh -->
		<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.mCustomScrollbar.concat.min.js"></script>