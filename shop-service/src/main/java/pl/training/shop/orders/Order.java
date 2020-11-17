package pl.training.shop.orders;

import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.LocalMoney;
import pl.training.shop.payments.Payment;
import pl.training.shop.products.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "orders")
@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private Long clientId;
    @ManyToMany
    @NonNull
    private List<Product> products;
    @OneToOne
    private Payment payment;

    public FastMoney getTotalValue() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(LocalMoney.zero(), FastMoney::add);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Order)) {
            return false;
        }
        Order otherOrder = (Order) otherObject;
        return id != null && Objects.equals(id, otherOrder.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
