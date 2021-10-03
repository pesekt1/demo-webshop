package swc3.demowebshop.controllers.invoice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.demowebshop.DTOs.InvoiceDto;
import swc3.demowebshop.entities.Invoice;
import swc3.demowebshop.entities.InvoiceStatus;
import swc3.demowebshop.services.invoice.InvoiceServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesControllerImpl implements InvoiceControllerInterface {

    private final InvoiceServiceInterface invoiceService; //interface
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public InvoicesControllerImpl(InvoiceServiceInterface invoiceService, ModelMapper modelMapper) {
        this.invoiceService = invoiceService;
        this.modelMapper = modelMapper;
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

    @Override
    public void checkOverdue() {
        invoiceService.checkOverdue();
    }

    @Override
    public InvoiceDto getById(int id) {
        return convertToDto(invoiceService.getById(id));
    }

    @Override
    public List<InvoiceDto> getByOrderId(int id) {
        return invoiceService.getByOrderId(id)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getByStatus(InvoiceStatus status) {
        return invoiceService.getByStatus(status)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        invoiceService.delete(id);
    }
}
