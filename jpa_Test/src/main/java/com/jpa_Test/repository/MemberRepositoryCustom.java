package com.jpa_Test.repository;

import java.util.List;

import com.jpa_Test.entity.MemberEntity;

public interface MemberRepositoryCustom {
	List<MemberEntity> findByNameOrEmail(String type, String keyword);
}
