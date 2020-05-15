package com.ss.training.spring.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.training.spring.lms.dao.BookCopiesDAO;
import com.ss.training.spring.lms.dao.LibraryBranchDAO;
//import com.ss.training.spring.lms.entity.Book;
import com.ss.training.spring.lms.entity.BookCopies;
import com.ss.training.spring.lms.entity.BookCopiesKey;
//import com.ss.training.spring.lms.entity.BookCopiesKey;
import com.ss.training.spring.lms.entity.LibraryBranch;

public class LibraryService {

	@Autowired
	BookCopiesDAO bookCopiesDao;

	@Autowired
	LibraryBranchDAO libraryBranchDao;

	// -----------------Library Branch Services---------------------
	public Integer updateLibraryBranch(LibraryBranch branch) {
		// update branch if id matches existing record
		if (libraryBranchDao.findById(branch.getBranchId()).isPresent()) {
			libraryBranchDao.save(branch);
			return 1;
		} else {
			return 0;
		}
	}

	public List<LibraryBranch> readAllLibraryBranch() {
		return libraryBranchDao.findAll();
	}

	public List<LibraryBranch> readLibraryBranch(Long branchId) {
		return libraryBranchDao.findByBranchId(branchId);
	}

	// -----------------Book Copies Services---------------------

	public Integer updateBookCopies(BookCopies copy) {
		// search for by id
		if (bookCopiesDao.findById(copy.getId()).isPresent()) {
			bookCopiesDao.save(copy);
			return 1;
		} else {
			return 0;
		}
	}

	public Optional<BookCopies> readBookCopiesByKey(BookCopiesKey id) {
		return bookCopiesDao.findById(id);

	}

	public List<BookCopies> readBookCopiesByBranchId(Long branchId) {
		return bookCopiesDao.findByIdBranchId(branchId);

	}

}
