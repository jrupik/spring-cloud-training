package pl.training.shopservice;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.FastMoney;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "products")
@Getter
@Setter
@ToString
@Entity
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private FastMoney price;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Product)) {
            return false;
        }
        Product otherProduct = (Product) otherObject;
        return id != null && Objects.equals(id, otherProduct.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
