package com.upside.api.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.upside.api.entity.MemberEntity;
import com.upside.api.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증       
        Optional<MemberEntity> LoginUser = memberRepository.findById(email);
                        
        if (!LoginUser.isPresent()){
        	log.info("로그인 실패 ------> " + email);
            throw new UsernameNotFoundException("User not authorized.");
        }
        
        return User.builder()        		
        		.username(LoginUser.get().getEmail())
        		.password(LoginUser.get().getPassword())
        		.roles(LoginUser.get().getAuthority())
        		.build();
    }

    }
