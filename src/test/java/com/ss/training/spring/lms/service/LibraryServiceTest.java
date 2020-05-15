package com.ss.training.spring.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ss.training.spring.lms.dao.BookCopiesDAO;
import com.ss.training.spring.lms.dao.LibraryBranchDAO;
import com.ss.training.spring.lms.entity.BookCopies;
//import com.ss.training.spring.lms.entity.BookCopies;
import com.ss.training.spring.lms.entity.BookCopiesKey;
import com.ss.training.spring.lms.entity.LibraryBranch;

@SpringBootTest
public class LibraryServiceTest {
	@Autowired
	BookCopiesDAO bookCopiesDao;

	@Autowired
	LibraryBranchDAO libraryBranchDao;
	
	@Autowired
	LibraryService ser;

// -----------------Library Branch Services Tests---------------------
	@Test
	public void updateLibraryBranchNotFound() {
		List<LibraryBranch> branches = libraryBranchDao.findAll();
		LibraryBranch branch = branches.get(branches.size() -1);
		LibraryBranch notVaildBranch = new LibraryBranch();
		notVaildBranch.setBranchId(branch.getBranchId() + 2);
		assertEquals(ser.updateLibraryBranch(notVaildBranch), 0);
	}
	
	@Test
	public void updateLibraryBranchFound() {
		List<LibraryBranch> branches = libraryBranchDao.findAll();
		LibraryBranch branch = branches.get(0);
		assertEquals(ser.updateLibraryBranch(branch), 1);
	}

	@Test
	public void readAllLibraryBranchNotNull() {
		assertNotNull(ser.readAllLibraryBranch());
	}

	@Test
	public void readLibraryBranchNotBiggerThanOne() {
		Long branchId = (long) 1;
		assertEquals(libraryBranchDao.findByBranchId(branchId).size(), 1);
	}

	@Test
	public void readLibraryBranchNull() {
		Long branchId = null;
		assertTrue(libraryBranchDao.findByBranchId(branchId).isEmpty());
	}

// -----------------Book Copies Services Test---------------------

	@Test
	public void readBookCopiesByKeyThere() {
		List<BookCopies> copies = bookCopiesDao.findAll();
		BookCopies copy = copies.get(0);
		assertNotNull(bookCopiesDao.findById(copy.getId()));

	}

	@Test
	public void readBookCopiesByBranchIdNotNull() {
		List<BookCopies> copies = bookCopiesDao.findAll();
		BookCopies copy = copies.get(0);
		assertNotNull(bookCopiesDao.findByIdBranchId(copy.getId().getBranchId()));

	}
	
	@Test
	public void updateBookCopiesNotFound() {
		List<LibraryBranch> branches = libraryBranchDao.findAll();
		LibraryBranch branch = branches.get(branches.size() -1 );
		//creating book copies key which is not vaid 
		BookCopiesKey copyId = new BookCopiesKey();
		copyId.setBookId(branch.getBranchId() +2 );
		copyId.setBranchId(branch.getBranchId() + 5);
		BookCopies copy = new BookCopies();
		copy.setId(copyId);
		assertEquals(ser.updateBookCopies(copy), 0);
	}
	
	@Test
	public void updateBookCopiesIsFound() {
		List<BookCopies> copies = bookCopiesDao.findAll();
		BookCopies copy = copies.get(0);
		assertEquals(ser.updateBookCopies(copy), 1);
	}

}
