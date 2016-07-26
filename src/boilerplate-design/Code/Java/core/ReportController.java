package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.commons.util.io.json.JsonObject;

public class ReportController implements Serializable {
private static final long serialVersionUID = 1L;
	
	public ArrayList<JsonJavaObject> dashboard1;
	public ArrayList<JsonJavaObject> clientDashboard;
	public ArrayList<JsonJavaObject> siteDashboard;
	public ArrayList<JsonJavaObject> operationsDashboard;
	public ArrayList<JsonJavaObject> financialDashboard;
	
	@SuppressWarnings("finally")
	public ArrayList<JsonJavaObject> RunReportDashboard1(String startDate, String endDate){
		ArrayList<JsonJavaObject> tempArray = null;
		
		try {
			tempArray = new ArrayList<JsonJavaObject>();
			
			tempArray.add(_GenerateReport1(startDate, endDate));
			tempArray.add(_GenerateReport2(startDate, endDate));
			tempArray.add(_GenerateReport3(startDate, endDate));
			tempArray.add(_GenerateReport4(startDate, endDate));
			
			System.out.println(tempArray.get(2).toString());
			dashboard1 = tempArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tempArray;
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<JsonJavaObject> RunReportsClientDashboard(String client, String region, String maintType, String startDate, String endDate){
		ArrayList<JsonJavaObject> tempArray = null;
		
		try {
			tempArray = new ArrayList<JsonJavaObject>();
			
			tempArray.add(_ClientReport1(client));
			tempArray.add(_ClientReport2(client, region, maintType, startDate, endDate));
			tempArray.add(_ClientReport3(client, region, maintType, startDate, endDate));
			tempArray.add(_ClientReport4(client, region, maintType, startDate, endDate));
			tempArray.add(_ClientReport5(client, region, maintType, startDate, endDate));
			
		//	System.out.println(tempArray.get(2).toString());
			clientDashboard = tempArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tempArray;
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<JsonJavaObject> RunReportsSiteDashboard(String client, String region, String county, String maintType, String startDate, String endDate){
		ArrayList<JsonJavaObject> tempArray = null;
		
		try {
			tempArray = new ArrayList<JsonJavaObject>();
			
			tempArray.add(_SiteReport1(client));
			tempArray.add(_SiteReport2(client, region, county, maintType, startDate, endDate));
			
		//	System.out.println(tempArray.get(2).toString());
			siteDashboard = tempArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tempArray;
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<JsonJavaObject> RunReportsOperationsDashboard(String startDate, String endDate){
		ArrayList<JsonJavaObject> tempArray = null;
		
		try {
			tempArray = new ArrayList<JsonJavaObject>();
			
			tempArray.add(_OperationsReport1(startDate, endDate));
			
		//	System.out.println(tempArray.get(2).toString());
			operationsDashboard = tempArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tempArray;
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<JsonJavaObject> RunReportsFinancialDashboard(String client){
		ArrayList<JsonJavaObject> tempArray = null;
		
		try {
			tempArray = new ArrayList<JsonJavaObject>();
			
			tempArray.add(_FinancialReport1(client));
			tempArray.add(_FinancialReport2(client));
			tempArray.add(_FinancialReport3(client));
			
		//	System.out.println(tempArray.get(2).toString());
			financialDashboard = tempArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tempArray;
		}
	}
	
	@SuppressWarnings("finally")
	public JsonJavaObject CreateReport(String reportType, String startDate, String endDate, String division,
			String business) {
//	public JsonJavaObject CreateReport(String reportType, JsonJavaObject reportCriteria) {

		JsonJavaObject reportData = null;
		
		try {
			switch (Integer.parseInt(reportType)) {
			case 1:// Report 1
			//	reportData = _GenerateReport1(reportCriteria);
				reportData = _GenerateReport1(startDate, endDate);
				break;

			case 2:// Report 2
				//reportData = _GenerateReport2(reportCriteria);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return reportData;
		}
	}

	@SuppressWarnings("finally")
	private JsonJavaObject _GenerateReport1(String startDate, String endDate) {
	//private JsonJavaObject _GenerateReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("UserName", "Suzy Smith");
			jsonData.put("Business", "Alpha");
			jsonData.put("Department", "Finance");
			jsonData.put("DateValue", "2016/01/01");
			jsonData.put("NumberValue", "6");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("UserName", "Johnny White");
			jsonData.put("Business", "Beta");
			jsonData.put("Department", "HR");
			jsonData.put("DateValue", "2015/12/01");
			jsonData.put("NumberValue", "10");
			dataArray.add(jsonData);

			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _GenerateReport2(String startDate, String endDate) {
	//private JsonJavaObject _GenerateReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("Month", "October");
			jsonData.put("LoggedInCount", "10");
			jsonData.put("CallsCount", "5");
			jsonData.put("ClosedCount", "2");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Month", "November");
			jsonData.put("LoggedInCount", "15");
			jsonData.put("CallsCount", "12");
			jsonData.put("ClosedCount", "8");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _GenerateReport3(String startDate, String endDate) {
	//private JsonJavaObject _GenerateReport3(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			//{"Division":"Corporate","RegisteredCount":"10","LoggedInCount":"8"}
			jsonData = new JsonJavaObject();
			jsonData.put("Division", "Corporate");
			jsonData.put("RegisteredCount", "10");
			jsonData.put("LoggedInCount", "8");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Division", "Operations");
			jsonData.put("RegisteredCount", "25");
			jsonData.put("LoggedInCount", "12");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _GenerateReport4(String startDate, String endDate) {
	//private JsonJavaObject _GenerateReport4(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			//{"source": "Hardware","percentage": 33},{"source": "Consultation","percentage": 17}
			jsonData = new JsonJavaObject();
			jsonData.put("source", "Development");
			jsonData.put("percentage", 42);
			jsonData.put("explode", true);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("source", "Software");
			jsonData.put("percentage", 8);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("source", "Hardware");
			jsonData.put("percentage", 33);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("source", "Consultation");
			jsonData.put("percentage", 17);
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _ClientReport1(String client) {
	//private JsonJavaObject _ClientReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("ClientName", client);
			jsonData.put("ClientCost", "500,000.00");
			jsonData.put("ClientRevenue", "1,000,000.00");
			jsonData.put("ClientSites", "12");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _ClientReport2(String client, String region, String maintType, String startDate, String endDate) {
	//private JsonJavaObject _ClientReport2(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "Gauteng");
			jsonData.put("CallsOpenCount", "10");
			jsonData.put("CallsPendingCount", "5");
			jsonData.put("CallsClosedCount", "3");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "North West");
			jsonData.put("CallsOpenCount", "20");
			jsonData.put("CallsPendingCount", "10");
			jsonData.put("CallsClosedCount", "6");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _ClientReport3(String client, String region, String maintType, String startDate, String endDate) {
	//private JsonJavaObject _ClientReport3(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("Technician", "Techie 1");
			jsonData.put("CallsOpenCount", "7");
			jsonData.put("CallsPendingCount", "15");
			jsonData.put("CallsClosedCount", "11");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Technician", "Techie 2");
			jsonData.put("CallsOpenCount", "15");
			jsonData.put("CallsPendingCount", "20");
			jsonData.put("CallsClosedCount", "25");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _ClientReport4(String client, String region, String maintType, String startDate, String endDate) {
	//private JsonJavaObject _ClientReport4(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "Region 1");
			jsonData.put("NumCalls", 10);
			jsonData.put("CallType", "Call Type 1");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "Region 2");
			jsonData.put("NumCalls",20);
			jsonData.put("CallType", "Call Type 2");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "Region 3");
			jsonData.put("NumCalls", 30);
			jsonData.put("CallType", "Call Type 3");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("Region", "Region 4");
			jsonData.put("NumCalls", 40);
			jsonData.put("CallType", "Call Type 4");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _ClientReport5(String client, String region, String maintType, String startDate, String endDate) {
	//private JsonJavaObject _ClientReport5(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site Name 1");
			jsonData.put("Region", "Region 1");
			jsonData.put("LastCallType", "Last Call Type 1");
			jsonData.put("LastJobDate", "2015/08/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site Name 2");
			jsonData.put("Region", "Region 2");
			jsonData.put("LastCallType", "Last Call Type 2");
			jsonData.put("LastJobDate", "2015/09/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site Name 3");
			jsonData.put("Region", "Region 3");
			jsonData.put("LastCallType", "Last Call Type 3");
			jsonData.put("LastJobDate", "2015/10/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site Name 4");
			jsonData.put("Region", "Region 4");
			jsonData.put("LastCallType", "Last Call Type 4");
			jsonData.put("LastJobDate", "2015/11/01");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _SiteReport1(String client) {
	//private JsonJavaObject _SiteReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 1");
			jsonData.put("Region", "Region 1");
			jsonData.put("County", "County 1");
			jsonData.put("Coordinates", "Coordinates 1");
			jsonData.put("SiteType", "SiteType 1");
			jsonData.put("BSNumber", "BS Number 1");
			jsonData.put("MaintOfficer", "Maint Officer 1");
			jsonData.put("LastVisit", "2015/08/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 2");
			jsonData.put("Region", "Region 2");
			jsonData.put("County", "County 2");
			jsonData.put("Coordinates", "Coordinates 2");
			jsonData.put("SiteType", "SiteType 2");
			jsonData.put("BSNumber", "BS Number 2");
			jsonData.put("MaintOfficer", "Maint Officer 2");
			jsonData.put("LastVisit", "2015/09/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 3");
			jsonData.put("Region", "Region 3");
			jsonData.put("County", "County 3");
			jsonData.put("Coordinates", "Coordinates 3");
			jsonData.put("SiteType", "SiteType 3");
			jsonData.put("BSNumber", "BS Number 3");
			jsonData.put("MaintOfficer", "Maint Officer 3");
			jsonData.put("LastVisit", "2015/10/01");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 4");
			jsonData.put("Region", "Region 4");
			jsonData.put("County", "County 4");
			jsonData.put("Coordinates", "Coordinates 4");
			jsonData.put("SiteType", "SiteType 4");
			jsonData.put("BSNumber", "BS Number 4");
			jsonData.put("MaintOfficer", "Maint Officer 4");
			jsonData.put("LastVisit", "2015/11/01");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _SiteReport2(String client, String region, String county, String maintType, String startDate, String endDate) {
	//private JsonJavaObject _SiteReport2(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 1");
			jsonData.put("Region", "Region 1");
			jsonData.put("County", "County 1");
			jsonData.put("NumCalls", 1);
			jsonData.put("TimeSpent", 10);
			jsonData.put("TravelDist", 100);
			jsonData.put("CallType", "Call Type 1");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 2");
			jsonData.put("Region", "Region 2");
			jsonData.put("County", "County 2");
			jsonData.put("NumCalls", 2);
			jsonData.put("TimeSpent", 20);
			jsonData.put("TravelDist", 200);
			jsonData.put("CallType", "Call Type 2");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 3");
			jsonData.put("Region", "Region 3");
			jsonData.put("County", "County 3");
			jsonData.put("NumCalls", 3);
			jsonData.put("TimeSpent", 30);
			jsonData.put("TravelDist", 300);
			jsonData.put("CallType", "Call Type 3");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 4");
			jsonData.put("Region", "Region 4");
			jsonData.put("County", "County 4");
			jsonData.put("NumCalls", 4);
			jsonData.put("TimeSpent", 40);
			jsonData.put("TravelDist", 400);
			jsonData.put("CallType", "Call Type 4");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _OperationsReport1(String startDate, String endDate) {
	//private JsonJavaObject _OperationsReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 1");
			jsonData.put("Region", "Region 1");
			jsonData.put("County", "County 1");
			jsonData.put("Employee", "Employee 1");
			jsonData.put("AveHours", 111);
			jsonData.put("AveOvertime", 11);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 2");
			jsonData.put("Region", "Region 2");
			jsonData.put("County", "County 2");
			jsonData.put("Employee", "Employee 2");
			jsonData.put("AveHours", 122);
			jsonData.put("AveOvertime", 12);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 3");
			jsonData.put("Region", "Region 3");
			jsonData.put("County", "County 3");
			jsonData.put("Employee", "Employee 3");
			jsonData.put("AveHours", 133);
			jsonData.put("AveOvertime", 13);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("SiteName", "Site 4");
			jsonData.put("Region", "Region 4");
			jsonData.put("County", "County 4");
			jsonData.put("Employee", "Employee 4");
			jsonData.put("AveHours", 144);
			jsonData.put("AveOvertime", 14);
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _FinancialReport1(String client) {
	//private JsonJavaObject _FinancialReport1(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("PONumber", "PO Number 1");
			jsonData.put("PODate", "2015/10/01");
			jsonData.put("POValue", 1000);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("PONumber", "PO Number 2");
			jsonData.put("PODate", "2015/11/01");
			jsonData.put("POValue", 2000);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("PONumber", "PO Number 3");
			jsonData.put("PODate", "2015/12/01");
			jsonData.put("POValue", 3000);
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("PONumber", "PO Number 4");
			jsonData.put("PODate", "2016/01/01");
			jsonData.put("POValue", 4000);
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _FinancialReport2(String client) {
	//private JsonJavaObject _FinancialReport2(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("WIPYear", "2014");
			jsonData.put("WIPTotal", "102250.20");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("WIPYear", "2015");
			jsonData.put("WIPTotal", "44670.80");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	@SuppressWarnings("finally")
	private JsonJavaObject _FinancialReport3(String client) {
	//private JsonJavaObject _FinancialReport3(JsonJavaObject reportCriteria) {
		JsonJavaObject jsonHeader = new JsonJavaObject();
		JsonJavaObject jsonData = null;
		Vector<JsonObject> dataArray = new Vector<JsonObject>();

		try {
			jsonData = new JsonJavaObject();
			jsonData.put("WIPMonth", "October 2015");
			jsonData.put("WIPTotal", "59900.50");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("WIPMonth", "November 2015");
			jsonData.put("WIPTotal", "65125.30");
			dataArray.add(jsonData);
			
			jsonData = new JsonJavaObject();
			jsonData.put("WIPMonth", "December 2015");
			jsonData.put("WIPTotal", "38100.70");
			dataArray.add(jsonData);
			
			// Add JsonData to Header
			jsonHeader.putJsonProperty("Count", dataArray.size());
			jsonHeader.putJsonProperty("ReportData", dataArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jsonHeader;
		}
	}
	
	public ArrayList<JsonJavaObject> getDashboard1() {
		return dashboard1;
	}
	
	public void setDashboard1(ArrayList<JsonJavaObject> dashboard1) {
		this.dashboard1 = dashboard1;
	}
	
	public ArrayList<JsonJavaObject> getClientDashboard() {
		return clientDashboard;
	}
	
	public void setClientDashboard(ArrayList<JsonJavaObject> clientDashboard) {
		this.clientDashboard = clientDashboard;
	}
	
	public ArrayList<JsonJavaObject> getSiteDashboard() {
		return siteDashboard;
	}
	
	public void setSiteDashboard(ArrayList<JsonJavaObject> siteDashboard) {
		this.siteDashboard = siteDashboard;
	}
	
	public ArrayList<JsonJavaObject> getOperationsDashboard() {
		return operationsDashboard;
	}
	
	public void setOperationsDashboard(ArrayList<JsonJavaObject> operationsDashboard) {
		this.operationsDashboard = operationsDashboard;
	}
	
	public ArrayList<JsonJavaObject> getFinancialDashboard() {
		return financialDashboard;
	}
	
	public void setFinancialDashboard(ArrayList<JsonJavaObject> financialDashboard) {
		this.financialDashboard = financialDashboard;
	}
}
