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

insert into Employee(FName, LName, BirthDate, DepartmentID, PositionID, Email, Number) values
('Colby', 'Guerra', '1992-04-13', 2, 1, 'email1@company.com', '094875898'),
('Teegan', 'Lewis', '1998-11-22', 1, 3, 'email2@company.com', '084928495'),
('Toby', 'Sutton', '2000-09-03', 3, 2, 'email3@company.com', '064356336'),
('Ashley', 'Trang', '1995-05-06', 2, 2, 'email4@company.com', '059695043'),
('Evangeline', 'Connor', '1998-06-17', 1, 1, 'email5@company.com', '088734953'),
('Wade', 'Malone', '2000-06-01', 4, 2, 'email6@company.com', '093568356'),
('Hoang', 'Quang', '1999-04-13', 4, 1, 'email7@company.com', '053589531');

insert into Employee(FName, LName, BirthDate, DepartmentID, PositionID, Email, Number) values
('Nguyen', 'Minh', '2002-07-13', 4, 4, 'minh@company.com', '0965381566');
insert into EmployeeStatus (EmpID, Attendance, LastAttend, Strikes) values
(8, 8, '2022-03-08', 0);
insert into LogInInfo (EmpID,Account,Password) values
(8, 'minh', '123');
insert into TaskInfo (Name, [Desc], Deadline) values
('test\n2line', 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', '2020-04-03 00:00:00'),
('The very first Task', 'First do this.\nThen do this\nFinish it', '2020-07-13 00:00:00'),
('The Second but longgggggggg Task', 'First do this.123444\nThen do this 568937.\n Finish it', '2022-04-01 18:00:00');
insert into StaffTask (id, EmpID, Seen, Mark) values
(3, 8, 0, 0),
(1, 8, 1, 70),
(2, 8, 0, 0);
insert into Fine (empid, fine, [desc]) values
(1, 50000, 'late for work'),
(8, 100000, 'late for assignment');

insert into EmployeeStatus (EmpID, Attendance, LastAttend, Strikes) values
(1, 8, '2022-03-08', 0),
(2, 7, '2022-03-08', 1),
(3, 8, '2022-03-08', 0),
(4, 6, '2022-03-08', 2),
(5, 6, '2022-03-08', 0),
(6, 7, '2022-03-08', 1),
(7, 8, '2022-03-08', 0);

insert into LogInInfo (EmpID,Account,Password) values
(1, 'email1@company.com', '123456'),
(2, 'email2@company.com', '123456'),
(3, 'email3@company.com', '123456'),
(4, 'email4@company.com', '123456'),
(5, 'email5@company.com', '123456'),
(6, 'email6@company.com', '123456'),
(7, 'email7@company.com', '123456');

insert into Salary (EmpID,BaseSal,Extra) values
(8, 2000000, 50000),
(1, 2500000, 100000),
(2, 1000000, 0),
(3, 1800000, 50000),
(4, 1800000, 0),
(5, 2500000, 0),
(6, 1800000, 80000),
(7, 2500000, 0);

insert into AdminInfo (Account, Password) values
('admin', 'admin');

