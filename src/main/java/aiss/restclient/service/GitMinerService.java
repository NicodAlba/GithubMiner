package aiss.restclient.service;

import aiss.restclient.model.GitMiner.*;
import aiss.restclient.model.comment.Comment;
import aiss.restclient.model.commit.Commit;
import aiss.restclient.model.issue.Issue;
import aiss.restclient.model.issue.User;
import aiss.restclient.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GitMinerService {
    @Autowired
    private ProjectService projectService;

    public GitMinerProject getProject(String workspace, String repo_slug) {
        Project gitHubRepository = projectService.getProject(workspace, repo_slug);
        String name = gitHubRepository.getName();
        String web_url = gitHubRepository.getUrl();
        String id = gitHubRepository.getNodeId();
        return new GitMinerProject(id, name, web_url, new ArrayList<>(), new ArrayList<>());
    }

    public List<GitMinerCommit> getCommit(String workspace, String repo_slug, Integer sinceCommits) {
        List<Commit> commits = projectService.getCommitList(workspace, repo_slug, sinceCommits);
        List<GitMinerCommit> result = new ArrayList<>();
        for(Commit commit : commits) {
            String email = commit.getCommit().getAuthor().getEmail();
            String authorName = commit.getCommit().getAuthor().getName();
            
            GitMinerCommit gCommit = new GitMinerCommit(
                    commit.getSha(),
                    "GitHub commit: " + commit.getSha(),
                    commit.getCommit().getMessage(),
                    authorName,
                    email,
                    commit.getCommit().getAuthor().getDate(),
                    commit.getHtmlUrl()
            );
            result.add(gCommit);
        }
        return result;
    }


    public List<GitMinerIssue> getIssues(String workspace, String repo_slug, Integer nIssues) {
        List<Issue> issues1 = projectService.getIssueList(workspace, repo_slug);
        List<Issue> issues;
        if(issues1.size() < nIssues) {
            issues = new ArrayList<>(issues1);
        } else {
            issues = issues1.subList(0, nIssues);
        }
        List<GitMinerIssue> result = new ArrayList<>();
        for (Issue issue : issues) {
            String closed_at = null;
            if (issue.getState().equals("closed")) {
                closed_at = issue.getClosedAt() != null ? issue.getClosedAt().toString() : null;
            }
            List<GitMinerComment> comments = getComments(workspace, repo_slug, issue.getNumber().toString());

            GitMinerUser author = new GitMinerUser(
                    issue.getUser().getNodeId(),
                    issue.getUser().getLogin(),
                    issue.getUser().getLogin(),
                    issue.getUser().getAvatarUrl(),
                    issue.getUser().getHtmlUrl()
            );

            GitMinerUser assignee = null;
            if(issue.getAssignee() != null) {
                Object assigneeObj = issue.getAssignee();
                User assigneeUser;
                if (assigneeObj instanceof User) {
                    assigneeUser = (User) assigneeObj;
                } else {
                    // Convert LinkedHashMap to User
                    assigneeUser = new com.fasterxml.jackson.databind.ObjectMapper().convertValue(assigneeObj, User.class);
                }
                assignee = new GitMinerUser(
                        assigneeUser.getNodeId(),
                        assigneeUser.getLogin(),
                        assigneeUser.getLogin(),
                        assigneeUser.getAvatarUrl(),
                        assigneeUser.getHtmlUrl()
                );
            }
            GitMinerIssue gIssue = new GitMinerIssue(
                    issue.getNumber().toString(),
                    issue.getTitle(),
                    issue.getBody(),
                    issue.getState(),
                    issue.getCreatedAt(),
                    issue.getUpdatedAt(),
                    closed_at,
                    new ArrayList<>(),
                    issue.getReactions() != null ? issue.getReactions().getTotalCount().toString() : "0",
                    comments,
                    author,
                    assignee
            );

            result.add(gIssue);
        }
        return result;
    }

    private List<GitMinerComment> getComments(String workspace, String repoSlug, String id) {
        List<GitMinerComment> result = new ArrayList<>();
        List<Comment> comments = projectService.getCommentList(workspace, repoSlug, id);
        for (Comment comment : comments) {
            aiss.restclient.model.comment.User user = comment.getUser();
            GitMinerUser author = new GitMinerUser(
                    user.getNodeId(),           
                    user.getLogin(),       
                    user.getLogin(),    
                    user.getAvatarUrl(),
                    user.getHtmlUrl()
            );

            GitMinerComment gComment = new GitMinerComment(
                    comment.getId().toString(),
                    comment.getBody(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt(),
                    author
            );

            result.add(gComment);
        }
        return result;
    }
    public GitMinerProject getGitMiner(String workspace, String repo_slug, int nCommits,
                                       int nIssues, int maxPages) {
        GitMinerProject result = getProject(workspace, repo_slug);
        result.setCommits(getCommit(workspace, repo_slug, nCommits));
        result.setIssues(getIssues(workspace, repo_slug, nIssues));
        return result;
    }
}
