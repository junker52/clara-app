package org.clara.translate.app.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "combinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Combination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origin_language_id")
    private Language originLanguage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_language_id")
    private Language targetLanguage;

    @NotNull
    private BigDecimal pricePerWord;
}
