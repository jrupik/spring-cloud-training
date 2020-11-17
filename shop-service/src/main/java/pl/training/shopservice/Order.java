package pl.training.shopservice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "orders")
@Getter
@Setter
@ToString
@Entity
public class Order {

    @GeneratedValue
    @Id
    private Long id;
    private Long clientId;
    @ManyToMany
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
