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
        setSuccessResult(result);
        
        return result;
    }
    
    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        
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
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg(messageSourceAccessor.getMessage("msg.ok")); // 정상처리되었습니다.
        
        return result;
    }
    
    /**
     * @Method : setSuccessResult
     * @Description : 결과에 api 요청 성공 데이터 세팅
     * @Parameter : [result]
     * @Return : null
     **/
    public void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg(messageSourceAccessor.getMessage("msg.ok")); // 정상처리되었습니다.
    }
    
    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        
        return result;
    }
    
    /**
     * @Method : setFailResult
     * @Description : 실패 결과 바인딩
     * @Parameter : [commonResponse, result]
     * @Return : null
     **/
    public void setFailResult(CommonResult result) {
        result.setSuccess(false);
        result.setMsg(messageSourceAccessor.getMessage("msg.fail")); // 실패하였습니다.
        result.setCode(-1);
    }
    
}