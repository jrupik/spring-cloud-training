package pl.training.shop.products;


import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.commons.FastMoneyConverter;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "products")
@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    @Convert(converter = FastMoneyConverter.class)
    @NonNull
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
