package com.jpa_Test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jpa_Test.entity.MemberEntity;
import com.jpa_Test.entity.OrderEntity;
import com.jpa_Test.repository.MemberRepository;
import com.jpa_Test.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private final MemberRepository memberRepository = null;
	
	@Autowired
	private final OrderRepository orderRepository = null;
	
	
	// member 생성
	public MemberEntity createMember(MemberEntity memberEntity) {
		MemberEntity saveMember = memberRepository.save(memberEntity);
		return null;
	}
	
	public OrderEntity createMember2(OrderEntity orderEntity) {
		OrderEntity saveMember = orderRepository.save(orderEntity);
		return null;
	}
	
	// member 수정
	public MemberEntity updateMember(MemberEntity memberEntity) {
        MemberEntity updatedMember = null;
        try {
            MemberEntity existMember = getMember(memberEntity.getId());
            if (!ObjectUtils.isEmpty(existMember)) {
                updatedMember = memberRepository.save(memberEntity);  // JpaRepository에서 제공하는 save() 함수
            }
        } catch (Exception e) {
        	System.err.println("[Fail] e:  " + e.toString());
        } finally {
            return updatedMember;
        }
    }
	
    /**
     * Member List 조회
     * 
     * @return
     */
    public List<MemberEntity> getMembers() {
        return memberRepository.findAll();  // JpaRepository에서 제공하는 findAll() 함수
    }

    /**
     * Id에 해당하는 Member 조회
     * 
     * @param id
     * @return
     */
    public MemberEntity getMember(Long id) {
        return memberRepository.getReferenceById(id);  // JpaRepository에서 제공하는 getById() 함수
    }

    /**
     * Id에 해당하는 Member 삭제
     * 
     * @param id
     */
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);  // JpaRepository에서 제공하는 deleteById() 함수
    }

	public List<MemberEntity> qfindByNameOrEmail(String type, String keyword) {
		List<MemberEntity> list = memberRepository.findByNameOrEmail(type, keyword);
		return list;
	}

}
