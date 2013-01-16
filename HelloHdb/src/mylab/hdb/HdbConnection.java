package mylab.hdb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HdbConnection implements Runnable {

	private static final String HDB_PAGE1 = "http://services2.hdb.gov.sg/webapp/BB33RTIS/BB33PReslTrans.jsp";
	private static final String HDB_PAGE2 = "http://services2.hdb.gov.sg/webapp/BB33RTIS/BB33SSearchWidget";
	private static final String HDB_PAGE2_INIT_PARAMS = "site=hdb&q=&filter_s=Within+HDB+Website%23hdb%7CWithin+All+Government+Websites%23default%7C&feedback_url=https%3A%2F%2Fservices2.hdb.gov.sg%2Fwebapp%2FBE05Feedback%2FBE05SFrontController%3Fservice%3DServiceFeedback%26operation%3DcreateFeedback&contact_url=http%3A%2F%2Fwww.hdb.gov.sg%2Ffi10%2Ffi10296p.nsf%2FWPDis%2FContact%2BUsOverview%3FOpenDocument&client=default&proxystylesheet=default&output=xml_no_dtd&Process=continue";
	
	public String hdbRoom = "";
	public String hdbTown = "";
	
	public Handler handler;
	
	@Override
	public void run() {
		HttpURLConnection connection = null;

        if (HelloHdbActivity.activityNotStop == false) return;
        
        PeriodDTO period = HdbCache.getInstance().delta(hdbTown,hdbRoom);
        String startPeriod = period.startPeriod;
        String endPeriod = period.endPeriod;

		try {
            if (startPeriod != null && endPeriod != null) {
                if (HelloHdbActivity.activityNotStop == false) return;

                URL url = new URL(HDB_PAGE1);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    throw new Exception("First connection attempt throws Exception : " + connection.getResponseCode() + " - " + connection.getResponseMessage());

                List<String> cookies = connection.getHeaderFields().get("Set-Cookie");

                connection.disconnect();

                if (HelloHdbActivity.activityNotStop == false) return;

                url = new URL(HDB_PAGE2);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");

                String requestCookie = "";
                for (String cookie : cookies) {
                    requestCookie += ";" + cookie.split(";", 2)[0];
                }
                requestCookie = requestCookie.substring(1);
                connection.setRequestProperty("Cookie", requestCookie);

                connection.setDoOutput(true);

                String hdbPage2FinalParams =
                        "&FLAT_TYPE=%s" +
                                "&NME_NEWTOWN=%s" +
                                "&NME_STREET=" +
                                "&NUM_BLK_FROM=" +
                                "&NUM_BLK_TO=" +
                                "&AMT_RESALE_PRICE_FROM=" +
                                "&AMT_RESALE_PRICE_TO=" +
                                "&DTE_APPROVAL_FROM=%s" +
                                "&DTE_APPROVAL_TO=%s";
                hdbPage2FinalParams = String.format(hdbPage2FinalParams, hdbRoom, URLEncoder.encode(hdbTown,"UTF-8"), startPeriod, endPeriod);
                hdbPage2FinalParams = HDB_PAGE2_INIT_PARAMS + hdbPage2FinalParams;

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(hdbPage2FinalParams);
                out.flush();
                out.close();

                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    throw new Exception("Second connection attempt throws Exception : " + connection.getResponseCode() + " - " + connection.getResponseMessage());

                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                StringBuilder sb = new StringBuilder();
                String str = br.readLine();
                while (str != null) {
                    sb.append(str);
                    str = br.readLine();
                }

                connection.disconnect();

                if (HelloHdbActivity.activityNotStop == false) return;

                cache(sb.toString());

            }

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			Log.d("HdbConnection", sw.toString());
		} finally {
            Log.d("HdbConnection", "Finished requesting data for room : " + hdbRoom + " and town : " + hdbTown);

            Bundle data = new Bundle();
            data.putString("hdbRoom", hdbRoom);
            data.putString("hdbTown", hdbTown);

            Message msg = new Message();
            msg.setData(data);
            handler.sendMessage(msg);

        }
	}
	
	private void cache(String str) {
        Pattern pattern = Pattern.compile("<tr height=\"30\" class=\"svcTableRow.+?>\\s*<td.*?><a.+?>(.*?)</a></td>\\s*<td.*?>(.*?)</td>\\s*<td.*?>(.*?)</td>\\s*<td.*?>(.*?)</td>\\s*<td.*?>(.*?)</td>\\s*<td.*?>(.*?)</td>\\s*<td.*?>(.*?)</td>");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            HdbDTO dto = new HdbDTO();

            dto.flatType = hdbRoom;
            dto.town = hdbTown;

            String block = matcher.group(1);
            dto.block = block;

            String street = matcher.group(2);
            street = street.replaceAll("&nbsp;", "");
            dto.street = street;

            String storey = matcher.group(3);
            dto.storey = storey;

            String flatModel = matcher.group(4);
            flatModel = flatModel.replaceAll("&#46;00<br/>", " ");
            dto.flatModel = flatModel;

            String leaseCommenceDate = matcher.group(5);
            dto.leaseCommenceDate = leaseCommenceDate;

            String resalePrice = matcher.group(6);
            resalePrice = ((resalePrice.replaceAll("&#44;", "")).replaceAll("&#46;", ".")).substring(1);
            dto.resalePrice = (int)(Double.parseDouble(resalePrice));

            String resaleApprovalDate = matcher.group(7);
            dto.resaleApprovalDate = resaleApprovalDate;

            HdbCache.getInstance().cache(dto);
        }
	}
	
}
