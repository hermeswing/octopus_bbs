package octopus.basic.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.basic.user.dto.UserDto;
import octopus.basic.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * 사용자 조회
     * 
     * @param userId -사용자 ID
     * @return 사용자 정보
     */
    public UserDto findById(final String userId) {
        
        UserDto dto = userRepository.findByUserId(userId);
        
        log.debug("dto :: {}", dto);
        
        return dto;
    }
    
}