package jsmg.com.platformbackend.repository;

import jsmg.com.platformbackend.domain.AuthorizedChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorizedChannelRepository extends JpaRepository<AuthorizedChannel, Long> {
    Optional<AuthorizedChannel> findByCode(String aString);
    @Transactional
    void deleteByCode(String aString);
}
