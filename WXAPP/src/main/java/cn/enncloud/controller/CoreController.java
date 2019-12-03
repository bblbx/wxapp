package cn.enncloud.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.enncloud.service.CoreService;
import cn.enncloud.util.CommonUtil;
import cn.enncloud.wx.util.SignUtil;

@RestController
@RequestMapping("/core")
public class CoreController {
	private static final Logger logger = LoggerFactory.getLogger(CoreController.class);
	@Autowired
	private CoreService coreService;
//	@RequestMapping(value="/check",method = {RequestMethod.GET, RequestMethod.POST})
	@RequestMapping(value="/check",method = {RequestMethod.GET})
    @ResponseBody
	public void checkSign(HttpServletRequest request, HttpServletResponse response){
		logger.error("微信验证开始");
		System.out.println("微信验证开始");
        //获取微信后台传入的四个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println("微信验证signature:"+signature+" timestamp:"+timestamp
        		+" nonce:"+nonce
        		+" echostr:"+echostr);
        if (CommonUtil.IsNullOrEmpty(signature) || CommonUtil.IsNullOrEmpty(timestamp) || 
        		CommonUtil.IsNullOrEmpty(nonce) || CommonUtil.IsNullOrEmpty(echostr)) {
        	return ;
        }
		boolean flag = SignUtil.checkSignature(signature, timestamp, nonce);
        PrintWriter p = null;
        try {
            p = response.getWriter();
            if(flag){
                p.print(echostr);//注意此处必须返回echostr以完成验证
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("微信验证失败"+CommonUtil.getTrace(e));
        } finally{
        	if (p!=null){
        		p.close();
        		p=null;
        	}
        }
	}
	/**
	 * @Description:  处理微信服务器发来的消息
	 * @author: liubaoxun
	 * @create: 2017年9月11日下午2:57:36
	 * @update: 2017年9月11日下午2:57:36
	 * @param request
	 * @param response void
	 */
	@RequestMapping(value="/check",method = {RequestMethod.POST})
    @ResponseBody
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		BufferedReader reader = null;
		String receiveXml ="";  //微信通知的xml
        try {
            // 消息的接收、处理、响应
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        // 调用核心业务类接收消息、处理消息
			logger.info("接收微信推送的通知消息...");
			String inputLine;
			reader = request.getReader();
			while ((inputLine = reader.readLine()) != null) {
				receiveXml += inputLine;
			}
			logger.info("收到微信消息原始报文：" + receiveXml);
//			coreService.ProcessXMLRequest(receiveXml);
			coreService.ProcessWXMsg(receiveXml);
//			String respXml = coreService.ProcessRequest(request);
//			System.out.println(respXml);
	        // 响应消息
	        out = response.getWriter();
//	        out.print(respXml);
	        out.print("success");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			if (reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (out!=null)
		        out.close();
		}
	}
}
