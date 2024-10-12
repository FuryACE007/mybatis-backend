-- Schema definition for the departments and employees

-- Department
CREATE TABLE dept (
	deptno   INTEGER      PRIMARY KEY,
	dname    VARCHAR(14),
	loc      VARCHAR(13),
	
);

-- Employees
CREATE TABLE emp (
	empno    INTEGER      PRIMARY KEY,
	ename    VARCHAR(10),
	job      VARCHAR(9),
	mgr      INTEGER,
	hiredate DATE,
	sal      NUMERIC(7,2),
	comm     NUMERIC(7,2),
	deptno   INTEGER      REFERENCES dept ( deptno )
) ;
