package com.dreams.users.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Register;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;
import com.dreams.users.service.GetUserServiceImpl;
import com.dreams.users.service.LoginServiceImpl;
import com.dreams.users.service.RegisterServiceImpl;
import com.dreams.users.utils.CustomPropertyEntity;
import com.dreams.users.utils.ServerException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value="User service", description="Interface for User services")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
		
	@Autowired private RegisterServiceImpl registerService;
	@Autowired private GetUserServiceImpl getUserService;
	@Autowired private LoginServiceImpl loginService;
	
	@GetMapping(value = {"/", "/ping"})
	@ApiOperation(value="check whether the service is up and working fine", response = String.class)
	public String ping()
	{
		return "Users service responding properly for ping : " + System.currentTimeMillis(); 
	}
	
	@ApiOperation(value = "Register new user",
			response = Response.class,
			notes = "This service is used to create new user account in our applicaion. User should provie the mandatory fields to create account")
	@PostMapping(value = "{version}/user")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "User created successfully!"),
			@ApiResponse(code = 202, message = "User creation failed!"),
			@ApiResponse(code = 203, message = "User already exist!"),
			@ApiResponse(code = 500, message = "Internal Server Error occured."),
			@ApiResponse(code = 0, message = "Something went wrong ie.unknown error")
	})
 	public Response register(HttpServletRequest request,
 			HttpServletResponse response,
 			@ApiParam(value="user param should contain entire user data") @Valid @RequestBody UsersEntity user,
 			@ApiParam(value="Api's version id") @PathVariable("version") long version,
 			@ApiParam(value="<b>Your Api Key</b>",  required = true) @RequestHeader(value = "user-key",  required = true)String userKey) throws ServerException
 	{
		Response toRet = null;
		toRet = registerService.createUser(user);
 		return toRet;
 	}
 	
	@GetMapping("{version}/user")
	public ResponseEntity<UsersEntity> getUser(@RequestParam(name = "email",required = true) String email) {
		UsersEntity resp = getUserService.getUser(email);
		return new ResponseEntity<UsersEntity>(resp, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Login",
			response = Response.class,
			notes = "This service is used to login into the application",
			produces = "application/json", consumes="application/json")
	@PostMapping("{version}/login")
 	public ResponseEntity<Response> login(HttpServletRequest request,
 			HttpServletResponse response,
 			@ApiParam(value="username param to idemtify the user", required = true) @RequestParam("username") String username,
 			@ApiParam(value="password param to authenticate the user", required = true) @RequestParam("password") String password,
 			@ApiParam(value="locale param to send response based on the response which requested") @RequestParam("locale") String locale,
 			@ApiParam(value="Api's version id") @PathVariable("version") long version,
 			@ApiParam(value="<b>Your Api Key</b>",  required = true) @RequestHeader(value = "user-key",  required = true)String userKey)
 			
 	{
		Response toRet = null;
		toRet = loginService.login(request, response, username, password);
 		return new ResponseEntity<Response>(toRet, HttpStatus.ACCEPTED);
 	}
	
	@ApiOperation(value = "Addprivilege",
				  notes = "This Api is used to add roles & privilege to the given user id",
				  produces = "application/json",
				  consumes="application/json",
				  response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "privilege added to user successfully!"),
			@ApiResponse(code = 202, message = "Add privilege failed failed!"),
			@ApiResponse(code = 500, message = "Internal Server Error occured."),
			@ApiResponse(code = 0, message = "Something went wrong ie.unknown error")
	})

	/*
	 * @PostMapping("{version}/auth/{type}") public ResponseEntity
	 * addAuth(@ApiParam(value="Api's version id") @PathVariable("version") long
	 * version,
	 * 
	 * @ApiParam(value="Type of operation ie. add") @PathVariable("type") String
	 * type,
	 * 
	 * @ApiParam(value="Request body along with privilege ids") @RequestBody
	 * UserRoles roles){
	 * 
	 * try {
	 * 
	 * for(Privileges privileges : roles.getPrivileges()) { Roles role = new
	 * Roles(); role.setPrivilegeId(privileges.getPrivilegeId());
	 * role.setPrivilegeName(privileges.getPrivilegeName());
	 * role.setUserId(roles.getUserId()); rolesRepository.save(role); }
	 * 
	 * return new ResponseEntity<UserRoles>(roles, HttpStatus.ACCEPTED); }
	 * catch(Exception e) {
	 * 
	 * }
	 * 
	 * Response toRet = new Response();
	 * toRet.setResponseCode(Constants.SOMETHING_WENT_WRONG);
	 * toRet.setResponseMessage(Constants.SOMETHING_WENT_WRONG_MSG); return new
	 * ResponseEntity<Response>(toRet, HttpStatus.CONFLICT); }
	 * 
	 * @ApiOperation(value = "getPrivileges", consumes = "application/json", notes =
	 * "This API is used to get privileges of given user id", response =
	 * UserResponse.class)
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, message = "privileges fetched successfully!"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server Error occured."),
	 * 
	 * @ApiResponse(code = 0, message = "Something went wrong ie.unknown error") })
	 * 
	 * @GetMapping("{version}/get/{userId}") public ResponseEntity
	 * getUser(@ApiParam(value="Api's version id") @PathVariable("version") String
	 * versionId,
	 * 
	 * @ApiParam(value="userId is used to fetch the privileges for that user"
	 * ) @PathVariable("userId") String userId) {
	 * 
	 * UserResponse toRet = new UserResponse();
	 * 
	 * UsersEntity user = userRepository.findByEmail(userId);
	 * toRet.setEmail(user.getEmail()); toRet.setFirstname(user.getFirstname());
	 * toRet.setLastname(user.getLastname()); toRet.setPhone(user.getPhone());
	 * toRet.setPassword(user.getPassword());
	 * toRet.setPrivileges(rolesRepository.getRolesByUserId(String.valueOf(userId)))
	 * ;
	 * 
	 * return new ResponseEntity<UserResponse>(toRet, HttpStatus.OK); }
	 */	
	@InitBinder
	public void initBinder(WebDataBinder binder,HttpServletResponse response) {
		binder.registerCustomEditor(Register.class, new CustomPropertyEntity(Register.class,response));
	}
}
