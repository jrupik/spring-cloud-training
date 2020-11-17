package pl.training.shop.orders;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.shop.payments.Payment;
import pl.training.shop.payments.PaymentInitializationException;
import pl.training.shop.payments.PaymentsService;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PaymentsService paymentsService;

    public void placeOrder(Order order) {
        var payment = paymentsService.pay(order.getTotalValue())
                .orElseThrow(PaymentInitializationException::new);
        order.setPayment(payment);
        ordersRepository.saveAndFlush(order);
    }

}
