/**
* Service4All: A Service-oriented Cloud Platform for All about Software Development
* Copyright (C) Institute of Advanced Computing Technology, Beihang University
* Contact: service4all@act.buaa.edu.cn
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 3.0 of the License, or any later version. 
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details. 
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
* USA

 */
package monitor.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.bean.AppInfo;
import monitor.bean.MonitorInfo;
import monitor.util.DocParser;
import monitor.util.ViewChart;


/**
 * Servlet for webapp invoke result chart request,
 * use host.id,tomcat.port,app.name as parameter,
 * returns corresponding invoke result chart image
 * 
 * @author Zhao Tao
 *
 */
public class GetInvokeResultInfo extends HttpServlet {

	private static final long serialVersionUID = 3789102903507919064L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out = response.getOutputStream();
		ViewChart vc = new ViewChart();
		DocParser parser=new DocParser();
		MonitorInfo host=parser.parse();
		String hostId=request.getParameter("hostId");
		String tomcatId=request.getParameter("tomcatId");
		String appId=request.getParameter("appId");
		AppInfo aInfo=host.getHostInfoById(Integer.parseInt(hostId)).getTomcatInfoById(Integer.parseInt(tomcatId)).getAppInfoById(appId);
		if(aInfo!=null){
			double[][] data={{aInfo.getSuccessfull(),aInfo.getFailed()}};
			String[] rowKeys = { "invoke result" };
			String[] columnKeys={"successful","failed"};
			String chartTitle = "Invoke Result";
			String valueAxisLabel = "invoke numbers";
			byte[] pic = vc.makeBarChart(data, rowKeys, columnKeys, chartTitle, valueAxisLabel);
			out.write(pic);
			out.flush();
			out.close();
		}
	}

}
