CREATE TABLE customers (
name NVARCHAR(128) NOT NULL,
address NVARCHAR(128),
category TINYINT NOT NULL
CHECK(category<=10 AND category >= 1),
CONSTRAINT PK_customer
PRIMARY KEY(name)
);
go
CREATE TABLE assemblies (
id INT NOT NULL IDENTITY,
customer_name NVARCHAR(128),
dateordered DATETIME NOT NULL,
assemblydetails NVARCHAR(128),
CONSTRAINT FK_customer_assembly
FOREIGN KEY (customer_name)
REFERENCES customers(name),
CONSTRAINT PK_assembly
PRIMARY KEY(id)
);
go
CREATE TABLE departments (
id INT NOT NULL IDENTITY,
departmentdata NVARCHAR(128),
CONSTRAINT PK_department
PRIMARY KEY(id)
);
CREATE TABLE cut_processes (
id INT NOT NULL ,
department INT,
cuttingtype NVARCHAR(128),
machinetype NVARCHAR(128),
CONSTRAINT  PK_cut_processes
PRIMARY KEY (id),
CONSTRAINT FK_cut_processes_departments
FOREIGN KEY (department)
REFERENCES departments(id)
);
go
CREATE TABLE paint_processes (
id INT NOT NULL,
department INT,
painttype NVARCHAR(128),
paintingmethod NVARCHAR(128),
CONSTRAINT  PK_paint_processes
PRIMARY KEY (id),
CONSTRAINT FK_paint_processes_departments
FOREIGN KEY (department)
REFERENCES departments(id)
);
go
CREATE TABLE fit_processes (
id INT NOT NULL,
department INT,
fittype NVARCHAR(128),
CONSTRAINT  PK_fit_processes
PRIMARY KEY (id),
CONSTRAINT FK_fit_processes_departments
FOREIGN KEY (department)
REFERENCES departments(id)
);
go
CREATE TABLE cut_jobs (
id INT NOT NULL ,
assemblyid INT NOT NULL,
processid INT NOT NULL,
startdate DATETIME NOT NULL,
enddate DATETIME,
cuttingtime int,
machinetype NVARCHAR(128),
materialused NVARCHAR(128),
labortime int,
CONSTRAINT  PK_cut_jobs
PRIMARY KEY (id),
);
go
CREATE TABLE paint_jobs (
id INT NOT NULL,
assemblyid INT NOT NULL,
processid INT NOT NULL,
startdate DATETIME NOT NULL,
enddate DATETIME,
paintcolor NVARCHAR(128),
paintvolume NVARCHAR(128),
labortime int,
CONSTRAINT  PK_paint_jobs
PRIMARY KEY (id),
);
go
CREATE TABLE fit_jobs (
id INT NOT NULL,
assemblyid INT NOT NULL,
processid INT NOT NULL,
startdate DATETIME NOT NULL,
enddate DATETIME,
fittype NVARCHAR(128),
labortime int,
CONSTRAINT  PK_fit_jobs
PRIMARY KEY (id),
);
go
CREATE TABLE assembly_accounts(
id INT NOT NULL,
dateestablished DATETIME NOT NULL,
assemblyid INT NOT NULL,
CONSTRAINT PK_assembly_account_number
PRIMARY KEY (id),
CONSTRAINT FK_assembly_account_assembly
FOREIGN KEY (assemblyid)
REFERENCES assemblies(id)
);
go
CREATE TABLE department_accounts(
id INT NOT NULL,
dateestablished DATETIME NOT NULL,
departmentid INT NOT NULL,
CONSTRAINT PK_department_account_number
PRIMARY KEY (id),
CONSTRAINT FK_department_account_department
FOREIGN KEY (departmentid)
REFERENCES departments(id)
);
go
CREATE TABLE process_accounts(
id INT NOT NULL,
dateestablished DATETIME NOT NULL,
processid INT NOT NULL,
CONSTRAINT PK_process_account_number
PRIMARY KEY (id)
);
go
CREATE TABLE transactions (
id INT NOT NULL,
supcost MONEY,
assemblyaccountid int,
processaccountid int,
departmentaccountid int
CONSTRAINT PK_transactions
PRIMARY KEY (id),
CONSTRAINT FK_assembly_account_id
FOREIGN KEY (assemblyaccountid)
REFERENCES assembly_accounts(id),
CONSTRAINT FK_process_account_id
FOREIGN KEY (processaccountid)
REFERENCES process_accounts(id),
CONSTRAINT FK_department_account_id
FOREIGN KEY (departmentaccountid)
REFERENCES department_accounts(id)
);
go
CREATE TRIGGER TR_fit_process_insert
ON fit_processes after insert
AS
if exists (
select id from inserted where id  IN (
SELECT id FROM paint_processes
UNION SELECT id FROM cut_processes
) ) BEGIN ROLLBACK TRANSACTION END
go
CREATE TRIGGER TR_paint_process_insert
ON paint_processes after insert
AS
if exists (
select id from inserted where id  IN (
SELECT id FROM fit_processes
UNION SELECT id FROM cut_processes
) ) BEGIN ROLLBACK TRANSACTION END
go
CREATE TRIGGER TR_cut_process_insert
ON cut_processes after insert
AS
if exists (
select id from inserted where id  IN (
SELECT id FROM paint_processes
UNION SELECT id FROM fit_processes
) ) BEGIN ROLLBACK TRANSACTION END
go
CREATE TRIGGER TR_process_account_id_rel
ON process_accounts
AFTER INSERT
as
if exists(
select processid from inserted
WHERE PROCESSID NOT IN(
SELECT id FROM cut_processes
UNION SELECT id FROM paint_processes
UNION SELECT id FROM fit_processes
)
)BEGIN ROLLBACK TRANSACTION END;