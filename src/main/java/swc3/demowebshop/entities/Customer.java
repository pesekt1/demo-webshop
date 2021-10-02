package swc3.demowebshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "customers", schema = "swc3_webshop")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "customer_id", nullable = false)
    private int customerId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Basic
    @Column(name = "birth_date", nullable = true)
    private Date birthDate;

    @Basic
    @Column(name = "phone", nullable = true, length = 50)
    private String phone;

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Basic
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Basic
    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @Basic
    @Column(name = "points", nullable = false)
    private int points;

    @OneToMany(mappedBy = "customersByCustomerId")
    private Collection<Order> ordersByCustomerId;

    @OneToMany(mappedBy = "customersByCustomerId")
    private Collection<Payment> paymentsByCustomerId;

    @OneToMany(mappedBy = "customersByCustomerId")
    private Collection<ProductRating> productRatingsByCustomerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customers = (Customer) o;
        return customerId == customers.customerId && points == customers.points && Objects.equals(firstName, customers.firstName) && Objects.equals(lastName, customers.lastName) && Objects.equals(birthDate, customers.birthDate) && Objects.equals(phone, customers.phone) && Objects.equals(address, customers.address) && Objects.equals(city, customers.city) && Objects.equals(state, customers.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, birthDate, phone, address, city, state, points);
    }

}
