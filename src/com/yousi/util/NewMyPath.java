package com.yousi.util;

public class NewMyPath {
private static String url = "http://172.16.3.141:8804/TeacherCenterInterface/";

/*name, pwd*/
public static String login_path = url + "login";

/*[before, after, grade, subject, order]*/
public static String demand_path = url + "demand";

/*rid*/
public static String orderShow_path = url + "orderShow";

/*rid*/
public static String getOrder_path = url + "getOrder";

/*type*/
public static String order_path = url + "order";

/*rid*/
public static String removeOrder_path = url + "removeOrder";

/*rid*/
public static String refuseOrder_path = url + "refuseOrder";

/*rid, listentime*/
public static String confirmTeach_path = url + "confirmTeach";

/*rid, hour*/
public static String beginToTeach_path = url + "beginToTeach";

/*rid*/
public static String giveupOrder_path = url + "giveupOrder";

/*type*/
public static String course_path = url + "course";
}
