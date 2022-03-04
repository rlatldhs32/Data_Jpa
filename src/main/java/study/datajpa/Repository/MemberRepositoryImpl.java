package study.datajpa.Repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor //private final 의 생성자
public class MemberRepositoryImpl implements MemberRepositoryCustom{ //(이름을 맞춰야함 Impl)

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
