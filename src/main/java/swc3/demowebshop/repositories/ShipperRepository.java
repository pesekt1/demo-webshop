package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, Short> {
}
