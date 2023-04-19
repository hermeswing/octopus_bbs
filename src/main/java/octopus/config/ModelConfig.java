package octopus.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {
	private final ModelMapper modelMapper = new ModelMapper();

	// Model Mapper
	// DTO <-> Entity Type Change
	@Bean
	public ModelMapper modelMapper() {
		return modelMapper;
	}

	@Bean
	public ModelMapper stricMapper() {
		// 매핑전략설정
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper;
	}

	@Bean
	public ModelMapper standardMapper() {
		// 매핑전략설정
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

		return modelMapper;
	}

	@Bean
	public ModelMapper looseMapper() {
		// 매핑전략설정
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return modelMapper;
	}
}
