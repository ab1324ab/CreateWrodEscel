import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;




public class Demo {

	public static String URL = "http://61.135.203.142:13004/cashier";
	public static  String TERMID = "CADC00010001001";
	public static  String TERMKEY = "596BE6C4A8CB7EC141C085BEE3C8190E";
	private static final String NOTIFYURL = "http://127.0.0.1:8080/qqPay/testBackNotify";
	private static final String FRONTNOTIFYURL = "http://free.ngrok.cc/unionWTZSD/testFrontNotify";
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	static int i = 0;
	public static void main(String[] args) throws Exception {

		T001();
		
	}
	

	/*
	 * 消费
	 */
	public static void T001() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T001");
		map.put("termId", TERMID);
		map.put("orderNo", "20180326024333222"); //DateUtil.format("yyyyMMddHHmmssSSS")
		map.put("txnDate", "20171120");
		map.put("txnTime", "123423");
		map.put("amount", "23000");
		map.put("notifyUrl", NOTIFYURL);
		//map.put("payType", Params.PAYTYPE_QQ);
		//map.put("payType", Params.PAYTYPE_UNIONK);
		map.put("payType", "UNIONQR");
		//map.put("payType", Params.PAYTYPE_UNIONGATE);
		map.put("body", "备注：103092");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);
		
		String retData = doPost(URL, map);
		System.err.println(retData);
		JSONObject json = new JSONObject(retData);
		System.err.println(json.get("qrCode"));
	}


	/**
	 * 交易查询
	 */
	public static void T002() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T002");
		map.put("termId", TERMID);
		map.put("orderNo", "20180310190729016"); // 20171120211649
		map.put("txnDate", "20171120");
		map.put("txnTime", "123423");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);

		String retData = doPost(URL, map);
		System.err.println(retData);
	}
	
	/**
	 * 代付
	 */
	public static void T003() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T003");
		map.put("termId", TERMID);
		//map.put("orderNo", DateUtil.format("yyyyMMddHHmmssSSS"));
		map.put("txnDate", "20171120");
		map.put("txnTime", "123423");
		map.put("amount", "14373");
		map.put("notifyUrl", NOTIFYURL);
		map.put("realName", "");
		map.put("accNo", "");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);

		String retData = doPost(URL, map);
		System.err.println(retData);
	}
	
	/**
	 * 代付查询
	 */
	public static void T004() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T004");
		map.put("termId", TERMID);
		map.put("orderNo", "20180313173953051");
		map.put("txnDate", "20171120");
		map.put("txnTime", "123423");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);

		String retData = doPost(URL, map);
		System.err.println(retData);
		
		
	}
	
	/**
	 * 额度查询
	 */
	public static void T005() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T005");
		map.put("termId", TERMID);
		map.put("txnDate", "20171120");
		map.put("txnTime", "123423");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);

		String retData = doPost(URL, map);
		System.err.println(retData);
	}
	
	public static void T006() throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("transType", "T006");
		map.put("termId", TERMID);
		map.put("amount", "120");
		map.put("body", "备注");
		//map.put("orderNo", DateUtil.format("yyyyMMddHHmmssSSS"));
		map.put("notifyUrl", "https://127.0.0.1");

		String signature = makeMac(map, TERMKEY);
		map.put("signature", signature);

		//String retData = AcpService.createAutoFormHtml(URL, map, null);
		//System.err.println(retData);
	}
	
	
	public static String makeMac(Map<String, String> contentData,String macKey){
		String macStr="";
		Object[] key_arr = contentData.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			Object value = contentData.get(key);
			if (value != null && !value.equals("")) {
				if (!key.equals("signature") ) {
					macStr+= key + "=" + value.toString() + "&";
				}
			}
		}
		macStr += macKey;
		System.err.println("macStr:" + macStr);
		String mac = getMD5Str(macStr);
		System.err.println("mac:" + mac);
		return mac;
	}
	
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	
	
	public static String doPost(String urlPath,Map<String, String> params) throws Exception{
		HttpClient httpClient = new HttpClient();
		
		PostMethod postMethod = new PostMethod(urlPath);
		// 设置http的头
		postMethod.setRequestHeader("ContentType",
		"application/x-www-form-urlencoded;charset=UTF-8");
		httpClient.getParams().setContentCharset("UTF-8");  
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		
		NameValuePair[] requestBody = new NameValuePair[params.size()];
		int i = 0;
		for(String key : params.keySet()){
			String value = params.get(key);
			requestBody[i] = new NameValuePair(key, value);
			i++;
		}
		
		postMethod.setRequestBody(requestBody);
		
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode == HttpStatus.SC_OK) {
			String retData = postMethod.getResponseBodyAsString();
			return retData;
		}
        
		return null;
	}

}
