select * from books;

select count(id) from users;

select count(distinct id) from users;

select * from users;

select count(*) from book_borrow
where is_returned = 0;

SELECT * from books where name='Book Borrow 2';