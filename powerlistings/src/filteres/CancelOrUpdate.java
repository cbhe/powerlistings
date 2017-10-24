package filteres;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import servlets.Cancel;
/**
 * 讲解：@WebFilter用以将一个类声明为过滤器，该注解将会在部署
 * 时被容器处理，容器根据具体的属性配置将相应的类部署为过滤器。
 * 该注解具有下表给出的一些常用属性（以下所有属性均为可选属性，
 * 但是value、urlPatterns、servletNames三者必须至少包含一个，
 * 且value和urlPatterns不能共存，如果同时指定，通常忽略value的
 * 取值）：
 * 
 * filterName		String			指定过滤器的name属性，等价于<filter-name>
 * value			String[]		该属性等价于urlPatterns属性，但是两者不应该同时使用
 * urlPatterns		String[]		指定一组过滤器URL匹配模式。等价于<url-pattern>
 * servletNames		String[]		指定过滤器将用于与哪些serlvet。取值是web.xml中<servlet-name>的值或者@WebServlet中的name属性的值。
 * dispatcherTypes	DispatcherType	指定过滤器转发模式，包括：ASYNC,ERROR,FORWARD,INCLUDE,REQUEST.
 * initParamns		WebInitParam[]	指定一组过滤器初始化参数，等价于<init-param>标签。
 * asyncSupported	boolean			声明过滤器是否支持异步操作模式，等价于<async-supported>标签。
 * description		String			该过滤器的描述信息，等价于<description>标签
 * displayName		String			该过滤器显示名称，通常配合工具使用，等价于<display-name>标签。
 * 
 * 简单的示例：
 * @WebFilter(
 * 			  servletNames = {"SimpleSerlvet"},
 * 			  filterName = "SimpleFilter"
 * 			 )
 */
/**
 * 该Filter的说明：
 * 用于分类Update请求和Cancel请求，因为这两个请求都是以/<id>结尾，
 * 所有无法根据URL来确定。但是Update使用Request.PUT方法而Cancel
 * 使用Request.DELETE方法，因此可以根据request.getMethod()的不同
 * 来区分是Update还是Cancel。
 */
@WebFilter(filterName="CancelOrUpdate", urlPatterns={"/*"})
public class CancelOrUpdate implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String method = req.getMethod();
		request.setCharacterEncoding("utf-8");

		if (method.equals("DELETE") || method.equals("PUT")) {
			//将powerlistings/<id>中的id取出放到session中保存已备下一个环节使用
			String dataId = req.getServletPath().substring(1);
			HttpSession session = req.getSession();
			session.setAttribute("dataId", dataId);
			
			if (method.equals("DELETE")) {
				request.getRequestDispatcher("/cancel").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/update").forward(request, response);
			}
			
		}
		else{
			chain.doFilter(request, response);
		}
	}

	
	
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {		
	}

}
