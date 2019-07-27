

--Question 1 Sample
SELECT DimDate.WEEK_DAY_FULL, COUNT(*) as LoanAmount
FROM FactLoan
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.')
GROUP BY DimDate.WEEK_DAY_FULL
ORDER BY LoanAmount DESC;


--Question 2 Sample
SELECT DimResource.CAMPUS_NAME, sum(FactLoan.TOTAL_FINES_PAID) as Total_Fines
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.') 
GROUP BY DimResource.Campus_Name
ORDER BY Total_Fines ASC;

--Question 3 Sample
SELECT DimResource.CAMPUS_NAME, COUNT(*) as Loan_Amount
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')
AND FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
GROUP BY DimResource.CAMPUS_NAME
ORDER BY Loan_Amount DESC;

--Question 4 Sample
SELECT DimDate.Quarter_ID, ROUND(AVG(FactLoan.RETAINED_DURATION)) as Average_Retained
FROM FactLoan
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.')
GROUP BY DimDate.Quarter_ID
ORDER BY Average_Retained DESC;



--Question 5 sample
SELECT DimResource.Resource_Type, COUNT(*) as Loan_Amount
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.') 
GROUP BY DimResource.Resource_Type
ORDER BY Loan_Amount DESC;


--Question 6 Sample
SELECT DimResource.CAMPUS_NAME, COUNT(*) as Loan_Amount
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')
AND REGEXP_LIKE(DimDate.QUARTER_ID, 'Q2-2018')
GROUP BY DimResource.CAMPUS_NAME
ORDER BY Loan_Amount DESC;



--Question 7 Sample
SELECT DimResource.CAMPUS_NAME, COUNT(*) as Unpaid_Fines
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.')
AND FactLoan.RETAINED_DURATION > DimResource.MAXIMUM_LOAN_LENGTH
OR FactLoan.RETAINED_DURATION IS NULL AND ((sysdate - FactLoan.DAY_KEY) > DimResource.MAXIMUM_LOAN_LENGTH)
AND FactLoan.TOTAL_FINES_PAID IS NULL
GROUP BY DimResource.Campus_Name
ORDER BY Unpaid_Fines DESC;


--Question 8 Sample
SELECT DimDate.QUARTER_ID, COUNT(*) as Unreturned_Resources
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')
AND REGEXP_LIKE(DimDate.YEAR_ID, '2018')
AND FactLoan.TOTAL_FINES_PAID IS NULL
AND (FactLoan.RETAINED_DURATION > DimResource.MAXIMUM_LOAN_LENGTH
    OR FactLoan.RETAINED_DURATION IS NULL AND ((sysdate - FactLoan.DAY_KEY) > DimResource.MAXIMUM_LOAN_LENGTH))
GROUP BY DimDate.QUARTER_ID
ORDER BY Unreturned_Resources DESC;

--Question 9 Sample
SELECT FactLoan.DAY_KEY, COUNT(*) as Loan_Amount
FROM FactLoan
INNER JOIN DimDate
ON DimDate.DAY_KEY = FactLoan.DAY_KEY
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')
AND REGEXP_LIKE(DimDate.YEAR_ID, '2018')
GROUP BY FactLoan.DAY_KEY
ORDER BY Loan_Amount DESC;

--Question 10 Sample
SELECT DimResource.RESOURCE_TITLE, COUNT(*) as Loan_Amount
FROM FactLoan
INNER JOIN DimResource
ON DimResource.Resource_Key = FactLoan.Resource_Key
WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('01/01/2018', 'mm/dd/yyyy')
AND TO_DATE('01/01/2019', 'mm/dd/yyyy')
AND REGEXP_LIKE(DimResource.Campus_Name, '.') 
GROUP BY DimResource.RESOURCE_TITLE
ORDER BY Loan_Amount DESC;

