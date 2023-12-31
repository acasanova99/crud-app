package com.skz.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate birthday;
}
