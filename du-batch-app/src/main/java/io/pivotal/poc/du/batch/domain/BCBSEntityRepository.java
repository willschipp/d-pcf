package io.pivotal.poc.du.batch.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * simple JPA Repository for the BCBS entity
 * @author wschipp
 *
 */
@Repository
public interface BCBSEntityRepository extends JpaRepository<BCBSEntity, Long> {

}
