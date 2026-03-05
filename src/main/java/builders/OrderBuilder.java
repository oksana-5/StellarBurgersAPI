package builders;

import io.qameta.allure.internal.shadowed.jackson.databind.annotation.JsonDeserialize;
import io.qameta.allure.internal.shadowed.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonDeserialize(builder = OrderBuilder.Builder.class)
public class OrderBuilder {

    private List<String> ingredients;

    private OrderBuilder(Builder builder) {
        this.ingredients = builder.ingredients;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private List<String> ingredients;

        public Builder withIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public OrderBuilder build() {
            return new OrderBuilder(this);
        }
    }
}
