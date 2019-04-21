package org.clara.translate.app.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class ContactMessageDTO {

    @NotEmpty
    private String name;

    @Email
    private String email;

    private String message;


}
