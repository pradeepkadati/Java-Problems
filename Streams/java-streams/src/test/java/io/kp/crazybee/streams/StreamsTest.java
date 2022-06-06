package io.kp.crazybee.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.kp.crazybee.streams.model.Address;
import io.kp.crazybee.streams.model.MobileNumber;
import io.kp.crazybee.streams.model.Student;
import io.kp.crazybee.streams.model.TempStudent;

public class StreamsTest {
	private List<Student> students;

	@BeforeEach
	public void setup() {
		Student student1 = new Student("Jayesh", 20, new Address("1234"),
				Arrays.asList(new MobileNumber("1233"), new MobileNumber("1234")));

		Student student2 = new Student("Khyati", 20, new Address("1235"),
				Arrays.asList(new MobileNumber("1111"), new MobileNumber("3333"), new MobileNumber("1233")));

		Student student3 = new Student("Jason", 20, new Address("1236"),
				Arrays.asList(new MobileNumber("3333"), new MobileNumber("4444")));

		students = Arrays.asList(student1, student2, student3);
	}

	// Get student with exact match name "jayesh"
	@Test
	public void test_exact_match() {

		Optional<Student> student = students.stream().filter(stu -> stu.getName().equals("Jayesh")).findFirst();

		assertEquals("Jayesh", student.get().getName());
	}

	// Get student with exact match name "jayesh1"
	@Test
	public void test_exact_match_not_found() {

		Optional<Student> student = students.stream().filter(stu -> stu.getName().equals("Jayesh1")).findFirst();

		assertFalse(student.isPresent());
	}

	// Get student with exact matching address "1235"
	@Test
	public void test_exact_matching_address_1235() {

		Optional<Address> oaddress = students.stream().map(stu -> stu.getAddress())
				.filter(address -> address.getZipcode().equals("1235")).findFirst();

		assertEquals("1235", oaddress.get().getZipcode());
	}

	// Get student with exact matching address "3333"
	@Test
	public void test_matching_mobile_3333() {

		List<Student> matches = students.stream()
				.filter(stu -> stu.getMobileNumbers().stream().anyMatch(mobile -> mobile.getNumber().equals("3333")))
				.collect(Collectors.toList());

		assertEquals(2, matches.size());
	}

	// Get student with with mobile numbers "1233" "1234"
	@Test
	public void test_matching_mobile_1233_1234() {

		List<Student> matches = students.stream().filter(stu -> stu.getMobileNumbers().stream().anyMatch(
				mobile -> Objects.equals(mobile.getNumber(), "1233") || Objects.equals(mobile.getNumber(), "1234")))
				.collect(Collectors.toList());

		assertEquals(2, matches.size());
	}

	@Test
	public void test_tempStu_to_student() {

		TempStudent tmpStud1 = new TempStudent("Jayesh1", 201, new Address("12341"),
				Arrays.asList(new MobileNumber("12331"), new MobileNumber("12341")));

		TempStudent tmpStud2 = new TempStudent("Khyati1", 202, new Address("12351"),
				Arrays.asList(new MobileNumber("11111"), new MobileNumber("33331"), new MobileNumber("12331")));

		List<TempStudent> tmpStus = List.of(tmpStud1, tmpStud2);

		List<Student> nStudents = tmpStus.stream()
				.map(tstu -> new Student(tstu.name, tstu.age, tstu.address, tstu.mobileNumbers))
				.collect(Collectors.toList());

		assertEquals(2, nStudents.size());
	}

	// Convert List<Student> to List<String> of student name

	@Test
	public void test_student_to_names_list() {

		List<String> names = students.stream().map(stu -> stu.getName()).collect(Collectors.toList());
		assertEquals(3, names.size());
		String name = students.stream().map(Student::getName).collect(Collectors.joining(",", "[", "]"));

		System.out.println(name);
	}

	@Test
	public void test_change_case() {

		List<String> nameList = Arrays.asList("Jayesh", "Dany", "Khyati", "Hello", "Mango");
		
	    List<String> uNames = nameList.stream().map(String::toUpperCase).collect(Collectors.toList());
	    
	    assertEquals(5, uNames.size());
	    assertIterableEquals(Arrays.asList("JAYESH", "DANY", "KHYATI", "HELLO", "MANGO"), uNames);
		
	}
}
