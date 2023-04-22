package octopus.bbs.controller;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.anotation.LoginUser;
import octopus.base.model.CommonResult;
import octopus.base.model.ListResult;
import octopus.base.model.SingleResult;
import octopus.base.model.UserSessionDto;
import octopus.base.service.ResponseService;
import octopus.bbs.dto.BoardDto;
import octopus.bbs.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bbs")
public class BoardController {
    
    private final MessageSourceAccessor messageSourceAccessor;
    private final BoardService          boardService;
    private final ResponseService       responseService;
    
    @GetMapping("/id/{id}")
    public SingleResult<BoardDto> findCodeByCd(@PathVariable Long id) {
        return responseService.getSingleResult(boardService.findById(id));
    }
    
    @GetMapping("/list")
    public ListResult<BoardDto> findAll() {
        return responseService.getListResult(boardService.findAll());
    }
    
    @PostMapping("/save")
    public SingleResult<BoardDto> save(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("BoardDto :: {}", dto);
        
        return responseService.getSingleResult(boardService.save(dto));
    }
    
    @PutMapping("/update")
    public SingleResult<String> update(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setMdfId(userDto.getUserId());
        
        log.debug("dto :: {}", dto);
        
        boardService.update(dto);
        
        return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsSaved")); // 저장되었습니다.
    }
    
    @DeleteMapping("/id/{id}")
    public CommonResult delete(@PathVariable Long id) {
        
        boardService.delete(id);
        
        return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsDeleted")); // 삭제되었습니다.
    }
}
