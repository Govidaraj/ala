package com.GroDel.App.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GroDel.App.Model.User;
import com.GroDel.App.Model.User1;
import com.GroDel.App.Repository.StaffRepository;
import com.GroDel.App.Repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	StaffRepository repository;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping
	public ResponseEntity<?> welcome()
	{
		return ResponseEntity.ok().body("Welcome...!");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User1 staff){
		
		User1 staff1 = new User1(generateUID(),staff.getName(),staff.getEmail(),staff.getMobNo(),
								encoder.encode(staff.getPassword()));
		
		User user = new User();
		user.setEmail(staff.getEmail());
		user.setMobNo(staff.getMobNo());
		user.setPassword(encoder.encode(staff.getPassword()));
//		user.setUserType(staff.getUserType().toString());

		userRepository.save(user);
		
		confirmationMail(staff.getEmail(), staff.getName());

		
		return ResponseEntity.ok().body(repository.save(staff1));
	}
	
	@GetMapping("/getByName/{name}")
	public ResponseEntity<?> getByUserName(@PathVariable String name)
	{
		Optional<User1> staff = repository.findByName(name);
		
		if(staff.isEmpty())
		{
			ResponseEntity.ok().body("No user found for given Name");
		}
		
		return ResponseEntity.ok().body(staff);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllUser()
	{
		List<User1> staff = repository.findAll();
		
		if(staff.isEmpty())
		{
			ResponseEntity.ok().body("No Data found");
		}
		
		return ResponseEntity.ok().body(staff);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User1 staff, @PathVariable String id)
	{
		Optional<User1> staffOptional = repository.findById(id);
		
		if(staffOptional.isPresent()) {
			User1 staffToUpdate = staffOptional.get();
			staffToUpdate.update(staff);
			staffToUpdate.setId(id);
			User1 updatedUser = repository.save(staffToUpdate);
			return ResponseEntity.ok().body(updatedUser);
		}
		else
		{
			return ResponseEntity.ok().body("User Not Found for given Id");
		}
	}
	
	private void confirmationMail(String email, String name) {
		
		try {
		
			String subject="User Registration";
			String content= "Dear "+name+",<br>"+ "You Have Registered Successfully.<br>";
			
			
			MimeMessage message= mailSender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(message,true);
		
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(content,true);
			mailSender.send(message);
		
		}catch(Exception e){
			System.out.println("Invalid Mail");
			e.printStackTrace();
		}
		
	}
	public static String generateUID() {
		// Generate a random alphanumeric ID with 10 digits
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
}
