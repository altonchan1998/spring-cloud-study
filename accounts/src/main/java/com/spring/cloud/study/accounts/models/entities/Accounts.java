package com.spring.cloud.study.accounts.models.entities;

import com.spring.cloud.study.accounts.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Accounts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Accounts extends Auditable {
    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="customer_id")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name="account_type")
    private AccountType accountType;

    @Column(name="branch_address")
    private String branchAddress;

    @Column(name = "communication_sw")
    private Boolean communicationSw;
}
