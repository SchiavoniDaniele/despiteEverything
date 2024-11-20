package main.controllers;

import main.dao.BookDAO;
import main.models.Book;

public class BookController {
private BookDAO bookDAO;
public BookController() {
	this.bookDAO=new BookDAO();
}
public Book createBook(int lvl) {
	Book book=new Book(lvl);
	book=bookDAO.save(book);
	return book;
}
public Book createBook(Book book) {
	return bookDAO.save(book);
}
}
