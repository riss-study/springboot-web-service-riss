package com.riss.book.springboot.domain.posts;

import com.riss.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts (String title, String content, String author) {
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update (String title, String content) {
        this.title=title;
        this.content=content;
    }
}

/*
* @Entity: table과 link될 class 임을 명시
*       기본값으로 class의 camel case 이름을 언더스코어 네이밍(_)으로 테이블 매칭
*       ex.SalesManager.java-> sales_manager table
*
* @Id: 해당 table의 PK 임을 명시
*
* @GeneratedValue: PK의 생성 규칙을 명시
*       GenerationType.IDENTITY: auto_increment(spring boot 2.0 이상)
*
* @Column: 테이블의 칼럼을 명시, 굳이 선언하지 않아도 해당 class의 field는 모두 column
*       해당 칼럼에서 기본값 외에 추가 변경이 필요한 옵션을 위해 사용
*       ex. 문자열 기본값이 VARCHAR(255)인데, 사이즈를 500으로 늘리고 싶은 경우,
*           타입을 TEXT로 변경하고 싶은 경우 등
*
* @NoArgsConstructor: 기본 생성자 자동추가. public Posts () {}와 같은 효과
*
* @Getter: class 내 모든 field의 Getter 메소드를 자동 생성
*
* @Builder: 해당 class의 builder pattern class를 생성
*       생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
*
* 주의: Entity class는 setter를 절대 만들지 않음.
*       필요한 경우 목적과 의도가 드러난 특정 메소드를 추가 ex. cancelOrder();
*
* 신기한 것: update 기능에서 dataase에 query를 날리는 부분이 없음.
* => JPA의 영속성 컨텍스트 때문에 가능한 일!
*
* 영속성 컨텍스트: entity를 영구 저장하는 환경. 일종의 논리적인 개념임.
* => JPA의 핵심 내용은 Entity가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림
* => JPA의 Entity Manager가 활성화된 상태(Spring Data Jpa를 쓴다면 기본 옵션)로
*     트랜잭션 안에서 데이터베이스에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태임.
*
* ** dirty Checking: 영속성 컨텍스트가 유지된 상태에서 해당 데이터의 값을 변경하면,
*     트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함.
*     즉, Entity 객체의 값만 변경하면 별도로 Update Query를 날릴 필요가 없는 것에 대한 개념
* */
