package octopus.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.val;

/** 基底インターセプター */
public abstract class BaseHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Controller 진입 전
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// Controller 처리 후

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 처리완료 후
	}

	/**
	 * Rest Controller인지 여부를 나타내는 값을 반환
	 *
	 * @param handler
	 * @return
	 */
	// protected boolean isRestController(Object handler) {
	// val bean = getBean(handler, AbstractRestController.class);
	//
	// if (bean instanceof AbstractRestController) {
	// return true;
	// }
	//
	// return false;
	// }

	/**
	 * 인수 개체가 지정 클래스인지 여부를 나타냅니다.
	 *
	 * @param obj
	 * @param clazz
	 * @return
	 */
	protected boolean isInstanceOf(Object obj, Class<?> clazz) {

		if (clazz.isAssignableFrom(obj.getClass())) {
			return true;
		}

		return false;
	}

	/**
	 * Handler의 Bean을 반환합니다.
	 *
	 * @param handler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getBean(Object handler, Class<T> clazz) {

		if (handler != null && handler instanceof HandlerMethod) {
			val hm = ((HandlerMethod) handler).getBean();

			if (clazz.isAssignableFrom(hm.getClass())) {
				return (T) hm;
			}

			return null;
		}

		return null;
	}
}