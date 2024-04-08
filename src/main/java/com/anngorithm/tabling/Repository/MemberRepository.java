package com.anngorithm.tabling.Repository;

import com.anngorithm.tabling.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

}
