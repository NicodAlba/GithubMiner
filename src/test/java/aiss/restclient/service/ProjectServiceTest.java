package aiss.restclient.service;

import aiss.restclient.model.commit.Commit;
import aiss.restclient.model.comment.Comment;
import aiss.restclient.model.issue.Issue;
import aiss.restclient.model.project.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;


    @Test
    @DisplayName("Get Repository")
    void getRepository() {
        Project repository = projectService.getProject("linebender", "vello");
        assertNotNull(repository);
        System.out.println("ArchiveUrl: "+ repository.getArchiveUrl());
        System.out.println("ArchiveUrl: " + repository.getArchiveUrl());
        System.out.println("GitUrl: " + repository.getGitUrl());
        System.out.println("SshUrl: " + repository.getSshUrl());
        System.out.println("CloneUrl: " + repository.getCloneUrl());
        System.out.println("SvnUrl: " + repository.getSvnUrl());
        System.out.println("Homepage: " + repository.getHomepage());
        System.out.println("Size: " + repository.getSize());
        System.out.println("StargazersCount: " + repository.getStargazersCount());
        System.out.println("WatchersCount: " + repository.getWatchersCount());
        System.out.println("Language: " + repository.getLanguage());
        System.out.println("HasIssues: " + repository.getHasIssues());
        System.out.println("HasProjects: " + repository.getHasProjects());
        System.out.println("HasDownloads: " + repository.getHasDownloads());
        System.out.println("HasWiki: " + repository.getHasWiki());
        System.out.println("HasPages: " + repository.getHasPages());
        System.out.println("HasDiscussions: " + repository.getHasDiscussions());
        System.out.println("ForksCount: " + repository.getForksCount());
        System.out.println("MirrorUrl: " + repository.getMirrorUrl());
        System.out.println("Archived: " + repository.getArchived());
        System.out.println("Disabled: " + repository.getDisabled());
        System.out.println("OpenIssuesCount: " + repository.getOpenIssuesCount());
        System.out.println("License: " + repository.getLicense());
        System.out.println("AllowForking: " + repository.getAllowForking());
        System.out.println("IsTemplate: " + repository.getIsTemplate());
        System.out.println("WebCommitSignoffRequired: " + repository.getWebCommitSignoffRequired());
        System.out.println("Topics: " + repository.getTopics());
        System.out.println("Visibility: " + repository.getVisibility());
        System.out.println("Forks: " + repository.getForks());
        System.out.println("OpenIssues: " + repository.getOpenIssues());
        System.out.println("Watchers: " + repository.getWatchers());
        System.out.println("DefaultBranch: " + repository.getDefaultBranch());
        System.out.println("TempCloneToken: " + repository.getTempCloneToken());
        System.out.println("CustomProperties: " + repository.getCustomProperties());
        System.out.println("Organization: " + repository.getOrganization());
        System.out.println("NetworkCount: " + repository.getNetworkCount());
        System.out.println("SubscribersCount: " + repository.getSubscribersCount());
    }
    @Test
    @DisplayName("Get Commit")
    void getCommit() {
        Commit commit =  projectService.getCommit("linebender","vello","862bbc47754065a050bf90e0cbce15274dc6fb48");
        assertNotNull(commit);
        System.out.println("Sha: " + commit.getSha());
        System.out.println("Node ID: " + commit.getNodeId());
        System.out.println("Commit: " + commit.getCommit());
        System.out.println("URL: " + commit.getUrl());
        System.out.println("HTML URL: " + commit.getHtmlUrl());
        System.out.println("Comments URL: " + commit.getCommentsUrl());
        System.out.println("Author: " + commit.getAuthor());
        System.out.println("Committer: " + commit.getCommitter());
        System.out.println("Parents: " + commit.getParents());
    }

    @Test
    @DisplayName("Get Issues")
    void getIssues() {
        Issue[] issues = projectService.getIssueList("linebender", "vello");
        assertNotNull(issues);
        for (Issue issue : issues) {
            System.out.println("\nIssue #" + issue.getNumber());
            System.out.println("Title: " + issue.getTitle());
            System.out.println("State: " + issue.getState());
            System.out.println("URL: " + issue.getUrl());
            System.out.println("Repository URL: " + issue.getRepositoryUrl());
            System.out.println("Labels URL: " + issue.getLabelsUrl());
            System.out.println("Comments URL: " + issue.getCommentsUrl());
            System.out.println("Events URL: " + issue.getEventsUrl());
            System.out.println("HTML URL: " + issue.getHtmlUrl());
            System.out.println("ID: " + issue.getId());
            System.out.println("Node ID: " + issue.getNodeId());
            System.out.println("User: " + issue.getUser());
            System.out.println("Labels: " + issue.getLabels());
            System.out.println("Locked: " + issue.getLocked());
            System.out.println("Assignee: " + issue.getAssignee());
            System.out.println("Assignees: " + issue.getAssignees());
            System.out.println("Milestone: " + issue.getMilestone());
            System.out.println("Comments: " + issue.getComments());
            System.out.println("Created At: " + issue.getCreatedAt());
            System.out.println("Updated At: " + issue.getUpdatedAt());
            System.out.println("Closed At: " + issue.getClosedAt());
            System.out.println("Author Association: " + issue.getAuthorAssociation());
            System.out.println("Type: " + issue.getType());
            System.out.println("Body: " + issue.getBody());
            System.out.println("Closed By: " + issue.getClosedBy());
            System.out.println("Reactions: " + issue.getReactions());
            System.out.println("Timeline URL: " + issue.getTimelineUrl());
            System.out.println("State Reason: " + issue.getStateReason());
        }
    }

    @Test
    @DisplayName("Get Comments")
    void getComments() {
        Comment[] comments = projectService.getCommentList("linebender", "vello");
        assertNotNull(comments);
        for (Comment comment : comments) {
            System.out.println("\nComment #" + comment.getId());
            System.out.println("URL: " + comment.getUrl());
            System.out.println("HTML URL: " + comment.getHtmlUrl());
            System.out.println("Issue URL: " + comment.getIssueUrl());
            System.out.println("Node ID: " + comment.getNodeId());
            System.out.println("User: " + comment.getUser());
            System.out.println("Created At: " + comment.getCreatedAt());
            System.out.println("Updated At: " + comment.getUpdatedAt());
            System.out.println("Author Association: " + comment.getAuthorAssociation());
            System.out.println("Body: " + comment.getBody());
            System.out.println("Reactions: " + comment.getReactions());
            System.out.println("Performed Via GitHub App: " + comment.getPerformedViaGithubApp());
        }
    }
}