package aiss.restclient.model.GitMinerModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "web_url"
})

public class GitMinerProject {

    private String name;
    private String web_url;

    public GitMinerProject( String name, String web_url) {
        this.name = name;
        this.web_url = web_url;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWeb_url() {
        return web_url;
    }
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String toString() {
        return "Project [name=" + name + ", web_url=" + web_url + "]";
    }
}
