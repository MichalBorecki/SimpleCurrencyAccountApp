package pl.borecki.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 2, max = 32, message = "Surname must be between 2 and 32 characters long")
    @Column(name = "surname")
    private String surname;

    @PESEL(message = "PESEL is not valid!")
    @Column(name = "pesel", unique = true)
    private String pesel;

    @OneToMany(mappedBy="account", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Subaccount> subaccountList;

}
