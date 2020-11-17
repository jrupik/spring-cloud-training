package pl.training.shop.products;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public Product getById(Long id) {
        return productsRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

}
