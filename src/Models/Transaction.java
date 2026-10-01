package Models;
import Enums.TransactionType;
import java.time.LocalDateTime;

public class Transaction {
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime dateTime;

    public Transaction(TransactionType type, Double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Type: " + type +
                " | Amount: " + amount +
                " | Description: " + description +
                " | Date: " + dateTime;
    }
}
