package pl.training.shop.orders;

import lombok.*;
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
