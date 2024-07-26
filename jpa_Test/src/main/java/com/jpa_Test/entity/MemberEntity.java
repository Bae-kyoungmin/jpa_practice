package com.jpa_Test.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "MEMBER")
public class MemberEntity {
	
    // 직접적인 인스턴스화를 방지하기 위한 private 생성자
    public MemberEntity() {
        // 기본 생성자
    }

    // 빌더 메서드
    public static MemberEntity builder() {
        return new MemberEntity();
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "NICKNAME")
	private String nickname;
	
	@Column(name = "AGE")
	private int age;
	
	@Column(name = "BIRTHDAY")
	private Date birthday;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	public void MemberEntityBuilder() {
        // 기본 생성자
    }

    public MemberEntity id(Long id) {
        this.id = id;
        return this;
    }

    public MemberEntity name(String name) {
        this.name = name;
        return this;
    }

    public MemberEntity email(String email) {
        this.email = email;
        return this;
    }

    public MemberEntity nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberEntity age(int age) {
        this.age = age;
        return this;
    }

    public MemberEntity birthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public MemberEntity build() {
        MemberEntity member = new MemberEntity();
        member.setId(this.id);
        member.setName(this.name);
        member.setEmail(this.email);
        member.setNickname(this.nickname);
        member.setAge(this.age);
        member.setBirthday(this.birthday);
        return member;
    }

	@Override
	public String toString() {
		return "MemberEntity [id=" + id + ", name=" + name + ", email=" + email + ", nickname=" + nickname + ", age="
				+ age + ", birthday=" + birthday + "]";
	}
	
	// projection 사용용
	//@QueryProjection
	public MemberEntity(String name, int age) {
		this.name = name;
		this.age = age;
	}

}
