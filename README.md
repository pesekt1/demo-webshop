# demo web shop server

- This is a Spring Boot Server demo project.
- Each branch is implementing some new feature.

## branches sequence:
- helloworld
- CRUD-tutorials
- swagger-DATA-REST
- exceptionHandling
- pagination
- integration_tests
- CI-CD
- httpClient  
- sorting
- profiles
- custom-queries
- spring_security
- JDBC-Communication
- MappingTheWholeDatabase
- Invoices_Orders

## Payment - service and controller

- change Date to LocalDate and BigDecimal to double in Payment class

```java
    @Column(name = "amount", columnDefinition = "DECIMAL(9,2)", nullable = false, precision = 2)
    private double amount;
```

### service layer
- interface:
```java
public interface PaymentServiceInterface {
    List<Payment> getAll();
    Payment getById(int id);
    List<Payment> getByCustomerId(int id);
    void create(Payment payment);
    void delete(int id);
}
```
- implementation:
```java
@Service
public class PaymentServiceImpl implements PaymentServiceInterface {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    private String errorMessage(long id){
        return "Not found payment with id = " + id;
    }

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository,
                              InvoiceRepository invoiceRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
...
...
```

### Controller layer
- interface:
```java
public interface PaymentControllerInterface {
    @GetMapping
    List<PaymentDto> getAll();

    @GetMapping("/{id}")
    PaymentDto getById(@PathVariable int id);

    @GetMapping("/customer/{id}")
    List<PaymentDto> getByCustomerId(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PaymentDto payment);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
```

- implementation:
```java
@RestController
@RequestMapping("/api/payments")
public class PaymentControllerImpl implements PaymentControllerInterface {
    PaymentServiceInterface paymentService;
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public PaymentControllerImpl(PaymentServiceInterface paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }


    //helper method to convert entity -> DTO
    private PaymentDto convertToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    //helper method to convert DTO -> entity
    private Payment convertToEntity(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payment.class);
    }

    @Override
    public List<PaymentDto> getAll() {
        return paymentService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
```

- new payment should automatically update the invoice.
- deleting a payment should also update the invoice.

For that we build triggers in the database on the payments table:

AFTER INSERT:
- update payment total.
- set payment date to now.
- change status to PAID if the whole invoice is covered.
```sql
create trigger payments_after_insert
    after insert
    on payments
    for each row
BEGIN
    UPDATE invoices
    SET
        payment_total = payment_total + NEW.amount,
        payment_date = now(),
        status = if(payment_total >= invoice_total, 'PAID', status)
    WHERE invoice_id = NEW.invoice_id;

END;
```

AFTER DELETE:
- update payment total.
- set payment date to now.
- change status to OVERDUE if due_date already passed.
```sql
create trigger payments_after_delete
    after delete
    on payments
    for each row
begin
    update invoices
    set payment_total = payment_total - OLD.amount,
        payment_date = now(),
        status = if(payment_total < invoice_total, if(due_date < now(), 'OVERDUE', 'OPEN'), status)
    where invoice_id = OLD.invoice_id;

end;
```


### http requests for testing the api:
```http request
###
GET http://{{host}}/api/payments HTTP/1.1
Content-Type: application/json

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 200, "Response status is not 200");
});
%}

###
GET http://{{host}}/api/payments/3 HTTP/1.1
Content-Type: application/json

### create new tutorial
POST http://{{host}}/api/payments HTTP/1.1
Content-Type: application/json

{
  "invoiceId": 32,
  "customerId": 1,
  "amount": 5,
  "paymentMethod": "1"
}

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 201, "Response status is not 201");
});
%}

...
...
```

Test locally and deploy to Heroku.