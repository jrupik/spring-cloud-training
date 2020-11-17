package pl.training.payments;

import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.FastMoneyConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier
@Table(name = "payments")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    private String id;
    private LocalDateTime timestamp;
    @Convert(converter = FastMoneyConverter.class)
    private FastMoney value;
    @Enumerated(EnumType.STRING)
    private PaymentStatus  status;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Payment)) {
            return false;
        }
        Payment otherPayment = (Payment) otherObject;
        return id != null && Objects.equals(id, otherPayment.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
