package octopus.bbs.posts.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.MessageSourceAccessor;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.anotation.LoginUser;
import octopus.base.model.CommonResult;
import octopus.base.model.ListResult;
import octopus.base.model.MessageDto;
import octopus.base.model.SingleResult;
import octopus.base.model.UserSessionDto;
import octopus.base.service.ResponseService;
import octopus.bbs.posts.dto.BoardDto;
import octopus.bbs.posts.dto.BoardSearchDto;
import octopus.bbs.posts.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class BoardController {
    
    private final MessageSourceAccessor messageSourceAccessor;
    private final BoardService          boardService;
    private final ResponseService       responseService;
    
    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        
        return "common/messageRedirect";
    }
    
    // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final BoardSearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }
    
    @GetMapping("/findAll")
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
    @GetMapping("/list")
    public String findAllOfPage(@ModelAttribute("params") final BoardSearchDto params,
            Model model) {
        
        PageRequest pageRequest = PageRequest.of(params.getPage(), params.getRecordSize(),
                Sort.by(Sort.Direction.DESC, "id"));
        
        model.addAttribute("result", boardService.findAllOfPage(params, pageRequest));
        
        return "post/list";
    }
    
    /**
     * <pre>
     * 게시글 리스트 페이지
     * Pageable 참조소스
     * </pre>
     * 
     * @return
     */
    @GetMapping("/list/page")
    public String findAllOfPage2(@ModelAttribute("params") final BoardSearchDto params,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        // page : 페이지
        // size : record의 갯수
        model.addAttribute("result", boardService.findAllOfPage2(params, pageable));
        
        return "post/list";
    }
    
    @GetMapping("/id/{id}")
    public @ResponseBody SingleResult<BoardDto> findCodeByCd(@PathVariable Long id) {
        return responseService.getSingleResult(boardService.findById(id));
    }
    
    // 게시글 상세 페이지
    @GetMapping("/view")
    public String openPostView(@RequestParam final Long id, Model model) {
        model.addAttribute("result", boardService.findById(id));
        
        return "post/view";
    }
    
    // 게시글 수정 페이지
    @GetMapping("/write")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Long id,
            Model model) {
        if (id != null) {
            BoardDto dto = boardService.findById(id);
            dto.parseDate(dto.getCrtDt(), dto.getMdfDt());
            log.debug("board dto :: {}", dto);
            model.addAttribute("result", dto);
        }
        
        return "post/write";
    }
    
    @PostMapping("/save")
    public String save(final BoardDto dto,
            @LoginUser UserSessionDto userDto, Model model) {
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("BoardDto :: {}", dto);
        
        boardService.save(dto);
        
        MessageDto message = new MessageDto(messageSourceAccessor.getMessage("msg.itIsSaved"),
                "/posts/list", RequestMethod.GET, null); // 저장되었습니다.
        return showMessageAndRedirect(message, model);
    }
    
    @PostMapping("/rest-save")
    public @ResponseBody SingleResult<BoardDto> restSave(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("BoardDto :: {}", dto);
        
        return responseService.getSingleResult(boardService.save(dto));
    }
    
    @PostMapping("/update")
    public String update(final BoardDto dto,
            @LoginUser UserSessionDto userDto, Model model) {
        
        dto.setMdfId(userDto.getUserId());
        
        log.debug("dto :: {}", dto);
        
        boardService.update(dto);
        MessageDto message = new MessageDto(messageSourceAccessor.getMessage("msg.itIsSaved"),
                "/posts/list", RequestMethod.GET, null); // 저장되었습니다.
        return showMessageAndRedirect(message, model);
    }
    
    @PutMapping("/rest-update")
    public @ResponseBody SingleResult<String> restUpdate(final @RequestBody BoardDto dto,
            @LoginUser UserSessionDto userDto) {
        
        dto.setMdfId(userDto.getUserId());
        
        log.debug("dto :: {}", dto);
        
        boardService.restUpdate(dto);
        
        return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsSaved")); // 저장되었습니다.
    }
    
    @DeleteMapping("/id/{id}")
    public @ResponseBody CommonResult restDelete(@PathVariable Long id) {
        
        boardService.delete(id);
        
        return responseService
                .getSuccessResult(messageSourceAccessor.getMessage("msg.itIsDeleted")); // 삭제되었습니다.
    }
}
