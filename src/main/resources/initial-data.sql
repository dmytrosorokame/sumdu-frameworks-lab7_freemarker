-- Initial data for testing (25 books)
INSERT INTO books (title, author, pub_year)
VALUES ('Clean Code', 'Robert Martin', 2008);
INSERT INTO books (title, author, pub_year)
VALUES ('Effective Java', 'Joshua Bloch', 2017);
INSERT INTO books (title, author, pub_year)
VALUES ('Design Patterns', 'Gang of Four', 1994);
INSERT INTO books (title, author, pub_year)
VALUES ('Head First Java', 'Kathy Sierra', 2005);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Java Concurrency in Practice',
    'Brian Goetz',
    2006
  );
INSERT INTO books (title, author, pub_year)
VALUES ('Refactoring', 'Martin Fowler', 1999);
INSERT INTO books (title, author, pub_year)
VALUES ('Clean Architecture', 'Robert Martin', 2017);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Working Effectively with Legacy Code',
    'Michael Feathers',
    2004
  );
INSERT INTO books (title, author, pub_year)
VALUES ('Domain-Driven Design', 'Eric Evans', 2003);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Patterns of Enterprise Application Architecture',
    'Martin Fowler',
    2002
  );
INSERT INTO books (title, author, pub_year)
VALUES ('Test-Driven Development', 'Kent Beck', 2002);
INSERT INTO books (title, author, pub_year)
VALUES ('Implementation Patterns', 'Kent Beck', 2008);
INSERT INTO books (title, author, pub_year)
VALUES ('Clean Coder', 'Robert Martin', 2011);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Agile Software Development',
    'Robert Martin',
    2002
  );
INSERT INTO books (title, author, pub_year)
VALUES (
    'Growing Object-Oriented Software',
    'Steve Freeman',
    2009
  );
INSERT INTO books (title, author, pub_year)
VALUES ('Continuous Delivery', 'Jez Humble', 2010);
INSERT INTO books (title, author, pub_year)
VALUES ('Release It!', 'Michael Nygard', 2007);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Enterprise Integration Patterns',
    'Gregor Hohpe',
    2003
  );
INSERT INTO books (title, author, pub_year)
VALUES (
    'The Pragmatic Programmer',
    'Andrew Hunt, David Thomas',
    1999
  );
INSERT INTO books (title, author, pub_year)
VALUES ('Clean Agile', 'Robert Martin', 2019);
INSERT INTO books (title, author, pub_year)
VALUES ('Effective JavaScript', 'David Herman', 2012);
INSERT INTO books (title, author, pub_year)
VALUES ('You Dont Know JS', 'Kyle Simpson', 2015);
INSERT INTO books (title, author, pub_year)
VALUES ('Programming Pearls', 'Jon Bentley', 1986);
INSERT INTO books (title, author, pub_year)
VALUES ('Code Complete', 'Steve McConnell', 2004);
INSERT INTO books (title, author, pub_year)
VALUES (
    'Structure and Interpretation of Computer Programs',
    'Harold Abelson',
    1996
  );
INSERT INTO comments (book_id, author, text)
VALUES (1, 'John Doe', 'Чудова книга для розробників!');
INSERT INTO comments (book_id, author, text)
VALUES (1, 'Jane Smith', 'Обов''язково до прочитання');
INSERT INTO comments (book_id, author, text)
VALUES (2, 'Alice Brown', 'Дуже корисні поради');
INSERT INTO comments (book_id, author, text)
VALUES (3, 'Bob Johnson', 'Класична книга з паттернів');

