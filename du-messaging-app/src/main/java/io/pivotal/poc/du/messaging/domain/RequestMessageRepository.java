package io.pivotal.poc.du.messaging.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {

}
