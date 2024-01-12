package drampas.springframework.pmtool.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "teamMembers",joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<PmUser> members=new HashSet<>();
    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,orphanRemoval = true,mappedBy = "user")
    private List<Project> projects=new ArrayList<>();
}
