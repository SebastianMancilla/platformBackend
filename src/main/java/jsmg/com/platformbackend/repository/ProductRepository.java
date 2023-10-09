package jsmg.com.platformbackend.repository;

import jsmg.com.platformbackend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String aString);
    @Transactional
    void deleteByCode(String aString);
}
