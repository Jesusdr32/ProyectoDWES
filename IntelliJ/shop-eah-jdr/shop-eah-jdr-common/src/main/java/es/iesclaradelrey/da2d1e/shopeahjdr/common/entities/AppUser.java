package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, unique = true, length = 25)
    private String userName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 9)
    private Integer phone;

    private LocalDate birthDate;

    private String password;

    private LocalDateTime createdAt;
}
