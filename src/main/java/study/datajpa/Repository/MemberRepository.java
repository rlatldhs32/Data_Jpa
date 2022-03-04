package study.datajpa.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> ,MemberRepositoryCustom{
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    //길어지면 힘듬

    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username,@Param("age") int age);

    @Query("select new study.datajpa.dto.MemberDto(m.id,m.username,t.name) from Member m join m.team t")
    //객채 생성해서 반환하는느낌.
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Slice<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m") //countQuery로 필요한부분만 가져올 수 있음! 성능최적화
    Page<Member> findByAge1(int age, Pageable pageable);

    //벌크성쿼리


    //변경
    @Modifying(clearAutomatically = true)
    //왜 에러? -> 업데이트쿼리일때 넣어줌. , JPA 는 영속성을 무시하고 그냥 DB에 넣는것이기때문에, 안맞을 수가 있다.
    //따라서 bulk 연산 후에는 영속성 컨텍스트를 다 날려버려야한다. em.flush(); 와 em.clear();을 벌크연산 후에 ㄱㄱ 그럴때 그냥
    @Query("update Member m set m.age = m.age+1 where m.age >=: age")
    int bulkAgePlus(@Param("age") int age);


    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value="true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
