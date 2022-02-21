package com.riss.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

}

/*
* extends JpaRepository<Entity 클래스, PK 타입>: 기본적인 CRUD 메소드 자동 생성
*
* 주의: Entity class와 기본 Entity Repository는 같은 디렉토리에 위치해야 함
* 프로젝트 규모 확장으로 인해 도메인 별 프로젝트 분리가 필요하면 이 두개는 함께 움직여야 하므로,
* 도메인 패키지에서 함께 관리
*
* */
