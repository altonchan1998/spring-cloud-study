package com.spring.cloud.study.loans.models.entities;

import com.spring.cloud.study.loans.models.enums.LoanType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "loans")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Loans extends Auditable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native")
	private Long loanId;

	private String mobileNumber;

	private String loanNumber;

	private LoanType loanType;

	private int totalLoan;

	private int amountPaid;

	private int outstandingAmount;
	
}
