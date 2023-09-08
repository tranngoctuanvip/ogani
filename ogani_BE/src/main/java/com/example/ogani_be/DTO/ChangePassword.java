package com.example.ogani_be.DTO;

import lombok.Data;

@Data
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
}
