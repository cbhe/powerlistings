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
 * ���⣺@WebFilter���Խ�һ��������Ϊ����������ע�⽫���ڲ���
 * ʱ�����������������ݾ�����������ý���Ӧ���ಿ��Ϊ��������
 * ��ע������±������һЩ�������ԣ������������Ծ�Ϊ��ѡ���ԣ�
 * ����value��urlPatterns��servletNames���߱������ٰ���һ����
 * ��value��urlPatterns���ܹ��棬���ͬʱָ����ͨ������value��
 * ȡֵ����
 * 
 * filterName		String			ָ����������name���ԣ��ȼ���<filter-name>
 * value			String[]		�����Եȼ���urlPatterns���ԣ��������߲�Ӧ��ͬʱʹ��
 * urlPatterns		String[]		ָ��һ�������URLƥ��ģʽ���ȼ���<url-pattern>
 * servletNames		String[]		ָ������������������Щserlvet��ȡֵ��web.xml��<servlet-name>��ֵ����@WebServlet�е�name���Ե�ֵ��
 * dispatcherTypes	DispatcherType	ָ��������ת��ģʽ��������ASYNC,ERROR,FORWARD,INCLUDE,REQUEST.
 * initParamns		WebInitParam[]	ָ��һ���������ʼ���������ȼ���<init-param>��ǩ��
 * asyncSupported	boolean			�����������Ƿ�֧���첽����ģʽ���ȼ���<async-supported>��ǩ��
 * description		String			�ù�������������Ϣ���ȼ���<description>��ǩ
 * displayName		String			�ù�������ʾ���ƣ�ͨ����Ϲ���ʹ�ã��ȼ���<display-name>��ǩ��
 * 
 * �򵥵�ʾ����
 * @WebFilter(
 * 			  servletNames = {"SimpleSerlvet"},
 * 			  filterName = "SimpleFilter"
 * 			 )
 */
/**
 * ��Filter��˵����
 * ���ڷ���Update�����Cancel������Ϊ��������������/<id>��β��
 * �����޷�����URL��ȷ��������Updateʹ��Request.PUT������Cancel
 * ʹ��Request.DELETE��������˿��Ը���request.getMethod()�Ĳ�ͬ
 * ��������Update����Cancel��
 */
@WebFilter(filterName="CancelOrUpdate", urlPatterns={"/*"})
public class CancelOrUpdate implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String method = req.getMethod();
		request.setCharacterEncoding("utf-8");

		if (method.equals("DELETE") || method.equals("PUT")) {
			//��powerlistings/<id>�е�idȡ���ŵ�session�б����ѱ���һ������ʹ��
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
