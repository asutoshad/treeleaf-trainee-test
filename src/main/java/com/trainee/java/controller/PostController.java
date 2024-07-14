package com.trainee.java.controller;

import com.trainee.java.model.Comment;

import com.trainee.java.model.Post;
import com.trainee.java.model.User;
import com.trainee.java.repository.UserRepository;
import com.trainee.java.service.CommentService;
import com.trainee.java.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/posts")
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/admin/posts/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create_post";
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, @RequestParam MultipartFile photo, Model model) {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                post.setUser(user);
                post.setUrl(photo.getOriginalFilename());
                postService.save(post);

                if (!photo.isEmpty()) {
                    try {
                        Files.copy(photo.getInputStream(), Path.of("src/main/resources/static/uploads/" + photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                        model.addAttribute("message", "Upload successful");
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("message", "Something went wrong during upload. Please try again.");
                    }
                } else {
                    model.addAttribute("message", "No image selected for upload.");
                }
            }
        } else {
            model.addAttribute("message", "User not authenticated.");
        }

        return "redirect:/posts";
    }

    private User getUserFromSession() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }






    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            return "redirect:/posts";
        }
        List<Comment> comments = commentService.findByPost(post);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments); // Update this line
        return "view_post";
    }


    @PostMapping("/posts/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login?error=Please log in to comment";
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        Post post = postService.findById(id);
        if (post == null) {
            return "redirect:/posts";
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        commentService.save(comment);

        return "redirect:/posts/" + id;
    }

}
