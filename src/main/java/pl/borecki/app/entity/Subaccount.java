package pl.borecki.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subaccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Size(min = 3, max = 3, message = "Currency code not valid!")
    @Column(name = "currency_code")
    String currencyCode;

    @PositiveOrZero(message = "Balance cannot be negative!")
    @Column(name = "balance")
    BigDecimal balance;

    public Subaccount(@NotNull Account account, @NotNull String currencyCode, @NotNull BigDecimal balance) {
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.account = account;
    }

}
