package cool.cfapps.addressservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest{
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
