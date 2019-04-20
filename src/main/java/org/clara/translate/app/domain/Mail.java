package org.clara.translate.app.domain;

import lombok.*;
import org.clara.translate.app.constants.MailTypes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private MailTypes type;

    private String mailFrom;

    private String message;

    @OneToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
