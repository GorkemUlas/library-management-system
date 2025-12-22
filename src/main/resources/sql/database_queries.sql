-- ============================================================
-- USER AUTHENTICATION & FETCH
-- ============================================================

-- Login: Hibernate generates a select with parameter binding
select u.user_id, u.name, u.email, u.password, u.role
from users u
where u.email = ?;

-- Fetch user by ID
select u.user_id, u.name, u.email, u.role
from users u
where u.user_id = ?;

-- List all users (with pagination)
select u.user_id, u.name, u.email, u.role
from users u
order by u.user_id asc
    limit ? offset ?;

-- Check if user exists
select count(*) from users where email = ?;

-- Insert new user
insert into users (user_id, name, email, password, role)
values (?, ?, ?, ?, 'USER');

-- Update user info
update users
set name = ?, email = ?
where user_id = ?;

-- Delete user (only if safe)
delete from users where user_id = ?;

-- ============================================================
-- ADMIN DASHBOARD METRICS
-- ============================================================

-- Total users
select count(*) as total_users from users;

-- Total books
select count(*) as total_books from books;

-- Active loans
select count(*) as active_loans from loans where status = 'ACTIVE';

-- Active holds
select count(*) as active_holds from holds where status = 'PENDING';

-- Overdue loans
select count(*) as overdue_loans from loans where status = 'OVERDUE';

-- Most borrowed category
select b.category, count(*) as borrow_count
from loans l
         join books b on l.book_id = b.book_id
group by b.category
order by borrow_count desc
    limit 1;

-- Stock status: available / low / out-of-stock
select book_id, title,
       case
           when available_copies = 0 then 'OUT_OF_STOCK'
           when available_copies < 3 then 'LOW_STOCK'
           else 'AVAILABLE'
           end as stock_status
from books;


-- ============================================================
-- BOOK MANAGEMENT
-- ============================================================

-- Insert new book
insert into books (book_id, title, author, category, total_copies, available_copies, created_at)
values (?, ?, ?, ?, ?, ?, ?);

-- Fetch all books (with pagination)
select b.book_id, b.title, b.author, b.category, b.total_copies, b.available_copies
from books b
order by b.title asc
    limit ? offset ?;

-- Fetch book by ID
select b.book_id, b.title, b.author, b.category, b.total_copies, b.available_copies
from books b
where b.book_id = ?;

-- Update book info
update books
set title = ?, author = ?, category = ?, total_copies = ?, available_copies = ?
where book_id = ?;

-- Delete book (only if safe)
select count(*) from loans where book_id = ? and status = 'ACTIVE';
-- If count = 0:
delete from books where book_id = ?;

-- Search books by title (Spring Data JPA query method)
select b.book_id, b.title, b.author, b.category
from books b
where lower(b.title) like lower(?);

-- Search books by category
select b.book_id, b.title, b.author, b.category
from books b
where b.category = ?;



-- ============================================================
-- LOAN SYSTEM
-- ============================================================

-- Borrow book (create loan + decrease stock)
insert into loans (loan_id, user_id, book_id, issue_date, due_date, status)
values (?, ?, ?, ?, ?, 'ACTIVE');

update books
set available_copies = available_copies - 1
where book_id = ?;

-- Fetch active loans for a user
select l.loan_id, l.book_id, l.issue_date, l.due_date, l.status, b.title, b.author
from loans l
         join books b on l.book_id = b.book_id
where l.user_id = ? and l.status = 'ACTIVE';

-- Fetch loan history for a user
select l.loan_id, l.book_id, l.issue_date, l.return_date, l.status, b.title
from loans l
         join books b on l.book_id = b.book_id
where l.user_id = ? and l.status = 'RETURNED';

-- Return book
update loans
set status = 'RETURNED', return_date = ?
where loan_id = ?;

update books
set available_copies = available_copies + 1
where book_id = ?;

-- Overdue loans check
select l.loan_id, l.user_id, l.book_id, l.due_date
from loans l
where l.status = 'ACTIVE' and l.due_date < current_date;



-- ============================================================
-- HOLD SYSTEM
-- ============================================================

-- Create hold (when book is out of stock)
insert into holds (hold_id, user_id, book_id, hold_date, status)
values (?, ?, ?, ?, 'PENDING');

-- Fetch active holds for a user
select h.hold_id, h.book_id, h.hold_date, h.status, b.title
from holds h
         join books b on h.book_id = b.book_id
where h.user_id = ? and h.status = 'PENDING';

-- Complete hold (when book becomes available)
update holds
set status = 'COMPLETED'
where hold_id = ?;

-- Auto-loan from hold queue
select h.hold_id, h.user_id, h.book_id
from holds h
where h.book_id = ? and h.status = 'PENDING'
order by h.hold_date asc
    limit 1;

-- If hold exists:
insert into loans (loan_id, user_id, book_id, issue_date, due_date, status)
values (?, ?, ?, ?, ?, 'ACTIVE');
delete from holds where hold_id = ?;



-- ============================================================
-- SAFETY & INTEGRITY RULES
-- ============================================================

-- User delete safety check
select count(*) from loans where user_id = ? and status = 'ACTIVE';
select count(*) from holds where user_id = ? and status = 'PENDING';
-- If both counts = 0:
delete from users where user_id = ?;

-- Book delete safety check
select count(*) from loans where book_id = ? and status = 'ACTIVE';
-- If count = 0:
delete from books where book_id = ?;



-- ============================================================
-- ADVANCED HIBERNATE QUERIES
-- ============================================================

-- Lazy loading: Hibernate loads only user basic info
select u.user_id, u.name, u.email, u.role
from users u
where u.user_id = ?;

-- When accessing user.loans → Hibernate triggers another query
select l.loan_id, l.book_id, l.issue_date, l.due_date, l.status
from loans l
where l.user_id = ?;

-- Join fetch (to avoid N+1 problem)
select u.user_id, u.name, u.email, u.role,
       l.loan_id, l.issue_date, l.due_date, l.status,
       b.book_id, b.title, b.author
from users u
         left join loans l on u.user_id = l.user_id
         left join books b on l.book_id = b.book_id
where u.user_id = ?;

-- Pagination query (Spring Data JPA)
select b.book_id, b.title, b.author, b.category
from books b
order by b.title asc
    limit ? offset ?;

-- Sorting query
select b.book_id, b.title, b.author, b.category
from books b
order by b.category asc;



-- ============================================================
-- EXTRA QUERIES
-- ============================================================

-- Audit log insert (optional feature)
insert into audit_logs (log_id, user_id, action, entity, entity_id, timestamp)
values (?, ?, ?, ?, ?, ?);

-- Fetch reading statistics per user
select u.user_id, u.name, count(l.loan_id) as total_read
from users u
         join loans l on u.user_id = l.user_id
where l.status = 'RETURNED'
group by u.user_id;

-- Top 5 most borrowed books
select b.title, count(l.loan_id) as borrow_count
from loans l
         join books b on l.book_id = b.book_id
group by b.title
order by borrow_count desc
    limit 5;
