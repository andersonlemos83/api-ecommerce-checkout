package br.com.alc.ecommerce.checkout.infrastructure.helper.repository;

import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleOrderRepositoryHelper extends JpaRepository<SaleOrderEntity, Long> {

}