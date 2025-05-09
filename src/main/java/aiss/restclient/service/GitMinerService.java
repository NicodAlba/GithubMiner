package aiss.restclient.service;

import aiss.restclient.model.GitMinerModels.GitMinerCommit;
import aiss.restclient.model.GitMinerModels.GitMinerProject;
import aiss.restclient.model.commit.Commit;
import aiss.restclient.model.project.Project;
import org.springframework.stereotype.Service;

@Service
public class GitMinerService {

    private ProjectService projectService;

    public GitMinerProject getProject(String workspace, String repo_slug) {
        Project gitHubRepo = projectService.getProject(workspace, repo_slug);
        String name = gitHubRepo.getName();
        String web_url = gitHubRepo.getUrl().toString();
        return new GitMinerProject(name, web_url);
    }

    public GitMinerCommit getCommit(String workspace, String repo_slug, String nCommits) {
        Commit commit = projectService.getCommit(workspace, repo_slug, nCommits);
        // TODO: Implement commit conversion to GitMinerCommit
        return null;
    }

    public GitMinerCommit getCommit(String workspace, String repo_slug) {
        return getCommit(workspace, repo_slug, "5");
    }
}
