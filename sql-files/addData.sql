use HRManagement;

insert into Department(DepartmentID, Name) values
(1, 'Administration/operations'),
(2, 'Marketing'),
(3, 'Customer service'),
(4, 'Designing'),
(5, 'Accounting');

insert into Position(PositionID, Name) values
(1, 'Department Manager'),
(2, 'Manager''s Secretary'),
(3, 'Senior/Mentor'),
(4, 'Staff');

insert into Employee(FName, LName, BirthDate, Age, DepartmentID, PositionID, Email, Number) values
('Colby', 'Guerra', '1992-04-13', 30, 2, 1, 'email1@company.com', '094875898'),
('Teegan', 'Lewis', '1998-11-22', 24, 1, 3, 'email2@company.com', '084928495'),
('Toby', 'Sutton', '2000-09-03', 22, 3, 2, 'email3@company.com', '064356336'),
('Ashley', 'Trang', '1995-05-06', 27, 2, 2, 'email4@company.com', '059695043'),
('Evangeline', 'Connor', '1998-06-17', 24, 1, 1, 'email5@company.com', '088734953'),
('Wade', 'Malone', '2000-06-01', 22, 4, 2, 'email6@company.com', '093568356'),
('Hoang', 'Quang', '1999-04-13', 23, 4, 1, 'email7@company.com', '053589531');

insert into EmployeeStatus (EmpID, Attendance, Strikes, Holidays) values
(1, 8, 0, 3),
(2, 7, 1, 3),
(3, 8, 0, 2),
(4, 6, 2, 1),
(5, 6, 0, 2),
(6, 7, 1, 3),
(7, 8, 0, 2);

insert into LogInInfo (EmpID,Account,Password) values
(1, 'email1@company.com', '123456'),
(2, 'email2@company.com', '123456'),
(3, 'email3@company.com', '123456'),
(4, 'email4@company.com', '123456'),
(5, 'email5@company.com', '123456'),
(6, 'email6@company.com', '123456'),
(7, 'email7@company.com', '123456');

insert into Salary (EmpID,BaseSal,Extra,Fine) values
(1, 2500000, 100000, 0),
(2, 1000000, 0, 20000),
(3, 1800000, 50000, 10000),
(4, 1800000, 0, 0),
(5, 2500000, 0, 100000),
(6, 1800000, 80000, 0),
(7, 2500000, 0, 50000);