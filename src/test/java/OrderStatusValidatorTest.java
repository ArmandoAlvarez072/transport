import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.exception.InvalidStateTransitionException;
import com.armandoalvarez.transport.service.OrderStatusValidator;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderStatusValidatorTest {

    private final OrderStatusValidator validator = new OrderStatusValidator();

    @Test
    void allowsCreatedToInTransit() {
        assertDoesNotThrow(() -> validator.validate(OrderStatus.CREATED, OrderStatus.IN_TRANSIT));
    }

    @Test
    void rejectsDeliveredToInTransit() {
        assertThrows(InvalidStateTransitionException.class,
                () -> validator.validate(OrderStatus.DELIVERED, OrderStatus.IN_TRANSIT));
    }
}