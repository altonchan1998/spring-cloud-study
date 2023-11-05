package com.spring.cloud.study.accounts.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Customer")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;

}
