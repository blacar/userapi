package com.blacar.spike.userapi.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class User implements Serializable {

    private static final long serialVersionUID = -708000416238516042L;

    @Id
    private String id;
    private String name;
    private String email;
    private String phone;

    public User() {
    }

    public User(final String name, final String email, final String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // @checkstyle HiddenField (10 lines)
    public User(
        final String id,
        final String name,
        final String email,
        final String phone
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public User update(final User userparam) {
        return new User(
            this.id,
            userparam.name,
            userparam.email,
            userparam.phone
        );
    }

    @Override
    public boolean equals(final Object o) {
        final boolean result;
        if (this == o) {
            result = true;
        } else {
            if (o == null || getClass() != o.getClass()) {
                result = false;
            } else {
                final User user = (User) o;
                result = java.util.Objects.equals(id, user.id)
                    && java.util.Objects.equals(name, user.name)
                    && java.util.Objects.equals(email, user.email)
                    && java.util.Objects.equals(phone, user.phone);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, email, phone);
    }

    @Override
    public String toString() {
        return new StringBuffer("User{")
            .append("id=").append(id)
            .append(", name='").append(name).append('\'')
            .append(", email='").append(email).append('\'')
            .append(", phone='").append(phone).append('\'')
            .append('}').toString();
    }
}
