package com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;

import com.data.Data;

public class MainClass {
	public static void main(String[] args) throws Exception {
		String date = "2015-04-17";
		String filePath1 = "D:\\" + date + "重庆.txt";
		writeString2File("数据个数,时间,万位,千位,百位,十位,个位\r\n", filePath1);
		String filePath2 = "D:\\" + date + "江西.txt";
		writeString2File("数据个数,时间,万位,千位,百位,十位,个位\r\n", filePath2);
		while(true) {
			System.out.println("重庆：");
			try {
				calcData("http://baidu.lecai.com/lottery/draw/sorts/ajax_get_draw_data.php?lottery_type=200&date=" + date, filePath1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("江西：");
			try {
				calcData("http://baidu.lecai.com/lottery/draw/sorts/ajax_get_draw_data.php?lottery_type=202&date=" + date, filePath2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.currentThread().sleep(600000L);
		}
	}
	
	public static void calcData(String url, String filePath) throws HttpException, IOException {
		int cpdata[][] = null;
		
		String dataStr = Data.getCQSSCData(url);
		JSONObject object = JSONObject.fromObject(dataStr);
		JSONObject data = object.getJSONObject("data");
		String datetime = object.getString("datetime");
		int rows = data.getInt("rows");
		cpdata = new int[rows][5];
		JSONArray dataArr = data.getJSONArray("data");
		
		for(int i = 0; i < dataArr.size(); i++) {
			JSONObject everyData = dataArr.getJSONObject(i);
			JSONObject everyDataResult = everyData.getJSONObject("result");
			JSONArray everyDataResultArr = everyDataResult.getJSONArray("result");
			JSONObject resultdataObj = everyDataResultArr.getJSONObject(0);
			JSONArray dataArray = resultdataObj.getJSONArray("data");
			
			int cqDataArr[] = new int[5];
			for(int j = 0; j < 5; j ++) {
				cqDataArr[j] = Integer.parseInt(dataArray.getString(j));
			}
			cpdata[i] = cqDataArr;
		}
		
		int resultData[] = new int[]{0, 0, 0, 0, 0};
		
		for(int i = 0; i < cpdata.length; i++) {
			int tmpData[] = cpdata[i];
			for(int k = 0; k < 5; k++) {
				resultData[k] += tmpData[k];
			}
		}
		String dataResultStr = rows + "," + datetime + "," + resultData[0] + "," + resultData[1] + "," + resultData[2] + "," + resultData[3] + "," + resultData[4] + "\r\n";
		System.out.println(dataResultStr);
		writeString2File(dataResultStr, filePath);
		
//		System.out.println("    单       ||  双");
//		for(int k = 0; k < 5; k++) {
//			boolean a = cpdata[0][k]%2==1 && cpdata[1][k]%2==1 && cpdata[2][k]%2==1 && cpdata[3][k]%2==1 && cpdata[4][k]%2==1;
//			boolean b = cpdata[0][k]%2==0 && cpdata[1][k]%2==0 && cpdata[2][k]%2==0 && cpdata[3][k]%2==0 && cpdata[4][k]%2==0;
//			System.out.println(a + " || " + b);
//		}
	}

	private static void writeString2File(String dataResultStr, String filePath) throws IOException {
		FileWriter writer = new FileWriter(new File(filePath), true);
		writer.append(dataResultStr);
		writer.flush();
		writer.close();
	}
}
