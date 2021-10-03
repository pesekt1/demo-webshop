# demo web shop server

## Invoice - service and controller layer

- Change all the Date types in Invoice class to LocalDate.
- Change all BigDecimal types to double.

There might be a problem with hibernate schama_validation, to solve it add columnDefinition = "DECIMAL(9,2)" to @Column:
 ```java
    @Column(name = "invoice_total",columnDefinition = "DECIMAL(9,2)", nullable = false, precision = 2)
    private double invoiceTotal;
 ```

### Service layer:
- Interface:
```java
public interface InvoiceService {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByOrderId(int id);
    List<Invoice> getByStatus(InvoiceStatus status);
    void checkOverdue();
    void create(Invoice invoice);
    void delete(int id);
}
```
- Implementation:
```java
...
...
    @Override
    public void checkOverdue() {
        List<Invoice> updatedInvoices = new ArrayList<>();

        invoiceRepository.findByStatus(InvoiceStatus.OPEN)
            .forEach(invoice -> {
                if (invoice.getDueDate().isBefore(invoice.getInvoiceDate())){
                    invoice.setStatus(InvoiceStatus.OVERDUE);
                    updatedInvoices.add(invoice);
                }
            });

        invoiceRepository.saveAll(updatedInvoices); //better performance then to save(invoice) in a loop
    }
...
...
```

### Controller layer:
- interface:
```java
public interface InvoiceControllerInterface {
    @GetMapping
    List<InvoiceDto> getAll();

    @GetMapping("/check-overdue")
    void checkOverdue();

    @GetMapping("/{id}")
    InvoiceDto getById(@PathVariable int id);

    @GetMapping("/order/{id}")
    List<InvoiceDto> getByOrderId(@PathVariable int id);

    @GetMapping("/status")
    List<InvoiceDto> getByStatus(@RequestParam(required = true) InvoiceStatus status);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
```
- Implementation:
```java
@RestController
@RequestMapping("/api/invoices")
public class InvoicesControllerImpl implements InvoiceControllerInterface {

    private final InvoiceService invoiceService; //interface
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public InvoicesControllerImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        this.modelMapper = new ModelMapper();
    }

    //helper method to convert entity -> DTO
    private InvoiceDto convertToDto(Invoice invoice) {
        return modelMapper.map(invoice, InvoiceDto.class);
    }
    
    @Override
    public List<InvoiceDto> getAll() {
        return invoiceService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
...
...
```

```java

```

```java

```

## order - service and controller layer
In Order class change Date types to LocalDate.

### service layer

we need some extra logic in some entities:
- Order class:
```java
    //Hibernate will ignore this field
    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0;
        for (OrderItem oi : getOrderItems()) {
            sum += oi.getTotalPrice();
        }
        return sum;
    }
```

- OrderItem class:

Change unitPrice type to double
```java
    @Transient
    public double getTotalPrice() {
        return getUnitPrice() * getQuantity();
    }
```

- Service interface:
```java
public interface OrderServiceInterface {
    Order getById(int id);
    List<Order> getAll();
    void create(Order order);
    void delete(int id);
}
```
- Service implementation:
```java
@Service
public class OrderServiceImpl implements OrderServiceInterface {

    private static final byte NEW_ORDER_STATUS = (byte)4;
    private static final int INVOICE_DUE_PERIOD = 30;

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderItemNoteRepository orderItemNoteRepository;
    InvoiceService invoiceService;

    private String errorMessage(long id){
        return "Not found order with id = " + id;
    }

    @Autowired
    public OrderServiceImpl(
            OrderRepository ordersRepository,
            OrderItemRepository orderItemRepository,
            OrderItemNoteRepository orderItemNoteRepository,
            InvoiceService invoiceService
    ) {
        this.orderRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemNoteRepository = orderItemNoteRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    public Order getById(int id) {
        return orderRepository.findById(id) //returns Optional class
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orderRepository.findAll());
    }

    @Override
    public void create(Order order) {
        order.setOrderDate(LocalDate.now()); //set the date
        order.setStatus(NEW_ORDER_STATUS); //set the default status
        
        var savedOrder = orderRepository.save(order);

        var orderItems = savedOrder.getOrderItems();
        for (OrderItem orderItem:orderItems) {
            orderItem.setOrderId(savedOrder.getOrderId());
            var orderItemNotes = orderItem.getOrderItemNotes();
            var savedOrderItem = orderItemRepository.save(orderItem);

            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderId(savedOrderItem.getOrderId());
                orderItemNote.setProductId(savedOrderItem.getProductId());
            }
            orderItemNoteRepository.saveAll(orderItemNotes);
        }
        createInvoice(savedOrder);
    }

    @Override
    public void delete(int id) {
        if (!orderRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        orderRepository.deleteById(id);
    }

    private void createInvoice(Order savedOrder) {
        var invoice = new Invoice();
        invoice.setNumber(ObjectId.get().toString()); //using ObjectId data type - generating new ObjectId
        invoice.setInvoiceTotal(savedOrder.getTotalOrderPrice());
        invoice.setPaymentTotal(0);
        invoice.setInvoiceDate(savedOrder.getOrderDate());
        invoice.setDueDate(savedOrder.getOrderDate().plusDays(INVOICE_DUE_PERIOD));
        invoice.setOrderId(savedOrder.getOrderId());
        invoice.setStatus(InvoiceStatus.OPEN);

        invoiceService.create(invoice);
    }
}
```
We are using ObjectId - for that we need dependency in pom.xml:
```xml
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>bson</artifactId>
        </dependency>
```


### Controller layer
- Interface:
```java
public interface OrderControllerInterface {
    @GetMapping
    List<OrderDto> getAll();

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody OrderDto orderDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
```

- Implementation:
```java
@RestController
@RequestMapping("/api")
public class OrderController implements OrderControllerInterface{
    OrderServiceInterface orderService;
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public OrderController(OrderServiceInterface orderService) {
        this.orderService = orderService;
        this.modelMapper = new ModelMapper();
    }

    //helper method to convert entity -> DTO
    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    //helper method to convert DTO -> entity
    private Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    @Override
    public List<OrderDto> getAll() {
        return orderService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getById(int id) {
        return convertToDto(orderService.getById(id));
    }

    @Override
    public void create(OrderDto orderDto) {
        orderService.create(convertToEntity(orderDto));
    }

    @Override
    public void delete(int id) {
        orderService.delete(id);
    }
}
```

To allow the delete order method we need to set up the constrants between the tables:
Foreign keys must have CASCADE on delete, this relates to following tables:
- order_items
- order_items_notes
- invoices
- payments


Test locally and deploy to Heroku