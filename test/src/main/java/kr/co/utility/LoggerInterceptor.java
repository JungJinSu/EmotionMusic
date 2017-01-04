package kr.co.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;	

/*
 * Interceptor �� ����ϴ� ����
<<<<<<< HEAD
 	1) ��ÿ� ���ʿ��� �αװ� ��� ��µȴ�. 	
 		- �ý����� �����ϰ� ��� ��, System.out.println()�� ��� ã�Ƽ� �����شٸ� ����, ���� ��κ��� �׳� �Ѵ�....��_��; �׷��� ������� �α׷� �Ʊ�� ���ҽ��� ����ȴ�.
	2) ��� �α׸� ����������, ������ �������, �� ���� ������ ã�Ⱑ ����� ���� �ִ�. 
		- ���� ���, �ý��ۿ� �ߴ��� ������ �� ���, �α׸� ����س����� �س��µ�, ������ System.out.println�� ��� �������ȴٸ�, �αװ� �ȳ��� ���� �ִ�.
	3) ���ɿ� ū ������ ��ģ��. 
		- ��� ���� �߿��� ������. �츮�� ���α׷��� �����ϴٰ� System.out.println() �� ������ ���� ȣ���ϸ� ���α׷��� ��ü���� ������ �������°��� Ȯ���� �� �ִ�. ���� ��� 1���� 100������ ��� ���ϴ� ���α׷��� ���������, �α׸� �ϳ��� �������� ���� 0.01�ʵ� �Ȱɷ��� ��������, �� �������� ��� System.out.println()���� ȭ�鿡 ����....���� �ɸ���... Ư�� ���߻���ڸ� ó���ؾ� �ϴ� ������ System.out.println()�� ���� ū ������ ����������. 
 
 *
 * ��� Ŭ���� : HandlerInterceptorAdapter - ��ó����� ��ó���� ����� ���� 
 *   client -> controller �� ��û�� ��, �� ��û�� ó���� �޼��� �ϳ�(��ó����)
 *   controller -> client �� ������ ��, �� ��û�� ó���� �޼��� �ϳ�(��ó����)
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	// log4.j  �� ����ϴ� ���
	protected Log log = LogFactory.getLog(LoggerInterceptor.class);		// Log ��ü�� log �̸����� ����(�����ڿ� ���� Ŭ���� �Է�)
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
	       if (log.isDebugEnabled()) {
	            log.debug("======================================          START         ======================================");
	            log.debug(" Request URI \t:  " + request.getRequestURI());
	        }
		return super.preHandle(request, response, handler);
	}
	
	 @Override
	 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        if (log.isDebugEnabled()) {
	            log.debug("======================================           END          ======================================\n");
	        }
	  }
	
}

