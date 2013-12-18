
-- ------------------------------------------------------------
-- Citadel 3 Schema
-- ------------------------------------------------------------

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

CREATE TABLE groups_membership (
  `member_id` INT,
  `group_id` INT,
  `role` INT DEFAULT 0,
  `updated` DATETIME,
  `created` DATETIME,
  PRIMARY KEY (member_id, group_id)
);

ALTER TABLE groups_membership
ADD FOREIGN KEY (member_id)
REFERENCES groups_member (id);

ALTER TABLE groups_membership
ADD FOREIGN KEY (group_id)
REFERENCES groups_group (id);

CREATE TABLE citadel_reinforcement (
  `id` INT AUTO_INCREMENT,
  `x` INT(11),
  `y` INT(11),
  `z` INT(11),
  `world` VARCHAR(20),
  `chunk_id` VARCHAR(20),
  `durability` INT(11),
  `security_level` INT(11),
  `group_id` INT(11),
  `material_id` INT(11),
  `insecure` TINYINT(1),
  `maturation_time` INT(11),
  `updated` DATETIME,
  `created` DATETIME,
  PRIMARY KEY (id)
);

ALTER TABLE citadel_reinforcement
ADD FOREIGN KEY (group_id)
REFERENCES groups_group (id);

-- ------------------------------------------------------------
-- Citadel 3 Upgrade Script
-- ------------------------------------------------------------

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
INSERT INTO groups_membership(member_id, group_id, role, updated, created) 
SELECT gm.id, g.id, 0, now(), now() 
FROM groups_member gm, faction f, groups_group g
WHERE gm.name = f.founder AND g.name = f.name;

-- Migrate Group Members
INSERT INTO groups_membership(member_id, group_id, role, updated, created) 
SELECT gm.id, g.id, 2, now(), now()
FROM faction_member fm, groups_member gm, groups_group g
WHERE gm.name = fm.memberName AND g.name = fm.factionName;

-- Migrate Group Moderators
INSERT INTO groups_membership(member_id, group_id, role, updated, created)
SELECT gm.id, g.id, 1, now(), now()
FROM moderator m, groups_member gm, groups_group g
WHERE gm.name = m.memberName AND g.name = m.factionName;

-- Migrate Reinforcements
INSERT INTO citadel_reinforcement(x, y, z, world, chunk_id, group_id, material_id, durability, security_level, insecure, maturation_time, updated, created)
SELECT r.x, r.y, r.z, r.world, r.chunk_id, g.id, r.material_id, r.durability, r.security_level, r.insecure, r.maturation_time, now(), now()
FROM reinforcement r, groups_group g
WHERE g.name = r.name;

-- Selects
SELECT * FROM groups_member;
SELECT * FROM groups_group;
SELECT * FROM groups_membership;
SELECT * FROM citadel_reinforcement;
