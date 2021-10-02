package swc3.demowebshop.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shippers", schema = "swc3_webshop")
public class Shipper {
    private short shipperId;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id", nullable = false)
    public short getShipperId() {
        return shipperId;
    }

    public void setShipperId(short shipperId) {
        this.shipperId = shipperId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipper shipper = (Shipper) o;
        return shipperId == shipper.shipperId && Objects.equals(name, shipper.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipperId, name);
    }
}
