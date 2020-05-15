package com.ss.training.spring.lms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.spring.lms.entity.BookCopies;
import com.ss.training.spring.lms.entity.BookCopiesKey;
import com.ss.training.spring.lms.entity.LibraryBranch;
import com.ss.training.spring.lms.service.LibraryService;

@RestController
@RequestMapping("/lms/public/library")

public class LibraryController {

	@Autowired
	LibraryService libraryService;

	// ----------------------Branches Mapping --------------------------------
	// read all branches
	@GetMapping("/branches")
	public ResponseEntity<List<LibraryBranch>> getAllBranches() {
		List<LibraryBranch> branch = libraryService.readAllLibraryBranch();
		// a successful request should produce a list not null with a size greater than
		// zero
		if (branch != null && branch.size() > 0) {
			return new ResponseEntity<List<LibraryBranch>>(branch, HttpStatus.OK);
		} else {
			// branch id not found, return 404 status
			return ResponseEntity.notFound().build();
		}
	}

	// read branch by Id
	@GetMapping("/branches/branch/{branchId}")
	public ResponseEntity<List<LibraryBranch>> getBranch(@PathVariable Long branchId) {
		List<LibraryBranch> branch = libraryService.readLibraryBranch(branchId);
		// a successful request should produce a list not null with a size greater than
		// zero
		if (branch != null && branch.size() > 0) {
			return new ResponseEntity<List<LibraryBranch>>(branch, HttpStatus.OK);
		} else {
			// branch id not found, return 404 status
			return ResponseEntity.notFound().build();
		}

	}

	// update all branches
	@PutMapping("/branches/branch/{branchId}")
	public ResponseEntity<String> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch,
			@PathVariable Long branchId) {
		Integer returnInt = -1; // for determining HttpStatus
		Integer success = 1;
		libraryBranch.setBranchId(branchId);
		returnInt = libraryService.updateLibraryBranch(libraryBranch);

		// indicate success or failure
		if (returnInt == success) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
		}
	}

	// ----------------------Book Copies Mapping --------------------------------

	// read all book copies by branchId
	@GetMapping("/branches/branch/{branchId}/bookCopies")
	public ResponseEntity<List<BookCopies>> getLibraryBookCopiesByBranch(@PathVariable Long branchId) {
		List<BookCopies> copies = libraryService.readBookCopiesByBranchId(branchId);
		// a successful request should produce a list not null with a size greater than
		// zero
		if (copies != null && copies.size() > 0) {
			return new ResponseEntity<List<BookCopies>>(copies, HttpStatus.OK);
		} else {
			// copy id not found, return 404 status
			return ResponseEntity.notFound().build();
		}
	}

	// read all book copies by bookCopiesKey
	@GetMapping("/branches/branch/{branchId}/bookCopies/bookcopy/{bookId}")
	public ResponseEntity<BookCopies> getAllLibraryBookCopiesByBranchAndBook(@PathVariable Long branchId,
			@PathVariable Long bookId) {
		BookCopiesKey id = new BookCopiesKey(branchId, bookId);
		Optional<BookCopies> copy = libraryService.readBookCopiesByKey(id);
		// check if result found, and send response accordingly
		if (copy.isPresent()) {
			return new ResponseEntity<BookCopies>(copy.get(), HttpStatus.OK);
		} else {
			// copy id not found, return 404 status
			return ResponseEntity.notFound().build();
		}
	}

	// update number of book copies
	@PutMapping("/branches/branch/{branchId}/bookCopies/bookcopy/{bookId}")
	public ResponseEntity<String> addBookCopiesToBranch(@RequestBody BookCopies bookCopies, @PathVariable Long branchId,
			@PathVariable Long bookId) {
		Integer returnInt = -1; // for determining HttpStatus
		Integer success = 1;
		// set BookCopiesKey id to branchId and bookId from path
		BookCopiesKey id = new BookCopiesKey(branchId, bookId);
		// set BookCopies BookCopiesKey
		bookCopies.setId(id);
		returnInt = libraryService.updateBookCopies(bookCopies);
		
		// indicate success or failure
		if (returnInt == success) {
			return new ResponseEntity<String>("Success" + returnInt, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Bad Request" + returnInt, HttpStatus.BAD_REQUEST);
		}
	}

}
