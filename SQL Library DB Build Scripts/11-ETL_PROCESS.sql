Begin

    INSERT INTO DimResource (Resource_Key, Isbn, Campus_Name, Address_Line1, City, Resource_Title, Resource_Type,  Maximum_Loan_Length)
        SELECT tblResource.Resource_ID,tblResource.Isbn,
        (SELECT Campus_Name FROM tblCampus WHERE tblCampus.Campus_ID = tblResource.Campus_ID),
        (SELECT Address_Line1 FROM tblCampus WHERE tblCampus.Campus_ID = tblResource.Campus_ID),
        (SELECT City FROM tblCampus WHERE tblCampus.Campus_ID = tblResource.Campus_ID),
        tblResource.Resource_Title,
        (SELECT tblResourceType.Resource_Type FROM tblResourceType WHERE tblResource.RESOURCE_TYPE_ID = tblResourceType.RESOURCE_TYPE_ID),
        tblResource.Maximum_Loan_Length FROM tblResource;

    INSERT INTO FactLoan (Resource_Key, Day_Key, Retained_Duration, Total_Fines_Paid)
        SELECT tblLoanItem.Resource_ID,
        (SELECT DimDate.Day_Key From DimDate WHERE DimDate.Day_Key = to_date(tblLoan.DATE_ISSUED,'DD-MON-YY')),
        ((SELECT tblReturnItem.Date_Returned FROM tblReturnItem WHERE tblReturnItem.LOAN_ID = tblLoanItem.LOAN_ID AND ROWNUM <= 1) - (SELECT DimDate.Day_Key From DimDate WHERE DimDate.Day_Key = to_date(tblLoan.DATE_ISSUED,'DD-MON-YY'))),
        (select sum(Amount_Paid) from tblFine Where tblFine.Resource_ID = tblLoanItem.Resource_ID)
        FROM tblLoan 
        LEFT JOIN tblLoanItem ON tblLoan.Loan_ID = tblLoanItem.LOAN_ID
        WHERE tblLoan.DATE_ISSUED >= TO_DATE('2018/01/01', 'yyyy/mm/dd')
        AND tblLoan.DATE_ISSUED <= TO_DATE('2018/07/01','yyyy/mm/dd')
        ORDER BY tblLoanItem.RESOURCE_ID;

End;    



