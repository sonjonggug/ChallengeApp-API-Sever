package com.upside.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.upside.api.entity.MemberEntity;
import com.upside.api.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class UserService implements UserDetailsService{

	@Autowired
	MemberRepository memberRepository;
    

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증       
        MemberEntity LoginUser = memberRepository.findByUserId(userid);

        if (LoginUser == null){
        	log.info("회원가입 실패 ------> " + userid);
            throw new UsernameNotFoundException("User not authorized.");
        }
        return User.builder()        		
        		.username(LoginUser.getUserId())
        		.password(LoginUser.getPassword())
        		.roles(LoginUser.getAuthority())
        		.build();
    }

    }
