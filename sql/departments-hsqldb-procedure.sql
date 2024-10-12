
-- To run this, move the file to the resource folder and include the following in the beans.xml file: 
--         <jdbc:script location="classpath:departments-hsqldb-procedure.sql" separator="#" />
-- The separator declaration prevents confusion between the semicolons in the procedure and the end of the procedure itself

CREATE PROCEDURE proc_reassign_emp_delete_dept(
	IN parm_dept_to_delete INTEGER,
	IN parm_dept_to_assign INTEGER
)
MODIFIES SQL DATA
BEGIN ATOMIC
	UPDATE emp
	SET	   deptno = parm_dept_to_assign
	WHERE  deptno = parm_dept_to_delete;
	
    DELETE FROM
           dept
	WHERE  deptno = parm_dept_to_delete;

END
#
