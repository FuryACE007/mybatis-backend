
-- This is the Oracle version. The HyperSQL version is in the file departments-hsqldb-procedure.sql.
-- You may use either or both
CREATE OR REPLACE PROCEDURE proc_reassign_emp_delete_dept(
	parm_dept_to_delete IN NUMBER,
	parm_dept_to_assign IN NUMBER
)
IS
BEGIN
	UPDATE emp
	SET	   deptno = parm_dept_to_assign
	WHERE  deptno = parm_dept_to_delete;
	
    DELETE FROM
           dept
	WHERE  deptno = parm_dept_to_delete;

END;
/

