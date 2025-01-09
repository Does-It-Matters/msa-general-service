package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <b> HashMap을 위한 GeneralUser Entity </b>
 */
@Getter
@Setter
@AllArgsConstructor
public class GeneralUserEntity implements GeneralUserDTO {
    private String id;
    private String email;
    private int age;
}
