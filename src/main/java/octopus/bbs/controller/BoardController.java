package octopus.bbs.controller;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.anotation.LoginUser;
import octopus.base.model.CommonResult;
import octopus.base.model.ListResult;
import octopus.base.model.PagingListResult;
import octopus.base.model.SingleResult;
import octopus.base.model.UserSessionDto;
import octopus.base.service.ResponseService;
import octopus.bbs.dto.BoardDto;
import octopus.bbs.dto.BoardSearchDto;
import octopus.bbs.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/bbs")
public class BoardController {
    
    private final MessageSourceAccessor messageSourceAccessor;
    private final BoardService          boardService;
    private final ResponseService       responseService;
    
    @GetMapping("/list")
    public @ResponseBody ListResult<BoardDto> findAll() {
        return responseService.getListResult(boardService.findAll());
    }
    
    /**
     * <pre>
     * 게시글 리스트 페이지
     * </pre>
     * 
     * @return
     */
    @GetMapping("/list/page")
    public String findAllOfPage(@ModelAttribute("params") final BoardSearchDto params,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        // page : 페이지
        // size : record의 갯수
        model.addAttribute("result", boardService.findAllOfPage(params, pageable));
        
        return "post/list";
    }
    
    /**
     * <pre>
     * 게시글 리스트 페이지
     * </pre>
     * 
     * @return
     */
    @GetMapping("/list/page2")
    public String findAllOfPage2(@ModelAttribute("params") final BoardSearchDto params,
            Model model) {
        
        PageRequest pageRequest = PageRequest.of(params.getPage(), params.getRecordSize(), Sort.by(Sort.Direction.DESC, "id"));
        
        model.addAttribute("result", boardService.findAllOfPage2(params, pageRequest));
        
        return "post/list";
    }
    
    @GetMapping("/id/{id}")
    public @ResponseBody SingleResult<BoardDto> findCodeByCd(@PathVariable Long id) {
        return responseService.getSingleResult(boardService.findById(id));
    }
    
    // 게시글 상세 페이지
    @GetMapping("/view/{id}")
    public String openPostView(@RequestParam final Long id, Model model) {
        model.addAttribute("result", boardService.findById(id));
        return "post/view";
    }
    
    @PostMapping("/save")
    public @ResponseBody SingleResult<BoardDto> save(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("BoardDto :: {}", dto);
        
        return responseService.getSingleResult(boardService.save(dto));
    }
    
    @PutMapping("/update")
    public @ResponseBody SingleResult<String> update(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setMdfId(userDto.getUserId());
        
        log.debug("dto :: {}", dto);
        
        boardService.update(dto);
        
        return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsSaved")); // 저장되었습니다.
    }
    
    @DeleteMapping("/id/{id}")
    public @ResponseBody CommonResult delete(@PathVariable Long id) {
        
        boardService.delete(id);
        
        return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsDeleted")); // 삭제되었습니다.
    }
}
