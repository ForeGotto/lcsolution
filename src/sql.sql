/*
Table: Person

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
PersonId is the primary key column for this table.
Table: Address

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
AddressId is the primary key column for this table.
Write a SQL query for a report that provides the following information for each person in the Person table, regardless if there is an address for each of those people:

FirstName, LastName, City, State
 */
SELECT
  FirstName, LastName, City, State
FROM
  Person LEFT JOIN Address
ON
  Person.PersonId = Address.PersonId;

/*
Write a SQL query to get the second highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the query should return 200 as the second highest salary.
If there is no second highest salary, then the query should return null.

+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
 */
SELECT
  max(e.Salary) AS SecondHighestSalary
FROM
  Employee e
WHERE
  e.Salary NOT IN
  (SELECT max(f.Salary) FROM Employee f);

/*
Write a SQL query to get the nth highest salary from the Employee table.

+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
For example, given the above Employee table, the nth highest salary where n = 2 is 200.
If there is no nth highest salary, then the query should return null.

+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+
 */
# sure you can use {limit N-1, 1} to solve this problem
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
  BEGIN
    SET N = N - 1;
    RETURN (
      # Write your MySQL query statement below.
      SELECT
        max(e.Salary)
      FROM
        Employee e
      WHERE
        e.Salary NOT IN
        (SELECT * FROM (SELECT DISTINCT f.Salary FROM Employee f ORDER BY f.Salary DESC LIMIT N) t)
    );
  END;

/*
Write a SQL query to rank scores.
If there is a tie between two scores, both should have the same ranking.
Note that after a tie, the next ranking number should be the next consecutive integer value.
In other words, there should be no "holes" between ranks.

+----+-------+
| Id | Score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+
For example, given the above Scores table, your query should generate the following report (order by highest score):

+-------+------+
| Score | Rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+
 */
SET @r = 0;
SET @l = -1;
SELECT
  Score, Rank
FROM
  (
    SELECT
      Score,
      if(@l=Score, @r:=@r, @r:=@r+1) as Rank,
      if(@l=Score, @l:=Score, @l:=Score) as last
    FROM Scores
    ORDER BY Score DESC
  ) t;

/*
Write a SQL query to find all numbers that appear at least three times consecutively.

+----+-----+
| Id | Num |
+----+-----+
| 1  |  1  |
| 2  |  1  |
| 3  |  1  |
| 4  |  2  |
| 5  |  1  |
| 6  |  2  |
| 7  |  2  |
+----+-----+
For example, given the above Logs table,
1 is the only number that appears consecutively for at least three times.

+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+
 */
SET @last := (SELECT Num FROM Logs LIMIT 1);
SET @count := 0;
SELECT DISTINCT t.Num AS ConsecutiveNums
FROM (
  SELECT
    Num,
    if (@last = Num, @count := @count + 1, @count := 1) c,
    (@last := Num) l
  FROM
    Logs
) t
WHERE c > 2;

/*
The Employee table holds all employees including their managers.
Every employee has an Id, and there is also a column for the manager Id.

+----+-------+--------+-----------+
| Id | Name  | Salary | ManagerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+
Given the Employee table, write a SQL query that finds out employees who earn more than their managers.
For the above table, Joe is the only employee who earns more than his manager.

+----------+
| Employee |
+----------+
| Joe      |
+----------+
 */
SELECT
  e1.Name AS Employee
FROM
  Employee e1 JOIN Employee e2 ON e1.ManagerId = e2.Id
WHERE e1.Salary > e2.Salary;
