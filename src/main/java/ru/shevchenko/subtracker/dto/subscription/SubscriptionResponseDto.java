package ru.shevchenko.subtracker.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {

    private Long id;
    private String serviceName;
}
