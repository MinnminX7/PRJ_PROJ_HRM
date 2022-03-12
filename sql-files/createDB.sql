create database HRManagement;

go
use HRManagement;
create table Department (
	DepartmentID int primary key,
	Name nvarchar(32)
);
create table Position (
	PositionID int primary key,
	Name nvarchar(32)
);
create table Employee (
	EmpID int identity(1,1) primary key,
	FName nvarchar(32),
	LName nvarchar(32),
	BirthDate date,
	Age int,
	DepartmentID int foreign key references Department(DepartmentID),
	PositionID int foreign key references Position(PositionID),
	Email nvarchar(32),
	Number nvarchar(12)
);
create table EmployeeStatus (
	EmpID int foreign key references Employee(EmpID),
	Attendance int,
	LastAttend date,
	Strikes int,
	Holidays int
);
create table Salary (
	EmpID int foreign key references Employee(EmpID),
	BaseSal int,
	Extra int,
	Fine int
);
create table LogInInfo (
	EmpID int foreign key references Employee(EmpID),
	Account nvarchar(128),
	Password nvarchar(128)
);
create table AdminInfo (
	Account nvarchar(128) primary key,
	Password nvarchar(128)
);
create table TaskInfo (
	id int primary key,
	Name nvarchar(128),
	[Desc] nvarchar(1024),
	Deadline datetime
);
create table StaffTask (
	id int foreign key references TaskInfo(id),
	EmpID int foreign key references Employee(EmpID),
	Seen bit
);
go