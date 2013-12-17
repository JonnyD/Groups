-- Citadel 3 Schema

CREATE TABLE groups_group (
  `id` INT AUTO_INCREMENT,
  `name` VARCHAR(255) UNIQUE,
  `password` VARCHAR(255),
  `personal` TINYINT(1) DEFAULT 0,
  `status` INT(2) DEFAULT 0,
  `updated` DATETIME,
  `created` DATETIME,
  PRIMARY KEY (id)
);


CREATE TABLE groups_member (
  `id` INT AUTO_INCREMENT,
  `name` VARCHAR(32) UNIQUE,
  `updated` DATETIME,
  `created` DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE groups_group_member (
  `memberId` INT,
  `groupId` INT,
  `role` INT DEFAULT 0,
  `updated` DATETIME,
  `created` DATETIME,
  PRIMARY KEY (memberId, groupId)
);

-- Citadel 3 Upgrade Script

-- Migrate members
INSERT INTO groups_member (name, updated, created) 
SELECT name, now(), now() FROM member;

-- Migrate Groups
INSERT INTO groups_group(name, password, updated, created) 
SELECT name, password, now(), now() FROM faction;

-- Migrate Personal Groups
UPDATE groups_group 
SET personal = 1
WHERE name IN (SELECT name FROM personal_group);

-- Migrate Disciplined Groups
UPDATE groups_group g
JOIN faction f on g.name = f.name
SET status = 2
WHERE f.discipline_flags = 1;

-- Migrate Group Admins
INSERT INTO groups_group_member(memberId, groupId, role, updated, created) 
SELECT gm.id AS memberId, g.id AS groupId, 0, now(), now() 
FROM groups_member gm, faction f, groups_group g
WHERE gm.name = f.founder AND g.name = f.name;

-- Migrate Group Members
INSERT INTO groups_group_member(memberId, groupId, role, updated, created) 
SELECT gm.id AS memberId, g.id AS groupId, 2, now(), now()
FROM faction_member fm, groups_member gm, groups_group g
WHERE gm.name = fm.memberName AND g.name = fm.factionName;

-- Migrate Group Moderators
INSERT INTO groups_group_member(memberId, groupId, role, updated, created)
SELECT gm.id AS memberId, g.id AS groupId, 1, now(), now()
FROM moderator m, groups_member gm, groups_group g
WHERE gm.name = m.memberName AND g.name = m.factionName;

-- Citadel 2 Schema
SELECT * FROM faction;
SELECT * FROM personal_group;
SELECT * FROM member;
SELECT * FROM faction_member;
SELECT * FROM moderator;

-- Citadel 3 Schema
SELECT * FROM groups_member;
SELECT * FROM groups_group;
SELECT * FROM groups_group_member;
