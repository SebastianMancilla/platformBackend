package jsmg.com.platformbackend.repository;

import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.SupplierDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByCode(String aString);
   Supplier findByNotificationEmail(String aString);

    @Transactional
    void deleteByCode(String aString);
}
