
package aiss.restclient.model.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
public class CustomProperties {


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomProperties.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
