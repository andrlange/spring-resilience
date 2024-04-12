package cool.cfapps.studentservice.entity;

import cool.cfapps.studentservice.dto.StudentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long addressId;

    public boolean equalsWithoutAddress(StudentRequest o) {
        return this.firstName.equals(o.getFirstName()) &&
                this.lastName.equals(o.getLastName()) &&
                this.email.equals(o.getEmail());
    }
}
