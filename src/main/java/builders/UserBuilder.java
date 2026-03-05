package builders;

import io.qameta.allure.internal.shadowed.jackson.databind.annotation.JsonDeserialize;
import io.qameta.allure.internal.shadowed.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = UserBuilder.Builder.class)
public class UserBuilder {

    private String email;
    private String password;
    private String name;

    private UserBuilder(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.name = builder.name;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private String email;
        private String password;
        private String name;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder build() {
            return new UserBuilder(this);
        }

    }

}
