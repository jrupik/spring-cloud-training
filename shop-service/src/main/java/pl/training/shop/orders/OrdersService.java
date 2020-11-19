package pl.training.shop.orders;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.shop.commons.profiler.ExecutionTime;
import pl.training.shop.commons.retry.Retry;
import pl.training.shop.payments.Payment;
import pl.training.shop.payments.PaymentInitializationException;
import pl.training.shop.payments.PaymentsService;

import java.util.UUID;

@Transactional
@Service
@Log
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PaymentsService paymentsService;
    private final OrderFee orderFee;

    @HystrixCommand(fallbackMethod = "placeOrderFallback", ignoreExceptions = RuntimeException.class)
    public void placeOrder(Order order) {
       processOrder(order);
    }

    public void placeOrderFallback(Order order) {
        log.info("Adding order to queue");
    }

    @Scheduled(fixedRate = 10_000)
    public void processFailedOrders() {
        log.info("Processing orders from queue");
    }

    private void processOrder(Order order) {
        var paymentValue = order.getTotalValue().add(orderFee.getValue());
        var payment = paymentsService.pay(paymentValue)
                .orElseThrow(PaymentInitializationException::new);
        order.setPayment(payment);
        ordersRepository.saveAndFlush(order);
    }


    public Order getById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

}
