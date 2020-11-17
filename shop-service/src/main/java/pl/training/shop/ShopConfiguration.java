package pl.training.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.training.commons.LocalMoney;
import pl.training.shop.products.Product;
import pl.training.shop.products.ProductsRepository;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@ComponentScan("pl.training.commons")
@EnableFeignClients(basePackages = "pl.training.payments")
@EnableSwagger2
@Configuration
public class ShopConfiguration {

    @Autowired
    private ProductsRepository productsRepository;

    @PostConstruct
    public void setup() {
        productsRepository.saveAndFlush(new Product("Spring in action", LocalMoney.of(200)));
        productsRepository.saveAndFlush(new Product("Angular in action", LocalMoney.of(150)));
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.training.shop"))
                .build();
    }

    /*@LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

}
