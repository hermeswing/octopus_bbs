package octopus.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import octopus.base.model.CommonResult;
import octopus.base.model.ListResult;
import octopus.base.model.SingleResult;

@Service
public class ResponseService {

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	// 단일건 결과를 처리하는 메소드
	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		result.setCode(0);
		result.setMsg(messageSourceAccessor.getMessage("msg.ok")); // 정상처리되었습니다.
		return result;
	}

	// 다중건 결과를 처리하는 메소드
	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		result.setCode(0);
		result.setMsg(messageSourceAccessor.getMessage("msg.ok")); // 정상처리되었습니다.
		return result;
	}

	// HATEOAS를 적용한 다중건 결과를 처리하는 메소드
	// public <T> ListResult<T> getListResult(CollectionModel<T> collection) {
	// ListResult<T> result = new ListResult<>();
	// result.setCollection(collection);
	// setSuccessResult(result);
	// return result;
	// }

	// 성공 결과만 처리하는 메소드
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		result.setCode(0);
		result.setMsg(messageSourceAccessor.getMessage("msg.ok")); // 정상처리되었습니다.
		return result;
	}

	// 실패 결과만 처리하는 메소드
	public CommonResult getFailResult(int code, String msg) {
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

}