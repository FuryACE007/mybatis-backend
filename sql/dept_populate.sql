
DROP TABLE dept CASCADE CONSTRAINTS;

--------------------------------------------------------
--  File created - Saturday-January-06-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table DEPT
--------------------------------------------------------

  CREATE TABLE "DEPT" 
   (	"DEPTNO" NUMBER(2,0), 
	"DNAME" VARCHAR2(14), 
	"LOC" VARCHAR2(13)
   ) ;
REM INSERTING into DEPT
SET DEFINE OFF;
Insert into DEPT (DEPTNO,DNAME,LOC) values (10,'ACCOUNTING','NEW YORK');
Insert into DEPT (DEPTNO,DNAME,LOC) values (20,'RESEARCH','DALLAS');
Insert into DEPT (DEPTNO,DNAME,LOC) values (30,'SALES','CHICAGO');
Insert into DEPT (DEPTNO,DNAME,LOC) values (40,'OPERATIONS','BOSTON');
--------------------------------------------------------
--  DDL for Index PK_DEPT
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DEPT" ON "DEPT" ("DEPTNO") 
  ;
--------------------------------------------------------
--  Constraints for Table DEPT
--------------------------------------------------------

  ALTER TABLE "DEPT" ADD CONSTRAINT "PK_DEPT" PRIMARY KEY ("DEPTNO") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table EMP
--------------------------------------------------------

  ALTER TABLE "EMP" ADD CONSTRAINT "FK_DEPTNO" FOREIGN KEY ("DEPTNO")
	  REFERENCES "DEPT" ("DEPTNO") ENABLE;
