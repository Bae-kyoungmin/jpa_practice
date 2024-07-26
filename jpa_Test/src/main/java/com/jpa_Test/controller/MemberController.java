package com.jpa_Test.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa_Test.entity.MemberEntity;
import com.jpa_Test.entity.OrderEntity;
import com.jpa_Test.entity.QMemberEntity;
import com.jpa_Test.entity.QOrderEntity;
import com.jpa_Test.service.MemberService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {

	@Autowired
    private final MemberService memberService = new MemberService();
	
	@GetMapping("/")
	public String index() {
		
		return "test";
	}

    /**
     * Member 생성
     *
     * @return
     * @throws ParseException
     */
    @PostMapping("/create")
    public ResponseEntity<MemberEntity> createMember(String name, String email, String nickname, int age, String birthday) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);
        MemberEntity member = MemberEntity.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .age(age)
                .birthday(date)
                .build();
        MemberEntity savedMember = memberService.createMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.OK);
    }

    /**
     * Member 수정
     *
     * @return
     * @throws ParseException
     */
    @PutMapping("update")
    public ResponseEntity<MemberEntity> updateMember() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("1999-08-02");
        MemberEntity member = MemberEntity.builder()
                .id(3l)
                .name("김홀란드")
                .email("holransloveda@gmail.com")
                .nickname("king scorer")
                .age(23)
                .birthday(date)
                .build();
        MemberEntity updatedMember = memberService.updateMember(member);
        if (!ObjectUtils.isEmpty(updatedMember)) {
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(member, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Member List 조회
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<MemberEntity>> getMembers() {
        List<MemberEntity> members = memberService.getMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * Id에 해당하는 Member 조회
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<MemberEntity> getMember(
            @PathVariable("id") Long id) {
        MemberEntity member = memberService.getMember(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * Id에 해당하는 Member 삭제
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteMember(
            @PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
    /**
     * QueryDSL 이용 contain으로 찾기
     * 
     * @param type 
     * @param keyword 찾을 단어
     * @return
     */
    @GetMapping("qdsl")
    public ResponseEntity<List<MemberEntity>> getMember2(String type, String keyword) {
    	List<MemberEntity> result1 = memberService.qfindByNameOrEmail(type, keyword);
    	System.out.println(result1.toString());
    	return ResponseEntity.ok(result1);
    }
    
    @Autowired
	private JPAQueryFactory jpaQueryFactory;
	private QMemberEntity qMember = QMemberEntity.memberEntity;
	
	@GetMapping("fetch")
	public void fetchTest(String keyword) {
//		List<MemberEntity> member = jpaQueryFactory.select(qMember).from(qMember).where(qMember.name.contains(keyword)).fetch();
//		System.out.println("test =========> " + member.toString());
//		return ResponseEntity.ok(member);
		
		MemberEntity result2 = jpaQueryFactory.select(qMember).from(qMember).fetchFirst();
		List<MemberEntity> result3 = jpaQueryFactory.select(qMember).from(qMember).fetch();
		long result4 = jpaQueryFactory.select(qMember).from(qMember).fetchCount();
		QueryResults<MemberEntity> result5 = jpaQueryFactory.select(qMember).from(qMember).fetchResults();
		MemberEntity result1 = jpaQueryFactory.select(qMember).from(qMember).fetchOne();
		
		System.out.println("result2 =======> " + result2);
		System.out.println("result3 =======> " + result3);
		System.out.println("result4 =======> " + result4);
		System.out.println("result5 =======> " + result5);
		System.out.println("result1 =======> " + result1);
	}
	
	@GetMapping("oper")
	public ResponseEntity<List<MemberEntity>> operTest(String keyword) {
//		List<MemberEntity> eqResult = jpaQueryFactory.selectFrom(qMember).where(qMember.name.eq("홍글동")).fetch();
//		List<MemberEntity> neResult = jpaQueryFactory.selectFrom(qMember).where(qMember.name.ne("홍글동")).fetch();
//		List<MemberEntity> betResult = jpaQueryFactory.selectFrom(qMember).where(qMember.age.between(30, 35)).fetch();
//		List<MemberEntity> inResult = jpaQueryFactory.selectFrom(qMember).where(qMember.age.in(34, 35)).fetch();
//		List<MemberEntity> likeResult = jpaQueryFactory.selectFrom(qMember).where(qMember.name.like("%덕배%")).fetch();
		List<MemberEntity> containsResult = jpaQueryFactory.selectFrom(qMember).where(qMember.name.contains("드록")).fetch();
		
		System.out.println(containsResult);
		return ResponseEntity.ok(containsResult);
	}
    
	@GetMapping("sort")
	public ResponseEntity<List<MemberEntity>> sortTest() {
		List<MemberEntity> result = jpaQueryFactory.selectFrom(qMember).orderBy(qMember.nickname.desc().nullsFirst()).fetch();
		System.out.println(result);
		return ResponseEntity.ok(result);
	}
    
	@GetMapping(value = "func", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> funcTest() {
		List<Tuple> result = jpaQueryFactory.select(qMember.age.avg().round(),
													qMember.age.sum(),
													qMember.age.max(),
													qMember.age.min(),
													qMember.age.count()
													)
											.from(qMember).fetch();
		Tuple tupleResult = result.get(0);
		
		Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("avg", tupleResult.get(0, Double.class));
	    resultMap.put("sum", tupleResult.get(1, Integer.class));
	    resultMap.put("max", tupleResult.get(2, Integer.class));
	    resultMap.put("min", tupleResult.get(3, Integer.class));
	    resultMap.put("count", tupleResult.get(4, Long.class));
	    
		return ResponseEntity.ok(resultMap);
	}
	
	@GetMapping("grouping")
	public ResponseEntity<List<Map<String, Object>>> groupingTest() {
		List<Tuple> result = jpaQueryFactory.select(qMember.name, qMember.age, qMember.email)
									.from(qMember)
									.groupBy(qMember.age, qMember.email)
									.having(qMember.age.eq(34))
									.orderBy(qMember.age.asc())
									.fetch()
									;
	    List<Map<String, Object>> resultMapList = new ArrayList();
	    
		for (Tuple tuple : result) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("name", tuple.get(qMember.name));
			resultMap.put("ageCount", tuple.get(qMember.age));
			resultMap.put("email", tuple.get(qMember.email));
			resultMapList.add(resultMap);
		}
		
		return ResponseEntity.ok(resultMapList); 
	}
    
	private QOrderEntity qOrder = QOrderEntity.orderEntity; 
	
	// order 테이블 데이터 생성
	@PostMapping("create2")
	public ResponseEntity<OrderEntity> createMember2(String name, String sp) {
		OrderEntity order = OrderEntity.builder().name(name).sp(sp).build();
		OrderEntity savedMember = memberService.createMember2(order);
		return new ResponseEntity<>(savedMember, HttpStatus.OK);
	}
	
	@GetMapping("join")
	public ResponseEntity<List<Map<String, Object>>> joinTest() {
		List<Tuple> result = jpaQueryFactory.select(qMember.name, qMember.age, qOrder.name, qOrder.sp)
													.from(qMember)
													.join(qOrder).fetchJoin()
													.on(qMember.id.eq(qOrder.id))
													.fetch();
		List<Map<String, Object>> serializedResult = new ArrayList<>();

	    for (Tuple tuple : result) {
	        Map<String, Object> tupleMap = new HashMap<>();
	        tupleMap.put("name", tuple.get(qMember.name));
	        tupleMap.put("orderName", tuple.get(qOrder.name));
	        tupleMap.put("age", tuple.get(qMember.age));
	        tupleMap.put("sp", tuple.get(qOrder.sp));

	        serializedResult.add(tupleMap);
	    }
	    
		return ResponseEntity.ok(serializedResult);
	}
    
	@GetMapping("sub")
	public ResponseEntity<List<Map<String, Object>>> subTest() {
		// 서브쿼리
		Expression<String> spAlias = JPAExpressions.select(qOrder.sp)
										            .from(qOrder)
										            .where(qOrder.name.eq("드록바"));
		
		List<Tuple> result = jpaQueryFactory.select(qMember.name, qMember.email, spAlias).from(qMember)
													.where(qMember.name.eq(JPAExpressions.select(qOrder.name)
																						.from(qOrder)
																						.where(qOrder.name.eq("드록바"))))
													.fetch();
		List<Map<String, Object>> resultLast = new ArrayList<>();
		
		for(Tuple tuple: result) {
			Map<String, Object> tupleMap = new HashMap<>();
			tupleMap.put("name", tuple.get(qMember.name));
			tupleMap.put("email", tuple.get(qMember.email));
			tupleMap.put("sp", tuple.get(spAlias));
			
			resultLast.add(tupleMap);
		}
		return ResponseEntity.ok(resultLast);
	}
	
	@GetMapping("case")
	public ResponseEntity<List<Map<String, Object>>> caseTest() {
		List<Tuple> result = jpaQueryFactory.select(new CaseBuilder().when(qMember.age.between(18, 22)).then("유망주")
																	 .when(qMember.age.between(23, 31)).then("전성기")
																	 .when(qMember.age.between(32, 40)).then("노장")
																	 .when(qMember.age.between(41, 60)).then("은퇴")
																	 .otherwise("X")
																	 .as("asStatus"),
													qMember.name,
													qMember.age).from(qMember).fetch();
		List<Map<String, Object>> resultListMap = new ArrayList<>();
		
		for(Tuple tuple : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", tuple.get(new CaseBuilder().when(qMember.age.between(18, 22)).then("유망주")
														 .when(qMember.age.between(23, 31)).then("전성기")
														 .when(qMember.age.between(32, 40)).then("노장")
														 .when(qMember.age.between(41, 60)).then("은퇴")
														 .otherwise("X")
														 .as("asStatus")));
			map.put("name", tuple.get(qMember.name));
			map.put("age", tuple.get(qMember.age));
			
			resultListMap.add(map);
		}
		
		return ResponseEntity.ok(resultListMap);
	}
    
    @GetMapping("const")
    public ResponseEntity<Tuple> constTest() {
    	Tuple tuple = jpaQueryFactory.select(qMember.nickname, Expressions.constant("캡틴")).from(qMember).fetchFirst();
    	System.out.println(tuple);
    	return ResponseEntity.ok(tuple);
	}
    
    @GetMapping("concat")
    public ResponseEntity<String> concatTest() {
    	String result = jpaQueryFactory.select(qMember.name.concat("-")
    													   .concat(qMember.nickname)
    													   .concat("-")
    													   .concat(qMember.age.stringValue()))
    									.from(qMember)
    									.where(qMember.name.eq("드록바"))
    									.fetchOne();
    	System.out.println(result);
//    	long result2 = jpaQueryFactory.update(qMember).set(qMember.age, 20).where(qMember.name.eq("드록바")).execute();
    	return ResponseEntity.ok(result);
	}
    
    @GetMapping("dynamic")
    public ResponseEntity<List<MemberEntity>> dynamic(String name, Integer age) {
//    	BooleanBuilder builder = new BooleanBuilder();
//    	if(name != null) {
//    		builder.and(qMember.name.eq(name));
//    	}
//    	if(age != null) {
//    		builder.and(qMember.age.eq(age));
//    	}
    	
    	// 동적쿼리 booleanBuilder
//    	List<MemberEntity> result = jpaQueryFactory.selectFrom(qMember).where(builder).fetch();
    	// 메서드 이용
    	List<MemberEntity> result = jpaQueryFactory.selectFrom(qMember).where(nameCheck(name), ageCheck(age)).fetch();
    	return ResponseEntity.ok(result);
    }
    
    private BooleanExpression nameCheck(String name) {
    	return name != null ? qMember.name.eq(name) : null;
    }
    
    private BooleanExpression ageCheck(Integer age) {
    	return age != null ? qMember.age.eq(age) : null;
    }
    
    
    @GetMapping("dist")
    public ResponseEntity<List<Tuple>> dist(String name, Integer age) {
    	List<Tuple> result = jpaQueryFactory.select(qMember.name, 
    													   qMember.age).distinct()
    										 .from(qMember)
    										 .where(qMember.age.eq(34))
    										 .fetch();
    	return ResponseEntity.ok(result);
    }
    
    
//    // projections 
    @GetMapping("proj")
    public ResponseEntity<List<MemberEntity> > proj() {
    	List<MemberEntity> setterResult = jpaQueryFactory.select(Projections.bean(MemberEntity.class, 
    													   qMember.name, 
    													   qMember.age))
		    										 .from(qMember)
		    										 .where(qMember.age.eq(34))
		    										 .fetch();
    	return ResponseEntity.ok(setterResult);
    	
//    	List<MemberEntity> fieldResult = jpaQueryFactory.select(Projections.fields(MemberEntity.class, 
//															   qMember.name, 
//															   qMember.age))
//														 .from(qMember)
//														 .where(qMember.age.eq(34))
//														 .fetch();
//    	return ResponseEntity.ok(fieldResult);
//    	List<MemberEntity> consturctorResult = jpaQueryFactory.select(Projections.constructor(MemberEntity.class, 
//															   qMember.name, 
//															   qMember.age))
//														 .from(qMember)
//														 .where(qMember.age.eq(34))
//														 .fetch();
//		return ResponseEntity.ok(consturctorResult);
//    	List<MemberEntity> consturctorResult = jpaQueryFactory.select(new QMemberEntity(qMember.name,qMember.age))
//														 .from(qMember)
//														 .where(qMember.age.eq(34))
//														 .fetch();
//		return ResponseEntity.ok(consturctorResult);

    }
    
    @Autowired
    EntityManager em ;
    @Transactional
    @PutMapping("updatedsl")
    public void update() {
    	long count = jpaQueryFactory.update(qMember).set(qMember.age, 28).where(qMember.name.eq("드록바")).execute();
    	em.flush();
    	 em.clear();
    }
    
    @Transactional
    @DeleteMapping("deletedsl")
    public void delete() {
    	long count  = jpaQueryFactory.delete(qMember).where(qMember.name.eq("메시")).execute();
    }
    
    
    
    
    
    
}
