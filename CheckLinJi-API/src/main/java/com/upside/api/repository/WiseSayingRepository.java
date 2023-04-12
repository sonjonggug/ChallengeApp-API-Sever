package com.upside.api.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upside.api.entity.SayingEntity;

public interface WiseSayingRepository extends JpaRepository<SayingEntity, Long> {
		 

	Optional<SayingEntity> findBysaySeq (Long saySeq);
}

