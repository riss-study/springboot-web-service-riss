package com.riss.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // JPA Auditing 활성화 (기존의 @SpringBootApplication 과 분리) => 두 어노테이션이 같이 있으면(SpringBootApplication 실행 시 @EnableJpaAuditing 을 스캔하므로 이때 @Entity 가 필요) @WebMvcTest 에서 이를 스캔하는데 @Entity 가 없으므로 에러 발생 (@WebNvcTest 는 일반적인 @configuration 은 스캔하지 않음)
public class JpaConfig {
}
