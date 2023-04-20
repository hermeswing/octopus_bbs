package octopus.bbs.controller;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.model.ListResult;
import octopus.base.model.SingleResult;
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
    //
    // @PostMapping("/code")
    // public SingleResult<BoardDto> save(
    // @RequestParam(required = true) String title,
    // @RequestParam(required = true) String contents,
    // @LoginUser UserSessionDto userDto) {
    //
    // BoardDto dto = BoardDto.builder().title(title).contents(contents).build();
    //
    // dto.setCrtId(userDto.getUserId());
    // dto.setMdfId(userDto.getUserId());
    //
    // log.debug("tCodeMDto :: {}", dto);
    //
    // TBoardM board = boardService.save(dto);
    //
    // return responseService.getSingleResult(dto.makeDto(board));
    // }
    //
    // @PutMapping("/code")
    // public SingleResult<String> update(
    // @RequestParam(required = true) String title,
    // @RequestParam(required = true) String contents) {
    //
    // BoardDto dto = BoardDto.builder().title(title).contents(contents)
    // .build();
    //
    // log.debug("dto :: {}", dto);
    //
    // boardService.update02(dto);
    //
    // return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsSaved")); // 저장되었습니다.
    // }
    //
    // @DeleteMapping("/id/{id}")
    // public CommonResult delete(
    // @RequestParam(required = true) Long id) {
    //
    // BoardDto dto = BoardDto.builder().id(id).build();
    //
    // boardService.delete(dto);
    //
    // return responseService.getSingleResult(messageSourceAccessor.getMessage("msg.itIsDeleted")); // 삭제되었습니다.
    // }
}
