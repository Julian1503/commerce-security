package com.julian.commerceauthsecurity.api.request.user;

import com.julian.commerceauthsecurity.api.request.PagedRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
public class GetUsersWithFilterRequest extends PagedRequest {
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_\\- ]*$", message = "Name can only contain letters, numbers, spaces, dashes, and underscores")
    private String username;
    @Email(message = "Invalid email format")
    private String email;
    @Size(max = 50, message = "Role cannot exceed 50 characters")
    private Collection<String> role;
    private Boolean active;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAfter;

    public GetUsersWithFilterRequest(int page, int size, String sort, String direction, String username, String email, Collection<String> role, Boolean active, LocalDate createdAfter, LocalDate createdBefore) {
        super(page, size, sort, direction);
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
        this.createdAfter = createdAfter;
        this.createdBefore = createdBefore;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdBefore;



    public Boolean getActive() {
        return active != null ? active : false;
    }
}