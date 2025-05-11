package aiss.restclient.GithubController;

import aiss.restclient.model.GitMiner.GitMinerProject;
import aiss.restclient.service.GitMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/github")
public class Controller {

    RestTemplate restTemplate;

    @Autowired
    public Controller(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Autowired
    GitMinerService gitMinerService;

    // Read-only operation for testing: get data from GitHub, do NOT send to GitMiner
    @GetMapping("/{owner}/{repoName}")
    public GitMinerProject getProject(@PathVariable String owner, @PathVariable String repoName,
                                      @RequestParam(defaultValue = "2") int sinceCommits,
                                      @RequestParam(defaultValue = "20") int sinceIssues,
                                      @RequestParam(defaultValue = "2") int maxPages) {
        Optional<GitMinerProject> gitMinerProject = Optional.ofNullable(
                gitMinerService.getGitMiner(owner, repoName, sinceCommits, sinceIssues, maxPages));
        return gitMinerProject.orElse(null);
    }

    // POST: get data from GitHub and send to GitMiner
    @PostMapping("/{owner}/{repoName}")
    public GitMinerProject sendToGitMiner(@PathVariable String owner, @PathVariable String repoName,
                                          @RequestParam(defaultValue = "2") int sinceCommits,
                                          @RequestParam(defaultValue = "20") int sinceIssues,
                                          @RequestParam(defaultValue = "2") int maxPages) {
        String uri = "http://localhost:8080/gitminer/projects";
        Optional<GitMinerProject> gitMinerProject = Optional.ofNullable(
                gitMinerService.getGitMiner(owner, repoName, sinceCommits, sinceIssues, maxPages));
        if (gitMinerProject.isEmpty()) return null;
        HttpEntity<GitMinerProject> request = new HttpEntity<>(gitMinerProject.get());
        ResponseEntity<GitMinerProject> response = restTemplate.exchange(uri, HttpMethod.POST,
                request, GitMinerProject.class);
        return response.getBody();
    }
}