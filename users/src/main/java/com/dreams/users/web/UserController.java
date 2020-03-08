
package com.dreams.users.web;

import javax.naming.AuthenticationException;
import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.dreams.users.constants.Constants;
import com.dreams.users.entities.UserRoles;
import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Register;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;
import com.dreams.users.service.GetUserServiceImpl;
import com.dreams.users.service.LoginServiceImpl;
import com.dreams.users.service.RegisterServiceImpl;
import com.dreams.users.service.ValidateServiceImpl;
import com.dreams.users.utils.CustomPropertyEntity;
import com.dreams.users.utils.ServerException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController

@Api(value = "User service", description = "Interface for User services")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RegisterServiceImpl registerService;

	@Autowired
	private GetUserServiceImpl getUserService;

	@Autowired
	private LoginServiceImpl loginService;

	@Autowired
	private ValidateServiceImpl validateService;

	@Autowired
	Environment env;

	@GetMapping(value = { "/", "/ping" })

	@ApiOperation(value = "check whether the service is up and working fine", response = String.class)
	public String ping() {
		return "Users service responding properly for ping : " + System.currentTimeMillis() + env.getProperty("port");
	}

	@ApiOperation(value = "Register new user", response = Response.class, notes = "This service is used to create new user account in our applicaion. User should provie the mandatory fields to create account")

	@PostMapping(value = "{version}/user")

	@ApiResponses(value = {

			@ApiResponse(code = 201, message = "User created successfully!"),

			@ApiResponse(code = 202, message = "User creation failed!"),

			@ApiResponse(code = 203, message = "User already exist!"),

			@ApiResponse(code = 500, message = "Internal Server Error occured."),

			@ApiResponse(code = 0, message = "Something went wrong ie.unknown error") })
	public Response register(HttpServletRequest request, HttpServletResponse response,

			@ApiParam(value = "user param should contain entire user data") @Valid @RequestBody UsersEntity user,

			@ApiParam(value = "Api's version id") @PathVariable("version") long version,

			@ApiParam(value = "<b>Your Api Key</b>", required = true) @RequestHeader(value = "user-key", required = true) String userKey)
			throws ServerException {
		Response toRet = null;
		toRet = registerService.createUser(user);
		return toRet;
	}

	@GetMapping("{version}/user")
	public ResponseEntity<UsersEntity> getUser(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsersEntity resp = getUserService.getUser(request);
		return new ResponseEntity<UsersEntity>(resp, HttpStatus.OK);
	}

	@GetMapping("{version}/user/{email}")
	public ResponseEntity<UsersEntity> getUserByEmail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "email") String email) throws AuthenticationException {
		UsersEntity resp = getUserService.getUserByEmail(request, email);
		return new ResponseEntity<UsersEntity>(resp, HttpStatus.OK);
	}

	@ApiOperation(value = "Login", response = Response.class, notes = "This service is used to login into the application", produces = "application/json", consumes = "application/json")

	@PostMapping("{version}/login")
	public ResponseEntity<Response> login(HttpServletRequest request, HttpServletResponse response,

			@ApiParam(value = "username param to idemtify the user", required = true) @RequestParam("username") String username,

			@ApiParam(value = "password param to authenticate the user", required = true) @RequestParam("password") String password,

			@ApiParam(value = "locale param to send response based on the response which requested") @RequestParam("locale") String locale,

			@ApiParam(value = "Api's version id") @PathVariable("version") long version,

			@ApiParam(value = "<b>Your Api Key</b>", required = true) @RequestHeader(value = "user-key", required = true) String userKey)

	{
		Response toRet = null;
		toRet = loginService.login(request, response, username, password);
		return new ResponseEntity<Response>(toRet, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Verify Security Question", response = Response.class, notes = "This service is used to the user by verifying security question")

	@ApiResponses(value = {

			@ApiResponse(code = 200, message = "Security question validated!"),

			@ApiResponse(code = 500, message = "Internal Server Error occured."),

			@ApiResponse(code = 0, message = "Something went wrong ie.unknown error") })

	@GetMapping("{version}/checkquestion/{id}")
	public ResponseEntity<Response> validateSequrityQuestion(
			@ApiParam(value = "userId is used to validate the password for that user.")
			@PathVariable(name = "id") String email,
			@RequestParam(name = "answer") String answer, 
			@RequestParam(name = "question") Questions question) {

		Response toRet = validateService.validateSecurityQuestion(question.getQuestion(), answer, email);

		return new ResponseEntity<Response>(toRet, HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletResponse response) {
		binder.registerCustomEditor(Register.class, new CustomPropertyEntity(Register.class, response));
	}

	public final String MALE = "M";

	public enum Questions {
		What_is_the_name_of_the_city_your_born("What is the name of the city your born?"),
		Name_of_your_crush("Name of your crush?");

		private String question;

		Questions(String question) {
			this.question = question;
		}

		public String getQuestion() {
			return this.question;
		}
	}
}
