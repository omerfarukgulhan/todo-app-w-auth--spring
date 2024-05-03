package com.todo.user;

import com.todo.config.CurrentUser;
import com.todo.user.dto.UserCreate;
import com.todo.user.dto.UserDTO;
import com.todo.user.dto.UserUpdate;
import com.todo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<Page<UserDTO>> getUsers(Pageable page, @AuthenticationPrincipal CurrentUser currentUser) {
        return new ApiResponse<>("success", "Data fetched successfully", userService.getUsers(page, currentUser).map(UserDTO::new));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable long id) {
        return new ApiResponse<>("success", "Data fetched successfully", new UserDTO(userService.getUser(id)));
    }

    @PostMapping
    public ApiResponse<Void> createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        return new ApiResponse<>("success", "Activation mail has been send", null);
    }

    @PatchMapping("/{token}/activate")
    public ApiResponse<Void> activateUser(@PathVariable String token) {
        userService.activateUser(token);
        return new ApiResponse<>("success", "User activated successfully", null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ApiResponse<UserDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate) {
        return new ApiResponse<>("success", "Data updated successfully", new UserDTO(userService.updateUser(id, userUpdate)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ApiResponse<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ApiResponse<>("success", "User deleted successfully", null);
    }
}
