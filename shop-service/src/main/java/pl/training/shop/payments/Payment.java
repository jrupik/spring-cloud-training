package pl.training.shop.payments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.training.shop.orders.Order;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Payment {

    @GeneratedValue
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

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
