package com.fabicoding94.MinimalGreenApp.controllers;

import com.fabicoding94.MinimalGreenApp.DTO.RegisterDTO;
import com.fabicoding94.MinimalGreenApp.DTO.UserUpdateDTO;
import com.fabicoding94.MinimalGreenApp.entities.post.Image;
import com.fabicoding94.MinimalGreenApp.entities.post.Post;
import com.fabicoding94.MinimalGreenApp.entities.user.Role;
import com.fabicoding94.MinimalGreenApp.entities.user.RoleType;
import com.fabicoding94.MinimalGreenApp.entities.user.User;
import com.fabicoding94.MinimalGreenApp.repositories.RoleRepository;
import com.fabicoding94.MinimalGreenApp.services.ImageService;
import com.fabicoding94.MinimalGreenApp.services.PostService;
import com.fabicoding94.MinimalGreenApp.services.RoleService;
import com.fabicoding94.MinimalGreenApp.services.UserService;
import com.fabicoding94.MinimalGreenApp.utils.UserRequest;
import com.fabicoding94.MinimalGreenApp.utils.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200/") //serve per collegare il fe al be
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;


    //---------------------------- Get --------------------------------

    //GET ALL
    @GetMapping("")
    //@PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin
    public List<User> getAllUsers() {

        return userService.getAll();

    }

    //GET ALL PAGEABLE
    @GetMapping("/paginated")
    //@PreAuthorize("hasRole('ADMIN', 'USER')")
    public ResponseEntity<Page<User>> getAllUsersPaginated( Pageable p ) {

        Page<User> findAll = userService.getAllAndPaginate( p );

        if( findAll.hasContent() ) {
            return new ResponseEntity<>( findAll, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND );
        }

    }

    // GET BY ID
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getById( @PathVariable Long id ) throws Exception {

        return new ResponseEntity<>(
                userService.getById( id ),
                HttpStatus.OK
        );
    }

    //GET BY USERNAME (UNIQUE)
    @GetMapping("/username/{username}")
    //@PreAuthorize("hasRole('ADMIN','USER')")
    public ResponseEntity<User> getByUsername( @PathVariable String username ) {

        return new ResponseEntity<>(
                userService.findByUsername( username ).isPresent() ?
                        userService.findByUsername( username ).get() : null,
                HttpStatus.OK
        );

    }

    // GET BY Username CONTAINS
//    @GetMapping("/username-contains/{username}")
//    //@PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<User>> getByUsernameContains( @PathVariable String username ) {
//
//        return new ResponseEntity<>(
//                UserService.findByUsernameContains( username ),
//                HttpStatus.OK
//        );
//
//    }

    @GetMapping("{id}/followers")
    public List<User> getFollowers(@PathVariable("id") Long id) {
        return userService.getAllFollowers(id);
    }

    @GetMapping("{id}/followed")
    public List<User> getFollowed(@PathVariable("id") Long id) {
        return userService.getAllFollowed(id);
    }

//---------------------------- Post --------------------------------



//    @PostMapping("/new-user")
//   // @PreAuthorize("hasRole('ADMIN','USER')")
//    public User saveUser(
//            @RequestParam(value="nome",required=true) String nome,
//            @RequestParam(value="username",required=true) String username,
//            @RequestParam(value="email",required=false) String email,
//            @RequestParam(value="password",required=true) String password
//    )
//    {
//        User user = User.builder()
//
//                .username(username)
//                .email(email)
//                .password((password))
//                .active(true)
//                .build();
//
//        return userService.save(user);
//    }

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterDTO dto) throws Exception {


        User user = User.builder()
                .name(dto.getName())
                .lastname(dto.getLastname())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .active(true)
                .isOnline(false)
                .isPrivate(false)
                .registration(LocalDateTime.now())
                .roles(new HashSet<Role>())
                .followed(new HashSet<User>())
                .build();

        user.addRole(roleService.getByRole(RoleType.ROLE_USER));

        return userService.save(user);
    }


//    //UPDATE
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<UserResponse> update(@RequestBody UserRequest user, @PathVariable("id") Long id ) {
//        try {
//            return new ResponseEntity<>( userService.updateResponse( user, id ),
//                    HttpStatus.OK);
//        } catch( Exception e ) {
//            log.error( e.getMessage() );
//        }
//        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
//    }


// ---------------------------- Put --------------------------------
    // ADD ROLE ADMIN
    @PutMapping("/{id}/add-role/{roleType}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void addRole(
            @PathVariable("id") Long id,
            @PathVariable("roleType") String roleType
    ) throws Exception {

        User u = userService.getById( id );

       // if( roleType.equals( "ADMIN" ) ) {

            u.addRole( roleService.getByRole(RoleType.valueOf(roleType)) );

            userService.update( u );

        //}

    }

    // REMOVE ROLE ADMIN
    @PutMapping("/{id}/remove-role/{roleType}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeRole(
            @PathVariable("id") Long id,
            @PathVariable("roleType") String roleType
    ) throws Exception {

        User u = userService.getById( id );

        // if( roleType.equals( "ADMIN" ) ) {

        u.removeRole( roleService.getByRole( RoleType.ROLE_ADMIN ) );

        userService.update( u );

        //}

        }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDTO update) {

        User user = userService.getById(id);

        if (update.getName() != null) user.setName(update.getName());
        if (update.getLastname() != null) user.setLastname(update.getLastname());
        if (update.getUsername() != null) user.setUsername(update.getUsername());
        if (update.getEmail() != null) user.setEmail(update.getEmail());
        if (update.getPassword() != null) user.setPassword(update.getPassword());
        if (update.getDescription() != null) user.setDescription(update.getDescription());
        if (update.getIsPrivate() != null) user.setIsPrivate(update.getIsPrivate());

        userService.save(user);
        return user;
    }

    @PutMapping(value="/{id}/updateProfilePic", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public User updateProfilePic(@PathVariable("id") Long id, @RequestPart("imageFile") MultipartFile file) {

        Image postImage = uploadImage(file);
        imageService.save(postImage);

        User user = userService.getById(id);
        user.setImage(postImage);
        userService.save(user);

        return user;
    }

    public Image uploadImage(MultipartFile file) {

        try {
            Image img = Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imgBytes(file.getBytes())
                    .build();
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @PutMapping("/{id}/follow") // TODO non dovresti poter seguir te stesso
    public User follow(@PathVariable("id") Long followerId, @RequestBody Long followedId) {

        User follower = userService.getById(followerId);
        User followed = userService.getById(followedId);

        if (follower.getFollowed().contains(followed))
            follower.unfollow(followed);
        else
            follower.follow(followed);

        userService.save(follower);

        return follower;

    }

// -------------------------- Delete -------------------------------




    // DELETE
//        @DeleteMapping("/{id}")
//        //@PreAuthorize("hasRole('ADMIN')")
//        public void deleteById (@PathVariable Long id ){
//
//            try {
//
//                userService.delete(id);
//
//            } catch (Exception e) {
//
//                log.error(e.getMessage());
//
//            }
//
//        }
// DELETE
        @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        List<Post> userPosts = postService.getByAuthorId(id);
        postService.deletePostList(userPosts);
        userService.delete(id);
//		return "User deleted successfully";
    }
    }
