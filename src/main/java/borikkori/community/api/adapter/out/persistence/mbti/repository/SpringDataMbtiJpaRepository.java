package borikkori.community.api.adapter.out.persistence.mbti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;

public interface SpringDataMbtiJpaRepository extends JpaRepository<MbtiEntity, Long> {

}
