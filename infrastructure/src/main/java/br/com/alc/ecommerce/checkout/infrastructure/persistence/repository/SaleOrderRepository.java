package br.com.alc.ecommerce.checkout.infrastructure.persistence.repository;

import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrderEntity, Long> {

    Optional<SaleOrderEntity> findFirstByNumberOrderOrderByUpdatedDateDesc(String numberOrder);

    @Query(nativeQuery = true, value = "" +
            "SELECT * " +
            "FROM ECOMMERCE_CHECKOUT_OWNER.SALE_ORDER so " +
            "WHERE so.STATUS = 'IN_PROCESSING' " +
            "AND so.NUMBER_ORDER IN (?1) " +
            "ORDER BY so.UPDATE_DATE DESC ")
    Optional<SaleOrderEntity> findFirstByNumberOrderAndStatusInProcessing(String numberOrder);

}