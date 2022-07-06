package pl.borecki.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borecki.app.entity.Subaccount;

import java.util.Optional;

public interface SubaccountRepository extends JpaRepository<Subaccount, Long> {

    Optional<Subaccount> findOneByAccountIdAndCurrencyCode(@PathVariable Long accountId, @PathVariable String currencyCode);

}