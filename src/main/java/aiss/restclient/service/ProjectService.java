package aiss.restclient.service;

import aiss.restclient.model.commit.Commit;
import aiss.restclient.model.comment.Comment;
import aiss.restclient.model.issue.Issue;
import aiss.restclient.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    RestTemplate restTemplate;

    @Autowired
    public ProjectService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public Project getProject(String user, String project){
        String uri = "https://api.github.com/repos/"+user+"/"+project;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Project> request = new HttpEntity<>(null, headers);

        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET,
                request, Project.class);
        return response.getBody();
    }
    public Commit getCommit(String user, String project, String sha){
        String uri = "https://api.github.com/repos/"+user
                +"/"+project+"/commits/"+sha;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Commit> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit> response = restTemplate.exchange(uri, HttpMethod.GET, request, Commit.class);
        return response.getBody();
    }

    public Issue[] getIssueList(String user, String project){
        String baseUri = "https://api.github.com/repos/"+user
                +"/"+project+"/issues";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        
        String uri = baseUri + "?per_page=100&page=1";
        ResponseEntity<Issue[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> allIssues = new ArrayList<>(Arrays.asList(response.getBody()));
        
        int page = 2;
        while (response.getBody() != null && response.getBody().length == 100) {
            uri = baseUri + "?per_page=100&page=" + page;
            response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
            if (response.getBody() != null) {
                allIssues.addAll(Arrays.asList(response.getBody()));
            }
            page++;
        }
        
        return allIssues.toArray(new Issue[0]);
    }

    public Comment[] getCommentList(String user, String project){
        String baseUri = "https://api.github.com/repos/"+user
                +"/"+project+"/issues/comments";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        
        String uri = baseUri + "?per_page=100&page=1";
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
        List<Comment> allComments = new ArrayList<>(Arrays.asList(response.getBody()));
        
        int page = 2;
        while (response.getBody() != null && response.getBody().length == 100) {
            uri = baseUri + "?per_page=100&page=" + page;
            response = restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
            if (response.getBody() != null) {
                allComments.addAll(Arrays.asList(response.getBody()));
            }
            page++;
        }
        
        return allComments.toArray(new Comment[0]);
    }

}
