package com.ats.tril.common;

public class Constants {

	
	
	public static final String url="http://localhost:8093/";
	//public static final String url="http://tomcat.aaryatechindia.in:2908/webapi/"; 
	//public static final String url="http://132.148.143.124:8080/TrilWebApi/"; 
	//public static final String ReportURL = "http://tomcat.aaryatechindia.in:2908/admin";
	//public static final String ReportURL = "http://localhost:8080/tril";
	public static final String ReportURL = "http://132.148.143.124:8080/triladmin";
	public static final int ItemImage = 1;  
	//public static final String Item_Image_URL ="C:/pdf/";
	//public static final String Item_Image_URL ="http://tomcat.aaryatechindia.in:2908/uploads/Item/";
	public static final String Item_Image_URL = "http://132.148.143.124:8080/triluploads/";
	//public static String REPORT_SAVE = "/home/aaryate1/tomcat.aaryatechindia.in/tomcat-8.0.18/webapps/admin/report.pdf";
	public static String REPORT_SAVE = "/opt/apache-tomcat-8.5.6/webapps/triladmin/report.pdf";
	//public static String REPORT_SAVE = "/home/lenovo/Documents/pdf/Report.pdf";
	public static int delStatus = 1;
	public static int subAct=0;
	public static int mainAct=0;
	
	
	/*select id.ind_d_id,id.item_id from indtrans id,indent ih where id.del_status=1 and id.ind_d_status in (9,7) and id.ind_m_id=ih.ind_m_id 
			and ih.ind_m_status in (9,7) and ih.del_status=1 and ih.ind_m_type=1 and ih.cat_id=2*/
	
	

}
