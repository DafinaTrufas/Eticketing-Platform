package ro.pao.model;

import lombok.*;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CardInformation {
    private String balance;

    private String firstNameOwner;

    private String lastNameOwner;

    private String cardNumber;

    private final Predicate<String> cardCondition = (cardNumber) -> cardNumber.length() == 16 && cardNumber.matches("[0-9]+") && cardNumber.equals(this.cardNumber);

    private final BiPredicate<String, String> fullNameCondition = (firstNameOwner, lastNameOwner) -> firstNameOwner.equals(this.firstNameOwner) && lastNameOwner.equals(this.lastNameOwner);

    public CardInformation(String card) {
        if (!cardCondition.test(card)) {
            System.out.println("The card number is not valid.");
        }

        if (!fullNameCondition.test(firstNameOwner, lastNameOwner)) {
            System.out.println("The name does not match.");
        }

    }

    public CardInformation(String balance, String firstNameOwner, String lastNameOwner, String cardNumber) {
        this.balance = balance;
        this.firstNameOwner = firstNameOwner;
        this.lastNameOwner = lastNameOwner;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CardInformation{" +
                "balance='" + balance + '\'' +
                ", firstNameOwner='" + firstNameOwner + '\'' +
                ", lastNameOwner='" + lastNameOwner + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

}
