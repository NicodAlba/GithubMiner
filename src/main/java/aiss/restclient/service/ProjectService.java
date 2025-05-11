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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public List<Commit> getCommitList(String user, String project) {
        String uri = "https://api.github.com/repos/"+user+"/"+project+"/commits";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET, 
                request, Commit[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<Commit> getCommitList(String user, String project, Integer sinceCommits) {
        // Calculate the date X days ago (default to 2 if sinceCommits is null)
        int days = (sinceCommits != null) ? sinceCommits : 2;
        LocalDateTime sinceDate = LocalDateTime.now().minusDays(days);
        String sinceDateStr = sinceDate.format(DateTimeFormatter.ISO_DATE_TIME);
        
        String uri = "https://api.github.com/repos/"+user+"/"+project+"/commits?since=" + sinceDateStr;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET, 
                request, Commit[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<Issue> getIssueList(String user, String project) {
        String uri = "https://api.github.com/repos/"+user+"/"+project+"/issues?per_page=100&page=1";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        
        ResponseEntity<Issue[]> response = restTemplate.exchange(uri, HttpMethod.GET, 
                request, Issue[].class);
        List<Issue> result = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        
        if (response.getBody() != null && response.getBody().length == 100) {
            int page = 2;
            while (true) {
                String newUri = "https://api.github.com/repos/"+user+"/"+project+"/issues?per_page=100&page=" + page;
                response = restTemplate.exchange(newUri, HttpMethod.GET, request, Issue[].class);
                if (response.getBody() == null || response.getBody().length == 0) {
                    break;
                }
                result.addAll(Arrays.asList(response.getBody()));
                page++;
            }
        }
        return result;
    }

    public List<Comment> getCommentList(String user, String project, String issueId) {
        String uri = "https://api.github.com/repos/"+user+"/"+project+"/issues/"+issueId+"/comments?per_page=100&page=1";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, 
                request, Comment[].class);
        List<Comment> result = new ArrayList<>(Arrays.asList(Objects.requireNonNull(response.getBody())));
        
        if (response.getBody() != null && response.getBody().length == 100) {
            int page = 2;
            while (true) {
                String newUri = "https://api.github.com/repos/"+user+"/"+project+"/issues/"+issueId+"/comments?per_page=100&page=" + page;
                response = restTemplate.exchange(newUri, HttpMethod.GET, request, Comment[].class);
                if (response.getBody() == null || response.getBody().length == 0) {
                    break;
                }
                result.addAll(Arrays.asList(response.getBody()));
                page++;
            }
        }
        return result;
    }
}
