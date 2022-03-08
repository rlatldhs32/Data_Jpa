package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {
    //새로운 엔티티인지 확인하기위해서
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id){
        this.id=id;
    }

    @Override
    public boolean isNew() {
        return createdDate==null;
    }
}
