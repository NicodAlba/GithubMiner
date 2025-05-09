package aiss.restclient.model.GitMinerModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "username",
        "name",
        "avatar_url",
        "web_url"
})
public class GitMinerUser {
    private String username;
    private String name;
    private String avatar_url;
    private String web_url;

    public GitMinerUser(String id, String username, String name, String avatar_url, String web_url) {
        this.username = username;
        this.name = name;
        this.avatar_url = avatar_url;
        this.web_url = web_url;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public String getWeb_url() {
        return web_url;
    }
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String toString() {
        return "User[username: " + username + ", name: " + name + " avatar_url: " + avatar_url + ", web_url: " + web_url + "]";
    }
}
