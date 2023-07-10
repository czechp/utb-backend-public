package app.web.utb.cart.domain;

import app.web.utb.cart.domain.event.CartDescriptionAssigned;
import app.web.utb.cart.infrastructure.persistence.CartTableName;
import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import app.web.utb.validator.CommonValidators;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = CartTableName.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CartImpl extends AggregateRoot implements Cart {
    @NotBlank
    @Length(min = CartConstraint.UMUP_NUMBER_MIN_LENGTH)
    private String umupNumber;
    private String description;

    CartImpl(String umupNumber, String description) {
        this.umupNumber = umupNumber;
        this.description = description;
        validate();
    }

    @Override
    public CartSnapshot getSnapshot() {
        return new CartSnapshot(super.getId());
    }

    private void validate() {
        CommonValidators.validateMinLength(this.umupNumber, CartConstraint.UMUP_NUMBER_MIN_LENGTH, "Nr. umup");
    }

    @Override
    public void assignDescription(String description) {
        this.description = description;
        super.addDomainEvent(new CartDescriptionAssigned(super.getId(), description));
    }
}
