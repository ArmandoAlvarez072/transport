import com.armandoalvarez.transport.entity.Order;
import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.mapper.OrderMapper;
import com.armandoalvarez.transport.repository.DriverRepository;
import com.armandoalvarez.transport.repository.OrderRepository;
import com.armandoalvarez.transport.dto.request.OrderCreateRequest;
import com.armandoalvarez.transport.service.OrderStatusValidator;
import com.armandoalvarez.transport.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock
    private DriverRepository driverRepository;
    @Mock private OrderMapper orderMapper;
    @Mock private OrderStatusValidator statusValidator;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository, driverRepository, orderMapper, statusValidator);
    }

    @Test
    void createSavesOrderWithCreatedStatus() {
        var request = new OrderCreateRequest();
        request.setOrigin("CDMX");
        request.setDestination("Puebla");
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.create(request);

        verify(orderRepository).save(argThat(order -> order.getStatus() == OrderStatus.CREATED));
    }
}