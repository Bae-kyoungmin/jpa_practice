package com.jpa_Test.repositoryImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpa_Test.entity.MemberEntity;
import com.jpa_Test.entity.QMemberEntity;
import com.jpa_Test.repository.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	private QMemberEntity qMember = QMemberEntity.memberEntity;

	@Override
	public List<MemberEntity> findByNameOrEmail(String type, String keyword) {
		List<MemberEntity> content = jpaQueryFactory.select(qMember)
										.from(qMember)
										.where(qMember.name.contains(keyword))
										.fetch();
		return content;
	}
}
