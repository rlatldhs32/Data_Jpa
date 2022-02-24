package study.datajpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsername(String username);
}