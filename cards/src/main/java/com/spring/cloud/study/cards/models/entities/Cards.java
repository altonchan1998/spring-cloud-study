package com.spring.cloud.study.cards.models.entities;

import com.spring.cloud.study.cards.models.enums.CardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Cards")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cards extends Auditable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native")
	private Long cardId;

	private String mobileNumber;

	private String cardNumber;

	@Enumerated(EnumType.STRING)
	private CardType cardType;

	private int totalLimit;

	private int amountUsed;

	private int availableAmount;
	
}
