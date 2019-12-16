package cn.enncloud.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.enncloud.util.Constants;
import cn.enncloud.util.PropertyConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends HttpServlet implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	private static final long serialVersionUID = 1L;
		
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
  	  HttpServletResponse resp = (HttpServletResponse)response;  
      HttpServletRequest req = (HttpServletRequest)request; 
      String userAgent = req.getHeader("User-Agent").toLowerCase();
      String requestURL = req.getRequestURL().toString();  
      String requestName = requestURL.substring(requestURL.lastIndexOf("/")+1); 
      if(requestName.matches(".*\\.js$")||
    		  requestName.matches(".*\\.png$")||
    		  requestName.matches(".*\\.css$")||
    		  requestName.matches(".*\\.html$") ||
    		  requestName.matches(".*\\.jpg$")||
    		  requestName.matches(".*\\.txt$")){ 
    	  chain.doFilter(request,response);  
          return;  
      }  
      //判断是不是在微信中打开，如果不是微信中打开则跳转到错误页面
      if( userAgent == null || userAgent.indexOf("micromessenger") == -1 ){
 		 if(PropertyConstants.TEST_ENV){
 			chain.doFilter(request,response); //测试
		 } else {
	    	  String path = req.getRequestURI().substring(req.getContextPath().length()); 
	    	  if(!Constants.WhitePath.contains(path)){//黑名单中的地址不允许访问
	    	    	 logger.info("不是在微信浏览器中打开，限制访问"+requestURL+" userAgent:"+userAgent);
	    	    	 System.out.println("不是在微信浏览器中打开，限制访问"+requestURL+" userAgent:"+userAgent);
		    		  resp.sendRedirect(req.getContextPath()+"/page/errorpage01.html");//提示页面
		    		  return ;
		    	  }
	    	  chain.doFilter(request,response); return ;
		 }
    	  return ;
      }else {
          chain.doFilter(request,response); return ;
      }
	}

}
