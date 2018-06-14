DROP TABLE IF EXISTS changelog
/

CREATE TABLE changelog(
	script_file	VARCHAR(128) NOT NULL,
	PRIMARY KEY(script_file)
)