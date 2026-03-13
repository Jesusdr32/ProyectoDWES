package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRol {
    @Id
    @Column(length = 6)
    private String rolId;

    @Column(length = 100, nullable = false)
    private String rolDescription;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appUsers = new HashSet<>();

    public Set<AppUser> getAppUsers() {return Collections.unmodifiableSet(appUsers);}
}
