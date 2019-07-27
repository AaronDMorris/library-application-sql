--Insert test data for the Users table
--PASSWORDS ARE HASHES OF THE USER NAME: e.g username = vicechancellor, password = vicechancellor
INSERT INTO tblUsers
(Username, Password_Hash, Has_Level1_Access, Has_Level2_Access, Has_Level3_Access)
VALUES
('vicechancellor', '$2a$12$kvRPMcjgYR5ZWuBMRlbhuev8vfwdI.ok9rNS7qFBpST5xp/i/bI.q', 1, 1, 1);

INSERT INTO tblUsers
(Username, Password_Hash, Has_Level1_Access, Has_Level2_Access, Has_Level3_Access)
VALUES
('chieflibrarian', '$2a$12$TUXtd9T9Xi0/Bdx8.ob7zeu/msommNF7VxbCuz5zz.cbUVDH775QW', 1, 1, 0);

INSERT INTO tblUsers
(Username, Password_Hash, Has_Level1_Access, Has_Level2_Access, Has_Level3_Access)
VALUES
('headcampuslibrarian', '$2a$12$dafon/Zfp5jHEHV0vZjoX.NTHbWfJlXgYo9lbgeE3KM9eEA/hU1Fa', 1, 0, 0);
