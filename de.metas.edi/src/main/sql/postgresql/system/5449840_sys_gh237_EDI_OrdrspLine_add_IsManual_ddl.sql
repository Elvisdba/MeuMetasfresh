-- 30.08.2016 11:48
-- URL zum Konzept
ALTER TABLE EDI_OrdrspLine ADD IsManual CHAR(1) DEFAULT 'Y' CHECK (IsManual IN ('Y','N')) NOT NULL
;

