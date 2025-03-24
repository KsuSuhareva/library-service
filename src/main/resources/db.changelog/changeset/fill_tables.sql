INSERT INTO subscriptions (user_login, user_full_name, user_email, borrow_allowed)
VALUES
('john_doe', 'John Doe', 'k.sukhareva@itq-group.com', true),
('jane_smith', 'Jane Smith', 'jane.smith@example.com', true),
('michael_jones', 'Michael Jones', 'michael.jones@example.com', false);

INSERT INTO books (title, author, published_date, subscription_id)
VALUES
('The Catcher in the Rye', 'J.D. Salinger', '1951-07-16', 1),
('To Kill a Mockingbird', 'Harper Lee', '1960-07-11', 1),
('1984', 'George Orwell', '1949-06-08', 2),
('Pride and Prejudice', 'Jane Austen', '1813-01-28', null);

INSERT INTO accounting_books (subscription_id, book_id, borrowed_date, returned_date)
VALUES
(1, 1, '2023-01-15', '2023-01-30'),
(1, 2, '2023-02-01', NULL),
(2, 3, '2023-03-10', '2023-03-20'),
(3, 4, '2023-04-05', '2023-04-10');